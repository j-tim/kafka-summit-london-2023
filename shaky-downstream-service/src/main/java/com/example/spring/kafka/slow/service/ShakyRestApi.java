package com.example.spring.kafka.slow.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.Thread.*;

@RestController
@Slf4j
public class ShakyRestApi {

    private long delayMs;

    public ShakyRestApi(@Value("${shaky.rest.api.delay-ms}") long delayMs) {
        this.delayMs = delayMs;
        log.info("Started Shaky REST API with delay of: {} ms", delayMs);
    }

    @GetMapping("/api/greet")
    public Greeting greeting() {
        Greeting greeting = new Greeting("Hello World!");

        try {
            sleep(delayMs);
            log.info("Greeting: {}", greeting);
        } catch (InterruptedException e) {
            log.info("Whoops");
        }

        return greeting;
    }
}
