package com.example.consumer.plain;

import com.example.avro.stock.quote.StockQuote;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Properties;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.Meter;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlainConsumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(PlainConsumer.class);

  //Uncomment to be able to call consumer from the main method
  public static void main(String[] args) {
    Meter meter = buildMeter();
    registerReadyTime(meter);

    startSimpleConsumer();
  }

  private static void registerReadyTime(Meter meter) {
    var currentTimeMillis = System.currentTimeMillis();
    var processStartTime = ProcessHandle.current().info().startInstant().get();
    long applicationReadyTimeValue = currentTimeMillis - processStartTime.toEpochMilli();

    meter.gaugeBuilder("application.ready.time")
            .setDescription("Time taken (ms) for the application to be ready to service requests")
            .setUnit("milliseconds")
            .ofLongs()
            .buildWithCallback(measurement -> {
              Attributes attributes = Attributes.builder()
                      .put("main_application_class", PlainConsumer.class.getCanonicalName())
                      .put("application", "plain-kafka-consumer")
                      .build();
              measurement.record(applicationReadyTimeValue, attributes);
            });

    LOGGER.info("Registered application ready time: {} ms", applicationReadyTimeValue);
  }

  private static Meter buildMeter() {
      return GlobalOpenTelemetry.meterBuilder("com.example.plain.consumer.custom")
          .setInstrumentationVersion("1.10.0-alpha-rc.1")
          .build();
  }


  // Create consumer from GenericRecord (dynamic typing).

  // method for consuming events from kafka
  public static void startSimpleConsumer() {
    Properties props = new Properties();

    String[] topics = {"stock-quotes-exchange-nyse", "stock-quotes-exchange-nasdaq", "stock-quotes-exchange-ams"};
    // Each producer should have a client id
    props.put(ConsumerConfig.CLIENT_ID_CONFIG, "plain-kafka-consumer");

    props.put(ConsumerConfig.GROUP_ID_CONFIG, "plain-kafka-consumer-group");
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:29092");

    // Schema Registry connection
    props.put("schema.registry.url", "http://schema-registry:8081");

    // Configure the offset management, depending on the requirements on delivery-semantic
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);

    // Key deserializer
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

    // Payload deserializer
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
    // initialize the kafka consumer instance
    KafkaConsumer<String, StockQuote> consumer = new KafkaConsumer<>(props);
    consumer.subscribe(List.of(topics));
    try {
      while (true) {
        try {
          ConsumerRecords<String, StockQuote> records = consumer.poll(Duration.of(100, ChronoUnit.MILLIS));
          for (ConsumerRecord<String, StockQuote> record : records) {
            final GenericRecord value = record.value();
            // Your event processing implementation
            // Etc...
            LOGGER.info("Consumed from topic: {} partition: {} value: {}", record.topic(), record.partition(), value);
          }
        } catch (Exception e) {
          //Handle error in case an error occur
          LOGGER.error(e.getMessage(), e);
        }
      }
    } catch (WakeupException e) {
      // Ignore Exception if closing
    } finally {
      consumer.close();
    }
  }
}
