package com.example.joshua.derivenav.Api;

import com.example.joshua.derivenav.Api.Facade.FacadeApiService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Joshua on 03/05/2018.
 */

public class ApiController {

    private FacadeApiService mFacadeApiService;
    OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

    public FacadeApiService getDestinationsService() {
        okHttpClient.addInterceptor(loggingInterceptor);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mFacadeApiService == null) {

            Retrofit.Builder retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.HTTP.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient.build());


            mFacadeApiService = retrofit.build().create(FacadeApiService.class);
        }
        return mFacadeApiService;
    }
}
