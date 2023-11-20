package se.sundsvall.incident.integration.messaging;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.sundsvall.incident.TestDataFactory.createIncidentEntity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import se.sundsvall.incident.integration.messaging.configuration.MessagingProperties;

import generated.se.sundsvall.messaging.EmailRequest;
import generated.se.sundsvall.messaging.MessageResult;

@ExtendWith(MockitoExtension.class)
class MessagingIntegrationTest {

	@Mock
	private MessagingProperties mockMessagingProperties;

	@Mock
	private MessagingClient mockMessagingClient;

	@InjectMocks
	private MessagingIntegration messagingIntegration;

	@Test
	void sendEmail() {
		var address = "someemail@host.se";
		var name = "someEmailName";
		var replyTo = "elsewhere@email.com";
		final var sender = new MessagingProperties.Sender(address, name, replyTo);
		when(mockMessagingProperties.sender()).thenReturn(sender);

		var incident = createIncidentEntity();

		when(mockMessagingClient.sendEmail(any())).thenReturn(new MessageResult());
		messagingIntegration.sendEmail(incident);

		verify(mockMessagingClient).sendEmail(any(EmailRequest.class));

	}

	@Test
	void sendMSVAEmail() {
		var address = "someemail@host.se";
		var name = "someEmailName";
		var replyTo = "elsewhere@email.com";
		final var sender = new MessagingProperties.Sender(address, name, replyTo);
		when(mockMessagingProperties.sender()).thenReturn(sender);
		var incident = createIncidentEntity();

		when(mockMessagingClient.sendEmail(any())).thenReturn(new MessageResult());
		messagingIntegration.sendMSVAEmail(incident);

		verify(mockMessagingClient).sendEmail(any(EmailRequest.class));
	}
}