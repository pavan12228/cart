package com.example.ravinderreddy.retroauthenticationdemo;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedInput;

/**
 * Created by Ravinder Reddy on 17-06-2017.
 */

public interface Apiservice {
    @GET("/categories")
    void categories(Callback<JsonObject> callback);


    @GET("/products&status=A&items_per_page=30&page=2")
    public abstract void productsApi(@Query("q") String q, Callback<JsonObject> callback);


    @GET("/carts/{uid}")
    public  abstract  void getCart(@Path("uid") String Uid,Callback<JsonObject> callback);

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("/Addtocart")
    public void postToCart(@Body TypedInput typedInput, Callback<JsonArray> callback);




    @Headers("Content-Type: application/json;charset=UTF-8")
    @PUT("/Addtocart/{id}")
    public void removeToCart(@Path("id") String id,@Body TypedInput typedInput, Callback<JsonArray> callback);

}
