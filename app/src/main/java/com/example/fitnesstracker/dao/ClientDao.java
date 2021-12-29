package com.example.fitnesstracker.dao;



import android.os.AsyncTask;

import com.example.fitnesstracker.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClientDao extends AsyncTask<String, Void, User> {


    @Override
    protected User doInBackground(String... params) {

        User user=null;
        String excep="";
        try {

            String username = params[0];



            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://sql4.freemysqlhosting.net:3306/sql4461783",
                    "sql4461783", "57Jn33Hwd4");


            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user where username=?");
            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUsername(username);
                user.setPassword(rs.getString("password"));
                user.setAge(rs.getInt("age"));
                user.setGender(rs.getString("gender"));
                user.setHeight(rs.getDouble("height"));
                user.setWeight(rs.getDouble("weight"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setPicture(rs.getString("picture"));
                user.setRole(rs.getString("role"));
            }


            connection.close();

        }
        catch(Exception e)
        {
            excep = e.toString();
        }

        return user;
    }
}

