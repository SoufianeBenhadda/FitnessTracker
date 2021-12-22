package com.example.fitnesstracker.dao;

import android.os.AsyncTask;

import com.example.fitnesstracker.model.Tracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GraphDao extends AsyncTask<String, Void, List<Tracker>> {

    @Override
    protected List<Tracker> doInBackground(String... params) {

        String str=null;
        List<Tracker> trackers = new ArrayList<Tracker>();
        String userid = params[0];
        String exoid = params[1];
        try {
            //Connection connection=new DBConnexion().getConnection();
            Class.forName("com.mysql.jdbc.Driver");
            //Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.12:3306/fitness", "user1", "1234");

            Connection connection = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11460118", "sql11460118", "nlikDMLg5c");            //Connection connection = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11452971", "sql11452971", "WAYSFm5dqX");


            PreparedStatement statement = connection.prepareStatement("SELECT Max(repmax),id,userid,excerciseid,date,repmax,reps,weight from tracker where userid=? and excerciseid=? group by date ");
            statement.setString(1,userid);
            statement.setString(2,exoid);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Tracker tracker = new Tracker(rs.getInt("id"), rs.getString("userid"), rs.getInt("excerciseid"), rs.getString("date"), rs.getDouble("weight"), rs.getInt("reps"), rs.getDouble("repmax"));
                trackers.add(tracker);
            }

            connection.close();

        }
        catch(Exception e)
        {
            str = e.toString();
        }

        return trackers;
    }
}
