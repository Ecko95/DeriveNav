package com.example.joshua.derivenav.NewTrip;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;

import android.widget.TextView;
import android.widget.Toast;


import com.example.joshua.derivenav.R;
import com.example.joshua.derivenav.com.joshua.api.controller.RestManager;
import com.example.joshua.derivenav.com.joshua.api.model.City;
import com.example.joshua.derivenav.com.joshua.api.model.Facade.apiClient;
import com.example.joshua.derivenav.com.joshua.api.model.adapter.POIAdapter;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment2 extends ButterKnifeFragment implements BlockingStep {

    @BindView(R.id.txt_selected_search)
    TextView chosenSearch;

    private StepDataManager stepDataManager;

    private AlertDialog dialog;

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
        return R.layout.fragment_step_fragment2;
    }

    private POIAdapter mPOIAdapter;
    private ProgressDialog mDialog;
    private RestManager mManager;

    public StepFragment2() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {
        Toast.makeText(getContext(), stepDataManager.getData(), Toast.LENGTH_SHORT).show();
        chosenSearch.setText(stepDataManager.getData());
    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public static Step newInstance() {
        return new StepFragment2();
    }

    @Override
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.fui_phone_progress_dialog);
        builder.setCancelable(false);
        dialog = builder.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                callback.goToNextStep();
            }
        }, 2000L);
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {

    }
}
