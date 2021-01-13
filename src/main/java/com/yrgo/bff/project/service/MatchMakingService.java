package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.MatchingRequest;
import com.yrgo.bff.project.domain.UserAccount;
import org.json.simple.JSONObject;


public interface MatchMakingService {

    MatchingRequest addUserMatchRequest(JSONObject requestParam, String location);
    void removeUserMatchRequest(String username, String location);
    void removeUserMatchRequest(Long id);
    JSONObject getUsersLookingToBeMatched();
    void askToJoinGame(Long id);
    void acceptJoinRequest(Long matchingRequestId, int joinRequestId);
    void rejectJoinRequest(Long matchingRequestId, int joinRequestId);


}
