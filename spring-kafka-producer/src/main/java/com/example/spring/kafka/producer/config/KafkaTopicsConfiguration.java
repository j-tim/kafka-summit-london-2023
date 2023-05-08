package com.example.spring.kafka.producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicsConfiguration {

    public static final String STOCK_QUOTES_TOPIC_NAME = "stock-quotes";

    @Bean
    @ConditionalOnProperty(name = "kafka.producer.enabled", havingValue = "true")
    public NewTopic stockQuotesTopic() {
        return TopicBuilder.name(STOCK_QUOTES_TOPIC_NAME)
            .build();
    }
}
