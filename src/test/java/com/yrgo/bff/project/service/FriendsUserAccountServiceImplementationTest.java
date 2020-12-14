package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.UserAccount;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FriendsUserAccountServiceImplementationTest {

    @Autowired
    UserAccountServiceImplementation userAccountServiceImplementation;

    @BeforeAll
    private List<UserAccount> init() {
        UserAccount user = new UserAccount("Simon", "hej");
        UserAccount user2 = new UserAccount("Johan", "yo");
        UserAccount user3 = new UserAccount("Erik", "yalla");

        List<UserAccount> ar = new ArrayList<>();
        ar.add(user);
        ar.add(user2);
        ar.add(user3);

        return ar;
    }

    @BeforeEach
    private void addFriends() {
        UserAccount user = userAccountServiceImplementation.readUser("Simon");
        user.addFriend(userAccountServiceImplementation.readUser("Johan"));
    }

    @Test
    public void addFriendsTest() {
        assertEquals(userAccountServiceImplementation.readUser("Simon").getFriends().getFriendsGroup().get(0).getUsername(), "Johan");
    }
}
