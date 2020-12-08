package com.yrgo.bff.project.controllers;

import com.yrgo.bff.project.domain.ApplicationUser;
import com.yrgo.bff.project.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendsController {

    @Autowired
    UserAccountService userAccountService;

    @PostMapping("/friend")
    public void addFriend(@RequestBody ApplicationUser user) {

    }

}
