package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.GpsCoordinates;
import com.yrgo.bff.project.domain.User;

import java.util.Map;

public class MatchingServiceImplementation implements MatchingService{

    // String location
    private Map<User, String> usersLookingToBeMatched;

    @Override
    public void addUserMatchRequest(User user, String location) {

    }

    @Override
    public void removeUserMatchRequest(User user, String location) {

    }

    @Override
    public void matchUsers() {

    }

    private static boolean match(GpsCoordinates playerA, GpsCoordinates playerB){
        final double range = 1; // +- 0.1 corresponds to approx 10 km radius in Sweden

        return (isWithinInterval(playerA.getLongitude(),playerB.getLongitude()-range, playerB.getLongitude()+range) &&
                isWithinInterval(playerA.getLatitude(), playerB.getLatitude()-range, playerB.getLatitude()+range) &&
                isWithinInterval(playerB.getLatitude(),playerA.getLatitude()-range,playerA.getLatitude()+range) &&
                isWithinInterval(playerB.getLongitude(),playerA.getLongitude()-range,playerA.getLongitude()+range));

    }

    /**
     * Check if a value i within an interval
     *
     * */
    private static boolean isWithinInterval(double valueToCheck, double minvalue, double maxvalue){
        return valueToCheck >= minvalue && valueToCheck <= maxvalue;
    }
}
