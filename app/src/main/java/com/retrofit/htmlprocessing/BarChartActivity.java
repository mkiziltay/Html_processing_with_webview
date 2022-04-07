package com.retrofit.htmlprocessing;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class BarChartActivity extends AppCompatActivity {

    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchart);

        define();
        drawChart();
    }


    private void define() {
        barChart = findViewById(R.id.barChart);
    }

    private void drawChart() {
        ArrayList<BarEntry> barEntries = MainActivity.barEntries; //Calling values from MainActivity class

        BarDataSet barDataSet = new BarDataSet(barEntries, "Currencies");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(14f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Currencies Percentages");
        barChart.animateY(2000); // animation chart from X to Y in 2 sec.
    }
}