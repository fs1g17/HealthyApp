package com.example.healthyz;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MyViewModel extends AndroidViewModel {
    private String currentDate;
    private int day;
    private int month;
    private int year;
    private int mealCounter;
    private HashMap<Integer, ArrayList<String>> table;

    private MealRepository mRepository;
    private LiveData<List<Meal>> mealsByDate;

    public MyViewModel (Application application) {
        super(application);
        mealCounter = 0;
        table = new HashMap<>();

        Calendar c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);

        if(day < 10){
            if(month < 10){
                currentDate = "0" + day + "0" + month + year;
            }
            else{
                currentDate = "0" + day + month + year;
            }
        } else{
            if(month < 10){
                currentDate = day + "0" + month + year;
            }
            else{
                currentDate = day + month + year + "";
            }
        }

        mRepository = new MealRepository(application);
        mealsByDate = mRepository.getMealsByDate(currentDate);
    }

    public void setDate(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;

        String date;
        if(day < 10){
            if(month < 10){
                date = "0" + day + "0" + month + year;
            }
            else{
                date = "0" + day + month + year;
            }
        } else{
            if(month < 10){
                date = day + "0" + month + year;
            }
            else{
                date = day + month + year + "";
            }
        }

        currentDate = date;
        mealCounter = 0;
        table = new HashMap<>();

        mealsByDate = mRepository.getMealsByDate(currentDate);
    }

    public int[] getDayMonthYear(){
        return new int[] {day,month,year};
    }

    public String getPrettyDate(){
        return day + "/" + (month+1) + "/" + year;
    }

    /*
    public void save(){
        mRepository.save(table, currentDate);
    }
     */

    public void save(){
        mRepository.save(table,currentDate);
    }

    public void addMeal(int mealID, String[] foods){
        ArrayList<String> foodList = new ArrayList<>();

        for(String food : foods){
            foodList.add(food);
        }

        table.put(mealID,foodList);
    }

    public void setMealCounter(int newValue){
        mealCounter = newValue;
    }

    LiveData<List<Meal>> getAllMeals(){
        return mealsByDate;
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