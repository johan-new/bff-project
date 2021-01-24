package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.UserAccount;
import com.yrgo.bff.project.exception.BadRequestException;
import com.yrgo.bff.project.service.useraccount.UserAccountService;
import com.yrgo.bff.project.service.useraccount.UserAccountServiceImplementation;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UserAccountAccountServiceImplementationTest
 *
 * Integrations tests
 *
 */

@SpringBootTest
public class UserAccountAccountServiceImplementationTest {

    @Autowired
    UserAccountService userAccountService;

    private Log log = LogFactory.getLog(getClass());

    private UserAccount user;

    /**
     * testing to read a user
     */
    @Test
    void testReadUser() {
        user = new UserAccount(FriendsUserAccountServiceImplementationTest.getRandomUsername(),"test");
        userAccountService.createUser(user.getUsername(),"test");
        assertNotNull(userAccountService.readUser(user.getUsername()));
    }

    /**
     * testing to find all users
     */
    @Test
    void testFindAll() {
        userAccountService.createUser(FriendsUserAccountServiceImplementationTest.getRandomUsername(),"test");
        assertFalse(userAccountService.findAll().isEmpty());
    }

    /**
     * testing to create a user
     */
    @Test
    public void testCreateUser() {
        //empty password not OK
        assertThrows(BadRequestException.class, ()->{
            userAccountService.createUser(FriendsUserAccountServiceImplementationTest.getRandomUsername(),"");
        });
        //nor empty username
        assertThrows(BadRequestException.class, ()->{
            userAccountService.createUser("","");
        });
        //nor invalid email address
        assertThrows(BadRequestException.class, ()->{
            userAccountService.createUser("a","");
        });
        //valid username and password
        assertDoesNotThrow(()->{
            userAccountService.createUser(FriendsUserAccountServiceImplementationTest.getRandomUsername(),"test");
        });
    }

    /**
     * testing to remove a user
     */
    @Test
    public void testRemoveUser() {
        String username = FriendsUserAccountServiceImplementationTest.getRandomUsername();
        userAccountService.createUser(username,"test");
        assertNotNull(userAccountService.readUser(username));
        userAccountService.removeUser(username);
        assertNull(userAccountService.readUser(username));
    }

    /**
     * testing if email address has valid format
     */
    @Test
    public void testValidEmailAddress(){
        assertTrue(UserAccountServiceImplementation.validEmailAddress("hej@mail.com"));
        assertFalse(UserAccountServiceImplementation.validEmailAddress("asdf"));
        assertFalse(UserAccountServiceImplementation.validEmailAddress("@"));
        assertFalse(UserAccountServiceImplementation.validEmailAddress("."));
    }

    /**
     * testing to change password
     */
    @Test @WithMockUser(username = "yetanothermockusers@mail.com")
    void testChangePassword() {
        final String newPassword = "asdf";
        final String oldPassword = "test";

        JSONObject json = new JSONObject();
        userAccountService.createUser("yetanothermockusers@mail.com",oldPassword);

        //intentionally assigning wrong value
        json.put("oldPassword","wrong password");
        json.put("newPassword",newPassword);
        //shouldn't work to change to a new password without passing the correct old one
        assertThrows(BadRequestException.class,()->userAccountService.updateUser(json));

        //should work, passing the old password as a verification
        json.put("oldPassword",oldPassword);
        assertDoesNotThrow(()->userAccountService.updateUser(json));
    }

    /**
     * test of changing user personal data
     */
    @Test @WithMockUser(username = "testChangeUserInformation@mail.com")
    void testChangeUserInformation() {
        userAccountService.createUser("testChangeUserInformation@mail.com","test");

        final String newPresentation = "This is me, then";
        final String newCity = "Lund";
        final String newGender = "FEMALE";
        final int newAge = 30;

        JSONObject json = new JSONObject();
        json.put("presentation",newPresentation);
        json.put("city",newCity);
        json.put("gender",newGender);
        json.put("age",newAge);

        userAccountService.updateUser(json);
        assertEquals(userAccountService.readLoggedInUser().getPresentation(), newPresentation);
        assertEquals(userAccountService.readLoggedInUser().getCity(), newCity);
        assertEquals(userAccountService.readLoggedInUser().getGender(), newGender);
        assertEquals(userAccountService.readLoggedInUser().getAge(), newAge);

    }
}