package com.andrewlaurien.imovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

//import androidx.room.Entity;
//
//@Entity
public class Track  {

//    @PrimaryKey(autoGenerate = true)
//    private int id;

    @SerializedName("trackId")
    private Integer trackId;
    @SerializedName("trackName")
    private String trackName;
    @SerializedName("artworkUrl100")
    private String artworkUrl100;
    @SerializedName("artworkUrl60")
    private String artworkUrl60;
    @SerializedName("trackPrice")
    private Double trackPrice;
    @SerializedName("primaryGenreName")
    private String primaryGenreName;
    @SerializedName("longDescription")
    private String longDescription;

    public Integer getTrackId() {
        return trackId;
    }

    public void setTrackId(Integer trackId) {
        this.trackId = trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }

    public String getArtworkUrl60() {
        return artworkUrl60;
    }

    public void setArtworkUrl60(String artworkUrl60) {
        this.artworkUrl60 = artworkUrl60;
    }

    public Double getTrackPrice() {
        return trackPrice;
    }

    public void setTrackPrice(Double trackPrice) {
        this.trackPrice = trackPrice;
    }

    public String getPrimaryGenreName() {
        return primaryGenreName;
    }

    public void setPrimaryGenreName(String primaryGenreName) {
        this.primaryGenreName = primaryGenreName;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }
}
