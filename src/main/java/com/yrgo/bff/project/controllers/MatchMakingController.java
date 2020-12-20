package com.yrgo.bff.project.controllers;

import com.yrgo.bff.project.domain.UserAccount;
import com.yrgo.bff.project.service.MatchMakingService;
import com.yrgo.bff.project.service.UserAccountService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
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

    private Log log = LogFactory.getLog(getClass());


    @GetMapping("/match/queue")
    public ResponseEntity usersLookingToBeMatched() {
        return ResponseEntity.status(HttpStatus.OK).body(matchMakingService.getUsersLookingToBeMatched());
    }
    @GetMapping("/matching/pending")
    public ResponseEntity locationAndUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(matchMakingService.getUsersLookingToBeMatched());
    }


    @PostMapping("/match")
    public ResponseEntity submitMatchingRequest(@RequestBody JSONObject location) throws Exception {
        UserAccount userObject = userAccountService.readLoggedInUser();
        String venue = (String)location.get("location");
        if (userObject!=null) {
            matchMakingService.addUserMatchRequest(userObject,venue);
            return ResponseEntity.status(HttpStatus.CREATED).body("search added");
        } else {
            log.error("No such user");
            throw new Exception("No such user");
        }
    }


    @DeleteMapping(value = "/match")
    public ResponseEntity cancelMatchingRequest(@RequestBody JSONObject location) throws Exception {
        UserAccount userObject = userAccountService.readLoggedInUser();
        if (userObject !=null) {
            matchMakingService.removeUserMatchRequest(userObject,(String)location.get("location"));
            return ResponseEntity.status(HttpStatus.OK).body("Match request cancelled!");
        }
        else {
            log.error("No such user");
            throw new Exception("No such user");
        }

    }

}