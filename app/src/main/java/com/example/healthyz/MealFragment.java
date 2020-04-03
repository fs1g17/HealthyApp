package com.example.healthyz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MealFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {
    private View thisView;
    private ImageButton actionButton;
    private boolean actionButtonIsDelete;
    private MyViewModel myViewModel;
    private int mealNumber;
    private ArrayList<String> foodList;

    public static MealFragment newInstance(int mealNumber) {
        Bundle args = new Bundle();
        args.putInt("mealNumber",mealNumber);
        MealFragment fragment = new MealFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static MealFragment restoreInstance(int mealNumber, String foodList){
        Bundle args = new Bundle();
        args.putInt("mealNumber",mealNumber);
        args.putString("foodList",foodList);
        MealFragment fragment = new MealFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mealNumber = this.getArguments().getInt("mealNumber");
        String food;

        if(this.getArguments().containsKey("foodList")){
            food = this.getArguments().getString("foodList");
            String[] foodArray = food.split("\t");

            for(int i=0; i<foodArray.length; i++){
                getChildFragmentManager()
                        .beginTransaction()
                        .add(R.id.food_item_container,
                                FoodFragment.restoreInstance(mealNumber,foodArray[i]))
                        .commit();
            }
        }
        else{
            foodList = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.fragment_meal, container, false);
        return thisView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        actionButtonIsDelete = false;
        actionButton = thisView.findViewById(R.id.add_food_item);
        actionButton.setOnClickListener(this);
        actionButton.setOnLongClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myViewModel = new ViewModelProvider(getActivity()).get(MyViewModel.class);
    }

    @Override
    public void onClick(View v) {
        if(!actionButtonIsDelete){
            int foodItemID = mealNumber;
            getChildFragmentManager().beginTransaction().add(R.id.food_item_container, FoodFragment.newInstance(foodItemID)).commit();
        }
        else{
            myViewModel.removeMeal(mealNumber);
            getParentFragmentManager().beginTransaction().remove(this).commit();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(myViewModel.getFoodList(mealNumber).isEmpty()){
            setActionButtonToDelete();
        }
        return true;
    }

    public void setActionButtonToDelete(){
        actionButtonIsDelete = true;
        actionButton.setImageResource(R.drawable.ic_close);
        int cancelStates = R.drawable.cancel_button_states;
        actionButton.setBackground(getResources().getDrawable(cancelStates));
    }
}