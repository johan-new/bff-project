package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.UserAccount;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserAccountAccountServiceImplementationTest {

    @Autowired
    UserAccountService userAccountService;

    private Log log = LogFactory.getLog(getClass());

    private UserAccount user;

    @Test
    void testReadUser() throws Exception {
        user = new UserAccount(FriendsUserAccountServiceImplementationTest.getRandomUsername(),"");
        userAccountService.createUser(user.getUsername(),"");
        assertNotNull(userAccountService.readUser(user.getUsername()));
    }

    @Test
    void testFindAll() throws Exception {
        userAccountService.createUser(FriendsUserAccountServiceImplementationTest.getRandomUsername(),"");
        assertFalse(userAccountService.findAll().isEmpty());
    }

    @Test
    public void testCreateUser() {
        assertDoesNotThrow(()->{
            userAccountService.createUser(FriendsUserAccountServiceImplementationTest.getRandomUsername(),"");
        });
        assertThrows(Exception.class, ()->{
            userAccountService.createUser("","");
        });
        assertThrows(Exception.class, ()->{
            userAccountService.createUser("a","");
        });
    }

    @Test
    public void testRemoveUser() throws Exception {
        String username = FriendsUserAccountServiceImplementationTest.getRandomUsername();
        userAccountService.createUser(username,"");
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

    @Test @WithMockUser(username = "testChangeUserInformation@mail.com")
    void testChangeUserInformation() throws Exception {
        userAccountService.createUser("testChangeUserInformation@mail.com","asdf");

        final String newPresentation = "This is me, then";
        final String newCity = "Lund";
        final String newGender = "FEMALE";
        final String newAge = "30";

        JSONObject json = new JSONObject();
        json.put("presentation",newPresentation);
        json.put("city",newCity);
        json.put("gender",newGender);
        json.put("age",newAge);

        userAccountService.updateUser(json);

        assertEquals(userAccountService.readLoggedInUser().getPresentation(), newPresentation);
        assertEquals(userAccountService.readLoggedInUser().getCity(), newCity);
        assertEquals(userAccountService.readLoggedInUser().getGender(), newGender);
        assertEquals(userAccountService.readLoggedInUser().getAge(), Integer.parseInt(newAge));

    }
}