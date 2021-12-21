package com.example.fitnesstracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnesstracker.dao.SignupDao;
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


public class Exerciseinfo extends AppCompatActivity {
    TextView exoTitle;
    ImageView exoImg;
    Button validate;
    Button graph;
    EditText weight;
    EditText reps;

    EditText mDateFormat;
    //Date picker
    DatePickerDialog.OnDateSetListener  onDateSetListener;

    //Table View Columns and Data
    static String[] columns = {"Weight","Reps","Rep Max","Date"};
    //String[][] data= new String[3][4];
    private Double[] weights= new Double[]{10.5,20.5,15.5};
    private int[] repMax= new int[]{7,5,9};
    private String[] dates= new String[]{"03/12/2014","03/12/2015","03/12/2016"};
    private ArrayList<Tracker> trackerlArrayList;
    private ListView listView;

    Date c = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault());
    String formattedDate = df.format(c);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exerciseinfo);
        exoTitle=findViewById(R.id.textView_ExoName);
        exoImg=findViewById(R.id.imageView_exoimage);
        validate=findViewById(R.id.button_validate);
        graph=findViewById(R.id.button_validate2);
        weight=findViewById(R.id.TextNumber_weight);
        reps=findViewById(R.id.TextNumber_Reps);
        Intent i = getIntent();
        final Excercise[] exo = {(Excercise) i.getSerializableExtra("Exo")};
        final User[] user = {(User) i.getSerializableExtra("user")};
        exoTitle.setText(exo[0].getTitle());
        Picasso.with(getApplicationContext()).load(exo[0].getImage())
                .into(exoImg);

       
        listView = findViewById(R.id.listView);
        trackerlArrayList=getData(user[0].getUsername(),String.valueOf(exo[0].getId()),formattedDate);

        RepsAdapter repsAdapter = new RepsAdapter(this,trackerlArrayList);
        listView.setAdapter(repsAdapter);

        //getData(data,"abc@gmail.com","1","03/12/2021");

        //TableView Section
        //getData(user[0].getUsername(),String.valueOf(exo[0].getId()));
/*
        final TableView<String[]> tableView = (TableView<String[]>) findViewById(R.id.tableView);
        //tableView.setHeaderBackgroundColor(Color.parseColor("#3236a8"));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this,columns));
        tableView.setColumnCount(4);
        tableView.setDataAdapter(new SimpleTableDataAdapter(this,data));
        */



        // Date Section
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        mDateFormat = findViewById(R.id.dateFormat);
        mDateFormat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Exerciseinfo.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener,
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
                RepsAdapter repsAdapter1 = new RepsAdapter(Exerciseinfo.this,trackerlArrayList);
                listView.setAdapter(repsAdapter1);

               // listView.setAdapter(repsAdapter);


                //getData(user[0].getUsername(),String.valueOf(exo[0].getId()),mDateFormat.getText().toString());
            }

        };


        mDateFormat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               // getData(data,"abc@gmail.com","1","03/12/2021");

            }

            @Override
            public void afterTextChanged(Editable editable) {
               // getData(data,"abc@gmail.com","1","03/12/2021");

                //repsAdapter.notifyDataSetChanged();

                Toast.makeText(Exerciseinfo.this,"new Data", Toast.LENGTH_SHORT).show();
            }
        });
        //tableView.setDataAdapter(new SimpleTableDataAdapter(this,data));


        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // String userid,exoid,weight,reps;
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


                Toast toast=Toast.makeText(getApplicationContext(),"Tracking successfully registered",Toast.LENGTH_LONG);
                toast.show();
            }
        });

        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RmGraph.class);
                i.putExtra("Exo",exo);
                i.putExtra("user",user);
                startActivity(i);

                Toast toast=Toast.makeText(getApplicationContext(),"Tracking successfully registered",Toast.LENGTH_LONG);
                toast.show();
            }
        });


    }

    private ArrayList<Tracker> getData(String userId,String exoId,String mydate){
        ArrayList list = new ArrayList<>();
        try {
            List<Tracker> table =new TrackingTableDao().execute(userId,exoId,mydate).get();

            //data = new String[table.size()][4];

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

    //
}
