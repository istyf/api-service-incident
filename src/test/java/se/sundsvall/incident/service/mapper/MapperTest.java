package se.sundsvall.incident.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.incident.TestDataFactory.createCategoryEntity;
import static se.sundsvall.incident.TestDataFactory.createCategoryPost;
import static se.sundsvall.incident.service.mapper.Mapper.toAttachment;
import static se.sundsvall.incident.service.mapper.Mapper.toAttachmentEntity;
import static se.sundsvall.incident.service.mapper.Mapper.toCategoryDto;
import static se.sundsvall.incident.service.mapper.Mapper.toCategoryEntity;
import static se.sundsvall.incident.service.mapper.Mapper.toIncidentEntity;
import static se.sundsvall.incident.service.mapper.Mapper.toIncidentOepResponse;
import static se.sundsvall.incident.service.mapper.Mapper.toIncidentResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MapperTest {

	@Test
	void toCategoryDtoTest() {
		var entity = createCategoryEntity();

		var dto = toCategoryDto(entity);

		assertThat(dto.getCategoryId()).isEqualTo(entity.getCategoryId());
		assertThat(dto.getTitle()).isEqualTo(entity.getTitle());
		assertThat(dto.getLabel()).isEqualTo(entity.getLabel());
		assertThat(dto.getForwardTo()).isEqualTo(entity.getForwardTo());
	}

	@Test
	void toCategoryDtoWhenNullTest() {
		var dto = toCategoryDto(null);

		assertThat(dto).isNull();
	}

	@Test
	void toCategoryEntityTest() {
		var postRequest = createCategoryPost();

		var entity = toCategoryEntity(postRequest);

		assertThat(entity.getTitle()).isEqualTo(postRequest.title());
		assertThat(entity.getLabel()).isEqualTo(postRequest.label());
		assertThat(entity.getForwardTo()).isEqualTo(postRequest.forwardTo());
	}

	@Test
	void toCategoryEntityWhenNullTest() {
		var entity = toCategoryEntity(null);
		assertThat(entity).isNull();
	}

	@Test
	void toIncidentEntityWhenNullTest() {
		var entity = toIncidentEntity(null, null, null);
		assertThat(entity).isNull();
	}

	@Test
	void toIncidentResponseWhenNullTest() {
		var entity = toIncidentResponse(null);
		assertThat(entity).isNull();
	}

	@Test
	void toIncidentOepResponseWhenNullTest() {
		var entity = toIncidentOepResponse(null);
		assertThat(entity).isNull();
	}

	@Test
	void toAttachmentWhenNullTest() {
		var entity = toAttachment(null);
		assertThat(entity).isNull();
	}

	@Test
	void toAttachmentEntityWhenNullTest() {
		var entity = toAttachmentEntity(null);
		assertThat(entity).isNull();
	}

}
