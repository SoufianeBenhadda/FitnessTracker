package com.example.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnesstracker.dao.ProfileDao;
import com.squareup.picasso.Picasso;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;


import com.example.fitnesstracker.model.User;

import java.util.concurrent.ExecutionException;


public class Profile extends AppCompatActivity {
    private User user;
    TextView fl,vusern;
    private EditText ed_age,ed_w,ed_h;
    ImageView iv;
    private Button btnUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fl = findViewById(R.id.flname);
        vusern = findViewById(R.id.vusername);
        ed_age=findViewById(R.id.e_age);
        ed_w=findViewById(R.id.e_w);
        ed_h=findViewById(R.id.e_h);

        iv=findViewById(R.id.pic);
        Intent i = getIntent();
        user = (User)i.getSerializableExtra("user");
        fl.setText(user.getFirstName()+" "+user.getLastName());
        vusern.setText(user.getUsername());
        ed_age.setText(Integer.toString(user.getAge()));
        ed_w.setText(Double.toString(user.getWeight()));
        ed_h.setText(Double.toString(user.getHeight()));
        Picasso.with(this).load(user.getPicture()).into(iv);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnUpdate = findViewById(R.id.btn_update);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s_age, s_height, s_weight,s_usr,fn,ln,img;
                fn=user.getFirstName();
                ln=user.getLastName();
                img=user.getPicture();
                s_usr=user.getUsername();
                s_age = ed_age.getText().toString();
                s_height = ed_h.getText().toString();
                s_weight = ed_w.getText().toString();
                try {
                    user=new ProfileDao().execute(s_usr,s_age,s_height,
                            s_weight,fn,ln,img).get();

                    Log.d("username1",s_usr);
                }
                catch (ExecutionException e) {
                    e.printStackTrace();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(getApplicationContext(), Profile.class);
                i.putExtra("user",user);
                startActivity(i);
                Toast toast=Toast.makeText(getApplicationContext(),"Profile Update Successfully",Toast.LENGTH_LONG);
                toast.show();
            }

            });
    }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {
           case android.R.id.home:
               onBackPressed();
               return true;
           default:
               return super.onOptionsItemSelected(item);
       }
   }
}