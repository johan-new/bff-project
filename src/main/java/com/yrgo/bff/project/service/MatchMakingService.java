package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.UserAccount;
import org.json.simple.JSONArray;

public interface MatchMakingService {

    void addUserMatchRequest(UserAccount user, String location);
    void removeUserMatchRequest(UserAccount user);
    JSONArray getUsersLookingToBeMatched();
}
