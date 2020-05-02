package com.example.healthyz.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.healthyz.database.HEIRecord;
import com.example.healthyz.viewmodel.MyViewModel;
import com.example.healthyz.R;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.snakydesign.livedataextensions.Lives;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SummaryFragment extends Fragment implements View.OnClickListener {
    private TextView TMP;
    private View thisView;
    private float[] HEIScore;
    private Button tableToggle;
    private boolean tableHidden;
    private RadarChart HEIChart;
    private MyViewModel myViewModel;

    private static final String F_TOTAL = "total_fruits";
    private static final String F_WHOLE = "whole_fruits";
    private static final String V_TOTAL = "total_vegies";
    private static final String V_GREEN = "greens_beans";
    private static final String G_WHOLE = "whole_grains";
    private static final String D_TOTAL = "dairy_things";
    private static final String PF_TOTAL = "protein_food";
    private static final String PF_SEA_PLANT = "seas_plan_pr";
    private static final String FA = "fatty_acids";
    private static final String G_REFINED = "refined_grain";
    private static final String NA_EST = "estimated_sodium";
    private static final String NA_ACT ="actual_sodium";
    private static final String ADD_SUGARS = "added_sugars";
    private static final String SAT_FATS = "saturated_fats";

    // TODO: Rename and change types and number of parameters
    public static SummaryFragment newInstance() {
        SummaryFragment fragment = new SummaryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.fragment_summary, container, false);
        tableToggle = thisView.findViewById(R.id.toggle_table_button);
        tableToggle.setText("View Scores");
        tableHidden = true;
        TMP = thisView.findViewById(R.id.TMP);
        HEIScore = null;
        return thisView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tableToggle.setOnClickListener(this);
        HEIChart = thisView.findViewById(R.id.radar_graph);
        HEIChart.getDescription().setEnabled(false);
        HEIChart.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        HEIChart.setWebLineWidth(1f);
        HEIChart.setWebColor(getResources().getColor(R.color.colorAccent));
        HEIChart.setWebLineWidthInner(1f);
        HEIChart.setWebColorInner(getResources().getColor(R.color.colorAccent));
        HEIChart.setWebAlpha(100);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myViewModel = new ViewModelProvider(getActivity()).get(MyViewModel.class);

        MediatorLiveData<String> liveDataMerger = new MediatorLiveData<>();
        liveDataMerger.addSource(myViewModel.getLocalHEI(), value -> liveDataMerger.setValue(value));
        liveDataMerger.addSource(myViewModel.getRemoteHEI(), value -> liveDataMerger.setValue(value));
        liveDataMerger.observe(getViewLifecycleOwner(),getNewObserver());
    }

    private Observer<String> getNewObserver(){
        return s -> {
            if(HEIScore == null){
                try{
                    JSONObject heiJSON = new JSONObject(s);
                    myViewModel.saveHEIScore(heiJSON.toString());
                    float[] TEST = new float[14];
                    //TODO: get all the components
                    TEST[0] = (float)heiJSON.getDouble(F_TOTAL);
                    TEST[1] = (float)heiJSON.getDouble(F_WHOLE);
                    TEST[2] = (float)heiJSON.getDouble(V_TOTAL);
                    TEST[3] = (float)heiJSON.getDouble(V_GREEN);
                    TEST[4] = (float)heiJSON.getDouble(G_WHOLE);
                    TEST[5] = (float)heiJSON.getDouble(G_REFINED);
                    TEST[6] = (float)heiJSON.getDouble(PF_TOTAL);
                    TEST[7] = (float)heiJSON.getDouble(PF_SEA_PLANT);
                    TEST[8] = (float)heiJSON.getDouble(D_TOTAL);
                    TEST[9] = (float)heiJSON.getDouble(FA);
                    TEST[10] = (float)heiJSON.getDouble(SAT_FATS);
                    TEST[11] = (float)heiJSON.getDouble(NA_EST);
                    TEST[12] = (float)heiJSON.getDouble(ADD_SUGARS);
                    TEST[13] = (float)heiJSON.getDouble(NA_ACT);
                    HEIScore = TEST;
                    initialise();
                } catch (JSONException e){
                    HEIScore = new float[]{10,10,10,10,10,10,10,10,10,10,10,10,10,10};
                    initialise();
                }
            }
        };
    }

    private void initialise(){
        MarkerView mv = new RadarMarkerView(getContext(), R.layout.marker_view_layout);
        mv.setChartView(HEIChart);
        HEIChart.setMarker(mv);

        setData();
        HEIChart.animateXY(1400, 1400);

        XAxis xAxis = HEIChart.getXAxis();
        xAxis.setTextSize(15f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new ValueFormatter() {
            private final String[] HEIComponents = new String[]{"1","2","3","4","5","6","7","8","9",
            "10","11","12","13"};

            @Override
            public String getFormattedValue(float value) {
                return HEIComponents[(int) value % HEIComponents.length];
            }
        });

        xAxis.setTextColor(Color.WHITE);

        YAxis yAxis = HEIChart.getYAxis();
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(15f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(100f);
        yAxis.setDrawLabels(false);

        Legend l = HEIChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(1f);
        l.setYEntrySpace(1f);
        l.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    private void setData(){
        ArrayList<RadarEntry> entries1 = new ArrayList<>();

        for (int i = 0; i < 13; i++) {
            entries1.add(new RadarEntry(HEIScore[i]));
        }

        RadarDataSet set1 = new RadarDataSet(entries1, myViewModel.getPrettyDate());
        set1.setColor(getResources().getColor(R.color.colorButtonDark));
        set1.setFillColor(getResources().getColor(R.color.colorButton));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        sets.add(set1);

        RadarData data = new RadarData(sets);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.RED);

        HEIChart.setData(data);
        HEIChart.invalidate();
    }

    @Override
    public void onClick(View v) {
        if(HEIScore != null){
            if(tableHidden){
                getChildFragmentManager()
                        .beginTransaction()
                        .add(R.id.table_container, HEITable.newInstance(HEIScore))
                        .commit();
                tableHidden = false;
                tableToggle.setText("Hide Scores");
            } else {
                for(Fragment fragment : getChildFragmentManager().getFragments()){
                    getChildFragmentManager().beginTransaction().remove(fragment).commit();
                }
                tableHidden = true;
                tableToggle.setText("View Scores");
            }
        }
    }

    private class RadarMarkerView extends MarkerView{
        public RadarMarkerView(Context context, int layoutResource) {
            super(context, layoutResource);
        }
    }
}
