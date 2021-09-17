package com.example.retrofitjavademo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiPostInterface {
    @GET("posts")
    public Call<List<Post>> getPost(@Query("postId") String postId);
}
