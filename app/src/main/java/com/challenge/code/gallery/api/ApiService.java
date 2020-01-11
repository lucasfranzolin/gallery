package com.challenge.code.gallery.api;

import com.challenge.code.gallery.models.Cat;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET
    Call<List<Cat>> getCats();
}
