package com.yrgo.bff.project;

import com.yrgo.bff.project.domain.GpsCoordinates;
import com.yrgo.bff.project.domain.User;
import com.yrgo.bff.project.service.MatchingService;
import com.yrgo.bff.project.service.MatchingServiceImplementation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GpsMatchingServiceTest {

    @Test
    void testMatching(){
        User user1 = new User("Abraham","Bengtsoon");
        User user2 = new User("Noel","Jul");
        User user3 = new User("Bengt","Jul");
        String location = "Stockholm";
        String location2 = "Göteborg";

        MatchingService matchingService = new MatchingServiceImplementation();
        matchingService.addUserMatchRequest(user1,location);
        matchingService.addUserMatchRequest(user2,location);
        matchingService.addUserMatchRequest(user3,location2);

        matchingService.matchUsers();
    }

    @Test
    void testIntervalCheck(){
        assertTrue(MatchingServiceImplementation.isWithinInterval(0.9,0.8,1.0));
        assertFalse(MatchingServiceImplementation.isWithinInterval(8,9,10));
    }

    @Test
    void testMatchingService(){
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
