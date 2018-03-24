package com.example.joshua.derivenav;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.joshua.derivenav.com.joshua.trips.NewTripFragment;


public class TripsFragment extends Fragment{


    public static TripsFragment newInstance() {
        TripsFragment fragment = new TripsFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity) getActivity();
        if(activity != null){
            activity.showUpButton();
        }
    }

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
        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment= new NewTripFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
                transaction.addToBackStack(null);  // this will manage backstack
                transaction.commit();
                Toast.makeText(getContext(), "Done!", Toast.LENGTH_SHORT).show();
            }
        });


        return inflater.inflate(R.layout.fragment_trips, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
//        menu.clear(); // Remove all existing items from the menu, leaving it empty as if it had just been created.
        getActivity().getMenuInflater().inflate(R.menu.form_menu,menu);
    }
}
