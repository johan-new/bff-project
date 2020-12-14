package com.yrgo.bff.project.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
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

    //@Test
    /*public void removeFriendsTest() {
        UserAccount userWithFriendsListAgain = new UserAccount("Gösta", "lolz");
        for (UserAccount u: ar) {
            userWithFriendsListAgain.addFriend(u);
        }
        userWithFriendsListAgain.removeFriend(ar.get(1));

        assertNotEquals(userWithFriendsListAgain.getFriends().getFriendsGroup().size(), 3);
        System.out.println(userWithFriendsListAgain.getFriends().getFriendsGroup());
    }

    //@Test
    public void getAllFriendsTest() {
        UserAccount userWithFriendsListYetAgain = new UserAccount("Pål", "lolz");
        for (UserAccount u: ar) {
            userWithFriendsListYetAgain.addFriend(u);
        }
        assertNotNull(userWithFriendsListYetAgain.getFriends().getFriendsGroup());
        System.out.println(userWithFriendsListYetAgain.getUsername() + " vänner är: " + userWithFriendsListYetAgain.getFriends().getFriendsGroup());
    }*/

}