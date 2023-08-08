package se.sundsvall.incident.api.model.validation;

import static java.util.Objects.isNull;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidCoordsValidator implements ConstraintValidator<ValidCoords, String> {
	private static final String REGEX_PATTERN = "^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$";

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (isNull(value)) {
			return true;
		}

		return value.matches(REGEX_PATTERN);
	}
}
