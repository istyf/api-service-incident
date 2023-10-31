package se.sundsvall.incident.integration.db.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.incident.TestDataFactory.buildIncidentEntity;

import java.lang.reflect.Field;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Test;

import se.sundsvall.incident.dto.Category;
import se.sundsvall.incident.dto.Status;

class IncidentEntityTest {

	@Test
	void testIncidentIdHasCorrectAnnotationsAndValues() {
		final Field incidentId = FieldUtils.getDeclaredField(IncidentEntity.class, "incidentId", true);
		assertThat(incidentId.getAnnotations()).hasSize(2);
		assertThat(incidentId.getType()).isEqualTo(String.class);

		final Column column = incidentId.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("IncidentId");
	}

	@Test
	void testExternalCaseIdHasCorrectAnnotationsAndValues() {
		final Field externalCaseId = FieldUtils.getDeclaredField(IncidentEntity.class, "externalCaseId", true);
		assertThat(externalCaseId.getAnnotations()).hasSize(1);
		assertThat(externalCaseId.getType()).isEqualTo(String.class);

		final Column column = externalCaseId.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("externalCaseId");
	}

	@Test
	void testPersonIdHasCorrectAnnotationsAndValues() {
		final Field personID = FieldUtils.getDeclaredField(IncidentEntity.class, "personID", true);
		assertThat(personID.getAnnotations()).hasSize(1);
		assertThat(personID.getType()).isEqualTo(String.class);

		final Column column = personID.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("PersonId");
	}

	@Test
	void testCreatedHasCorrectAnnotationsAndValues() {
		final Field created = FieldUtils.getDeclaredField(IncidentEntity.class, "created", true);
		assertThat(created.getAnnotations()).hasSize(1);
		assertThat(created.getType()).isEqualTo(String.class);

		final Column column = created.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("Created");
	}

	@Test
	void testPhoneNumberHasCorrectAnnotationsAndValues() {
		final Field phoneNumber = FieldUtils.getDeclaredField(IncidentEntity.class, "phoneNumber", true);
		assertThat(phoneNumber.getAnnotations()).hasSize(1);
		assertThat(phoneNumber.getType()).isEqualTo(String.class);

		final Column column = phoneNumber.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("PhoneNumber");
	}

	@Test
	void testEmailHasCorrectAnnotationsAndValues() {
		final Field email = FieldUtils.getDeclaredField(IncidentEntity.class, "email", true);
		assertThat(email.getAnnotations()).hasSize(1);
		assertThat(email.getType()).isEqualTo(String.class);

		final Column column = email.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("Email");
	}

	@Test
	void testContactMethodHasCorrectAnnotationsAndValues() {
		final Field contactMethod = FieldUtils.getDeclaredField(IncidentEntity.class, "contactMethod", true);
		assertThat(contactMethod.getAnnotations()).hasSize(1);
		assertThat(contactMethod.getType()).isEqualTo(String.class);

		final Column column = contactMethod.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("ContactMethod");
	}

	@Test
	void testUpdatedHasCorrectAnnotationsAndValues() {
		final Field updated = FieldUtils.getDeclaredField(IncidentEntity.class, "updated", true);
		assertThat(updated.getAnnotations()).hasSize(1);
		assertThat(updated.getType()).isEqualTo(String.class);

		final Column column = updated.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("Updated");
	}

	@Test
	void testDescriptionHasCorrectAnnotationsAndValues() {
		final Field description = FieldUtils.getDeclaredField(IncidentEntity.class, "description", true);
		assertThat(description.getAnnotations()).hasSize(1);
		assertThat(description.getType()).isEqualTo(String.class);

		final Column column = description.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("Description");
	}

	@Test
	void testMapCoordinatesHasCorrectAnnotationsAndValues() {
		final Field mapCoordinates = FieldUtils.getDeclaredField(IncidentEntity.class, "mapCoordinates", true);
		assertThat(mapCoordinates.getAnnotations()).hasSize(1);
		assertThat(mapCoordinates.getType()).isEqualTo(String.class);

		final Column column = mapCoordinates.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("MapCoordinates");
	}

	@Test
	void testFeedbackHasCorrectAnnotationsAndValues() {
		final Field feedback = FieldUtils.getDeclaredField(IncidentEntity.class, "feedback", true);
		assertThat(feedback.getAnnotations()).hasSize(1);
		assertThat(feedback.getType()).isEqualTo(String.class);

		final Column column = feedback.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("Feedback");
	}

	@Test
	void testStatusHasCorrectAnnotationsAndValues() {
		final Field status = FieldUtils.getDeclaredField(IncidentEntity.class, "status", true);
		assertThat(status.getAnnotations()).hasSize(2);
		assertThat(status.getType()).isEqualTo(Status.class);

		final Enumerated enumerated = status.getDeclaredAnnotation(Enumerated.class);
		final Column column = status.getDeclaredAnnotation(Column.class);
		assertThat(enumerated.value()).isEqualTo(EnumType.STRING);
		assertThat(column.name()).isEqualTo("Status");
	}

	@Test
	void testCategoryHasCorrectAnnotationsAndValues() {
		final Field category = FieldUtils.getDeclaredField(IncidentEntity.class, "category", true);
		assertThat(category.getAnnotations()).hasSize(2);
		assertThat(category.getType()).isEqualTo(Category.class);

		final Enumerated enumerated = category.getDeclaredAnnotation(Enumerated.class);
		final Column column = category.getDeclaredAnnotation(Column.class);
		assertThat(enumerated.value()).isEqualTo(EnumType.STRING);
		assertThat(column.name()).isEqualTo("Category");
	}


	@Test
	void getterTest() {
		var entity = buildIncidentEntity(Category.OVRIGT);
		var incidentId = entity.getIncidentId();
		var externalCaseId = entity.getExternalCaseId();
		var personID = entity.getPersonID();
		var created = entity.getCreated();
		var phoneNumber = entity.getPhoneNumber();
		var email = entity.getEmail();
		var contactMethod = entity.getContactMethod();
		var updated = entity.getUpdated();
		var description = entity.getDescription();
		var mapCoordinates = entity.getMapCoordinates();
		var feedback = entity.getFeedback();
		var category = entity.getCategory();
		var status = entity.getStatus();

		assertThat(incidentId).isEqualTo(entity.getIncidentId());
		assertThat(externalCaseId).isEqualTo(entity.getExternalCaseId());
		assertThat(personID).isEqualTo(entity.getPersonID());
		assertThat(created).isEqualTo(entity.getCreated());
		assertThat(phoneNumber).isEqualTo(entity.getPhoneNumber());
		assertThat(email).isEqualTo(entity.getEmail());
		assertThat(contactMethod).isEqualTo(entity.getContactMethod());
		assertThat(updated).isEqualTo(entity.getUpdated());
		assertThat(description).isEqualTo(entity.getDescription());
		assertThat(mapCoordinates).isEqualTo(entity.getMapCoordinates());
		assertThat(feedback).isEqualTo(entity.getFeedback());
		assertThat(category).isEqualTo(entity.getCategory());
		assertThat(status).isEqualTo(entity.getStatus());
	}

	@Test
	void setterTest() {
		var incident = new IncidentEntity();
		incident.setIncidentId("incidentId");
		incident.setExternalCaseId("externalCaseId");
		incident.setPersonID("personId");
		incident.setCreated("created");
		incident.setPhoneNumber("phoneNumber");
		incident.setEmail("email");
		incident.setContactMethod("contactMethod");
		incident.setUpdated("updated");
		incident.setDescription("description");
		incident.setMapCoordinates("mapCoordinates");
		incident.setFeedback("feedback");
		incident.setStatus(Status.SPARAT);
		incident.setCategory(Category.OVRIGT);


		assertThat(incident.getIncidentId()).isEqualTo("incidentId");
		assertThat(incident.getExternalCaseId()).isEqualTo("externalCaseId");
		assertThat(incident.getPersonID()).isEqualTo("personId");
		assertThat(incident.getCreated()).isEqualTo("created");
		assertThat(incident.getPhoneNumber()).isEqualTo("phoneNumber");
		assertThat(incident.getEmail()).isEqualTo("email");
		assertThat(incident.getContactMethod()).isEqualTo("contactMethod");
		assertThat(incident.getUpdated()).isEqualTo("updated");
		assertThat(incident.getDescription()).isEqualTo("description");
		assertThat(incident.getMapCoordinates()).isEqualTo("mapCoordinates");
		assertThat(incident.getFeedback()).isEqualTo("feedback");
		assertThat(incident.getStatus()).isEqualTo(Status.SPARAT);
		assertThat(incident.getCategory()).isEqualTo(Category.OVRIGT);
	}

}
