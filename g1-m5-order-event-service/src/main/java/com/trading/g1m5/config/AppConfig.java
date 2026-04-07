package com.trading.g1m5.config;

import com.trading.g1m5.policy.DroppedEventTracker;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(EventProperties.class)
public class AppConfig {

    @Bean
    public DroppedEventTracker droppedEventTracker() {
        return new DroppedEventTracker();
    }
}
