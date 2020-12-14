package com.yrgo.bff.project.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class FriendsTest {

//    List<UserAccount> ar;
    UserAccount user = new UserAccount("Simon", "hej");
    UserAccount user2 = new UserAccount("Johan", "yo");
    UserAccount user3 = new UserAccount("Erik", "yalla");

    List<UserAccount> ar = new ArrayList<>();


    @Test
    public void addFriendsTest() {
        UserAccount userWithFriendsList = new UserAccount("Göte", "lol");
        UserAccount user01 = new UserAccount("Georg","lolzz");
//        for (ApplicationUser u: ar) {
//            userWithFriendsList.addFriend(u);
//        }
        System.out.println(user);
        System.out.println(userWithFriendsList);
        userWithFriendsList.addFriend(user01);


        assertEquals(userWithFriendsList.getFriends().getFriendsGroup().size(), 1);
        assertEquals(userWithFriendsList.getFriends().getFriendsGroup().get(0), user01);
        System.out.println(userWithFriendsList.getFriends().toString());
    }

    @Test
    public void removeFriendsTest() {
        UserAccount userWithFriendsListAgain = new UserAccount("Gösta", "lolz");
        for (UserAccount u: ar) {
            userWithFriendsListAgain.addFriend(u);
        }
        userWithFriendsListAgain.removeFriend(ar.get(1));

        assertNotEquals(userWithFriendsListAgain.getFriends().getFriendsGroup().size(), 3);
        System.out.println(userWithFriendsListAgain.getFriends().getFriendsGroup());
    }

    @Test
    public void getAllFriendsTest() {
        UserAccount userWithFriendsListYetAgain = new UserAccount("Pål", "lolz");
        for (UserAccount u: ar) {
            userWithFriendsListYetAgain.addFriend(u);
        }
        assertNotNull(userWithFriendsListYetAgain.getFriends().getFriendsGroup());
        System.out.println(userWithFriendsListYetAgain.getUsername() + " vänner är: " + userWithFriendsListYetAgain.getFriends().getFriendsGroup());
    }

}