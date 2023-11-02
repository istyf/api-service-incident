package se.sundsvall.incident.service.mapper;

import java.util.List;
import java.util.UUID;

import se.sundsvall.incident.api.model.AttachmentRequest;
import se.sundsvall.incident.api.model.CategoryDTO;
import se.sundsvall.incident.api.model.CategoryPost;
import se.sundsvall.incident.api.model.IncidentSaveRequest;
import se.sundsvall.incident.dto.AttachmentDto;
import se.sundsvall.incident.dto.Category;
import se.sundsvall.incident.dto.IncidentDto;
import se.sundsvall.incident.dto.Status;
import se.sundsvall.incident.integration.db.entity.AttachmentEntity;
import se.sundsvall.incident.integration.db.entity.CategoryEntity;
import se.sundsvall.incident.integration.db.entity.IncidentEntity;

public final class Mapper {

	private Mapper() {
		// Never instantiate
	}

	public static IncidentEntity toEntity(final IncidentSaveRequest request) {
		if (request == null) {
			return null;
		}

		return IncidentEntity.builder()
			.withIncidentId(UUID.randomUUID().toString())
			.withExternalCaseId(request.getExternalCaseId())
			.withPersonId(request.getPersonId())
			.withPhoneNumber(request.getPhoneNumber())
			.withEmail(request.getEmail())
			.withContactMethod(request.getContactMethod())
			.withCategory(Category.forValue(request.getCategory()))
			.withDescription(request.getDescription())
			.withCoordinates(request.getMapCoordinates())
			.withStatus(Status.INSKICKAT)
			.build();
	}

	public static AttachmentEntity toEntity(final AttachmentRequest request, final String incidentId) {
		if (request == null) {
			return null;
		}
		return AttachmentEntity.builder()
			.withMimeType(request.getMimeType())
			.withNote(request.getNote())
			.withExtension(request.getExtension())
			.withFile(request.getFile())
			.withCategory(request.getCategory())
			.withIncidentId(incidentId)
			.withName(UUID.randomUUID().toString())
			.build();
	}

	public static IncidentDto toIncidentDto(final IncidentEntity entity) {
		if (entity == null) {
			return null;
		}

		return IncidentDto.builder()
			.withIncidentId(entity.getIncidentId())
			.withExternalCaseId(entity.getExternalCaseId())
			.withPersonId(entity.getPersonId())
			.withCreated(entity.getCreated())
			.withUpdated(entity.getUpdated())
			.withPhoneNumber(entity.getPhoneNumber())
			.withEmail(entity.getEmail())
			.withContactMethod(entity.getContactMethod())
			.withCategory(entity.getCategory().getValue())
			.withDescription(entity.getDescription())
			.withMapCoordinates(entity.getCoordinates())
			.withFeedback(entity.getFeedback())
			.withStatusText(entity.getStatus().getLabel())
			.withStatusId(entity.getStatus().getValue())
			.build();
	}

	public static IncidentDto toIncidentDto(final IncidentEntity entity, final List<AttachmentEntity> attachmentList) {
		if (entity == null) {
			return null;
		}

		return IncidentDto.builder()
			.withIncidentId(entity.getIncidentId())
			.withExternalCaseId(entity.getExternalCaseId())
			.withPersonId(entity.getPersonId())
			.withCreated(entity.getCreated())
			.withUpdated(entity.getUpdated())
			.withPhoneNumber(entity.getPhoneNumber())
			.withEmail(entity.getEmail())
			.withContactMethod(entity.getContactMethod())
			.withCategory(entity.getCategory().getValue())
			.withDescription(entity.getDescription())
			.withMapCoordinates(entity.getCoordinates())
			.withFeedback(entity.getFeedback())
			.withAttachments(attachmentList.stream()
				.map(Mapper::toAttachmentDto)
				.toList())
			.build();
	}

	public static AttachmentDto toAttachmentDto(final AttachmentEntity entity) {
		if (entity == null) {
			return null;
		}

		return AttachmentDto.builder()
			.withName(entity.getName())
			.withMimeType(entity.getMimeType())
			.withNote(entity.getNote())
			.withExtension(entity.getExtension())
			.withFile(entity.getFile())
			.withCategory(entity.getCategory())
			.withIncidentId(entity.getIncidentId())
			.build();
	}

	public static CategoryDTO toCategoryDto(final CategoryEntity entity) {
		if (entity == null) {
			return null;
		}

		return CategoryDTO.builder()
			.withCategoryId(entity.getCategoryId())
			.withTitle(entity.getTitle())
			.withLabel(entity.getLabel())
			.withForwardTo(entity.getForwardTo())
			.build();
	}

	public static CategoryEntity toCategoryEntity(final CategoryPost categoryPost) {
		if (categoryPost == null) {
			return null;
		}

		return CategoryEntity.builder()
			.withLabel(categoryPost.label())
			.withTitle(categoryPost.title())
			.withForwardTo(categoryPost.forwardTo())
			.build();
	}

}
