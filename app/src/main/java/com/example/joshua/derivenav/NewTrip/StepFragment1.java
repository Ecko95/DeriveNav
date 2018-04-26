package com.example.joshua.derivenav.NewTrip;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joshua.derivenav.NewTripActivity;
import com.example.joshua.derivenav.R;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment1 extends ButterKnifeFragment implements BlockingStep {

    @BindView(R.id.editText)
    EditText editText;

    private StepDataManager stepDataManager;

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
        return R.layout.fragment_step_fragment1;
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        String enteredText = editText.getText().toString();
        stepDataManager.saveStepData(enteredText);
        callback.goToNextStep();
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


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//
//
//
//        View view = inflater.inflate(R.layout.fragment_step_fragment1, container, false);
//        ButterKnife.bind(this,view);
//
//        try{
//
//            NewTripActivity activity = (NewTripActivity) getActivity();
//            String myDataFromActivity = activity.getChosenSearch();
//            txt_selected_search.setText(myDataFromActivity);
//
//            return view;
////            String chosenSearch = getArguments().getString("chosenSearch");
////            txt_selected_search.setText(chosenSearch);
//
//        }catch(Exception e){
//
//        }
//
//        //initialize your UI
//        return view;
//
//    }

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

    public void setOnClickListener(View.OnClickListener onClickListener) {
    }

    public static Step newInstance() {
        return new StepFragment1();
    }
}
