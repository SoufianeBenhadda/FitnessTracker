package com.example.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnesstracker.dao.ProfileDao;
import com.example.fitnesstracker.model.User;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutionException;

public class ProfileFragment  extends Fragment {
    private User user;
    TextView fl,vusern;
    private EditText ed_age,ed_w,ed_h;
    ImageView iv;
    private Button btnUpdate;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         String username=this.getArguments().getString("username");
         Double weight=this.getArguments().getDouble("weight");
         Double height=this.getArguments().getDouble("height");
         int age=this.getArguments().getInt("age");
         String picture=this.getArguments().getString("pic");
         String firstName=this.getArguments().getString("fname");
         String lastName=this.getArguments().getString("lname");
         View view =inflater.inflate(R.layout.fragment_profile, container, false);
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_profile);
        fl = view.findViewById(R.id.flname);
        vusern = view.findViewById(R.id.vusername);
        ed_age=view.findViewById(R.id.e_age);
        ed_w=view.findViewById(R.id.e_w);
        ed_h=view.findViewById(R.id.e_h);

        iv=view.findViewById(R.id.pic);
        //Intent i =getIntent();
        //user = (User)i.getSerializableExtra("user");
        fl.setText(firstName+" "+lastName);
        vusern.setText(username);
        ed_age.setText(Integer.toString(age));
        ed_w.setText(Double.toString(weight));
        ed_h.setText(Double.toString(height));
        Picasso.with(getActivity()).load(picture).into(iv);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnUpdate = view.findViewById(R.id.btn_update);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s_age, s_height, s_weight,s_usr,fn,ln,img;
                fn=firstName;
                ln=lastName;
                img=picture;
                s_usr=username;
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
               /* Intent i = new Intent(getApplicationContext(), Profile.class);
                i.putExtra("user",user);
                startActivity(i);*/
                Toast toast=Toast.makeText(getActivity(),"Profile Update Successfully",Toast.LENGTH_LONG);
                toast.show();
            }

        });

        return view;
    }
}
