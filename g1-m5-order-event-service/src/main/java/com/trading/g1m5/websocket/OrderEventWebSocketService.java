package com.trading.g1m5.websocket;

import com.trading.g1m5.contract.OrderEvent;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventWebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public OrderEventWebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void broadcast(OrderEvent event, String destination) {
        messagingTemplate.convertAndSend(destination, event);
    }
}
