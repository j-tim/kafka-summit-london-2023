package com.example.spring.kafka.consumer.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

/**
 * In this class we'll add all the manual configuration required for Observability to
 * work.
 */
@Configuration(proxyBeanMethods = false)
public class TracingConsumerConfig {

  private final ConcurrentKafkaListenerContainerFactory concurrentKafkaListenerContainerFactory;

  public TracingConsumerConfig(
      ConcurrentKafkaListenerContainerFactory concurrentKafkaListenerContainerFactory) {
    this.concurrentKafkaListenerContainerFactory = concurrentKafkaListenerContainerFactory;
  }

  @PostConstruct
  public void setup() {
    this.concurrentKafkaListenerContainerFactory
        .getContainerProperties()
        .setObservationEnabled(true);
  }

}