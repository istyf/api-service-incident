package se.sundsvall.incident.integration.messaging;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "email")
class EmailMapperProperties {

	private String feedbackEmail;
	private String senderEmailAddress;
	private String senderName;

	private String msvaRecipientEmailAddress;
	private String msvaEmailSubject;

	private String recipientEmailAddress;
}
