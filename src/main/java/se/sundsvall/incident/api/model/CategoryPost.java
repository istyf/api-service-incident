package se.sundsvall.incident.api.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

public record CategoryPost(

	@NotBlank
	@Schema(description = "Category name", example = "GANGCYKELVAG", requiredMode = Schema.RequiredMode.REQUIRED)
	String title,

	@NotBlank
	@Schema(description = "Description of the category", example = "Gång- och cykelväg", requiredMode = Schema.RequiredMode.REQUIRED)
	String label,

	@NotBlank
	@Email
	@Schema(description = "The E-mail where the incidents are forwarded to", example = "nowhere@nowhere.com", requiredMode = Schema.RequiredMode.REQUIRED)
	String forwardTo) {
}
