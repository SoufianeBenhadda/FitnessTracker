package com.example.fitnesstracker.dao;

import android.os.AsyncTask;

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
            Connection connection = DriverManager.getConnection("jdbc:mysql://sql4.freemysqlhosting.net:3306/sql4461783",
                    "sql4461783", "57Jn33Hwd4");

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM tracker where userid=? and excerciseid=? and date=?");
            statement.setString(1,userid);
            statement.setString(2,exoid);
            statement.setString(3,mydate);
            // statement.setString(3,date);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                Tracker tracker = new Tracker(rs.getInt("id"), rs.getString("userid"),
                        rs.getInt("excerciseid"), rs.getString("date"),
                        rs.getDouble("weight"), rs.getInt("reps"),
                        rs.getDouble("repmax"));
                trackers.add(tracker);
            }
            connection.close();

        } catch (Exception e) {
            str = e.toString();
        }
        return trackers;
    }

}
