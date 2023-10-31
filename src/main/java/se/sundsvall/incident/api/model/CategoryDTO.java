package se.sundsvall.incident.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@Schema(description = "Category model")
public class CategoryDTO {

	@Schema(description = "Category ID", example = "4")
	private int categoryId;

	@Schema(description = "Category name", example = "GANGCYKELVAG")
	private String title;

	@Schema(description = "Description of the category", example = "Gång- och cykelväg")
	private String label;

	@Schema(description = "The E-mail where the incidents are forwarded to", example = "nowhere@nowhere.com")
	private String forwardTo;

}
