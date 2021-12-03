package com.example.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnesstracker.dao.SignupDao;
import com.example.fitnesstracker.dao.TrackingDao;
import com.example.fitnesstracker.model.Excercise;
import com.example.fitnesstracker.model.User;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutionException;

public class Exerciseinfo extends AppCompatActivity {
    TextView exoTitle;
    ImageView exoImg;
    Button validate;
    EditText weight;
    EditText reps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exerciseinfo);
        exoTitle=findViewById(R.id.textView_ExoName);
        exoImg=findViewById(R.id.imageView_exoimage);
        validate=findViewById(R.id.button_validate);
        weight=findViewById(R.id.TextNumber_weight);
        reps=findViewById(R.id.TextNumber_Reps);
        Intent i = getIntent();
        final Excercise[] exo = {(Excercise) i.getSerializableExtra("Exo")};
        final User[] user = {(User) i.getSerializableExtra("user")};
        exoTitle.setText(exo[0].getTitle());
        Picasso.with(getApplicationContext()).load(exo[0].getImage())
                .into(exoImg);

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
    }
    //
}
