package com.yrgo.bff.project.controllers;

import com.yrgo.bff.project.domain.User;
import com.yrgo.bff.project.service.MatchingService;
import com.yrgo.bff.project.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class MatchController {

    @Autowired
    MatchingService matchingService;

    @Autowired
    UserAccountService userAccountService;

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        User u1 = new User("erik@mail.com", "pw");
        User u2 = new User("simon@mail.com", "pw");
        User u3 = new User("Johan@mail.com", "pw");

    }

    @CrossOrigin
    @RequestMapping(value = "/match", headers = {
            "content-type=application/json"}, consumes =  MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public void userWantTobeMatched(@RequestBody String location) throws Exception {
        User userObject = userAccountService.readUser("Erik");
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
