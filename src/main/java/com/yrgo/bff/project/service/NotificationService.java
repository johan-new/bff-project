package com.yrgo.bff.project.service;

import java.util.Map;

public interface NotificationService {
    Map<Integer, Map<String, String>> getNotifications();
    void addNotification(String username, String notification, NotificationService.Type type);

    enum Type {
        MATCH_SUCCESS,
        GENERAL
    }
}
