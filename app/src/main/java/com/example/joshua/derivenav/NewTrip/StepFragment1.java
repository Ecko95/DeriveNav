package com.example.joshua.derivenav.NewTrip;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joshua.derivenav.LoginActivity;
import com.example.joshua.derivenav.MainActivity;
import com.example.joshua.derivenav.NewTrip.Adapters.StepperAdapter;
import com.example.joshua.derivenav.NewTrip.DataManagers.StepDataManager;
import com.example.joshua.derivenav.NewTripActivity;
import com.example.joshua.derivenav.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.stepstone.stepper.adapter.StepAdapter;
import com.takusemba.spotlight.OnSpotlightEndedListener;
import com.takusemba.spotlight.OnSpotlightStartedListener;
import com.takusemba.spotlight.OnTargetStateChangedListener;
import com.takusemba.spotlight.SimpleTarget;
import com.takusemba.spotlight.Spotlight;

import net.steamcrafted.materialiconlib.MaterialMenuInflater;

import org.w3c.dom.Text;

import butterknife.BindFloat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class StepFragment1 extends Fragment implements BlockingStep, StepperLayout.StepperListener {

    @BindView(R.id.txt_selected_search)
    TextView txt_selected_search;
    @BindView(R.id.sp_category_trip)
    Spinner spinner_categories;
    @BindView(R.id.txt_title_trip)
    TextInputEditText txt_title_trip;
    @BindView(R.id.txt_desc_trip)
    TextInputEditText txt_desc_trip;

    private String mTripTitle;
    private String mTripDesc;
    private String mTripCategory;

    private StepDataManager stepDataManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_step_fragment1, container, false);
        ButterKnife.bind(this,view);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.category_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_categories.setAdapter(adapter);
        //spinner actions
        spinner_categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mTripCategory = spinner_categories.getItemAtPosition(i).toString();
                //Toast.makeText(getContext(), mTripCategory, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        try{

            NewTripActivity activity = (NewTripActivity) getActivity();
            String myDataFromActivity = activity.getChosenSearch();
            txt_selected_search.setText(myDataFromActivity);

            String chosenSearch = getArguments().getString("chosenSearch");
            txt_selected_search.setText(chosenSearch);

            return view;


        }catch(Exception e){

        }

        if (txt_selected_search.getText() != ""){
            //Toast.makeText(getContext(), "search null", Toast.LENGTH_SHORT).show();
        }

        //initialize UI
        return view;


    }

    private void disableNextStep(final StepperLayout.OnNextClickedCallback callback) {
//        disables next fragment button
        callback.getStepperLayout().setNextButtonEnabled(false);
        callback.getStepperLayout().setNextButtonVerificationFailed(true);
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


    @Override
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
        if (txt_selected_search.getText() == ""){
            //disables next button
            disableNextStep(callback);
        }
        else{
            callback.getStepperLayout().showProgress("Operation in progress, please wait...");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //saves data from search activity
                    //saves city search

                    String enteredText = txt_selected_search.getText().toString();
                    stepDataManager.saveStepData(enteredText);

                    mTripTitle = txt_title_trip.getText().toString();
                    mTripDesc = txt_desc_trip.getText().toString();
                    // spinner gets text on listener

                    stepDataManager.saveStepData(enteredText);
                    stepDataManager.saveTripTitle(mTripTitle);
                    stepDataManager.saveTripDesc(mTripDesc);
                    stepDataManager.saveTripCategory(mTripCategory);

                    callback.goToNextStep();
                    callback.getStepperLayout().hideProgress();
                }
            }, 2000L);

        }

    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        callback.complete();
        //Toast.makeText(getContext(), "Finish", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }


    public StepFragment1() {
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
    public void onCompleted(View completeButton) {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    @Override
    public void onStepSelected(int newStepPosition) {

    }

    @Override
    public void onReturn() {

    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
    }

    public static Step newInstance() {
        return new StepFragment1();
    }


}
