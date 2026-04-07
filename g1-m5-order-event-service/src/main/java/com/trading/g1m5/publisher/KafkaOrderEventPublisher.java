package com.trading.g1m5.publisher;

import com.trading.g1m5.contract.OrderEvent;
import com.trading.g1m5.util.EventSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.events.broker.enabled", havingValue = "true")
public class KafkaOrderEventPublisher implements OrderEventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final EventSerializer eventSerializer;

    public KafkaOrderEventPublisher(KafkaTemplate<String, String> kafkaTemplate, EventSerializer eventSerializer) {
        this.kafkaTemplate = kafkaTemplate;
        this.eventSerializer = eventSerializer;
    }

    @Override
    public void publish(OrderEvent event, String topic) {
        kafkaTemplate.send(topic, event.getOrderId(), eventSerializer.toJson(event));
    }
}
