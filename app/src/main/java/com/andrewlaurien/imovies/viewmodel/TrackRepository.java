package com.andrewlaurien.imovies.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.andrewlaurien.imovies.model.Track;
import com.andrewlaurien.imovies.model.TrackModel;
import com.andrewlaurien.imovies.service.AppleService;
import com.andrewlaurien.imovies.service.WebClient;
import com.google.gson.Gson;

import java.util.List;
import java.util.prefs.Preferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackRepository {

    private AppleService webClient;


    public TrackRepository() {

        webClient = WebClient.getClient().create(AppleService.class);
    }


    /**
     *  Send a Http Request  to get the
     *  result from the Apple API
     *  once the http request has already a returned
     *  The observer will be invoked resulting in populating
     *  the recyclerview
     *
     * @param key provided by the user
     * @param country hardcoded as of the moment
     * @param media hardcoded as of the moment
     * @return
     */
    public LiveData<List<Track>> searchTask(String key, String country, String media) {

        final MutableLiveData<List<Track>> data = new MutableLiveData<>();


        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("term", key)
                .add("country", "au")
                .add("media", "movie");

        RequestBody formBody = formBuilder.build();


        Call<TrackModel> trackModelCall = webClient.getTrackResults(key, country, media);
        trackModelCall.enqueue(new Callback<TrackModel>() {
            @Override
            public void onResponse(Call<TrackModel> call, Response<TrackModel> response) {
                Log.d("Response",""+response.body().toString());
                data.postValue(response.body().getTracks());
            }

            @Override
            public void onFailure(Call<TrackModel> call, Throwable t) {
            }
        });

        return data;
    }
}
