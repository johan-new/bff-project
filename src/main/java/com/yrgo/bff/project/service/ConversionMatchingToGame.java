package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.MatchingRequest;
import com.yrgo.bff.project.domain.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class ConversionMatchingToGame {

    @Autowired
    NotificationService notificationService;
    @Autowired
    UserAccountService userAccountService;

    @Autowired
    MatchMakingService matchMakingService;

    @Autowired
    GameService gameService;

    void convertRequestToGame(MatchingRequest matchingRequest) {
        if (gameIsFull(matchingRequest)) {
            System.out.println("Game is full, creating game...");
            Set<UserAccount> participants = getParticipantsAsASet(matchingRequest);
            //TODO: Date is deprecated, refactor Game class
            //create new game with the accepted participants
            gameService.createGame(new Date(),
                    matchingRequest.getVenue(),
                    participants);
            //notify them
            notifyUsers(participants,matchingRequest);
            //remove the request
            matchMakingService.removeUserMatchRequest(matchingRequest.getId());
        }
    }

    private void notifyUsers(Set<UserAccount> participants, MatchingRequest matchingRequest) {
        for (UserAccount user:participants) {
            notificationService.addNotification(user.getUsername(),
                    "Houston, we got a match! At " + matchingRequest.getVenue() + " " +
                            matchingRequest.getTime() + ". Please go to the Game page for more info.",
                    NotificationService.Type.MATCH_SUCCESS);
        }
    }

    private Set<UserAccount> getParticipantsAsASet(MatchingRequest matchingRequest) {
        Set<UserAccount> users = new HashSet<>();
        //adding the organizer
        users.add(userAccountService.readUser(matchingRequest.getUsername()));
        //and the ones that person accepted
        for (String username: matchingRequest.getConfirmedParticipants()) {
            users.add(userAccountService.readUser(username));
        }
        return users;
    }

    private boolean gameIsFull(MatchingRequest matchingRequest) {
        return matchingRequest.getConfirmedParticipants().size() == matchingRequest.getRequestedParticipants();
    }
}
