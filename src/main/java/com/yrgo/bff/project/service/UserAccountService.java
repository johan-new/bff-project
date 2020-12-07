package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.ApplicationUser;

import java.util.Set;

public interface UserAccountService {
    ApplicationUser createUser(String username, String password);
    ApplicationUser removeUser(String username, String password);
    ApplicationUser updateUser(String username, String password, String newPassword);
    ApplicationUser readUser(String username, String password);
    ApplicationUser readUser(String username);

    Set<ApplicationUser> findAll();
}
