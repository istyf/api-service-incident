package se.sundsvall.incident.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.incident.TestDataFactory.createCategoryEntity;
import static se.sundsvall.incident.TestDataFactory.createCategoryPost;
import static se.sundsvall.incident.service.mapper.Mapper.toAttachment;
import static se.sundsvall.incident.service.mapper.Mapper.toAttachmentEntity;
import static se.sundsvall.incident.service.mapper.Mapper.toCategory;
import static se.sundsvall.incident.service.mapper.Mapper.toCategoryEntity;
import static se.sundsvall.incident.service.mapper.Mapper.toIncidentEntity;
import static se.sundsvall.incident.service.mapper.Mapper.toIncidentOepResponse;
import static se.sundsvall.incident.service.mapper.Mapper.toIncidentResponse;

import org.junit.jupiter.api.Test;

class MapperTest {

	@Test
	void toCategoryTest() {
		var entity = createCategoryEntity();

		var category = toCategory(entity);

		assertThat(category.getCategoryId()).isEqualTo(entity.getCategoryId());
		assertThat(category.getTitle()).isEqualTo(entity.getTitle());
		assertThat(category.getLabel()).isEqualTo(entity.getLabel());
		assertThat(category.getForwardTo()).isEqualTo(entity.getForwardTo());
	}

	@Test
	void toCategoryWhenNullTest() {
		var category = toCategory(null);

		assertThat(category).isNull();
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
		var response = toIncidentResponse(null);
		assertThat(response).isNull();
	}

	@Test
	void toIncidentOepResponseWhenNullTest() {
		var response = toIncidentOepResponse(null);
		assertThat(response).isNull();
	}

	@Test
	void toAttachmentWhenNullTest() {
		var attachment = toAttachment(null);
		assertThat(attachment).isNull();
	}

	@Test
	void toAttachmentEntityWhenNullTest() {
		var entity = toAttachmentEntity(null);
		assertThat(entity).isNull();
	}

}
