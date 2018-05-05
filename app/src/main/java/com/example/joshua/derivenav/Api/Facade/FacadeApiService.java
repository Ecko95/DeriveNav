package com.example.joshua.derivenav.Api.Facade;

import com.example.joshua.derivenav.NewTrip.Models.DestinationModel;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Joshua on 03/05/2018.
 */

public interface FacadeApiService {

        // Use Integer instead of int so it can become nullable
        //gets points of interest repository
//        @GET("/v1.2/points-of-interest/yapq-search-text/")
//        Call<List<DestinationModel>> getAllPointsOfInterest(
//                @Query("apikey") String apikey,
//                @Query("city_name") String cityName
//        );

        //V 2.0
        @GET("/v1.2/points-of-interest/yapq-search-text/")
        Call<DestinationModel> getAllPointsOfInterest(
                @Query("apikey") String apikey,
                @Query("city_name") String cityName
        );
//        @GET("/users")
//        Call<List<DestinationModel>> getAllPointsOfInterest(
//                @Query("name") String Name
//        );

        //gets all photos
//        @GET("/photos")
//        Call<List<DestinationModel>> getAllPointsOfInterest(
////                @Query("title") String title
//        );


}
