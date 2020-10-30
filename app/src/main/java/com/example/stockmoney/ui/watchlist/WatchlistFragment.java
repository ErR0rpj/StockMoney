package com.example.stockmoney.ui.watchlist;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.stockmoney.HomeAdapterClass;
import com.example.stockmoney.R;
import com.example.stockmoney.data.StockFirebaseColumns;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class WatchlistFragment extends Fragment {

    private static final String LOG_TAG = WatchlistFragment.class.getSimpleName();

    private ListView LISThome;
    private HomeAdapterClass adapter;
    private TextView TVempty;
    private ProgressBar progressBar;

    //Variables for Firebase Database
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;
    public static ChildEventListener mChildEventListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_watchlist, container, false);

        TVempty = view.findViewById(R.id.TVempty);
        progressBar =  view.findViewById(R.id.progressBar);
        LISThome = view.findViewById(R.id.LISThome);

        TVempty.setText("Fetching Data....\nPlease Wait");

        ArrayList<StockFirebaseColumns> list = new ArrayList<>();
        adapter = new HomeAdapterClass(getActivity(), list);
        LISThome.setAdapter(adapter);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("stocks");

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                StockFirebaseColumns sfc = snapshot.getValue(StockFirebaseColumns.class);
                adapter.add(sfc);
                if(sfc != null) {
                    TVempty.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                }
                else{
                    progressBar.setVisibility(View.GONE);
                    TVempty.setText("Problem Retrieving Data....\nCheck your Network Connection");
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                StockFirebaseColumns sfc = snapshot.getValue(StockFirebaseColumns.class);

                //TODO: Change this id in Firebase acc to API-ID and subtract id variable accly to...
                //TODO: ...get id starting from 0.
                int id =  sfc.getId();
                double price = sfc.getPrice();
                String chg = sfc.getChg();

                View v = LISThome.getChildAt(id - LISThome.getFirstVisiblePosition());

                if(v == null){
                    Log.e(LOG_TAG, "view in change is null");
                    return;
                }

                TextView TVprice = v.findViewById(R.id.TVprice);
                TextView TVname = v.findViewById(R.id.TVname);
                TextView TVchg =  v.findViewById(R.id.TVchg);

                TVprice.setText(String.format("₹ %.2f", price));

                if(chg.charAt(0)=='-'){
                    TVchg.setTextColor(Color.RED);
                }
                else{
                    TVchg.setTextColor(Color.GREEN);
                }
                TVchg.setText(chg);

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(LOG_TAG, "Problem retreiving data from database: " + error.getCode());
            }
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);

        return view;
    }
}