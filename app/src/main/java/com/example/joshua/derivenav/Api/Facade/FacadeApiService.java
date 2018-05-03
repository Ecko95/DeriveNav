package com.example.joshua.derivenav.Api.Facade;

import com.example.joshua.derivenav.NewTrip.Models.DestinationModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Joshua on 03/05/2018.
 */

public interface FacadeApiService {

        // Use Integer instead of int so it can become nullable
        //gets photos repository
        @GET("/v1.2/points-of-interest/yapq-search-text/")
        Call<List<DestinationModel>> getAllPointsOfInterest(
                @Query("apikey") String apikey,
                @Query("city_name") String cityName,
                @Query("number_of_results") Integer numberOfResults
        );

}
