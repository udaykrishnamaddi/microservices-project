package com.ecommerce.payment_service.dto;

import jakarta.validation.constraints.NotNull;

//amount is not accepted from client, itis looked from Order Service
public class PaymentRequest {

    @NotNull(message = "orderId is required")
    private Long orderId;

    public Long getOrderId(){
        return orderId;
    }
    public void setOrderId(Long orderId){
        this.orderId=orderId;
    }
}
