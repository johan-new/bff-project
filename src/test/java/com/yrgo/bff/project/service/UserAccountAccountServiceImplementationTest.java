package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.UserAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserAccountAccountServiceImplementationTest {

    @Autowired
    UserAccountService userAccountService;

    // Variable name is the users name and password as camel notation.
    private UserAccount user;
    private Set<UserAccount> userSet;;

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
    public void testCreateUser() throws Exception {
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
/*
    @Test  @WithMockUser(username=username)
    public void testMockUpdateUser() {
        String newPassword = "newCoolPassword";
        String oldPassword = user.getPassword();
        userAccountServiceImplementation.updateUser(oldPassword, newPassword);
        assertEquals(user.getPassword(), newPassword);
    }
*/
    @Test
    public void testValidEmailAddress(){
        assertTrue(UserAccountServiceImplementation.validEmailAddress("hej@mail.com"));
        assertFalse(UserAccountServiceImplementation.validEmailAddress("asdf"));
        assertFalse(UserAccountServiceImplementation.validEmailAddress("@"));
        assertFalse(UserAccountServiceImplementation.validEmailAddress("."));

    }
}