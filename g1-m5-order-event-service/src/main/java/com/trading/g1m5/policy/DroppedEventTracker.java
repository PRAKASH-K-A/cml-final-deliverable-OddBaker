package com.trading.g1m5.policy;

import java.util.concurrent.atomic.AtomicLong;

public class DroppedEventTracker {

    private final AtomicLong droppedCount = new AtomicLong();

    public long incrementAndGet() {
        return droppedCount.incrementAndGet();
    }

    public long getDroppedCount() {
        return droppedCount.get();
    }
}
