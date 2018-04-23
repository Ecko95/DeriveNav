package com.example.joshua.derivenav;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 123;
    @BindView(R.id.root)
    View mRootView;

    public static Intent createIntent(Context context){
        return new Intent(context, LoginActivity.class);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            handleSignInResponse(resultCode,data);
            return;
        }
    }

    private void handleSignInResponse(int resultCode, Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);

        if (resultCode == RESULT_OK){
            startActivity(new Intent(this, MainActivity.class)
                    .putExtra("my_token", response.getIdpToken()));
            finish();

        }else{
            if (response == null){
                finish();
                
                //showSnackbar("auth not working");
                return;
            }
            if(response.getError().getErrorCode() == ErrorCodes.NO_NETWORK){
                showSnackbar("no internet connection");
                return;

            }
            showSnackbar("unknown error");

        }

    }

    private void startSignedInActivity(IdpResponse response) {
    }


    private void showSnackbar(String errorMessageRes){
        Snackbar.make(mRootView, errorMessageRes, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null){
            Toast.makeText(this, "You're already signed in", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "You're not signed in, try again", Toast.LENGTH_SHORT).show();
        }

        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build(),
                        new AuthUI.IdpConfig.GoogleBuilder().build()
                ))
                .build(),RC_SIGN_IN);



    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
