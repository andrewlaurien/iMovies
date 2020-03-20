package com.andrewlaurien.imovies.service;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebClient {

    //https://itunes.apple.com/search?term=star&amp;country=au&amp;media=movie&amp;all
    private static String BASE_URL = "https://itunes.apple.com/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient.addInterceptor(httpLoggingInterceptor);


        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient.build()).addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;
    }
}
