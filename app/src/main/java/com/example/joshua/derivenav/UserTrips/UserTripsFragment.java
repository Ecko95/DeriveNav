package com.example.joshua.derivenav.UserTrips;


import android.os.Bundle;

import android.support.v7.widget.GridLayoutManager;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;

import android.support.v4.widget.SwipeRefreshLayout;

import com.example.joshua.derivenav.R;
import com.example.joshua.derivenav.com.joshua.api.model.Trip;
import com.example.joshua.derivenav.com.joshua.api.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.os.Handler;


import android.view.ViewGroup;
import android.view.MenuInflater;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserTripsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class UserTripsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_recycler_list) SwipeRefreshLayout swipeRefreshRecyclerList;

    private UserTripsAdapter mAdapter;

    private ArrayList<UserTrips> modelList = new ArrayList<>();


    private static final String TAG = "";

    private FirebaseAuth mAuth;
    private static FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private String userID;

    public static FirebaseDatabase getDatabase() {
        if (mFirebaseDatabase == null) {
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mFirebaseDatabase.setPersistenceEnabled(true);
        }
        return mFirebaseDatabase;
    }

    public UserTripsFragment() {
        // Required empty public constructor
    }


//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment UserTripsFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static UserTripsFragment newInstance(String param1, String param2) {
//        UserTripsFragment fragment = new UserTripsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {





        View view = inflater.inflate(R.layout.fragment_user_trips, container, false);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();



        mRef.child("Trips").child(userID).addValueEventListener(new ValueEventListener() {



            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                recyclerView.setAdapter(mAdapter);
                //clears list for refresh
                modelList.clear();


                    final Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                    while (items.hasNext()){

                        DataSnapshot item = items.next();
                        Trip userInfo = item.getValue(Trip.class);
                        modelList.add(new UserTrips(userInfo.getName(), userInfo.getDesc(), userInfo.getPushID()));
                    }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.addItemDecoration(new GridMarginDecoration(getActivity(), 2, 2, 2, 2));
        recyclerView.setLayoutManager(layoutManager);



        mAdapter.SetOnItemClickListener(new UserTripsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, UserTrips model) {
                removeItem(model);
                for (UserTrips trip : modelList) {
                    System.out.println("Number = " + trip);
                }
                mAdapter.notifyDataSetChanged();
//                Toast.makeText(getContext(), "Deleted: " + model.getTitle(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void removeItem(UserTrips trip){
        ArrayList<UserTrips> modelList2 = new ArrayList<>();

        mRef.child("Trips").child(userID).child(trip.getKey()).removeValue();

        mAdapter.updateList(modelList);
        Toast.makeText(getContext(), "deleted" + trip.getTitle(), Toast.LENGTH_SHORT).show();

    }

}
