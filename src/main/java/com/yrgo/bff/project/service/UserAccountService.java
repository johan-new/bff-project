package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.ApplicationUser;
import org.springframework.context.ApplicationContext;

import java.util.Set;

public interface UserAccountService {
    ApplicationUser createUser(String username, String password);
    ApplicationUser removeUser(String username);
    ApplicationUser updateUser(String newPassword);
    ApplicationUser readUser(String username);
    ApplicationUser readLoggedInUser();

    Set<ApplicationUser> findAll();
}
