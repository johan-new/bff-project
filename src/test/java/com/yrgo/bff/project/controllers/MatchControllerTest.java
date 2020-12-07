//package com.yrgo.bff.project;
//
//import com.yrgo.bff.project.controllers.MatchController;
//import com.yrgo.bff.project.service.UserAccountService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@SpringBootTest
//public class MatchControllerTest {
//
//    @Autowired
//    MatchController matchController;
//
//    @Autowired
//    UserAccountService userAccountService;
//
//    @Test
//    public void contextLoads() {
//        final String createdUser = "Kalle";
//        final String notCreatedUser = "Stina";
//        final String location = "Stockholm";
//
//        userAccountService.createUser(createdUser,"asdf");
//
//        //can the create users be added to the matching que?
//        assertDoesNotThrow(()->{
//            matchController.userWantTobeMatched(createdUser,location);
//        });
//        //user not created, should throw exception
//        Assertions.assertThrows(Exception.class, ()->{
//           matchController.userWantTobeMatched(notCreatedUser,location);
//        });
//
//        //user created, should be removed
//        assertDoesNotThrow(()->{
//            matchController.userWantTobeMatchedNoMore(createdUser);
//        });
//
//        //user not created, should throw exception
//        Assertions.assertThrows(Exception.class, ()->{
//            matchController.userWantTobeMatchedNoMore(notCreatedUser);
//        });
//
//
//    }
//
//}
