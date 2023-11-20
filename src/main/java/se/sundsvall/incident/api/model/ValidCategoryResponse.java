package se.sundsvall.incident.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ValidCategoryResponse {

	@Schema(description = "The name of the category", example = "VATTENMÄTARE")
	private String category;

	@Schema(description = "The ID of the category", example = "15")
	private Integer categoryId;

}
