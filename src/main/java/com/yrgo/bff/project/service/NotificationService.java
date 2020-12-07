package com.yrgo.bff.project.service;

import java.util.List;

public interface NotificationService {
    List<String> getNotifications();
    void addNotification(String username, String notification);
}
