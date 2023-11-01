package se.sundsvall.incident.integration.db.entity;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;

import java.lang.reflect.Field;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.hamcrest.MatcherAssert;
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
		assertThat(generatedValue.strategy()).isEqualTo(GenerationType.IDENTITY);
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
	void testBean() {
		MatcherAssert.assertThat(CategoryEntity.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters()));
	}

}
