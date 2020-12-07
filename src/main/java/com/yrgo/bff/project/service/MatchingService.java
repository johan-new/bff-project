package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.ApplicationUser;

public interface MatchingService {
    void addUserMatchRequest(ApplicationUser user, String location);
    void removeUserMatchRequest(ApplicationUser user);
    void matchUsers();

}
