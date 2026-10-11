package com.ecommerce.notification_service.listener;

import com.ecommerce.notification_service.event.OrderConfirmedEvent;
import com.ecommerce.notification_service.model.Notification;
import com.ecommerce.notification_service.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class OrderEventListener {

    private static final Logger log = LoggerFactory.getLogger(OrderEventListener.class);

    private final NotificationService notificationService;

    public OrderEventListener(NotificationService notificationService) {
        this.notificationService=notificationService;
    }

    @KafkaListener(topics = "order-confirmed-events", groupId = "notification-service")
    public void onOrderConfirmed(OrderConfirmedEvent event) {
        String message = String.format(
                "Hi Your order #%d for %dx %s ($%s) has been confirmed.",
                event.getOrderId(), event.getQuantity(), event.getProductName(), event.getTotalPrice()
        );

        log.info("Sending notification: {}", message);
        notificationService.add(new Notification(event.getOrderId(), message, Instant.now()));
    }
}
