package com.example.fitnesstracker.dao;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.fitnesstracker.model.Excercise;
import com.example.fitnesstracker.model.Tracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TrackingTableDao extends AsyncTask<String,Void, List<Tracker>> {


    @Override
    protected List<Tracker> doInBackground(String... params) {
        String str = null;
        String userid = params[0];
        String exoid = params[1];
        String mydate = params[2];
        List<Tracker> trackers = new ArrayList<>();
        try {
            //Connection connection=new DBConnexion().getConnection();
            Class.forName("com.mysql.jdbc.Driver");
            //Connection connection = DriverManager.getConnection("jdbc:mysql://10.12.131.24:3306/fitness", "user1", "1234");
            Connection connection = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11458541", "sql11458541", "KV5M53tUtZ");            //Connection connection = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11452971", "sql11452971", "WAYSFm5dqX");


            PreparedStatement statement = connection.prepareStatement("SELECT * FROM tracker where userid=? and excerciseid=? and date=?");
            statement.setString(1,userid);
            statement.setString(2,exoid);
            statement.setString(3,mydate);
           // statement.setString(3,date);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                Tracker tracker = new Tracker(rs.getInt("id"), rs.getString("userid"), rs.getInt("excerciseid"), rs.getString("date"), rs.getDouble("weight"), rs.getInt("reps"), rs.getDouble("repmax"));
                trackers.add(tracker);
            }
            connection.close();

        } catch (Exception e) {
            str = e.toString();
        }
        return trackers;
    }

}
