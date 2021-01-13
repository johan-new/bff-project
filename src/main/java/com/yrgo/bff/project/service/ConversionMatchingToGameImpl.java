package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.MatchingRequest;
import com.yrgo.bff.project.domain.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class ConversionMatchingToGameImpl implements ConversionMatchingToGame {

    @Autowired
    NotificationService notificationService;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    MatchMakingService matchMakingService;

    @Autowired
    GameService gameService;

    public void convertRequestToGame(MatchingRequest matchingRequest) {
        System.out.println("Initiating conversion of " + matchingRequest);
        if (gameIsFull(matchingRequest)) {
            //TODO: Remove below
            Set<UserAccount> participants = getParticipantsAsASet(matchingRequest);
            //TODO: Date is deprecated, refactor Game class
            //create new game with the accepted participants
            System.out.println("Game is full, creating game...");
            gameService.createGame(new Date(),
                    matchingRequest.getVenue(),
                    participants);
            //notify them
            System.out.println("Notifing users...");
            notifyUsers(participants,matchingRequest);
            //remove the request
            System.out.println("Removing request...");
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

    @Transactional
    private Set<UserAccount> getParticipantsAsASet(MatchingRequest matchingRequest) {
        Set<UserAccount> users = new HashSet<>();
        //adding the organizer
        System.out.println("SERVICE REFERS TO " + userAccountService.toString());
        System.out.println("Putting all users in one HashSet...\nAdding organizer " + matchingRequest.getUsername());
        System.out.println(matchingRequest.getUsername().length());
        System.out.println(matchingRequest.getUsername().equals("johan@a.a"));
        System.out.println(matchingRequest.getUsername().trim().equals("johan@a.a"));
        UserAccount loggedin = userAccountService.readLoggedInUser();
        System.out.println("logged in read " + loggedin);
        UserAccount someUser = userAccountService.readUser("erik@a.a");
        UserAccount organizer = userAccountService.readUser("johan@a.a");
        System.out.println("REFERENS: " + organizer);
        users.add(organizer);
        System.out.println("Organizer added...");
        //and the ones that person accepted
        for (String username: matchingRequest.getConfirmedParticipants()) {
            users.add(userAccountService.readUser(username));
            System.out.println("added participant...");
        }
        return users;
    }

    private boolean gameIsFull(MatchingRequest matchingRequest) {
        return matchingRequest.getConfirmedParticipants().size() == matchingRequest.getRequestedParticipants();
    }
}
