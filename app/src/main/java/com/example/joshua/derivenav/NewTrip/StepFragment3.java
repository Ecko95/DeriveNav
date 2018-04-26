package com.example.joshua.derivenav.NewTrip;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.joshua.derivenav.NewTripActivity;
import com.example.joshua.derivenav.R;
import com.example.joshua.derivenav.com.joshua.api.controller.RestManager;

import com.example.joshua.derivenav.com.joshua.api.model.City;
import com.example.joshua.derivenav.com.joshua.api.model.Trip;
import com.example.joshua.derivenav.com.joshua.api.model.adapter.POIAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;


import net.steamcrafted.materialiconlib.MaterialMenuInflater;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment3 extends ButterKnifeFragment implements Step{

    private StepDataManager stepDataManager;

    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_description)
    EditText editDesc;

    private Menu menu;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference dbRef;

    private String userID;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof StepDataManager) {
            stepDataManager = (StepDataManager) context;
        } else {
            throw new IllegalStateException("Activity must implement DataManager interface!");
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_step_fragment3;
    }

    public StepFragment3() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    public static Step newInstance() {
        return new StepFragment3();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        dbRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // User is signed in
                    //Toast.makeText(LoginActivity.this, "you're signed in", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "you're not signed in", Toast.LENGTH_SHORT).show();
                }
            }
        };

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @OnClick(R.id.btn_save_trip)
    public void saveTrip(View view) {

        String name = editName.getText().toString();
        String desc = editDesc.getText().toString();

        if(name != "" && desc != ""){

            Trip newTrip = new Trip(name,desc);
            dbRef.child("trips").child(userID).setValue(newTrip);
            Toast.makeText(getContext(), "New Trip Added", Toast.LENGTH_SHORT).show();
            editName.setText("");
            editDesc.setText("");

        }else{
            Toast.makeText(getContext(), "Please fill in the fields", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.menu = menu;
        menu.findItem(R.id.action_search).setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }



}
