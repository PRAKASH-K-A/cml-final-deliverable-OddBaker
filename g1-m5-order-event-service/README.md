# G1-M5 Order Event Service

Strict Spring Boot implementation for **G1-M5: Order Event Publishing (WebSocket + Broker)**.

## Scope covered
- Publish `OrderReceived`, `OrderCancelled`, and `OrderUpdated` events
- Broker publishing abstraction
- Internal WebSocket stream for low-latency subscribers
- JSON event contracts with versioning
- Backpressure policy with bounded event buffer

## Tech stack
- Java 17
- Spring Boot 3
- Spring WebSocket (STOMP)
- Spring Kafka (optional broker mode)

## Run locally
```bash
mvn spring-boot:run
```

Broker publishing is simulated by default through logs.
To switch to Kafka broker publishing, change this in `application.yml`:
```yaml
app:
  events:
    broker:
      enabled: true
```

## Internal test APIs
### Publish ORDER_RECEIVED
```bash
curl -X POST http://localhost:8080/internal/events/orders/received \
  -H "Content-Type: application/json" \
  -d '{
    "orderId": "ORD-1001",
    "clientId": "CLIENT-1",
    "symbol": "INFY",
    "side": "BUY",
    "quantity": 100,
    "price": 1500.50,
    "status": "NEW"
  }'
```

### Publish ORDER_CANCELLED
```bash
curl -X POST http://localhost:8080/internal/events/orders/cancelled \
  -H "Content-Type: application/json" \
  -d '{
    "orderId": "ORD-1001",
    "clientId": "CLIENT-1",
    "symbol": "INFY",
    "side": "BUY",
    "quantity": 100,
    "price": 1500.50,
    "status": "CANCELLED"
  }'
```

### Publish ORDER_UPDATED
```bash
curl -X POST http://localhost:8080/internal/events/orders/updated \
  -H "Content-Type: application/json" \
  -d '{
    "orderId": "ORD-1001",
    "clientId": "CLIENT-1",
    "symbol": "INFY",
    "side": "BUY",
    "quantity": 120,
    "price": 1499.90,
    "status": "UPDATED"
  }'
```

### Buffer stats
```bash
curl http://localhost:8080/internal/events/orders/buffer
```

## WebSocket subscription
Connect to:
- Endpoint: `/ws/orders`
- Topics:
  - `/topic/orders`
  - `/topic/orders/received`
  - `/topic/orders/cancelled`
  - `/topic/orders/updated`

## Suggested GitHub repo name
`g1-m5-order-event-service`
