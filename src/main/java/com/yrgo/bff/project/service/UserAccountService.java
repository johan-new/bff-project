package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.User;

public interface UserAccountService {
    User createUser(String username, String password);
    User removeUser(String username, String password);
    User updateUser(String username, String password);
    User readUser(String username, String password);
}