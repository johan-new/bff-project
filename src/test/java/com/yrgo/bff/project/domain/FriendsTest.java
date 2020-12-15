package com.yrgo.bff.project.domain;

import org.h2.engine.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class FriendsTest {

    @Test
    public void addFriendsTest() throws Exception {
        final String username = "Göte@mail.com";
        UserAccount userWithFriendsList = new UserAccount(username, "lol");

        userWithFriendsList.addFriend(new UserAccount(username,"lolzz"));

        System.out.println(userWithFriendsList);
        System.out.println(userWithFriendsList.getFriends());

        assertEquals(userWithFriendsList.getFriends().size(), 1);
        assertTrue(userWithFriendsList.getFriends().contains(username));
    }

    @Test
    public void removeFriendsTest() throws Exception {
        final String username = "mange@mange.se";
        final UserAccount username2 = new UserAccount("kondrad@konken.se", "hej");
        final UserAccount username3 = new UserAccount("abraham@abris.se", "hej");

        UserAccount userWithFriendsList = new UserAccount(username, "lol");

        userWithFriendsList.addFriend(username2);
        userWithFriendsList.addFriend(username3);

        //Kollar så att bägge vännerna ligger i listan
        assertEquals(userWithFriendsList.getFriends().size(), 2);
        //Tar bort en vän ur listan
        userWithFriendsList.removeFriend(username2);
        //Kollar igen så att vännen togs bort
        assertEquals(userWithFriendsList.getFriends().size(), 1);

    }

}