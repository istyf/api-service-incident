package se.sundsvall.incident.integration.messaging;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import se.sundsvall.incident.dto.AttachmentDto;
import se.sundsvall.incident.dto.IncidentDto;

import generated.se.sundsvall.messaging.EmailAttachment;
import generated.se.sundsvall.messaging.EmailRequest;
import generated.se.sundsvall.messaging.EmailSender;

@Component
@EnableConfigurationProperties(EmailMapperProperties.class)
class EmailMapper {

    private final EmailMapperProperties properties;
    private final SpringTemplateEngine thymeleafTemplateEngine;

    EmailMapper(EmailMapperProperties properties, SpringTemplateEngine thymeleafTemplateEngine) {
        this.properties = properties;
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
    }

    EmailRequest toMSVAEmailDto(IncidentDto dto) {

        String[] splitDescription = dto.getDescription().split("-");
        var htmlmessage = "<p>Vattenmätare " + splitDescription[0] + " genererat larm" + splitDescription[1] + " klockan " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "</p>";
        return new EmailRequest()
                .sender(new EmailSender()
                        .address(properties.getSenderEmailAddress())
                        .name(properties.getSenderName()))
                .emailAddress(properties.getMsvaRecipientEmailAddress())
                .subject(properties.getMsvaEmailSubject() + " " + dto.getDescription())
                .htmlMessage(Base64.getEncoder().encodeToString(htmlmessage.getBytes(StandardCharsets.UTF_8)));
    }

    EmailRequest toEmailDto(IncidentDto dto) {
        return new EmailRequest()
                .sender(new EmailSender()
                        .address(properties.getSenderEmailAddress())
                        .name(properties.getSenderName()))
                .emailAddress(properties.getRecipientEmailAddress())
                .subject("Det har inkommit en felanmälan.")
                .htmlMessage(generateHtmlMessage(dto))
                .attachments(dto.getAttachments().stream()
                        .map(this::toAttachmentEmailDto)
                        .toList());
    }

    private String generateHtmlMessage(IncidentDto dto) {
        Map<String, Object> templateModel = new HashMap<>();

        templateModel.put("incidentId", dto.getIncidentId());
        templateModel.put("phonenumber", dto.getPhoneNumber());
        templateModel.put("email", dto.getEmail());
        templateModel.put("coords", dto.getMapCoordinates());
        templateModel.put("emailDescription", dto.getDescription());
        templateModel.put("currentTime", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        templateModel.put("feedbackmail", properties.getFeedbackEmail());
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        var result = thymeleafTemplateEngine.process("email-template.html", thymeleafContext);
        if (result != null) {
            return Base64.getEncoder().encodeToString(result.getBytes(StandardCharsets.UTF_8));
        } else {
            return null;
        }
    }

    EmailAttachment toAttachmentEmailDto(AttachmentDto dto) {
        return new EmailAttachment()
                .content(dto.getFile())
                .name(dto.getName())
                .contentType(dto.getMimeType());

    }
}
