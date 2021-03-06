package com.yrgo.bff.project.domain;

import com.yrgo.bff.project.service.FriendsUserAccountServiceImplementationTest;
import com.yrgo.bff.project.service.useraccount.UserAccountService;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MatchingRequestTest
 *
 * Integration tests of MatchingRequest functionality
 */

@SpringBootTest
public class MatchingRequestTest {


    @Autowired
    UserAccountService userAccountService;

    /**
     * Joining a MatchingRequest
     */
    @Test
    void testAskToJoin() {
        JSONObject request = newRequest();
        MatchingRequest matchingRequest = new MatchingRequest(request);
        System.out.println(matchingRequest);

        final String username = (String)request.get("username");

        userAccountService.createUser(username,"asdf");
        matchingRequest.askToJoin(userAccountService.readUser(username));

        assertTrue(matchingRequest.getJoinRequests().size()==1);
    }

    /**
     * Organizer accepting a JoinRequest
     */
    @Test
    void testAcceptRequest() {
        JSONObject request = newRequest();
        MatchingRequest matchingRequest = new MatchingRequest(request);
        System.out.println(matchingRequest);

        final String username = (String)request.get("username");

        userAccountService.createUser(username,"asdf");
        matchingRequest.askToJoin(userAccountService.readUser(username));
        matchingRequest.accept(0);
        assertEquals(matchingRequest.getJoinRequests().get(0).getStatus(), MatchingRequest.JoinRequestStatus.ACCEPTED);
    }

    /**
     * Organizer rejecting a JoinRequest
     */
    @Test
    void testRejectRequest() throws Exception {
        JSONObject request = newRequest();
        MatchingRequest matchingRequest = new MatchingRequest(request);
        System.out.println(matchingRequest);

        final String username = (String)request.get("username");

        userAccountService.createUser(username,"asdf");
        matchingRequest.askToJoin(userAccountService.readUser(username));
        System.out.println(matchingRequest);
        matchingRequest.reject(0);
        System.out.println(matchingRequest);
        assertEquals(matchingRequest.getJoinRequests().get(0).getStatus(), MatchingRequest.JoinRequestStatus.REJECTED);
    }

    /**
     * @return dummy data for a MatchingRequest
     */
    public JSONObject newRequest(){
        Map request = new HashMap();
        request.put("username", FriendsUserAccountServiceImplementationTest.getRandomUsername());
        request.put("date","2020-01-01");
        request.put("time","20:00:00");
        request.put("reservation",false);
        request.put("venue","GLTK");
        request.put("participants",3);
        return new JSONObject(request);
    }


}
