package com.yrgo.bff.project.service;

import org.json.simple.JSONObject;
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
    public void addNotification(String username, String notification, Type type) {
        if (notifications.containsKey(username)) {
            notifications.get(username).add(type.name());
            notifications.get(username).add(notification);
        } else {
            List<String> newNotification = new ArrayList<>();
            newNotification.add(type.name());
            newNotification.add(notification);
            notifications.put(username,newNotification);
        }
        System.out.println("Added new " + type.name() + " notification for " + username + "\n" + notification);
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
