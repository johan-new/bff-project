package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.MatchingRequest;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchMakingServiceImplementation implements MatchMakingService {
    //location, username
    private Map<String, List<MatchingRequest>> usersLookingToBeMatched = new HashMap<>();

    @Autowired
    NotificationService notificationService;
    @Autowired
    UserAccountService userAccountService;

    private Log log = LogFactory.getLog(getClass());


    @Override
    public void addUserMatchRequest(JSONObject requestParam, String location) {
        MatchingRequest matchingRequest = new MatchingRequest(requestParam);
        if (!usersLookingToBeMatched.containsKey(location)) {
            usersLookingToBeMatched.put(location, new ArrayList<>());
        }
        usersLookingToBeMatched.get(location).add(matchingRequest);
        matchUsers();
    }

    @Override
    public void removeUserMatchRequest(String username, String location) {
        for (MatchingRequest matchingRequest : usersLookingToBeMatched.get(location)) {
            if (matchingRequest.getUsername().equals(username) && usersLookingToBeMatched.get(location).size() <= 1) {
                usersLookingToBeMatched.remove(location);
                break;
            } else if (matchingRequest.getUsername().equals(username)) {
                usersLookingToBeMatched.get(location).remove(matchingRequest);
                break;
            }
        }
    }

    private void matchUsers() {
        if (usersLookingToBeMatched.size()>1) {
            Map<String, List<MatchingRequest>> matchingUsers = usersLookingToBeMatched.entrySet().
                    stream().
                    filter(a->a.getValue().size()>1).
                    collect(Collectors.toMap(e->e.getKey(),e->e.getValue()));

            notifyUsersThatMatch(matchingUsers);

            //removing matching users
            usersLookingToBeMatched = usersLookingToBeMatched.entrySet().
                    stream().
                    filter(a->a.getValue().size()<=1).
                    collect(Collectors.toMap(e->e.getKey(),e->e.getValue()));
        }
    }

    private void notifyUsersThatMatch(Map<String, List<MatchingRequest>> matchingUsers) {
        Iterator iterator = matchingUsers.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry set = (Map.Entry) iterator.next();
            for (Object matchingRequest : (List)set.getValue()) {
                MatchingRequest castedRequest = (MatchingRequest)matchingRequest;
                notificationService.addNotification(castedRequest.getUsername(),
                        set.getValue().toString(),
                        NotificationService.Type.MATCH_SUCCESS);
            }
        }
    }

    @Override
    public JSONObject getUsersLookingToBeMatched() {
        return new JSONObject(usersLookingToBeMatched);
    }

}