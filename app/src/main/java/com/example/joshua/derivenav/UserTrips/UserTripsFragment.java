package com.example.joshua.derivenav.UserTrips;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.GridLayoutManager;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;

import android.support.v4.widget.SwipeRefreshLayout;

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
import com.squareup.picasso.Picasso;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.os.Handler;


import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UserTripsFragment extends Fragment {



    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_recycler_list) SwipeRefreshLayout swipeRefreshRecyclerList;
    @BindView(R.id.empty_view_user_trips)
    FrameLayout emptyLayout;

    private UserTripsAdapter mAdapter;

    private ArrayList<UserTrips> modelList = new ArrayList<>();


    private static final String TAG = "";

    private FirebaseAuth mAuth;
    private static FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private String userID;
    private Dialog dialog;



    public UserTripsFragment() {
        // Required empty public constructor
    }

    public static UserTripsFragment newInstance() {
        UserTripsFragment fragment = new UserTripsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mAdapter = new UserTripsAdapter(getActivity(), modelList);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_trips, container, false);

        ButterKnife.bind(this, view);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference().child("Trips");
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        Context mContext = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(R.layout.fui_phone_progress_dialog);
        builder.setCancelable(false);
        builder.setTitle("Loading, Please wait...");
        dialog = builder.show();
        dialog.setCanceledOnTouchOutside(true);

        mRef.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                recyclerView.setAdapter(mAdapter);
                if (dataSnapshot.exists()) {
                    //clears list for refresh
                    modelList.clear();

                    emptyLayout.setVisibility(View.GONE);
                    for (DataSnapshot item : dataSnapshot.getChildren()) {

                        TripModel userInfo = item.getValue(TripModel.class);
                        modelList.add(new UserTrips(userInfo.getName(), userInfo.getDesc(), userInfo.getPushID(), userInfo.getTripImg()));

                    }
                } else {
                    emptyLayout.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                }

                dialog.dismiss();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                emptyLayout.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }


        });

        return view;

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        setAdapter();

        swipeRefreshRecyclerList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // Do your stuff on refresh
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (swipeRefreshRecyclerList.isRefreshing())
                            mAdapter.notifyDataSetChanged();
                            swipeRefreshRecyclerList.setRefreshing(false);

                    }
                }, 3000);

            }
        });

    }

    private void setAdapter() {

        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.addItemDecoration(new GridMarginDecoration(getActivity(), 8, 8, 8, 8));
        recyclerView.setLayoutManager(layoutManager);

        mAdapter.SetOnItemClickListener(new UserTripsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, UserTrips model) {


                //removeItem(model);
//                for (UserTrips trip : modelList) {
//                    System.out.println("Number = " + trip);
//                }
//                mAdapter.notifyDataSetChanged();
//                Toast.makeText(getContext(), "Deleted: " + model.getTitle(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}
