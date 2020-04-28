package com.example.healthyz;

import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
}

/*
    public LiveData<double[]> getTESTHEIScore(){
        MutableLiveData<double[]> liveTESTHEIScore = new MutableLiveData<>();
        Call<HEIScore> call = myAPI.getTESTScore();
        call.enqueue(new Callback<com.example.healthyz.HEIScore>() {
            @Override
            public void onResponse(Call<HEIScore> call, Response<HEIScore> response) {
                if(!response.isSuccessful()){
                    return;
                }

                com.example.healthyz.HEIScore loadedHEIScore = response.body();

                double[] TESTHEIScore = new double[14];
                TESTHEIScore[0] = loadedHEIScore.getFatty_acids();
                TESTHEIScore[1] = loadedHEIScore.getTotal_fruits();
                TESTHEIScore[2] = loadedHEIScore.getWhole_fruits();
                TESTHEIScore[3] = loadedHEIScore.getTotal_vegies();
                TESTHEIScore[4] = loadedHEIScore.getGreens_beans();
                TESTHEIScore[5] = loadedHEIScore.getWhole_grains();
                TESTHEIScore[6] = loadedHEIScore.getDairy_things();
                TESTHEIScore[7] = loadedHEIScore.getProtein_food();
                TESTHEIScore[8] = loadedHEIScore.getSeas_plan_pr();
                TESTHEIScore[9] = loadedHEIScore.getAdded_sugars();
                TESTHEIScore[10] = loadedHEIScore.getRefined_grain();
                TESTHEIScore[11] = loadedHEIScore.getActual_sodium();
                TESTHEIScore[12] = loadedHEIScore.getSaturated_fats();
                TESTHEIScore[13] = loadedHEIScore.getEstimated_sodium();
                liveTESTHEIScore.postValue(TESTHEIScore);
            }

            @Override
            public void onFailure(Call<com.example.healthyz.HEIScore> call, Throwable t) {
                double[] TESTHEIScore = new double[14];
                TESTHEIScore[0] = -1.0;
                TESTHEIScore[1] = -1.0;
                TESTHEIScore[2] = -1.0;
                TESTHEIScore[3] = -1.0;
                TESTHEIScore[4] = -1.0;
                TESTHEIScore[5] = -1.0;
                TESTHEIScore[6] = -1.0;
                TESTHEIScore[7] = -1.0;
                TESTHEIScore[8] = -1.0;
                TESTHEIScore[9] = -1.0;
                TESTHEIScore[10] = -1.0;
                TESTHEIScore[11] = -1.0;
                TESTHEIScore[12] = -1.0;
                TESTHEIScore[13] = -1.0;
                liveTESTHEIScore.postValue(TESTHEIScore);
            }
        });
        return liveTESTHEIScore;
    }
 */