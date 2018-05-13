package com.example.joshua.derivenav.Api.Facade;

import com.example.joshua.derivenav.NewTrip.Models.DestinationModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface FacadeApiService {

    // HTTP Retrofit Call to Amadeus yapq Api
        @GET("/v1.2/points-of-interest/yapq-search-text/")
        Call<DestinationModel> getAllPointsOfInterest(
                @Query("apikey") String apikey,
                @Query("city_name") String cityName
        );

//Testing Api Calls
//        @GET("/users")
//        Call<List<DestinationModel>> getAllPointsOfInterest(
//                @Query("name") String Name
//        );


//        @GET("/photos")
//        Call<List<DestinationModel>> getAllPointsOfInterest(
//               @Query("title") String title
//        );


}
