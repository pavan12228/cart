package com.example.ravinderreddy.retroauthenticationdemo;

import android.util.Base64;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by Ravinder Reddy on 17-06-2017.
 */

public class ApiBuilder {
    public static Apiservice apiservice;

    public static Apiservice callApi(String middleUrl) {
        final String credentials = "phani141@gmail.com"+ ":" + "1pNql0g9saL9UoD19d6411T6G5X7GL6w";
        RestAdapter adapter = new  RestAdapter.Builder().
                setEndpoint(StringConstants.endurl+middleUrl)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        String string = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                        request.addHeader("Authorization", string);
                        request.addHeader("Accept", "application/json");
                    }
                })
                .build();
        return  adapter.create(Apiservice.class);
    }

}
