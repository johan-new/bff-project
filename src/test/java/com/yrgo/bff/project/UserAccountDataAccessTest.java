package com.yrgo.bff.project;


import com.yrgo.bff.project.dao.UserAccountDataAccess;
import com.yrgo.bff.project.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class UserAccountDataAccessTest {

    @Autowired
    UserAccountDataAccess userAccountDataAccess;

    @Test
    void testFindByUserName(){
        final String username = "test@mail.com";
        User user = new User(username,"password");
        userAccountDataAccess.save(user);

        assertNotNull(userAccountDataAccess.findByUsername(username));
        assertNull(userAccountDataAccess.findByUsername(username+"asdf"));

    }


}
