package com.example.healthyz.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.util.ArrayList;

@Entity(tableName = "meal_table", primaryKeys = {"date","meal_id"})
public class Meal {
    @NonNull
    @ColumnInfo(name = "date")
    private String date;

    @NonNull
    @ColumnInfo(name = "meal_id")
    private int mealID;

    @NonNull
    @ColumnInfo(name = "meal")
    private String meal;

    public Meal(String date, int mealID, String meal){
        this.date = date;
        this.mealID = mealID;
        this.meal = meal;
    }

    public String getDate(){
        return date;
    }

    public int getMealID(){
        return mealID;
    }

    public String getMeal(){
        return meal;
    }
}
