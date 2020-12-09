package com.yrgo.bff.project.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationServiceImplementation implements NotificationService {

    Map<String,List<String>> notifications;

    public NotificationServiceImplementation() {
        this.notifications =  new HashMap();
    }

    @Override
    public List<String> getNotifications() {
        try {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return notifications.remove(username);
        } catch (Exception e) {
            return null;
        } finally {
        }
    }

    @Override
    public void addNotification(String username, String notification) {
        if (notifications.containsKey(username)) {
            notifications.get(username).add(notification);
        } else {
            List<String> newNotification = new ArrayList<>();
            newNotification.add(notification);
            notifications.put(username,newNotification);
        }
        System.out.println("Added new notification for " + username + "\n" + notification);
    }
}
