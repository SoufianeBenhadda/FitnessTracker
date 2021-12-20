package com.example.fitnesstracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnesstracker.dao.TrackingDao;
import com.example.fitnesstracker.dao.TrackingTableDao;
import com.example.fitnesstracker.model.Excercise;
import com.example.fitnesstracker.model.Tracker;
import com.example.fitnesstracker.model.User;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class Exerciseinfo extends Fragment {
    TextView exoTitle;
    ImageView exoImg;
    Button validate;
    EditText weight;
    EditText reps;

    EditText mDateFormat;

    DatePickerDialog.OnDateSetListener  onDateSetListener;

    static String[] columns = {"Weight","Reps","Rep Max","Date"};

    private Double[] weights= new Double[]{10.5,20.5,15.5};
    private int[] repMax= new int[]{7,5,9};
    private String[] dates= new String[]{"03/12/2014","03/12/2015","03/12/2016"};
    private ArrayList<Tracker> trackerlArrayList;
    private ListView listView;

    Date c = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault());
    String formattedDate = df.format(c);
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_exerciseinfo, container, false);

        exoTitle=view.findViewById(R.id.textView_ExoName);
        exoImg=view.findViewById(R.id.imageView_exoimage);
        validate=view.findViewById(R.id.button_validate);
        weight=view.findViewById(R.id.TextNumber_weight);
        reps=view.findViewById(R.id.TextNumber_Reps);
        //Intent i = getIntent();
        Bundle bundle = getArguments();

        final Excercise[] exo = {(Excercise) bundle.getSerializable("exo")};
        final User[] user = {(User) bundle.getSerializable("user")};
        exoTitle.setText(exo[0].getTitle());
        Picasso.with(getActivity()).load(exo[0].getImage())
                .into(exoImg);


        listView =view.findViewById(R.id.listView);
        trackerlArrayList=getData(user[0].getUsername(),String.valueOf(exo[0].getId()),formattedDate);

        RepsAdapter repsAdapter = new RepsAdapter(getActivity(),trackerlArrayList);
        listView.setAdapter(repsAdapter);

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        mDateFormat =view.findViewById(R.id.dateFormat);
        mDateFormat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener,
                        year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month=month+1;
                if(day>=1 && day<=9){
                    String date ="0"+day+"/"+month+"/"+year;
                    mDateFormat.setText(date);
                }
                else{
                    String date =day+"/"+month+"/"+year;
                    mDateFormat.setText(date);
                }


                trackerlArrayList =  getData(user[0].getUsername(),String.valueOf(exo[0].getId()),mDateFormat.getText().toString());
                RepsAdapter repsAdapter1 = new RepsAdapter(getActivity(),trackerlArrayList);
                listView.setAdapter(repsAdapter1);

            }

        };


        mDateFormat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {


                Toast.makeText(getActivity(),"new Data", Toast.LENGTH_SHORT).show();
            }
        });



        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userid= user[0].getUsername();
                String exoid=String.valueOf(exo[0].getId());
                String inputweight=weight.getText().toString();
                String inputreps=reps.getText().toString();

                try {
                    Log.d("before","beeeee");
                    String str = new TrackingDao().execute(userid,exoid,inputweight,inputreps).get();
                    //Log.d("erroooooooooor",str);
                }
                catch (ExecutionException e) {
                    e.printStackTrace();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }


                Toast toast=Toast.makeText(getActivity(),"Tracking successfully registered",Toast.LENGTH_LONG);
                toast.show();
            }
        });
        return view;}
    private ArrayList<Tracker> getData(String userId,String exoId,String mydate){
        ArrayList list = new ArrayList<>();
        try {
            List<Tracker> table =new TrackingTableDao().execute(userId,exoId,mydate).get();



            for(int j=0; j<table.size();j++){
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

    private ArrayList<Tracker> populateList(){

        ArrayList<Tracker> list = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            Tracker tracker = new Tracker();
            tracker.setWeight(weights[i]);
            tracker.setReps(repMax[i]);
            tracker.setRepmax(repMax[i]);
            tracker.setDate(dates[i]);
            list.add(tracker);
        }

        return list;
    }
}
