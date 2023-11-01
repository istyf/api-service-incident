package se.sundsvall.incident.api.model.validation;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintValidatorContext;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NullOrNotBlankValidatorTest {

	@InjectMocks
	private NullOrNotBlankValidator validator;

	@Mock
	private ConstraintValidatorContext context;

	@Test
	void isValidWithNullInput() {
		var result = validator.isValid(null, context);

		assertThat(result).isTrue();
	}

	@Test
	void isValidWithInvalidInput() {
		var result = validator.isValid("   ", context);

		assertThat(result).isFalse();
	}

	@Test
	void isValidWithValidInput() {
		var result = validator.isValid("any string", context);

		assertThat(result).isTrue();
	}
}
