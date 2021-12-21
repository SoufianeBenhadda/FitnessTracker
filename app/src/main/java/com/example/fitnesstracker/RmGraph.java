package com.example.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnesstracker.dao.GraphDao;
import com.example.fitnesstracker.dao.TrackingTableDao;
import com.example.fitnesstracker.model.Excercise;
import com.example.fitnesstracker.model.Tracker;
import com.example.fitnesstracker.model.User;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RmGraph extends AppCompatActivity {
    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rmgraph);
        lineChart = (LineChart) findViewById(R.id.linechart);
        Intent i = getIntent();
        final Excercise[] exo = {(Excercise) i.getSerializableExtra("Exo")};
        final User[] user = {(User) i.getSerializableExtra("user")};
        Log.d("repmax","before data");
        List<Tracker> trackers = getData(user[0].getUsername(),String.valueOf(exo[0].getId()));
        Log.d("repmax","after data");
        final ArrayList<Entry> yValues = new ArrayList<>();
        final ArrayList<String> xAxes = new ArrayList<>();
        for(int e=0;e<trackers.size(); e++) {
            float f = (float)trackers.get(e).getRepmax();
            Log.d("repmax",String.valueOf(f));
            yValues.add(new Entry(e, f ));  // Y axis values
            xAxes.add(e, trackers.get(e).getDate()); //Dynamic x-axis labels

        }

        ArrayList<ILineDataSet>dataSets = new ArrayList<>();
        LineDataSet set1;

        set1 = new LineDataSet(yValues, "RM");

        dataSets.add(set1);
        lineChart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxes));



        LineData linedata =new LineData(dataSets);
        lineChart.setData(linedata);

        lineChart.invalidate();


    }
    private ArrayList<Tracker> getData(String userId, String exoId ) {
        ArrayList list = new ArrayList<>();
        try {
            List<Tracker> table = new GraphDao().execute(userId, exoId).get();

            //data = new String[table.size()][4];

            for (int j = 0; j < table.size(); j++) {
                Tracker ttt = table.get(j);
                list.add(ttt);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return list;
    }
}
