package com.example.spring.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import com.example.spring.kafka.consumer.service.StockQuoteService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import com.example.avro.stock.quote.StockQuote;

@Component
@Slf4j
public class StockQuoteConsumer {
    private final StockQuoteService service;

    public StockQuoteConsumer(StockQuoteService service) {
        this.service = service;
    }

    @KafkaListener(topics = {"stock-quotes"})
    public void on(StockQuote stockQuote, @Header(KafkaHeaders.RECEIVED_PARTITION) String partition) {
        log.info("Consumed from partition: {} value: {}", partition, stockQuote);
        service.handle(stockQuote);
    }
}
