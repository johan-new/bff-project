package com.yrgo.bff.project.service.matching;

import com.yrgo.bff.project.domain.MatchingRequest;
/**
 * ConversionMatchingToGame
 *
 * Handles conversion from MatchingRequest(removed afterwards) to Game (persisted).
 *
 **/

public interface ConversionMatchingToGame {
    void convertRequestToGame(MatchingRequest matchingRequest);
}
