package com.example.joshua.derivenav.UserTripDetails;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;

import com.example.joshua.derivenav.LoginActivity;
import com.example.joshua.derivenav.MainActivity;
import com.example.joshua.derivenav.NewTrip.Models.TripModel;
import com.example.joshua.derivenav.R;
import com.example.joshua.derivenav.UserTripDetails.Models.UserTripDetailsModel;
import com.example.joshua.derivenav.UserTrips.Models.UserTrips;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialMenuInflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class UserTripDetails extends AppCompatActivity {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private UserTripDetailsRecyclerViewAdapter mAdapter;
    private ArrayList<UserTripDetailsModel> modelList = new ArrayList<>();
    private Dialog mDialog;
    private FirebaseAuth mAuth;
    private static FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private String userID;
    private static final String TAG = "UserTripDetails";


    String city_title;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_trip_details);

        ButterKnife.bind(this);
        initToolbar("Trip Details");


        getDetails();


//        Toast.makeText(this, city_title + key, Toast.LENGTH_SHORT).show();

//        modelList.add(new UserTripDetailsModel("Android", "Hello " + " Android"));
//        modelList.add(new UserTripDetailsModel("Beta", "Hello " + " Beta"));
//        modelList.add(new UserTripDetailsModel("Cupcake", "Hello " + " Cupcake"));
//        modelList.add(new UserTripDetailsModel("Donut", "Hello " + " Donut"));

    }

    public void initToolbar(String title) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);
    }


    private void setAdapter() {


        recyclerView.setHasFixedSize(true);

        // use a linear layout manager

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener(new UserTripDetailsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, UserTripDetailsModel model) {

                //handle item click events here
                Toast.makeText(UserTripDetails.this, "Hey " + model.getCityName(), Toast.LENGTH_SHORT).show();


            }
        });


        mAdapter.SetOnHeaderClickListener(new UserTripDetailsRecyclerViewAdapter.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(View view, String headerTitle) {

                //handle item click events here
                Toast.makeText(UserTripDetails.this, "Hey I am a header", Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void loadFirebaseData() {

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        final FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        Query query = FirebaseDatabase.getInstance().getReference("Destinations")
                .orderByChild("key").equalTo(key);
        Query query2 = FirebaseDatabase.getInstance().getReference("Trips").child(userID)
                .orderByChild("pushID").equalTo(key);


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {

                    UserTripDetailsModel userInfo = item.getValue(UserTripDetailsModel.class);
                    if (userInfo != null) {
                        modelList.add(new UserTripDetailsModel(userInfo.getCityName(), userInfo.getKey(), userInfo.getUserID()));
                    }
                }
                mAdapter = new UserTripDetailsRecyclerViewAdapter(UserTripDetails.this, modelList, city_title);
                setAdapter();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    TripModel tripModel = item.getValue(TripModel.class);
                    if (tripModel != null) {
                        //add to list

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void getDetails() {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents ");
        if (getIntent().hasExtra("title") && getIntent().hasExtra("key")) {

            city_title = getIntent().getStringExtra("title");
            key = getIntent().getStringExtra("key");


            loadFirebaseData();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MaterialMenuInflater
                .with(this) // Provide the activity context
                // Set the fall-back color for all the icons. Colors set inside the XML will always have higher priority
                .setDefaultColor(Color.WHITE)
                // Inflate the menu
                .inflate(R.menu.detail_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.action_delete:
                deleteTrip();
                return true;

            default:
                Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
                return false;
        }
    }

    public void deleteTrip() {

        Drawable materialDeleteIcon = MaterialDrawableBuilder.with(getApplicationContext())
                .setIcon(MaterialDrawableBuilder.IconValue.DELETE)
                .setColor(Color.RED)
                .setToActionbarSize()
                .build();

        new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Do you really want to delete this Trip?")
                .setIcon(materialDeleteIcon)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        mRef.child("Trips").child(userID).child(key).removeValue();
                        mAdapter.updateList(modelList);
                        onBackPressed();
                        Toast.makeText(UserTripDetails.this, "deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }
}

