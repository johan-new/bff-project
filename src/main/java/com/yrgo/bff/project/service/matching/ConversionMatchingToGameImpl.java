package com.yrgo.bff.project.service.matching;

import com.yrgo.bff.project.domain.MatchingRequest;
import com.yrgo.bff.project.domain.UserAccount;
import com.yrgo.bff.project.service.game.GameService;
import com.yrgo.bff.project.service.notification.NotificationService;
import com.yrgo.bff.project.service.useraccount.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            gameService.createGame(matchingRequest.getDate(),
                    matchingRequest.getLocalTime(),
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

    @Transactional
    private Set<UserAccount> getParticipantsAsASet(MatchingRequest matchingRequest) {
        Set<UserAccount> users = new HashSet<>();
        //adding the organizer
        final UserAccount organizer = userAccountService.readUser(matchingRequest.getUsername());
        users.add(organizer);
        //adding the other players
        for (String username: matchingRequest.getConfirmedParticipants()) {
            users.add(userAccountService.readUser(username));
        }
        return users;
    }

    private boolean gameIsFull(MatchingRequest matchingRequest) {
        return matchingRequest.getConfirmedParticipants().size() == matchingRequest.getRequestedParticipants();
    }
}