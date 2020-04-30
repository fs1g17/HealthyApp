package com.example.healthyz.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "hei_table")
public class HEIRecord {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "date")
    private String date;

    @NonNull
    @ColumnInfo(name = "hei_score")
    private String HEIScore;

    public HEIRecord(String date, String HEIScore){
        this.date = date;
        this.HEIScore = HEIScore;
    }

    public String getDate(){
        return date;
    }

    public String getHEIScore(){
        return HEIScore;
    }
}
