package com.ecommerce.payment_service.exception;

import com.ecommerce.payment_service.dto.PaymentRequest;

public class PaymentNotFoundException extends RuntimeException{
    public PaymentNotFoundException(Long id) {
        super("Payment not found with id: "+id);
    }
}
