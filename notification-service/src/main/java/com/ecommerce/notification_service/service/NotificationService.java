package com.ecommerce.notification_service.service;

import com.ecommerce.notification_service.model.Notification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class NotificationService {

    private final List<Notification> notifications = new CopyOnWriteArrayList<>();

    public void add(Notification notification) {
        notifications.add(notification);
    }

    public List<Notification> getAll() {
        return notifications;
    }
}
