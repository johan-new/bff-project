package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.GpsCoordinates;
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

        matchMakingService = new MatchMakingServiceImplementation();
        matchMakingService.addUserMatchRequest(user1, location);
        matchMakingService.addUserMatchRequest(user2, location);
        matchMakingService.addUserMatchRequest(user3, location2);
        matchMakingService.addUserMatchRequest(user4, location2);
        matchMakingService.addUserMatchRequest(user5, location2);
        matchMakingService.addUserMatchRequest(user6, location2);

    }

    @Test
    public void testCategorizeUsersByVenue() {
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


    @Test
    void testIntervalCheck() {
        assertTrue(MatchMakingServiceImplementation.isWithinInterval(0.9, 0.8, 1.0));
        assertFalse(MatchMakingServiceImplementation.isWithinInterval(8, 9, 10));
    }

    @Test
    void testMatchingService() {
        //same spot, Gothenburg
        assertTrue(MatchMakingServiceImplementation.match(new GpsCoordinates(57.708870, 11.974560), new GpsCoordinates(57.708870, 11.974560)));

        //Gothenburg vs Landvetter
        assertTrue(MatchMakingServiceImplementation.match(new GpsCoordinates(57.708870, 11.974560), new GpsCoordinates(57.395015, 12.165695)));

        //Gothenburg vs Boras
        assertTrue(MatchMakingServiceImplementation.match(new GpsCoordinates(57.708870, 11.974560), new GpsCoordinates(57.72101, 12.9401)));

        //Gothenburg vs Linköping
        assertFalse(MatchMakingServiceImplementation.match(new GpsCoordinates(57.708870, 11.974560), new GpsCoordinates(58.41086, 15.62157)));


        //edge cases
        assertFalse(MatchMakingServiceImplementation.match(new GpsCoordinates(600, 60), new GpsCoordinates(60, 60)));
        assertFalse(MatchMakingServiceImplementation.match(new GpsCoordinates(60, 600), new GpsCoordinates(60, 60)));
        assertFalse(MatchMakingServiceImplementation.match(new GpsCoordinates(60, 60), new GpsCoordinates(600, 60)));
        assertFalse(MatchMakingServiceImplementation.match(new GpsCoordinates(60, 60), new GpsCoordinates(60, 600)));


    }

}
