package se.sundsvall.incident.api.model;

import java.time.LocalDateTime;
import java.util.List;

import se.sundsvall.incident.integration.db.entity.util.Status;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class IncidentResponse {

	@Schema(description = "The Incident ID", example = "58f96da8-6d76-4fa6-bb92-64f71fdc6aa5")
	private String incidentId;

	@Schema(description = "The external case ID", example = "12345")
	private String externalCaseId;

	@Schema(description = "The ID of the person", example = "58f96da8-6d76-4fa6-bb92-64f71fdc6aa5")
	private String personId;

	@Schema(description = "The phone number", example = "0701234578")
	private String phoneNumber;

	@Schema(description = "Reporters e-mail address", example = "someemail@somehost.se")
	private String email;

	@Schema(description = "The way the reporter want to be contacted", example = "email")
	private String contactMethod;

	@Schema(description = "Description of the incident", example = "A description")
	private String description;

	@Schema(description = "Status of the incident", example = "INSKICKAT")
	private Status status;

	@Schema(description = "The incident category")
	private Category category;

	@Schema(description = "Attachments")
	private List<Attachment> attachments;

	@Schema(description = "When the incident were created", example = "2023-11-07T10:15:30")
	private LocalDateTime created;

	@Schema(description = "When the incident were last updated", example = "2023-11-07T10:15:30")
	private LocalDateTime updated;

}
