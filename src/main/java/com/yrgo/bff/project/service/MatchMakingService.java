package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.UserAccount;

import java.util.ArrayList;
import java.util.Map;

public interface MatchMakingService {

    void addUserMatchRequest(UserAccount user, String location);
    void removeUserMatchRequest(UserAccount user);
    Map<UserAccount, String> getUsersLookingToBeMatched();
    Map<String, ArrayList<UserAccount>> getLocationAndUsers();

}
