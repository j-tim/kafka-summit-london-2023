package com.example.spring.kafka.producer;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.extern.slf4j.Slf4j;
import com.example.avro.stock.quote.StockQuote;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StockQuoteProducer {

    private final KafkaTemplate<String, StockQuote> kafkaTemplate;
    private final ObservationRegistry observationRegistry;

    public StockQuoteProducer(KafkaTemplate<String, StockQuote> kafkaTemplate, ObservationRegistry observationRegistry) {
        this.kafkaTemplate = kafkaTemplate;
        this.observationRegistry = observationRegistry;
    }

    public void produce(StockQuote stockQuote) {
        Observation.createNotStarted("kafka-producer", this.observationRegistry).observeChecked(() -> {
            kafkaTemplate.send("stock-quotes", stockQuote.getSymbol(), stockQuote);
            log.info("Produced stock quote: {}", stockQuote);
        });
    }
}
