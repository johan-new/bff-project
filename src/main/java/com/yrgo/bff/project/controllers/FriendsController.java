package com.yrgo.bff.project.controllers;

import com.yrgo.bff.project.service.useraccount.UserAccountService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * FriendsController
 *
 * REST Controller for handling the logged in users friendlist
 *
 **/

@RestController
@ComponentScan("com.yrgo.bff.project.service")
public class FriendsController {

    @Autowired
    UserAccountService userAccountService;

    private Log log = LogFactory.getLog(getClass());



    @PostMapping("/friend")
    public ResponseEntity addFriend(@RequestBody JSONObject user) {
        final String username = (String)user.get("username");
        log.debug("******* " + username);
                userAccountService.addFriend(username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //returns the logged in users friendlist
    @GetMapping(value = "/friend/all")
    public Set<String> loadMyFriends() {
        return userAccountService.loadFriends(userAccountService.readLoggedInUser().getUsername());
    }

    @GetMapping(value = "/friends")
    public Set<String> getFriends(@RequestParam String username) {
        return userAccountService.loadFriends(username);
    }

    @DeleteMapping("/friend")
    ResponseEntity removeFriend(@RequestBody JSONObject user) {
        final String username = (String)user.get("username");
        log.debug("******* " + username);
        userAccountService.removeFriend(username);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
