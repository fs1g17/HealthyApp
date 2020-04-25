package com.example.healthyz;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SummaryFragment extends Fragment implements View.OnClickListener {
    private View thisView;
    private float[] HEIScore;
    private Button tableToggle;
    private boolean tableHidden;
    private RadarChart HEIChart;
    private MyViewModel myViewModel;

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://healthy-z.com:8500/HEIScore")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyAPI myAPI = retrofit.create(MyAPI.class);
        Call<List<HEIScore>> call = myAPI.loadScores();

        HEIScore = myViewModel.getHEIScore();
        initialise();
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
        if(tableHidden){
            getChildFragmentManager()
                    .beginTransaction()
                    .add(R.id.table_container,HEITable.newInstance(HEIScore))
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


    private class RadarMarkerView extends MarkerView{
        public RadarMarkerView(Context context, int layoutResource) {
            super(context, layoutResource);
        }
    }
}
