package com.andrewlaurien.imovies.service;

import com.andrewlaurien.imovies.model.Track;
import com.andrewlaurien.imovies.model.TrackModel;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface AppleService {

    @GET("search?")
    Call<TrackModel> getTrackResults(@Query("term") String key, @Query("country") String country, @Query("media") String media);


}
