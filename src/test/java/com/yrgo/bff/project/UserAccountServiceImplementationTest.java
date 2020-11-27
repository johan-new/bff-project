package com.yrgo.bff.project;

import com.yrgo.bff.project.dao.UserAccountDataAccess;
import com.yrgo.bff.project.domain.User;
import com.yrgo.bff.project.service.UserAccountServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserAccountServiceImplementationTest {

    @MockBean
    private UserAccountDataAccess userAccountDataAccess;

    @Autowired
    private UserAccountServiceImplementation userAccountServiceImplementation;

    // Variable name is the users name and password as camel notation.
    private User erekoPassword;
    private Set<User> userSet;

    @BeforeEach
    void init() {

        erekoPassword = new User("Ereko", "password");
        Mockito.when(userAccountDataAccess.findByUserName(erekoPassword.getUserName())).thenReturn(erekoPassword);
        Mockito.when(userAccountDataAccess.save(erekoPassword)).thenReturn(erekoPassword);

        //For testMockFindAll
        Set<User> users = new HashSet<>();
        users.add(erekoPassword);
        Mockito.when(userAccountDataAccess.findAll()).thenReturn(users);

    }

    @Test
    void testMockReadUser() {
        User found = userAccountServiceImplementation.readUser("Ereko", "password");
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
    public void testMockCreateUser() {
        User createdUser = userAccountServiceImplementation.createUser(erekoPassword.getUserName(), erekoPassword.getPassword());
        assertEquals(createdUser, erekoPassword);
    }

    @Test
    public void testMockRemoveUser() {
        userAccountServiceImplementation.removeUser(erekoPassword.getUserName(), erekoPassword.getPassword());
        Mockito.verify(userAccountDataAccess, Mockito.times(1)).delete(erekoPassword);
    }

    @Test
    public void testMockUpdateUser() {
        String newPassword = "newCoolPassword";
        userAccountServiceImplementation.updateUser(erekoPassword.getUserName(), erekoPassword.getPassword(), newPassword);
        assertEquals(erekoPassword.getPassword(), newPassword);
    }
}