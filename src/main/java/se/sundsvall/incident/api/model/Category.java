package se.sundsvall.incident.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Schema(description = "Category model")
public class Category {

	@Schema(description = "The category id", example = "15")
	private Integer categoryId;

	@Schema(description = "Category name", example = "GANGCYKELVAG")
	private String title;

	@Schema(description = "Description of the category", example = "Gång- och cykelväg")
	private String label;

	@Schema(description = "The E-mail where the incidents are forwarded to", example = "nowhere@nowhere.com")
	private String forwardTo;

	@Schema(description = "The E-mail subject", example = "Nytt Larm")
	private String subject;

}
