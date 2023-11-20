package se.sundsvall.incident.api.model;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import jakarta.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class AttachmentRequest {

	@NotBlank
	@Schema(description = "The attachment category type", example = "Adress", requiredMode = REQUIRED)
	private String category;

	@NotBlank
	@Schema(description = "The attachment extension type", example = ".txt", requiredMode = REQUIRED)
	private String extension;

	@NotBlank
	@Schema(description = "The attachment content type", example = "text/plain", requiredMode = REQUIRED)
	private String mimeType;

	@Schema(description = "The attachment note", example = "a small note about this file")
	private String note;

	@NotBlank
	@Schema(description = "The attachment (file) content as a BASE64-encoded string", example = "aGVsbG8gd29ybGQK", requiredMode = REQUIRED)
	private String file;
}
