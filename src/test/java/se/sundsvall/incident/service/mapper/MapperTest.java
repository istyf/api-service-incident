package se.sundsvall.incident.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.incident.TestDataFactory.createCategoryEntity;
import static se.sundsvall.incident.TestDataFactory.createCategoryPost;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MapperTest {

	@InjectMocks
	private Mapper mapper;

	@Test
	void toCategoryDtoTest() {
		var entity = createCategoryEntity();

		var dto = mapper.toCategoryDto(entity);

		assertThat(dto.getCategoryId()).isEqualTo(entity.getCategoryId());
		assertThat(dto.getTitle()).isEqualTo(entity.getTitle());
		assertThat(dto.getLabel()).isEqualTo(entity.getLabel());
		assertThat(dto.getForwardTo()).isEqualTo(entity.getForwardTo());
	}

	@Test
	void toCategoryDtoWhenNullTest() {
		var dto = mapper.toCategoryDto(null);

		assertThat(dto).isNull();
	}

	@Test
	void toCategoryEntityTest() {
		var postRequest = createCategoryPost();

		var entity = mapper.toCategoryEntity(postRequest);

		assertThat(entity.getTitle()).isEqualTo(postRequest.title());
		assertThat(entity.getLabel()).isEqualTo(postRequest.label());
		assertThat(entity.getForwardTo()).isEqualTo(postRequest.forwardTo());
	}

	@Test
	void toCategoryEntityWhenNullTest() {
		var entity = mapper.toCategoryEntity(null);

		assertThat(entity).isNull();
	}

}
