package se.sundsvall.incident.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static se.sundsvall.incident.TestDataFactory.createCategoryEntity;
import static se.sundsvall.incident.TestDataFactory.createCategoryPatch;
import static se.sundsvall.incident.TestDataFactory.createCategoryPost;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.zalando.problem.Problem;

import se.sundsvall.incident.api.model.CategoryDTO;
import se.sundsvall.incident.api.model.CategoryPost;
import se.sundsvall.incident.integration.db.CategoryRepository;
import se.sundsvall.incident.integration.db.entity.CategoryEntity;
import se.sundsvall.incident.service.mapper.Mapper;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private Mapper mockMapper;

	@Mock
	private CategoryRepository mockCategoryRepository;

	@InjectMocks
	private CategoryService categoryService;

	@Test
	void fetchCategoryByIdTest() {
		when(mockCategoryRepository.findById(any(Integer.class)))
			.thenReturn(Optional.ofNullable(createCategoryEntity()));

		var result = categoryService.fetchCategoryById(5);
		
		assertThat(result).isInstanceOf(CategoryDTO.class).isNotNull();

		verify(mockCategoryRepository, times(1)).findById(any());
		verify(mockMapper, times(1)).toCategoryDto(any());
		verifyNoMoreInteractions(mockCategoryRepository, mockMapper);
	}

	@Test
	void fetchCategoryByIdWhenNotFoundTest() {
		final var id = 5;
		when(mockCategoryRepository.findById(any(Integer.class)))
			.thenReturn(Optional.empty());

		assertThatThrownBy(() -> categoryService.fetchCategoryById(id))
			.hasMessageContaining("Category with id: " + id + " not found")
			.isInstanceOf(Problem.class);

		verify(mockCategoryRepository, times(1)).findById(any());
		verifyNoMoreInteractions(mockCategoryRepository);
		verifyNoInteractions(mockMapper);
	}

	@Test
	void fetchAllCategoryWhenFound() {
		when(mockCategoryRepository.findAll()).thenReturn(List.of(createCategoryEntity(), createCategoryEntity()));

		final var categories = categoryService.fetchAllCategories();

		assertThat(categories).isNotNull().isNotEmpty().hasSize(2);

		verify(mockCategoryRepository, times(1)).findAll();
		verify(mockMapper, times(2)).toCategoryDto(any(CategoryEntity.class));
		verifyNoMoreInteractions(mockCategoryRepository, mockMapper);
	}

	@Test
	void fetchAllCategoriesWhenEmpty() {
		when(mockCategoryRepository.findAll()).thenReturn(List.of());

		final var categories = categoryService.fetchAllCategories();

		assertThat(categories).isNotNull().isEmpty();

		verify(mockCategoryRepository, times(1)).findAll();
		verifyNoMoreInteractions(mockCategoryRepository);
		verifyNoInteractions(mockMapper);
	}

	@Test
	void createCategoryTest() {
		final var entity = createCategoryEntity();
		when(mockCategoryRepository.save(any())).thenReturn(entity);

		final var category = categoryService.createCategory(createCategoryPost());

		assertThat(category.getCategoryId()).isEqualTo(entity.getCategoryId());
		assertThat(category.getForwardTo()).isEqualTo(entity.getForwardTo());
		assertThat(category.getTitle()).isEqualTo(entity.getTitle());

		verify(mockCategoryRepository, times(1)).save(any());
		verify(mockMapper, times(1)).toCategoryDto(any(CategoryEntity.class));
		verify(mockMapper, times(1)).toCategoryEntity(any(CategoryPost.class));
		verifyNoMoreInteractions(mockCategoryRepository, mockMapper);
	}

	@Test
	void deleteCategoryByIdWhenFoundTest() {
		when(mockCategoryRepository.existsById(any(Integer.class))).thenReturn(true);

		categoryService.deleteCategoryById(5);

		verify(mockCategoryRepository, times(1)).existsById(any());
		verify(mockCategoryRepository, times(1)).deleteById(any());
		verifyNoInteractions(mockMapper);
	}

	@Test
	void deleteCategoryByIdWhenNotFoundTest() {
		final var id = 5;
		when(mockCategoryRepository.existsById(any(Integer.class))).thenReturn(false);

		assertThatThrownBy(() -> categoryService.deleteCategoryById(id))
			.hasMessageContaining("Category with id: " + id + " not found")
			.isInstanceOf(Problem.class);

		verify(mockCategoryRepository, times(1)).existsById(any());
		verifyNoMoreInteractions(mockCategoryRepository);
		verifyNoInteractions(mockMapper);
	}

	@Test
	void patchCategoryWhenFoundTest() {
		final var entity = createCategoryEntity();
		final var patch = createCategoryPatch();
		when(mockCategoryRepository.findById(any(Integer.class))).thenReturn(Optional.of(entity));
		when(mockCategoryRepository.save(any())).thenReturn(categoryService.patchEntity(entity, patch));

		final var patchedEntity = categoryService.patchCategory(entity.getCategoryId(), patch);

		assertThat(patchedEntity.getTitle()).isEqualTo(patch.title());
		assertThat(patchedEntity.getLabel()).isEqualTo(entity.getLabel());
		assertThat(patchedEntity.getForwardTo()).isEqualTo(entity.getForwardTo());

		verify(mockCategoryRepository, times(1)).findById(any());
		verify(mockMapper, times(1)).toCategoryDto(any(CategoryEntity.class));
		verifyNoMoreInteractions(mockCategoryRepository, mockMapper);
	}

	@Test
	void patchCompanyWhenNotFoundTest() {
		final var id = 5;
		when(mockCategoryRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

		assertThatThrownBy(() -> categoryService.patchCategory(id, any()))
			.hasMessageContaining("Category with id: " + id + " not found")
			.isInstanceOf(Problem.class);

		verify(mockCategoryRepository, times(1)).findById(any());
		verifyNoInteractions(mockMapper);
	}

	@Test
	void patchEntityTest() {
		final var entity = createCategoryEntity();
		final var titleBeforePatch = entity.getTitle();
		final var forwardToBeforePatch = entity.getForwardTo();
		final var patch = createCategoryPatch();

		final var patchedEntity = categoryService.patchEntity(entity, patch);

		assertThat(patchedEntity.getTitle()).satisfies(title -> {
			assertThat(title).isEqualTo(patch.title());
			assertThat(title).isNotEqualTo(titleBeforePatch);
		});
		assertThat(patchedEntity.getForwardTo()).satisfies(email -> {
			assertThat(email).isEqualTo(forwardToBeforePatch);
			assertThat(email).isNotEqualTo(patch.forwardTo());
		});
	}


}
