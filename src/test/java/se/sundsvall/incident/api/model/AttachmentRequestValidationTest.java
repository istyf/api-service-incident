package se.sundsvall.incident.api.model;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.incident.TestDataFactory.createAttachmentRequest;

import java.util.List;

import org.junit.jupiter.api.Test;

import se.sundsvall.incident.TestDataFactory;

class AttachmentRequestValidationTest extends AbstractValidationTest {

	@Test
	void testValidationWithValidValues() {
		final var request = createAttachmentRequest();

		assertThat(validator.validate(request)).isEmpty();
	}

	@Test
	void testValidationWithInvalidCategory() {
		final var request = TestDataFactory.createAttachmentRequest(req -> req.setCategory(null));

		final var constraints = List.copyOf(validator.validate(request));
		assertThat(constraints).hasSize(1);
		assertThat(constraints.get(0)).satisfies(constraintViolation -> {
			assertThat(constraintViolation.getPropertyPath()).hasToString("category");
			assertThat(constraintViolation.getMessage()).isEqualTo("must not be blank");
		});
	}

	@Test
	void testValidationWithInvalidExtension() {
		final var request = TestDataFactory.createAttachmentRequest(req -> req.setExtension(null));

		final var constraints = List.copyOf(validator.validate(request));
		assertThat(constraints).hasSize(1);
		assertThat(constraints.get(0)).satisfies(constraintViolation -> {
			assertThat(constraintViolation.getPropertyPath()).hasToString("extension");
			assertThat(constraintViolation.getMessage()).isEqualTo("must not be blank");
		});
	}

	@Test
	void testValidationWithInvalidMimeType() {
		final var request = TestDataFactory.createAttachmentRequest(req -> req.setMimeType(null));

		final var constraints = List.copyOf(validator.validate(request));
		assertThat(constraints).hasSize(1);
		assertThat(constraints.get(0)).satisfies(constraintViolation -> {
			assertThat(constraintViolation.getPropertyPath()).hasToString("mimeType");
			assertThat(constraintViolation.getMessage()).isEqualTo("must not be blank");
		});
	}

	@Test
	void testValidationWithInvalidFile() {
		final var request = TestDataFactory.createAttachmentRequest(req -> req.setFile(null));

		final var constraints = List.copyOf(validator.validate(request));
		assertThat(constraints).hasSize(1);
		assertThat(constraints.get(0)).satisfies(constraintViolation -> {
			assertThat(constraintViolation.getPropertyPath()).hasToString("file");
			assertThat(constraintViolation.getMessage()).isEqualTo("must not be blank");
		});
	}
}
