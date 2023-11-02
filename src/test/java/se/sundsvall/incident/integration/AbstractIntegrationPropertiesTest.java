package se.sundsvall.incident.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import se.sundsvall.incident.integration.messaging.MessagingIntegrationProperties;

class AbstractIntegrationPropertiesTest {

	@Test
	void getterAndSetterTest() {
		var properties = new MessagingIntegrationProperties();

		var grantType = "client_credentials";
		var baseUrl = "baseUrl";
		var connectTimeout = Duration.ofSeconds(15);
		var readTimeout = Duration.ofSeconds(5);

		properties.getOAuth2().setGrantType(grantType);
		properties.setBaseUrl(baseUrl);
		properties.setConnectTimeout(connectTimeout);
		properties.setReadTimeout(readTimeout);

		assertThat(properties.getOAuth2().getGrantType()).isEqualTo("client_credentials");
		assertThat(properties.getBaseUrl()).isEqualTo("baseUrl");
		assertThat(properties.getConnectTimeout()).isEqualTo(Duration.ofSeconds(15));
		assertThat(properties.getReadTimeout()).isEqualTo(Duration.ofSeconds(5));

		var propertiesOauth2GrantType = properties.getOAuth2().getGrantType();
		var propertiesBaseUrl = properties.getBaseUrl();
		var propertiesConnectTimeout = properties.getConnectTimeout();
		var propertiesReadTimeout = properties.getReadTimeout();

		assertThat(propertiesOauth2GrantType).isEqualTo("client_credentials");
		assertThat(propertiesBaseUrl).isEqualTo("baseUrl");
		assertThat(propertiesConnectTimeout).isEqualTo(Duration.ofSeconds(15));
		assertThat(propertiesReadTimeout).isEqualTo(Duration.ofSeconds(5));

	}
}
