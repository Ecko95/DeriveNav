package com.example.joshua.derivenav.NewTrip;


import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import android.support.v4.widget.SwipeRefreshLayout;

import com.example.joshua.derivenav.NewTrip.Adapters.MapRecyclerViewAdapter;
import com.example.joshua.derivenav.NewTrip.DataManagers.StepDataManager;
import com.example.joshua.derivenav.NewTrip.Models.DestinationModel;
import com.example.joshua.derivenav.NewTrip.Models.MapModel;
import com.example.joshua.derivenav.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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


public class MapFragment extends Fragment implements BlockingStep, OnMapReadyCallback,GoogleMap.OnCameraMoveStartedListener{

    private RecyclerView recyclerView;

    // @BindView(R.id.recycler_view)
    // RecyclerView recyclerView;


    // @BindView(R.id.swipe_refresh_recycler_list)
    // SwipeRefreshLayout swipeRefreshRecyclerList;

    private SwipeRefreshLayout swipeRefreshRecyclerList;
    private MapRecyclerViewAdapter mAdapter;

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




        return view;

    }

    private void getLocationPermission(){
        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                                android.Manifest.permission.ACCESS_COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    mLocationPermissionGranted = true;
            }else{
                ActivityCompat.requestPermissions(getActivity(),permissions,LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        mLocationPermissionGranted = false;

        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for (int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
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

        initMap(view,savedInstanceState);

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

        destinationModelList = stepDataManager.getNewDestinationList();
        Log.d(TAG, destinationModelList.toString());
//        for (int i = 0; i < destinationModelList.size(); i++) {
//            DestinationModel destination = destinationModelList.get(i);
//            mAdapter.addDestinations(destination);
//
//        }


        //destinationModelList.add(new DestinationModel(name, "Hello " + " Android"));
//        modelList.add(new MapModel("Beta", "Hello " + " Beta"));
//        modelList.add(new MapModel("Cupcake", "Hello " + " Cupcake"));
//        modelList.add(new MapModel("Donut", "Hello " + " Donut"));
//        modelList.add(new MapModel("Eclair", "Hello " + " Eclair"));
//        modelList.add(new MapModel("Froyo", "Hello " + " Froyo"));
//        modelList.add(new MapModel("Gingerbread", "Hello " + " Gingerbread"));
//        modelList.add(new MapModel("Honeycomb", "Hello " + " Honeycomb"));
//        modelList.add(new MapModel("Ice Cream Sandwich", "Hello " + " Ice Cream Sandwich"));
//        modelList.add(new MapModel("Jelly Bean", "Hello " + " Jelly Bean"));
//        modelList.add(new MapModel("KitKat", "Hello " + " KitKat"));
//        modelList.add(new MapModel("Lollipop", "Hello " + " Lollipop"));
//        modelList.add(new MapModel("Marshmallow", "Hello " + " Marshmallow"));
//        modelList.add(new MapModel("Nougat", "Hello " + " Nougat"));
//        modelList.add(new MapModel("Android O", "Hello " + " Android O"));


        mAdapter = new MapRecyclerViewAdapter(getActivity(), destinationModelList);//modelList

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener(new MapRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, DestinationModel model) { //MapModel model



                //handle item click events here
                Toast.makeText(getActivity(), "Hey " + model.getTitle(), Toast.LENGTH_SHORT).show();

                LatLng coordinates = new LatLng(5 + position + .510357,-0.116773);
                CameraUpdate location = CameraUpdateFactory.newLatLngZoom(coordinates,12);

                googleMap.addMarker(new MarkerOptions().position(coordinates).title("Marker" + position));

                googleMap.animateCamera(location);
            }
        });


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
        callback.complete();
        getActivity().finish();
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

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-33.87365, 151.20689), 10));

    }

    @Override
    public void onCameraMoveStarted(int camera) {
        if (camera == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            Toast.makeText(getActivity(), "The user gestured on the map.",
                    Toast.LENGTH_SHORT).show();
        } else if (camera == GoogleMap.OnCameraMoveStartedListener
                .REASON_API_ANIMATION) {
            Toast.makeText(getActivity(), "The user tapped something on the map.",
                    Toast.LENGTH_SHORT).show();
        } else if (camera == GoogleMap.OnCameraMoveStartedListener
                .REASON_DEVELOPER_ANIMATION) {
            Toast.makeText(getActivity(), "The app moved the camera.",
                    Toast.LENGTH_SHORT).show();
        }

    }

}
