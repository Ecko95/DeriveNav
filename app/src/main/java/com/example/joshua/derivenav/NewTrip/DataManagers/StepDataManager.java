package com.example.joshua.derivenav.NewTrip.DataManagers;

import com.example.joshua.derivenav.NewTrip.Models.DestinationModel;

import java.util.ArrayList;


public interface StepDataManager {

    //Initial step data test
    void saveStepData(String data);

    String getData();

    //Trip Title
    void saveTripTitle(String title);

    String getTripTitle();

    //Trip description
    void saveTripDesc(String description);

    String getDesc();

    void saveTripCategory(String category);

    String getCategory();

    //Array list of new Destination Objects
    void saveDestinationList(ArrayList<DestinationModel> newDestinationList);

    ArrayList<DestinationModel> getNewDestinationList();

}
