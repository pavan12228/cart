package com.example.ravinderreddy.retroauthenticationdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CartActivity extends AppCompatActivity {

    private RecyclerView rvList;
    List<Model> modelList = new ArrayList<>();
    ProductAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        rvList = (RecyclerView) findViewById(R.id.recycler_view_cart);
        cartAdapter = new ProductAdapter(modelList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvList.setLayoutManager(layoutManager);
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.setAdapter(cartAdapter);

        getCartApi();


    }

    private void getCartApi() {


        Apiservice apiservice = ApiBuilder.callApi("");
        apiservice.getCart("1181", new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {
                Log.d("getCartApi", "" + jsonObject.toString());

                JsonObject jsonObject1 = jsonObject.getAsJsonObject();
                JsonArray jsonArray = jsonObject1.get("products").getAsJsonArray();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject jsonObject2 = jsonArray.get(i).getAsJsonObject();
                    if (jsonObject2 != null) {
                        String productName= null;
                        String price=null;
                        int userSelectedAmount=0;
                        if(jsonObject2.has("amount")){
                            userSelectedAmount = jsonObject2.get("amount").getAsInt();
                            productName = jsonObject2.get("product").getAsString();
                            price = jsonObject2.get("price").getAsString();
                            }
                        if (jsonObject2.has("extra")) try {
                            {
                                JsonObject jsonObject3 = jsonObject2.get("extra").getAsJsonObject();
                                if (jsonObject3 != null) {
                                    String uid = null;
                                    if (jsonObject3.has("user_id")) {
                                        uid = jsonObject3.get("user_id").getAsString();
                                    }
                                    String productId = null;
                                    if (jsonObject3.has("product_id")) {
                                        productId = jsonObject3.get("product_id").getAsString();
                                    }
                                    String image = null;
                                    if (jsonObject3.has("image_path")) {
                                        image = jsonObject3.get("image_path").getAsString();
                                    }
                                    Model model = new Model();
                                    model.setUserId(uid);
                                    model.setPrice(price);
                                    model.setProduct_name(productName);
                                    model.setProduct_id(productId);
                                    model.setUser_selected_amount(userSelectedAmount);
                                    model.setImage(image);
                                    modelList.add(model);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                    cartAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(CartActivity.this, "failure" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
