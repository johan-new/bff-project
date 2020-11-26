package com.yrgo.bff.project;

import com.yrgo.bff.project.domain.User;
import com.yrgo.bff.project.service.UserAccountServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

//import static org.junit.jupiter.api.Assertions.assertThat;
@SpringBootTest
public class UserAccountServiceImplementationTest {

//    @MockBean
//    private UserAccountDataAccess userAccountDataAccess;

    @Autowired
    private UserAccountServiceImplementation userAccountServiceImplementation;

    private User ereko;
    private User simono;
    private User juanito;

    @BeforeEach
    void init() {
        ereko = new User("Ereko", "password");
        simono = new User("Simono", "pw");
        juanito = new User("Juanito", "admin");
    }



    @Test
    public void testReadUser() {
        User userino = userAccountServiceImplementation.createUser("Erik", "Manfredovich");
        User readUser = userAccountServiceImplementation.readUser(userino.getUserName(), userino.getPassword());
        assertNotNull(readUser);
    }

    @Test
    public void testFindAll() {
        Set<User> foundUsers = new HashSet<User>();
        userAccountServiceImplementation.createUser(ereko.getUserName(), ereko.getPassword());
        userAccountServiceImplementation.createUser(simono.getUserName(), simono.getPassword());
        userAccountServiceImplementation.createUser(juanito.getUserName(), juanito.getPassword());
        Set<User> createdUsers = new HashSet<User>();
        createdUsers.add(ereko);
        createdUsers.add(simono);
        createdUsers.add(juanito);
        foundUsers = userAccountServiceImplementation.findAll();
        assertEquals(createdUsers, foundUsers);
    }

    @Test
    public void testCreateUser() {
        User created = userAccountServiceImplementation.createUser("Ereko", "password");
        User found = userAccountServiceImplementation.readUser("Ereko", "password");
        assertEquals(created, found);
    }


    @Test
    public void testRemoveUser() {
        User heatoN = new User("HeatoN", "1337");
        userAccountServiceImplementation.createUser(heatoN.getUserName(), heatoN.getPassword());
        userAccountServiceImplementation.removeUser(heatoN.getUserName(), heatoN.getPassword());
        User nullUser = userAccountServiceImplementation.readUser(heatoN.getUserName(), heatoN.getPassword());
        assertNull(nullUser);
    }

    @Test
    public void testUpdateUser() {
        User newUser = new User("Username", "password");
        String newPassword = "newPassword";
        userAccountServiceImplementation.createUser(newUser.getUserName(), newUser.getPassword());
        User newReadUser = userAccountServiceImplementation.updateUser(newUser.getUserName(), newUser.getPassword(), newPassword);
        User newPasswordUser = userAccountServiceImplementation.readUser(newUser.getUserName(), newPassword);
        assertEquals(newPasswordUser.getPassword(), newPassword);
    }

}

//    @MockBean
//    private EmployeeRepository employeeRepository;
//
//@Before
//public void setUp() {
//        Employee alex = new Employee("alex");
//
//        Mockito.when(employeeRepository.findByName(alex.getName()))
//        .thenReturn(alex);
//        }