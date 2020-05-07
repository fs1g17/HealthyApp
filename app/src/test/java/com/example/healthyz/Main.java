package com.example.healthyz;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args){
        try{
            FoodFragmentTest ff = new FoodFragmentTest();
            ff.FoodFragmentIDCheck();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
