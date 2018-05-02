package com.example.joshua.derivenav;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;


public class HomeFragment extends Fragment {

    private MaterialSearchView materialSearchView;
    private Menu menu;

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
        Toast.makeText(getActivity(), "you clicked on action: add", Toast.LENGTH_SHORT).show();

    }
    @OnClick(R.id.action_card_profile)
    public void setAction_card_profile(View view){
        startActivity(new Intent(getActivity(), ProfileActivity.class));
        //Toast.makeText(getActivity(), "you clicked on action: profile", Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.action_card_help)
    public void setAction_card_help(View view) {
        Toast.makeText(getActivity(), "you clicked on action: help", Toast.LENGTH_SHORT).show();
    }
}
