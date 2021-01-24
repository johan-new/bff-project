package com.yrgo.bff.project.controllers;


import com.yrgo.bff.project.service.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * NotificationController
 *
 * REST Controller for fetching the notifications of the logged in user.
 *
 **/

@RestController
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    /**
     * Reads a notification from the logged in user when used
     *
     * Uses the interface NotificationService
     **/

    @GetMapping("/notifications")
    Map<Integer, Map<String, String>> getNotifications(){
        return notificationService.getNotifications();
    }
}
