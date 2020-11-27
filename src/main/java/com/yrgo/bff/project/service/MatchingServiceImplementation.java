package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.GpsCoordinates;
import com.yrgo.bff.project.domain.User;

import javax.swing.text.html.HTMLDocument;
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

        //collecting locations UNIQUE values
        while (iterator.hasNext()){
            Map.Entry set = (Map.Entry)iterator.next();
            final String location = (String)set.getValue();
            if (!locationsOccurrences.contains(location)) {
                locationsOccurrences.add(location);
                System.out.println(location + " är tillagd!");
            }
        }

        System.out.println(locationsOccurrences);

        for (String location:locationsOccurrences) {
            System.out.println("Går igenom " + location);
            List <Object> usersAtThatSpecificLocation;
            usersAtThatSpecificLocation = Arrays.asList(usersLookingToBeMatched.entrySet().stream().filter(s->s.getValue().equals(location)).toArray());
            usersAtThatSpecificLocation.stream().forEach(System.out::println);


            System.out.println(usersAtThatSpecificLocation.getClass());

            System.out.println("AAAAAA");
            System.out.println(usersAtThatSpecificLocation.get(0) + "\t" + usersAtThatSpecificLocation.get(0).getClass().getSimpleName());




            // iterator2 = hits.entrySet().iterator();
            List<User> matchingUsers = new ArrayList<>();
            User u = HashMap.entry(usersAtThatSpecificLocation.get(0)).getValue();

            //while (iterator2.hasNext())
            {
                //Map.Entry set = (Map.Entry)iterator2.next();
                //matchingUsers.add((User) set.getValue());
            }

            System.out.println("\nSamlat värden i denna");
            System.out.println(matchingUsers);
            System.out.println("\n");
            match.put(location,matchingUsers);
            System.out.println("----");
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
