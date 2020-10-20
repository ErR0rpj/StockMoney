package com.example.stockmoney;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.stockmoney.data.UserDetails;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Arrays;

import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG =MainActivity.class.getSimpleName();

    //Variables for firebase authentication
    public static final int RC_SIGN_IN = 1;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    //Information of users which are used everywhere in the app
    public static String Username = "";
    public static int funds = 0;
    public static String email = "";
    public static String uid = "";
    public static int rank = 1;

    //variablesof firebase database
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Checking internet connection.
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        Log.e(LOG_TAG, "Internet Connection = " + isConnected);

        if(!isConnected) {
            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }
        //Checking internet connection....code finished.

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase =FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("users");
        Log.e("mainActivty: ", mDatabaseReference.toString());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations. ye maine nhi likha h @ojha
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_watchlist, R.id.navigation_portfolio, R.id.navigation_search, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        //FirebaseUi and authentication...code starting
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();

                if(user != null){
                    email = user.getEmail();
                    uid = user.getUid();
                    Log.e("MainActivity: ", uid);
                    onSignedInInitialize(user.getDisplayName());
                }
                else {
                    onSignedOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.GoogleBuilder().build(),
                                    new AuthUI.IdpConfig.EmailBuilder().build()
                                    ))
                            .build(),
                    RC_SIGN_IN);
                }
            }
        };
        //FirebaseUI and authentication...code end

    }

    //Setting variables after successful signIn.
    private void onSignedInInitialize(String username){
        Username = username;
    }

    //After logout cleaning up the variables
    private void onSignedOutCleanup(){
        Username = "";
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    //TODO: Initializing all the variables of the user and storing it in firebase
    //userdetails class to be made and testing.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN) {
            if(resultCode == RESULT_OK) {

                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.hasChild(uid)) {
                            Log.e("MainActivity/ ","Uid already in database");
                        }
                        else {
                            funds = 15000;
                            UserDetails userDetails = new UserDetails(email, funds, Username);
                            mDatabaseReference = mFirebaseDatabase.getReference().child("users").child(uid);
                            mDatabaseReference.setValue(userDetails);
                            Log.e("MainActivity/ ", "User added in database.");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Toast.makeText(MainActivity.this, "Signed In Successfully", Toast.LENGTH_SHORT).show();

            }

            else if(resultCode == RESULT_CANCELED) {
                Toast.makeText(MainActivity.this, "Signed In Cancelled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    //This is for refresh button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //This is for refresh button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_refresh){
            Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
