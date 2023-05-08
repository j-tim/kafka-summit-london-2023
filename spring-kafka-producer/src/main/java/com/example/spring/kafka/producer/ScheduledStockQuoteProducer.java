package com.example.spring.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import com.example.avro.stock.quote.StockQuote;
import com.example.spring.kafka.producer.generator.RandomStockQuoteGenerator;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
public class ScheduledStockQuoteProducer {

    private final StockQuoteProducer producer;
    private final RandomStockQuoteGenerator generator;

    public ScheduledStockQuoteProducer(StockQuoteProducer producer, RandomStockQuoteGenerator generator) {
        this.producer = producer;
        this.generator = generator;
    }

    @Scheduled(fixedRateString = "${kafka.producer.rate.ms}")
    public void produce() {
        StockQuote stockQuote = generator.generate();
        producer.produce(stockQuote);
    }
}
