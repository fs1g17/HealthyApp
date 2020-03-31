package com.example.healthyz;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

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
}

//Room executes all queries on a separate thread. Then observed LiveData will notify the observer
// on the main thread when the data has changed. We need to not run the insert on the main thread,
// so we use the ExecutorService we created in the WordRoomDatabase to perform the insert on a
// background thread.