package com.example.joshua.derivenav.NewTrip.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import com.example.joshua.derivenav.NewTrip.CityDestinationsFragment;
import com.example.joshua.derivenav.NewTrip.MapFragment;
import com.example.joshua.derivenav.NewTrip.StepFragment1;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;

public class StepperAdapter extends AbstractFragmentStepAdapter {

    public StepperAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm, context);
    }

    @Override

    //returns the total number of stepper slides
    public int getCount() {
        return 3;
    }

    //step 1 = search, step 2 = cityDestination selection, step 3 = Map view
    @Override
    public Step createStep(int position) {

        switch (position){
            case 0:
                return StepFragment1.newInstance();
            case 1:
                return CityDestinationsFragment.newInstance();
            case 2:
                return MapFragment.newInstance();
            default:
                throw new IllegalArgumentException("Unsupported position: " + position);
        }

    }
}
