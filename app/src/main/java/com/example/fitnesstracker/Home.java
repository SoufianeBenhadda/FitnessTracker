package com.example.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fitnesstracker.dao.HomeDao;
import com.example.fitnesstracker.dao.LoginDao;
import com.example.fitnesstracker.model.Excercise;
import com.example.fitnesstracker.model.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Home extends AppCompatActivity   {
    private List<Excercise> exos;
    private User user;
    private EditText etSearch;
    private CustomListAdapter adapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        etSearch = (EditText) findViewById(R.id.etSearch);
        List<Excercise> image_details = getListData();
        final ListView listView = (ListView) findViewById(R.id.listView);
        adapter1=new CustomListAdapter(this, image_details);
        listView.setAdapter(adapter1);

        Intent i = getIntent();
        user = (User)i.getSerializableExtra("user");
        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
                adapter1.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        // When the user clicks on the ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                Excercise exo = (Excercise) o;
                Toast.makeText(getApplicationContext(), "Selected :" + " " + exo, Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), Exerciseinfo.class);
                i.putExtra("Exo",exo);
                i.putExtra("user",user);
                startActivity(i);
            }
        });
    }

    private List<Excercise> getListData() {
        try {
            exos=new HomeDao().execute().get();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(exos==null){
            Toast toast=Toast.makeText(getApplicationContext(),"No data",Toast.LENGTH_LONG);
            toast.show();
            Log.d("myerror","Error");
        }
        else{

            Toast toast=Toast.makeText(getApplicationContext(),"yes data",Toast.LENGTH_LONG);
            toast.show();
            Log.d("logged","Logged");

        }
        return exos;


    }
}