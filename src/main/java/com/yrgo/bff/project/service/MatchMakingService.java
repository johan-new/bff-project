package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.UserAccount;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface MatchMakingService {

    void addUserMatchRequest(UserAccount user, String location);
    void removeUserMatchRequest(UserAccount user, String location);
    Map<String, List<String>> getUsersLookingToBeMatched();

}
