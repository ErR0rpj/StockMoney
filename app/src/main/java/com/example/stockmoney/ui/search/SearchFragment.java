package com.example.stockmoney.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stockmoney.R;
import com.example.stockmoney.StockDetailActivity;
import com.example.stockmoney.mystockadapter;
import com.example.stockmoney.stockmodel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    EditText editsearchs;
    ListView listViewstocks;

    public static List<stockmodel> stockmodelList = new ArrayList<>();
    stockmodel stockmodel;
    mystockadapter mystockadapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        searchViewModel =
//                ViewModelProviders.of(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        final TextView textView = root.findViewById(R.id.text_search);
//        searchViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//
//
//
//
//            }
//        });
       // return root;


        editsearchs =  root.findViewById(R.id.editsearchs);
        listViewstocks = root.findViewById(R.id.listviewstocks);

        fetchData();

        listViewstocks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startActivity(new Intent(getContext(), StockDetailActivity.class).putExtra("position",position));
            }
        });

        editsearchs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mystockadapter.getFilter().filter(s);
                mystockadapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return root;
    }
         @Override
         public  boolean onOptionsItemSelected(@NonNull MenuItem item)
         {
             if(item.getItemId()==android.R.id.home) {
                 try {
                     finalize();
                 } catch (Throwable throwable) {
                     throwable.printStackTrace();
                 }
             }

             return super.onOptionsItemSelected(item);
         }



    private void fetchData() {

        String url = "https://fcsapi.com/api-v2/stock/latest?id=1,2,3,4,5&access_key=aOccqp276qs0Ue5a0CVjMG91IMTghjKMGr8gnr9iI2LwG5";

        final StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String price = jsonObject.getString("price");
                        String high = jsonObject.getString("high");
                        String low = jsonObject.getString("low");
                        String chg = jsonObject.getString("chg");
                        String chg_percent = jsonObject.getString("chg_percent");
                        String dateTime = jsonObject.getString("dateTime");
                        String symbol = jsonObject.getString("symbol");
                        String id = jsonObject.getString("id");

                        JSONObject object = jsonObject.getJSONObject("symbol");
                    //    String symbol = object.getString("symbol");


                        stockmodel = new stockmodel(price,high,low,chg,chg_percent,dateTime,symbol,id);
                        stockmodelList.add(stockmodel);

                    }

                    mystockadapter = new mystockadapter(getActivity(), stockmodelList);
                    listViewstocks.setAdapter(mystockadapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Try changing getActivity() to getContext() if error found.
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);

        


    }

}
