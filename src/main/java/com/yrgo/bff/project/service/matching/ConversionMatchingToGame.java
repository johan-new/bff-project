package com.yrgo.bff.project.service.matching;

import com.yrgo.bff.project.domain.MatchingRequest;

public interface ConversionMatchingToGame {
    void convertRequestToGame(MatchingRequest matchingRequest);
}
