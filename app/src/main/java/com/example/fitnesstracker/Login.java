package com.example.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitnesstracker.model.User;
import com.example.fitnesstracker.service.UserServiceImpl;

public class Login extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private UserServiceImpl userService;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);

        userService=new UserServiceImpl();
    }

    public void authenticate(View view){
        String usr,pwd;
        Log.d("auth","Auth");
        usr=username.getText().toString();
        pwd=password.getText().toString();
        Log.d("usr",usr);
        Log.d("pwd",pwd);
        user=userService.authenticate(usr,pwd);
        if(user==null){
            Toast toast=Toast.makeText(this,"Error",Toast.LENGTH_LONG);
            toast.show();
            Log.d("myerror","Error");
        }
        else{
            Toast toast=Toast.makeText(this,"Logged",Toast.LENGTH_LONG);
            toast.show();
            Log.d("logged","Logged");
        }

    }
}