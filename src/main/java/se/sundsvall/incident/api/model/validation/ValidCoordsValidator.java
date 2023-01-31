package se.sundsvall.incident.api.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import static java.util.Objects.isNull;

public class ValidCoordsValidator implements ConstraintValidator<ValidCoords, String> {
    private static final String REGEX_PATTERN = "^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$";

    public ValidCoordsValidator() {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (isNull(value)) {
            return true;
        }

        return value.matches(REGEX_PATTERN);
    }

}
