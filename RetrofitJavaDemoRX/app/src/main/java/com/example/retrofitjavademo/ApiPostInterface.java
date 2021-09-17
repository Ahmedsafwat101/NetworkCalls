package com.example.retrofitjavademo;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiPostInterface {
    @GET("posts")
    Single<List<Post>> getPost(@Query("postId") String postId);
}
