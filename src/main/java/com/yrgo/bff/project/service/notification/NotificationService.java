package com.yrgo.bff.project.service.notification;

import java.util.Map;

/**
 * NotificationService
 *
 * Handles all user notifications. Please note, these are not persisted.
 * Once fetched they are erased.
 *
 * Notifications can only be fetched by a logged in user, and those
 * are specific for that user.
 *
 **/

public interface NotificationService {
    Map<Integer, Map<String, String>> getNotifications();
    void addNotification(String username, String notification, NotificationService.Type type);

    enum Type {
        MATCH_SUCCESS,
        GENERAL,
        REJECTED_JOIN_REQUEST,
        ACCEPTED_JOIN_REQUEST,
        NEW_JOIN_REQUEST,
        GAME_CREATED
    }
}
