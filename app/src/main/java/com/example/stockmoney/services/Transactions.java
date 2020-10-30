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

import static com.example.stockmoney.data.DataItems.currentUser;

public class Transactions {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private StocksOwn currentOwned;
    private double avgPrice;

    //TODO: Fetch latest data from firebase for the current stock and pass here.
    public void buy(final double currentPrice, final String symbol, int quantity, StocksOwn currentOwned, Activity activity, Context context){
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

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("users").child(currentUser.getUid());

        double funds = currentUser.getFunds();
        funds = funds - (currentPrice * quantity);
        if(funds < 0){
            Toast.makeText(activity, "Not enough funds to buy.", Toast.LENGTH_SHORT).show();
            return;
        }
        double totalPrice;

        if(currentOwned == null){
            currentOwned = new StocksOwn(0, "", 0, symbol);
        }

        totalPrice = (currentOwned.getAvgPrice() * currentOwned.getQuantity()) + (currentPrice * quantity);
        quantity = quantity + currentOwned.getQuantity();
        avgPrice = totalPrice/quantity;

        currentOwned.setAvgPrice(avgPrice);
        currentOwned.setQuantity(quantity);

        mDatabaseReference.child("stocksOwn").child(symbol).setValue(currentOwned);

        currentUser.setFunds(funds);
        mDatabaseReference.child("funds").setValue(funds);

        Toast.makeText(activity, "Bought Successfully", Toast.LENGTH_SHORT).show();
    }


    //TODO: Fetch latest data from firebase for the current stock and pass here.
    public void sell(final double currentPrice, final String symbol, int quantity, StocksOwn currentOwned, Activity activity, Context context){
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

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("users").child(currentUser.getUid());

        if(currentOwned == null){
            currentOwned = new StocksOwn(0, "", 0, symbol);
        }

        if(currentOwned.getQuantity() < quantity){
            Toast.makeText(activity, "You do not have enough quantity to sell this stock", Toast.LENGTH_SHORT).show();
            return;
        }

        double funds = currentUser.getFunds();
        funds = funds + (currentPrice*quantity);

        double totalPrice = (currentOwned.getAvgPrice() * currentOwned.getQuantity()) - (currentPrice * quantity);
        quantity = currentOwned.getQuantity() - quantity;
        if(quantity == 0) {
            avgPrice = 0;
        }
        else {
            avgPrice = totalPrice / quantity;
        }

        currentOwned.setAvgPrice(avgPrice);
        currentOwned.setQuantity(quantity);

        mDatabaseReference.child("stocksOwn").child(symbol).setValue(currentOwned);

        currentUser.setFunds(funds);
        mDatabaseReference.child("funds").setValue(funds);

        Toast.makeText(activity, "Sold Successfully", Toast.LENGTH_SHORT).show();
    }
}