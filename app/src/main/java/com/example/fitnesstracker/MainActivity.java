package com.example.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void login(View view){
        startActivity(new Intent(this,Login.class));
        Log.d("login","Login");
    }

    public void signup(View view){
        startActivity(new Intent(this,Signup.class));
        Log.d("signup","Signup");
    }
}