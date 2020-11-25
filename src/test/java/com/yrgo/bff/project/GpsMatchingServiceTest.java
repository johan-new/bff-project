package com.yrgo.bff.project;

import com.yrgo.bff.project.domain.GpsCoordinates;
import com.yrgo.bff.project.service.GeographicalMatchingService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GpsMatchingServiceTest {

    @Test
    void testIntervalCheck(){
        assertTrue(GeographicalMatchingService.isWithinInterval(0.9,0.8,1.0));
        assertFalse(GeographicalMatchingService.isWithinInterval(8,9,10));
    }

    @Test
    void testMatchingService(){
        //same spot, Gothenburg
        assertTrue(GeographicalMatchingService.match(new GpsCoordinates(57.708870, 11.974560), new GpsCoordinates(57.708870, 11.974560)));

        //Gothenburg vs Landvetter
        assertTrue(GeographicalMatchingService.match(new GpsCoordinates(57.708870, 11.974560), new GpsCoordinates(57.395015, 12.165695)));

        //Gothenburg vs Boras
        assertFalse(GeographicalMatchingService.match(new GpsCoordinates(57.708870, 11.974560), new GpsCoordinates(57.72101, 12.9401)));

        //Gothenburg vs Marstrand
        assertFalse(GeographicalMatchingService.match(new GpsCoordinates(57.708870, 11.974560), new GpsCoordinates(57.8858 , 11.5851)));


        //edge cases
        assertFalse(GeographicalMatchingService.match(new GpsCoordinates(600, 60), new GpsCoordinates(60, 60)));
        assertFalse(GeographicalMatchingService.match(new GpsCoordinates(60, 600), new GpsCoordinates(60, 60)));
        assertFalse(GeographicalMatchingService.match(new GpsCoordinates(60, 60), new GpsCoordinates(600, 60)));
        assertFalse(GeographicalMatchingService.match(new GpsCoordinates(60, 60), new GpsCoordinates(60, 600)));


    }

}
