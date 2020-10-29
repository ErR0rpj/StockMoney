package com.example.stockmoney.services;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.stockmoney.MainActivity;
import com.example.stockmoney.data.StocksOwn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Transactions {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private StocksOwn currentOwned;

    //TODO: Fetch latest data from firebase for the current stock and pass here.
    public void buy(final double currentPrice, final String symbol, int quantity, Activity activity, Context context){
        if(quantity < 1) {
            Toast.makeText(activity, "Quantity cannot be 0", Toast.LENGTH_SHORT).show();
            return;
        }

        //TODO: Take this code separate in a new class.
        //TODO: Call the below code after the button is pressed and before coming in this function to reduce number of arguments.
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if(!isConnected) {
            Toast.makeText(activity, "No Internet Connection! Unable to Buy.", Toast.LENGTH_SHORT).show();
            return;
        }

        //TODO: Fetch /stocksOwn/symbol from firebase and then change that class accordingly.

        double funds = MainActivity.currentUser.getFunds();
        funds = funds - (currentPrice*quantity);
        if(funds < 0){
            Toast.makeText(activity, "Not enough funds to buy.", Toast.LENGTH_SHORT).show();
            return;
        }
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("users").child(MainActivity.currentUser.getUid());
        currentOwned = new StocksOwn(currentPrice, "", quantity, symbol);

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("stocksOwn/"+symbol)){
//                    mDatabaseReference = mDatabaseReference.child("stockOwn").child(symbol);
                    currentOwned = (StocksOwn) snapshot.child("stockOwn").child(symbol).getValue();
                    Log.e("Transaction: ", currentOwned.getSymbol());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //TODO: Change below lines to fetch data from firebase.
        currentOwned = new StocksOwn(currentPrice, "", quantity, symbol);

        //TODO: Take this code out in a new class.

        mDatabaseReference = mFirebaseDatabase.getReference().child("users").child(MainActivity.currentUser.getUid()).child("stocksOwn").child(symbol);
        mDatabaseReference.setValue(currentOwned);

        MainActivity.currentUser.setFunds(funds);
        mDatabaseReference = mFirebaseDatabase.getReference().child("users").child(MainActivity.currentUser.getUid());
        mDatabaseReference.child("funds").setValue(funds);

        Toast.makeText(activity, "Bought Successfully", Toast.LENGTH_SHORT).show();
    }


    //TODO: Fetch latest data from firebase for the current stock and pass here.
    public void sell(double currentPrice, String symbol, int quantity, Activity activity, Context context){
        if(quantity < 1) {
            Toast.makeText(activity, "Quantity cannot be 0", Toast.LENGTH_SHORT).show();
            return;
        }

        //TODO: Take this code separate in a new class.
        //TODO: Call the below code after the button is pressed and before coming in this function to reduce number of arguments.
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if(!isConnected) {
            Toast.makeText(activity, "No Internet Connection! Unable to Sell.", Toast.LENGTH_SHORT).show();
            return;
        }

        //TODO: Fetch /stocksOwn/symbol from firebase and then change that class accordingly.
        //TODO: check if the user have enough quantity of current stock.

        double funds = MainActivity.currentUser.getFunds();
        funds = funds + (currentPrice*quantity);

        //Change below lines to fetch data from firebase.
        StocksOwn currentOwned = new StocksOwn(currentPrice, "edit", quantity, symbol);

        //TODO: Take this code out in a new class.
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("users").child(MainActivity.currentUser.getUid()).child("stocksOwn").child(symbol);
        mDatabaseReference.setValue(currentOwned);

        MainActivity.currentUser.setFunds(funds);
        mDatabaseReference = mFirebaseDatabase.getReference().child("users").child(MainActivity.currentUser.getUid());
        mDatabaseReference.child("funds").setValue(funds);

        Toast.makeText(activity, "Sold Successfully", Toast.LENGTH_SHORT).show();
    }
}