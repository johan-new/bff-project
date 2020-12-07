package com.yrgo.bff.project.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class NotificationServiceTest {

    @Autowired
    NotificationService notificationService;

    @Test
    @WithMockUser(username="Johan")
    void testAddingNotifications(){
        final String greeting = "Hejsan!";
        notificationService.addNotification("Johan",greeting);
        //is the notification there?
        assertEquals(notificationService.getNotifications().get(0),greeting);
        //once fetched - it should be deleted :)
        assertNull(notificationService.getNotifications());

    }
}
