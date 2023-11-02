package se.sundsvall.incident.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import se.sundsvall.incident.dto.Category;
import se.sundsvall.incident.dto.Status;

@ExtendWith(MockitoExtension.class)
class ValidListResourceTest {

	@InjectMocks
	private ValidListResource validListResource;

	@Test
	void getValidStatuses() {

		var response = validListResource.getValidstatuses();
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

		var response = validListResource.getValidCategories();
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody()).hasSize(Category.values().length);
		response.getBody()
			.forEach(validCategoryResponse -> assertThat(validCategoryResponse.getCategory())
				.isEqualTo(Category.forValue(validCategoryResponse.getCategoryId())
					.getLabel()));
	}

	@Test
	void getValidOepCategories() {

		var response = validListResource.getValidOepCategories();
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody()).hasSize(Category.values().length);
		response.getBody().forEach(
			validOepCategoryResponse -> assertThat(validOepCategoryResponse.getValue())
				.isEqualTo(Category.forValue(Integer.parseInt(validOepCategoryResponse.getKey()))
					.getLabel())
		);
	}
}
