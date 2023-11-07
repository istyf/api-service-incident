package se.sundsvall.incident.integration.messaging;

import java.util.Optional;

import org.springframework.stereotype.Component;

import se.sundsvall.incident.integration.db.entity.IncidentEntity;

import generated.se.sundsvall.messaging.MessageResult;

@Component
public class MessagingIntegration {

	private final MessagingMapper messagingMapper;
	private final MessagingClient client;

	public MessagingIntegration(final MessagingMapper messagingMapper, final MessagingClient client) {
		this.messagingMapper = messagingMapper;
		this.client = client;
	}

	public Optional<MessageResult> sendEmail(final IncidentEntity incident) {
		return Optional.of(client.sendEmail(messagingMapper.toEmailDto(incident)));
	}

	public Optional<MessageResult> sendMSVAEmail(final IncidentEntity incident) {
		return Optional.of(client.sendEmail(messagingMapper.toMSVAEmailRequest(incident)));
	}
}
