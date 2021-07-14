package com.palana.paachoodelivery;

import com.google.gson.JsonObject;


import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login")
    Call<Result> login(@Field("contact_no") String email,
                       @Field("password") String password);

    @FormUrlEncoded
    @POST("deliveryOrdersList")
    Call<Response> ordersList(@Header("Authorization") String token,
                              @Field("order_status") String orderStatus);
    @POST("delivery_boys_list")
    Call<Response> deliveryBoysList(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("orderedItemDetails")
    Call<Result> orderDetails(@Header("Authorization") String token,
                              @Field("order_id") String orderId);

    @FormUrlEncoded
    @POST("orderStatusUpdate")
    Call<Result> updateOrderStatus(@Header("Authorization") String token,
                                   @Field("order_id") String orderId,
                                   @Field("order_status") String orderStatus);

    @FormUrlEncoded
    @POST("delivery_boy_order")
    Call<Result> updateOrderBoy(@Header("Authorization") String token,
                                   @Field("order_id") String orderId,
                                   @Field("delivery_boy_id") String orderStatus);


}