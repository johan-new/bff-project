package com.yrgo.bff.project.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class FriendsTest {

    List<ApplicationUser> ar;
    ApplicationUser user;
    ApplicationUser user2;
    ApplicationUser user3;

    @BeforeEach
     void init() {
        user = new ApplicationUser("Simon", "hej");
        user2 = new ApplicationUser("Johan", "yo");
        user3 = new ApplicationUser("Erik", "yalla");


        ar = new ArrayList<>();
        ar.add(user);
        ar.add(user2);
        ar.add(user3);

        System.out.println(ar);
    }

    @Test
    public void addFriendsTest() {
        ApplicationUser userWithFriendsList = new ApplicationUser("Göte", "lol");
        ApplicationUser user01 = new ApplicationUser("Georg","lolzz");
//        for (ApplicationUser u: ar) {
//            userWithFriendsList.addFriend(u);
//        }
        userWithFriendsList.addFriend(user01);


        assertEquals(userWithFriendsList.getFriends().getFriendsGroup().size(), 1);
        assertEquals(userWithFriendsList.getFriends().getFriendsGroup().get(0), user01);
        System.out.println(userWithFriendsList.getFriends().toString());
    }

    @Test
    public void removeFriendsTest() {
        ApplicationUser userWithFriendsListAgain = new ApplicationUser("Gösta", "lolz");
        for (ApplicationUser u: ar) {
            userWithFriendsListAgain.addFriend(u);
        }
        userWithFriendsListAgain.removeFriend(ar.get(1));

        assertNotEquals(userWithFriendsListAgain.getFriends().getFriendsGroup().size(), 3);
        System.out.println(userWithFriendsListAgain.getFriends().getFriendsGroup());
    }

    @Test
    public void getAllFriendsTest() {
        ApplicationUser userWithFriendsListYetAgain = new ApplicationUser("Pål", "lolz");
        for (ApplicationUser u: ar) {
            userWithFriendsListYetAgain.addFriend(u);
        }
        assertNotNull(userWithFriendsListYetAgain.getFriends().getFriendsGroup());
        System.out.println(userWithFriendsListYetAgain.getUsername() + " vänner är: " + userWithFriendsListYetAgain.getFriends().getFriendsGroup());
    }

}