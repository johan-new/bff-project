package com.yrgo.bff.project;

import com.yrgo.bff.project.dao.UserAccountDataAccess;
import com.yrgo.bff.project.domain.User;
import com.yrgo.bff.project.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class AuthenticationServiceImplementationTest {

    @Autowired
    UserAccountDataAccess userAccountDataAccess;

    @Autowired
    AuthenticationService authenticationService;

    @Test
    void testAuthenticationSuccess(){
        final String username = "bill@microsoft.com";
        final String password = "Melinda";
        userAccountDataAccess.save(new User(username,password));
        assertFalse(authenticationService.authenticationSuccess(username,"Wrongpassword"));
        assertTrue(authenticationService.authenticationSuccess(username,password));
    }
}
