package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.GpsCoordinates;
import com.yrgo.bff.project.domain.User;

import java.util.*;
import java.util.stream.Collectors;

public class MatchingServiceImplementation implements MatchingService, Runnable{

    // String location
    private Map<User, String> usersLookingToBeMatched = new HashMap<>();

    private boolean interrupt = false;

    @Override
    public void addUserMatchRequest(User user, String location) {
        if (!usersLookingToBeMatched.containsKey(user)){
            usersLookingToBeMatched.put(user,location);
        }
    }

    @Override
    public void removeUserMatchRequest(User user) {
        usersLookingToBeMatched.remove(user);
    }

    @Override
    public void matchUsers() {
        //locations -> gbg -> users
        Map<String,List<User>> match = new HashMap<>();
        List<String> locationsOccurrences = new ArrayList<>();

        Iterator iterator = usersLookingToBeMatched.entrySet().iterator();

        while (iterator.hasNext()){
            Map.Entry set = (Map.Entry)iterator.next();
            final String location = (String)set.getValue();
            if (!locationsOccurrences.contains(location)) {
                locationsOccurrences.add(location);
            }
            System.out.println(location + " är tillagd!");
        }

        for (String location:locationsOccurrences) {
            List <Object> usersAtThatSpecificLocation;
            usersAtThatSpecificLocation = Arrays.asList(usersLookingToBeMatched.entrySet().stream().filter(s->s.getValue().equals(location)).toArray());

            usersAtThatSpecificLocation.stream().forEach(System.out::println);
        }

        //Erik Göteborg
        // Erik



       /* for (int i = 0; i < usersLookingToBeMatched.entrySet().size(); i++)
        {
            while (iterator.hasNext()){
                Map.Entry set =
            }
        }*/

    }

    public static boolean match(GpsCoordinates playerA, GpsCoordinates playerB){
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
    public static boolean isWithinInterval(double valueToCheck, double minvalue, double maxvalue){
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
