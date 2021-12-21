package com.example.fitnesstracker.dao;

import android.os.AsyncTask;
import android.util.Log;

import com.example.fitnesstracker.model.Excercise;
import com.example.fitnesstracker.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HomeDao extends AsyncTask<Void, Void, List<Excercise>> {


    @Override
    protected List<Excercise> doInBackground(Void...Params) {
        String str="null";
        List<Excercise> exos= new ArrayList<Excercise>();

        try {
            //Connection connection=new DBConnexion().getConnection();
            Class.forName("com.mysql.jdbc.Driver");
            //Connection connection = DriverManager.getConnection("jdbc:mysql://10.12.131.24:3306/fitness", "user1", "1234");

            Connection connection = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11460118", "sql11460118", "nlikDMLg5c");            //Connection connection = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11452971", "sql11452971", "WAYSFm5dqX");



            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Excercise ");


            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                Excercise exo = new Excercise(rs.getInt("id"),rs.getString("Title"), rs.getString("description"),rs.getString("image"));


                exos.add(exo);
            }


            connection.close();

        }
        catch(Exception e)
        {
            str = e.toString();
        }

        return exos;
    }
}
