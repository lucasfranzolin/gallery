package com.challenge.code.gallery.api;

import com.challenge.code.gallery.models.Cat;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ApiService {
    @Headers("Authorization: Client-ID 1ceddedc03a5d71")
    @GET("3/gallery/search/?q=cats")
    Call<Cat> getCats();
}
