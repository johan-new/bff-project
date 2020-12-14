package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.ApplicationUser;

import java.util.ArrayList;
import java.util.Map;

public interface MatchingService {
    void addUserMatchRequest(ApplicationUser user, String location);
    void removeUserMatchRequest(ApplicationUser user);
    Map<ApplicationUser, String> getUsersLookingToBeMatched();
    Map<String, ArrayList<ApplicationUser>> getLocationAndUsers();
}
