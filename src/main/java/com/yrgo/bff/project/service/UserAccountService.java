package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.ApplicationUser;

import java.util.List;
import java.util.Set;

public interface UserAccountService {
    ApplicationUser createUser(String username, String password);
    ApplicationUser removeUser(String username);
    ApplicationUser updateUser(String oldPassword, String newPassword);
    ApplicationUser readUser(String username);
    ApplicationUser readLoggedInUser();

    Set<ApplicationUser> findAll();

    void addFriend(String username);
    void removeFriend(String username);

    List<ApplicationUser> loadFriends();

}
