package com.example.stockmoney;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class mystockadapter  extends ArrayAdapter<stockmodel> {

    private Context context;
    private List<stockmodel> stockmodelList;

    public mystockadapter(Context context,List<stockmodel> stockmodelList) {
        super(context, R.layout.list_custom_item,stockmodelList);


        this.context = context;
        this.stockmodelList = stockmodelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_item,null,true);

        TextView stocksymbol = view.findViewById(R.id.stocksymbol);
        stocksymbol.setText(stockmodelList.get(position).getSymbol());



        return view;
    }
}
