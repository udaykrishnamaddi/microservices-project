package com.ecommerce.payment_service.client;

import com.ecommerce.payment_service.dto.OrderDto;
import com.ecommerce.payment_service.dto.OrderStatusUpdateRequest;
import com.ecommerce.payment_service.dto.PaymentRequest;
import com.ecommerce.payment_service.exception.OrderNotFoundException;
import com.ecommerce.payment_service.exception.OrderServiceException;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

@Component
public class OrderServiceClient {
    private final RestClient restClient;

    public OrderServiceClient(RestClient orderServiceRestClient) {
        this.restClient = orderServiceRestClient;
    }

    public OrderDto getOrder(Long orderId) {
        try {
            return restClient.get()
                    .uri("/api/orders/{id}", orderId)
                    .retrieve()
                    .body(OrderDto.class);
        } catch (RestClientResponseException ex) {
            if(ex.getStatusCode().value() == 404) {
                throw new OrderNotFoundException(orderId);
            }
            throw new OrderServiceException("Order service returned an error: "+ ex.getStatusCode());
        } catch (RestClientException ex) {
            throw new OrderServiceException("Order service is unavailable: "+ex.getMessage());
        }
    }

    public void updateOrderStatus(Long orderId, String status) {
        try {
            restClient.patch()
                    .uri("/api/orders/{id}/status", orderId)
                    .body(new OrderStatusUpdateRequest(status))
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientException ex) {
            throw new OrderServiceException("Failed to update order status: "+ ex.getMessage());
        }
    }
}
