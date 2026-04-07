package com.trading.g1m5.service;

import com.trading.g1m5.contract.OrderCancelledEvent;
import com.trading.g1m5.contract.OrderEvent;
import com.trading.g1m5.contract.OrderReceivedEvent;
import com.trading.g1m5.contract.OrderUpdatedEvent;
import com.trading.g1m5.dto.OrderSnapshot;
import com.trading.g1m5.enums.EventType;
import com.trading.g1m5.enums.EventVersion;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class OrderEventFactory {

    public OrderEvent buildReceived(OrderSnapshot snapshot) {
        OrderReceivedEvent event = new OrderReceivedEvent();
        fillCommonFields(event, snapshot, EventType.ORDER_RECEIVED);
        return event;
    }

    public OrderEvent buildCancelled(OrderSnapshot snapshot) {
        OrderCancelledEvent event = new OrderCancelledEvent();
        fillCommonFields(event, snapshot, EventType.ORDER_CANCELLED);
        return event;
    }

    public OrderEvent buildUpdated(OrderSnapshot snapshot) {
        OrderUpdatedEvent event = new OrderUpdatedEvent();
        fillCommonFields(event, snapshot, EventType.ORDER_UPDATED);
        return event;
    }

    private void fillCommonFields(OrderEvent event, OrderSnapshot snapshot, EventType eventType) {
        event.setEventId(UUID.randomUUID().toString());
        event.setEventType(eventType);
        event.setEventVersion(EventVersion.V1.getValue());
        event.setTimestamp(Instant.now());
        event.setOrderId(snapshot.getOrderId());
        event.setClientId(snapshot.getClientId());
        event.setSymbol(snapshot.getSymbol());
        event.setSide(snapshot.getSide());
        event.setQuantity(snapshot.getQuantity());
        event.setPrice(snapshot.getPrice());
        event.setStatus(snapshot.getStatus());
        event.getMetadata().put("producer", "g1-m5-order-event-service");
    }
}
