package com.example.healthyz.server;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MyAPI {

    @GET("HEICalc")
    Call<HEIScore> getHEIScore(@Query("user_id") int userID, @Query("date") String date);

    @GET("HEICalc")
    Call<String> getHEIString(@Query("user_id") int userID, @Query("date") String date);

    @GET("TestServlet")
    Call<HEIScore> getTESTScore();

    @GET("getNewUserID")
    Call<UserID> getNewUserID();

    /*
    @GET("getStringHEI")
    Call<String> getTESTString();
     */

    /* DO NOT DELETE TODO: this is super important
    @GET("TestServlet")
    Call<String> getTESTString();
    */

    @GET("HEICalc")
    Call<String> getTESTString(@Query("userID") int userID, @Query("date") String date);

    @POST("upload")
    Call<String> upload(@Body DayOfEating doe);

    @FormUrlEncoded
    @POST("upload")
    Call<String> upload(
            @Field("userID") int userID,
            @Field("date") String date,
            @Field("foodList") String foodList
    );

    @GET("recommend")
    Call<String> getRecommendations(@Query("userID") int userID, @Query("date") String date);
}

class UserID {
    @SerializedName("user_id")
    private int userID;

    public int getUserID(){
        return userID;
    }
}
