package com.example.spring.kafka.consumer.config;

import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.tracing.ConditionalOnEnabledTracing;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OpenTelemetryTracingProperties.class)
@ConditionalOnEnabledTracing
@Slf4j
public class OpenTelemetryTracingConfiguration {

    private final OpenTelemetryTracingProperties openTelemetryTracingProperties;

    public OpenTelemetryTracingConfiguration(OpenTelemetryTracingProperties openTelemetryTracingProperties) {
        this.openTelemetryTracingProperties = openTelemetryTracingProperties;
    }

    @Bean
    public SpanExporter spanExporter() {
        log.info("Creating custom OtlpGrpcSpanExporter bean!");
        return OtlpGrpcSpanExporter.builder()
                .setTimeout(openTelemetryTracingProperties.getTimeout())
                .setEndpoint(openTelemetryTracingProperties.getEndpoint())
                .setCompression(openTelemetryTracingProperties.getCompression())
                .build();
    }
}
