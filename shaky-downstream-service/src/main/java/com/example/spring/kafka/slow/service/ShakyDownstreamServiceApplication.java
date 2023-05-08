package com.example.spring.kafka.slow.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShakyDownstreamServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShakyDownstreamServiceApplication.class, args);
	}

}
