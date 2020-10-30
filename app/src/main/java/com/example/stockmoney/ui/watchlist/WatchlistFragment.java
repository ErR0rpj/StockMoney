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
import com.example.stockmoney.data.StocksOwn;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.stockmoney.data.DataItems.currentPriceMap;
import static com.example.stockmoney.data.DataItems.currentUser;

public class WatchlistFragment extends Fragment {

    private static final String LOG_TAG = WatchlistFragment.class.getSimpleName();

    private ListView LISThome;
    private HomeAdapterClass adapter;
    private ProgressBar progressBar;

    //Variables for Firebase Database
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;
    public static ChildEventListener mChildEventListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_watchlist, container, false);

        Log.e(LOG_TAG, currentUser.getUid());

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("users").child(currentUser.getUid()).child("stocksOwn");

        progressBar =  view.findViewById(R.id.progressBar);
        LISThome = view.findViewById(R.id.LISThome);

        ArrayList<StocksOwn> list = new ArrayList<>();
        adapter = new HomeAdapterClass(getActivity(), list);
        LISThome.setAdapter(adapter);

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                progressBar.setVisibility(View.VISIBLE);
                StocksOwn currentOwnednow = snapshot.getValue(StocksOwn.class);

                if(currentOwnednow != null){

                    if(currentPriceMap.containsKey(currentOwnednow.getSymbol())){
                        double stockPriceNow = currentPriceMap.get(currentOwnednow.getSymbol()) * currentOwnednow.getQuantity();
                        double avgPriceBought = currentOwnednow.getAvgPrice() * currentOwnednow.getQuantity();
                        double profit;
                        profit = stockPriceNow - avgPriceBought;
                        currentOwnednow.setProfit(profit);
                        adapter.add(currentOwnednow);
                    }
                    else{
                        Log.e(LOG_TAG, "First go to search fragment");
                    }
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);

        return view;
    }
}