package com.yrgo.bff.project.service;
import com.yrgo.bff.project.domain.UserAccount;

import com.yrgo.bff.project.service.useraccount.UserAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * FriendsUserAccountServiceImplementationTest
 *
 * Integration tests of friend list functionality
 **/

@SpringBootTest
public class FriendsUserAccountServiceImplementationTest {

    @Autowired
    UserAccountService userAccountService;

    static final String username = getRandomUsername();
    static final String username2 = getRandomUsername();
    static final String username3 = getRandomUsername();
    static final String username4 = getRandomUsername();
    static final String username5 = getRandomUsername();
    static final String username6 = getRandomUsername();
    static final String username7 = getRandomUsername();

    /**
     * Integration test: adding a friend
     */
    @Test @Transactional
    public void addFriendsTest() {
        userAccountService.createUser(username, "hej");
        userAccountService.createUser(username2, "hej");
        userAccountService.readUser(username).addFriend(userAccountService.readUser(username2));
        assertTrue(userAccountService.readUser(username).getFriends().contains(username2));
    }

    /**
     * Integration test: removing a friend
     */
    @Test @Transactional
    public void removeFriendsTest() {
        UserAccount userWithFriends = userAccountService.createUser(username3, "hej");
        UserAccount theFriend1 = userAccountService.createUser(username4, "hej");
        UserAccount theFriend2 = userAccountService.createUser(username5, "hej");
        userWithFriends.addFriend(theFriend1);
        userWithFriends.addFriend(theFriend2);

        //Kollar så att vännerna lagts till
        assertEquals(userWithFriends.getFriends().size(),2);
        //Tar bort en vän ur listan
        userWithFriends.removeFriend(theFriend1);
        //Kollar så att vännen togs bort ur listan
        assertEquals(userWithFriends.getFriends().size(), 1);

    }

    /**
     * @return a random "email adress"
     */
    public static String getRandomUsername() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Math.random());
        stringBuilder.append("@mail.com");
        return stringBuilder.toString();
    }


}
