package com.example.ravinderreddy.retroauthenticationdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.ravinderreddy.retroauthenticationdemo.R.id.cart_count;

public class ProductActvity extends AppCompatActivity implements View.OnClickListener {


    Apiservice apiservice;
    List<Model> modelList=new ArrayList<>();
    private RecyclerView recyclerView;
    ImageView imageView;
    TextView textView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView= (RecyclerView) findViewById(R.id.recycler);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        imageView= (ImageView)toolbar.findViewById(R.id.cart_iv);
        imageView.setOnClickListener(this);
        textView= (TextView) findViewById(cart_count);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        showProductsList();
    }

    private void showProductsList() {
        apiservice = ApiBuilder.callApi("");

        apiservice.productsApi("", new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {

                JsonObject jsonObject1 = jsonObject.getAsJsonObject();
                JsonArray jsonArray = jsonObject1.get("products").getAsJsonArray();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject jsonObject2 = jsonArray.get(i).getAsJsonObject();
                    String mproductId = jsonObject2.get("product_id").getAsString();
                    String productName = jsonObject2.get("product").getAsString();
                    String productPrice = jsonObject2.get("price").getAsString();
                    String productAmount = jsonObject2.get("amount").getAsString();
                    JsonObject jsonObject3 = null;
                    if (jsonObject2.has("main_pair")) {
                        jsonObject3 = jsonObject2.get("main_pair").getAsJsonObject();
                    } else {
                    }

                    String image=null;
                    if (jsonObject3!=null && jsonObject3.has("detailed")) {
                        JsonObject jsonObject4 = jsonObject3.get("detailed").getAsJsonObject();
                        image = jsonObject4.get("image_path").getAsString();
                    } else {
                    }
                    Model model=new Model();
                    model.setProduct_id(mproductId);
                    model.setProduct_name(productName);
                    model.setPrice(productPrice);
                    model.setAmount(productAmount);
                    model.setUser_selected_amount(0);

                    model.setImage(image);


                    modelList.add(model);






                }

            ProductAdapter productAdapter=new ProductAdapter(modelList,textView) ;
                recyclerView.setAdapter(productAdapter);
                getCartApi();

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {


            startActivity(new Intent(getApplicationContext(),CartActivity.class));


    }



    private void getCartApi() {


        Apiservice apiservice = ApiBuilder.callApi("");
        apiservice.getCart("1181", new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {
                Log.d("getCartApi", "" + jsonObject.toString());

                JsonObject jsonObject1 = jsonObject.getAsJsonObject();
                String amount = jsonObject1.get("cart_products").getAsString();
                textView.setText(""+amount);



            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(ProductActvity.this, "failure" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}
