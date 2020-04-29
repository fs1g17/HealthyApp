package com.example.healthyz;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyAPI {

    @GET("HEICalc")
    Call<HEIScore> getHEIScore(@Query("user_id") int userID, @Query("date") String date);

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
