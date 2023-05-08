package com.example.spring.kafka.producer.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * Enable tracing
 */
@Configuration(proxyBeanMethods = false)
public class TracingProducerConfig {

  private final KafkaTemplate kafkaTemplate;

  public TracingProducerConfig(KafkaTemplate kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @PostConstruct
  public void setup() {
    this.kafkaTemplate.setObservationEnabled(true);
  }
}
