package se.sundsvall.incident.api;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.Problem;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import se.sundsvall.incident.api.model.ValidCategoryResponse;
import se.sundsvall.incident.api.model.ValidOepCategoryReponse;
import se.sundsvall.incident.api.model.ValidStatusResponse;
import se.sundsvall.incident.dto.Category;
import se.sundsvall.incident.dto.Status;

@Tag(name = "Valid list resources")
@RestController
@RequestMapping("/api")
public class ValidListResource {

	@Operation(summary = "Get a list of valid statuses")
	@ApiResponse(
		responseCode = "200",
		description = "Successful Operation",
		content = @Content(schema = @Schema(implementation = ValidStatusResponse.class)))
	@ApiResponse(
		responseCode = "400",
		description = "Bad Request",
		content = @Content(schema = @Schema(implementation = Problem.class)))
	@ApiResponse(
		responseCode = "500",
		description = "Internal Server Error",
		content = @Content(schema = @Schema(implementation = Problem.class)))
	@GetMapping("/validstatuses")
	public ResponseEntity<List<ValidStatusResponse>> getValidstatuses() {
		return ResponseEntity.ok(Arrays.stream(Status.values())
			.map(dto -> ValidStatusResponse.builder()
				.withStatus(dto.getLabel())
				.withSTATUS_ID(dto.getValue()).build())
			.toList());
	}

	@Operation(summary = "Get a list of valid categories")
	@ApiResponse(
		responseCode = "200",
		description = "Successful Operation",
		content = @Content(schema = @Schema(implementation = ValidCategoryResponse.class)))
	@ApiResponse(
		responseCode = "400",
		description = "Bad Request",
		content = @Content(schema = @Schema(implementation = Problem.class)))
	@ApiResponse(
		responseCode = "500",
		description = "Internal Server Error",
		content = @Content(schema = @Schema(implementation = Problem.class)))
	@GetMapping("/validcategories")
	public ResponseEntity<List<ValidCategoryResponse>> getValidCategories() {
		return ResponseEntity.ok(Arrays.stream(Category.values())
			.map(cat -> ValidCategoryResponse.builder()
				.withCategory(cat.getLabel())
				.withCATEGORY_ID(cat.getValue())
				.build())
			.toList());
	}

	@Operation(summary = "Get a list of valid categories in oep format")
	@ApiResponse(
		responseCode = "200",
		description = "Successful Operation",
		content = @Content(schema = @Schema(implementation = ValidOepCategoryReponse.class)))
	@ApiResponse(
		responseCode = "400",
		description = "Bad Request",
		content = @Content(schema = @Schema(implementation = Problem.class)))
	@ApiResponse(
		responseCode = "500",
		description = "Internal Server Error",
		content = @Content(schema = @Schema(implementation = Problem.class)))
	@GetMapping("validcategories/oep")
	public ResponseEntity<List<ValidOepCategoryReponse>> getValidOepCategories() {
		return ResponseEntity.ok(Arrays.stream(Category.values())
			.map(cat -> ValidOepCategoryReponse.builder()
				.withKey(String.valueOf(cat.getValue()))
				.withValue(cat.getLabel())
				.build())
			.toList());
	}
}
