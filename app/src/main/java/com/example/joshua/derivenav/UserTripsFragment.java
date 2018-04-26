package com.example.joshua.derivenav;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joshua.derivenav.com.joshua.api.model.Trip;
import com.example.joshua.derivenav.com.joshua.api.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserTripsFragment extends Fragment {

    private static final String TAG = "";
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mRef;
    private String userID;

    @BindView(R.id.user_trips_lv)
    ListView mListView;
    @BindView(R.id.txt_debug)
    TextView txt_debug;

    public UserTripsFragment() {
        // Required empty public constructor
    }

    public static UserTripsFragment newInstance() {
        UserTripsFragment fragment = new UserTripsFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_trips, container, false);
        ButterKnife.bind(this, view);


        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("Trips");
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        mRef = FirebaseDatabase
                .getInstance()
                .getReference("Trips")
                .child(userID);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    private void showData(DataSnapshot dataSnapshot) {

        ArrayList<String> userTrips = new ArrayList<>();
        

        txt_debug.setText(dataSnapshot.child(mRef.getKey()).toString());
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

}
