package se.sundsvall.incident.api.model;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.incident.TestDataFactory.createAttachmentRequest;

import org.junit.jupiter.api.Test;

class AttachmentRequestTest {

	@Test
	void testBuildersAndGetters() {
		var request = createAttachmentRequest();
		assertThat(request.getCategory()).isEqualTo("ADRESS");
		assertThat(request.getExtension()).isEqualTo(".txt");
		assertThat(request.getMimeType()).isEqualTo("text/plain");
		assertThat(request.getNote()).isEqualTo("Some note");
		assertThat(request.getFile()).isEqualTo("Zmlsc29tIGJhc2U2NA==");
	}

	@Test
	void emptyConstructor() {
		AttachmentRequest test = new AttachmentRequest();
		assertThat(test).isNotNull();
	}

}