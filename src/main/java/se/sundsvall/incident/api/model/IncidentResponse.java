package se.sundsvall.incident.api.model;

import java.time.LocalDateTime;
import java.util.List;

import se.sundsvall.incident.integration.db.entity.util.Status;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class IncidentResponse {

	private String incidentId;
	private String externalCaseId;
	private String personId;
	private String phoneNumber;
	private String email;
	private String contactMethod;
	private String description;
	private Status status;
	private Category category;
	private List<Attachment> attachments;
	private LocalDateTime created;
	private LocalDateTime updated;

}
