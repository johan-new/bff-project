package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.GpsCoordinates;
import com.yrgo.bff.project.domain.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class MatchingServiceTest {

    private MatchingService matchingService;
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
    void init() {
        user1 = new UserAccount("Abraham", "Lincoln");
        user2 = new UserAccount("Noel", "Noelsson");
        user3 = new UserAccount("Bengt", "Bengan");
        user4 = new UserAccount("Glenn", "Glennsson");
        user5 = new UserAccount("Åke", "Åkesson");
        user6 = new UserAccount("Sven", "Svensson");
        location = "Stockholm";
        location2 = "Göteborg";
        usersLookingToBeMatched = new HashMap<>();

        matchingService = new MatchingServiceImplementation();
        matchingService.addUserMatchRequest(user1, location);
        matchingService.addUserMatchRequest(user2, location);
        matchingService.addUserMatchRequest(user3, location2);
        matchingService.addUserMatchRequest(user4, location2);
        matchingService.addUserMatchRequest(user5, location2);
        matchingService.addUserMatchRequest(user6, location2);

    }

    @Test
    public void testCategorizeUsersByVenue() {
        MatchingServiceImplementation matchingServiceImplementation = new MatchingServiceImplementation();

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

    @Test
    void testMatching() {
        matchingService.matchUsers();
        // Fill in with test here
    }

    @Test
    void testIntervalCheck() {
        assertTrue(MatchingServiceImplementation.isWithinInterval(0.9, 0.8, 1.0));
        assertFalse(MatchingServiceImplementation.isWithinInterval(8, 9, 10));
    }

    @Test
    void testMatchingService() {
        //same spot, Gothenburg
        assertTrue(MatchingServiceImplementation.match(new GpsCoordinates(57.708870, 11.974560), new GpsCoordinates(57.708870, 11.974560)));

        //Gothenburg vs Landvetter
        assertTrue(MatchingServiceImplementation.match(new GpsCoordinates(57.708870, 11.974560), new GpsCoordinates(57.395015, 12.165695)));

        //Gothenburg vs Boras
        assertTrue(MatchingServiceImplementation.match(new GpsCoordinates(57.708870, 11.974560), new GpsCoordinates(57.72101, 12.9401)));

        //Gothenburg vs Linköping
        assertFalse(MatchingServiceImplementation.match(new GpsCoordinates(57.708870, 11.974560), new GpsCoordinates(58.41086, 15.62157)));


        //edge cases
        assertFalse(MatchingServiceImplementation.match(new GpsCoordinates(600, 60), new GpsCoordinates(60, 60)));
        assertFalse(MatchingServiceImplementation.match(new GpsCoordinates(60, 600), new GpsCoordinates(60, 60)));
        assertFalse(MatchingServiceImplementation.match(new GpsCoordinates(60, 60), new GpsCoordinates(600, 60)));
        assertFalse(MatchingServiceImplementation.match(new GpsCoordinates(60, 60), new GpsCoordinates(60, 600)));


    }

}
