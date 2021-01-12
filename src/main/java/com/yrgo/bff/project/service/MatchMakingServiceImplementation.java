package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.MatchingRequest;
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
    private Map<String, List<MatchingRequest>> usersLookingToBeMatched = new HashMap<>();

    @Autowired
    NotificationService notificationService;
    @Autowired
    UserAccountService userAccountService;

    private Log log = LogFactory.getLog(getClass());

    @Override
    public void askToJoinGame(Long id) {
        getRequestByRequestId(id).ifPresent(this::joinRequest);
    }

    @Override
    public void acceptJoinRequest(Long matchingRequestId, int joinRequestId) {
        MatchingRequest matchingRequest = getRequestByRequestId(matchingRequestId).get();
        UserAccount loggedInUser = userAccountService.readLoggedInUser();

        if (loggedInUser.getUsername().equals(matchingRequest.getUsername())) {
            matchingRequest.accept(joinRequestId);
            notificationService.addNotification(loggedInUser.getUsername(),
                    matchingRequest.toString(),
                    NotificationService.Type.ACCEPTED_JOIN_REQUEST);
        } else {
            //TODO: Exception? Or return a boolean indicating success?
            log.error("ACCEPTING REQUEST ONLY POSSIBLE BY THE ORGANIZER");
        }
    }

    @Override
    public void rejectJoinRequest(Long matchingRequestId, int joinRequestId) {
        MatchingRequest matchingRequest = getRequestByRequestId(matchingRequestId).get();
        UserAccount loggedInUser = userAccountService.readLoggedInUser();

        if (loggedInUser.getUsername().equals(matchingRequest.getUsername())) {
            matchingRequest.reject(joinRequestId);
            notificationService.addNotification(loggedInUser.getUsername(),
                    matchingRequest.toString(),
                    NotificationService.Type.REJECTED_JOIN_REQUEST);
        } else {
            //TODO: Exception? Or return a boolean indicating success?
            log.error("ACCEPTING REQUEST ONLY POSSIBLE BY THE ORGANIZER");
        }
    }

    private void joinRequest(MatchingRequest joinRequest){
        UserAccount loggedInUser = userAccountService.readLoggedInUser();
        joinRequest.askToJoin(loggedInUser);

        final String organizer = joinRequest.getUsername();

        notificationService.addNotification(organizer,
                joinRequest.toString(),
                NotificationService.Type.NEW_JOIN_REQUEST);
    }


    private Optional<MatchingRequest> getRequestByRequestId(Long id) {
        Iterator it = usersLookingToBeMatched.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            for (MatchingRequest r: (List<MatchingRequest>)pair.getValue()) {
                if (r.getId().equals(id)){
                    return Optional.ofNullable(r);
                }
            }
        }
        return Optional.empty();
    }


    @Override
    public MatchingRequest addUserMatchRequest(JSONObject requestParam, String location) {
        MatchingRequest matchingRequest = new MatchingRequest(requestParam);
        if (!usersLookingToBeMatched.containsKey(location)) {
            usersLookingToBeMatched.put(location, new ArrayList<>());
        }
        usersLookingToBeMatched.get(location).add(matchingRequest);
        matchUsers();
        //class MatchingRequest don't have any set-ers, therefore no read-only wrapping
        return matchingRequest;
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