package com.example.spring.kafka.producer.generator;

import com.example.avro.stock.quote.StockQuote;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

@Component
public class RandomStockQuoteGenerator extends AbstractRandomStockQuoteGenerator {

    public StockQuote generate() {
        Instrument randomInstrument = pickRandomInstrument();
        BigDecimal randomPrice = generateRandomPrice();
        return new StockQuote(randomInstrument.getSymbol(), randomInstrument.getExchange(), randomPrice.toPlainString(),
            randomInstrument.getCurrency(), randomInstrument.getSymbol() + " stock", Instant.now());
    }
}
