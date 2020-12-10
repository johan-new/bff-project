package com.yrgo.bff.project.controllers;

import com.yrgo.bff.project.domain.ApplicationUser;
import com.yrgo.bff.project.domain.Friends;
import com.yrgo.bff.project.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FriendsController {

    @Autowired
    UserAccountService userAccountService;


    @PostMapping("/friend")
    public void addFriend(ApplicationUser user) {
        userAccountService.addFriend(user);
    }

    @GetMapping("/friend")
    public List<ApplicationUser> loadFriends() {
       return userAccountService.loadFriends();
    }

    @DeleteMapping("/friend")
    void removeFriend(ApplicationUser user) {
        userAccountService.removeFriend(user);
    }
}
