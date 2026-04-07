package com.trading.g1m5.publisher;

import com.trading.g1m5.contract.OrderEvent;
import com.trading.g1m5.util.EventSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.events.broker.enabled", havingValue = "false", matchIfMissing = true)
public class LoggingOrderEventPublisher implements OrderEventPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingOrderEventPublisher.class);

    private final EventSerializer eventSerializer;

    public LoggingOrderEventPublisher(EventSerializer eventSerializer) {
        this.eventSerializer = eventSerializer;
    }

    @Override
    public void publish(OrderEvent event, String topic) {
        LOGGER.info("Broker disabled. Simulated publish to topic={} payload={}", topic, eventSerializer.toJson(event));
    }
}
