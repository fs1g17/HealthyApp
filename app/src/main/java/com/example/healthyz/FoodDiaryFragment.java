package com.example.healthyz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Set;

public class FoodDiaryFragment extends Fragment implements View.OnClickListener {
    private View thisView;
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
        addEntry = thisView.findViewById(R.id.add_meal);
        addEntry.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myViewModel = new ViewModelProvider(getActivity()).get(MyViewModel.class);
        initialise();
    }

    private void initialise(){
        Set<Integer> keySet = myViewModel.getTableKeySet();
        for(Integer i : keySet){
            MealFragment currentEntry = MealFragment.restoreInstance(i,myViewModel.getFoodList(i));
            getChildFragmentManager().beginTransaction()
                    .add(R.id.food_diary_container, currentEntry)
                    .commit();
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
            //SAVE
        }
    }
}
