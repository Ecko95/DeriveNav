package com.example.joshua.derivenav;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import net.steamcrafted.materialiconlib.MaterialMenuInflater;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.iv_profilePic) ImageView iv_profilePic;
    @BindView(R.id.user_info_lv) ListView mListView;
    @BindView(R.id.profile_toolbar)
    android.support.v7.widget.Toolbar toolbar;

    private Menu menu;

    private static final String TAG = "Database Message: ";
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mRef;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        //initialise Firebase DB
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("User Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (user != null) {

            try {
                for (UserInfo profile : user.getProviderData()) {
                    // Id of the provider (ex: google.com)
                    String providerId = profile.getProviderId();

                    // UID specific to the provider
                    String uid = profile.getUid();

                    // Name, email address, and profile photo Url
                    String name = profile.getDisplayName();
                    String email = profile.getEmail();
                    Uri photoUrl = null;
                    photoUrl = profile.getPhotoUrl();

                    ArrayList<String> arrayList = new ArrayList<>();
                    //adds data to arrayList Adapter
                    arrayList.add(name);
                    arrayList.add(email);
                    ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
                    mListView.setAdapter(adapter);
                    Picasso.with(this).load(photoUrl).into(iv_profilePic);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        switch (item.getItemId()) {
            case android.R.id.home:
                //Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
                onBackPressed();
                return true;

            case R.id.action_logout:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {

                                // user is now signed out
                                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                                finish();
                            }
                        });
                return true;
            case R.id.action_about:
                intent.setData(Uri.parse("https://github.com/Ecko95/DeriveNav"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (intent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
                    getApplicationContext().startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "sorry link not found", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.action_api:
                intent.setData(Uri.parse("https://sandbox.amadeus.com/api-catalog"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (intent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
                    getApplicationContext().startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "sorry link not found", Toast.LENGTH_LONG).show();
                }
                return true;

            default:
                //Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
                return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MaterialMenuInflater
                .with(this) // Provide the activity context
                // Set the fall-back color for all the icons. Colors set inside the XML will always have higher priority
                .setDefaultColor(Color.WHITE)
                // Inflate the menu
                .inflate(R.menu.menu, menu);

        this.menu = menu;

        hideMenu();
        return true;
    }

    public void hideMenu() {
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_help).setVisible(false);
    }

}
