package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.User;

import java.util.Map;

public class MatchingServiceImplementation implements MatchingService{

    // String location
    private Map<User, String> usersLookingToBeMatched;

    @Override
    public void addUserMatchRequest(User user, String location) {

    }

    @Override
    public void removeUserMatchRequest(User user, String location) {

    }

    @Override
    public void matchUsers() {

    }
}
