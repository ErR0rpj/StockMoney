package com.example.stockmoney.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.stockmoney.MainActivity;
import com.example.stockmoney.R;

public class ProfileFragment extends Fragment {

    EditText ETuserName;
    EditText ETmobile;
    EditText ETemail;
    TextView email_field, userName_field, funds_field, rank_field;

    private final String LOG_TAG = ProfileFragment.class.getSimpleName();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ETuserName = view.findViewById(R.id.ETuserName);
        ETmobile = view.findViewById(R.id.ETmobile);
        ETemail = view.findViewById(R.id.ETemail);
        email_field = view.findViewById(R.id.email_field);
        userName_field = view.findViewById(R.id.username_field);
        funds_field = view.findViewById(R.id.funds_field);
        rank_field = view.findViewById(R.id.rank_field);

        ETuserName.setText(MainActivity.currentUser.getUsername());
        ETemail.setText(MainActivity.currentUser.getEmail());
        email_field.setText(MainActivity.currentUser.getEmail());
        userName_field.setText(MainActivity.currentUser.getUsername());
//        funds_field.setText(MainActivity.funds);
//        rank_field.setText(MainActivity.rank);

        return view;
    }
}
