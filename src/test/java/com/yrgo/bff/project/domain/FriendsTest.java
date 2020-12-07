package com.yrgo.bff.project.domain;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
public class FriendsTest {

    private List<ApplicationUser> myTestArray() {
        ApplicationUser user = new ApplicationUser("Simon", "hej");
        ApplicationUser user2 = new ApplicationUser("Johan", "yo");
        ApplicationUser user3 = new ApplicationUser("Erik", "yalla");

        List<ApplicationUser> ar = new ArrayList<>();
        ar.add(user);
        ar.add(user2);
        ar.add(user3);

        return ar;
    }

    @Test
    public void addFriendsTest() {
        ApplicationUser userWithFriendsList = new ApplicationUser("Göte", "lol");
        List<ApplicationUser> dummyList = myTestArray();
        for (ApplicationUser u: dummyList) {
            userWithFriendsList.addFriend(u);
        }

        assertEquals(userWithFriendsList.getFriends().getFriendsGroup().size(), 3);
        System.out.println(userWithFriendsList.getFriends().getFriendsGroup());
    }

    @Test
    public void removeFriendsTest() {
        ApplicationUser userWithFriendsListAgain = new ApplicationUser("Gösta", "lolz");
        List<ApplicationUser> dummyList2 = myTestArray();
        for (ApplicationUser u: dummyList2) {
            userWithFriendsListAgain.addFriend(u);
        }
        userWithFriendsListAgain.removeFriend(myTestArray().remove(1));

        assertNotEquals(userWithFriendsListAgain.getFriends().getFriendsGroup().size(), 3);
 //       System.out.println(userWithFriendsListAgain.getFriends().getFriendsGroup());
    }

    @Test
    public void getAllFriendsTest() {
        ApplicationUser userWithFriendsListYetAgain = new ApplicationUser("Pål", "lolz");
        List<ApplicationUser> dummyList2 = myTestArray();
        for (ApplicationUser u: dummyList2) {
            userWithFriendsListYetAgain.addFriend(u);
        }
        assertNotNull(userWithFriendsListYetAgain.getFriends().getFriendsGroup());
//        System.out.println(userWithFriendsListYetAgain.getUsername() + " vänner är: " + userWithFriendsListYetAgain.getFriends().getFriendsGroup());
    }

}