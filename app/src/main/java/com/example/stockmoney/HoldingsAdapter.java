package com.example.stockmoney;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.stockmoney.data.StockFirebaseColumns;
import com.example.stockmoney.data.StocksOwn;

import java.util.ArrayList;

public class HoldingsAdapter extends ArrayAdapter<StocksOwn> {

    private final String LOG_TAG = HoldingsAdapter.class.getSimpleName();

    public HoldingsAdapter(Activity context, ArrayList<StocksOwn> list){
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View LISTholdings = convertView;
        if(LISTholdings == null){
            LISTholdings = LayoutInflater.from(getContext()).inflate(
                    R.layout.listitem_holdings, parent, false
            );
        }

        TextView TVavgPrice = LISTholdings.findViewById(R.id.TVavgPrice);
        TextView TVsymbol_holdings = LISTholdings.findViewById(R.id.TVsymbol_holdings);
        TextView TVprofit =  LISTholdings.findViewById(R.id.TVprofit);
        TextView TVquantity_holdings = LISTholdings.findViewById(R.id.TVquantity_holdings);

        StocksOwn currentListItem = getItem(position);

        if(currentListItem == null){
            Log.e(LOG_TAG, "currentListItem is null");
            return LISTholdings;
        }

        TVavgPrice.setText(Double.toString(currentListItem.getAvgPrice()));
        TVsymbol_holdings.setText(currentListItem.getSymbol());
        TVquantity_holdings.setText("Quantity: " + Double.toString(currentListItem.getQuantity()));

        double profit = currentListItem.getProfit();
        if(profit < 0){
            TVprofit.setTextColor(Color.RED);
        }
        else{
            TVprofit.setTextColor(Color.GREEN);
        }
        TVprofit.setText(Double.toString(profit));

        return LISTholdings;
    }

}
