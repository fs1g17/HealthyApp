package com.example.healthyz;

import android.content.Context;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import androidx.test.core.app.ApplicationProvider;
import com.example.healthyz.view.FoodFragment;
import com.example.healthyz.view.MealFragment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import android.content.Context;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class FoodFragmentTest {
    @Mock
    private Context context;

    /*
    @Test
    public void FoodFragmentIDCheck() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
        int mealID = 10;
        FoodFragment ff = FoodFragment.newInstance(mealID);
        Class<?> ffClass = ff.getClass();

        Field ffMealID = ffClass.getDeclaredField("mealNumber");
        ffMealID.setAccessible(true);
        //assert(ffMealID.get(ff)==(Integer)mealID);
        assertThat(ffMealID.get(ff)).isEqualTo((Integer)mealID);
    }
     */

    public void checkPlusButton()throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException{

    }
}