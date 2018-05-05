package com.example.joshua.derivenav.UserTripDetails;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;

import com.example.joshua.derivenav.NewTrip.Models.TripModel;
import com.example.joshua.derivenav.R;
import com.example.joshua.derivenav.UserTrips.Models.UserTrips;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.widget.Toast;
import android.os.Handler;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_trip_details);

        ButterKnife.bind(this);
        initToolbar("Takeoff Android");
        setAdapter();
        loadFirebaseData();


    }

    public void initToolbar(String title) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);
    }


    private void setAdapter() {


//        modelList.add(new UserTripDetailsModel("Android", "Hello " + " Android"));
//        modelList.add(new UserTripDetailsModel("Beta", "Hello " + " Beta"));
//        modelList.add(new UserTripDetailsModel("Cupcake", "Hello " + " Cupcake"));
//        modelList.add(new UserTripDetailsModel("Donut", "Hello " + " Donut"));


        mAdapter = new UserTripDetailsRecyclerViewAdapter(UserTripDetails.this, modelList, "Header");


        recyclerView.setHasFixedSize(true);

        // use a linear layout manager

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener(new UserTripDetailsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, UserTripDetailsModel model) {

                //handle item click events here
                Toast.makeText(UserTripDetails.this, "Hey " + model.getTitle(), Toast.LENGTH_SHORT).show();


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

        mRef.child("Trips").child(userID).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                recyclerView.setAdapter(mAdapter);
                //clears list for refresh
                modelList.clear();

                final Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()) {

                    DataSnapshot item = items.next();
                    TripModel userInfo = item.getValue(TripModel.class);

                    modelList.add(new UserTripDetailsModel(userInfo.getName(), userInfo.getDesc(), userInfo.getPushID()));


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


    }

    public void removeItem(UserTrips trip) {
        ArrayList<UserTrips> modelList2 = new ArrayList<>();

        mRef.child("Trips").child(userID).child(trip.getKey()).removeValue();

        mAdapter.updateList(modelList);
        Toast.makeText(getApplicationContext(), "deleted" + trip.getTitle(), Toast.LENGTH_SHORT).show();

    }

}

