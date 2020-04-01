package com.example.healthyz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

public class FoodFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {
    private View thisView;
    private ImageButton actionButton;
    private EditText userInputFood;
    private String thisFoodItem;
    private int mealNumber;
    private MyViewModel myViewModel;
    private boolean actionButtonIsOK;
    private int foodItemContainer;

    public static FoodFragment newInstance(int mealNumber) {
        Bundle args = new Bundle();
        args.putInt("mealNumber",mealNumber);
        FoodFragment fragment = new FoodFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static FoodFragment restoreInstance(int mealNumber, String thisFoodItem){
        Bundle args = new Bundle();
        args.putInt("mealNumber",mealNumber);
        args.putString("thisFoodItem",thisFoodItem);
        FoodFragment fragment = new FoodFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mealNumber = this.getArguments().getInt("mealNumber");

        if(this.getArguments().containsKey("thisFoodItem")){
            thisFoodItem = this.getArguments().getString("thisFoodItem");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.fragment_food, container, false);
        return thisView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        actionButton = thisView.findViewById(R.id.user_input_food_ok);
        actionButtonIsOK = true;
        userInputFood = thisView.findViewById(R.id.user_input_food);

        if(thisFoodItem != null){
            userInputFood.setText(thisFoodItem);
            userInputFood.setKeyListener(null);
        }

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
        if(actionButtonIsOK){
            thisFoodItem = userInputFood.getText().toString();
            if(!thisFoodItem.matches("")){
                userInputFood.setText(thisFoodItem);
                myViewModel.addFood(mealNumber,thisFoodItem);
                userInputFood.setKeyListener(null);
            }
        }
        else if(thisFoodItem != null){
            myViewModel.removeFood(mealNumber,thisFoodItem);
            getParentFragmentManager().beginTransaction().remove(this).commit();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(thisFoodItem != null){
            setActionButton(false);
        }
        return true;
    }

    public void setActionButton(boolean state){
        if(state){
            //setting actionButton to ok
            actionButtonIsOK = true;
            actionButton.setImageResource(R.drawable.ic_tick);
            int okStates = R.drawable.ok_button_states;
            actionButton.setBackground(getResources().getDrawable(okStates));
        }
        else{
            //setting actionButton to cancel
            actionButtonIsOK = false;
            actionButton.setImageResource(R.drawable.ic_close);
            int cancelStates = R.drawable.cancel_button_states;
            actionButton.setBackground(getResources().getDrawable(cancelStates));
        }
    }
}