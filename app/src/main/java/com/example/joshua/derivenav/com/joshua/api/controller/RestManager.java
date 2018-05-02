/*
 * Copyright (c) 2015-2016 Filippo Engidashet. All Rights Reserved.
 * <p>
 *  Save to the extent permitted by law, you may not use, copy, modify,
 *  distribute or create derivative works of this material or any part
 *  of it without the prior written consent of Filippo Engidashet.
 *  <p>
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 */

package com.example.joshua.derivenav.com.joshua.api.controller;


import com.example.joshua.derivenav.com.joshua.api.service.apiService;
import com.example.joshua.derivenav.com.joshua.api.helper.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @date 1/22/2016
 */
public class RestManager {

    private apiService mApiService;

    public apiService getFlowerService() {
        if (mApiService == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.HTTP.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mApiService = retrofit.create(apiService.class);
        }
        return mApiService;
    }
}
