package com.yrgo.bff.project.controllers;

import com.yrgo.bff.project.domain.UserAccount;
import com.yrgo.bff.project.service.MatchMakingService;
import com.yrgo.bff.project.service.UserAccountService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MatchMakingController {

    @Autowired
    MatchMakingService matchMakingService;

    @Autowired
    UserAccountService userAccountService;

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        UserAccount u1 = new UserAccount("erik@mail.com", "pw");
        UserAccount u2 = new UserAccount("simon@mail.com", "pw");
        UserAccount u3 = new UserAccount("Johan@mail.com", "pw");

    }


    @GetMapping("/match/queue")
    public ResponseEntity usersLookingToBeMatched() {
        return ResponseEntity.status(HttpStatus.OK).body(matchMakingService.getUsersLookingToBeMatched());
    }
    /*
    Används ej nu, vi ska skapa en egen lista/map

    @GetMapping("/match/queue/venue")
    public ResponseEntity locationAndUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(matchMakingService.getLocationAndUsers());
    }
    */

    @PostMapping("/match")
    public ResponseEntity submitMatchingRequest(@RequestBody JSONObject location) throws Exception {
        UserAccount userObject = userAccountService.readLoggedInUser();
        String venue = (String)location.get("location");
        if (userObject!=null) {
            matchMakingService.addUserMatchRequest(userObject,venue);
            return ResponseEntity.status(HttpStatus.CREATED).body("search added");
        } else {
            throw new Exception("No such user");
        }
    }

    @DeleteMapping(value = "/match")
    public ResponseEntity cancelMatchingRequest() throws Exception {
        UserAccount userObject = userAccountService.readLoggedInUser();
        if (userObject !=null) {
            matchMakingService.removeUserMatchRequest(userObject);
            return ResponseEntity.status(HttpStatus.OK).body("Match request cancelled!");
        }
        else {
            throw new Exception("No such user");
        }

        }

    }

