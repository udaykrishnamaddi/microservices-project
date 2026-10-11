package com.ecommerce.order_service.event;

import com.ecommerce.order_service.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class OrderEventPublisher {
    public static final String ORDER_CONFIRMED_TOPIC = "order-confirmed-events";
    private static final Logger log = LoggerFactory.getLogger(OrderEventPublisher.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderEventPublisher(KafkaTemplate<String, Object> kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }

    public void publishOrderConfirmed(Order order) {
        OrderConfirmedEvent event = new OrderConfirmedEvent(
                order.getId(),
                order.getProductId(),
                order.getProductName(),
                order.getQuantity(),
                order.getTotalPrice(),
                Instant.now()
        );

        try {
            kafkaTemplate.send(ORDER_CONFIRMED_TOPIC, order.getId().toString(), event)
                    .whenComplete((result, ex) -> {
                        if(ex!=null) {
                            log.error("Async publish failed for order {}", order.getId(), ex);
                        } else{
                            log.info("Published order-confirmed event for order {} (partition {}, offset {})",
                            order.getId(),
                            result.getRecordMetadata().partition(),
                            result.getRecordMetadata().offset());

                        }
                    });
        } catch (Exception ex) {
            log.error("Failed to publish order-confirmed event for order {}", order.getId(), ex);
        }


    }
}
