package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.GpsCoordinates;
import com.yrgo.bff.project.domain.User;

import java.util.*;
import java.util.stream.Collectors;

public class MatchingServiceImplementation implements MatchingService, Runnable {

    private Map<User, String> usersLookingToBeMatched = new HashMap<>();
    List<Map.Entry<User, String>> usersAtThatSpecificLocation;
    Map<String, ArrayList<User>> locationAndUsers;

    private boolean interrupt = false;

    @Override
    public void addUserMatchRequest(User user, String location) {
        if (!usersLookingToBeMatched.containsKey(user)) {
            usersLookingToBeMatched.put(user, location);
        }
    }

    @Override
    public void removeUserMatchRequest(User user) {
        usersLookingToBeMatched.remove(user);
    }

    @Override
    public void matchUsers() {
        //locations -> gbg -> users
        Map<String, List<User>> match = new HashMap<>();
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
            usersAtThatSpecificLocation = usersLookingToBeMatched.entrySet().stream().filter(s -> s.getValue().equals(location)).collect(Collectors.toList());
//            for (int k = 0; k < location.length())
            for (int i = 0; i < locationsOccurrences.size(); i++) {
                ArrayList<User> userino = new ArrayList<>();
                for (int j = 0; j < usersAtThatSpecificLocation.size(); j++) {
                    if (usersAtThatSpecificLocation.get(j).getValue().equals(locationsOccurrences.get(i))) {
                        userino.add(usersAtThatSpecificLocation.get(j).getKey());
                    }
                    if (usersAtThatSpecificLocation.get(j).getValue().equals(locationsOccurrences.get(i))) {
                        locationAndUsers.put(locationsOccurrences.get(i), userino);
                    }
                }
            }
        }
        System.out.println("\n=======================================================================================\n");
        System.out.println();
        System.out.println("HashMap<String, ArrayList<User> :" + locationAndUsers);
        System.out.println("\n=======================================================================================\n");
        System.out.println("HashMap.get(\"Göteborg\") : " + locationAndUsers.get("Göteborg"));
        System.out.println("\n=======================================================================================\n");
        System.out.println("HashMap.get(\"Stockholm\") : " + locationAndUsers.get("Stockholm"));
        System.out.println();
        System.out.println("\n=======================================================================================\n");
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
}
