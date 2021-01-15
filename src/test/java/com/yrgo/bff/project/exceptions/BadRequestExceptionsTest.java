package com.yrgo.bff.project.exceptions;

import com.yrgo.bff.project.exception.BadRequestException;
import com.yrgo.bff.project.service.FriendsUserAccountServiceImplementationTest;
import com.yrgo.bff.project.service.UserAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class BadRequestExceptionsTest {

    @Autowired
    UserAccountService userAccountService;

    @Test
    public void testCreateUserThatThrowsBadRequestExceptionWhenCreatingWithEmptyPassword() {
        assertThrows(BadRequestException.class, ()-> {
          userAccountService.createUser(FriendsUserAccountServiceImplementationTest.getRandomUsername(), "");
        });
    }

    @Test
    public void testCreateUserThatThrowsBadRequestExceptionWhenCreatingWithInvalidUsername() {
        assertThrows(BadRequestException.class, ()-> {
            userAccountService.createUser("blaha", "hej");
        });
    }

    @Test
    public void testCreateUserThatThrowsBadRequestExceptionWhenCreatingWithEmptyUsername() {
        assertThrows(BadRequestException.class, ()-> {
           userAccountService.createUser("","hej");
        });
    }

}
