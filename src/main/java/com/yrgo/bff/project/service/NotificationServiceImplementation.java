package com.yrgo.bff.project.service;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NotificationServiceImplementation implements NotificationService {

    Map<String,Map<Integer,Map<String,String>>> notifications;

    private Log log = LogFactory.getLog(getClass());


    public NotificationServiceImplementation() {
        this.notifications =  new HashMap();
    }

    @Override
    public Map<Integer, Map<String, String>> getNotifications() {
        try {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return notifications.remove(username);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void addNotification(String username, String content, Type type) {
        Map<String,String> notificationContent = new HashMap<>();
        notificationContent.put("type",type.name());
        notificationContent.put("timestamp",new Date().toString());
        notificationContent.put("content", content);

        if (notifications.containsKey(username)) {
            notifications.get(username).put(notificationContent.hashCode(),notificationContent);
        } else {
            Map<Integer,Map<String,String>> notificationsMapForSpecificUser = new HashMap<>();
            notificationsMapForSpecificUser.put(notificationContent.hashCode(),notificationContent);

            notifications.put(username,notificationsMapForSpecificUser);
        }
        log.debug("Added new " + type.name() + " notification for " + username);
    }

}
