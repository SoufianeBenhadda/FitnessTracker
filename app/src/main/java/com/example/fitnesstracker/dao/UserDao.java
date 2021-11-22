package com.example.fitnesstracker.dao;

import com.example.fitnesstracker.model.User;

public interface UserDao {

    public User authenticate(String username, String password);
}
