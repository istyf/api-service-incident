package se.sundsvall.incident.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static se.sundsvall.incident.TestDataFactory.createValidCategoryResponse;
import static se.sundsvall.incident.TestDataFactory.createValidOepCategoryResponse;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import se.sundsvall.incident.integration.db.entity.util.Status;
import se.sundsvall.incident.service.CategoryService;

@ExtendWith(MockitoExtension.class)
class ValidListResourceTest {

	@Mock
	private CategoryService mockCategoryService;

	@InjectMocks
	private ValidListResource validListResource;

	@Test
	void getValidStatuses() {
		var response = validListResource.getValidStatuses();
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody()).hasSize(Status.values().length);
		response.getBody()
			.forEach(validStatusResponse -> assertThat(validStatusResponse.getStatus())
				.isEqualTo(Status.forValue(validStatusResponse.getStatusId())
					.getLabel()));
	}

	@Test
	void getValidCategories() {
		when(mockCategoryService.fetchValidCategories()).thenReturn(List.of(createValidCategoryResponse()));

		var response = validListResource.getValidCategories();
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody()).hasSize(1);

		verify(mockCategoryService, times(1)).fetchValidCategories();
		verifyNoMoreInteractions(mockCategoryService);
	}

	@Test
	void getValidOepCategories() {
		when(mockCategoryService.fetchValidOepCategories()).thenReturn(List.of(createValidOepCategoryResponse()));

		var response = validListResource.getValidOepCategories();
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody()).hasSize(1);

		verify(mockCategoryService, times(1)).fetchValidOepCategories();
		verifyNoMoreInteractions(mockCategoryService);
	}
}
