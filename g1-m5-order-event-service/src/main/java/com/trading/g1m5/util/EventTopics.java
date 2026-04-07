package com.trading.g1m5.util;

public final class EventTopics {

    private EventTopics() {
    }

    public static final String BROKER_ORDER_RECEIVED = "orders.received";
    public static final String BROKER_ORDER_CANCELLED = "orders.cancelled";
    public static final String BROKER_ORDER_UPDATED = "orders.updated";

    public static final String WS_ROOT = "/topic/orders";
    public static final String WS_ORDER_RECEIVED = "/topic/orders/received";
    public static final String WS_ORDER_CANCELLED = "/topic/orders/cancelled";
    public static final String WS_ORDER_UPDATED = "/topic/orders/updated";
}
