package com.example.healthyz.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HEIRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(HEIRecord heiRecord);

    @Query("DELETE FROM hei_table")
    void deleteAll();

    @Query("DELETE FROM hei_table WHERE date = :date")
    void deleteAllByDate(String date);

    @Query("SELECT * FROM hei_table WHERE date = :date")
    LiveData<List<HEIRecord>> getHEIRecordByDate(String date);

    //might have to remove LIMIT 1
    @Query("SELECT hei_score FROM hei_table WHERE date = :date LIMIT 1")
    LiveData<String> getHEIStringByDate(String date);
}
