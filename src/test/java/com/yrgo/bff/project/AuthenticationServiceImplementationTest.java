package com.yrgo.bff.project;

import com.yrgo.bff.project.dao.UserAccountDataAccess;
import com.yrgo.bff.project.domain.User;
import com.yrgo.bff.project.service.AuthenticationService;
import com.yrgo.bff.project.service.UserAccountService;
import com.yrgo.bff.project.service.UserAccountServiceImplementation;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class AuthenticationServiceImplementationTest {

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    AuthenticationService authenticationService;

    @Test
    void testAuthenticationSuccess(){
        final String username = "bill@microsoft.com";
        final String password = "Melinda";
        final String hashedPassword = UserAccountServiceImplementation.hashThis(password);

        userAccountService.createUser(username,password);
        assertFalse(authenticationService.authenticationSuccess(username,password));
        assertTrue(authenticationService.authenticationSuccess(username,hashedPassword));
    }
}
