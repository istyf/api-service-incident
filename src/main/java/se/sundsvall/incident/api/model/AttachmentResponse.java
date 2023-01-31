package se.sundsvall.incident.api.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with", builderClassName = "Builder")
@JsonDeserialize(builder = AttachmentResponse.Builder.class)
@AllArgsConstructor
public class AttachmentResponse {

    private String incidentId;
    private String category;
    private String extension;
    private String mimeType;
    private String note;
    private String file;
    private String name;
}
