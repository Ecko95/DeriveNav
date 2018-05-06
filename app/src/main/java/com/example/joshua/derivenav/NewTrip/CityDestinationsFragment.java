package com.example.joshua.derivenav.NewTrip;


import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;
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
    private DestinationModel mNewDestinationModel = new DestinationModel();
    private static final String TAG = "CityDestinationsFragmen";

    private boolean isItemsChecked = false;

    // @BindView(R.id.recycler_view)
    // RecyclerView recyclerView;
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

        ButterKnife.bind(this, view);
        //findViews(view);

        try{

            NewTripActivity activity = (NewTripActivity) getActivity();
            String myDataFromActivity = activity.getChosenSearch();
            mChosenCitySearch = myDataFromActivity.toString();

            Toast.makeText(activity, mChosenCitySearch, Toast.LENGTH_SHORT).show();

            //disable for test only
            //get feed from AMADEUS API




        }catch(Exception e){

            Toast.makeText(getActivity(), "Nothing", Toast.LENGTH_SHORT).show();

        }



        return view;

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setAdapter();


    }


//    private void findViews(View view) {
//
//        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
//    }


    private void setAdapter() {


        //add test data
        //enable for test only
//        modelList.add(new DestinationModel("Android", "Hello " + " Android"));
//        modelList.add(new DestinationModel("Beta", "Hello " + " Beta"));
//        modelList.add(new DestinationModel("Cupcake", "Hello " + " Cupcake"));
//        modelList.add(new DestinationModel("Donut", "Hello " + " Donut"));
//        modelList.add(new DestinationModel("Eclair", "Hello " + " Eclair"));
//        modelList.add(new DestinationModel("Froyo", "Hello " + " Froyo"));
//        modelList.add(new DestinationModel("Gingerbread", "Hello " + " Gingerbread"));
//        modelList.add(new DestinationModel("Honeycomb", "Hello " + " Honeycomb"));
//        modelList.add(new DestinationModel("Ice Cream Sandwich", "Hello " + " Ice Cream Sandwich"));
//        modelList.add(new DestinationModel("Jelly Bean", "Hello " + " Jelly Bean"));
//        modelList.add(new DestinationModel("KitKat", "Hello " + " KitKat"));
//        modelList.add(new DestinationModel("Lollipop", "Hello " + " Lollipop"));
//        modelList.add(new DestinationModel("Marshmallow", "Hello " + " Marshmallow"));
//        modelList.add(new DestinationModel("Nougat", "Hello " + " Nougat"));
//        modelList.add(new DestinationModel("Android O", "Hello " + " Android O"));


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
                Toast.makeText(getActivity(), "Hey " + model.getTitle(), Toast.LENGTH_SHORT).show();


            }
        });


        mAdapter.SetOnCheckedListener(new DestinationsRecyclerViewAdapter.OnCheckedListener() {

            @Override
            public void onChecked(View view, boolean isChecked, int position, DestinationModel model) {

                //only pass list if users check items

                if (isChecked) {
                    isItemsChecked = true;
                    mCheckedDestinationList.add(model);
                    //Toast.makeText(getContext(), "list will not be cleared", Toast.LENGTH_SHORT).show();
                } else {


                    //mDestinationList.clear();

                }


                Toast.makeText(getActivity(), (isChecked ? "Checked " : "Unchecked ") + model.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });


        mAdapter.SetOnHeaderClickListener(new DestinationsRecyclerViewAdapter.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(View view, String headerTitle) {

                //handle item click events here
                Toast.makeText(getActivity(), "Hey I am a header", Toast.LENGTH_SHORT).show();

            }
        });


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

//                mDestinationList.add(new DestinationModel("Android", "Hello Android"));
//                mNewDestinationModel.setName(mChosenCitySearch);
//                mNewDestinationModel.setAddress(););

                //list
//                stepDataManager.saveDestinationList(newDestinationList);
                //string
//                stepDataManager.saveStepData(mChosenCitySearch);
                //object
//                    stepDataManager.saveDestinationModel(mNewDestinationModel);
                if (isItemsChecked) {
                    //proceed with all the list of destinations
                    mAdapter.updateList(mCheckedDestinationList);
                    stepDataManager.saveDestinationList(mCheckedDestinationList);

                } else {
                    //clear the destination list when users dont select at least one value
                    mDestinationList.clear();
                }


                callback.goToNextStep();
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
        Toast.makeText(getContext(), stepDataManager.getData(), Toast.LENGTH_SHORT).show();
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
                                            "s4beFzBDafeBP1KjVQUWoNnMPoGID9w7", //enter API KEY HERE!
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
//                                    mChosenCitySearch);//mChosenCitySearch
//
//                    listCall.enqueue(new Callback<List<DestinationModel>>() {
//                        @Override
//                        public void onResponse(Call<List<DestinationModel>> call, Response<List<DestinationModel>> response) {
//                            if (response.isSuccessful()) {
//
//                                Toast.makeText(getContext(), "OnResponse Successfull", Toast.LENGTH_SHORT).show();
//                                List<DestinationModel> DestinationList = response.body();
//
//                                for (int i = 0; i < DestinationList.size(); i++) {
//                                    DestinationModel destination = DestinationList.get(i);
//                                    mAdapter.addDestinations(destination);
//
//                                    //add new objects
////                                    mDestinationList.add(new DestinationModel(destination.getTitle(), destination.getThumbnailUrl()));
////                                    stepDataManager.saveDestinationList(mDestinationList);
//                                      mDestinationList.add(new DestinationModel(destination.getCurrent_city().getName(),destination.getCurrent_city().getLocation().getGoogle_maps_link()));
//                                      stepDataManager.saveDestinationList(mDestinationList);
//                                }
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
