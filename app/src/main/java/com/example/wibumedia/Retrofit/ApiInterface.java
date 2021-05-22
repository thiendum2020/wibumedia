package com.example.wibumedia.Retrofit;


import com.example.wibumedia.Models.JSONResponsePost;
import com.example.wibumedia.Models.JSONResponseUser;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    //    @GET("user")
//    Call<String> getUserLogin(@Query("username") String username, @Query("password") String password);
//
    @POST("login")
    Call<JSONResponseUser> getUserLogin(@Header("APIKEY") String key, @Query("username") String username, @Query("password") String password);

    //
//
    @GET("post")
    Call<JSONResponsePost> getPost(@Header("APIKEY") String key);

    @GET("post/user/{id}")
    Call<JSONResponsePost> getPostUserID(@Header("APIKEY") String key, @Path("id") String id);


    @POST("post")
    @Multipart
    Call<JSONResponsePost> addPost(@Header("APIKEY") String key, @Query("content") String content,
                                   @Part MultipartBody.Part file, @Query("user_id") String user_id);

    @PUT("post/{id}")
    Call<JSONResponsePost> updatePost(@Header("APIKEY") String key, @Path("id") String id, @Query("content") String content);
//
//
//    @GET("foods")
//    Call<JSONResponseFoods> getFoodDetail(@Query("_id") String id, @Header("api_key") String key);
//
//
//    @POST("orders")
//    @Headers("Content-type: application/json")
//    Call<ResponseModel> insertOrder(@Body Order orderJ, @Header("api_key") String key);
//
//    @POST("customers")
//    @FormUrlEncoded
//    Call<ResponseModel> insertCustomer(@Field("_id")String _id,
//                                       @Field("firstName")String firstName,
//                                       @Field("lastName")String lastname,
//                                       @Field("address")String address,
//                                       @Field("password")String pass,
//                                       @Field("isAdmin")boolean isAdmin,
//                                       @Header("api_key") String key);
//
//
//
//    @GET("orders")
//    Call<JSONResponseCustomerOrder> getListOrder(@Header("api_key") String key, @Query("customerid") String customerid);
//
//
//    @DELETE("orders/{id}")
//    Call<ResponseModel> deleteOrder(@Header("api_key") String key, @Path("id") String id);
//
//
//
//    @PUT("orders/{id}")
//    @Headers("Content-type: application/json")
//    Call<ResponseModel> updateOrder(@Header("api_key") String key, @Path("id") String id, @Body Order orderJ);
//
//
//    @GET("statistic")
//    Call<StaticalResponse> getStatistical(@Query("customerid") String id);
}
