package com.example.joshua.derivenav;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joshua.derivenav.com.joshua.api.controller.RestManager;
import com.example.joshua.derivenav.com.joshua.api.model.Facade.apiClient;
import com.example.joshua.derivenav.com.joshua.api.model.adapter.POIAdapter;
import com.example.joshua.derivenav.com.joshua.trips.NewTripFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TripsFragment extends Fragment implements POIAdapter.POIClickListener {

    private RecyclerView mRecyclerView;
    private POIAdapter mPOIAdapter;
    private RestManager mManager;
    private ProgressDialog mDialog;

    public static TripsFragment newInstance() {
        TripsFragment fragment = new TripsFragment();
        return fragment;
    }

    public void getFeed() {
        mDialog = new ProgressDialog(getActivity());
        mDialog.setMessage("Loading apiClient Data...");
        mDialog.setCancelable(true);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setIndeterminate(true);
        mManager = new RestManager();
        Call<List<apiClient>> listCall = mManager.getFlowerService().getAllPointsOfInterest();
        listCall.enqueue(new Callback<List<apiClient>>() {
            @Override
            public void onResponse(Call<List<apiClient>> call, Response<List<apiClient>> response) {
                if (response.isSuccessful()) {

                    List<apiClient> flowerList = response.body();

                    for (int i = 0; i < flowerList.size(); i++) {
                        apiClient flower = flowerList.get(i);

                        mPOIAdapter.addFlower(flower);
                    }
                } else {
                    int sc = response.code();
                    switch (sc) {
                        case 400:
                            Log.e("Error 400", "Bad Request");
                            break;
                        case 404:
                            Log.e("Error 404", "Not Found");
                            break;
                        default:
                            Log.e("Error", "Generic Error");
                    }
                }
                mDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<apiClient>> call, Throwable t) {
                mDialog.dismiss();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        MainActivity activity = (MainActivity) getActivity();
//        if(activity != null){
//            activity.showUpButton();
//        }
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //enables the fragment to have an options menu on Activity Toolbar
        setHasOptionsMenu(true);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_trips, container, false);
//        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Fragment fragment= new NewTripFragment();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragment_container, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
//                transaction.addToBackStack(null);  // this will manage backstack
//                transaction.commit();
//                Toast.makeText(getContext(), "Done!", Toast.LENGTH_SHORT).show();
//            }
//        });

//        View v = inflater.inflate(R.layout.fragment_home, container, false);

        Toast.makeText(view.getContext(), "Recycler viewer loaded", Toast.LENGTH_SHORT).show();

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        mPOIAdapter = new POIAdapter(this);

        mRecyclerView.setAdapter(mPOIAdapter);
        getFeed();
        return view;

//        return inflater.inflate(R.layout.fragment_trips, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

//    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//
////        menu.clear(); // Remove all existing items from the menu, leaving it empty as if it had just been created.
//
//
//        //getActivity().getMenuInflater().inflate(R.menu.form_menu,menu); //add new menu to toolbar on fragment
//
//        menu.findItem(R.id.action_logout).setVisible(false);
//        super.onPrepareOptionsMenu(menu);
//    }

    @Override
    public void onClick(int position) {
        Toast.makeText(getContext(), "you pressed: " + position, Toast.LENGTH_SHORT).show();
    }
}
