package com.example.spring.kafka.producer.rest;

import lombok.extern.slf4j.Slf4j;
import com.example.avro.stock.quote.StockQuote;
import com.example.spring.kafka.producer.StockQuoteProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/quotes")
@Slf4j
public class StockQuoteRestController {

    private final StockQuoteProducer stockQuoteProducer;

    public StockQuoteRestController(StockQuoteProducer stockQuoteProducer) {
        this.stockQuoteProducer = stockQuoteProducer;
    }

    @PostMapping
    public void produce(@RequestBody StockQuoteRequest request) {
        StockQuote stockQuote = new StockQuote(request.getSymbol(), request.getExchange(), request.getTradeValue(), request.getCurrency(), request.getDescription(), Instant.now());
        stockQuoteProducer.produce(stockQuote);
        log.info("Produce stock quote via Rest API");
    }
}
