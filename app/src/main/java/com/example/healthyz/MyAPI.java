package com.example.healthyz;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyAPI {

    @GET("HEICalc")
    Call<HEIScore> getHEIScore(String userID, String date);

    @GET("TestServlet")
    Call<HEIScore> getTESTScore();
}
