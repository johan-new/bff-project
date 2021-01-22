package com.yrgo.bff.project.service.matching;

import com.yrgo.bff.project.domain.MatchingRequest;
import com.yrgo.bff.project.domain.UserAccount;
import com.yrgo.bff.project.service.game.GameService;
import com.yrgo.bff.project.service.notification.NotificationService;
import com.yrgo.bff.project.service.useraccount.UserAccountService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
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

    private Log log = LogFactory.getLog(getClass());

    /**
     * This core functionality basically makes the MatchingRequest, a sort of
     * a preliminary Game, to an actual Game entity. This happens when
     * the requested participants is reached (first other users
     * ask to join, and then they have to be accepted by the organizer).
     * @param matchingRequest
     */
    public void convertRequestToGame(MatchingRequest matchingRequest) {
        System.out.println("Initiating conversion of " + matchingRequest);
        if (gameIsFull(matchingRequest)) {
            Set<UserAccount> participants = getParticipantsAsASet(matchingRequest);
            //create new game with the accepted participants
            log.debug("Request succeeded, creating game...\n" + matchingRequest.toJSON());
            gameService.createGame(matchingRequest.getDate(),
                    matchingRequest.getLocalTime(),
                    matchingRequest.getVenue(),
                    matchingRequest.getLocation(),
                    participants);
            //notify them
            notifyUsers(participants,matchingRequest);
            //remove the request
            matchMakingService.removeUserMatchRequest(matchingRequest.getId());
        }
    }

    /**
     * Notifies involved participants that a game is created
     * @param participants
     * @param matchingRequest
     */
    private void notifyUsers(Set<UserAccount> participants, MatchingRequest matchingRequest) {
        for (UserAccount user:participants) {
            notificationService.addNotification(user.getUsername(),
                    MatchingRequest.GAME_CREATED_NOTIFICATION,
                    NotificationService.Type.GAME_CREATED);
        }
    }

    /**
     * Parsing a set of Strings to a set of UserAccounts.
     * @param matchingRequest
     * @return
     */
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

    /**
     * Simple check if a MatchingRequest has reached is requested participants
     *
     * @param matchingRequest
     * @return
     */
    private boolean gameIsFull(MatchingRequest matchingRequest) {
        return matchingRequest.getConfirmedParticipants().size() == matchingRequest.getRequestedParticipants();
    }
}
