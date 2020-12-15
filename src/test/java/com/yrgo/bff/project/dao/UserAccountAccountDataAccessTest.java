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

    @Test
    void testFindByUserName() throws Exception {
        final String username = "test@mail.com";
        UserAccount user = new UserAccount(username,"password");
        userAccountDataAccess.save(user);

        assertNotNull(userAccountDataAccess.findByUsername(username));
        assertNull(userAccountDataAccess.findByUsername(username+"asdf"));

    }


}
