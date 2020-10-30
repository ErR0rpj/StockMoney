package com.example.stockmoney;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stockmoney.data.StockFirebaseColumns;
import com.example.stockmoney.data.StocksOwn;
import com.example.stockmoney.services.Transactions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.stockmoney.data.DataItems.currentUser;
import static com.example.stockmoney.data.DataItems.stockmodelListfilterd;

public class StockDetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = StockDetailActivity.class.getSimpleName();

    private StocksOwn currentOwned;

    private EditText ETquantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail);

        int position = getIntent().getIntExtra("position", -1);

        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseReference = mFirebaseDatabase.getReference().child("users").child(currentUser.getUid());

        TextView TVsymbol = findViewById(R.id.symbol);
        TextView TVprice = findViewById(R.id.price);
        TextView TVhigh = findViewById(R.id.high);
        TextView TVlow = findViewById(R.id.low);
        TextView TVchg = findViewById(R.id.chg);
        TextView TVchg_percent = findViewById(R.id.chg_percent);
        Button BTNbuy = findViewById(R.id.BTNbuy);
        Button BTNsell = findViewById(R.id.BTNsell);
        ETquantity = findViewById(R.id.ETquantity);
        TextView TVtotal_amount = findViewById(R.id.totalamount);

        if(position == -1){
            Log.e(LOG_TAG, "position is -1, check immediately");
            return;
        }

        final StockFirebaseColumns currentListItem = stockmodelListfilterd.get(position);

        DatabaseReference mDatabaseReference2 = mFirebaseDatabase.getReference().child("users").child(currentUser.getUid()).child("stocksOwn");

        TVsymbol.setText(currentListItem.getSymbol());
        TVprice.setText(String.format("₹ %.2f", currentListItem.getPrice()));
        TVhigh.setText(String.format("₹ %.2f", currentListItem.getHigh()));
        TVlow.setText(String.format("₹ %.2f", currentListItem.getLow()));
        TVchg_percent.setText(currentListItem.getChg_percent());
        TVchg.setText(currentListItem.getChg());

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUser.setFunds(snapshot.child("funds").getValue(Double.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabaseReference2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                StocksOwn currentOwnednow = snapshot.getValue(StocksOwn.class);

                if(currentOwnednow.getSymbol().equalsIgnoreCase(currentListItem.getSymbol())){
                    currentOwned = currentOwnednow;
                }
                else if(currentOwned == null || currentOwned.getQuantity() == 0){
                    Log.e(LOG_TAG, "currentOwned is null");
                    currentOwned = new StocksOwn(0, "", 0, currentListItem.getSymbol());
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                StocksOwn currentOwnednow = snapshot.getValue(StocksOwn.class);

                if(currentOwnednow.getSymbol().equals(currentListItem.getSymbol())){
                    currentOwned = currentOwnednow;
                }
                else if(currentOwned == null || currentOwned.getQuantity() == 0){
                    Log.e(LOG_TAG, "currentOwned is null");
                    currentOwned = new StocksOwn(0, "", 0, currentListItem.getSymbol());
                }
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

        BTNbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transactions transactions = new Transactions();

                if(ETquantity.getText() != null && ETquantity.getText().toString().length() > 0) {
                    int quantity = Integer.parseInt(ETquantity.getText().toString());
                    transactions.buy(currentListItem.getPrice(), currentListItem.getSymbol(), quantity, currentOwned, StockDetailActivity.this, getApplicationContext());
                }
                else{
                    Toast.makeText(StockDetailActivity.this, "Enter quantity!", Toast.LENGTH_SHORT).show();
                }}
        });

        BTNsell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transactions transactions = new Transactions();

                if(ETquantity.getText() != null && ETquantity.getText().toString().length() > 0) {
                    int quantity = Integer.parseInt(ETquantity.getText().toString());
                    transactions.sell(currentListItem.getPrice(), currentListItem.getSymbol(), quantity, currentOwned, StockDetailActivity.this, getApplicationContext());
                }
                else{
                    Toast.makeText(StockDetailActivity.this, "Enter quantity!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}