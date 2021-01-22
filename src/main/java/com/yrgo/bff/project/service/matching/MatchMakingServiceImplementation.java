package com.yrgo.bff.project.service.matching;

import com.yrgo.bff.project.domain.MatchingRequest;
import com.yrgo.bff.project.domain.UserAccount;
import com.yrgo.bff.project.service.notification.NotificationService;
import com.yrgo.bff.project.service.useraccount.UserAccountService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MatchMakingServiceImplementation implements MatchMakingService {
    //location, username
    private Map<String, List<MatchingRequest>> usersLookingToBeMatched = new HashMap<>();

    @Autowired
    NotificationService notificationService;
    @Autowired
    UserAccountService userAccountService;

    @Autowired
    ConversionMatchingToGame converter;

    private Log log = LogFactory.getLog(getClass());

    /**
     * Looking up MatchingRequest by its id number and
     * if succeeded a joinRequest is made
     * @param id
     */
    @Override
    public void askToJoinGame(Long id) {
        getRequestByRequestId(id).ifPresent(this::joinRequest);
    }

    /**
     * Used by an organizer to accept a join request
     *
     * @param matchingRequestId
     * @param joinRequestId
     */
    @Override
    public void acceptJoinRequest(Long matchingRequestId, int joinRequestId) {
        MatchingRequest matchingRequest = getRequestByRequestId(matchingRequestId).get();
        UserAccount loggedInUser = userAccountService.readLoggedInUser();

        if (loggedInUser.getUsername().equals(matchingRequest.getUsername())) {
            matchingRequest.accept(joinRequestId);
            try {
                final String sender = matchingRequest.getJoinRequests().get(joinRequestId).getSender();
                notificationService.addNotification(sender,
                        MatchingRequest.ACCEPTED_JOIN_NOTIFICATION,
                        NotificationService.Type.ACCEPTED_JOIN_REQUEST);
            } catch (NullPointerException e) {
                log.error("NullPointer in acceptJoinRequest(... Probably caused due to sender username is null");
            }
            converter.convertRequestToGame(matchingRequest);
        } else { //this will never occur due to the use of this method
            log.error("Accepting MatchingRequest #" + matchingRequest + ",  JoinRequest #" + joinRequestId +
                    " not possible. Can only be done by the organizer.");
        }
    }


    /**
     * Used by an organizer to reject a join request
     * @param matchingRequestId
     * @param joinRequestId
     */
    @Override
    public void rejectJoinRequest(Long matchingRequestId, int joinRequestId) {
        MatchingRequest matchingRequest = getRequestByRequestId(matchingRequestId).get();
        UserAccount loggedInUser = userAccountService.readLoggedInUser();

        if (loggedInUser.getUsername().equals(matchingRequest.getUsername())) {
            matchingRequest.reject(joinRequestId);
            try {
                notificationService.addNotification(loggedInUser.getUsername(),
                        MatchingRequest.REJECTED_JOIN_NOTIFICATION,
                        NotificationService.Type.REJECTED_JOIN_REQUEST);
            } catch (NullPointerException e) {
                log.error("NullPointer in rejectJoinRequest(... Probably caused due to sender username is null");
            }
        } else {//this will never occur due to the use of this method
            log.error("Rejecting MatchingRequest #" + matchingRequest + ",  JoinRequest #" + joinRequestId +
                    " not possible. Can only be done by the organizer.");
        }
    }

    /**
     * Sending join request from the logged in user, and notifies the organizer
     * @param joinRequest
     */
    private void joinRequest(MatchingRequest joinRequest){
        UserAccount loggedInUser = userAccountService.readLoggedInUser();
        joinRequest.askToJoin(loggedInUser);

        final String organizer = joinRequest.getUsername();

        notificationService.addNotification(organizer,
                MatchingRequest.NEW_JOIN_NOTIFICATION,
                NotificationService.Type.NEW_JOIN_REQUEST);
    }


    /**
     * @param id
     * @return returns a MatchingRequest object if it exists,
     *          wrapped around a Optional to simplify null handling.
     */
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


    /**
     * Initiate a MatchingRequest, and sorted by its location.
     *
     * @param requestParam
     * @param location
     * @return returns the created MatchinGRequest
     */
    @Override
    public MatchingRequest addUserMatchRequest(JSONObject requestParam, String location) {
        MatchingRequest matchingRequest = new MatchingRequest(requestParam);
        if (!usersLookingToBeMatched.containsKey(location)) {
            usersLookingToBeMatched.put(location, new ArrayList<>());
        }
        usersLookingToBeMatched.get(location).add(matchingRequest);
        //matchUsers();
        //class MatchingRequest don't have any set-ers, therefore no read-only wrapping
        return matchingRequest;
    }

    /**
     * Removes a MatchingRequest. Since it is sorted by the method
     * requires that argument to know which key to use in usersLookingToBeMatched
     * @param username the ones sending the removal request
     * @param location key in the Map usersLookingToBeMatched
     */
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


    /**
     * @return parsing the data to JSON
     */
    @Override
    public JSONObject getUsersLookingToBeMatched() {
        return new JSONObject(usersLookingToBeMatched);
    }

    /**
     * Removes a MatchingRequest by its id number. Since the data
     * in usersLookingToBeMatched is not structured by its id the
     * below approach is required.
     * @param id
     */
    @Override
    public void removeUserMatchRequest(Long id) {
        String location = "";
        String username = "";

        Iterator iterator = usersLookingToBeMatched.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry set = (Map.Entry) iterator.next();
            for (Object matchingRequest : (List)set.getValue()) {
                if (((MatchingRequest)matchingRequest).getId() == id ){
                    location = (String)set.getKey();
                    username = ((MatchingRequest) matchingRequest).getUsername();
                }
            }
        }

        if (!location.isBlank() && !username.isBlank()) {
            removeUserMatchRequest(username,location);
        }

    }
}