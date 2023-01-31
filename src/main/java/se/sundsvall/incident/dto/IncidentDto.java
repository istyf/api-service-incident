package se.sundsvall.incident.dto;


import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class IncidentDto {
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
    private String mapCoordinates;
    private String statusText;
    private Integer statusId;
    private String feedback;
    private List<AttachmentDto> attachments;
}
