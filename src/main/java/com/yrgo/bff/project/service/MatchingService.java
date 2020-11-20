package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.User;

public interface MatchingService {
    void addUserMatchRequest(User user, String location);
    void removeUserMatchRequest(User user, String location);
    void matchUsers();
}
