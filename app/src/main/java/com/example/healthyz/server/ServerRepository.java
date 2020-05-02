package com.example.healthyz.server;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ServerRepository {
    private MyAPI myAPI;
    private Retrofit retrofit;
    private static ServerRepository serverRepository;

    public static ServerRepository getInstance(){
        if(serverRepository == null){
            serverRepository = new ServerRepository();
        }
        return serverRepository;
    }

    public ServerRepository(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.36:8069/HealthyZBackend_war/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myAPI = retrofit.create(MyAPI.class);
    }

    public MutableLiveData<HEIScore> getTESTHEIScore(){
        MutableLiveData<HEIScore> TEST = new MutableLiveData<>();
        myAPI.getTESTScore().enqueue(new Callback<HEIScore>() {
            @Override
            public void onResponse(Call<HEIScore> call, Response<HEIScore> response) {
                if(response.isSuccessful()){
                    TEST.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<HEIScore> call, Throwable t) {
                TEST.setValue(null);
            }
        });
        return TEST;
    }

    public LiveData<String> getTESTString(){
        MutableLiveData<String> TEST = new MutableLiveData<>();
        myAPI.getTESTString().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    TEST.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                TEST.setValue(null);
            }
        });
        return TEST;
    }
}