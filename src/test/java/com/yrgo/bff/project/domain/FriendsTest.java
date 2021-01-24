package com.yrgo.bff.project.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FriendsTest
 *
 * Unit tests of user friends list functionality
 *
 * */


public class FriendsTest {

    /**
     * Adding a friend
     */
    @Test
    public void addFriendsTest() {
        final String username = "Göte@mail.com";
        UserAccount userWithFriendsList = new UserAccount(username, "lol");

        userWithFriendsList.addFriend(new UserAccount(username,"lolzz"));

        System.out.println(userWithFriendsList);
        System.out.println(userWithFriendsList.getFriends());

        assertEquals(userWithFriendsList.getFriends().size(), 1);
        assertTrue(userWithFriendsList.getFriends().contains(username));
    }

    /**
     * Removing a friend
     */
    @Test
    public void removeFriendsTest() {
        final String username = "mange@mange.se";
        final UserAccount username2 = new UserAccount("kondrad@konken.se", "hej");
        final UserAccount username3 = new UserAccount("abraham@abris.se", "hej");

        UserAccount userWithFriendsList = new UserAccount(username, "lol");

        userWithFriendsList.addFriend(username2);
        userWithFriendsList.addFriend(username3);

        //Cheking that both friends has been added
        assertEquals(userWithFriendsList.getFriends().size(), 2);
        //Removing a friend
        userWithFriendsList.removeFriend(username2);
        //validating that the friend is removed
        assertEquals(userWithFriendsList.getFriends().size(), 1);

    }

}