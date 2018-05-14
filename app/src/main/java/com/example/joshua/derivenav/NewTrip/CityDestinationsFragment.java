package com.example.joshua.derivenav.NewTrip;


import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import com.example.joshua.derivenav.Api.ApiController;
import com.example.joshua.derivenav.NewTrip.Adapters.DestinationsRecyclerViewAdapter;
import com.example.joshua.derivenav.NewTrip.DataManagers.StepDataManager;
import com.example.joshua.derivenav.NewTrip.Models.DestinationModel;
import com.example.joshua.derivenav.NewTripActivity;
import com.example.joshua.derivenav.R;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import android.widget.Toast;


import android.view.ViewGroup;
import android.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CityDestinationsFragment extends Fragment implements BlockingStep {

    private StepDataManager stepDataManager;
    private AlertDialog mDialog;
    private ApiController mControllerManager;
    private String mChosenCitySearch;
    private ArrayList<DestinationModel> mDestinationList = new ArrayList<>();
    private ArrayList<DestinationModel> mCheckedDestinationList = new ArrayList<>();
    private static final String TAG = "CityDestinationsFragmen";

    private boolean isItemsChecked = false;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private DestinationsRecyclerViewAdapter mAdapter;

    private ArrayList<DestinationModel> modelList = new ArrayList<>();


    public CityDestinationsFragment() {
        // Required empty public constructor
    }

    public static CityDestinationsFragment newInstance() {
        CityDestinationsFragment fragment = new CityDestinationsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

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
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_city_destinations, container, false);

        ButterKnife.bind(this, view); // inti views

        try{

            NewTripActivity activity = (NewTripActivity) getActivity();
            String myDataFromActivity = activity.getChosenSearch();
            mChosenCitySearch = myDataFromActivity.toString();

        }catch(Exception e){

            e.printStackTrace();

        }



        return view;

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setAdapter();


    }


    private void setAdapter() {

        mAdapter = new DestinationsRecyclerViewAdapter(getActivity(), modelList, "Points of Interests");


        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener(new DestinationsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, DestinationModel model) {

                //handle item click events here



            }
        });


        mAdapter.SetOnCheckedListener(new DestinationsRecyclerViewAdapter.OnCheckedListener() {

            @Override
            public void onChecked(View view, boolean isChecked, int position, DestinationModel model) {

                //only pass list if users check items

                if (isChecked) {
                    isItemsChecked = true;
                    mCheckedDestinationList.add(model);
                    //Toast.makeText(getContext(), "added item: " + model.getPoints_of_interest().get(position - 1).getTitle(), Toast.LENGTH_SHORT).show();
                } else {


                    //mDestinationList.clear();

                }
                //Toast.makeText(getActivity(), (isChecked ? "Checked " : "Unchecked ") + model.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });


        mAdapter.SetOnHeaderClickListener(new DestinationsRecyclerViewAdapter.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(View view, String headerTitle) {

                //handle item click events here


            }
        });


    }

    public void disableNextStep(final StepperLayout.OnNextClickedCallback callback) {
        //disable back button
        callback.getStepperLayout().setNextButtonVerificationFailed(true);
        callback.getStepperLayout().setNextButtonEnabled(false);
        callback.getStepperLayout().setBackButtonEnabled(false);
        int disabledColor = Color.alpha(R.color.disabled_button);
        callback.getStepperLayout().setBackButtonColor(disabledColor);
    }

    @Override
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.fui_phone_progress_dialog);
        builder.setCancelable(false);
        builder.setTitle("Loading, Please wait...");
        mDialog = builder.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDialog.dismiss();

                if (isItemsChecked) {

                    mAdapter.updateList(mCheckedDestinationList);
                    stepDataManager.saveDestinationList(mCheckedDestinationList);
                    callback.goToNextStep();

                    disableNextStep(callback);

                } else {
                    Toast.makeText(getActivity(), "Please select at least one destination", Toast.LENGTH_SHORT).show();
                }




            }
        }, 1000L);
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        //null N/A
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {
        Toast.makeText(getContext(), "Searching For: " + stepDataManager.getData(), Toast.LENGTH_SHORT).show();
        mChosenCitySearch = stepDataManager.getData();
        getFeed();

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    public void getFeed() {

        mControllerManager = new ApiController();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.fui_phone_progress_dialog);
        builder.setCancelable(false);
        builder.setTitle("Loading, Please wait...");
        mDialog = builder.show();

        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            Call<DestinationModel> listCall = mControllerManager.getDestinationsService()
                                    .getAllPointsOfInterest(
                                            "", //enter API KEY HERE!
                                            mChosenCitySearch);//mChosenCitySearch

                            listCall.enqueue(new Callback<DestinationModel>() {
                                @Override
                                public void onResponse(Call<DestinationModel> call, Response<DestinationModel> response) {
                                    if (response.isSuccessful()) {

                                        Toast.makeText(getContext(), "OnResponse Successfull", Toast.LENGTH_SHORT).show();
                                        DestinationModel DestinationList = response.body();

                                        //pass object as points of interest are nested.
                                        mAdapter.addDestinations(DestinationList);

                                        //loop if request its already a arrayList note* we remove 1 from index as it will conflict with header on RecyclerView
                                        for (int i = 0; i < DestinationList.getPoints_of_interest().size() - 1; i++) {

                                            Log.e(TAG, response.message());

                                            mAdapter.addDestinations(DestinationList);
                                            //                                    DestinationModel destination = DestinationList.get(i);
                                            //                                    mAdapter.addDestinations(destination);
                                            //
                                            //add new objects
                                            //                                    mDestinationList.add(new DestinationModel(destination.getTitle(), destination.getThumbnailUrl()));
                                            //                                    stepDataManager.saveDestinationList(mDestinationList);
                                            mDestinationList.add(DestinationList);
                                            stepDataManager.saveDestinationList(mDestinationList);
                                        }
                                    } else {
                                        int sc = response.code();
                                        switch (sc) {
                                            case 400:
                                                Log.e("Error 400", "Bad Request");
                                                break;
                                            case 429:
                                                Log.e("Error 429", "Monthly Quota Exceeded ");
                                                Toast.makeText(getContext(), "Monthly Quota Exceeded ", Toast.LENGTH_LONG).show();
                                                break;
                                            case 404:
                                                Log.e("Error 404", "Not Found");
                                                break;
                                            case 504:
                                                Log.e("Error 504", "Timeout");
                                                Toast.makeText(getContext(), "Server Timeout, Please try again later", Toast.LENGTH_LONG).show();
                                                break;
                                            default:
                                                Log.e("Error", "Generic Error");
                                        }
                                    }
                                    mDialog.dismiss();
                                }

                                @Override
                                public void onFailure(Call<DestinationModel> call, Throwable t) {
                                    mDialog.dismiss();
                                    t.printStackTrace();
                                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                    mDialog.dismiss();
                }
            }, 2000L);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_help).setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }


}
