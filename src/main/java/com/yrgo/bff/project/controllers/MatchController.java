package com.yrgo.bff.project.controllers;

import com.yrgo.bff.project.domain.User;
import com.yrgo.bff.project.service.MatchingService;
import com.yrgo.bff.project.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchController {

    @Autowired
    MatchingService matchingService;

    @Autowired
    UserAccountService userAccountService;


    @PostMapping(value = "/match/{user}/{location}")
    public void userWantTobeMatched(@PathVariable("user") String user, @PathVariable("location") String location) throws Exception {
        User userObject = userAccountService.readUser(user);
        if (userObject!=null) {
            matchingService.addUserMatchRequest(userObject,location);
        } else {
            throw new Exception("No such user");
        }
    }

    @DeleteMapping(value = "/match/{user}")
    public void userWantTobeMatchedNoMore(@PathVariable("user") String user) throws Exception {
        User userObject = userAccountService.readUser(user);
        if (userObject!=null) {
            matchingService.removeUserMatchRequest(userObject);
        } else {
            throw new Exception("No such user");
        }

    }
}
