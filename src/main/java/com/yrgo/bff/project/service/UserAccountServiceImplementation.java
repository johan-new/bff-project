package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImplementation implements UserAccountService{
    @Override
    public User createUser(String username, String password) {
        return null;
    }

    @Override
    public User removeUser(String username, String password) {
        return null;
    }

    @Override
    public User updateUser(String username, String password) {
        return null;
    }

    @Override
    public User readUser(String username, String password) {
        return null;
    }
}
