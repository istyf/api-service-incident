package se.sundsvall.incident.api.model;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class AttachmentResponse {

	private String incidentId;
	private String category;
	private String extension;
	private String mimeType;
	private String note;
	private String file;
	private String name;

}
