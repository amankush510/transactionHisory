package com.example.amank.greenspadeshistory.server.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by amank on 26-11-2017.
 */

public interface ApiEndPoint {
    @GET("data")
    Call<ResponseBody> getData();
}
