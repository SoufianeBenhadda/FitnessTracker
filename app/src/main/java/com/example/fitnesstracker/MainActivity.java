package com.example.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login=findViewById(R.id.button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                Log.d("login","Login");
            }
        });

    }

    /*public void login(View view){
        startActivity(new Intent(this,Login.class));
        Log.d("login","Login");
    }*/


    public void signup(View view){
        startActivity(new Intent(this,Signup.class));
        Log.d("signup","Signup");
    }
}