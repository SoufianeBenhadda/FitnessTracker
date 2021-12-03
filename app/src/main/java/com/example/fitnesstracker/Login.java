package com.example.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitnesstracker.dao.LoginDao;
import com.example.fitnesstracker.model.User;

import java.util.concurrent.ExecutionException;

public class Login extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        login=findViewById(R.id.button3);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usr,pwd;
                Log.d("auth","Auth");
                usr=username.getText().toString();
                pwd=password.getText().toString();
                Log.d("usr",usr);
                Log.d("pwd",pwd);
                try {
                    user=new LoginDao().execute(usr,pwd).get();
                }
                catch (ExecutionException e) {
                    e.printStackTrace();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(user==null){
                    Toast toast=Toast.makeText(getApplicationContext(),"Your username or password is incorrect",Toast.LENGTH_LONG);
                    toast.show();
                    Log.d("myerror","Error");
                }
                else{
                    //startActivity(new Intent(getApplicationContext(),Home.class));
                    Intent i = new Intent(getApplicationContext(), Home.class);
                    i.putExtra("user",user);
                    startActivity(i);
                    Toast toast=Toast.makeText(getApplicationContext(),"Logged",Toast.LENGTH_LONG);
                    toast.show();
                    Log.d("logged","Logged");
                }
            }
        });

    }
}