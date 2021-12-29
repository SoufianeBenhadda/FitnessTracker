package com.example.fitnesstracker.dao;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;

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




            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://sql4.freemysqlhosting.net:3306/sql4461783",
                    "sql4461783", "57Jn33Hwd4");


            String sql="INSERT INTO tracker(userid,excerciseid,date,weight,reps,repmax)" +
                    " VALUES(?,?,?,?,?,?)";
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setString(1,userid);
            statement.setInt(2,excerciseid);
            statement.setString(3,date);
            statement.setDouble(4,weight);
            statement.setInt(5,reps);
            statement.setDouble(6,repmax);


            statement.execute();



            connection.close();

        }
        catch(Exception e)
        {
            str=e.toString();
        }

        return str;

    }
}
