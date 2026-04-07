package com.trading.g1m5.policy;

import com.trading.g1m5.config.EventProperties;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Component
public class EventBuffer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventBuffer.class);

    private final BlockingQueue<EventEnvelope> queue;
    private final EventProperties eventProperties;
    private final DroppedEventTracker droppedEventTracker;
    private volatile boolean running = true;
    private Thread worker;

    public EventBuffer(EventProperties eventProperties, DroppedEventTracker droppedEventTracker) {
        this.eventProperties = eventProperties;
        this.droppedEventTracker = droppedEventTracker;
        this.queue = new ArrayBlockingQueue<>(eventProperties.getBuffer().getCapacity());
    }

    public void start(Consumer<EventEnvelope> consumer) {
        if (worker != null) {
            return;
        }

        worker = new Thread(() -> {
            while (running || !queue.isEmpty()) {
                try {
                    EventEnvelope envelope = queue.poll(500, TimeUnit.MILLISECONDS);
                    if (envelope != null) {
                        consumer.accept(envelope);
                    }
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    LOGGER.warn("Event buffer worker interrupted");
                    break;
                } catch (Exception exception) {
                    LOGGER.error("Failed while consuming buffered order event", exception);
                }
            }
        }, "order-event-buffer-worker");

        worker.setDaemon(true);
        worker.start();
    }

    public boolean offer(EventEnvelope envelope) {
        BackpressurePolicy policy = eventProperties.getBuffer().getPolicy();

        return switch (policy) {
            case DROP_OLDEST -> offerByDroppingOldest(envelope);
            case DROP_NEWEST -> offerByDroppingNewest(envelope);
            case BLOCK_PRODUCER -> offerByBlocking(envelope);
        };
    }

    private boolean offerByDroppingOldest(EventEnvelope envelope) {
        while (!queue.offer(envelope)) {
            EventEnvelope discarded = queue.poll();
            long dropped = droppedEventTracker.incrementAndGet();
            LOGGER.warn("Buffer full. Dropped oldest event {}. Total dropped={}", discarded != null ? discarded.getEvent().getEventId() : "unknown", dropped);
        }
        return true;
    }

    private boolean offerByDroppingNewest(EventEnvelope envelope) {
        boolean accepted = queue.offer(envelope);
        if (!accepted) {
            long dropped = droppedEventTracker.incrementAndGet();
            LOGGER.warn("Buffer full. Dropped newest event {}. Total dropped={}", envelope.getEvent().getEventId(), dropped);
        }
        return accepted;
    }

    private boolean offerByBlocking(EventEnvelope envelope) {
        try {
            queue.put(envelope);
            return true;
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    public int size() {
        return queue.size();
    }

    public long droppedCount() {
        return droppedEventTracker.getDroppedCount();
    }

    @PreDestroy
    public void shutdown() {
        running = false;
        if (worker != null) {
            worker.interrupt();
        }
    }
}
