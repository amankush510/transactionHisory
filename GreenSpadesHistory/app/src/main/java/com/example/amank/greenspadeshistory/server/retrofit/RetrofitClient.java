package com.example.amank.greenspadeshistory.server.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by amank on 26-11-2017.
 */

public class RetrofitClient {
    public static final String URL = "http://35.154.241.44:5555/api/v1/greenspades/";
    private static Retrofit retrofit = null;


    public static ApiEndPoint getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiEndPoint.class);
    }
}
