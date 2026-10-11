package com.ecommerce.notification_service.model;

import java.time.Instant;

public class Notification {
    private Long orderId;
    private String message;
    private Instant sentAt;

    public Notification() {

    }

    public Notification(Long orderId, String message, Instant sentAt){
        this.orderId=orderId;
        this.message=message;
        this.sentAt=sentAt;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getSentAt() {
        return sentAt;
    }

    public void setSentAt(Instant sentAt) {
        this.sentAt = sentAt;
    }
}
