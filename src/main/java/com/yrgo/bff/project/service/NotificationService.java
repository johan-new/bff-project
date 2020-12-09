package com.yrgo.bff.project.service;

import org.aspectj.weaver.ast.Not;

import java.util.List;

public interface NotificationService {
    List<String> getNotifications();
    void addNotification(String username, String notification, NotificationService.Type type);

    enum Type {
        MATCH_SUCCESS,
        GENERAL
    }
}
