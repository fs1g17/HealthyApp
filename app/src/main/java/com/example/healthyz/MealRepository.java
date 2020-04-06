package com.example.healthyz;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

class MealRepository {
    private MealDao mMealDao;
    private LiveData<List<Meal>> mAllMeals;

    MealRepository(Application application){
        MealRoomDatabase db = MealRoomDatabase.getDatabase(application);
        mMealDao = db.mealDao();
        mAllMeals = mMealDao.getAllMeals();
    }

    LiveData<List<Meal>> getAllMeals(){
        return mAllMeals;
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

    //By calling deleteAll() before saving again, we ensure that deletion works
    //TODO: fix dates
    void save(@NotNull HashMap<Integer, ArrayList<String>> table){
        deleteAll();
        Set<Integer> keySet = table.keySet();
        String today = "03042020";

        if(!keySet.isEmpty()){
            for(Integer mealID : keySet){
                ArrayList<String> foodList = table.get(mealID);

                if(foodList.isEmpty()){
                    //DO NOTHING
                }
                else if(foodList.size() == 1){
                    Meal meal = new Meal(today,mealID,foodList.get(0));
                    insert(meal);
                }
                else{
                    String foods = foodList.get(0);

                    for(int i=1; i<foodList.size(); i++){
                        foods = foods + "\t" + foodList.get(i);
                    }

                    Meal meal = new Meal(today,mealID,foods);
                    insert(meal);
                }
            }
        }
    }
}

//Room executes all queries on a separate thread. Then observed LiveData will notify the observer
// on the main thread when the data has changed. We need to not run the insert on the main thread,
// so we use the ExecutorService we created in the WordRoomDatabase to perform the insert on a
// background thread.