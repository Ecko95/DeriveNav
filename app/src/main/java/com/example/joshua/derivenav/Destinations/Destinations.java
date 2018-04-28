package com.example.joshua.derivenav.Destinations;


import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import com.example.joshua.derivenav.R;

import android.widget.Toast;


import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Destinations#newInstance} factory method to
 * create an instance of this fragment.
 */


public class Destinations extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private RecyclerView recyclerView;

    // @BindView(R.id.recycler_view)
    // RecyclerView recyclerView;


    private DestinationAdapter mAdapter;

    private ArrayList<DestinationModel> modelList = new ArrayList<>();


    public Destinations() {
        // Required empty public constructor
    }



    public static Destinations newInstance() {
        Destinations fragment = new Destinations();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_destinations, container, false);

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


        mAdapter = new DestinationAdapter(getActivity(), modelList, "Header");


        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener(new DestinationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, DestinationModel model) {

                //handle item click events here
                Toast.makeText(getActivity(), "Hey " + model.getTitle(), Toast.LENGTH_SHORT).show();


            }
        });


        mAdapter.SetOnCheckedListener(new DestinationAdapter.OnCheckedListener() {
            @Override
            public void onChecked(View view, boolean isChecked, int position, DestinationModel model) {

                Toast.makeText(getActivity(), (isChecked ? "Checked " : "Unchecked ") + model.getTitle(), Toast.LENGTH_SHORT).show();


            }
        });


        mAdapter.SetOnHeaderClickListener(new DestinationAdapter.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(View view, String headerTitle) {

                //handle item click events here
                Toast.makeText(getActivity(), "Hey I am a header", Toast.LENGTH_SHORT).show();

            }
        });


    }

}
