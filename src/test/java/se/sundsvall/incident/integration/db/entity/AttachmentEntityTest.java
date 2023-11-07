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
import static se.sundsvall.incident.TestDataFactory.createAttachmentEntity;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Random;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AttachmentEntityTest {

	@BeforeAll
	static void setup() {
		registerValueGenerator(() -> now().plusDays(new Random().nextInt()), LocalDateTime.class);
	}

	@Test
	void testIdHasCorrectAnnotationsAndValues() {
		final Field id = FieldUtils.getDeclaredField(AttachmentEntity.class, "id", true);
		assertThat(id.getAnnotations()).hasSize(3);
		assertThat(id.getType()).isEqualTo(Integer.class);

		final Column column = id.getDeclaredAnnotation(Column.class);
		final GeneratedValue generatedValue = id.getDeclaredAnnotation(GeneratedValue.class);

		assertThat(column.name()).isEqualTo("id");
		assertThat(generatedValue.strategy()).isEqualTo(GenerationType.IDENTITY);
	}

	@Test
	void testCategoryHasCorrectAnnotationsAndValues() {
		final Field category = FieldUtils.getDeclaredField(AttachmentEntity.class, "category", true);
		assertThat(category.getAnnotations()).hasSize(1);
		assertThat(category.getType()).isEqualTo(String.class);

		final Column column = category.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("category");
	}

	@Test
	void testExtensionHasCorrectAnnotationsAndValues() {
		final Field extension = FieldUtils.getDeclaredField(AttachmentEntity.class, "extension", true);
		assertThat(extension.getAnnotations()).hasSize(1);
		assertThat(extension.getType()).isEqualTo(String.class);

		final Column column = extension.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("extension");
	}

	@Test
	void testMimeTypeHasCorrectAnnotationsAndValues() {
		final Field mimeType = FieldUtils.getDeclaredField(AttachmentEntity.class, "mimeType", true);
		assertThat(mimeType.getAnnotations()).hasSize(1);
		assertThat(mimeType.getType()).isEqualTo(String.class);

		final Column column = mimeType.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("mime_type");
	}

	@Test
	void testNoteHasCorrectAnnotationsAndValues() {
		final Field note = FieldUtils.getDeclaredField(AttachmentEntity.class, "note", true);
		assertThat(note.getAnnotations()).hasSize(1);
		assertThat(note.getType()).isEqualTo(String.class);

		final Column column = note.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("note");
	}

	@Test
	void testFileHasCorrectAnnotationsAndValues() {
		final Field file = FieldUtils.getDeclaredField(AttachmentEntity.class, "file", true);
		assertThat(file.getAnnotations()).hasSize(1);
		assertThat(file.getType()).isEqualTo(String.class);

		final Column column = file.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("file");
	}

	@Test
	void testNameHasCorrectAnnotationsAndValues() {
		final Field name = FieldUtils.getDeclaredField(AttachmentEntity.class, "name", true);
		assertThat(name.getAnnotations()).hasSize(1);
		assertThat(name.getType()).isEqualTo(String.class);

		final Column column = name.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("name");
	}

	@Test
	void testCreatedHasCorrectAnnotationsAndValues() {
		final Field created = FieldUtils.getDeclaredField(AttachmentEntity.class, "created", true);
		assertThat(created.getAnnotations()).hasSize(1);
		assertThat(created.getType()).isEqualTo(LocalDateTime.class);

		final Column column = created.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("created");
	}

	@Test
	void testBean() {
		MatcherAssert.assertThat(AttachmentEntity.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters()));
	}

	@Test
	void prePersistTest() {
		var attachment = createAttachmentEntity();
		attachment.prePersist();

		assertThat(attachment.getCreated()).isNotNull();
		assertThat(attachment.getCreated()).isCloseTo(now(systemDefault()), within(2, SECONDS));
	}
}
