package se.sundsvall.incident.api.model.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidCoordsValidator.class)
public @interface ValidCoords {


    String message() default "must be valid coordinates e.g 62.4097,17.24024";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
