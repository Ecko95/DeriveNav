package com.example.joshua.derivenav.com.joshua.api.model.service;

import com.example.joshua.derivenav.com.joshua.api.model.Facade.apiClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface apiService {

    @GET("/photos")
    Call<List<apiClient>> getAllPointsOfInterest();
}
