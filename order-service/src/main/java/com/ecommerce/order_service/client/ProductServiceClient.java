package com.ecommerce.order_service.client;

import com.ecommerce.order_service.dto.ProductDto;
import com.ecommerce.order_service.exception.ProductNotFoundException;
import com.ecommerce.order_service.exception.ProductServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

@Component
public class ProductServiceClient {
    private final RestClient restClient;
    public ProductServiceClient(RestClient productServiceRestClient) {
        this.restClient = productServiceRestClient;
    }

    public ProductDto getproduct(Long productId) {
        try {
            return restClient.get()
                    .uri("/api/products/{id}", productId)
                    .retrieve()
                    .body(ProductDto.class);
        } catch (RestClientResponseException ex) {
            if(ex.getStatusCode().value() == 404) {
                throw new ProductNotFoundException(productId);
            }
            throw new ProductServiceException("Product Service returned an error: "+ex.getStatusCode());
        } catch (RestClientException ex) {
            throw new ProductServiceException("product service is unavailable: "+ ex.getMessage());
        }
    }

    public void reduceStock(Long productId, int quantity) {
        try {
            restClient.patch()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/products/{id}/reduce-stock")
                            .queryParam("quantity", quantity)
                            .build(productId))
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientException ex) {
            throw new ProductServiceException("failed to reduce stock in product service: "+ex.getMessage());
        }
    }
}
