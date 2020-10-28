package com.example.stockmoney.ui.search;

import android.content.Intent;
import android.icu.text.StringSearch;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stockmoney.MainActivity;
import com.example.stockmoney.R;
import com.example.stockmoney.StockDetailActivity;
import com.example.stockmoney.mystockadapter;
import com.example.stockmoney.stockmodel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class SearchFragment extends Fragment {

    EditText editsearchs;
    ListView listViewstocks;


    public static List<stockmodel> stockmodelList = new ArrayList<>();
    stockmodel stockmodel;
    mystockadapter mystockadapter;

    private SearchViewModel searchViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search, container, false);

       // return root;


        editsearchs =  root.findViewById(R.id.editsearchs);
        listViewstocks = root.findViewById(R.id.listviewstocks);

        fetchData();

        listViewstocks.setAdapter(mystockadapter);


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
                    JSONObject jsonObject1 = new JSONObject(response);

                     //jsonArray -- jsonObject

                    JSONArray jsonArray = jsonObject1.getJSONArray("response");

                    Log.e("SearchFragment: json: ", jsonArray.toString());

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


                        stockmodel = new stockmodel(price,high,low,chg,chg_percent,dateTime,symbol,id);
                        stockmodelList.add(stockmodel);
                        Log.e("SearchFragment: price ", price);

                    }

                    mystockadapter = new mystockadapter(getActivity(), stockmodelList);
                    listViewstocks.setAdapter(mystockadapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("StockFragment: json", "response nhi mila");
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("StockFragment: respo", "response nhi mila");
            }
        });

        Log.e("SearchFragment: last: ", request.toString());

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);


    }

}
