package se.sundsvall.incident.integration.db.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.incident.TestDataFactory.createCategoryEntity;

import java.lang.reflect.Field;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Test;

class CategoryEntityTest {

	@Test
	void testCategoryIdHasCorrectAnnotationsAndValues() {
		final Field id = FieldUtils.getDeclaredField(CategoryEntity.class, "categoryId", true);
		assertThat(id.getAnnotations()).hasSize(3);
		assertThat(id.getType()).isEqualTo(Integer.class);

		final Column column = id.getDeclaredAnnotation(Column.class);
		final GeneratedValue generatedValue = id.getDeclaredAnnotation(GeneratedValue.class);

		assertThat(column.name()).isEqualTo("category_id");
		assertThat(generatedValue.strategy()).isEqualTo(GenerationType.AUTO);
	}

	@Test
	void testTitleHasCorrectAnnotationsAndValues() {
		final Field title = FieldUtils.getDeclaredField(CategoryEntity.class, "title", true);
		assertThat(title.getAnnotations()).hasSize(1);
		assertThat(title.getType()).isEqualTo(String.class);

		final Column column = title.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("title");
	}

	@Test
	void testLabelHasCorrectAnnotationsAndValues() {
		final Field label = FieldUtils.getDeclaredField(CategoryEntity.class, "label", true);
		assertThat(label.getAnnotations()).hasSize(1);
		assertThat(label.getType()).isEqualTo(String.class);

		final Column column = label.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("label");
	}

	@Test
	void testForwardToHasCorrectAnnotationsAndValues() {
		final Field forwardTo = FieldUtils.getDeclaredField(CategoryEntity.class, "forwardTo", true);
		assertThat(forwardTo.getAnnotations()).hasSize(1);
		assertThat(forwardTo.getType()).isEqualTo(String.class);

		final Column column = forwardTo.getDeclaredAnnotation(Column.class);
		assertThat(column.name()).isEqualTo("forward_to");
	}

	@Test
	void getterTest() {
		var entity = createCategoryEntity();
		var categoryId = entity.getCategoryId();
		var label = entity.getLabel();
		var title = entity.getTitle();
		var forwardTo = entity.getForwardTo();

		assertThat(categoryId).isEqualTo(entity.getCategoryId());
		assertThat(label).isEqualTo(entity.getLabel());
		assertThat(title).isEqualTo(entity.getTitle());
		assertThat(forwardTo).isEqualTo(entity.getForwardTo());
	}

	@Test
	void setterTest() {
		var entity = createCategoryEntity();
		entity.setCategoryId(1337);
		entity.setTitle("new title");
		entity.setLabel("new label");
		entity.setForwardTo("new email");

		assertThat(entity.getCategoryId()).isEqualTo(1337);
		assertThat(entity.getTitle()).isEqualTo("new title");
		assertThat(entity.getLabel()).isEqualTo("new label");
		assertThat(entity.getForwardTo()).isEqualTo("new email");
	}

}
