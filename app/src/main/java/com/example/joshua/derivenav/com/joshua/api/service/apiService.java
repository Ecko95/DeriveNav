package com.example.joshua.derivenav.com.joshua.api.service;

import com.example.joshua.derivenav.Constants;
import com.example.joshua.derivenav.com.joshua.api.Facade.apiClient;
import com.example.joshua.derivenav.com.joshua.api.model.City;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface apiService {
    //gets photos repository
    @GET("/photos")
    Call<List<apiClient>> getAllPointsOfInterest();
}
