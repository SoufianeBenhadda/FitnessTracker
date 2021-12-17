package com.example.fitnesstracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnesstracker.model.User;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.nio.BufferUnderflowException;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent i = getIntent();
        user = (User)i.getSerializableExtra("user");
        Log.d("test",user.getUsername());
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer =findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header, navigationView, false);


        TextView iv = (TextView) headerView.findViewById(R.id.header_username);
        ImageView tv=(ImageView) headerView.findViewById(R.id.header_pic);
        Picasso.with(this).load(user.getPicture()).resize(192, 192).into(tv);
        iv.setText(user.getUsername());
        navigationView.addHeaderView(headerView);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
               /* Intent in = new Intent(getApplicationContext(), Home.class);
                in.putExtra("user",user);
                startActivity(in);*/

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                Toast.makeText(this, "Chat", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_profile:
               /* Intent i = new Intent(getApplicationContext(), Profile.class);
                i.putExtra("user",user);
                startActivity(i);*/
                Bundle bundle=new Bundle();
                bundle.putString("username",user.getUsername());
                bundle.putString("fname",user.getFirstName());
                bundle.putString("lname",user.getLastName());
                bundle.putString("pic",user.getPicture());
                bundle.putInt("age",user.getAge());
                bundle.putDouble("weight",user.getWeight());
                bundle.putDouble("height",user.getHeight());
                ProfileFragment pf=new ProfileFragment();
                pf.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        pf).commit();

                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_chat:
                Bundle bundle1=new Bundle();
                bundle1.putString("username",user.getUsername());
                ChatFragment ch=new ChatFragment();
                ch.setArguments(bundle1);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        ch).commit();
                Toast.makeText(this, "Chat", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                startActivity(new Intent(getApplicationContext(),Login.class));
                Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
                break;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}