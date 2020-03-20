package com.andrewlaurien.imovies.viewmodel;

import com.andrewlaurien.imovies.model.Track;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class TrackViewModel extends ViewModel {

    private TrackRepository repository;
    LiveData<List<Track>> mTracks;

    public TrackViewModel() {
        this.repository = new TrackRepository();
    }


    public LiveData<List<Track>> getTracks(String key, String country, String media) {
        mTracks = repository.searchTask(key, country, media);
        return mTracks;
    }

}
