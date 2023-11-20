package se.sundsvall.incident.integration.messaging;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static java.util.Base64.getEncoder;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import se.sundsvall.incident.integration.db.entity.AttachmentEntity;
import se.sundsvall.incident.integration.db.entity.IncidentEntity;
import se.sundsvall.incident.integration.messaging.configuration.MessagingProperties;

import generated.se.sundsvall.messaging.EmailAttachment;
import generated.se.sundsvall.messaging.EmailRequest;
import generated.se.sundsvall.messaging.EmailSender;

@EnableConfigurationProperties(MessagingProperties.class)
public final class MessagingMapper {


	private static final SpringTemplateEngine thymeleafTemplateEngine = new SpringTemplateEngine();

	private MessagingMapper() {

	}

	public static EmailRequest toMSVAEmailRequest(final IncidentEntity incident, final MessagingProperties properties) {
		final String[] splitDescription = incident.getDescription().split("-");
		final var htmlmessage = "<p>" + incident.getCategory().getLabel() + " " + splitDescription[0] + " genererat larm" + splitDescription[1] + " klockan " + now().format(ISO_LOCAL_DATE_TIME) + "</p>";
		return new EmailRequest(incident.getCategory().getForwardTo(), incident.getCategory().getSubject() + " " + incident.getDescription())
			.htmlMessage(getEncoder().encodeToString(htmlmessage.getBytes(UTF_8)))
			.sender(new EmailSender()
				.address(properties.sender().address())
				.name(properties.sender().name())
				.replyTo(properties.sender().replyTo()));

	}

	public static EmailRequest toEmailDto(final IncidentEntity incident, final MessagingProperties properties) {
		return new EmailRequest(incident.getCategory().getForwardTo(), "Det har inkommit en felanmälan.")
			.sender(new EmailSender(properties.sender().name(), properties.sender().address()))
			.htmlMessage(generateHtmlMessage(incident, properties))
			.attachments(incident.getAttachments().stream()
				.map(MessagingMapper::toAttachmentEmailDto)
				.toList());
	}

	private static String generateHtmlMessage(IncidentEntity incident, final MessagingProperties properties) {
		final Map<String, Object> templateModel = new HashMap<>();

		templateModel.put("incidentId", incident.getIncidentId());
		templateModel.put("phonenumber", incident.getPhoneNumber());
		templateModel.put("email", incident.getEmail());
		templateModel.put("coords", incident.getCoordinates());
		templateModel.put("emailDescription", incident.getDescription());
		templateModel.put("currentTime", OffsetDateTime.now().format(ISO_LOCAL_DATE_TIME));
		templateModel.put("feedbackmail", properties.sender().replyTo());
		final Context thymeleafContext = new Context();
		thymeleafContext.setVariables(templateModel);
		final var result = thymeleafTemplateEngine.process("email-template.html", thymeleafContext);
		if (result != null) {
			return getEncoder().encodeToString(result.getBytes(UTF_8));
		}
		return null;
	}

	static EmailAttachment toAttachmentEmailDto(AttachmentEntity attachment) {
		return new EmailAttachment(attachment.getName(), attachment.getFile())
			.contentType(attachment.getMimeType());
	}
}
