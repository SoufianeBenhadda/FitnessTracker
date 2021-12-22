package com.example.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnesstracker.dao.GraphDao;
import com.example.fitnesstracker.model.Excercise;
import com.example.fitnesstracker.model.Tracker;
import com.example.fitnesstracker.model.User;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class RMFragment extends Fragment {

    LineChart lineChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_r_m, container, false);
        lineChart = (LineChart) view.findViewById(R.id.linechart);


        Log.d("before","intent");
        /*Intent i = getIntent();
        Excercise exo = (Excercise) i.getSerializableExtra("Exo");
        User user = (User) i.getSerializableExtra("user");*/

        Bundle bundle = getArguments();

        Excercise exo = (Excercise) bundle.getSerializable("exo");
        User user = (User) bundle.getSerializable("user");

        Log.d("repmax","before data");
        List<Tracker> trackers = getData(user.getUsername(),String.valueOf(exo.getId()));
        Log.d("repmax","after data");
        List<Entry> yValues = new ArrayList<>();
        List<String> xAxes = new ArrayList<>();
        for(int e=0;e<trackers.size(); e++) {
            float f = (float)trackers.get(e).getRepmax();
            Log.d("val",String.valueOf(f));
            yValues.add(new Entry(e, f ));  // Y axis values
            Log.d("val",String.valueOf(f));
            xAxes.add( trackers.get(e).getDate()); //Dynamic x-axis labels

        }
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);
        lineChart.setPinchZoom(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setExtraLeftOffset(15);
        lineChart.setExtraRightOffset(15);
        //to hide background lines
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawGridLines(false);

        //to hide right Y and top X border
        YAxis rightYAxis = lineChart.getAxisRight();
        //rightYAxis.setEnabled(false);
        YAxis leftYAxis = lineChart.getAxisLeft();
        //leftYAxis.setEnabled(false);
        XAxis topXAxis = lineChart.getXAxis();
        topXAxis.setEnabled(false);


        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f);

        xAxis.setEnabled(true);
        xAxis.setLabelCount(trackers.size());
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        ArrayList<ILineDataSet>dataSets = new ArrayList<>();
        LineDataSet set1;

        set1 = new LineDataSet(yValues, "RM");
        set1.setDrawFilled(true);
        set1.setFillDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.gradient));
        dataSets.add(set1);
        lineChart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxes));


        LineData linedata =new LineData(dataSets);
        lineChart.setData(linedata);

        lineChart.invalidate();
        lineChart.getLegend().setEnabled(false);
        lineChart.getDescription().setText("RM per Date");

        return view;


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
