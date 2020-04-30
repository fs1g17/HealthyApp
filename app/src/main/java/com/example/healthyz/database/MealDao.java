package com.example.healthyz.database;

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

    @Query("DELETE FROM meal_table WHERE date = :date")
    void deleteAllByDate(String date);

    @Query("SELECT * from meal_table")
    LiveData<List<Meal>> getAllMeals();

    @Query("SELECT * from meal_table WHERE date = :date")
    LiveData<List<Meal>> getMealsByDate(String date);
}

//Room queries returning LiveData automatically run asynchronously on a background thread