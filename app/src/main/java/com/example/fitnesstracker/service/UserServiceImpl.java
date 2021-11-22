package com.example.fitnesstracker.service;

import com.example.fitnesstracker.dao.UserDaoImpl;
import com.example.fitnesstracker.model.User;

public class UserServiceImpl implements UserService{
    private UserDaoImpl userDao;

    @Override
    public User authenticate(String username, String password) {
        return userDao.authenticate(username,password);
    }
}
