package se.sundsvall.incident.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static se.sundsvall.incident.TestDataFactory.createCategoryEntity;
import static se.sundsvall.incident.TestDataFactory.createCategoryPatch;
import static se.sundsvall.incident.TestDataFactory.createCategoryPost;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.zalando.problem.Problem;

import se.sundsvall.incident.api.model.Category;
import se.sundsvall.incident.api.model.ValidCategoryResponse;
import se.sundsvall.incident.api.model.ValidOepCategoryResponse;
import se.sundsvall.incident.integration.db.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

	@Mock
	private CategoryRepository mockCategoryRepository;

	@InjectMocks
	private CategoryService categoryService;

	@Test
	void fetchCategoryByIdTest() {
		when(mockCategoryRepository.findById(any(Integer.class)))
			.thenReturn(Optional.of(createCategoryEntity()));

		var result = categoryService.fetchCategoryById(5);

		assertThat(result).isInstanceOf(Category.class).isNotNull();

		verify(mockCategoryRepository).findById(5);
		verifyNoMoreInteractions(mockCategoryRepository);
	}

	@Test
	void fetchCategoryByIdNotFoundTest() {
		when(mockCategoryRepository.findById(any(Integer.class)))
			.thenReturn(Optional.empty());

		assertThatThrownBy(() -> categoryService.fetchCategoryById(any(Integer.class)))
			.isInstanceOf(Problem.class)
			.hasMessageContaining("Not Found: Category");

		verify(mockCategoryRepository).findById(anyInt());
		verifyNoMoreInteractions(mockCategoryRepository);
	}

	@Test
	void fetchAllCategoryWhenFound() {
		when(mockCategoryRepository.findAll()).thenReturn(List.of(createCategoryEntity(), createCategoryEntity()));

		final var categories = categoryService.fetchAllCategories();

		assertThat(categories).isNotNull().isNotEmpty().hasSize(2);

		verify(mockCategoryRepository).findAll();
		verifyNoMoreInteractions(mockCategoryRepository);
	}

	@Test
	void fetchAllCategoriesWhenEmpty() {
		when(mockCategoryRepository.findAll()).thenReturn(List.of());

		final var categories = categoryService.fetchAllCategories();

		assertThat(categories).isNotNull().isEmpty();

		verify(mockCategoryRepository).findAll();
		verifyNoMoreInteractions(mockCategoryRepository);
	}

	@Test
	void createCategoryTest() {
		final var entity = createCategoryEntity();
		when(mockCategoryRepository.save(any())).thenReturn(entity);

		final var category = categoryService.createCategory(createCategoryPost());

		assertThat(category.getCategoryId()).isEqualTo(entity.getCategoryId());
		assertThat(category.getForwardTo()).isEqualTo(entity.getForwardTo());
		assertThat(category.getTitle()).isEqualTo(entity.getTitle());

		verify(mockCategoryRepository).save(any());
		verifyNoMoreInteractions(mockCategoryRepository);
	}

	@Test
	void deleteCategoryByIdTest() {
		when(mockCategoryRepository.existsById(5)).thenReturn(true);

		categoryService.deleteCategoryById(5);
		verify(mockCategoryRepository).existsById(5);
		verify(mockCategoryRepository).deleteById(5);
	}

	@Test
	void deleteCategoryByIdNotFoundTest() {
		when(mockCategoryRepository.existsById(anyInt())).thenReturn(false);

		assertThatThrownBy(() -> categoryService.deleteCategoryById(anyInt()))
			.isInstanceOf(Problem.class)
			.hasMessageContaining("Not Found: Category");

		verify(mockCategoryRepository, never()).deleteById(any());
	}

	@Test
	void patchEntityTest() {
		var entity = createCategoryEntity();
		when(mockCategoryRepository.findById(anyInt())).thenReturn(Optional.ofNullable(entity));
		when(mockCategoryRepository.save(any())).thenReturn(entity);

		var result = categoryService.patchCategory(5, createCategoryPatch());

		assertThat(result).isInstanceOf(Category.class);
		verify(mockCategoryRepository).findById(5);
		verify(mockCategoryRepository).save(any());
	}

	@Test
	void patchEntityNotFoundTest() {
		var patch = createCategoryPatch();
		when(mockCategoryRepository.findById(anyInt())).thenReturn(Optional.empty());

		assertThatThrownBy(() -> categoryService.patchCategory(anyInt(), patch))
			.isInstanceOf(Problem.class)
			.hasMessageContaining("Not Found: Category");

		verify(mockCategoryRepository).findById(anyInt());
		verify(mockCategoryRepository, never()).save(any());
	}

	@Test
	void fetchValidCategoriesTest() {
		when(mockCategoryRepository.findAll()).thenReturn(List.of(createCategoryEntity(), createCategoryEntity()));

		var result = categoryService.fetchValidCategories();

		assertThat(result).hasSize(2);
		assertThat(result.get(0)).isInstanceOf(ValidCategoryResponse.class);
		verify(mockCategoryRepository).findAll();
		verifyNoMoreInteractions(mockCategoryRepository);
	}

	@Test
	void fetchValidOepCategoriesTest() {
		when(mockCategoryRepository.findAll()).thenReturn(List.of(createCategoryEntity(), createCategoryEntity()));

		var result = categoryService.fetchValidOepCategories();

		assertThat(result).hasSize(2);
		assertThat(result.get(0)).isInstanceOf(ValidOepCategoryResponse.class);
		verify(mockCategoryRepository).findAll();
		verifyNoMoreInteractions(mockCategoryRepository);
	}

}
