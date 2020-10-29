package com.example.stockmoney.ui.search;

import android.content.Intent;
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
import com.example.stockmoney.data.StockFirebaseColumns;
import com.example.stockmoney.mystockadapter;
import static com.example.stockmoney.data.DataItems.stockmodelList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SearchFragment extends Fragment {

    EditText editsearchs;
    ListView listViewstocks;

//    public static List<StockFirebaseColumns> stockmodelList = new ArrayList<>();
    mystockadapter mystockadapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search, container, false);

        editsearchs =  root.findViewById(R.id.editsearchs);
        listViewstocks = root.findViewById(R.id.listviewstocks);

        if(stockmodelList == null || stockmodelList.size() == 0) {
            fetchData();
        }

        mystockadapter = new mystockadapter(getActivity(), stockmodelList);
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

       
        
        String url = "https://fcsapi.com/api-v2/stock/latest?id=63541,63542,63543,63544,63545,63546,63547,63548,63549,63551,63552,63553,63554,63555,63556,63557,63558,63559,63560,63561,63562,63563,63564,63565,63566,63567,63568,63569,63570,63571,63576,63579,63580,63581,63582,63583,63584,63585,63589,63590,63593,63594,63595,63601,63602,63603,63604,63605,63608,63609,63610,,63613,63614,63615,63616,63617,63618,63619,63620,63621,63622,63623,63624,63625,63626,63627,63628,63629,63631,63632,63634,63636,63637,63638,63639,63640,63641,63642,63645,63647,63648,63650,63653,63654,63655,63656,63657,63660,63662,63664,63665,63667,63670,63671,63724,63725,63728,63729,63731,63732,63734,63735,63736,63745,63755,63760,63761,63764,63767,63770,63778,63785,63786,63787,63788,63791,63794,63795,63796,63797,63802,63803,63804,63809,63812,63816,63818,63821,63945,64170,64173,64174,64176,64178,64181,64182,,64184,64187,64190,64198,64204,64209,64221,64223,64235,64643&access_key=aOccqp276qs0Ue5a0CVjMG91IMTghjKMGr8gnr9iI2LwG5";


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

                        Double price = Double.parseDouble(jsonObject.getString("price"));
                        Double high = Double.parseDouble(jsonObject.getString("high"));
                        Double low = Double.parseDouble(jsonObject.getString("low"));
                        String chg = jsonObject.getString("chg");
                        String chg_percent = jsonObject.getString("chg_percent");
                        String dateTime = jsonObject.getString("dateTime");
                        String symbol = jsonObject.getString("symbol");
                        int id = Integer.parseInt(jsonObject.getString("id"));


                        StockFirebaseColumns stockmodel = new StockFirebaseColumns(chg,chg_percent,high,id,low,symbol,price,symbol);
                        stockmodelList.add(stockmodel);
                        Log.e("SearchFragment: price ", price.toString());

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
