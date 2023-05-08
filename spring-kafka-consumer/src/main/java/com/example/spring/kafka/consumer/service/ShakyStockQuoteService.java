package com.example.spring.kafka.consumer.service;

import com.example.avro.stock.quote.StockQuote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ShakyStockQuoteService implements StockQuoteService {

    private final RestTemplate restTemplate;

    private final boolean callDownStreamService;
    private final String url;

    public ShakyStockQuoteService(RestTemplate restTemplate,
                                  @Value("${kafka.consumer.call-downstream-service}") boolean callDownStreamService,
                                  @Value("${shaky.service.api.url}") String url) {
        this.restTemplate = restTemplate;
        this.callDownStreamService = callDownStreamService;
        this.url = url;

        log.info("Call downstream service: {}, url: {}", callDownStreamService, url);
    }

    @Override
    public void handle(StockQuote stockQuote) {
        if ("KABOOM".equalsIgnoreCase(stockQuote.getSymbol())) {
            throw new RuntimeException("Whoops something went wrong...");
        }

        if (callDownStreamService) {
            ResponseEntity<Greeting> reponse = restTemplate.getForEntity(url, Greeting.class);
        }
    }
}

