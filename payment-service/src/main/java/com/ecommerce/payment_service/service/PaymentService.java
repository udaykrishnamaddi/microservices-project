package com.ecommerce.payment_service.service;

import com.ecommerce.payment_service.client.OrderServiceClient;
import com.ecommerce.payment_service.dto.OrderDto;
import com.ecommerce.payment_service.dto.PaymentRequest;
import com.ecommerce.payment_service.exception.PaymentNotFoundException;
import com.ecommerce.payment_service.model.Payment;
import com.ecommerce.payment_service.model.PaymentStatus;
import com.ecommerce.payment_service.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {
    private static final double MOCK_SUCCESS_RATE = 0.8;

    private final PaymentRepository paymentRepository;
    private final OrderServiceClient orderServiceClient;
    private Random random = new Random();

    public PaymentService(PaymentRepository paymentRepository, OrderServiceClient orderServiceClient){
        this.paymentRepository=paymentRepository;
        this.orderServiceClient=orderServiceClient;
    }

    public Payment processPayment(PaymentRequest request) {
        OrderDto order = orderServiceClient.getOrder(request.getOrderId());

        if (!"PENDING".equals(order.getStatus())) {
            throw new IllegalStateException(
                    "order " + order.getId() + " is not awaiting payment (current status: " + order.getStatus() + ")"
            );
        }

        boolean success = random.nextDouble() < MOCK_SUCCESS_RATE;

        Payment payment = new Payment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getTotalPrice());
        payment.setStatus(success ? PaymentStatus.SUCCESS : PaymentStatus.FAILED);
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setCreatedAt(Instant.now());
        payment = paymentRepository.save(payment);

        orderServiceClient.updateOrderStatus(order.getId(), success ? "CONFIRMED" : "PAYMENT_FAILED");

        return payment;
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));
    }

    public List<Payment> getPaymentsByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }
}
