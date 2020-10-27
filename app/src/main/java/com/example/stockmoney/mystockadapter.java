package com.example.stockmoney;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.stockmoney.ui.search.SearchFragment;

import java.util.ArrayList;
import java.util.List;

public class mystockadapter  extends ArrayAdapter<stockmodel> {

    private Context context;
    private List<stockmodel> stockmodelList;
    private List<stockmodel> stockmodelListfilterd;

    public mystockadapter(SearchFragment context, List<stockmodel> stockmodelList) {
        super(context, R.layout.list_custom_item,stockmodelList);


        this.context = context;
        this.stockmodelList = stockmodelList;
        this.stockmodelListfilterd = stockmodelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_item,null,true);

        TextView stocksymbol = view.findViewById(R.id.stocksymbol);
        stocksymbol.setText(stockmodelListfilterd.get(position).getSymbol());



        return view;
    }

    @Override
    public int getCount() {
        return stockmodelListfilterd.size();
    }

    @Nullable
    @Override
    public stockmodel getItem(int position) {
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
                     List<stockmodel> resultsmodel = new ArrayList<>();
                     String searchStr = constraint.toString().toLowerCase();

                     for(stockmodel itemsModel:stockmodelList)
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
                stockmodelListfilterd = (List<stockmodel>) results.values;
                SearchFragment.stockmodelList = (List<stockmodel>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}

