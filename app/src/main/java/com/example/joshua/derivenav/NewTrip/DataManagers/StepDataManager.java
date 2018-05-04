package com.example.joshua.derivenav.NewTrip.DataManagers;

import com.example.joshua.derivenav.NewTrip.Models.DestinationModel;

import java.util.ArrayList;

/**
 * Created by Joshua on 26/04/2018.
 */

public interface StepDataManager {

    void saveStepData(String data);

    String getData();

    void saveDestinationModel(DestinationModel data);

    DestinationModel getDestinationModel();

    //Array list of new Destination Objects
    void saveDestinationList(ArrayList<DestinationModel> newDestinationList);

    ArrayList<DestinationModel> getNewDestinationList();

}
