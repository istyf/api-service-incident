package se.sundsvall.incident.integration.messaging;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.sundsvall.incident.TestDataFactory.createAttachmentEntity;
import static se.sundsvall.incident.TestDataFactory.createCategoryEntity;
import static se.sundsvall.incident.TestDataFactory.createIncidentEntity;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.spring6.SpringTemplateEngine;

import se.sundsvall.incident.integration.messaging.configuration.MessagingProperties;

@ExtendWith(MockitoExtension.class)
class MessagingMapperTest {

	@Mock
	private MessagingProperties mockProperties;

	@Mock
	private SpringTemplateEngine mockThymeleafTemplateEngine;

	@InjectMocks
	private MessagingMapper mockMessagingMapper;

	@ParameterizedTest
	@ValueSource(strings = {"Vattenmätare, Bräddövervakningslarm"})
	void toMSVAEmailRequest(final String categoryLabel) {
		var address = "someemail@host.se";
		var name = "someEmailName";
		var replyTo = "elsewhere@email.com";
		final var sender = new MessagingProperties.Sender(address, name, replyTo);
		final var incident = createIncidentEntity();
		incident.setCategory(createCategoryEntity(
			categoryEntity -> categoryEntity.setLabel(categoryLabel)));

		when(mockProperties.sender()).thenReturn(sender);
		final var response = mockMessagingMapper.toMSVAEmailRequest(incident, mockProperties);

		var decodedMessage = Base64.getDecoder().decode(response.getHtmlMessage());
		var email = new String(decodedMessage, StandardCharsets.UTF_8);
		assertThat(email).contains(categoryLabel);
		assertThat(response).isNotNull();
		assertThat(response.getSubject()).isEqualTo("this is a subject asdas - asdasd");
		assertThat(response.getEmailAddress()).isEqualTo("nowhere@nowhere.com");
		assertThat(response.getSender().getName()).isEqualTo("someEmailName");
		assertThat(response.getSender().getAddress()).isEqualTo("someemail@host.se");
		assertThat(response.getSender().getReplyTo()).isEqualTo("elsewhere@email.com");
		verify(mockProperties, times(3)).sender();
	}

	@Test
	void toEmailDto() {
		var address = "someemail@host.se";
		var name = "someEmailName";
		var replyTo = "elsewhere@email.com";
		final var sender = new MessagingProperties.Sender(address, name, replyTo);
		final var incident = createIncidentEntity();

		when(mockProperties.sender()).thenReturn(sender);
		final var response = mockMessagingMapper.toEmailDto(incident, mockProperties);

		assertThat(response).isNotNull();
		assertThat(response.getEmailAddress()).isEqualTo(incident.getCategory().getForwardTo());
		assertThat(response.getSender().getName()).isEqualTo("someEmailName");
		assertThat(response.getSender().getAddress()).isEqualTo("someemail@host.se");
		assertThat(response.getAttachments()).hasSize(2);
		verify(mockProperties, times(3)).sender();
	}

	@Test
	void toAttachmentEmailDto() {
		final var attachment = createAttachmentEntity();
		final var response = mockMessagingMapper.toAttachmentEmailDto(attachment);
		assertThat(response.getContent()).isEqualTo(attachment.getFile());
		assertThat(response.getName()).isEqualTo(attachment.getName());
		assertThat(response.getContentType()).isEqualTo(attachment.getMimeType());
	}
}
