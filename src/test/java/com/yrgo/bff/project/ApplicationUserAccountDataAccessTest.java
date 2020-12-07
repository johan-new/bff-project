package com.yrgo.bff.project;


import com.yrgo.bff.project.dao.UserAccountDataAccess;
import com.yrgo.bff.project.domain.ApplicationUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class ApplicationUserAccountDataAccessTest {

    @Autowired
    UserAccountDataAccess userAccountDataAccess;

    @Test
    void testFindByUserName(){
        final String username = "test@mail.com";
        ApplicationUser user = new ApplicationUser(username,"password");
        userAccountDataAccess.save(user);

        assertNotNull(userAccountDataAccess.findByUsername(username));
        assertNull(userAccountDataAccess.findByUsername(username+"asdf"));

    }


}
