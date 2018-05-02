package com.example.joshua.derivenav.com.joshua.api.service;

import com.example.joshua.derivenav.com.joshua.api.Facade.apiClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface apiService {

    @GET("/photos")
    Call<List<apiClient>> getAllPointsOfInterest();
}
