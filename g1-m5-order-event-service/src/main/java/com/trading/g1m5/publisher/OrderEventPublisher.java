package com.trading.g1m5.publisher;

import com.trading.g1m5.contract.OrderEvent;

public interface OrderEventPublisher {

    void publish(OrderEvent event, String topic);
}
