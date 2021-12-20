package com.example.fitnesstracker.dao;

import android.os.AsyncTask;
import android.util.Log;

import com.example.fitnesstracker.connection.DBConnexion;
import com.example.fitnesstracker.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProfileDao extends AsyncTask<String, Void, User> {


    @Override
    protected User doInBackground(String... params) {

        User user=null;
        try {

            String username = params[0];
            String age=params[1];
            String weight=params[3];
            String height=params[2];
            String firstn=params[4];
            String lastn=params[5];
            String image=params[6];


            Class.forName("com.mysql.jdbc.Driver");
            //Connection connection = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11458541", "sql11458541", "KV5M53tUtZ");
            Connection connection = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11460118", "sql11460118", "nlikDMLg5c");

            String sql="UPDATE user SET age =?,weight=?,height=? WHERE username=?";
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setString(4,username);
            statement.setInt(1,Integer.parseInt(age));
            statement.setDouble(3,Double.parseDouble(height));
            statement.setDouble(2,Double.parseDouble(weight));

            //Log.d("username",username);
            //statement.setString(9,null);
            statement.executeUpdate();

            user = new User();
            user.setUsername(username);
            user.setFirstName(firstn);
            user.setLastName(lastn);
            user.setAge(Integer.parseInt(age));
            user.setHeight(Double.parseDouble(height));
            user.setWeight(Double.parseDouble(weight));
            user.setPicture(image);

            connection.close();

        }
        catch(Exception e)
        {
            //excep = e.toString();
        }

        return user;
    }
}
