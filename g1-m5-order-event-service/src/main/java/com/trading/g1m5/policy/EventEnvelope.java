package com.trading.g1m5.policy;

import com.trading.g1m5.contract.OrderEvent;

public class EventEnvelope {

    private final OrderEvent event;
    private final String brokerTopic;
    private final String webSocketTopic;

    public EventEnvelope(OrderEvent event, String brokerTopic, String webSocketTopic) {
        this.event = event;
        this.brokerTopic = brokerTopic;
        this.webSocketTopic = webSocketTopic;
    }

    public OrderEvent getEvent() {
        return event;
    }

    public String getBrokerTopic() {
        return brokerTopic;
    }

    public String getWebSocketTopic() {
        return webSocketTopic;
    }
}
