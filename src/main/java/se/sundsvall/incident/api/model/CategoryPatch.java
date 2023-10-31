package se.sundsvall.incident.api.model;

import jakarta.validation.constraints.Email;

import io.swagger.v3.oas.annotations.media.Schema;

public record CategoryPatch(

	@Schema(description = "Category title")
	String title,

	@Schema(description = "Category label")
	String label,

	@Email
	@Schema(description = "The E-mail where the incidents are forwarded to")
	String forwardTo) {
}
