package com.example.fitnesstracker.dao;

import android.os.AsyncTask;

import com.example.fitnesstracker.model.Excercise;

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
            Connection connection = DriverManager.getConnection("jdbc:mysql://sql4.freemysqlhosting.net:3306/sql4461783",
                    "sql4461783", "57Jn33Hwd4");


            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Excercise ");


            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                Excercise exo = new Excercise(rs.getInt("id"),
                        rs.getString("Title"),
                        rs.getString("description"),rs.getString("image"));


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