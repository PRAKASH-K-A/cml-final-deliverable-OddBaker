package com.trading.g1m5.service;

import com.trading.g1m5.contract.OrderEvent;
import com.trading.g1m5.dto.OrderSnapshot;
import com.trading.g1m5.policy.EventBuffer;
import com.trading.g1m5.policy.EventEnvelope;
import com.trading.g1m5.publisher.OrderEventPublisher;
import com.trading.g1m5.util.EventTopics;
import com.trading.g1m5.websocket.OrderEventWebSocketService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class OrderEventDispatchService {

    private final OrderEventFactory orderEventFactory;
    private final OrderEventPublisher orderEventPublisher;
    private final OrderEventWebSocketService webSocketService;
    private final EventBuffer eventBuffer;

    public OrderEventDispatchService(OrderEventFactory orderEventFactory,
                                     OrderEventPublisher orderEventPublisher,
                                     OrderEventWebSocketService webSocketService,
                                     EventBuffer eventBuffer) {
        this.orderEventFactory = orderEventFactory;
        this.orderEventPublisher = orderEventPublisher;
        this.webSocketService = webSocketService;
        this.eventBuffer = eventBuffer;
    }

    @PostConstruct
    public void initConsumer() {
        eventBuffer.start(this::flush);
    }

    public boolean handleOrderReceived(@Valid OrderSnapshot snapshot) {
        OrderEvent event = orderEventFactory.buildReceived(snapshot);
        return eventBuffer.offer(new EventEnvelope(event, EventTopics.BROKER_ORDER_RECEIVED, EventTopics.WS_ORDER_RECEIVED));
    }

    public boolean handleOrderCancelled(@Valid OrderSnapshot snapshot) {
        OrderEvent event = orderEventFactory.buildCancelled(snapshot);
        return eventBuffer.offer(new EventEnvelope(event, EventTopics.BROKER_ORDER_CANCELLED, EventTopics.WS_ORDER_CANCELLED));
    }

    public boolean handleOrderUpdated(@Valid OrderSnapshot snapshot) {
        OrderEvent event = orderEventFactory.buildUpdated(snapshot);
        return eventBuffer.offer(new EventEnvelope(event, EventTopics.BROKER_ORDER_UPDATED, EventTopics.WS_ORDER_UPDATED));
    }

    public int currentBufferSize() {
        return eventBuffer.size();
    }

    public long totalDroppedEvents() {
        return eventBuffer.droppedCount();
    }

    private void flush(EventEnvelope envelope) {
        orderEventPublisher.publish(envelope.getEvent(), envelope.getBrokerTopic());
        webSocketService.broadcast(envelope.getEvent(), EventTopics.WS_ROOT);
        webSocketService.broadcast(envelope.getEvent(), envelope.getWebSocketTopic());
    }
}
