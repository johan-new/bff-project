package com.yrgo.bff.project.domain;

import com.yrgo.bff.project.service.UserAccountService;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MatchingRequestTest {

    @Autowired
    UserAccountService userAccountService;

    @Test
    void testAskToJoin() throws Exception {
        Map request = new HashMap();
        request.put("username","someuser");
        request.put("date","2020-01-01");
        request.put("time","20:00:00");
        request.put("reservation",false);
        request.put("venue","GLTK");
        request.put("participants",3);

        MatchingRequest matchingRequest = new MatchingRequest(new JSONObject(request));

        System.out.println(matchingRequest);

        String username = "hej@mail.com";
        userAccountService.createUser(username,"asdf");

        matchingRequest.askToJoin(userAccountService.readUser(username));

        assertTrue(matchingRequest.getPendingJoinRequests().size()==1);
    }
}
