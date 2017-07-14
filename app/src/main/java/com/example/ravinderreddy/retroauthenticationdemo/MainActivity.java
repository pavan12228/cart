package com.example.ravinderreddy.retroauthenticationdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;

import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {
   public Apiservice apiservice;
    private String TAG = "PavanMA";
    public static final String ADDITIONAL_URL = "products&status=A&items_per_page=30&page=";
    RecyclerView  recyclerView;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Apiservice apiservice = ApiBuilder.callApi("");

        apiservice.categories(new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {
                Log.d(TAG, "success: "+jsonObject.toString());






                 }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "failure: "+error.getMessage());
            }
        });


    }



    private void callApi() {
        final String credentials = "phani141@gmail.com"+ ":" + "1pNql0g9saL9UoD19d6411T6G5X7GL6w";
        RestAdapter adapter = new  RestAdapter.Builder().
                setEndpoint(StringConstants.endurl)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        String string = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                        request.addHeader("Authorization", string);
                        request.addHeader("Accept", "application/json");
                    }
                })
                .build();
        apiservice = adapter.create(Apiservice.class);
    }


}
