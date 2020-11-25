package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.User;

import java.util.Set;

public interface UserAccountService {
    User createUser(String username, String password);
    User removeUser(String username, String password);
    User updateUser(String username, String password, String newPassword);
    User readUser(String username, String password);

    Set<User> findAll();
}
