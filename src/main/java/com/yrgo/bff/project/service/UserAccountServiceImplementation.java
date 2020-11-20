package com.yrgo.bff.project.service;

import com.yrgo.bff.project.dao.UserAccountDataAccess;
import com.yrgo.bff.project.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImplementation implements UserAccountService{

    @Autowired
    UserAccountDataAccess userAccountDataAccess;

    @Override
    public User createUser(String username, String password) {
        User user = new User(username,password);
        userAccountDataAccess.save(new User(username,password));
        return user;
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
