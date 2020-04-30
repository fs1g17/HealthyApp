package com.example.healthyz;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

class MealRepository {
    private MealDao mMealDao;

    MealRepository(Application application){
        MealRoomDatabase db = MealRoomDatabase.getDatabase(application);
        mMealDao = db.mealDao();
    }

    LiveData<List<Meal>> getMealsByDate(String date){
        return mMealDao.getMealsByDate(date);
    }

    //Must call insert on a non-UI thread of the app
    void insert(Meal meal){
        MealRoomDatabase.databaseWriteExecutor.execute(() -> {
            mMealDao.insert(meal);
        });
    }

    void deleteAll(){
        MealRoomDatabase.databaseWriteExecutor.execute(() -> {
            mMealDao.deleteAll();
        });
    }

    void deleteByDate(String date){
        MealRoomDatabase.databaseWriteExecutor.execute(() -> {
            mMealDao.deleteAllByDate(date);
        });
    }

    //By calling deleteAll() before saving again, we ensure that deletion works
    //Removed any calls to other methods - this ensures that everything is done on one thread
    //TODO: fix dates
    void save(@NotNull HashMap<Integer, JSONArray> table, String date){
        MealRoomDatabase.databaseWriteExecutor.execute(() -> {
            mMealDao.deleteAllByDate(date);
            Set<Integer> keySet = table.keySet();

            if(!keySet.isEmpty()){
                for(Integer mealID : keySet){
                    JSONArray foodList = table.get(mealID);

                    if(foodList.length() == 0){
                        //DO NOTHING
                    }
                    else{
                        //the replace statment ensures everything is written to the database correctly
                        String foods = foodList.toString().replace("'","''");
                        Meal meal = new Meal(date,mealID,foods);
                        mMealDao.insert(meal);
                    }
                }
            }
        });
    }
}

//Room executes all queries on a separate thread. Then observed LiveData will notify the observer
// on the main thread when the data has changed. We need to not run the insert on the main thread,
// so we use the ExecutorService we created in the WordRoomDatabase to perform the insert on a
// background thread.

/*
void save(@NotNull HashMap<Integer, ArrayList<String>> table, String date){
        MealRoomDatabase.databaseWriteExecutor.execute(() -> {
            mMealDao.deleteAllByDate(date);
            Set<Integer> keySet = table.keySet();

            if(!keySet.isEmpty()){
                for(Integer mealID : keySet){
                    ArrayList<String> foodList = table.get(mealID);

                    if(foodList.isEmpty()){
                        //DO NOTHING
                    }
                    else if(foodList.size() == 1){
                        Meal meal = new Meal(date,mealID,foodList.get(0));
                        mMealDao.insert(meal);
                    }
                    else{
                        String foods = foodList.get(0);

                        for(int i=1; i<foodList.size(); i++){
                            foods = foods + "\t" + foodList.get(i);
                        }

                        Meal meal = new Meal(date,mealID,foods);
                        mMealDao.insert(meal);
                    }
                }
            }
        });
    }
 */