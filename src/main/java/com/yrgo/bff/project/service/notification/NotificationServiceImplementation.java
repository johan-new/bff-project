package com.yrgo.bff.project.service.notification;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class NotificationServiceImplementation implements NotificationService {

    Map<String,Map<Integer,Map<String,String>>> notifications;

    private Log log = LogFactory.getLog(getClass());


    public NotificationServiceImplementation() {
        this.notifications =  new HashMap();
    }

    /**
     * Method to get the notification for the logged in user
     *
     * @return Notification and then it's erased
     */

    @Override
    public Map<Integer, Map<String, String>> getNotifications() {
        try {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return notifications.remove(username);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * Adds a notification for the logged in user
     *
     * @param content - HashMap containing notification to be updated
     *                           > type
     *                           > date
     *                           > time
     *                           > content
     * @return Notification for the logged in user
     *
     */

    @Override
    public void addNotification(String username, String content, Type type) {
        Map<String,String> notificationContent = new HashMap<>();
        notificationContent.put("type",type.name());
        notificationContent.put("date", LocalDate.now().toString());
        notificationContent.put("time", LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
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
