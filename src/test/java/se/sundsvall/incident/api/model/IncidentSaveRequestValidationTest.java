package se.sundsvall.incident.api.model;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.incident.TestDataFactory.buildIncidentSaveRequest;

import java.util.List;

import org.junit.jupiter.api.Test;

import se.sundsvall.incident.dto.Category;

class IncidentSaveRequestValidationTest extends AbstractValidationTest {

	@Test
	void testValidationWithValidValues() {
		final var request = buildIncidentSaveRequest(Category.FELPARKERAD_BIL);

		assertThat(validator.validate(request)).isEmpty();
	}

	@Test
	void testValidationWithInvalidPhoneNumber() {
		final var request = buildIncidentSaveRequest(Category.FELPARKERAD_BIL, req -> req.setPhoneNumber("076127121297"));

		final var constraints = List.copyOf(validator.validate(request));
		assertThat(constraints).hasSize(1);
		assertThat(constraints.get(0)).satisfies(constraintViolation -> {
			assertThat(constraintViolation.getPropertyPath()).hasToString("phoneNumber");
			assertThat(constraintViolation.getMessage()).startsWith("must match");
		});

	}

	@Test
	void testValidationWithInvalidEmail() {
		final var request = buildIncidentSaveRequest(Category.FELPARKERAD_BIL, req -> req.setEmail("emailAddress"));

		final var constraints = List.copyOf(validator.validate(request));
		assertThat(constraints).hasSize(1);
		assertThat(constraints.get(0)).satisfies(constraintViolation -> {
			assertThat(constraintViolation.getPropertyPath()).hasToString("email");
			assertThat(constraintViolation.getMessage()).isEqualTo("must be a well-formed email address");
		});
	}

	@Test
	void testValidationWithInvalidCoordinates() {
		final var request = buildIncidentSaveRequest(Category.FELPARKERAD_BIL, req -> req.setMapCoordinates("123123"));

		final var constraints = List.copyOf(validator.validate(request));
		assertThat(constraints).hasSize(1);
		assertThat(constraints.get(0)).satisfies(constraintViolation -> {
			assertThat(constraintViolation.getPropertyPath()).hasToString("mapCoordinates");
			assertThat(constraintViolation.getMessage()).isEqualTo("must be valid coordinates e.g 62.4097,17.24024");
		});
	}

	@Test
	void testValidationWithInvalidCategory() {
		final var request = buildIncidentSaveRequest(Category.FELPARKERAD_BIL, req -> req.setCategory(null));

		final var constraints = List.copyOf(validator.validate(request));
		assertThat(constraints).hasSize(1);
		assertThat(constraints.get(0)).satisfies(constraintViolation -> {
			assertThat(constraintViolation.getPropertyPath()).hasToString("category");
			assertThat(constraintViolation.getMessage()).startsWith("must not be null");
		});
	}

	@Test
	void testValidationWithInvalidDescription() {
		final var request = buildIncidentSaveRequest(Category.FELPARKERAD_BIL, req -> req.setDescription(null));

		final var constraints = List.copyOf(validator.validate(request));
		assertThat(constraints).hasSize(1);
		assertThat(constraints.get(0)).satisfies(constraintViolation -> {
			assertThat(constraintViolation.getPropertyPath()).hasToString("description");
			assertThat(constraintViolation.getMessage()).startsWith("must not be blank");
		});
	}
}
