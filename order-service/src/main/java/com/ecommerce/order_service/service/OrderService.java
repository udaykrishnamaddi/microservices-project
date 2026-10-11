package com.ecommerce.order_service.service;

import com.ecommerce.order_service.client.ProductServiceClient;
import com.ecommerce.order_service.dto.OrderRequest;
import com.ecommerce.order_service.dto.ProductDto;
import com.ecommerce.order_service.event.OrderEventPublisher;
import com.ecommerce.order_service.exception.OrderNotFoundException;
import com.ecommerce.order_service.model.Order;
import com.ecommerce.order_service.model.OrderStatus;
import com.ecommerce.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductServiceClient productServiceClient;
    private final OrderEventPublisher orderEventPublisher;

    public OrderService(OrderRepository orderRepository, ProductServiceClient productServiceClient, OrderEventPublisher orderEventPublisher) {
        this.orderRepository = orderRepository;
        this.productServiceClient = productServiceClient;
        this.orderEventPublisher=orderEventPublisher;
    }

    public Order createOrder(OrderRequest request) {
        ProductDto product = productServiceClient.getproduct(request.getProductId());

        Order order = new Order();
        order.setProductId(product.getId());
        order.setProductName(product.getName());
        order.setQuantity(product.getQuantity());
        order.setUnitPrice((product.getPrice()));
        order.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
        order.setCreatedAt(Instant.now());

        if(product.getQuantity() < request.getQuantity()) {
            order.setStatus(OrderStatus.OUT_OF_STOCK);
        }
        else{
            productServiceClient.reduceStock(request.getProductId(), request.getQuantity());
            order.setStatus(OrderStatus.PENDING);
        }
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(()->new OrderNotFoundException(id));
    }

    public OrderStatus getOrderStatus(Long id) {
        return getOrderById(id).getStatus();
    }

    public Order updateStatus(Long id, OrderStatus status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        order = orderRepository.save(order);

        if(status == OrderStatus.CONFIRMED) {
            orderEventPublisher.publishOrderConfirmed(order);
        }

        return order;
    }
}
