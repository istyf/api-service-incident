package se.sundsvall.incident.integration.messaging;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import generated.se.sundsvall.messaging.EmailAttachment;
import generated.se.sundsvall.messaging.EmailRequest;
import generated.se.sundsvall.messaging.EmailSender;
import se.sundsvall.incident.dto.AttachmentDto;
import se.sundsvall.incident.dto.IncidentDto;

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

		final String[] splitDescription = dto.getDescription().split("-");
		final var htmlmessage = "<p>Vattenmätare " + splitDescription[0] + " genererat larm" + splitDescription[1] + " klockan " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "</p>";
		return new EmailRequest(properties.getMsvaRecipientEmailAddress(), properties.getMsvaEmailSubject() + " " + dto.getDescription())
			.sender(new EmailSender(properties.getSenderName(), properties.getSenderEmailAddress()))
			.htmlMessage(Base64.getEncoder().encodeToString(htmlmessage.getBytes(StandardCharsets.UTF_8)));
	}

	EmailRequest toEmailDto(IncidentDto dto) {
		return new EmailRequest(properties.getRecipientEmailAddress(), "Det har inkommit en felanmälan.")
			.sender(new EmailSender(properties.getSenderName(), properties.getSenderEmailAddress()))
			.htmlMessage(generateHtmlMessage(dto))
			.attachments(dto.getAttachments().stream()
				.map(this::toAttachmentEmailDto)
				.toList());
	}

	private String generateHtmlMessage(IncidentDto dto) {
		final Map<String, Object> templateModel = new HashMap<>();

		templateModel.put("incidentId", dto.getIncidentId());
		templateModel.put("phonenumber", dto.getPhoneNumber());
		templateModel.put("email", dto.getEmail());
		templateModel.put("coords", dto.getMapCoordinates());
		templateModel.put("emailDescription", dto.getDescription());
		templateModel.put("currentTime", OffsetDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		templateModel.put("feedbackmail", properties.getFeedbackEmail());
		final Context thymeleafContext = new Context();
		thymeleafContext.setVariables(templateModel);
		final var result = thymeleafTemplateEngine.process("email-template.html", thymeleafContext);
		if (result != null) {
			return Base64.getEncoder().encodeToString(result.getBytes(StandardCharsets.UTF_8));
		}
		return null;
	}

	EmailAttachment toAttachmentEmailDto(AttachmentDto dto) {
		return new EmailAttachment(dto.getName(), dto.getFile())
			.contentType(dto.getMimeType());
	}
}
