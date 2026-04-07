package com.trading.g1m5.config;

import com.trading.g1m5.policy.BackpressurePolicy;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.events")
public class EventProperties {

    private Broker broker = new Broker();
    private WebSocket webSocket = new WebSocket();
    private Buffer buffer = new Buffer();

    public Broker getBroker() {
        return broker;
    }

    public void setBroker(Broker broker) {
        this.broker = broker;
    }

    public WebSocket getWebSocket() {
        return webSocket;
    }

    public void setWebSocket(WebSocket webSocket) {
        this.webSocket = webSocket;
    }

    public Buffer getBuffer() {
        return buffer;
    }

    public void setBuffer(Buffer buffer) {
        this.buffer = buffer;
    }

    public static class Broker {
        private boolean enabled;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    public static class WebSocket {
        private String endpoint = "/ws/orders";
        private String applicationDestinationPrefix = "/app";
        private String brokerDestinationPrefix = "/topic";

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getApplicationDestinationPrefix() {
            return applicationDestinationPrefix;
        }

        public void setApplicationDestinationPrefix(String applicationDestinationPrefix) {
            this.applicationDestinationPrefix = applicationDestinationPrefix;
        }

        public String getBrokerDestinationPrefix() {
            return brokerDestinationPrefix;
        }

        public void setBrokerDestinationPrefix(String brokerDestinationPrefix) {
            this.brokerDestinationPrefix = brokerDestinationPrefix;
        }
    }

    public static class Buffer {
        private int capacity = 5000;
        private BackpressurePolicy policy = BackpressurePolicy.DROP_OLDEST;

        public int getCapacity() {
            return capacity;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }

        public BackpressurePolicy getPolicy() {
            return policy;
        }

        public void setPolicy(BackpressurePolicy policy) {
            this.policy = policy;
        }
    }
}
