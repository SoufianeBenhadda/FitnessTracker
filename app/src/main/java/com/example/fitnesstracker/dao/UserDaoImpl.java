package com.example.fitnesstracker.dao;

import com.example.fitnesstracker.connection.DBConnection;
import com.example.fitnesstracker.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao{

    DBConnection connexion;

    public UserDaoImpl() {
        connexion=new DBConnection();
    }

    @Override
    public User authenticate(String username, String password) {
        User user=new User();
        String sql="select * from user where username='"+username+"' and password='"+password+"';";
        try {
            ResultSet rs=connexion.getSt().executeQuery(sql);
            if(rs.next()) {

                user.setUsername(username);
                user.setPassword(password);
                user.setAge(rs.getInt("age"));
                user.setGender(rs.getString("gender"));
                user.setHeight(rs.getDouble("height"));
                user.setWeight(rs.getDouble("weight"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setPicture(rs.getString("picture"));
            }
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return user;
    }
}
