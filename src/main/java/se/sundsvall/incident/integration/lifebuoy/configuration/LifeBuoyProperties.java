package se.sundsvall.incident.integration.lifebuoy.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "integration.lifebuoy")
public record LifeBuoyProperties(int connectTimeout, int readTimeout, String apiKey) {

}
