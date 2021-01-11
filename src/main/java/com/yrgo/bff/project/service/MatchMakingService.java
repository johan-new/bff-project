package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.MatchingRequest;
import com.yrgo.bff.project.domain.UserAccount;
import org.json.simple.JSONObject;


public interface MatchMakingService {

    MatchingRequest addUserMatchRequest(JSONObject requestParam, String location);
    void removeUserMatchRequest(String username, String location);
    JSONObject getUsersLookingToBeMatched();
    void askToJoinGame(Long id);


}
