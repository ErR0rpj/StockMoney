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

import java.util.ArrayList;


//Adapter for home page list.
public class HomeAdapterClass extends ArrayAdapter<StockFirebaseColumns> {

    private final String LOG_TAG = HomeAdapterClass.class.getSimpleName();

    public HomeAdapterClass(Activity context, ArrayList<StockFirebaseColumns> list){
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View LISThome = convertView;
        if(LISThome == null){
            LISThome = LayoutInflater.from(getContext()).inflate(
                    R.layout.listitem_home, parent, false
            );
        }

        TextView TVprice = LISThome.findViewById(R.id.TVprice);
        TextView TVname = LISThome.findViewById(R.id.TVname);
        TextView TVchg =  LISThome.findViewById(R.id.TVchg);

        StockFirebaseColumns currentListItem = getItem(position);

        if(currentListItem == null){
            Log.e(LOG_TAG, "currentListItem is null");
            return LISThome;
        }

        TVprice.setText(Double.toString(currentListItem.getPrice()));
        TVname.setText(currentListItem.getName());

        String chg = currentListItem.getChg();
        if(chg.charAt(0)=='-'){
            TVchg.setTextColor(Color.RED);
        }
        else{
            TVchg.setTextColor(Color.GREEN);
        }
        TVchg.setText(currentListItem.getChg());

        return LISThome;
    }
}
