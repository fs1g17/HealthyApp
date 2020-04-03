package com.example.healthyz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FoodDiaryFragment extends Fragment implements View.OnClickListener {
    private View thisView;
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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myViewModel = new ViewModelProvider(getActivity()).get(MyViewModel.class);
        initialise();
    }


    public void initialise(){
        //we load the data into the ViewModel only once when the application loads as it is costly
        if(myViewModel.getTableSize() == 0){
            myViewModel.getAllMeals().observe(requireActivity(), mAllMeals -> {
                if(myViewModel.getTableSize() == 0){
                    int highestMealID = 0;
                    for(Meal meal : mAllMeals){
                        int mealID = meal.getMealID();

                        if(mealID > highestMealID){
                            highestMealID = mealID;
                        }

                        String foodList = meal.getMeal();
                        FoodDiaryFragment
                                .this
                                .getChildFragmentManager()
                                .beginTransaction()
                                .add(R.id.food_diary_container,
                                        MealFragment.restoreInstance(mealID,foodList))
                                .commit();
                        myViewModel.addMeal(mealID,foodList.split("\t"));
                    }
                    myViewModel.setMealCounter(highestMealID + 1);
                }
            });
        }
        else{
            Set<Integer> keySet = myViewModel.getTableKeySet();
            for(Integer i : keySet){
                ArrayList<String> foodList = myViewModel.getFoodList(i);

                if(foodList.isEmpty()){
                    //DO NOTHING
                }
                else if(foodList.size() == 1){
                    getChildFragmentManager()
                            .beginTransaction()
                            .add(R.id.food_diary_container,
                                    MealFragment.restoreInstance(i,foodList.get(0)))
                            .commit();
                }
                else{
                    String foods = foodList.get(0);

                    for(int j=1; j<foodList.size(); j++){
                        foods = foods + "\t" + foodList.get(j);
                    }

                    getChildFragmentManager()
                            .beginTransaction()
                            .add(R.id.food_diary_container,
                                    MealFragment.restoreInstance(i,foods))
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
                MealFragment currentEntry = MealFragment.newInstance(mealNumber);
                getChildFragmentManager().beginTransaction()
                        .add(R.id.food_diary_container, currentEntry)
                        .commit();
                myViewModel.incrementMealCounter();
            }
        }
        if(v.getId() == R.id.save){
            myViewModel.save();
        }
    }
}
