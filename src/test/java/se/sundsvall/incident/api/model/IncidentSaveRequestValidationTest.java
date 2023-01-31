package se.sundsvall.incident.api.model;

import org.junit.jupiter.api.Test;
import se.sundsvall.incident.dto.Category;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.incident.TestDataFactory.buildIncidentSaveRequest;

public class IncidentSaveRequestValidationTest extends AbstractValidationTest {

    @Test
    void testValidationWithValidValues() {
        var request = buildIncidentSaveRequest(Category.FELPARKERAD_BIL);

        assertThat(validator.validate(request)).isEmpty();
    }


    @Test
    void testValidationWithInvalidPhoneNumber() {
        var request = buildIncidentSaveRequest(Category.FELPARKERAD_BIL, req -> req.setPhoneNumber("076127121297"));

        var constraints = List.copyOf(validator.validate(request));
        assertThat(constraints).hasSize(1);
        assertThat(constraints.get(0)).satisfies(constraintViolation -> {
            assertThat(constraintViolation.getPropertyPath().toString()).isEqualTo("phoneNumber");
            assertThat(constraintViolation.getMessage()).startsWith("must match");
        });

    }

    @Test
    void testValidationWithInvalidEmail() {
        var request = buildIncidentSaveRequest(Category.FELPARKERAD_BIL, req -> req.setEmail("emailAddress"));

        var constraints = List.copyOf(validator.validate(request));
        assertThat(constraints).hasSize(1);
        assertThat(constraints.get(0)).satisfies(constraintViolation -> {
            assertThat(constraintViolation.getPropertyPath().toString()).isEqualTo("email");
            assertThat(constraintViolation.getMessage()).isEqualTo("must be a well-formed email address");
        });
    }

    @Test
    void testValidationWithInvalidCoordinates() {
        var request = buildIncidentSaveRequest(Category.FELPARKERAD_BIL, req -> req.setMapCoordinates("123123"));

        var constraints = List.copyOf(validator.validate(request));
        assertThat(constraints).hasSize(1);
        assertThat(constraints.get(0)).satisfies(constraintViolation -> {
            assertThat(constraintViolation.getPropertyPath().toString()).isEqualTo("mapCoordinates");
            assertThat(constraintViolation.getMessage()).isEqualTo("must be valid coordinates e.g 62.4097,17.24024");
        });
    }

    @Test
    void testValidationWithInvalidCategory() {
        var request = buildIncidentSaveRequest(Category.FELPARKERAD_BIL, req -> req.setCategory(null));

        var constraints = List.copyOf(validator.validate(request));
        assertThat(constraints).hasSize(1);
        assertThat(constraints.get(0)).satisfies(constraintViolation -> {
            assertThat(constraintViolation.getPropertyPath().toString()).isEqualTo("category");
            assertThat(constraintViolation.getMessage()).startsWith("must not be null");
        });
    }

    @Test
    void testValidationWithInvalidDescription() {
        var request = buildIncidentSaveRequest(Category.FELPARKERAD_BIL, req -> req.setDescription(null));

        var constraints = List.copyOf(validator.validate(request));
        assertThat(constraints).hasSize(1);
        assertThat(constraints.get(0)).satisfies(constraintViolation -> {
            assertThat(constraintViolation.getPropertyPath().toString()).isEqualTo("description");
            assertThat(constraintViolation.getMessage()).startsWith("must not be blank");
        });
    }
}
