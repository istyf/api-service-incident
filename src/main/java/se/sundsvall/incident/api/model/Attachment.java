package se.sundsvall.incident.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Attachment {

	@Schema(description = "The category label", example = "Vattenmätare")
	private String category;

	@Schema(description = "The name of the attachment", example = "Vattenmätningar")
	private String name;

	@Schema(description = "The attachment extension", example = ".pdf")
	private String extension;

	@Schema(description = "The attachment MIME type", example = "application/json")
	private String mimeType;

	@Schema(description = "The attachment note", example = "Beskrivande meddelande")
	private String note;

	@Schema(description = "The file content as base64 encoded string", example = "aGVqaGVq")
	private String file;

}
