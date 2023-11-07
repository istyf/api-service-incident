package se.sundsvall.incident.api;

import static org.springframework.http.ResponseEntity.ok;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.Problem;

import se.sundsvall.incident.api.model.ValidCategoryResponse;
import se.sundsvall.incident.api.model.ValidOepCategoryResponse;
import se.sundsvall.incident.api.model.ValidStatusResponse;
import se.sundsvall.incident.integration.db.entity.util.Status;
import se.sundsvall.incident.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Valid list resources")
@RestController
@RequestMapping("/api")
public class ValidListResource {

	private final CategoryService categoryService;

	public ValidListResource(final CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Operation(summary = "Get a list of valid statuses")
	@ApiResponse(
		responseCode = "200",
		description = "Successful Operation",
		useReturnTypeSchema = true)
	@ApiResponse(
		responseCode = "400",
		description = "Bad Request",
		content = @Content(schema = @Schema(implementation = Problem.class)))
	@ApiResponse(
		responseCode = "500",
		description = "Internal Server Error",
		content = @Content(schema = @Schema(implementation = Problem.class)))
	@GetMapping("/validstatuses")
	public ResponseEntity<List<ValidStatusResponse>> getValidStatuses() {
		return ok(Arrays.stream(Status.values())
			.map(dto -> ValidStatusResponse.builder()
				.withStatus(dto.getLabel())
				.withStatusId(dto.getValue()).build())
			.toList());
	}

	@Operation(summary = "Get a list of valid categories")
	@ApiResponse(responseCode = "200", description = "Successful Operation",
		useReturnTypeSchema = true)
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
		var validCategories = categoryService.fetchValidCategories();
		return ok(validCategories);
	}

	@Operation(summary = "Get a list of valid categories in oep format")
	@ApiResponse(
		responseCode = "200",
		description = "Successful Operation",
		useReturnTypeSchema = true)
	@ApiResponse(
		responseCode = "400",
		description = "Bad Request",
		content = @Content(schema = @Schema(implementation = Problem.class)))
	@ApiResponse(
		responseCode = "500",
		description = "Internal Server Error",
		content = @Content(schema = @Schema(implementation = Problem.class)))
	@GetMapping("validcategories/oep")
	public ResponseEntity<List<ValidOepCategoryResponse>> getValidOepCategories() {
		var validOepCategories = categoryService.fetchValidOepCategories();
		return ok(validOepCategories);
	}
}
