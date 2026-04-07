package com.trading.g1m5.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final EventProperties eventProperties;

    public WebSocketConfig(EventProperties eventProperties) {
        this.eventProperties = eventProperties;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(eventProperties.getWebSocket().getBrokerDestinationPrefix());
        registry.setApplicationDestinationPrefixes(eventProperties.getWebSocket().getApplicationDestinationPrefix());
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(eventProperties.getWebSocket().getEndpoint())
                .setAllowedOriginPatterns("*");
    }
}
