package se.sundsvall.incident.api.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;

class CategoryPostValidationTest {

	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private Validator validator;

	@BeforeEach
	void setUp() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Test
	void validFieldsTest() {
		var categoryPost = new CategoryPost("valid title", "valid label", "valid@email.com", "valid subject");

		var constraints = List.copyOf(validator.validate(categoryPost));

		assertThat(constraints).isEmpty();
	}

	@Test
	void invalidTitleTest() {
		var categoryPost = new CategoryPost("", "valid label", "valid@email.com", "valid subject");

		var constraints = List.copyOf(validator.validate(categoryPost));

		assertThat(constraints).hasSize(1);
		assertThat(constraints.get(0)).satisfies(constraintViolation -> {
			assertThat(constraintViolation.getPropertyPath()).hasToString("title");
			assertThat(constraintViolation.getMessage()).startsWith("must not be blank");
		});
	}

	@Test
	void invalidLabelTest() {
		var categoryPost = new CategoryPost("valid title", "", "valid@email.com", "valid subject");

		var constraints = List.copyOf(validator.validate(categoryPost));

		assertThat(constraints).hasSize(1);
		assertThat(constraints.get(0)).satisfies(constraintViolation -> {
			assertThat(constraintViolation.getPropertyPath()).hasToString("label");
			assertThat(constraintViolation.getMessage()).startsWith("must not be blank");
		});
	}

	@Test
	void blankEmailTest() {
		var categoryPost = new CategoryPost("valid title", "valid label", "", "valid subject");

		var constraints = List.copyOf(validator.validate(categoryPost));

		assertThat(constraints).hasSize(1);
		assertThat(constraints.get(0)).satisfies(constraintViolation -> {
			assertThat(constraintViolation.getPropertyPath()).hasToString("forwardTo");
			assertThat(constraintViolation.getMessage()).startsWith("must not be blank");
		});
	}

	@Test
	void invalidEmailTest() {
		var categoryPost = new CategoryPost("valid title", "valid label", "invalid email", "valid subject");

		var constraints = List.copyOf(validator.validate(categoryPost));

		assertThat(constraints).hasSize(1);
		assertThat(constraints.get(0)).satisfies(constraintViolation -> {
			assertThat(constraintViolation.getPropertyPath()).hasToString("forwardTo");
			assertThat(constraintViolation.getMessage()).startsWith("must be a well-formed email address");
		});
	}
}
