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

    private static final String location = "Stockholm";
    private  static final String location2 = "Göteborg";

    @Autowired
    MatchMakingService matchMakingService;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    NotificationService notificationService;

    @WithMockUser(username=user1)
    @Test
    public void testCategorizeUsersByVenue() {
        userAccountService.createUser(user1,somePassword);
        userAccountService.createUser(user2,somePassword);

        matchMakingService.addUserMatchRequest(userAccountService.readUser(user1), location);
        matchMakingService.addUserMatchRequest(userAccountService.readUser(user2), location);

        String notifications = notificationService.getNotifications().toString();
        assertTrue(notifications.contains(NotificationService.Type.MATCH_SUCCESS.name()));
    }
/*
    @Test
    void testAddUserMatchRequest() {
        usersLookingToBeMatched.put(user1, location);
        assertFalse(usersLookingToBeMatched.containsValue("Göteborg"));
        assertTrue(usersLookingToBeMatched.containsValue("Stockholm"));
        // Lägger till samma user, ska ej läggas till i HashMapen.
        usersLookingToBeMatched.put(user1, location2);
        assertEquals(usersLookingToBeMatched.size(), 1);
    }

    @Test
    void testRemoveUserMatchRequest() {
        usersLookingToBeMatched.put(user2, location2);
        assertEquals(usersLookingToBeMatched.size(), 1);

        usersLookingToBeMatched.remove(user2);
        assertEquals(usersLookingToBeMatched.size(), 0);
    }*/


}
