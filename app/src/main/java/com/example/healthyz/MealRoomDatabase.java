package com.example.healthyz;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Meal.class}, version = 1, exportSchema = false)
public abstract class MealRoomDatabase extends RoomDatabase {
    public abstract MealDao mealDao();
    private static volatile MealRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static MealRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (MealRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MealRoomDatabase.class,"meal_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                MealDao mealDao = INSTANCE.mealDao();
                Meal meal = new Meal("00000000",9898,"");
                mealDao.insert(meal);
            });
        }
    };
}
