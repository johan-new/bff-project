package com.yrgo.bff.project.dao;


import com.yrgo.bff.project.domain.UserAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class UserAccountAccountDataAccessTest {

    @Autowired
    UserAccountDataAccess userAccountDataAccess;

    /**
     * Simple integrations test between dao and domain layer
     * for the UserAccount entities.
     */
    @Test
    void testFindByUserName() {
        final String username = "test@mail.com";
        UserAccount user = new UserAccount(username,"password");

        //persisting
        userAccountDataAccess.save(user);

        assertNotNull(userAccountDataAccess.findByUsername(username));
        assertNull(userAccountDataAccess.findByUsername(username+"asdf"));

    }


}
