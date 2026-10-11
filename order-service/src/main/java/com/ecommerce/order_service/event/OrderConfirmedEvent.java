package com.ecommerce.order_service.event;

import java.math.BigDecimal;
import java.time.Instant;

public class OrderConfirmedEvent {

    private Long orderId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal totalPrice;
    private Instant confirmedAt;

    public OrderConfirmedEvent(){

    }
    public OrderConfirmedEvent(Long orderId, Long productId, String productName, Integer quantity, BigDecimal totalPrice, Instant confirmedAt){
        this.orderId=orderId;
        this.productId=productId;
        this.productName=productName;
        this.quantity=quantity;
        this.totalPrice=totalPrice;
        this.confirmedAt=confirmedAt;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Instant getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(Instant confirmedAt) {
        this.confirmedAt = confirmedAt;
    }
}
