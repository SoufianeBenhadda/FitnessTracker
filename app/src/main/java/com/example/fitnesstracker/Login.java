package com.example.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitnesstracker.dao.LoginDao;
import com.example.fitnesstracker.model.User;

import java.util.concurrent.ExecutionException;

public class Login extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);

    }

    public void authenticate(View view) throws ExecutionException, InterruptedException {
        String usr,pwd;
        Log.d("auth","Auth");
        usr=username.getText().toString();
        pwd=password.getText().toString();
        Log.d("usr",usr);
        Log.d("pwd",pwd);
        user=new LoginDao().execute(usr,pwd).get();
        if(user==null){
            Toast toast=Toast.makeText(this,"Error",Toast.LENGTH_LONG);
            toast.show();
            Log.d("myerror","Error");
        }
        else{
            startActivity(new Intent(this,Home.class));
            Toast toast=Toast.makeText(this,"Logged",Toast.LENGTH_LONG);
            toast.show();
            Log.d("logged","Logged");
        }

    }
}