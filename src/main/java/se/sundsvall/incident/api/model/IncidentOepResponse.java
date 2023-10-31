package se.sundsvall.incident.api.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class IncidentOepResponse {

	private String incidentId;
	private String externalCaseId;
	private Integer statusId;
	private String statusText;

}
