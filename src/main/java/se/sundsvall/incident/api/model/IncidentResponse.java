package se.sundsvall.incident.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with", builderClassName = "Builder")
@JsonDeserialize(builder = IncidentResponse.Builder.class)
@AllArgsConstructor
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
