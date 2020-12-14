package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.ApplicationUser;
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
    private List<ApplicationUser> init() {
        ApplicationUser user = new ApplicationUser("Simon", "hej");
        ApplicationUser user2 = new ApplicationUser("Johan", "yo");
        ApplicationUser user3 = new ApplicationUser("Erik", "yalla");

        List<ApplicationUser> ar = new ArrayList<>();
        ar.add(user);
        ar.add(user2);
        ar.add(user3);

        return ar;
    }

    @BeforeEach
    private void addFriends() {
        ApplicationUser user = userAccountServiceImplementation.readUser("Simon");
        user.addFriend(userAccountServiceImplementation.readUser("Johan"));
    }

    @Test
    public void addFriendsTest() {
        assertEquals(userAccountServiceImplementation.readUser("Simon").getFriends().getFriendsGroup().get(0).getUsername(), "Johan");
    }
}
