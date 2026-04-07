package com.trading.g1m5.web;

import com.trading.g1m5.dto.OrderSnapshot;
import com.trading.g1m5.service.OrderEventDispatchService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/internal/events/orders")
public class InternalOrderEventController {

    private final OrderEventDispatchService dispatchService;

    public InternalOrderEventController(OrderEventDispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    @PostMapping("/received")
    public ResponseEntity<Map<String, Object>> publishReceived(@Valid @RequestBody OrderSnapshot snapshot) {
        return ResponseEntity.accepted().body(buildResponse("ORDER_RECEIVED", dispatchService.handleOrderReceived(snapshot)));
    }

    @PostMapping("/cancelled")
    public ResponseEntity<Map<String, Object>> publishCancelled(@Valid @RequestBody OrderSnapshot snapshot) {
        return ResponseEntity.accepted().body(buildResponse("ORDER_CANCELLED", dispatchService.handleOrderCancelled(snapshot)));
    }

    @PostMapping("/updated")
    public ResponseEntity<Map<String, Object>> publishUpdated(@Valid @RequestBody OrderSnapshot snapshot) {
        return ResponseEntity.accepted().body(buildResponse("ORDER_UPDATED", dispatchService.handleOrderUpdated(snapshot)));
    }

    @GetMapping("/buffer")
    public ResponseEntity<Map<String, Object>> bufferStats() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("bufferSize", dispatchService.currentBufferSize());
        response.put("droppedEvents", dispatchService.totalDroppedEvents());
        return ResponseEntity.ok(response);
    }

    private Map<String, Object> buildResponse(String eventType, boolean accepted) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("eventType", eventType);
        response.put("accepted", accepted);
        response.put("bufferSize", dispatchService.currentBufferSize());
        response.put("droppedEvents", dispatchService.totalDroppedEvents());
        return response;
    }
}
