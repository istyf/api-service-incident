package se.sundsvall.incident.integration.messaging;

import generated.se.sundsvall.messaging.EmailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import static se.sundsvall.incident.integration.messaging.MessagingIntegration.INTEGRATION_NAME;

@FeignClient(
        name = INTEGRATION_NAME,
        url = "${integration.messaging.client.url}",
        configuration = MessagingClientConfiguration.class
)
interface MessagingClient {

    @PostMapping("/email")
    void sendEmail(EmailRequest request);
}
