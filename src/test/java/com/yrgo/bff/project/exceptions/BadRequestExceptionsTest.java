package com.yrgo.bff.project.exceptions;

import com.yrgo.bff.project.exception.BadRequestException;
import com.yrgo.bff.project.service.FriendsUserAccountServiceImplementationTest;
import com.yrgo.bff.project.service.useraccount.UserAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * BadRequestExceptionsTest
 *
 * Testing that its thrown
 */


@SpringBootTest
public class BadRequestExceptionsTest {

    @Autowired
    UserAccountService userAccountService;

    /**
     * Should throw exception since the password is empty
     */
    @Test
    public void testCreateUserThatThrowsBadRequestExceptionWhenCreatingWithEmptyPassword() {
        assertThrows(BadRequestException.class, ()-> {
          userAccountService.createUser(FriendsUserAccountServiceImplementationTest.getRandomUsername(), "");
        });
    }

    /**
     * Should throw exception since the username is not a valid email address
     */
    @Test
    public void testCreateUserThatThrowsBadRequestExceptionWhenCreatingWithInvalidUsername() {
        assertThrows(BadRequestException.class, ()-> {
            userAccountService.createUser("blaha", "hej");
        });
    }

    /**
     * Should throw exception, empty usernames are of course not allowed
     */
    @Test
    public void testCreateUserThatThrowsBadRequestExceptionWhenCreatingWithEmptyUsername() {
        assertThrows(BadRequestException.class, ()-> {
           userAccountService.createUser("","hej");
        });
    }

}
