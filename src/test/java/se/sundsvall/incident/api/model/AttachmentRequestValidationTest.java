package se.sundsvall.incident.api.model;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.incident.TestDataFactory.buildAttachmentRequest;

public class AttachmentRequestValidationTest extends AbstractValidationTest {

    @Test
    void testValidationWithValidValues() {
        var request = buildAttachmentRequest();

        assertThat(validator.validate(request)).isEmpty();
    }


    @Test
    void testValidationWithInvalidCategory() {
        var request = buildAttachmentRequest(req -> req.setCategory(null));

        var constraints = List.copyOf(validator.validate(request));
        assertThat(constraints).hasSize(1);
        assertThat(constraints.get(0)).satisfies(constraintViolation -> {
            assertThat(constraintViolation.getPropertyPath().toString()).isEqualTo("category");
            assertThat(constraintViolation.getMessage()).isEqualTo("must not be blank");
        });
    }

    @Test
    void testValidationWithInvalidExtension() {
        var request = buildAttachmentRequest(req -> req.setExtension(null));

        var constraints = List.copyOf(validator.validate(request));
        assertThat(constraints).hasSize(1);
        assertThat(constraints.get(0)).satisfies(constraintViolation -> {
            assertThat(constraintViolation.getPropertyPath().toString()).isEqualTo("extension");
            assertThat(constraintViolation.getMessage()).isEqualTo("must not be blank");
        });
    }

    @Test
    void testValidationWithInvalidMimeType() {
        var request = buildAttachmentRequest(req -> req.setMimeType(null));

        var constraints = List.copyOf(validator.validate(request));
        assertThat(constraints).hasSize(1);
        assertThat(constraints.get(0)).satisfies(constraintViolation -> {
            assertThat(constraintViolation.getPropertyPath().toString()).isEqualTo("mimeType");
            assertThat(constraintViolation.getMessage()).isEqualTo("must not be blank");
        });
    }

    @Test
    void testValidationWithInvalidFile() {
        var request = buildAttachmentRequest(req -> req.setFile(null));

        var constraints = List.copyOf(validator.validate(request));
        assertThat(constraints).hasSize(1);
        assertThat(constraints.get(0)).satisfies(constraintViolation -> {
            assertThat(constraintViolation.getPropertyPath().toString()).isEqualTo("file");
            assertThat(constraintViolation.getMessage()).isEqualTo("must not be blank");
        });
    }
}
