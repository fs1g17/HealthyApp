package com.example.healthyz.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.healthyz.database.HEIRecord;
import com.example.healthyz.database.HEIRecordDao;
import com.example.healthyz.database.Meal;
import com.example.healthyz.database.MealDao;
import com.example.healthyz.database.MealRepository;
import com.example.healthyz.database.MealRoomDatabase;
import com.example.healthyz.server.HEIScore;
import com.example.healthyz.server.ServerRepository;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Repository {
    private MealRepository mRepository;
    private ServerRepository sRepository;
    private static final String F_TOTAL = "total_fruits";
    private static final String F_WHOLE = "whole_fruits";
    private static final String V_TOTAL = "total_vegies";
    private static final String V_GREEN = "greens_beans";
    private static final String G_WHOLE = "whole_grains";
    private static final String D_TOTAL = "dairy_things";
    private static final String PF_TOTAL = "protein_food";
    private static final String PF_SEA_PLANT = "seas_plan_pr";
    private static final String FA = "fatty_acids";
    private static final String G_REFINED = "refined_grain";
    private static final String NA_EST = "estimated_sodium";
    private static final String NA_ACT ="actual_sodium";
    private static final String ADD_SUGARS = "added_sugars";
    private static final String SAT_FATS = "saturated_fats";

    public Repository(Application application){
        mRepository = new MealRepository(application);
        sRepository = ServerRepository.getInstance();
    }

    //LOCAL: getting meals
    public LiveData<List<Meal>> getMealsByDate(String date){return mRepository.getMealsByDate(date);}
    public void insert(Meal meal){mRepository.insert(meal);}
    public void deleteAllMeals(){mRepository.deleteAll();}
    public void deleteAllMealsByDate(String date){mRepository.deleteByDate(date);}
    public void saveMeals(@NotNull HashMap<Integer, JSONArray> table, String date){mRepository.save(table,date);}

    //LOCAL: getting HEI records
    public LiveData<List<HEIRecord>> getHEIRecordByDate(String date) {return mRepository.getHEIRecordByDate(date);}
    public void insert(HEIRecord heiRecord){mRepository.insert(heiRecord);}
    public void deleteAllHEIRecords(){mRepository.deleteAllHEIRecords();}
    public void deleteHEIRecordByDate(String date){mRepository.deleteHEIRecordByDate(date);}

    //REMOTE: getting test score
    public MutableLiveData<HEIScore> getTESTHEIScore(){return sRepository.getTESTHEIScore();}
}
