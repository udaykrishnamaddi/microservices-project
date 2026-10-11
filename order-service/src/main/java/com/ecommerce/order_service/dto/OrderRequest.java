package com.ecommerce.order_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

//price is NOT accepted from the client
public class OrderRequest {

    @NotNull(message = "productID is required")
    private Long productId;

    @NotNull(message = "quantity is required")
    @Min(value = 1, message = "qunatity must be at least 1")
    private Integer quantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
