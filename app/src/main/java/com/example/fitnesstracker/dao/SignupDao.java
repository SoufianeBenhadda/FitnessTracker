package com.example.fitnesstracker.dao;

import android.os.AsyncTask;

import com.example.fitnesstracker.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SignupDao extends AsyncTask<String, Void, User> {


    @Override
    protected User doInBackground(String... params) {

        User user=null;
        try {

            String username = params[0];
            String password = params[1];
            String firstname=params[6];
            String lastname=params[7];
            String age=params[2];
            String weight=params[5];
            String height=params[4];
            String gender=params[3];


            //Connection connection=new DBConnexion().getConnection();
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://sql4.freemysqlhosting.net:3306/sql4461783",
                    "sql4461783", "57Jn33Hwd4");


            String sql="INSERT INTO user(username,password,age,gender,height,weight,firstName,lastName)" +
                    " VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setString(1,username);
            statement.setString(2,password);
            statement.setInt(3,Integer.parseInt(age));
            statement.setString(4,gender);
            statement.setDouble(5,Double.parseDouble(height));
            statement.setDouble(6,Double.parseDouble(weight));
            statement.setString(7,firstname);
            statement.setString(8,lastname);
            //Log.d("username",username);
            //statement.setString(9,null);
            statement.executeUpdate();

            user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setAge(Integer.parseInt(age));
            user.setGender(gender);
            user.setHeight(Double.parseDouble(height));
            user.setWeight(Double.parseDouble(weight));
            user.setFirstName(firstname);
            user.setLastName(lastname);
            //user.setPicture(rs.getString("picture"));

            connection.close();

        }
        catch(Exception e)
        {
            //excep = e.toString();
        }

        return user;
    }
}
