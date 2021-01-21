package com.yrgo.bff.project.service.matching;

import com.yrgo.bff.project.domain.MatchingRequest;
import org.json.simple.JSONObject;

/**
 * MatchMakingService
 *
 * Core logic of the applications. The implementations of this interface handles
 * - users wanting to play (the organizer)
 * - other users asking to join
 * - organizer accept och rejects these requests
 *
 * Once the spots has been filled, the MatchingRequest is removed, and a Game is
 * created instead.
 *
 **/

public interface MatchMakingService {
    MatchingRequest addUserMatchRequest(JSONObject requestParam, String location);

    void removeUserMatchRequest(String username, String location);
    void removeUserMatchRequest(Long id);
    JSONObject getUsersLookingToBeMatched();
    void askToJoinGame(Long id);
    void acceptJoinRequest(Long matchingRequestId, int joinRequestId);
    void rejectJoinRequest(Long matchingRequestId, int joinRequestId);


}