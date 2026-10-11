package com.ecommerce.payment_service.dto;

// Outgoing body for PATCH /api/orders/{id}/status on Order Service
public class OrderStatusUpdateRequest {
    private String status;

    public OrderStatusUpdateRequest(){

    }
    public OrderStatusUpdateRequest(String status) {
        this.status=status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
