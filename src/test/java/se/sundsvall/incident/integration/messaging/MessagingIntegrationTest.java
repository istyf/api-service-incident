package se.sundsvall.incident.integration.messaging;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.sundsvall.incident.TestDataFactory.createEmailRequest;
import static se.sundsvall.incident.TestDataFactory.createIncidentEntity;
import static se.sundsvall.incident.TestDataFactory.createMSVAEmailRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import generated.se.sundsvall.messaging.EmailRequest;
import generated.se.sundsvall.messaging.MessageResult;

@ExtendWith(MockitoExtension.class)
class MessagingIntegrationTest {

	@Mock
	private MessagingMapper messagingMapper;

	@Mock
	private MessagingClient messagingClient;

	@InjectMocks
	private MessagingIntegration messagingIntegration;

	@Test
	void sendEmail() {
		var incident = createIncidentEntity();
		when(messagingMapper.toEmailDto(any())).thenReturn(createEmailRequest());
		when(messagingClient.sendEmail(any())).thenReturn(new MessageResult());
		messagingIntegration.sendEmail(incident);

		verify(messagingClient, times(1)).sendEmail(any(EmailRequest.class));
		verify(messagingMapper, times(1)).toEmailDto(any());
	}

	@Test
	void sendMSVAEmail() {
		var incident = createIncidentEntity();
		when(messagingMapper.toMSVAEmailRequest(any())).thenReturn(createMSVAEmailRequest());
		when(messagingClient.sendEmail(any())).thenReturn(new MessageResult());
		messagingIntegration.sendMSVAEmail(incident);

		verify(messagingClient, times(1)).sendEmail(any(EmailRequest.class));
		verify(messagingMapper, times(1)).toMSVAEmailRequest(any());
	}
}