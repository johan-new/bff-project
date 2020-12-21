package com.yrgo.bff.project.service;

import org.json.simple.JSONObject;


public interface MatchMakingService {

    void addUserMatchRequest(JSONObject requestParam, String location);
    void removeUserMatchRequest(String username, String location);
    JSONObject getUsersLookingToBeMatched();


}
