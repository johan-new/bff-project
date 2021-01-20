package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.UserAccount;
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

    @Test
    void testReadUser() {
        user = new UserAccount(FriendsUserAccountServiceImplementationTest.getRandomUsername(),"test");
        userAccountService.createUser(user.getUsername(),"test");
        assertNotNull(userAccountService.readUser(user.getUsername()));
    }

    @Test
    void testFindAll() {
        userAccountService.createUser(FriendsUserAccountServiceImplementationTest.getRandomUsername(),"test");
        assertFalse(userAccountService.findAll().isEmpty());
    }

    @Test
    public void testCreateUser() {
        //valid username and password
        assertDoesNotThrow(()->{
            userAccountService.createUser(FriendsUserAccountServiceImplementationTest.getRandomUsername(),"test");
        });
        //empty password not OK
        assertThrows(Exception.class, ()->{
            userAccountService.createUser(FriendsUserAccountServiceImplementationTest.getRandomUsername(),"");
        });
        //nor empty username
        assertThrows(Exception.class, ()->{
            userAccountService.createUser("","");
        });
        //nor invalid email address
        assertThrows(Exception.class, ()->{
            userAccountService.createUser("a","");
        });
    }

    @Test
    public void testRemoveUser() {
        String username = FriendsUserAccountServiceImplementationTest.getRandomUsername();
        userAccountService.createUser(username,"test");
        assertNotNull(userAccountService.readUser(username));
        userAccountService.removeUser(username);
        assertNull(userAccountService.readUser(username));
    }

    @Test
    public void testValidEmailAddress(){
        assertTrue(UserAccountServiceImplementation.validEmailAddress("hej@mail.com"));
        assertFalse(UserAccountServiceImplementation.validEmailAddress("asdf"));
        assertFalse(UserAccountServiceImplementation.validEmailAddress("@"));
        assertFalse(UserAccountServiceImplementation.validEmailAddress("."));
    }

    @Test @WithMockUser(username = "yetanothermockusers@mail.com")
    void testChangePassword() throws Exception {
        final String newPassword = "asdf";
        final String oldPassword = "test";

        JSONObject json = new JSONObject();
        userAccountService.createUser("yetanothermockusers@mail.com",oldPassword);

        //intentionally assigning wrong value
        json.put("oldPassword",newPassword);
        json.put("newPassword",newPassword);
        //shouldnt work to change to a new password without passing the old one
        assertThrows(Exception.class,()->userAccountService.updateUser(json));

        //should work, passing the old password as a verification
        json.put("oldPassword",oldPassword);
        assertDoesNotThrow(()->userAccountService.updateUser(json));
    }

    @Test @WithMockUser(username = "testChangeUserInformation@mail.com")
    void testChangeUserInformation() throws Exception {

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