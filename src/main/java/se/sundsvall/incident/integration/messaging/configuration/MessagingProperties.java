package se.sundsvall.incident.integration.messaging.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "integration.messaging")
public record MessagingProperties(int connectTimeout, int readTimeout, Sender sender) {

	public record Sender(String address, String name, String replyTo) {

	}

}
