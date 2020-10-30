package com.example.stockmoney.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.stockmoney.MainActivity;
import com.example.stockmoney.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.stockmoney.ui.RankLeaderboard;

import static com.example.stockmoney.data.DataItems.currentUser;

public class ProfileFragment extends Fragment {

    TextView userName_field, funds_field, rank_field, ETuserName, ETemail, ETmobile;

    TextView btn_start;


    private final String LOG_TAG = ProfileFragment.class.getSimpleName();

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ETuserName = view.findViewById(R.id.ETuserName);
        ETmobile = view.findViewById(R.id.ETmobile);
        ETemail = view.findViewById(R.id.ETemail);
        userName_field = view.findViewById(R.id.username_field);
        funds_field = view.findViewById(R.id.funds_field);
        rank_field = view.findViewById(R.id.rank_field);
        ETuserName.setText(currentUser.getUsername());
        ETemail.setText(currentUser.getEmail());
        userName_field.setText(currentUser.getUsername());
        funds_field.setText(Double.toString(currentUser.getFunds()));
        rank_field = view.findViewById(R.id.rank_field);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("users").child(currentUser.getUid());

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUser.setFunds(snapshot.child("funds").getValue(Double.class));
                funds_field.setText(Double.toString(currentUser.getFunds()));
                currentUser.setRank(snapshot.child("rank").getValue(Integer.class));
                rank_field.setText(Integer.toString(currentUser.getRank()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        btn_start =  view.findViewById(R.id.rank);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(getActivity(),RankLeaderboard.class);
                startActivity(in);
            }
        });

        return view;
    }
}
