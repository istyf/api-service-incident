package se.sundsvall.incident.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AttachmentDto {
    private String category;
    private String extension;
    private String mimeType;
    private String note;
    private String file;
    private String name;
    private String incidentId;
}
