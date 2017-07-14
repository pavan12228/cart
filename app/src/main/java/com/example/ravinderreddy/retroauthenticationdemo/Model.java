package com.example.ravinderreddy.retroauthenticationdemo;
public class Model
{
   private  String price;
   private String product_id;
   private String product_name;
   private String amount;
   private String image;
   private String shipping;
   private String UserId;

   public String getUserId() {
      return UserId;
   }

   public void setUserId(String userId) {
      UserId = userId;
   }

   public int getUser_selected_amount() {
      return user_selected_amount;
   }

   public void setUser_selected_amount(int user_selected_amount) {
      this.user_selected_amount = user_selected_amount;
   }

   private int user_selected_amount;

   public String getPrice() {
      return price;
   }

   public void setPrice(String price) {
      this.price = price;
   }

   public String getProduct_id() {
      return product_id;
   }

   public void setProduct_id(String product_id) {
      this.product_id = product_id;
   }

   public String getProduct_name() {
      return product_name;
   }

   public void setProduct_name(String product_name) {
      this.product_name = product_name;
   }

   public String getAmount() {
      return amount;
   }

   public void setAmount(String amount) {
      this.amount = amount;
   }

   public String getImage() {
      return image;
   }

   public void setImage(String image) {
      this.image = image;
   }

   public String getShipping() {
      return shipping;
   }

   public void setShipping(String shipping) {
      this.shipping = shipping;
   }
}
