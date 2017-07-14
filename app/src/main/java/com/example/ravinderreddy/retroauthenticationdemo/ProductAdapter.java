package com.example.ravinderreddy.retroauthenticationdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

/**
 * Created by Ravinder Reddy on 17-06-2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyviewHolder> {

    List<Model> modelList = null;
    Context context;
    TextView cart_count;

    public ProductAdapter(List<Model> modelList, TextView textView) {
        this.modelList = modelList;
        this.cart_count = textView;
    }

    public ProductAdapter(List<Model> modelList) {
        this.modelList = modelList;
    }

    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row, parent, false);
//        if(cart_count != null)
//        cart_count.setText("" + getcartCount());
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyviewHolder holder, final int position) {

        //  holder.productListImage.setImageResource();
        holder.productListCost.setText(modelList.get(position).getPrice());
        holder.productListProductInfo.setText(modelList.get(position).getProduct_name());
        Picasso.with(context).load("" + modelList.get(position).getImage()).into(holder.productListImage);
        holder.productListCounterValue.setText("" + modelList.get(position).getUser_selected_amount());
        String mprice=  modelList.get(position).getPrice();
        double mPrice= Double.parseDouble(mprice);
        double multiply= (int) (mPrice*modelList.get(position).getUser_selected_amount());
        String total= mPrice+"x"+modelList.get(position).getUser_selected_amount()+"="+multiply;
        holder.productListMultiply.setText(total);

        holder.productListPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (modelList.get(position).getUser_selected_amount()<=10) {
                    int productAmount = modelList.get(position).getUser_selected_amount();
                    productAmount++;
                    pushToCartApi(modelList.get(position));
                    modelList.get(position).setUser_selected_amount(productAmount);
                    notifyDataSetChanged();
                    if(cart_count != null)
//                    cart_count.setText("" + getcartCount());
                    getCartApi();
                }
            }
        });


        holder.productListMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(modelList.get(position).getUser_selected_amount()>0) {
                    int productamount = modelList.get(position).getUser_selected_amount();
                    productamount = productamount - 1;
                    removeToCartApi(modelList.get(position));
                    modelList.get(position).setUser_selected_amount(productamount);
                    getCartApi();
                    notifyDataSetChanged();

                }
            }
        });


    }

    private void removeToCartApi(Model model) {
        Apiservice apiservice = ApiBuilder.callApi("");
        JSONObject jsonObject= new JSONObject();


        try {
            jsonObject.put("user_id","1181");
            jsonObject.put("product_id",model.getProduct_id());
            jsonObject.put("amount",model.getUser_selected_amount()-1);
            jsonObject.put("price",model.getPrice());
            jsonObject.put("image_path",model.getImage());
            jsonObject.put("extra","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("Pavan", "pushToCartApi: "+jsonObject);
        TypedInput in = null;
        try {
            in = new TypedByteArray("application/json", jsonObject.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        apiservice.removeToCart("1181",in, new Callback<JsonArray>() {
            @Override
            public void success(JsonArray jsonArray, Response response) {
                Toast.makeText(context, "succestfully added to cart", Toast.LENGTH_SHORT).show();
                Log.d("pavan", "success: "+jsonArray.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(context, "failure"+error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("pavan",""+error.getMessage());

            }
        });




    }

    private void pushToCartApi(Model model) {


        Apiservice apiservice = ApiBuilder.callApi("");
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("user_id","1181");
            jsonObject.put("product_id",model.getProduct_id());
            jsonObject.put("amount","1");
            jsonObject.put("price",model.getPrice());
            jsonObject.put("image_path",model.getImage());
            jsonObject.put("extra","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("Pavan", "pushToCartApi: "+jsonObject);
        TypedInput in = null;
        try {
            in = new TypedByteArray("application/json", jsonObject.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        apiservice.postToCart(in, new Callback<JsonArray>() {
            @Override
            public void success(JsonArray jsonArray, Response response) {
                Toast.makeText(context, "succestfully added to cart", Toast.LENGTH_SHORT).show();
                Log.d("pavan", "success: "+jsonArray.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(context, "failure"+error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("pavan",""+error.getMessage());

            }
        });

 }


    public int getcartCount() {

        int cartCount = 0;
        for (int i = 0; i < modelList.size(); i++) {
            if (modelList.get(i).getUser_selected_amount() > 0) {
                cartCount++;
            }
        }

        return cartCount;
    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        private ImageView productListImage;
        private TextView productListProductInfo;
        private TextView productListCost,productListMultiply;
        private ImageView productListMinus;
        private EditText productListCounterValue;
        private ImageView productListPlus;

        public MyviewHolder(View itemView) {
            super(itemView);
            productListImage = (ImageView) itemView.findViewById(R.id.product_list_image);
            productListProductInfo = (TextView) itemView.findViewById(R.id.product_list_product_info);
            productListCost = (TextView) itemView.findViewById(R.id.product_list_cost);
            productListMinus = (ImageView) itemView.findViewById(R.id.product_list_minus);
            productListCounterValue = (EditText) itemView.findViewById(R.id.product_list_counter_value);
            productListPlus = (ImageView) itemView.findViewById(R.id.product_list_plus);
            productListMultiply= (TextView) itemView.findViewById(R.id.product_multiply);

        }


    }

    private void getCartApi() {


        Apiservice apiservice = ApiBuilder.callApi("");
        apiservice.getCart("1181", new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {
                Log.d("getCartApi", "" + jsonObject.toString());

                JsonObject jsonObject1 = jsonObject.getAsJsonObject();
                JsonArray jsonArray = jsonObject1.get("products").getAsJsonArray();
                cart_count.setText(""+jsonArray.size());


            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(context, "failure" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}
