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

    private MealRepository mRepository;
    private LiveData<List<Meal>> mAllMeals;

    public MyViewModel (Application application) {
        super(application);
        mealCounter = 0;
        table = new HashMap<>();

        mRepository = new MealRepository(application);
        mAllMeals = mRepository.getAllMeals();
        initialise();
    }

    void initialise(){
        List<Meal> mealList = mAllMeals.getValue();
        if((mealList != null) && (!mealList.isEmpty())){
            for(Meal meal : mealList){
                int mealID = meal.getMealID();
                String[] foodList = meal.getMeal().split("\t");
                ArrayList<String> foods = new ArrayList<>();

                for(int i=0; i<foodList.length; i++){
                    foods.add(foodList[i]);
                }

                table.put(mealID,foods);
                if(mealID > mealCounter){
                    mealCounter = mealID + 1;
                }
            }
        }
    }

    LiveData<List<Meal>> getAllMeals(){
        return mAllMeals;
    }

    public void insert(Meal meal){
        mRepository.insert(meal);
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