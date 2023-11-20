package se.sundsvall.incident.api.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class IncidentSaveResponse {

	@Schema(description = "Incident status", example = "KLART")
	private String status;

	@Schema(description = "The incident ID", example = "58f96da8-6d76-4fa6-bb92-64f71fdc6aa5")
	private String incidentId;

}
