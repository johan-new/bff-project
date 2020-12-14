package com.yrgo.bff.project.controllers;

import com.yrgo.bff.project.domain.UserAccount;
import com.yrgo.bff.project.service.UserAccountService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FriendsController {

    @Autowired
    UserAccountService userAccountService;


    @PostMapping("/friend")
    public void addFriend(@RequestBody JSONObject user) {
        final String username = (String)user.get("username");
                userAccountService.addFriend(username);
    }

    @GetMapping(value = "/friend")
    public List<UserAccount> loadFriends() {
        return userAccountService.loadFriends();
    }

    @DeleteMapping("/friend")
    void removeFriend(@RequestBody JSONObject user) {
        final String username = (String)user.get("username");
        userAccountService.removeFriend(username);
    }
}
