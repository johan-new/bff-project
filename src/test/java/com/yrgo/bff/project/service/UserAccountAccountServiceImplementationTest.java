package com.yrgo.bff.project.service;

import com.yrgo.bff.project.dao.UserAccountDataAccess;
import com.yrgo.bff.project.domain.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserAccountAccountServiceImplementationTest {

    @MockBean
    private UserAccountDataAccess userAccountDataAccess;

    @Autowired
    private UserAccountServiceImplementation userAccountServiceImplementation;

    // Variable name is the users name and password as camel notation.
    private UserAccount erekoPassword;
    private Set<UserAccount> userSet;

    private final String username = "ereko@mail.com";

    @BeforeEach
    void init() throws Exception {

        erekoPassword = new UserAccount(username, "password");
        Mockito.when(userAccountDataAccess.findByUsername(erekoPassword.getUsername())).thenReturn(erekoPassword);
        Mockito.when(userAccountDataAccess.save(erekoPassword)).thenReturn(erekoPassword);

        //For testMockFindAll
        Set<UserAccount> users = new HashSet<>();
        users.add(erekoPassword);
        Mockito.when(userAccountDataAccess.findAll()).thenReturn(users);

    }

    @Test
    void testMockReadUser() {
        UserAccount found = userAccountServiceImplementation.readUser(username);
        assertEquals(found, erekoPassword);
    }

    @Test
    void testMockReadUserNoPw() {

    }

    @Test
    void testMockFindAll() {
        userSet = new HashSet<>();
        userSet = userAccountServiceImplementation.findAll();
        assertEquals(userSet.size(), 1);
    }

    @Test
    public void testMockCreateUser() throws Exception {
        UserAccount createdUser = userAccountServiceImplementation.createUser(erekoPassword.getUsername(), erekoPassword.getPassword());
        assertEquals(createdUser, erekoPassword);
    }

    @Test
    public void testMockRemoveUser() {
        userAccountServiceImplementation.removeUser(erekoPassword.getUsername());
        Mockito.verify(userAccountDataAccess, Mockito.times(1)).delete(erekoPassword);
    }

    @Test  @WithMockUser(username=username)
    public void testMockUpdateUser() {
        String newPassword = "newCoolPassword";
        String oldPassword = erekoPassword.getPassword();
        userAccountServiceImplementation.updateUser(oldPassword, newPassword);
        assertEquals(erekoPassword.getPassword(), newPassword);
    }

    @Test
    public void testValidEmailAddress(){
        assertTrue(UserAccountServiceImplementation.validEmailAddress("hej@mail.com"));
        assertFalse(UserAccountServiceImplementation.validEmailAddress("asdf"));
        assertFalse(UserAccountServiceImplementation.validEmailAddress("@"));
        assertFalse(UserAccountServiceImplementation.validEmailAddress("."));

    }
}