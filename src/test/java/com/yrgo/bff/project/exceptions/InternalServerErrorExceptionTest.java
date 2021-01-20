package com.yrgo.bff.project.exceptions;

import com.yrgo.bff.project.controllers.UserAccountController;
import com.yrgo.bff.project.exception.InternalServerErrorException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * InternalServerErrorExceptionTest
 *
 */

@SpringBootTest
public class InternalServerErrorExceptionTest {

    @Autowired
    UserAccountController userAccount;

    @Test
    public void testInternalServerErrorWithoutMockUser() {
        assertThrows(InternalServerErrorException.class,()-> {
           userAccount.readUser();
        });
    }

}
