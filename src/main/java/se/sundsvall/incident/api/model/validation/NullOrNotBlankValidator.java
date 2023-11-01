package se.sundsvall.incident.api.model.validation;

import static java.util.Objects.isNull;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NullOrNotBlankValidator implements ConstraintValidator<NullOrNotBlank, String> {

	private static final String REGEX_PATTERN = "^(?=\\s*\\S).*$";

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (isNull(value)) {
			return true;
		}

		return value.matches(REGEX_PATTERN);
	}
}
