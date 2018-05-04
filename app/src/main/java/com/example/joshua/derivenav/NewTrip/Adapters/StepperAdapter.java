package com.example.joshua.derivenav.NewTrip.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.joshua.derivenav.NewTrip.CityDestinationsFragment;
import com.example.joshua.derivenav.NewTrip.MapFragment;
import com.example.joshua.derivenav.NewTrip.StepFragment1;
import com.example.joshua.derivenav.NewTrip.StepFragment2;
import com.example.joshua.derivenav.NewTrip.StepFragment3;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;

/**
 * Created by Joshua on 26/04/2018.
 */

public class StepperAdapter extends AbstractFragmentStepAdapter {

    public StepperAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm, context);
    }

    @Override

    public int getCount() {
        return 3;
    }

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
