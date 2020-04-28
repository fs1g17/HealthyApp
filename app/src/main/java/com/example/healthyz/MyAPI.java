package com.example.healthyz;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyAPI {

    @GET("HEICalc")
    Call<HEIScore> getHEIScore(String userID, String date);

    @GET("TestServlet")
    Call<HEIScore> getTESTScore();

    @GET("getNewUserID")
    Call<UserID> getNewUserID();
}

class UserID {
    @SerializedName("user_id")
    private int userID;

    public int getUserID(){
        return userID;
    }
}
