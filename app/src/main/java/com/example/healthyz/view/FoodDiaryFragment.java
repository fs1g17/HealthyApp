package com.example.healthyz.view;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;

import com.example.healthyz.database.Meal;
import com.example.healthyz.viewmodel.MyViewModel;
import com.example.healthyz.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;
import java.util.Set;

public class FoodDiaryFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private View thisView;
    private Button datePicker;
    private ImageButton save;
    private ImageButton addEntry;
    private MyViewModel myViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.fragment_food_diary, container, false);
        return thisView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        save = thisView.findViewById(R.id.save);
        save.setOnClickListener(this);

        addEntry = thisView.findViewById(R.id.add_meal);
        addEntry.setOnClickListener(this);

        datePicker = thisView.findViewById(R.id.date_picker_button);
        datePicker.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myViewModel = new ViewModelProvider(getActivity()).get(MyViewModel.class);
        String date = myViewModel.getPrettyDate();

        datePicker.setText(date);
        initialise();
    }

    public Observer<List<Meal>> getObserver(){
        return new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> mealList) {
                if(myViewModel.getTableSize() == 0){
                    int highestMealID = 0;
                    for(Meal meal : mealList){
                        int mealID = meal.getMealID();

                        if(mealID > highestMealID){
                            highestMealID = mealID;
                        }

                        //"'" is replaced with "''" (MealRepository:64), here the action is reversed
                        String foodList = meal.getMeal().replace("''","'");
                        FoodDiaryFragment
                                .this
                                .getChildFragmentManager()
                                .beginTransaction()
                                .add(R.id.food_diary_container,
                                        MealFragment.restoreInstance(mealID,foodList))
                                .commit();

                        try{
                            JSONArray foodListJSONArray = new JSONArray(foodList);
                            myViewModel.addMeal(mealID,foodListJSONArray);
                        } catch (JSONException e){
                            //TODO: make a pop up dialog maybe?
                        }
                    }
                    myViewModel.setMealCounter(highestMealID + 1);
                }
            }
        };
    }


    public void initialise(){
        //we load the data into the ViewModel only once when the application loads as it is costly
        if(myViewModel.getTableSize() == 0){
            myViewModel.getAllMeals().observe(getViewLifecycleOwner(), getObserver());
        }
        else{
            Set<Integer> keySet = myViewModel.getTableKeySet();
            for(Integer i : keySet){
                JSONArray foodListJSONArray = myViewModel.getFoodList(i);

                if(foodListJSONArray.length() == 0){
                    //DO NOTHING
                }
                else{
                    getChildFragmentManager()
                            .beginTransaction()
                            .add(R.id.food_diary_container,
                                    MealFragment.restoreInstance(i,foodListJSONArray.toString()))
                            .commit();
                }

            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.add_meal){
            if(!myViewModel.isLastMealEmpty()){
                myViewModel.createMeal();
                int mealNumber = myViewModel.getMealCounter();
                getChildFragmentManager().beginTransaction()
                        .add(R.id.food_diary_container, MealFragment.newInstance(mealNumber))
                        .commit();
                myViewModel.incrementMealCounter();
            }
        }
        if(v.getId() == R.id.save){
            myViewModel.save();
        }
        if(v.getId() == R.id.date_picker_button){
            showDatePickerDialog();
        }
    }

    private void showDatePickerDialog(){
        int[] dayMonthYear = myViewModel.getDayMonthYear();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                R.style.MyDatePickerDialogTheme,
                this,
                dayMonthYear[2],
                dayMonthYear[1],
                dayMonthYear[0]);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        for(Fragment fragment : getChildFragmentManager().getFragments()){
            getChildFragmentManager().beginTransaction().remove(fragment).commit();
        }
        myViewModel.setDate(dayOfMonth,month,year);
        myViewModel.getAllMeals().observe(getViewLifecycleOwner(), getObserver());
        month++;
        String prettyDate = dayOfMonth + "/" + month + "/" + year;
        datePicker.setText(prettyDate);
    }
}
