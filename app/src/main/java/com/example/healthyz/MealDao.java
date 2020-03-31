package com.example.healthyz;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MealDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Meal meal);

    @Query("DELETE FROM meal_table")
    void deleteAll();

    @Query("SELECT * from meal_table")
    LiveData<List<Meal>> getAllMeals();
}

//Room queries returning LiveData automatically run asynchronously on a background thread