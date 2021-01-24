package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.MatchingRequest;
import com.yrgo.bff.project.domain.UserAccount;
import com.yrgo.bff.project.service.game.GameService;
import com.yrgo.bff.project.service.matching.MatchMakingService;
import com.yrgo.bff.project.service.notification.NotificationService;
import com.yrgo.bff.project.service.useraccount.UserAccountService;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MatchMakingServiceTest
 *
 * Integration tests of MatchMakingService functionality
 **/


@SpringBootTest
public class MatchMakingServiceTest {

    //setting up dummy data
    private static final String user1 = "alfa@mail.com";
    private static final String user2 = "beta@mail.com";
    private static final String user3 = "gamma@mail.com";
    private static final String user4 = "epsilon@mail.com";
    private static final String user5 = "zeta@mail.com";
    private static final String user6 = "eta@mail.com";
    private static final String user7 = "theta@mail.com";
    private static final String user8 = "jota@mail.com";


    private static final String somePassword = "asdf";
    private static final LocalDate ld = LocalDate.parse("2020-12-24");
    private static final LocalDate ld2 = LocalDate.parse("2020-12-25");
    private static final LocalTime lt = LocalTime.parse("17:00");
    private static final LocalTime lt2 = LocalTime.parse("18:00");
    private static final String venue = "GLTK";
    private static final String venue2 = "PadelHallen AB";
    private static final String venue3 = "Padelcenter";
    private static final String venue4 = "Padel4u";

    @Autowired
    MatchMakingService matchMakingService;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    NotificationService notificationService;

    /**
     * Integration test of posting and accepting a JoinRequest
     */
    @WithMockUser(username=user6)
    @Test
    void testJoinRequests() {
        UserAccount organizer = userAccountService.createUser(user6,"asdf");

        Map<String,Object> request = new HashMap();
        request.put("username", organizer.getUsername());
        request.put("date","2020-01-01");
        request.put("time","20:00:00");
        request.put("reservation",false);
        request.put("venue","GLTK");
        request.put("participants",3);

        MatchingRequest matchingRequest = matchMakingService.addUserMatchRequest(new JSONObject(request),"Norumsgärde");
        final Long id = matchingRequest.getId();

        matchMakingService.askToJoinGame(id);
        assertTrue(matchMakingService.getUsersLookingToBeMatched().toString().contains("PENDING"));
        matchMakingService.acceptJoinRequest(id,0);
        assertTrue(matchMakingService.getUsersLookingToBeMatched().toString().contains("ACCEPTED"));
        assertTrue(notificationService.getNotifications().toString().contains(NotificationService.Type.ACCEPTED_JOIN_REQUEST.name()));

    }

    /**
     * Integration test of rejecting a JoinRequest
     */
    @WithMockUser(username=user7)
    @Test
    void testDenyRequest() {
        UserAccount organizer = userAccountService.createUser(user7,"asdf");

        Map<String,Object> request = new HashMap();
        request.put("username", organizer.getUsername());
        request.put("date","2020-01-01");
        request.put("time","20:00:00");
        request.put("reservation",false);
        request.put("venue","GLTK");
        request.put("participants",3);

        MatchingRequest matchingRequest = matchMakingService.addUserMatchRequest(new JSONObject(request),"Partille");

        final Long id = matchingRequest.getId();

        matchMakingService.askToJoinGame(id);

        //assure its pending
        assertTrue(matchMakingService.getUsersLookingToBeMatched().toString().contains("PENDING"));
        //assure organizer is notified
        assertTrue(notificationService.getNotifications().toString().contains(NotificationService.Type.NEW_JOIN_REQUEST.name()));

        matchMakingService.rejectJoinRequest(id,0);
        //assure its rejected
        assertFalse(matchMakingService.getUsersLookingToBeMatched().toString().contains("PENDING"));
        assertTrue(matchMakingService.getUsersLookingToBeMatched().toString().contains("REJECTED"));
        //assure the the requestee is notified
        assertTrue(notificationService.getNotifications().toString().contains(NotificationService.Type.REJECTED_JOIN_REQUEST.name()));
    }


    /**
     * Integration test: will MatchMakingServiceImpl.
     * convert the MAtchingRequest to a Game when
     * full?
     */
    @WithMockUser(username=user8)
    @Test
    @Transactional
    void testConversionMatchingRequestToGame() throws Exception {
        UserAccount organizer =  userAccountService.createUser(user8,somePassword);

        final String date = "2020-03-14";
        final String time = "21:00:00";
        final String venue ="Ullevi";

        Map<String,Object> request = new HashMap();
        request.put("username", organizer.getUsername());
        request.put("date",date);
        request.put("time",time);
        request.put("reservation",true);
        request.put("venue", venue);
        request.put("participants",1);

        MatchingRequest matchingRequest = matchMakingService.addUserMatchRequest(new JSONObject(request),"Partille");
        final Long id = matchingRequest.getId();
        matchMakingService.askToJoinGame(id);
        matchMakingService.acceptJoinRequest(id,0);


        assertTrue(notificationService.getNotifications().toString().contains(NotificationService.Type.GAME_CREATED.name()));
    }


}
