package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.UserAccount;
import org.json.simple.JSONArray;
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

        //sorting out singles
        locationAndUsers=locationAndUsers.entrySet().stream().filter(s->s.getValue().size()>1).collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (oldValue,newValue)->oldValue,HashMap::new));


        return locationAndUsers;
    }


//    public Map<UserAccount, String> getUsersLookingToBeMatched() {
    public JSONArray getUsersLookingToBeMatched() {
        System.out.println("usersLookingToBM i getU... " + usersLookingToBeMatched);
//        Map<String, Map<String, String>> theQueue = new HashMap<>();

        JSONArray a = new JSONArray();

        for (Map.Entry<UserAccount, String> queueingUser : usersLookingToBeMatched.entrySet()) {
            Map<String, String> playerQueue = new HashMap<>();
            playerQueue.put("username", queueingUser.getKey().getUsername());
            playerQueue.put("location", queueingUser.getValue());
            a.add(playerQueue);
        }
        return a;


    }
//    Map<String, String> gameDetails = new HashMap<>();
//    List<String> players = new ArrayList<>();
//
//        for (UserAccount user : participants) {
//        players.add(user.getUsername());
//    }
//
//        gameDetails.put("id", getId().toString());
//        gameDetails.put("when", getWhen().toString());
//        gameDetails.put("venue", getVenue());
//        gameDetails.put("players", players.toString());
//
//        return gameDetails;


//    public JSONObject getPreviousGamesAsJSON() {
//        try {
//            Map<String, Map<String, String>> previousGamesMapped = new HashMap<>();
//
//            for (Game game : previousGames) {
//                previousGamesMapped.put(game.getId().toString(), game.mapGameDetails());
//            }
//
//            return new JSONObject(previousGamesMapped);
//        } catch (NullPointerException e) {
//            return new JSONObject();
//        }
//    }




}
