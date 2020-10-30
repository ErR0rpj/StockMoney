package com.example.stockmoney.ui.portfolio;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.stockmoney.R;
import com.example.stockmoney.data.StocksOwn;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.stockmoney.data.DataItems.currentPriceMap;
import static com.example.stockmoney.data.DataItems.currentUser;
import static com.example.stockmoney.data.DataItems.stocksOwnList;

public class PortfolioFragment extends Fragment {

    private static final String LOG_TAG = PortfolioFragment.class.getSimpleName();

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference, mDatabaseReference2;
    private double valuation = 0;

    private TextView TVnetWorth, TVtotalGain;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_portfolio, container, false);

        TVnetWorth = root.findViewById(R.id.TVnetWorth);
        TVtotalGain = root.findViewById(R.id.TVtotalGain);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("users").child(currentUser.getUid());

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUser.setFunds(snapshot.child("funds").getValue(Double.class));
                valuation = valuation + currentUser.getFunds();
                Log.e(LOG_TAG, "valuation after funds: " + Double.toString(valuation));
                TVnetWorth.setText(Double.toString(valuation));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        //TODO: get stocksown items. Show them in list. Find networth by adding the current price from api.
        //TODO: update stockown items every time buy or sell works. To make app fast.

        mDatabaseReference2 = mFirebaseDatabase.getReference().child("users").child(currentUser.getUid()).child("stocksOwn");
        mDatabaseReference2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                StocksOwn currentOwnednow = snapshot.getValue(StocksOwn.class);
                Log.e(LOG_TAG, currentOwnednow.getSymbol());
                if(currentOwnednow != null){
                    stocksOwnList.add(currentOwnednow);
                    if(currentPriceMap.containsKey(currentOwnednow.getSymbol())){
                        valuation = valuation + (currentPriceMap.get(currentOwnednow.getSymbol()) * currentOwnednow.getQuantity());
                    }
                    else{
                        Log.e(LOG_TAG, "FIrst go to search fragment");
                    }
                }
                Log.e(LOG_TAG,"Valuation after own: " + Double.toString(valuation));
                TVnetWorth.setText(Double.toString(valuation));
                TVtotalGain.setText(Double.toString(valuation - 1000000));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //TODO: check if the app works properly even after the
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
        });

        return root;
    }
}