package com.example.joshua.derivenav;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.joshua.derivenav.Api.ApiController;
import com.example.joshua.derivenav.NewTrip.Models.DestinationModel;
import com.example.joshua.derivenav.NewTrip.Models.TripModel;
import com.example.joshua.derivenav.UserTripDetails.UserTripDetails;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;


public class HomeFragment extends Fragment {

    private MaterialSearchView materialSearchView;
    private Menu menu;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference dbRef;
    private String userID;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        return inflater.inflate(R.layout.fragment_home, container, false);

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity) getActivity();
        if(activity != null){
            activity.hideUpButton();
        }
    }

    @OnClick(R.id.action_card_add)
    public void setAction_card_add(View view){
        startActivity(new Intent(getActivity(), NewTripActivity.class));
        ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle();
//        Toast.makeText(getActivity(), "you clicked on action: add", Toast.LENGTH_SHORT).show();

    }
    @OnClick(R.id.action_card_profile)
    public void setAction_card_profile(View view){
        startActivity(new Intent(getActivity(), ProfileActivity.class));
        //Toast.makeText(getActivity(), "you clicked on action: profile", Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.action_card_help)
    public void setAction_card_help(View view) {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    mAuth = FirebaseAuth.getInstance();
                    mFirebaseDatabase = FirebaseDatabase.getInstance();
                    dbRef = mFirebaseDatabase.getReference();
                    FirebaseUser user = mAuth.getCurrentUser();
                    userID = user.getUid();

                    String name = "Trip Name";
                    String desc = "Description";
                    final String Tripkey = dbRef.child("Locations").child(userID).push().getKey();


                    //if fields are entered, then create new Trip
                    //populate data with API

                    TripModel newTrip = new TripModel(name, desc, Tripkey, "https://picsum.photos/200/300/?random");
                    Map newLocationData = new HashMap();
                    newLocationData.put("cityName", "Madrid");
                    newLocationData.put("key", Tripkey);
                    newLocationData.put("userID", userID);


                    newLocationData.put("cityName", "Test City");
                    newLocationData.put("description", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam placerat in tellus id vehicula. Maecenas pellentesque aliquam nulla ac maximus. Praesent eget nibh dolor. Pellentesque tempus sollicitudin scelerisque. Praesent commodo justo venenatis nisi euismod laoreet a at nisi. Pellentesque pretium orci nec diam fermentum consequat.");
                    newLocationData.put("userID", userID);
                    newLocationData.put("key", Tripkey);
                    newLocationData.put("lat", 40.0361);
                    newLocationData.put("lng", -3.605);
                    newLocationData.put("img", "https://picsum.photos/200/300/?random");
                    newLocationData.put("wikiPage", "https://en.wikipedia.org/wiki/Central Park Zoo");
                    newLocationData.put("googleMaps", "http://maps.google.com?q=40.8506,-73.8754");

                    dbRef.child("Trips").child(userID).child(Tripkey).setValue(newTrip);
                    dbRef.child("Destinations").push().setValue(newLocationData);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    //AMADEUS API TEST
    private ApiController mControllerManager;
    private AlertDialog mDialog;
    private static final String TAG = "HOMEFRAGMENT";

    public void getFeed() {
        mControllerManager = new ApiController();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.fui_phone_progress_dialog);
        builder.setCancelable(false);
        builder.setTitle("Loading, Please wait...");
        mDialog = builder.show();

//        try {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mDialog.dismiss();
//
//                    //run code here
//
////                HashMap<String,String> hashMap = new HashMap<>();
////                hashMap.put("search",mChosenCitySearch);
//
//                    Call<List<DestinationModel>> listCall = mControllerManager.getDestinationsService()
//                            .getAllPointsOfInterest(
//                                    "t6UEKv6YlQGM32tunEqo9oXNX91hGTsi",
//                                    "Madrid"
//                            );//mChosenCitySearch
//
//                    listCall.enqueue(new Callback<List<DestinationModel>>() {
//                        @Override
//                        public void onResponse(Call<List<DestinationModel>> call, Response<List<DestinationModel>> response) {
//                            if (response.isSuccessful()) {
//
//                                Toast.makeText(getContext(), "OnResponse Successfull", Toast.LENGTH_SHORT).show();
//                                Log.d(TAG,response.message());
//                            } else {
//                                int sc = response.code();
//                                switch (sc) {
//                                    case 400:
//                                        Log.e("Error 400", "Bad Request");
//                                        break;
//                                    case 404:
//                                        Log.e("Error 404", "Not Found");
//                                        break;
//                                    default:
//                                        Log.e("Error", "Generic Error");
//                                }
//                            }
//                            mDialog.dismiss();
//                        }
//
//                        @Override
//                        public void onFailure(Call<List<DestinationModel>> call, Throwable t) {
//                            mDialog.dismiss();
//                            t.printStackTrace();
//                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                }
//            }, 1000L);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//        Call<List<DestinationModel>> listCall = mControllerManager.getDestinationsService().getAllPointsOfInterest(
//                "UOJASf28IviDWrP4lFnEYGGJuLgrSxpH",
//                mChosenCitySearch,
//                10
//        );


    }
}
