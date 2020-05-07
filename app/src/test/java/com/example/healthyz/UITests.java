package com.example.healthyz;

import android.content.Context;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import androidx.test.core.app.ApplicationProvider;
import com.example.healthyz.view.FoodFragment;
import com.example.healthyz.view.MealFragment;

import org.junit.Test;

public class UITests {
    private Context context = ApplicationProvider.getApplicationContext();

    @Test
    public void FoodFragmentIDCheck() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        int mealID = 10;
        FoodFragment ff = FoodFragment.newInstance(mealID);
        Class<?> foodFragmentClass = ff.getClass();
    }
}
