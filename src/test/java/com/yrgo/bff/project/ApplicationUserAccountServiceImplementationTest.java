package com.yrgo.bff.project;

import com.yrgo.bff.project.dao.UserAccountDataAccess;
import com.yrgo.bff.project.domain.ApplicationUser;
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
public class ApplicationUserAccountServiceImplementationTest {

    @MockBean
    private UserAccountDataAccess userAccountDataAccess;

    @Autowired
    private UserAccountServiceImplementation userAccountServiceImplementation;

    // Variable name is the users name and password as camel notation.
    private ApplicationUser erekoPassword;
    private Set<ApplicationUser> userSet;

    @BeforeEach
    void init() {

        erekoPassword = new ApplicationUser("Ereko", "password");
        Mockito.when(userAccountDataAccess.findByUsername(erekoPassword.getUsername())).thenReturn(erekoPassword);
        Mockito.when(userAccountDataAccess.save(erekoPassword)).thenReturn(erekoPassword);

        //For testMockFindAll
        Set<ApplicationUser> users = new HashSet<>();
        users.add(erekoPassword);
        Mockito.when(userAccountDataAccess.findAll()).thenReturn(users);

    }

    @Test
    void testMockReadUser() {
        ApplicationUser found = userAccountServiceImplementation.readUser("Ereko", "password");
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
        ApplicationUser createdUser = userAccountServiceImplementation.createUser(erekoPassword.getUsername(), erekoPassword.getPassword());
        assertEquals(createdUser, erekoPassword);
    }

    @Test
    public void testMockRemoveUser() {
        userAccountServiceImplementation.removeUser(erekoPassword.getUsername(), erekoPassword.getPassword());
        Mockito.verify(userAccountDataAccess, Mockito.times(1)).delete(erekoPassword);
    }

    @Test
    public void testMockUpdateUser() {
        String newPassword = "newCoolPassword";
        userAccountServiceImplementation.updateUser(erekoPassword.getUsername(), erekoPassword.getPassword(), newPassword);
        assertEquals(erekoPassword.getPassword(), newPassword);
    }
}