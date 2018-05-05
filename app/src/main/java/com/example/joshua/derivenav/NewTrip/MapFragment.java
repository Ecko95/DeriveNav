package com.example.joshua.derivenav.NewTrip;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.v4.widget.SwipeRefreshLayout;

import com.example.joshua.derivenav.NewTrip.Adapters.DestinationsRecyclerViewAdapter;
import com.example.joshua.derivenav.NewTrip.Adapters.MapRecyclerViewAdapter;
import com.example.joshua.derivenav.NewTrip.DataManagers.StepDataManager;
import com.example.joshua.derivenav.NewTrip.Models.DestinationModel;
import com.example.joshua.derivenav.NewTrip.Models.MapModel;
import com.example.joshua.derivenav.NewTrip.Models.TripModel;
import com.example.joshua.derivenav.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import android.widget.Toast;


import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class MapFragment extends Fragment implements BlockingStep, OnMapReadyCallback, GoogleMap.OnCameraMoveStartedListener {

    private RecyclerView recyclerView;

    // @BindView(R.id.recycler_view)
    // RecyclerView recyclerView;


    // @BindView(R.id.swipe_refresh_recycler_list)
    // SwipeRefreshLayout swipeRefreshRecyclerList;

    private SwipeRefreshLayout swipeRefreshRecyclerList;
    private MapRecyclerViewAdapter mAdapter;
    private DestinationsRecyclerViewAdapter mDestinationAdapter;

    private ArrayList<MapModel> modelList = new ArrayList<>();
    private ArrayList<DestinationModel> destinationModelList = new ArrayList<>();
    private MapView mapView;
    private GoogleMap googleMap;

    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private boolean mLocationPermissionGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private StepDataManager stepDataManager;
    private static final String TAG = "MapFragment";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference dbRef;

    private String userID;
    private Dialog mDialog;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof StepDataManager) {
            stepDataManager = (StepDataManager) context;
        } else {
            throw new IllegalStateException("Activity must implement DataManager interface!");
        }
    }

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);
        findViews(view);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        dbRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();


        return view;

    }

    private void getLocationPermission() {
        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            } else {
                ActivityCompat.requestPermissions(getActivity(), permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        mLocationPermissionGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initMap(view, savedInstanceState);

        getLocationPermission();


//        setAdapter();

    }

    private void initMap(View view, Bundle savedInstanceState) {
        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
    }


    private void findViews(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    }


    private void setAdapter() {

        //set list to mapFragmentAdapter RecyclerViews
        destinationModelList = stepDataManager.getNewDestinationList();

//        for (int i = 0; i < destinationModelList.size(); i++) {
//            DestinationModel destination = destinationModelList.get(i);
//            mAdapter.addDestinations(destination);
//
//        }


        mAdapter = new MapRecyclerViewAdapter(getActivity(), destinationModelList);//modelList

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(mAdapter);

        //disable for tests only
//        mAdapter.SetOnItemClickListener(new MapRecyclerViewAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position, DestinationModel model) { //MapModel model
//
//
//                //handle item click events here
//                LatLng coordinates = new LatLng(model.getPoints_of_interest().get(position).getLocation().getLatitude(), model.getPoints_of_interest().get(position).getLocation().getLongitude());
//                CameraUpdate location = CameraUpdateFactory.newLatLngZoom(coordinates, 12);
//
//                //get model gps coordinates and 1- add marker 2- move camera to model coordinates
//                googleMap.addMarker(new MarkerOptions().position(coordinates).title(model.getPoints_of_interest().get(position).getTitle()));
//                googleMap.animateCamera(location);
//            }
//        });

        //mAdapter.updateList(destinationModelList);


    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {
        setAdapter();
        //Toast.makeText(getContext(), stepDataManager.getDestinationModel().getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        //null
    }


    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.fui_phone_progress_dialog);
        builder.setCancelable(false);
        mDialog = builder.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDialog.dismiss();
                getCheckedDestinationTrips();
                String name = "Trip Name";
                String desc = "Description";
                final String key = dbRef.child("Trips").child(userID).push().getKey();

                //if fields are entered, then create new Trip
                //populate data with API
                try {
//                   List<DestinationModel.Points_of_interest> points_of_interest = null;
//                   DestinationModel.Points_of_interest destinationPointOfInterests = new DestinationModel.Points_of_interest();
//                   DestinationModel.Details details = new DestinationModel.Details();
//                   destinationPointOfInterests.setTitle("Title 1");
//                   details.setShort_description("Lorem Ipsum");
//                   destinationPointOfInterests.setDetails(details);
//                   points_of_interest.add(0,destinationPointOfInterests);


                    Map newLocationData = new HashMap();

                    newLocationData.put("city_name", "Madrid");
                    newLocationData.put("lat", "32.3403");
                    newLocationData.put("log", "-21.3234");

                    Map newLocationData2 = new HashMap();

                    newLocationData2.put("city_name", "Barcelona");
                    newLocationData2.put("lat", "32.3403");
                    newLocationData2.put("lon", "-21.3234");

                    TripModel newTrip = new TripModel(name, desc, key);
                    dbRef.child("Trips").child(userID).child(key).setValue(newTrip);
                    final String key2 = dbRef.push().getKey();


                    dbRef.child("Trips").child(userID).child(key).child("locations").child("1").setValue(newLocationData);
                    dbRef.child("Trips").child(userID).child(key).child("locations").child("2").setValue(newLocationData2);

//                   dbRef.child("Trips").child(userID).child(key).child("locations").child(key2).setValue(newLocationData);
//                   getActivity().finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, 1000L);

        callback.complete();


//        callback.complete();
//        getActivity().finish();
    }

    private void getCheckedDestinationTrips() {
//        DestinationModel testDestinationModel = new DestinationModel();
//        DestinationModel.Current_city testCurrentCity = new DestinationModel.Current_city();
//        testCurrentCity.setName("CITY TEST");
//        testCurrentCity.setTotal_points_of_interest(2);
//        for(int i = 0; i < destinationModelList.size(); i++){
////            String name = destinationModelList.get(i).getCurrent_city().getName();
////            String desc = destinationModelList.get(i).getPoints_of_interest().get(i).getDetails().getShort_description();
//
//
//
//            TripModel newTrip = new TripModel(name,desc,key,destinationModelList);
//
//        }
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    @Override
    public void onMapReady(GoogleMap mMap) {
        googleMap = mMap;
        googleMap.setOnCameraMoveStartedListener(this);
        googleMap.getUiSettings().setMapToolbarEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(52.594688, -2.0337076), 10));

    }

    @Override
    public void onCameraMoveStarted(int camera) {
        if (camera == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            //Toast.makeText(getActivity(), "The user gestured on the map.", Toast.LENGTH_SHORT).show();
        } else if (camera == GoogleMap.OnCameraMoveStartedListener
                .REASON_API_ANIMATION) {
            //Toast.makeText(getActivity(), "The user tapped something on the map.", Toast.LENGTH_SHORT).show();
        } else if (camera == GoogleMap.OnCameraMoveStartedListener
                .REASON_DEVELOPER_ANIMATION) {
            //Toast.makeText(getActivity(), "The app moved the camera.", Toast.LENGTH_SHORT).show();
        }

    }

}
