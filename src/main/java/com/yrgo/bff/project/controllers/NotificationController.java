package com.yrgo.bff.project.controllers;


import com.yrgo.bff.project.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotificationController {

    @Autowired
    NotificationService notificationService;



    @GetMapping("/notifications")
    List<String> getNotifications(){
        return notificationService.getNotifications();
    }
}
