package com.example.healthyz.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.healthyz.database.HEIRecord;
import com.example.healthyz.database.Meal;
import com.example.healthyz.database.MealRepository;
import com.example.healthyz.server.HEIScore;
import com.example.healthyz.server.ServerRepository;
import com.example.healthyz.view.FoodFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MyViewModel extends AndroidViewModel {
    private int currentUserID;
    private String currentDate;
    private int day;
    private int month;
    private int year;
    private int mealCounter;
    private HashMap<Integer, JSONArray> table;

    private Repository repository;
    private LiveData<List<Meal>> mealsByDate;
    private LiveData<List<HEIRecord>> heiRecordByDate;
    private MutableLiveData<HEIScore> TEST;

    //TESTING
    //TODO: this is a test
    private LiveData<String> localHEI;
    private LiveData<String> remoteHEI;

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

        repository = new Repository(application);
        mealsByDate = repository.getMealsByDate(currentDate);
        heiRecordByDate = repository.getHEIRecordByDate(currentDate);
        TEST = repository.getTESTHEIScore();

        //FOR NOW ITS HARD CODED

        localHEI = repository.getHEIStringByDate(currentDate);
        remoteHEI = repository.getTESTString();

    }

    public void saveHEIScore(String retrievedScore){repository.insert(new HEIRecord(currentDate,retrievedScore));}

    public LiveData<String> getLocalHEI(){
        return localHEI;
    }

    public LiveData<String> getRemoteHEI(){
        return remoteHEI;
    }

    public LiveData<List<HEIRecord>> getCurrentHEIRecord(){
        return heiRecordByDate;
    }

    public void setUserID(int userID){
        currentUserID = userID;
    }

    public int getUserID(){
        return currentUserID;
    }

    public LiveData<List<Meal>> getUserInfo(){
        return repository.getMealsByDate("00000000");
    }

    public LiveData<HEIScore> getTESTScore(){
        return TEST;
    }

    //added synchronized keyword
    public synchronized void setDate(int day, int month, int year){
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

        mealsByDate = repository.getMealsByDate(currentDate);
    }

    public int[] getDayMonthYear(){
        return new int[] {day,month,year};
    }

    public String getPrettyDate(){
        return day + "/" + (month+1) + "/" + year;
    }

    public void save(){
        repository.saveMeals(table,currentDate);
    }

    //added synchronized keyword
    public synchronized void addMeal(int mealID, JSONArray foodList){
        table.put(mealID,foodList);
    }

    public void setMealCounter(int newValue){
        mealCounter = newValue;
    }

    public LiveData<List<Meal>> getAllMeals(){
        return mealsByDate;
    }

    public void insert(Meal meal){
        repository.insert(meal);
    }

    //added synchronized keyword
    public synchronized void createMeal(){
        JSONArray foodList = new JSONArray();
        table.put(mealCounter,foodList);
    }

    public void incrementMealCounter(){
        mealCounter++;
    }

    public int getMealCounter(){
        return mealCounter;
    }

    //added synchronized keyword
    public synchronized int getTableSize(){
        return table.size();
    }

    //added synchronized keyword
    public synchronized Set<Integer> getTableKeySet(){
        return table.keySet();
    }

    //added synchronized keyword
    public synchronized boolean isLastMealEmpty(){
        if(getTableSize() == 0){
            return false;
        }

        boolean empty = true;
        for(Integer i : table.keySet()){
            if(table.get(i).length() == 0){
                empty = true;
            } else {
                empty = false;
            }
        }
        return empty;
    }

    //added synchronized keyword
    public synchronized JSONArray getFoodList(int meal){
        return table.get(meal);
    }

    //added synchronized keyword
    public synchronized void addFood(int meal, JSONObject food){
        table.get(meal).put(food);
    }

    //added synchronized keyword
    public synchronized void removeFood(int meal, JSONObject food){
        JSONArray foodList = table.get(meal);
        for(int i=0; i<foodList.length(); i++){
            try{
                JSONObject jo = foodList.getJSONObject(i);
                String joFood = jo.getString(FoodFragment.JSONFOOD);
                String joSalt = jo.getString(FoodFragment.JSONSALT);
                String joKCal = jo.getString(FoodFragment.JSONKCAL);

                String foodFood = food.getString(FoodFragment.JSONFOOD);
                String foodSalt = food.getString(FoodFragment.JSONSALT);
                String foodKCal = food.getString(FoodFragment.JSONKCAL);

                if(joFood.equals(foodFood) && joSalt.equals(foodSalt) && joKCal.equals(foodKCal)){
                    foodList.remove(i);
                }
            } catch (JSONException e){
                //TODO: implement error popup
            }
        }
    }

    //added synchronized keyword
    public synchronized void removeMeal(int meal){
        table.remove(meal);
    }
}