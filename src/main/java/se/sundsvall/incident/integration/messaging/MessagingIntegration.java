package se.sundsvall.incident.integration.messaging;

import org.springframework.stereotype.Component;

import se.sundsvall.incident.integration.db.entity.IncidentEntity;

@Component
public class MessagingIntegration {

	static final String INTEGRATION_NAME = "MessagingClient";
	private final MessagingMapper messagingMapper;
	private final MessagingClient client;

	public MessagingIntegration(final MessagingMapper messagingMapper, final MessagingClient client) {
		this.messagingMapper = messagingMapper;
		this.client = client;
	}

	public void sendEmail(final IncidentEntity incident) {
		client.sendEmail(messagingMapper.toEmailDto(incident));
	}

	public void sendMSVAEmail(final IncidentEntity incident) {
		client.sendEmail(messagingMapper.toMSVAEmailRequest(incident));
	}
}
