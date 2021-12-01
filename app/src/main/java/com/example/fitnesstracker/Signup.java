package com.example.fitnesstracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.fitnesstracker.dao.SignupDao;
import com.example.fitnesstracker.model.User;

import java.util.concurrent.ExecutionException;

public class Signup extends AppCompatActivity {

    private EditText username, firstname, lastname, age, weight, height, password, confirm_password;
    private RadioGroup rgroup;
    private Button signup;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.r_username);
        firstname = findViewById(R.id.r_first_name);
        lastname = findViewById(R.id.r_last_name);
        age = findViewById(R.id.r_age);
        weight = findViewById(R.id.r_weight);
        height = findViewById(R.id.r_height);
        password = findViewById(R.id.r_password);
        confirm_password = findViewById(R.id.r_confirm);
        rgroup = findViewById(R.id.radioGroup);
        signup = findViewById(R.id.button4);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gender;
                String usr, pwd, s_age, s_height, s_weight, s_firstname, s_lastname, s_confirm_password;
                usr = username.getText().toString();
                pwd = password.getText().toString();
                s_confirm_password = confirm_password.getText().toString();
                s_age = age.getText().toString();
                s_height = height.getText().toString();
                s_weight = weight.getText().toString();
                s_firstname = firstname.getText().toString();
                s_lastname = lastname.getText().toString();
                if (rgroup.getCheckedRadioButtonId() == R.id.radioButton) gender = "female";
                else gender = "male";
                //String excep;
                if (usr.length() == 0 || pwd.length() == 0 || s_age.length() == 0 || s_height.length() == 0 || s_weight.length() == 0 || s_firstname.length() == 0
                        || s_lastname.length() == 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please make sure you entered all the fields", Toast.LENGTH_LONG);
                    toast.show();
                }
                else {

                    if (!pwd.equals(s_confirm_password)) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Please make sure your passwords match", Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        AlertDialog.Builder buildDialog = new AlertDialog.Builder(Signup.this);
                        buildDialog.setTitle("Confirmation");
                        buildDialog.setMessage("Do you want confirm the information ?");
                        buildDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    user = new SignupDao().execute(usr, pwd, s_age, gender, s_height,
                                            s_weight, s_firstname, s_lastname).get();
                                    if (user == null) {
                                        Log.d("gg", "error");
                                    } else {
                                        Log.d("registered", "registered");
                                    }
                                    //Log.d("db",excep);
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                startActivity(new Intent(getApplicationContext(), Home.class));
                                Toast toast = Toast.makeText(getApplicationContext(), "User successfully registered", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        });
                        buildDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        buildDialog.show();

                    }
                }

            }
        });
    }
}

