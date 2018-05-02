package com.example.joshua.derivenav.NewTrip;


import android.app.AlertDialog;
import android.os.Bundle;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import com.example.joshua.derivenav.NewTrip.Adapters.DestinationsRecyclerViewAdapter;
import com.example.joshua.derivenav.NewTrip.DataManagers.StepDataManager;
import com.example.joshua.derivenav.NewTrip.Models.DestinationModel;
import com.example.joshua.derivenav.R;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import android.widget.Toast;


import android.view.ViewGroup;


public class CityDestinationsFragment extends Fragment implements BlockingStep {



    private RecyclerView recyclerView;
    private StepDataManager stepDataManager;
    private AlertDialog dialog;

    // @BindView(R.id.recycler_view)
    // RecyclerView recyclerView;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_city_destinations, container, false);

        // ButterKnife.bind(this);
        findViews(view);

        return view;

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setAdapter();


    }


    private void findViews(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    }


    private void setAdapter() {


        modelList.add(new DestinationModel("Android", "Hello " + " Android"));
        modelList.add(new DestinationModel("Beta", "Hello " + " Beta"));
        modelList.add(new DestinationModel("Cupcake", "Hello " + " Cupcake"));
        modelList.add(new DestinationModel("Donut", "Hello " + " Donut"));
        modelList.add(new DestinationModel("Eclair", "Hello " + " Eclair"));
        modelList.add(new DestinationModel("Froyo", "Hello " + " Froyo"));
        modelList.add(new DestinationModel("Gingerbread", "Hello " + " Gingerbread"));
        modelList.add(new DestinationModel("Honeycomb", "Hello " + " Honeycomb"));
        modelList.add(new DestinationModel("Ice Cream Sandwich", "Hello " + " Ice Cream Sandwich"));
        modelList.add(new DestinationModel("Jelly Bean", "Hello " + " Jelly Bean"));
        modelList.add(new DestinationModel("KitKat", "Hello " + " KitKat"));
        modelList.add(new DestinationModel("Lollipop", "Hello " + " Lollipop"));
        modelList.add(new DestinationModel("Marshmallow", "Hello " + " Marshmallow"));
        modelList.add(new DestinationModel("Nougat", "Hello " + " Nougat"));
        modelList.add(new DestinationModel("Android O", "Hello " + " Android O"));


        mAdapter = new DestinationsRecyclerViewAdapter(getActivity(), modelList, "Header");


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
        dialog = builder.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
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
}
