package com.example.stockmoney.ui.watchlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.stockmoney.HomeAdapterClass;
import com.example.stockmoney.R;
import com.example.stockmoney.data.StockFirebaseColumns;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class WatchlistFragment extends Fragment {

    private static final String LOG_TAG = WatchlistFragment.class.getSimpleName();

    private ListView LISThome;
    private HomeAdapterClass adapter;

    //Variables for Firebase Database
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;
    public static ChildEventListener mChildEventListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_watchlist, container, false);

        LISThome = view.findViewById(R.id.LISThome);
        ArrayList<StockFirebaseColumns> list = new ArrayList<>();
        adapter = new HomeAdapterClass(getActivity(), list);
        LISThome.setAdapter(adapter);

        return view;
    }
}