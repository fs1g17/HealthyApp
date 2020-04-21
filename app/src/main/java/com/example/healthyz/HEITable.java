package com.example.healthyz;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HEITable extends Fragment {
    float[] HEIScore;
    private View thisView;

    // TODO: Rename and change types and number of parameters
    public static HEITable newInstance(float[] HEIScore) {
        HEITable fragment = new HEITable();
        Bundle args = new Bundle();
        args.putFloatArray("HEIScore",HEIScore);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String key = "HEIScore";
        if (getArguments().keySet().contains(key)) {
            HEIScore = getArguments().getFloatArray(key);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.fragment_hei_table, container, false);;
        return thisView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialise();
    }

    private void initialise(){
        String[] HEIComponents = new String[]{"Total Fruits","Whole Fruits",
                "Total Vegetables","Greens and Beans","Whole Grains","Dairy",
                "Total Protein Foods","Seafood and Plant Proteins","Fatty Acids",
                "Refined Grains","Sodium","Added Sugars","Saturated Fats"};

        TextView totalFruits = thisView.findViewById(R.id.total_fruits);
        TextView wholeFruits = thisView.findViewById(R.id.whole_fruits);
        TextView totalVegies = thisView.findViewById(R.id.total_vegetables);
        TextView greensBeans = thisView.findViewById(R.id.greens_beans);
        TextView wholeGrains = thisView.findViewById(R.id.whole_grains);
        TextView totalDairys = thisView.findViewById(R.id.dairy);
        TextView allProtFood = thisView.findViewById(R.id.total_protein_foods);
        TextView seaAndPlant = thisView.findViewById(R.id.seafood_plant_proteins);
        TextView fattyAcidsR = thisView.findViewById(R.id.fatty_acid_ratio);
        TextView refineGrain = thisView.findViewById(R.id.refined_grains);
        TextView sodiumValue = thisView.findViewById(R.id.sodium);
        TextView addedSugars = thisView.findViewById(R.id.added_sugars);
        TextView satFatAcids = thisView.findViewById(R.id.saturated_fats);

        totalFruits.setText("" + HEIScore[0]);
        wholeFruits.setText("" + HEIScore[1]);
        totalVegies.setText("" + HEIScore[2]);
        greensBeans.setText("" + HEIScore[3]);
        wholeGrains.setText("" + HEIScore[4]);
        totalDairys.setText("" + HEIScore[5]);
        allProtFood.setText("" + HEIScore[6]);
        seaAndPlant.setText("" + HEIScore[7]);
        fattyAcidsR.setText("" + HEIScore[8]);
        refineGrain.setText("" + HEIScore[9]);
        sodiumValue.setText("" + HEIScore[10]);
        addedSugars.setText("" + HEIScore[11]);
        satFatAcids.setText("" + HEIScore[12]);
    }
}
