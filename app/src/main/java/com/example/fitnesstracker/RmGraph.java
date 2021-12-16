package com.example.fitnesstracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class RmGraph extends AppCompatActivity {
    LineChart lineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rmgraph);
        lineChart = (LineChart) findViewById(R.id.linechart);

        ArrayList<Entry> data=new ArrayList<Entry>();
        data.add(new Entry(01,60));
        data.add(new Entry(02,70));
        data.add(new Entry(03,75));
        data.add(new Entry(04,80));
        data.add(new Entry(05,77));


        LineDataSet lineDataSet = new LineDataSet(data,"datas");

        ArrayList<ILineDataSet> dataSets=new ArrayList<>();
        dataSets.add(lineDataSet);

        LineData linedata =new LineData(dataSets);
        lineChart.setData(linedata);

        lineChart.invalidate();
    }
}
