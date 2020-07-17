package com.example.stockmoney.ui.watchlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.stockmoney.R;

public class WatchlistFragment extends Fragment {

    private WatchlistViewModel watchlistViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        watchlistViewModel =
                ViewModelProviders.of(this).get(WatchlistViewModel.class);
        View root = inflater.inflate(R.layout.fragment_watchlist, container, false);
        final TextView textView = root.findViewById(R.id.text_watchlist);
        watchlistViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}