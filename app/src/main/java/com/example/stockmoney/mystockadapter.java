package com.example.stockmoney;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.stockmoney.data.StockFirebaseColumns;
import com.example.stockmoney.ui.search.SearchFragment;

import java.util.ArrayList;
import java.util.List;

public class mystockadapter  extends ArrayAdapter<StockFirebaseColumns> {

    private static final String LOG_TAG = mystockadapter.class.getSimpleName();

    private Context context;
    private List<StockFirebaseColumns> stockmodelList;
    private List<StockFirebaseColumns> stockmodelListfilterd;

    public mystockadapter(Activity context, List<StockFirebaseColumns> stockmodelList) {
        super(context, R.layout.list_custom_item,stockmodelList);

        this.context = context;
        this.stockmodelList = stockmodelList;
        this.stockmodelListfilterd = stockmodelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if(view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_home, parent, false);
        }

        TextView TVprice = view.findViewById(R.id.TVprice);
        TextView TVname = view.findViewById(R.id.TVname);
        TextView TVchg =  view.findViewById(R.id.TVchg);

        if(stockmodelListfilterd == null){
            Log.e(LOG_TAG, "currentListItem is null");
            return view;
        }

        StockFirebaseColumns currentListItem = stockmodelListfilterd.get(position);

        TVprice.setText(Double.toString(currentListItem.getPrice()));
        TVname.setText(stockmodelListfilterd.get(position).getSymbol());

        String chg = currentListItem.getChg();
        if(chg.charAt(0)=='-'){
            TVchg.setTextColor(Color.RED);
        }
        else{
            TVchg.setTextColor(Color.GREEN);
        }
        TVchg.setText(currentListItem.getChg());

        return view;
    }


    @Override
    public int getCount() {
        return stockmodelListfilterd.size();
    }

    @Nullable
    @Override
    public StockFirebaseColumns getItem(int position) {
        return stockmodelListfilterd.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint)
            {
                 FilterResults filterResults = new FilterResults();
                 if(constraint==null || constraint.length()==0)
                 {
                     filterResults.count = stockmodelList.size();
                     filterResults.values = stockmodelList;
                 }else
                 {
                     List<StockFirebaseColumns> resultsmodel = new ArrayList<>();
                     String searchStr = constraint.toString().toLowerCase();

                     for(StockFirebaseColumns itemsModel:stockmodelList)
                     {
                         if(itemsModel.getSymbol().toLowerCase().contains(searchStr)){
                             resultsmodel.add(itemsModel);
                         }
                         filterResults.count = resultsmodel.size();
                         filterResults.values = resultsmodel;
                     }
                 }
                 return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraints, FilterResults results)
            {
                stockmodelListfilterd = (List<StockFirebaseColumns>) results.values;
                com.example.stockmoney.data.DataItems.stockmodelList = (List<StockFirebaseColumns>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}

