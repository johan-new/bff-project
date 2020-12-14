package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class MatchMakingServiceTest {

    private MatchMakingService matchMakingService;
    private UserAccount user1;
    private UserAccount user2;
    private UserAccount user3;
    private UserAccount user4;
    private UserAccount user5;
    private UserAccount user6;
    private String location = "Stockholm";
    private String location2 = "Göteborg";
    private Map<UserAccount, String> usersLookingToBeMatched;


    @BeforeEach
    void init() throws Exception {
        user1 = new UserAccount("Abraham", "Lincoln");
        user2 = new UserAccount("Noel", "Noelsson");
        user3 = new UserAccount("Bengt", "Bengan");
        user4 = new UserAccount("Glenn", "Glennsson");
        user5 = new UserAccount("Åke", "Åkesson");
        user6 = new UserAccount("Sven", "Svensson");
        location = "Stockholm";
        location2 = "Göteborg";
        usersLookingToBeMatched = new HashMap<>();

        matchMakingService = new MatchMakingServiceImplementation();
        matchMakingService.addUserMatchRequest(user1, location);
        matchMakingService.addUserMatchRequest(user2, location);
        matchMakingService.addUserMatchRequest(user3, location2);
        matchMakingService.addUserMatchRequest(user4, location2);
        matchMakingService.addUserMatchRequest(user5, location2);
        matchMakingService.addUserMatchRequest(user6, location2);

    }

    @Test
    public void testCategorizeUsersByVenue() throws Exception {
        MatchMakingServiceImplementation matchingServiceImplementation = new MatchMakingServiceImplementation();

        UserAccount userTest1 = new UserAccount("Hej", "svej");
        UserAccount userTest2 = new UserAccount("Hejdå", "re");
        String loc = "GBG";

        matchingServiceImplementation.addUserMatchRequest(userTest1, loc);
        matchingServiceImplementation.addUserMatchRequest(userTest2, loc);

        Map<String, ArrayList<UserAccount>> locationAndUsers = new HashMap<>();
        locationAndUsers = matchingServiceImplementation.categorizeUsersByVenue();

        assertEquals(locationAndUsers.size(), 1);
        assertTrue(locationAndUsers.containsKey("GBG"));
    }

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
    }


}
