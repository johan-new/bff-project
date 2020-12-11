package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.GpsCoordinates;
import com.yrgo.bff.project.domain.ApplicationUser;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchingServiceImplementation implements MatchingService, Runnable {

    private Map<ApplicationUser, String> usersLookingToBeMatched = new HashMap<>();
    //Denna Mapen är den "slutgiltiga" över location - lista av folk på stället
    private Map<String, ArrayList<ApplicationUser>> locationAndUsers;

    private boolean interrupt = false;

    @Override
    public void addUserMatchRequest(ApplicationUser user, String location) {
        if (!usersLookingToBeMatched.containsKey(user)) {
            usersLookingToBeMatched.put(user, location);
            System.out.println("MatchingServiceImplementation.addUserMatchRequest "+ user + " " + location);
        }
    }

    @Override
    public void removeUserMatchRequest(ApplicationUser user) {
        usersLookingToBeMatched.remove(user);
    }

    // TODO: Facade pattern??? -> Tanke här, låt matchUsers returnera matchingUsers mapen, så kallar man den på @GetMapping("/match/queue/venue").
    // TODO: forts... Just nu anropar den metoden matchUsers, sedan hämtar via getter samma lista.
    @Override
    public void matchUsers() {
        Map<String, ArrayList<ApplicationUser>>  matchingUsers = categorizeUsersByVenue();
        //sortOutSingles();
        // TODO: notifyUsers funkar ej, Null ptrexception
//        notifyUsersThatMatch(matchingUsers);
    }


    private void notifyUsersThatMatch(Map<String, ArrayList<ApplicationUser>> matchingUsers) {
        Iterator iterator = matchingUsers.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry set = (Map.Entry) iterator.next();
            for (ApplicationUser u: (ArrayList<ApplicationUser>)set.getValue() ) {
                u.notifyUser(set.getValue().toString());
                removeUserMatchRequest(u);
            }
        }
    }

    public Map<String, ArrayList<ApplicationUser>>  categorizeUsersByVenue() {
        System.out.println("Körs jag?");
        //getting unique venue values
        List<String> locationsOccurrences = new ArrayList<>();
        Iterator iterator = usersLookingToBeMatched.entrySet().iterator();
        //collecting locations UNIQUE values
        while (iterator.hasNext()) {
            Map.Entry set = (Map.Entry) iterator.next();
            final String location = (String) set.getValue();
            if (!locationsOccurrences.contains(location)) {
                locationsOccurrences.add(location);
            }
        }

        locationAndUsers = new HashMap<>();
        for (String location : locationsOccurrences) {
            List<Map.Entry<ApplicationUser, String>> usersAtThatSpecificLocation = usersLookingToBeMatched.entrySet().stream().filter(s -> s.getValue().equals(location)).collect(Collectors.toList());
            ArrayList<ApplicationUser> usersAtSpecificVenue = new ArrayList<>();
            //building a list of users at every unique venue
            for (Map.Entry<ApplicationUser, String> userStringEntry : usersAtThatSpecificLocation) {
                if (userStringEntry.getValue().equals(location)) {
                    usersAtSpecificVenue.add(userStringEntry.getKey());
                    locationAndUsers.put(location, usersAtSpecificVenue);
                }
            }
        }
        return locationAndUsers;
    }

    public static boolean match(GpsCoordinates playerA, GpsCoordinates playerB) {
        final double range = 1; // +- 0.1 corresponds to approx 10 km radius in Sweden

        return (isWithinInterval(playerA.getLongitude(), playerB.getLongitude() - range, playerB.getLongitude() + range) &&
                isWithinInterval(playerA.getLatitude(), playerB.getLatitude() - range, playerB.getLatitude() + range) &&
                isWithinInterval(playerB.getLatitude(), playerA.getLatitude() - range, playerA.getLatitude() + range) &&
                isWithinInterval(playerB.getLongitude(), playerA.getLongitude() - range, playerA.getLongitude() + range));

    }

    /**
     * Check if a value i within an interval
     */
    public static boolean isWithinInterval(double valueToCheck, double minvalue, double maxvalue) {
        return valueToCheck >= minvalue && valueToCheck <= maxvalue;
    }

    @Override
    public void run() {
        while (!interrupt) {
            matchUsers();
            try {
                Thread.sleep(10000);
            } catch (Exception silent) {
            }
        }
    }

    public Map<ApplicationUser, String> getUsersLookingToBeMatched() {
        return usersLookingToBeMatched;
    }

    public Map<String, ArrayList<ApplicationUser>> getLocationAndUsers() {
        return locationAndUsers;
    }
}
