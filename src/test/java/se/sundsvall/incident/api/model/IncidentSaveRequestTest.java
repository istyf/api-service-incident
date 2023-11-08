package se.sundsvall.incident.api.model;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.incident.TestDataFactory.PERSON_ID;
import static se.sundsvall.incident.TestDataFactory.createIncidentSaveRequest;

import java.util.List;

import org.junit.jupiter.api.Test;

class IncidentSaveRequestTest {

	@Test
	void testBuildersAndGetters() {
		final var request = createIncidentSaveRequest();

		assertThat(request.getPersonId()).isEqualTo(PERSON_ID);
		assertThat(request.getPhoneNumber()).isEqualTo("0701234567");
		assertThat(request.getEmail()).isEqualTo("mail@mail.se");
		assertThat(request.getContactMethod()).isEqualTo("email");
		assertThat(request.getCategory()).isEqualTo(3);
		assertThat(request.getDescription()).isEqualTo("Ärendebeskrivning");
		assertThat(request.getMapCoordinates()).isEqualTo("62.23162,17.27403");
		assertThat(request.getExternalCaseId()).isEqualTo("123");
		assertThat(request.getAttachments()).hasSize(1);
	}

	@Test
	void allArgsConstructor() {
		var request = new IncidentSaveRequest("personId", "phoneNumber", "email", "contactMethod",
			5, "description", "mapCoordinates", "externalCaseId", List.of());

		assertThat(request).isNotNull();
	}

	@Test
	void emptyConstructor() {
		final IncidentSaveRequest test = new IncidentSaveRequest();
		assertThat(test).isNotNull();
	}
}
