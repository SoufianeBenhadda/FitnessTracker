package com.example.fitnesstracker.dao;

import android.os.AsyncTask;

import com.example.fitnesstracker.model.User;
import com.example.fitnesstracker.model.Tracker;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TrackingDao extends AsyncTask<String, Void, String> {


    @Override
    protected String doInBackground(String... params) {

        String str=null;
        try {

            String userid = params[0];
            int excerciseid = Integer.valueOf(params[1]);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String date = sdf.format(System.currentTimeMillis());

            double weight=Double.valueOf(params[2]);
            int reps=Integer.valueOf(params[3]);
            double repmax=weight/( 1.0278 - 0.0278 * reps );



            //Connection connection=new DBConnexion().getConnection();
            Class.forName("com.mysql.jdbc.Driver");
            //Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.12:3306/fitness", "user1", "1234");
            Connection connection = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11458541", "sql11458541", "KV5M53tUtZ");
            //Connection connection = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11452971", "sql11452971", "WAYSFm5dqX");



            String sql="INSERT INTO tracker(userid,excerciseid,date,weight,reps,repmax)" +
                    " VALUES(?,?,?,?,?,?)";
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setString(1,userid);
            statement.setInt(2,excerciseid);
            statement.setString(3,date);
            statement.setDouble(4,weight);
            statement.setInt(5,reps);
            statement.setDouble(6,repmax);

            //Log.d("username",username);
            //statement.setString(9,null);
            statement.execute();


            //user.setPicture(rs.getString("picture"));

            connection.close();

        }
        catch(Exception e)
        {
            //error = e.toString();
            str=e.toString();
        }

        return str;

    }
}
