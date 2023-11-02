package se.sundsvall.incident.integration.db.entity;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static com.google.code.beanmatchers.BeanMatchers.registerValueGenerator;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.systemDefault;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.within;
import static org.hamcrest.CoreMatchers.allOf;
import static se.sundsvall.incident.TestDataFactory.buildIncidentEntity;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Random;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import se.sundsvall.incident.dto.Category;
import se.sundsvall.incident.dto.Status;

class IncidentEntityTest {

	@BeforeAll
	static void setup() {
		registerValueGenerator(() -> now().plusDays(new Random().nextInt()), LocalDateTime.class);
	}

	@Test
	void testIncidentIdHasCorrectAnnotationsAndValues() {
		final Field incidentId = FieldUtils.getDeclaredField(IncidentEntity.class, "incidentId", true);
		assertThat(incidentId.getAnnotations()).hasSize(3);
		assertThat(incidentId.getType()).isEqualTo(String.class);

		final GeneratedValue generatedValue = incidentId.getDeclaredAnnotation(GeneratedValue.class);
		final Column column = incidentId.getDeclaredAnnotation(Column.class);
		assertThat(generatedValue.strategy()).isEqualTo(GenerationType.UUID);
		assertThat(column.name()).isEqualTo("incident_id");
	}

	@Test
	void testExternalCaseIdHasCorrectAnnotationsAndValues() {
		final Field externalCaseId = FieldUtils.getDeclaredField(IncidentEntity.class, "externalCaseId", true);
		assertThat(externalCaseId.getAnnotations()).hasSize(1);
		assertThat(externalCaseId.getType()).isEqualTo(String.class);

		final Column column = externalCaseId.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("external_case_id");
	}

	@Test
	void testPersonIdHasCorrectAnnotationsAndValues() {
		final Field personID = FieldUtils.getDeclaredField(IncidentEntity.class, "personId", true);
		assertThat(personID.getAnnotations()).hasSize(1);
		assertThat(personID.getType()).isEqualTo(String.class);

		final Column column = personID.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("person_id");
	}

	@Test
	void testCreatedHasCorrectAnnotationsAndValues() {
		final Field created = FieldUtils.getDeclaredField(IncidentEntity.class, "created", true);
		assertThat(created.getAnnotations()).hasSize(1);
		assertThat(created.getType()).isEqualTo(LocalDateTime.class);

		final Column column = created.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("created");
	}

	@Test
	void testPhoneNumberHasCorrectAnnotationsAndValues() {
		final Field phoneNumber = FieldUtils.getDeclaredField(IncidentEntity.class, "phoneNumber", true);
		assertThat(phoneNumber.getAnnotations()).hasSize(1);
		assertThat(phoneNumber.getType()).isEqualTo(String.class);

		final Column column = phoneNumber.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("phone_number");
	}

	@Test
	void testEmailHasCorrectAnnotationsAndValues() {
		final Field email = FieldUtils.getDeclaredField(IncidentEntity.class, "email", true);
		assertThat(email.getAnnotations()).hasSize(1);
		assertThat(email.getType()).isEqualTo(String.class);

		final Column column = email.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("email");
	}

	@Test
	void testContactMethodHasCorrectAnnotationsAndValues() {
		final Field contactMethod = FieldUtils.getDeclaredField(IncidentEntity.class, "contactMethod", true);
		assertThat(contactMethod.getAnnotations()).hasSize(1);
		assertThat(contactMethod.getType()).isEqualTo(String.class);

		final Column column = contactMethod.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("contact_method");
	}

	@Test
	void testUpdatedHasCorrectAnnotationsAndValues() {
		final Field updated = FieldUtils.getDeclaredField(IncidentEntity.class, "updated", true);
		assertThat(updated.getAnnotations()).hasSize(1);
		assertThat(updated.getType()).isEqualTo(LocalDateTime.class);

		final Column column = updated.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("updated");
	}

	@Test
	void testDescriptionHasCorrectAnnotationsAndValues() {
		final Field description = FieldUtils.getDeclaredField(IncidentEntity.class, "description", true);
		assertThat(description.getAnnotations()).hasSize(1);
		assertThat(description.getType()).isEqualTo(String.class);

		final Column column = description.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("description");
	}

	@Test
	void testMapCoordinatesHasCorrectAnnotationsAndValues() {
		final Field mapCoordinates = FieldUtils.getDeclaredField(IncidentEntity.class, "coordinates", true);
		assertThat(mapCoordinates.getAnnotations()).hasSize(1);
		assertThat(mapCoordinates.getType()).isEqualTo(String.class);

		final Column column = mapCoordinates.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("coordinates");
	}

	@Test
	void testFeedbackHasCorrectAnnotationsAndValues() {
		final Field feedback = FieldUtils.getDeclaredField(IncidentEntity.class, "feedback", true);
		assertThat(feedback.getAnnotations()).hasSize(1);
		assertThat(feedback.getType()).isEqualTo(String.class);

		final Column column = feedback.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("feedback");
	}

	@Test
	void testStatusHasCorrectAnnotationsAndValues() {
		final Field status = FieldUtils.getDeclaredField(IncidentEntity.class, "status", true);
		assertThat(status.getAnnotations()).hasSize(2);
		assertThat(status.getType()).isEqualTo(Status.class);

		final Enumerated enumerated = status.getDeclaredAnnotation(Enumerated.class);
		final Column column = status.getDeclaredAnnotation(Column.class);
		assertThat(enumerated.value()).isEqualTo(EnumType.STRING);
		assertThat(column.name()).isEqualTo("status");
	}

	@Test
	void testCategoryHasCorrectAnnotationsAndValues() {
		final Field category = FieldUtils.getDeclaredField(IncidentEntity.class, "category", true);
		assertThat(category.getAnnotations()).hasSize(2);
		assertThat(category.getType()).isEqualTo(Category.class);

		final Enumerated enumerated = category.getDeclaredAnnotation(Enumerated.class);
		final Column column = category.getDeclaredAnnotation(Column.class);
		assertThat(enumerated.value()).isEqualTo(EnumType.STRING);
		assertThat(column.name()).isEqualTo("category");
	}

	@Test
	void testBean() {
		MatcherAssert.assertThat(IncidentEntity.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters()));
	}

	@Test
	void preUpdateTest() {
		var incident = buildIncidentEntity(Category.VATTENMATARE);
		incident.preUpdate();

		assertThat(incident.getUpdated()).isNotNull();
		assertThat(incident.getUpdated()).isCloseTo(now(systemDefault()), within(2, SECONDS));
	}
	
}
