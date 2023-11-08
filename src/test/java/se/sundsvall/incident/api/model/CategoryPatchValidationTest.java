package se.sundsvall.incident.api.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;

class CategoryPatchValidationTest {

	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private Validator validator;

	@BeforeEach
	void setUp() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Test
	void validFieldsTest() {
		var categoryPatch = new CategoryPatch("valid title", "valid label", "valid@email.com");

		var constraints = List.copyOf(validator.validate(categoryPatch));

		assertThat(constraints).isEmpty();
	}

	@Test
	void invalidEmailTest() {
		var categoryPatch = new CategoryPatch(null, null, "invalid email");

		var constraints = List.copyOf(validator.validate(categoryPatch));

		assertThat(constraints).hasSize(1);
		assertThat(constraints.get(0)).satisfies(constraintViolation -> {
			assertThat(constraintViolation.getPropertyPath()).hasToString("forwardTo");
			assertThat(constraintViolation.getMessage()).startsWith("must be a well-formed email address");
		});
	}

	@Test
	void blankEmailTest() {
		var categoryPatch = new CategoryPatch(null, null, "");

		var constraints = List.copyOf(validator.validate(categoryPatch));

		assertThat(constraints).isEmpty();
	}

	@Test
	void blankTitleTest() {
		var categoryPatch = new CategoryPatch("", null, null);

		var constraints = List.copyOf(validator.validate(categoryPatch));

		assertThat(constraints).hasSize(1);
		assertThat(constraints.get(0)).satisfies(constraintViolation -> {
			assertThat(constraintViolation.getPropertyPath()).hasToString("title");
			assertThat(constraintViolation.getMessage()).startsWith("must be null or a not blank");
		});
	}

	@Test
	void blankLabelTest() {
		var categoryPatch = new CategoryPatch(null, "", null);

		var constraints = List.copyOf(validator.validate(categoryPatch));

		assertThat(constraints).hasSize(1);
		assertThat(constraints.get(0)).satisfies(constraintViolation -> {
			assertThat(constraintViolation.getPropertyPath()).hasToString("label");
			assertThat(constraintViolation.getMessage()).startsWith("must be null or a not blank");
		});
	}
}
