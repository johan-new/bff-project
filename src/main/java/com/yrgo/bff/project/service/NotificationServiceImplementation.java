package com.yrgo.bff.project.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NotificationServiceImplementation implements NotificationService {

    Map<String,Map<Integer,Map<String,String>>> notifications;

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
        System.out.println("Added new " + type.name() + " notification for " + username);
    }
/*
    private JSONObject buildNotification(String notification, Type type) {
        if (type == Type.MATCH_SUCCESS) {
            return matchingSuccessNotification(notification);
        } else {
            return  genericNotification(notification);
        }
    }

    private JSONObject matchingSuccessNotification(String notification) {
        Map<String,Map<String,String>> object = new HashMap<>();
        Map<String,String> data = new HashMap<>();
        data.put("type",Type.MATCH_SUCCESS.toString());
        data.put("participants", notification);
        object.put("data",data);
        return new JSONObject(object);
    }

    private JSONObject genericNotification(String notification) {
        Map<String,String> data = new HashMap<>();
        data.put("data",notification);
        return new JSONObject(data);
    }
*/

}
