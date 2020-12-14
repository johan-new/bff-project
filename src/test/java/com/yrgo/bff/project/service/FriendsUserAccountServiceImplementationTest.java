package com.yrgo.bff.project.service;
import com.yrgo.bff.project.domain.UserAccount;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FriendsUserAccountServiceImplementationTest {

    @Autowired
    UserAccountService userAccountService;

    static final String username = "Simon@apa.com";
    static final String username2 = "Erik@apa.com";
    static final String username3 = "Johan@apa.com";

    @Test
    public void addFriendsTest() throws Exception {
        UserAccount userWithFriends = userAccountService.createUser(username, "");
        UserAccount theFriend = userAccountService.createUser(username2, "");
        userWithFriends.addFriend(theFriend);
        assertTrue(userWithFriends.getFriends().contains(theFriend.getUsername()));
    }

}
