package se.sundsvall.incident.integration.messaging;

import org.springframework.stereotype.Component;
import se.sundsvall.incident.dto.IncidentDto;

@Component
public class MessagingIntegration {

    static final String INTEGRATION_NAME = "MessagingClient";
    private final EmailMapper emailMapper;
    private final MessagingClient client;

    public MessagingIntegration(EmailMapper emailMapper, MessagingClient client) {
        this.emailMapper = emailMapper;
        this.client = client;
    }

    public void sendEmail(IncidentDto dto) {

        client.sendEmail(emailMapper.toEmailDto(dto));
    }

    public void sendMSVAEmail(IncidentDto dto) {
        client.sendEmail(emailMapper.toMSVAEmailDto(dto));
    }
}
