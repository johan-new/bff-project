package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.UserAccount;

public interface MatchingService {
    void addUserMatchRequest(UserAccount user, String location);
    void removeUserMatchRequest(UserAccount user);
    void matchUsers();

}
