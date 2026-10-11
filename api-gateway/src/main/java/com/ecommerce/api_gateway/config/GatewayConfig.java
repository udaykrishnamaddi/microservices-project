package com.ecommerce.api_gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
public class GatewayConfig {

    @Bean
    public RouterFunction<ServerResponse> productServiceRoute(
            @Value("${services.product-url:http://localhost:8082}") String productUrl
    ) {
        return route("product-service")
                .route(RequestPredicates.path("/api/products/**"), http())
                .before(uri(productUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceRoute(
            @Value("${services.order-url:http://localhost:8081}") String orderUrl
    ) {
        return route("order-service")
                .route(RequestPredicates.path("/api/orders/**"), http())
                .before(uri(orderUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> paymentServiceRoute(
            @Value("${services.payment-url:http://localhost:8083}") String paymentUrl
    ) {
        return route("payment-service")
                .route(RequestPredicates.path("/api/payments/**"), http())
                .before(uri(paymentUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> notificationServiceRoute(
            @Value("${services.notification-url:http://localhost:8084}") String notificationUrl
    ) {
        return route("notification-service")
                .route(RequestPredicates.path("/api/notifications/**"), http())
                .before(uri(notificationUrl))
                .build();
    }
}
