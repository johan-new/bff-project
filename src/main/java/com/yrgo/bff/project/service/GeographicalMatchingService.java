package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.GpsCoordinates;

public class GeographicalMatchingService {
    /**
     *
     * GeographicalMatchingService
     *
     * @author Johan Nyberg
    * */

    public boolean match(GpsCoordinates playerA, GpsCoordinates playerB){
        final double range = 0.1; // +- 0.1 corresponds to approx 10 km radius in Sweden

        return (isWithinInterval(playerA.getLongitude(),playerB.getLongitude()-range, playerB.getLongitude()+range) &&
                isWithinInterval(playerA.getLatitude(), playerB.getLatitude()-range, playerB.getLatitude()+range) &&
                isWithinInterval(playerB.getLatitude(),playerA.getLatitude()-range,playerA.getLatitude()+range) &&
                isWithinInterval(playerB.getLongitude(),playerA.getLongitude()-range,playerB.getLongitude()+range));

    }

    /**
     * Check if a value i within an interval
     *
     * */
    private boolean isWithinInterval(double valueToCheck, double minvalue, double maxvalue){
        return valueToCheck >= minvalue && valueToCheck <= maxvalue;
    }
}
