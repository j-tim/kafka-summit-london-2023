package com.example.spring.kafka.consumer.service;

import com.example.avro.stock.quote.StockQuote;

public interface StockQuoteService {


    void handle(StockQuote stockQuote);
}
