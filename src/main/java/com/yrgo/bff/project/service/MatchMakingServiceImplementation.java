package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.UserAccount;
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
    private Map<String, List<String>> usersLookingToBeMatched = new HashMap<>();

    @Autowired
    NotificationService notificationService;
    @Autowired
    UserAccountService userAccountService;

    private Log log = LogFactory.getLog(getClass());


    @Override
    public void addUserMatchRequest(UserAccount user, String location) {
        if (!usersLookingToBeMatched.containsKey(location)) {
            usersLookingToBeMatched.put(location, new ArrayList<>());
        }
        usersLookingToBeMatched.get(location).add(user.getUsername());
        matchUsers();
    }

    @Override
    public void removeUserMatchRequest(UserAccount user, String location) {
        log.debug("removeUserMatchRequest\nTAR BORT " + user.getUsername() + " FRÅN " + location + "\nINNAN" + usersLookingToBeMatched.get(location));
        usersLookingToBeMatched.get(location).remove(user.getUsername());
        log.debug("\nEFTER" + usersLookingToBeMatched.get(location));
    }

    private void matchUsers() {
        if (usersLookingToBeMatched.size()>1) {
            Map<String, List<String>> matchingUsers = usersLookingToBeMatched.entrySet().
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

    private void notifyUsersThatMatch(Map<String, List<String>> matchingUsers) {
        Iterator iterator = matchingUsers.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry set = (Map.Entry) iterator.next();
            for (String username: (ArrayList<String>)set.getValue() ) {
                notificationService.addNotification(username,set.getValue().toString(), NotificationService.Type.MATCH_SUCCESS);
            }
        }
    }

    @Override
    public Map<String, List<String>> getUsersLookingToBeMatched() {
        return Collections.unmodifiableMap(usersLookingToBeMatched);
    }

}
