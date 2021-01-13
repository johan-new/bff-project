package com.yrgo.bff.project.exceptions;

import com.yrgo.bff.project.domain.UserAccount;
import com.yrgo.bff.project.exception.BadRequestException;
import com.yrgo.bff.project.service.FriendsUserAccountServiceImplementationTest;
import com.yrgo.bff.project.service.UserAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class BadRequestExceptionsTest {

    @Autowired
    UserAccountService userAccountService;

    private UserAccount user;


    @Test
    public void testCreateUserThatThrowsExceptionsWhenCreatingWithEmptyPassword() {
        assertThrows(BadRequestException.class, ()-> {
          userAccountService.createUser(FriendsUserAccountServiceImplementationTest.getRandomUsername(), "");
        });
    }

    @Test
    public void testCreateUserThatThrowsExceptionsWhenCreatingWithInvalidUsername() {
        assertThrows(BadRequestException.class, ()-> {
            userAccountService.createUser("blaha", "hej");
        });
    }

    @Test
    public void testCreateUserThatThrowsExceptionsWhenCreatingWithEmptyUsername() {
        assertThrows(BadRequestException.class, ()-> {
           userAccountService.createUser("","hej");
        });
    }

}
