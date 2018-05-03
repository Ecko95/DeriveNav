package com.example.joshua.derivenav.Api;

import com.example.joshua.derivenav.Api.Facade.FacadeApiService;
import com.example.joshua.derivenav.com.joshua.api.helper.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Joshua on 03/05/2018.
 */

public class ApiController {

    private FacadeApiService mFacadeApiService;

    public FacadeApiService getDestinationsService() {
        if (mFacadeApiService == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.HTTP.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mFacadeApiService = retrofit.create(FacadeApiService.class);
        }
        return mFacadeApiService;
    }
}
