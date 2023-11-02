package se.sundsvall.incident.api.model;

import java.util.List;

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
	private String created;
	private String updated;
	private String phoneNumber;
	private String email;
	private String contactMethod;
	private Integer category;
	private String description;
	private String status;
	private List<AttachmentResponse> attachments;

}
