package se.sundsvall.incident.integration.messaging;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import se.sundsvall.incident.integration.AbstractIntegrationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "integration.messaging")
public class MessagingIntegrationProperties extends AbstractIntegrationProperties {
    private OAuth2 oAuth2 = new OAuth2();
}
