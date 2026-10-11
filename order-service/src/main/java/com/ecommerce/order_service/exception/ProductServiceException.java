package com.ecommerce.order_service.exception;

public class ProductServiceException extends RuntimeException{
    public ProductServiceException(String message) {
        super(message);
    }
}
