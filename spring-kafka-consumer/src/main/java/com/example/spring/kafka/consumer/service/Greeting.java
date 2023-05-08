package com.example.spring.kafka.consumer.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Greeting {

    public final String message;

    @JsonCreator
    public Greeting(String message) {
        this.message = message;
    }
}
