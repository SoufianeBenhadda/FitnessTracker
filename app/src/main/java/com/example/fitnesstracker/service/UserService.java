package com.example.fitnesstracker.service;

import com.example.fitnesstracker.model.User;

public interface UserService {

    public User authenticate(String username, String password);
}
