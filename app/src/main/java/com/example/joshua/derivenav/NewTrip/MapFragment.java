package com.example.joshua.derivenav.NewTrip;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
    private ArrayList<DestinationModel.Points_of_interest> pointOfInterestList = new ArrayList<>();
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

        //setAdapter();

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

        //set selected list to mapFragmentAdapter RecyclerView
        destinationModelList = stepDataManager.getNewDestinationList();

        //save new checked list to view
        //pointOfInterestList = stepDataManager.getPointOfInterestList();


        mAdapter = new MapRecyclerViewAdapter(getActivity(), destinationModelList);//modelList

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);


        //disable for tests only
        //gets generated coordinates per model item
        mAdapter.SetOnItemClickListener(new MapRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, DestinationModel model) { //MapModel model


                //handle item click events here
                LatLng coordinates = new LatLng(model.getPoints_of_interest().get(position).getLocation().getLatitude(), model.getPoints_of_interest().get(position).getLocation().getLongitude());
                CameraUpdate location = CameraUpdateFactory.newLatLngZoom(coordinates, 12);

                //get model gps coordinates and 1- add marker 2- move camera to model coordinates
                googleMap.addMarker(new MarkerOptions().position(coordinates).title(model.getPoints_of_interest().get(position).getTitle()));
                googleMap.animateCamera(location);
            }
        });

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

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    destinationModelList = stepDataManager.getNewDestinationList();
                    final String key = dbRef.child("Trips").child(userID).push().getKey();

                    String title = stepDataManager.getTripTitle();
                    String desc = stepDataManager.getDesc();
                    String category = stepDataManager.getCategory();

                    //PointsOfInterests Init Variables

                    Map newLocationData = new HashMap();
                    String destinationName;
                    String tripImage;
                    String destinationImage;
                    String destinationDesc;
                    String destinationWikiPage;
                    String destinationGoogleMaps;
                    double destinationLat;
                    double destinationLng;

                    //gets Image for Trip Model second picture of Points of Interest is selected
                    DestinationModel destinationMainImg = destinationModelList.get(1);
                    tripImage = destinationMainImg.getPoints_of_interest().get(1).getMain_image();

                    //creates new trip object
                    TripModel newTrip = new TripModel(title, desc, key, tripImage, category);
                    dbRef.child("Trips").child(userID).child(key).setValue(newTrip);


                    //iterates through points of interests
                    for (int i = 0; i < destinationModelList.size(); i++) {

                        DestinationModel destinationInfo = destinationModelList.get(i);
                        destinationName = destinationInfo.getPoints_of_interest().get(i).getTitle();
                        destinationLat = destinationInfo.getPoints_of_interest().get(i).getLocation().getLatitude();
                        destinationLng = destinationInfo.getPoints_of_interest().get(i).getLocation().getLongitude();
                        destinationImage = destinationInfo.getPoints_of_interest().get(i).getMain_image();
                        destinationDesc = destinationInfo.getPoints_of_interest().get(i).getDetails().getDescription();
                        destinationWikiPage = destinationInfo.getPoints_of_interest().get(i).getDetails().getWiki_page_link();
                        destinationGoogleMaps = destinationInfo.getPoints_of_interest().get(i).getLocation().getGoogle_maps_link();

                        //adds locations data objects

                        newLocationData.put("cityName", destinationName);
                        newLocationData.put("description", destinationDesc);
                        newLocationData.put("userID", userID);
                        newLocationData.put("key", key);
                        newLocationData.put("lat", destinationLat);
                        newLocationData.put("lng", destinationLng);
                        newLocationData.put("img", destinationImage);
                        newLocationData.put("wikiPage", destinationWikiPage);
                        newLocationData.put("googleMaps", destinationGoogleMaps);

                        dbRef.child("Destinations").push().setValue(newLocationData);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        callback.complete();
        getActivity().finish();
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
