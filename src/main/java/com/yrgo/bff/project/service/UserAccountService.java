package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.UserAccount;

import java.util.List;
import java.util.Set;

public interface UserAccountService {
    UserAccount createUser(String username, String password) throws Exception;
    UserAccount removeUser(String username);
    UserAccount updateUser(String oldPassword, String newPassword);
    UserAccount readUser(String username);
    UserAccount readLoggedInUser();

    Set<UserAccount> findAll();

    void addFriend(String username);
    void removeFriend(String username);

    List<UserAccount> loadFriends();

}
