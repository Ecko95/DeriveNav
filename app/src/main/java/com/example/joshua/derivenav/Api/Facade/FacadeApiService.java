package com.example.joshua.derivenav.Api.Facade;

import com.example.joshua.derivenav.Api.Models.CityDestinationsModel;
import com.example.joshua.derivenav.NewTrip.Models.DestinationModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Joshua on 03/05/2018.
 */

public interface FacadeApiService {

        //gets photos repository
        @GET("/photos")
        Call<List<DestinationModel>> getAllPointsOfInterest();

}
