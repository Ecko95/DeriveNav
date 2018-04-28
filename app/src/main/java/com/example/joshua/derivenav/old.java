package com.example.joshua.derivenav;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joshua.derivenav.com.joshua.api.model.Trip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class old extends Fragment {

    private static final String TAG = "";
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mRef;
    private String userID;

    private List<Trip> trips;

    @BindView(R.id.rv_trips)
    RecyclerView rv_trips;
//    @BindView(R.id.txt_debug_name)
//    TextView txt_debug_name;
//    @BindView(R.id.txt_debug_desc)
//    TextView txt_debug_desc;
//    @BindView(R.id.txt_debug_key)
//    TextView txt_debug_key;

    public void initViews() {

//        SimpleRecyclerAdapter<Trip, TripBinder> adapter =
//                new SimpleRecyclerAdapter<>(new TripBinder());
//
//        rv_trips.setAdapter(adapter);
//        adapter.setData(trips);

        trips = new ArrayList<>();
        trips.add(new Trip("Joshua","lorem ipsum", "001"));
        trips.add(new Trip("Pepe","lorem ipsum", "002"));

//        TripAdapter tripAdapter = new TripAdapter();
//        rv_trips.setAdapter(tripAdapter);
//
//
//        tripAdapter.addData(trips);

    }
    private List<Trip> populateData(){
        trips = new ArrayList<>();
        trips.add(new Trip("Joshua","lorem ipsum", "001"));
        trips.add(new Trip("Pepe","lorem ipsum", "002"));
        Log.d(TAG,trips.toString());
        return trips;
    }

    public old() {
        // Required empty public constructor
    }

    public static old newInstance() {
        old fragment = new old();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.old, container, false);
        ButterKnife.bind(this, view);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("Trips");
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

//        mRef = FirebaseDatabase
//                .getInstance()
//                .getReference("Trips")
//                .child(userID);


//        mRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                showData(dataSnapshot);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                // ...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        };
        mRef.addChildEventListener(childEventListener);


        return view;
    }



    private void showData(DataSnapshot dataSnapshot) {

//        txt_debug_name.setText(dataSnapshot.child(mRef.getKey()).toString());
//        txt_debug_desc.setText(dataSnapshot.child(mRef.getKey()).toString());
//        txt_debug_key.setText(dataSnapshot.child(mRef.getKey()).toString());



//        Toast.makeText(getActivity(), dataSnapshot.toString(), Toast.LENGTH_LONG).show();
//        for(DataSnapshot ds : dataSnapshot.getChildren()){
//            String name = null;
//            String desc = null;
//            String key = mRef.push().getKey();
//
//            Trip tripInfo = new Trip(name,desc,key);
//
//
////            tripInfo.setName(ds.child(userID).getValue(Trip.class).getName()); //sets User name
////            tripInfo.setDesc(ds.child(userID).getValue(Trip.class).getDesc()); //sets User name
//
//            Log.d(TAG,"Data : name " + tripInfo.getName());
//            Log.d(TAG,"Data : desc " + tripInfo.getDesc());
//
//            ArrayList<String> arrayList = new ArrayList<>();
//            //adds data to arrayList Adapter
//            arrayList.add(tripInfo.getName());
//            arrayList.add(tripInfo.getDesc());
//            ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,arrayList);
//            mListView.setAdapter(adapter);
//
//        }
    }

    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Trip trip = dataSnapshot.getValue(Trip.class);

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

}
