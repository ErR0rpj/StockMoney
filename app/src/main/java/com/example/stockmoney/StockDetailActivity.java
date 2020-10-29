package com.example.stockmoney;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.stockmoney.data.StockFirebaseColumns;

import static com.example.stockmoney.data.DataItems.stockmodelListfilterd;

public class StockDetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = StockDetailActivity.class.getSimpleName();

    private TextView TVsymbol, TVprice, TVhigh, TVlow, TVchg, TVchg_percent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail);

        int position = getIntent().getIntExtra("position", -1);

        TVsymbol = findViewById(R.id.symbol);
        TVprice = findViewById(R.id.price);
        TVhigh = findViewById(R.id.high);
        TVlow = findViewById(R.id.low);
        TVchg = findViewById(R.id.chg);
        TVchg_percent = findViewById(R.id.chg_percent);

        if(position == -1){
            return;
        }

        StockFirebaseColumns currentListItem = stockmodelListfilterd.get(position);

        TVsymbol.setText(currentListItem.getSymbol());
        TVprice.setText(Double.toString(currentListItem.getPrice()));
        TVhigh.setText(Double.toString(currentListItem.getHigh()));
        TVlow.setText(Double.toString(currentListItem.getLow()));
        TVchg_percent.setText(currentListItem.getChg_percent());
        TVchg.setText(currentListItem.getChg());

    }
}