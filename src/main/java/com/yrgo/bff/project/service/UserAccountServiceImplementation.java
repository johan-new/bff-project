package com.yrgo.bff.project.service;

import com.yrgo.bff.project.dao.UserAccountDataAccess;
import com.yrgo.bff.project.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
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
        User user = userAccountDataAccess.findByUserName(username);
        userAccountDataAccess.delete(user);
        return user;
    }

    @Override
    public User updateUser(String username, String password, String newPassword) {
        User user = userAccountDataAccess.findByUserName(username);
        user.setPassword(newPassword);
        userAccountDataAccess.save(user);
        return user;
    }

    @Override
    public User readUser(String username, String password) {
        return userAccountDataAccess.findByUserName(username);
    }
}
