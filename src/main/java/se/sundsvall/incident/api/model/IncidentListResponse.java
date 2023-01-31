package se.sundsvall.incident.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with", builderClassName = "Builder")
@JsonDeserialize(builder = IncidentListResponse.Builder.class)
@AllArgsConstructor
public class IncidentListResponse {
    private String incidentId;
    private String externalCaseId;
    private String status;
}
