package com.example.healthyz.view;

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

import com.example.healthyz.viewmodel.MyViewModel;
import com.example.healthyz.R;

import org.json.JSONException;
import org.json.JSONObject;

public class FoodFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {
    private View thisView;
    private int mealNumber;
    public static final String JSONFOOD = "food_item";
    public static final String JSONKCAL = "calories";
    public static final String JSONSALT = "sodium";

    private String jsonInfo;
    private String thisFood;
    private String thisSodium;
    private String thisCalories;
    
    private EditText userInputFood;
    private EditText userInputSodium;
    private EditText userInputCalories;
    
    private MyViewModel myViewModel;
    private boolean actionButtonIsOK;
    private ImageButton actionButton;
    private int foodItemContainer;

    public static FoodFragment newInstance(int mealNumber) {
        Bundle args = new Bundle();
        args.putInt("mealNumber",mealNumber);
        FoodFragment fragment = new FoodFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static FoodFragment restoreInstance(int mealNumber, String jsonInfo){
        Bundle args = new Bundle();
        args.putInt("mealNumber",mealNumber);
        args.putString("jsonInfo",jsonInfo);
        FoodFragment fragment = new FoodFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mealNumber = this.getArguments().getInt("mealNumber");

        if(this.getArguments().containsKey("jsonInfo")){
            try {
                jsonInfo = this.getArguments().getString("jsonInfo");
                JSONObject info = new JSONObject(jsonInfo);
                thisFood = info.getString(JSONFOOD);
                thisSodium = info.getString(JSONSALT);
                thisCalories = info.getString(JSONKCAL);
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
        userInputSodium = thisView.findViewById(R.id.user_input_sodium);
        userInputCalories = thisView.findViewById(R.id.user_input_calories);

        if(thisFood != null && thisSodium != null && thisCalories != null){
            userInputFood.setText(thisFood);
            userInputSodium.setText(thisSodium);
            userInputCalories.setText(thisCalories);

            userInputFood.setKeyListener(null);
            userInputSodium.setKeyListener(null);
            userInputCalories.setKeyListener(null);
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
            thisFood = userInputFood.getText().toString();
            thisSodium = userInputSodium.getText().toString();
            thisCalories = userInputCalories.getText().toString();

            //TODO: implement pop up dialog notifying the user that the values in sodium and calories editext must be numbers
            if(!thisFood.matches("") && thisSodium.matches("[0-9]+") && thisCalories.matches("[0-9]+")){
                userInputFood.setText(thisFood);
                userInputSodium.setText(thisSodium);
                userInputCalories.setText(thisCalories);

                userInputFood.setKeyListener(null);
                userInputSodium.setKeyListener(null);
                userInputCalories.setKeyListener(null);

                try {
                    JSONObject info = new JSONObject();
                    info.put(JSONFOOD,thisFood);
                    info.put(JSONSALT,thisSodium);
                    info.put(JSONKCAL,thisCalories);
                    myViewModel.addFood(mealNumber, info);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        else if(thisFood != null){
            try{
                JSONObject currentFoodItem = new JSONObject();
                currentFoodItem.put(JSONFOOD, thisFood);
                currentFoodItem.put(JSONSALT, thisSodium);
                currentFoodItem.put(JSONKCAL, thisCalories);
                myViewModel.removeFood(mealNumber, currentFoodItem);
                getParentFragmentManager().beginTransaction().remove(this).commit();
            } catch (JSONException e){
                //TODO: implement pop up dialog to notify the user
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(thisFood != null){
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