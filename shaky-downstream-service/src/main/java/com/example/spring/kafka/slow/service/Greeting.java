package com.example.spring.kafka.slow.service;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Greeting {

    public final String message;

    public Greeting(String message) {
        this.message = message;
    }
}
