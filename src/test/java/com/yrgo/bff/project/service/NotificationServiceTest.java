package com.yrgo.bff.project.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class NotificationServiceTest {

    @Autowired
    NotificationService notificationService;

    @Test
    @WithMockUser(username="Testuser")
    void testAddingNotifications(){
        final String greeting = "Hejsan!";
        notificationService.addNotification("Testuser",greeting,NotificationService.Type.GENERAL);
        //is the notification there?
        assertTrue(notificationService.getNotifications().size()>0);
        //once fetched - it should be deleted :)
        assertNull(notificationService.getNotifications());

    }
}
