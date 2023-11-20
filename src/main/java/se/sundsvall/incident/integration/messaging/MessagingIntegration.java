package se.sundsvall.incident.integration.messaging;

import static se.sundsvall.incident.integration.messaging.MessagingMapper.toEmailDto;
import static se.sundsvall.incident.integration.messaging.MessagingMapper.toMSVAEmailRequest;

import java.util.Optional;

import org.springframework.stereotype.Component;

import se.sundsvall.incident.integration.db.entity.IncidentEntity;
import se.sundsvall.incident.integration.messaging.configuration.MessagingProperties;

import generated.se.sundsvall.messaging.MessageResult;

@Component
public class MessagingIntegration {

	private final MessagingProperties messagingProperties;
	private final MessagingClient client;

	public MessagingIntegration(final MessagingProperties messagingProperties,
		final MessagingClient client) {
		this.messagingProperties = messagingProperties;
		this.client = client;
	}

	public Optional<MessageResult> sendEmail(final IncidentEntity incident) {
		return Optional.of(client.sendEmail(toEmailDto(incident, messagingProperties)));
	}

	public Optional<MessageResult> sendMSVAEmail(final IncidentEntity incident) {
		return Optional.of(client.sendEmail(toMSVAEmailRequest(incident, messagingProperties)));
	}
}
