package com.example.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private String msg;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        login=findViewById(R.id.button3);

        sp=getSharedPreferences("session", Context.MODE_PRIVATE);

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
                    Log.d("msg",msg);
                }
                catch (ExecutionException e) {
                    e.printStackTrace();

                }
                catch (InterruptedException e) {
                    e.printStackTrace();

                }
                catch(Exception e){
                    Log.d("test",e.toString());
                }
                if(user==null){
                    Toast toast=Toast.makeText(getApplicationContext(),"Your username or password is incorrect",Toast.LENGTH_LONG);
                    toast.show();
                    Log.d("myerror","Error");
                }
                else{
                    if(user.getRole().equals("Client")){
                        Intent i = new Intent(getApplicationContext(), Home.class);
                        i.putExtra("user",user);
                        startActivity(i);
                    }
                    else if(user.getRole().equals("Coach")){
                        Intent intent=new Intent(getApplicationContext(),Conversations.class);
                        intent.putExtra("username",user.getUsername());
                        startActivity(intent);
                    }
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("session_username",usr);
                    editor.commit();
                    Toast toast=Toast.makeText(getApplicationContext(),"Logged",Toast.LENGTH_LONG);
                    toast.show();
                    Log.d("logged","Logged");


                }
            }
        });

    }
}