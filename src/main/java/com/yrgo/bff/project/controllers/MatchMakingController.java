package com.yrgo.bff.project.controllers;

import com.yrgo.bff.project.service.MatchMakingService;
import com.yrgo.bff.project.service.UserAccountService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity submitMatchingRequest(@RequestBody JSONObject request) throws Exception {
        request.put("username", userAccountService.readLoggedInUser().getUsername());
        String location = (String)request.get("location");
        if (location!=null) {
            request.remove(location);
            matchMakingService.addUserMatchRequest(request, location);
            return ResponseEntity.status(HttpStatus.CREATED).body("search added");
        } else {
            log.error("No such location");
            throw new Exception("No such location");
        }

    }


    @DeleteMapping(value = "/match")
    public ResponseEntity cancelMatchingRequest(@RequestBody JSONObject location) throws Exception {
            String username = userAccountService.readLoggedInUser().getUsername();
            matchMakingService.removeUserMatchRequest(username, (String)location.get("location"));
            return ResponseEntity.status(HttpStatus.OK).body("Match request cancelled!");
    }

    @PostMapping("/match/{matchingRequestId}")
    public ResponseEntity<String> organizersResponseToJoinRequest(
            @PathVariable(value="matchingRequestId") String matchingRequestId,
            @RequestBody JSONObject parameters){
        try {
            final int joinRequestId = (int)parameters.get("joinRequestId");
            final String action = (String)parameters.get("action");

            if (action.equals("accept")) {
                matchMakingService.acceptJoinRequest(Long.parseLong(matchingRequestId),joinRequestId);
            } else if (action.equals("reject")) {
                matchMakingService.rejectJoinRequest(Long.parseLong(matchingRequestId),joinRequestId);
            } else
            {
                throw new Exception("Action must be either accept or reject");
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/match/asktojoin/{matchingRequestId}")
    public ResponseEntity<String> userRequestsParticipation(
            @PathVariable(value="matchingRequestId") String matchingRequestId){
        try {
            matchMakingService.askToJoinGame(Long.parseLong(matchingRequestId));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}