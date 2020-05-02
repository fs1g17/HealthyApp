package com.example.healthyz.server;

public class DayOfEating {
    private int userID;
    private String date;
    private String jsonArrayOfMeals;

    public DayOfEating(int userID, String date, String jsonArrayOfMeals){
        this.userID = userID;
        this.date = date;
        this.jsonArrayOfMeals = jsonArrayOfMeals;
    }

    public int getUserID() {
        return userID;
    }

    public String getDate() {
        return date;
    }

    public String getJsonArrayOfMeals() {
        return jsonArrayOfMeals;
    }
}
