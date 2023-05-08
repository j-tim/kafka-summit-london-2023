/*
 * Copyright 2012-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.spring.kafka.producer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * Configuration properties for {@link OpenTelemetryTracingConfiguration}.
 *
 * @author Tim van Baarsen
 */
@ConfigurationProperties("management.otlp.tracing")
public class OpenTelemetryTracingProperties {

    /**
     * URL to the OpenTelemetry Grpc endpoint.
     */
    private String endpoint = "http://localhost:4317";

    private String compression = "none";

    /**
     * Timeout for requests to OpenTelemetry.
     */
    private Duration timeout = Duration.ofSeconds(10);

    public String getEndpoint() {
        return this.endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public String getCompression() {
        return compression;
    }

    public void setCompression(String compression) {
        this.compression = compression;
    }
}
