package com.example.joshua.derivenav.NewTrip;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joshua.derivenav.NewTripActivity;
import com.example.joshua.derivenav.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment1 extends Fragment {

    @BindView(R.id.txt_selected_search)
    TextView txt_selected_search;

    public StepFragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view = inflater.inflate(R.layout.fragment_step_fragment1, container, false);
        ButterKnife.bind(this,view);

        try{

            NewTripActivity activity = (NewTripActivity) getActivity();
            String myDataFromActivity = activity.getChosenSearch();
            txt_selected_search.setText(myDataFromActivity);

            return view;
//            String chosenSearch = getArguments().getString("chosenSearch");
//            txt_selected_search.setText(chosenSearch);

        }catch(Exception e){

        }

        //initialize your UI
        return view;






    }


    public void setOnClickListener(View.OnClickListener onClickListener) {
    }
}
