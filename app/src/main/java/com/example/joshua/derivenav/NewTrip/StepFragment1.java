package com.example.joshua.derivenav.NewTrip;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joshua.derivenav.NewTrip.Adapters.StepperAdapter;
import com.example.joshua.derivenav.NewTrip.DataManagers.StepDataManager;
import com.example.joshua.derivenav.NewTripActivity;
import com.example.joshua.derivenav.R;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.stepstone.stepper.adapter.StepAdapter;

import net.steamcrafted.materialiconlib.MaterialMenuInflater;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment1 extends Fragment implements BlockingStep, StepperLayout.StepperListener {

    @BindView(R.id.txt_selected_search)
    TextView txt_selected_search;

    private static final String CURRENT_STEP_POSITION_KEY = "position";
    private StepDataManager stepDataManager;
    private StepperLayout mStepperLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view = inflater.inflate(R.layout.fragment_step_fragment1, container, false);
        ButterKnife.bind(this,view);

        int startingStepPosition = savedInstanceState != null ? savedInstanceState.getInt(CURRENT_STEP_POSITION_KEY) : 0;

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
            Toast.makeText(getContext(), "search null", Toast.LENGTH_SHORT).show();
        }

        //initialize your UI
        return view;


    }

    private void disableNextStep() {

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
            Toast.makeText(getContext(), "search null", Toast.LENGTH_SHORT).show();
            callback.getStepperLayout().setNextButtonEnabled(false);
        }
        else{
            callback.getStepperLayout().showProgress("Operation in progress, please wait...");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    callback.goToNextStep();
                    callback.getStepperLayout().hideProgress();

                    //saves data from search activity
                    String enteredText = txt_selected_search.getText().toString();
                    stepDataManager.saveStepData(enteredText);
                }
            }, 2000L);

        }

    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        callback.complete();
        Toast.makeText(getContext(), "Finish", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

//    @BindView(R.id.txt_selected_search)
//    TextView txt_selected_search;

    public StepFragment1() {
        // Required empty public constructor
    }





    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_STEP_POSITION_KEY, mStepperLayout.getCurrentStepPosition());
        super.onSaveInstanceState(outState);
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
