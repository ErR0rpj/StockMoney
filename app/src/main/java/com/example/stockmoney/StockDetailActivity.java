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

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference, mDatabaseReference2;
    private StocksOwn currentOwned;

    private TextView TVsymbol, TVprice, TVhigh, TVlow, TVchg, TVchg_percent,TVtotal_amount;
    private Button BTNbuy, BTNsell;
    private EditText ETquantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail);

        int position = getIntent().getIntExtra("position", -1);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("users").child(currentUser.getUid());

        TVsymbol = findViewById(R.id.symbol);
        TVprice = findViewById(R.id.price);
        TVhigh = findViewById(R.id.high);
        TVlow = findViewById(R.id.low);
        TVchg = findViewById(R.id.chg);
        TVchg_percent = findViewById(R.id.chg_percent);
        BTNbuy = findViewById(R.id.BTNbuy);
        BTNsell = findViewById(R.id.BTNsell);
        ETquantity = findViewById(R.id.ETquantity);
        TVtotal_amount = findViewById(R.id.totalamount);

        if(position == -1){
            Log.e(LOG_TAG, "position is -1, check immediately");
            return;
        }

        final StockFirebaseColumns currentListItem = stockmodelListfilterd.get(position);

        mDatabaseReference2 = mFirebaseDatabase.getReference().child("users").child(currentUser.getUid()).child("stocksOwn");

        TVsymbol.setText(currentListItem.getSymbol());
        TVprice.setText(Double.toString(currentListItem.getPrice()));
        TVhigh.setText(Double.toString(currentListItem.getHigh()));
        TVlow.setText(Double.toString(currentListItem.getLow()));
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

                if(currentOwnednow.getSymbol().equals(currentListItem.getSymbol())){
                    currentOwned = currentOwnednow;
                }
                else{
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
                else{
                    Log.e(LOG_TAG, "currentOwned is null");
                    currentOwned = new StocksOwn(currentListItem.getPrice(), "", 0, currentListItem.getSymbol());
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