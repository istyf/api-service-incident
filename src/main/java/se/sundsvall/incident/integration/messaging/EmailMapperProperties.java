package se.sundsvall.incident.integration.messaging;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "email")
class EmailMapperProperties {

    private String feedbackEmail;
    private String senderEmailAddress;
    private String senderName;

    private String msvaRecipientEmailAddress;
    private String msvaEmailSubject;

    private String RecipientEmailAddress;
}
