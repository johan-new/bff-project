package com.yrgo.bff.project.service;

import java.util.Map;

public interface NotificationService {
    Map<Integer, Map<String, String>> getNotifications();
    void addNotification(String username, String notification, NotificationService.Type type);

    enum Type {
        MATCH_SUCCESS,
        GENERAL,
        REJECTED_JOIN_REQUEST,
        ACCEPTED_JOIN_REQUEST,
        NEW_JOIN_REQUEST
    }
}
