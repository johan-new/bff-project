package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.*;

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

    @Autowired
    MatchMakingService matchMakingService;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    NotificationService notificationService;

    @WithMockUser(username=user1)
    @Test
    public void testCategorizeUsersByVenue() {
        String location = Double.toString(Math.random());
        String location2 = Double.toString(Math.random());
        userAccountService.createUser(user1,somePassword);
        userAccountService.createUser(user2,somePassword);
        userAccountService.createUser(user3,somePassword);

        //sthlm
        matchMakingService.addUserMatchRequest(userAccountService.readUser(user1), location);
        matchMakingService.addUserMatchRequest(userAccountService.readUser(user2), location);
        //gbg
        matchMakingService.addUserMatchRequest(userAccountService.readUser(user3), location2);

        String notifications = notificationService.getNotifications().toString();

        assertTrue(notifications.contains(NotificationService.Type.MATCH_SUCCESS.name()));

        //clean up
        userAccountService.removeUser(user1);
        userAccountService.removeUser(user2);
        userAccountService.removeUser(user3);

        matchMakingService.removeUserMatchRequest(userAccountService.readUser(user1));
        matchMakingService.removeUserMatchRequest(userAccountService.readUser(user2));
        matchMakingService.removeUserMatchRequest(userAccountService.readUser(user3));
    }

    @WithMockUser(username=user3)
    @Test
    void testFalseNotification(){
        String location2 = Double.toString(Math.random());

        userAccountService.createUser(user3,somePassword);
        matchMakingService.addUserMatchRequest(userAccountService.readUser(user3), location2);
        String notifications = "";

        try{
            notifications = notificationService.getNotifications().toString();
        } catch(NullPointerException e)  {}

        assertFalse(notifications.contains(NotificationService.Type.MATCH_SUCCESS.name()));

        userAccountService.removeUser(user3);
        matchMakingService.removeUserMatchRequest(userAccountService.readUser(user3));
    }

    @WithMockUser(username=user4)
    @Test
    void testFalseNotificationSeveralUsers(){
        String location = Double.toString(Math.random());
        String location2 = Double.toString(Math.random());
        userAccountService.createUser(user4,somePassword);
        userAccountService.createUser(user5,somePassword);

        //sthlm
        matchMakingService.addUserMatchRequest(userAccountService.readUser(user4), location);
        //gbg
        matchMakingService.addUserMatchRequest(userAccountService.readUser(user5), location2);

        String notifications = "";

        try{
            notifications = notificationService.getNotifications().toString();
        } catch(NullPointerException e)  {}

        assertFalse(notifications.contains(NotificationService.Type.MATCH_SUCCESS.name()));


        userAccountService.removeUser(user4);
        userAccountService.removeUser(user5);
        matchMakingService.removeUserMatchRequest(userAccountService.readUser(user4));
        matchMakingService.removeUserMatchRequest(userAccountService.readUser(user5));
    }


}
