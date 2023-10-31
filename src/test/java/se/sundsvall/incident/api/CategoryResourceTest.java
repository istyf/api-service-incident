package se.sundsvall.incident.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static se.sundsvall.incident.TestDataFactory.createCategoryDTO;
import static se.sundsvall.incident.TestDataFactory.createCategoryPatch;
import static se.sundsvall.incident.TestDataFactory.createCategoryPost;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import se.sundsvall.incident.service.CategoryService;

@ExtendWith(MockitoExtension.class)
class CategoryResourceTest {

	@Mock
	private CategoryService mockCategoryService;

	@InjectMocks
	private CategoryResource categoryResource;

	@Test
	void getAllCategoriesTest_shouldReturn200_whenFound() {
		when(mockCategoryService.fetchAllCategories()).thenReturn(List.of(createCategoryDTO()));

		var response = categoryResource.getAllCategories();

		assertThat(response.getStatusCode()).isEqualTo(OK);
	}

	@Test
	void getAllCategoriesTest_shouldReturn204_whenEmpty() {
		when(mockCategoryService.fetchAllCategories()).thenReturn(List.of());

		var response = categoryResource.getAllCategories();

		assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);
	}

	@Test
	void getCategoryByIdTest_shouldReturn200_whenFound() {
		when(mockCategoryService.fetchCategoryById(any(Integer.class))).thenReturn(createCategoryDTO());

		var response = categoryResource.getCategoryById(5);

		assertThat(response.getStatusCode()).isEqualTo(OK);
		assertThat(response.getBody()).isNotNull();
	}

	@Test
	void postCategoryTest_shouldReturn201_whenCreated() {
		when(mockCategoryService.createCategory(any())).thenReturn(createCategoryDTO());

		var response = categoryResource.postCategory(createCategoryPost());

		assertThat(response.getStatusCode()).isEqualTo(CREATED);
	}

	@Test
	void patchCategoryTest_shouldReturn200_whenOk() {
		when(mockCategoryService.patchCategory(any(), any())).thenReturn(createCategoryDTO());

		var response = categoryResource.patchCategory(5, createCategoryPatch());

		assertThat(response.getStatusCode()).isEqualTo(OK);
	}

	@Test
	void deleteCategoryByIdTest_shouldReturn204_whenDeleted() {
		var response = categoryResource.deleteCategoryById(5);

		assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);
	}

}
