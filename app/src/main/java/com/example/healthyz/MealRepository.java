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
    //TODO: fix dates
    void save(@NotNull HashMap<Integer, ArrayList<String>> table, String date){
        deleteByDate(date);
        Set<Integer> keySet = table.keySet();

        if(!keySet.isEmpty()){
            for(Integer mealID : keySet){
                ArrayList<String> foodList = table.get(mealID);

                if(foodList.isEmpty()){
                    //DO NOTHING
                }
                else if(foodList.size() == 1){
                    Meal meal = new Meal(date,mealID,foodList.get(0));
                    insert(meal);
                }
                else{
                    String foods = foodList.get(0);

                    for(int i=1; i<foodList.size(); i++){
                        foods = foods + "\t" + foodList.get(i);
                    }

                    Meal meal = new Meal(date,mealID,foods);
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

/*
*
List<Meal> loadedMeals = mealsByDate.getValue();

        for(Meal oldMeal : loadedMeals){
            int mealID = oldMeal.getMealID();

            if(getTableKeySet().contains(mealID)){
                //CHECK DIFFERENCES
                String[] oldFoodItems = oldMeal.getMeal().split("\t");
                ArrayList<String> newFoodItems = table.get(mealID);

                //IF THEY'RE NOT THE SAME SIZE, UPDATE
                if(oldFoodItems.length != newFoodItems.size()){
                    mRepository.update(currentDate,mealID,newFoodItems);
                }
                else {
                    boolean update = false;

                    for(String oldFoodItem : oldFoodItems){
                        if(!newFoodItems.contains(oldFoodItem)){
                            update = true;
                            break;
                        }
                    }

                    for(String newFoodItem : newFoodItems){
                        boolean contained = false;

                        for(String oldFoodItem : oldFoodItems){
                            if(newFoodItem.equals(oldFoodItem)){
                                contained = true;
                            }
                        }

                        if(contained){
                            update = true;
                            break;
                        }
                    }

                    if(update){
                        mRepository.update(currentDate,mealID,newFoodItems);
                    }
                }
                //ACHIEVABLE BY INTERSECTION AND UNION;

                //IF THERE ARE DIFFERENCES, UPDATE
                //IF THERE AREN'T ANY DIFFERENCES, DO NOT UPDATE
            }
            else {
                mRepository.deleteByDateAndMealID(currentDate,mealID);
            }
        }
*/