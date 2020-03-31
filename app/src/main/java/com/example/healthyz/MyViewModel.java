package com.example.healthyz;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MyViewModel extends AndroidViewModel {
    private int mealCounter;
    private HashMap<Integer, ArrayList<String>> table;

    public MyViewModel (Application application) {
        super(application);
        mealCounter = 0;
        table = new HashMap<>();
    }

    public void createMeal(){
        ArrayList<String> foodList = new ArrayList<>();
        table.put(mealCounter,foodList);
    }

    public void incrementMealCounter(){
        mealCounter++;
    }

    public int getMealCounter(){
        return mealCounter;
    }

    public int getTableSize(){
        return table.size();
    }

    public Set<Integer> getTableKeySet(){
        return table.keySet();
    }

    public boolean isLastMealEmpty(){
        if(getTableSize() == 0){
            return false;
        }

        boolean empty = true;
        for(Integer i : table.keySet()){
            empty = table.get(i).isEmpty();
        }
        return empty;
    }

    public ArrayList<String> getFoodList(int meal){
        return table.get(meal);
    }

    public void addFood(int meal, String food){
        table.get(meal).add(food);
    }

    public void removeFood(int meal, String food){
        table.get(meal).remove(food);
        //DELETE EntryFragment if this is empty
    }

    public void removeMeal(int meal){
        table.remove(meal);
    }
}