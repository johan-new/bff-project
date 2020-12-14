package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchMakingServiceImplementation implements MatchMakingService {

    private Map<UserAccount, String> usersLookingToBeMatched = new HashMap<>();

    @Autowired
    NotificationService notificationService;

    private Map<String, ArrayList<UserAccount>> locationAndUsers;

    @Override
    public void addUserMatchRequest(UserAccount user, String location) {
        if (!usersLookingToBeMatched.containsKey(user)) {
            usersLookingToBeMatched.put(user, location);
            matchUsers();
            System.out.println("MatchingServiceImplementation.addUserMatchRequest "+ user + " " + location);

        }
    }

    @Override
    public void removeUserMatchRequest(UserAccount user) {
        usersLookingToBeMatched.remove(user);
    }


    // TODO: Facade pattern???
    // TODO: match 4 users per venue
    private void matchUsers() {
        if (usersLookingToBeMatched.size()>1) {
            System.out.println("TRYING TO MATCH USERS");
            Map<String, ArrayList<UserAccount>>  matchingUsers = categorizeUsersByVenue();
            notifyUsersThatMatch(matchingUsers);
        }
    }

    private void notifyUsersThatMatch(Map<String, ArrayList<UserAccount>> matchingUsers) {
        Iterator iterator = matchingUsers.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry set = (Map.Entry) iterator.next();
            for (UserAccount u: (ArrayList<UserAccount>)set.getValue() ) {
                //u.notifyUser(set.getValue().toString());
                notificationService.addNotification(u.getUsername(),set.getValue().toString(), NotificationService.Type.MATCH_SUCCESS);
                removeUserMatchRequest(u);
            }
        }
    }

    public Map<String, ArrayList<UserAccount>>  categorizeUsersByVenue() {
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
            List<Map.Entry<UserAccount, String>> usersAtThatSpecificLocation = usersLookingToBeMatched.entrySet().stream().filter(s -> s.getValue().equals(location)).collect(Collectors.toList());
            ArrayList<UserAccount> usersAtSpecificVenue = new ArrayList<>();

            //building a list of users at every unique venue
            for (Map.Entry<UserAccount, String> userStringEntry : usersAtThatSpecificLocation) {
                if (userStringEntry.getValue().equals(location)) {
                    usersAtSpecificVenue.add(userStringEntry.getKey());
                    locationAndUsers.put(location, usersAtSpecificVenue);
                }
            }
        }
        return locationAndUsers;
    }

    public Map<UserAccount, String> getUsersLookingToBeMatched() {
        return usersLookingToBeMatched;
    }

    public Map<String, ArrayList<UserAccount>> getLocationAndUsers() {
        return locationAndUsers;
    }

}
