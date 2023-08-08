package se.sundsvall.incident.api.model;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.incident.TestDataFactory.PERSONID;
import static se.sundsvall.incident.TestDataFactory.buildIncidentSaveRequest;

import org.junit.jupiter.api.Test;

import se.sundsvall.incident.dto.Category;

class IncidentSaveRequestTest {

	@Test
	void testBuildersAndGetters() {
		final var request = buildIncidentSaveRequest(Category.FELPARKERAD_BIL);

		assertThat(request.getPersonId()).isEqualTo(PERSONID);
		assertThat(request.getPhoneNumber()).isEqualTo("0701234567");
		assertThat(request.getEmail()).isEqualTo("mail@mail.se");
		assertThat(request.getContactMethod()).isEqualTo("email");
		assertThat(request.getCategory()).isEqualTo(Category.FELPARKERAD_BIL.getValue());
		assertThat(request.getDescription()).isEqualTo("Ärendebeskrivning");
		assertThat(request.getMapCoordinates()).isEqualTo("62.23162,17.27403");
		assertThat(request.getExternalCaseId()).isEqualTo("123");
		assertThat(request.getAttachments()).hasSize(1);

	}

	@Test
	void emptyConstructor() {
		final IncidentSaveRequest test = new IncidentSaveRequest();
		assertThat(test).isNotNull();
	}
}
