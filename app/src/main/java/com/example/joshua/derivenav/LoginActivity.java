package com.example.joshua.derivenav;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private String userID;
    private String userName;
    private String userEmail;
    private String userPhoto;
    private DatabaseReference mRef;

    private static final int RC_SIGN_IN = 123;
    @BindView(R.id.root)
    View mRootView;

    public FirebaseDatabase getDatabase() {

        //  Initialize SharedPreferences
        SharedPreferences getPrefs = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());

        //get database instance
        if (mFirebaseDatabase == null) {
            mFirebaseDatabase = FirebaseDatabase.getInstance();
        }
        //  Create a new boolean and preference and set it to true
        boolean isFirstStart = getPrefs.getBoolean("dataPersistenceOn", true);

        //  If the activity has never started before...
        if (isFirstStart) {

            //  Launch app intro

            mFirebaseDatabase.setPersistenceEnabled(true);

            //  Make a new preferences editor
            SharedPreferences.Editor e = getPrefs.edit();
            //  Edit preference to make it false because we don't want this to run again
            e.putBoolean("dataPersistenceOn", false);

            //  Apply changes
            e.apply();
        }
        return mFirebaseDatabase;
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            handleSignInResponse(resultCode, data);
            return;
        }
    }

    private void handleSignInResponse(int resultCode, Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);

        if (resultCode == RESULT_OK) {


            //add user data to db
            userID = mAuth.getCurrentUser().getUid();
            userName = mAuth.getCurrentUser().getDisplayName();
            userEmail = mAuth.getCurrentUser().getEmail();
            //gets users photo URL only for Google Accounts
            try {
                userPhoto = mAuth.getCurrentUser().getPhotoUrl().toString();
            } catch (Exception e) {
                userPhoto = "";
                e.printStackTrace();
            }

            mFirebaseDatabase = getDatabase();

            DatabaseReference userRef = mFirebaseDatabase.getReference().child("Users").child(userID);

            //adds new user to User db
            Map newPost = new HashMap();
            newPost.put("name", userName);
            newPost.put("email", userEmail);
            newPost.put("photoURL", userPhoto);
            userRef.setValue(newPost);

            startActivity(new Intent(this, MainActivity.class)
                    .putExtra("my_token", response.getIdpToken()));
            finish();

        } else {
            if (response == null) {
                finish();
                //showSnackbar("auth not working");
                return;
            }
            if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                showSnackbar("no internet connection");
                return;

            }
            showSnackbar("unknown error");

        }

    }

    private void showSnackbar(String errorMessageRes) {
        Snackbar.make(mRootView, errorMessageRes, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // User is signed in
                    //Toast.makeText(LoginActivity.this, "you're signed in", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "you're not signed in", Toast.LENGTH_SHORT).show();
                }
            }
        };

        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build(),
                        new AuthUI.IdpConfig.GoogleBuilder().build()
                ))
                .setTheme(R.style.AppTheme_NoActionBar)
                .setLogo(R.drawable.welcome)
                .build(), RC_SIGN_IN);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
