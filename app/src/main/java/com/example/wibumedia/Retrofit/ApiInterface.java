package com.example.wibumedia.Retrofit;


import com.example.wibumedia.Models.JSONResponseComment;
import com.example.wibumedia.Models.JSONResponseThongKe;
import com.example.wibumedia.Models.JSONResponsePost;
import com.example.wibumedia.Models.JSONResponseUser;


import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    // --------------------------------  API FOR USER   -----------------------------------------------------

    @POST("login")
    Call<JSONResponseUser> getUserLogin(@Header("APIKEY") String key, @Query("username") String username, @Query("password") String password);

    // ----- Create New USER ---------
    @POST("user")
    @Multipart
    Call<JSONResponseUser> addUser(@Header("APIKEY") String key,
                                   @Query("username") String username,  @Query("password") String password,
                                   @Query("name") String name,          @Query("email") String email,
                                   @Query("phone") String phone,        @Query("birthday") String birthday,
                                   @Part MultipartBody.Part file);

    @PUT("user/{id}")
    Call<JSONResponseUser> updateUser(@Header("APIKEY") String key, @Path("id") String id,
                                      @Query("password") String password,   @Query("name") String name,
                                      @Query("email") String email,         @Query("phone") String phone,
                                      @Query("birthday") String birthday);

    // --------------------------------  API FOR POST   -----------------------------------------------------
    @GET("post")
    Call<JSONResponsePost> getPost(@Header("APIKEY") String key);

//    @GET("post/user/{id}")
//    Call<JSONResponsePost> getPostUserID(@Header("APIKEY") String key, @Path("id") String id);

    @GET("post/profile/{username}")
    Call<JSONResponsePost> getPostUserByUsername(@Header("APIKEY") String key, @Path("username") String username);


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

    @GET("comment/post/{id}")
    Call<JSONResponseComment> getComment(@Header("APIKEY") String key, @Path("id") String id);

    @POST("comment")
    Call<JSONResponseComment> addComment(@Header("APIKEY") String key, @Query("content") String content, @Query("user_id") String user_id, @Query("post_id") String post_id);

    @PUT("comment/{id}")
    Call<JSONResponsePost> updateComment(@Header("APIKEY") String key, @Path("id") String id, @Query("content") String content);

    @GET("user/post/count")
    Call<JSONResponseThongKe> getThongKe(@Header("APIKEY") String key);
}
