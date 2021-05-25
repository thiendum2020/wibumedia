package com.example.wibumedia.Retrofit;


import com.example.wibumedia.Models.JSONResponseComment;
import com.example.wibumedia.Models.JSONResponsePost;
import com.example.wibumedia.Models.JSONResponseUser;
import com.example.wibumedia.Models.Post;
import com.example.wibumedia.Models.User;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @POST("login")
    Call<JSONResponseUser> getUserLogin(@Header("APIKEY") String key, @Query("username") String username, @Query("password") String password);

    // --------------------------------  API FOR POST   -----------------------------------------------------
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


    @GET("post/{id}")
    Call<JSONResponsePost> getDetailPost(@Header("APIKEY") String key, @Path("id") String id);

    @DELETE("post/{id}")
    Call<JSONResponsePost> deletePost(@Header("APIKEY") String key, @Path("id") String id);

    // --------------------------------  API FOR COMMNENT   -----------------------------------------------------

    @GET("comment/{id}")
    Call<JSONResponseComment> getComment(@Header("APIKEY") String key, @Path("id") String id);

    @POST("comment")
    Call<JSONResponseComment> addComment(@Header("APIKEY") String key, @Query("content") String content, @Query("user_id") String user_id, @Query("post_id") String post_id);

}
