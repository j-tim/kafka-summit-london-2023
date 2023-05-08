package com.example.kafka.streams.config;

import com.example.avro.stock.quote.StockQuote;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.KafkaStreamBrancher;

import static com.example.kafka.streams.config.KafkaTopicsConfiguration.STOCK_QUOTES_TOPIC_NAME;

@Slf4j
@Configuration
@EnableKafkaStreams
public class KafkaStreamsConfig {
    @Autowired
    private ObservationRegistry observationRegistry;

    @Bean
    public KStream<String, StockQuote> kStream(StreamsBuilder streamsBuilder) {
        KStream<String, StockQuote> branchedStream = new KafkaStreamBrancher<String, StockQuote>()
                .branch((key, value) -> value.getExchange().equalsIgnoreCase("NYSE"), kStream -> kStream.to("stock-quotes-exchange-nyse"))
                .branch((key, value) -> value.getExchange().equalsIgnoreCase("NASDAQ"), kStream -> kStream.to("stock-quotes-exchange-nasdaq"))
                .branch((key, value) -> value.getExchange().equalsIgnoreCase("AMS"), kStream -> kStream.to("stock-quotes-exchange-ams"))
                .defaultBranch(kStream -> kStream.to("stock-quotes-exchange-other"))
                .onTopOf(streamsBuilder.stream(STOCK_QUOTES_TOPIC_NAME));
        branchedStream.peek((key, value) -> {
            Observation.createNotStarted("on-message", this.observationRegistry).observe(() -> {
                log.info("Got message <{}:{}>", key, value);
            });
        });
        return branchedStream;
    }
}
