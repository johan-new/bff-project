package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.MatchingRequest;
import com.yrgo.bff.project.domain.UserAccount;
import com.yrgo.bff.project.service.matching.MatchMakingService;
import com.yrgo.bff.project.service.notification.NotificationService;
import com.yrgo.bff.project.service.useraccount.UserAccountService;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class MatchMakingServiceTest {

    private static final String user1 = "alfa@mail.com";
    private static final String user2 = "beta@mail.com";
    private static final String user3 = "gamma@mail.com";
    private static final String user4 = "epsilon@mail.com";
    private static final String user5 = "zeta@mail.com";
    private static final String user6 = "eta@mail.com";
    private static final String user7 = "theta@mail.com";

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

    @WithMockUser(username=user1)
    @Test
    public void testCategorizeUsersByVenue() throws Exception {
        String location = Double.toString(Math.random());
        String location2 = Double.toString(Math.random());
        userAccountService.createUser(user1,somePassword);
        userAccountService.createUser(user2,somePassword);
        userAccountService.createUser(user3,somePassword);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", user1);
        jsonObject.put("date", ld);
        jsonObject.put("time", lt);
        jsonObject.put("reservation", false);
        jsonObject.put("venue", venue);
        jsonObject.put("participants", 2);

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("username", user2);
        jsonObject2.put("date", ld2);
        jsonObject2.put("time", lt2);
        jsonObject2.put("reservation", true);
        jsonObject2.put("venue", venue2);
        jsonObject2.put("participants", 3);

        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("username", user3);
        jsonObject3.put("date", ld2);
        jsonObject3.put("time", lt);
        jsonObject3.put("reservation", true);
        jsonObject3.put("venue", venue4);
        jsonObject3.put("participants", 3);

        //sthlm
        matchMakingService.addUserMatchRequest(jsonObject, location);
        matchMakingService.addUserMatchRequest(jsonObject2, location);
        //gbg
        matchMakingService.addUserMatchRequest(jsonObject3, location2);

        String notifications = notificationService.getNotifications().toString();

        assertTrue(notifications.contains(NotificationService.Type.MATCH_SUCCESS.name()));


        //clean up, no pending request due to matching success
        assertThrows(NullPointerException.class,()->matchMakingService.removeUserMatchRequest(user1, location));
        matchMakingService.removeUserMatchRequest(user3, location2);

        //clean up
        userAccountService.removeUser(user1);
        userAccountService.removeUser(user2);
        userAccountService.removeUser(user3);

    }

    @WithMockUser(username=user3)
    @Test
    void testFalseNotification() {
        String location2 = Double.toString(Math.random());
        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("username", user3);
        jsonObject3.put("date", ld2);
        jsonObject3.put("time", lt);
        jsonObject3.put("reservation", true);
        jsonObject3.put("venue", venue4);
        jsonObject3.put("participants", 3);

        matchMakingService.addUserMatchRequest(jsonObject3, location2);
        String notifications = "";

        try{
            notifications = notificationService.getNotifications().toString();
        } catch(NullPointerException e)  {}

        assertFalse(notifications.contains(NotificationService.Type.MATCH_SUCCESS.name()));
        matchMakingService.removeUserMatchRequest(user3, location2);
    }

    @WithMockUser(username=user4)
    @Test
    void testFalseNotificationSeveralUsers() {
        String location = Double.toString(Math.random());
        String location2 = Double.toString(Math.random());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", user4);
        jsonObject.put("date", ld);
        jsonObject.put("time", lt);
        jsonObject.put("reservation", false);
        jsonObject.put("venue", venue);
        jsonObject.put("participants", 2);

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("username", user4);
        jsonObject2.put("date", ld2);
        jsonObject2.put("time", lt2);
        jsonObject2.put("reservation", true);
        jsonObject2.put("venue", venue2);
        jsonObject2.put("participants", 3);

        //sthlm
        matchMakingService.addUserMatchRequest(jsonObject, location);
        //gbg
        matchMakingService.addUserMatchRequest(jsonObject2, location2);

        String notifications = "";

        try{
            notifications = notificationService.getNotifications().toString();
        } catch(NullPointerException e)  {}

        assertFalse(notifications.contains(NotificationService.Type.MATCH_SUCCESS.name()));


        matchMakingService.removeUserMatchRequest(user4, location);
        matchMakingService.removeUserMatchRequest(user4, location2);
    }

    @WithMockUser(username=user6)
    @Test
    void testJoinRequests() throws Exception {
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

    @WithMockUser(username=user7)
    @Test
    void testDenyRequest() throws Exception {
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

        System.out.println(matchMakingService.getUsersLookingToBeMatched());
        //assure its pending
        assertTrue(matchMakingService.getUsersLookingToBeMatched().toString().contains("PENDING"));
        //assure organizer is notified
        assertTrue(notificationService.getNotifications().toString().contains(NotificationService.Type.NEW_JOIN_REQUEST.name()));


        matchMakingService.rejectJoinRequest(id,0);
        //assure its rejected
        assertTrue(matchMakingService.getUsersLookingToBeMatched().toString().contains("REJECTED"));
        assertTrue(matchMakingService.getUsersLookingToBeMatched().toString().contains("\"confirmedParticipants\":\"[]\""));
        //assure the the requestee is notified
        assertTrue(notificationService.getNotifications().toString().contains(NotificationService.Type.REJECTED_JOIN_REQUEST.name()));
        System.out.println(matchMakingService.getUsersLookingToBeMatched());
    }


}
