package com.example.fitnesstracker.dao;

import android.os.AsyncTask;

import com.example.fitnesstracker.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDao extends AsyncTask<String, Void, User> {


    @Override
    protected User doInBackground(String... params) {

        User user=null;
        String excep="";
        try {

            String username = params[0];
            String password = params[1];


            //Connection connection=new DBConnexion().getConnection();
            Class.forName("com.mysql.jdbc.Driver");
            //Connection connection = DriverManager.getConnection("jdbc:mysql://10.12.131.24:3306/fitness", "user1", "1234");
            //Connection connection = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11458541", "sql11458541", "KV5M53tUtZ");
            //Connection connection = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11452971", "sql11452971", "WAYSFm5dqX");
            Connection connection = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11460118", "sql11460118", "nlikDMLg5c");


                PreparedStatement statement = connection.prepareStatement("SELECT * FROM user where username=? and password=?");
                statement.setString(1, username);
                statement.setString(2, password);

                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
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
