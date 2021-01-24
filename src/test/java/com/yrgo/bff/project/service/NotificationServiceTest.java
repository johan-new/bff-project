package com.yrgo.bff.project.service;

import com.yrgo.bff.project.service.notification.NotificationService;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;

/**
 * NotificationServiceTest
 *
 * Integration test of ability to add and receive notifications
 *
 */

@SpringBootTest
public class NotificationServiceTest {

    @Autowired
    NotificationService notificationService;

    /**
     * Putting a notification in the queue and
     * verifies its there (notifications
     * can only be fetched once)
     */
    @Test
    @WithMockUser(username="Testuser")
    void testAddingNotifications(){
        final String greeting = "Hejsan!";
        notificationService.addNotification("Testuser",greeting,NotificationService.Type.GENERAL);
        //is the notification there?
        assertTrue(new JSONObject(notificationService.getNotifications()).toString().contains(greeting));
        //once fetched - it should be deleted :)
        assertNull(notificationService.getNotifications());
    }
}
