package com.yrgo.bff.project.controllers;

import com.yrgo.bff.project.domain.ApplicationUser;
import com.yrgo.bff.project.service.MatchingService;
import com.yrgo.bff.project.service.UserAccountService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MatchController {

    @Autowired
    MatchingService matchingService;

    @Autowired
    UserAccountService userAccountService;

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        ApplicationUser u1 = new ApplicationUser("erik@mail.com", "pw");
        ApplicationUser u2 = new ApplicationUser("simon@mail.com", "pw");
        ApplicationUser u3 = new ApplicationUser("Johan@mail.com", "pw");

    }

    @GetMapping("/match/queue")
    public ResponseEntity usersLookingToBeMatched() {
        return ResponseEntity.status(HttpStatus.OK).body(matchingService.getUsersLookingToBeMatched());
    }
    @GetMapping("/match/queue/venue")
    public ResponseEntity locationAndUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(matchingService.getLocationAndUsers());
    }


    @PostMapping("/match")
    public ResponseEntity submitMatchingRequest(@RequestBody JSONObject location) throws Exception {
        ApplicationUser userObject = userAccountService.readLoggedInUser();
        String venue = (String)location.get("location");
        if (userObject!=null) {
            matchingService.addUserMatchRequest(userObject,venue);
            return ResponseEntity.status(HttpStatus.CREATED).body("search added");
        } else {
            throw new Exception("No such user");
        }
    }

    //EJ param
    @DeleteMapping(value = "/match/{user}")
    public void cancelMatchingRequest(@PathVariable("user") String user) throws Exception {
        ApplicationUser userObject = userAccountService.readUser(user);
        if (userObject!=null) {
            matchingService.removeUserMatchRequest(userObject);
        } else {
            throw new Exception("No such user");
        }

    }
}
