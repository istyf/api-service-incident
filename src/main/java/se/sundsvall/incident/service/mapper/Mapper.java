package se.sundsvall.incident.service.mapper;

import java.util.List;
import java.util.UUID;

import se.sundsvall.incident.api.model.Attachment;
import se.sundsvall.incident.api.model.AttachmentRequest;
import se.sundsvall.incident.api.model.Category;
import se.sundsvall.incident.api.model.CategoryPost;
import se.sundsvall.incident.api.model.IncidentOepResponse;
import se.sundsvall.incident.api.model.IncidentResponse;
import se.sundsvall.incident.api.model.IncidentSaveRequest;
import se.sundsvall.incident.integration.db.entity.AttachmentEntity;
import se.sundsvall.incident.integration.db.entity.CategoryEntity;
import se.sundsvall.incident.integration.db.entity.IncidentEntity;
import se.sundsvall.incident.integration.db.entity.enums.Status;

public final class Mapper {

	private Mapper() {
		// Never instantiate
	}

	public static IncidentEntity toIncidentEntity(final IncidentSaveRequest request,
		final CategoryEntity category, final List<AttachmentEntity> attachments) {
		if (request == null) {
			return null;
		}
		return IncidentEntity.builder()
			.withPersonId(request.getPersonId())
			.withExternalCaseId(request.getExternalCaseId())
			.withDescription(request.getDescription())
			.withStatus(Status.INSKICKAT)
			.withEmail(request.getEmail())
			.withCoordinates(request.getMapCoordinates())
			.withContactMethod(request.getContactMethod())
			.withPhoneNumber(request.getPhoneNumber())
			.withCategory(category)
			.withAttachments(attachments)
			.build();
	}

	public static IncidentResponse toIncidentResponse(final IncidentEntity entity) {
		if (entity == null) {
			return null;
		}
		return IncidentResponse.builder()
			.withIncidentId(entity.getIncidentId())
			.withPersonId(entity.getPersonId())
			.withExternalCaseId(entity.getExternalCaseId())
			.withDescription(entity.getDescription())
			.withPhoneNumber(entity.getPhoneNumber())
			.withContactMethod(entity.getContactMethod())
			.withEmail(entity.getEmail())
			.withDescription(entity.getDescription())
			.withUpdated(entity.getUpdated())
			.withCreated(entity.getCreated())
			.withStatus(entity.getStatus())
			.withCategory(toCategory(entity.getCategory()))
			.withAttachments(entity.getAttachments().stream()
				.map(Mapper::toAttachment)
				.toList())
			.build();
	}

	public static IncidentOepResponse toIncidentOepResponse(final IncidentEntity entity) {
		if (entity == null) {
			return null;
		}
		return IncidentOepResponse.builder()
			.withIncidentId(entity.getIncidentId())
			.withStatusId(entity.getStatus().getValue())
			.withStatusText(entity.getStatus().getLabel())
			.withExternalCaseId(entity.getExternalCaseId())
			.build();
	}

	public static Category toCategory(final CategoryEntity entity) {
		if (entity == null) {
			return null;
		}
		return Category.builder()
			.withCategoryId(entity.getCategoryId())
			.withTitle(entity.getTitle())
			.withLabel(entity.getLabel())
			.withForwardTo(entity.getForwardTo())
			.withSubject(entity.getSubject())
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
			.withSubject(categoryPost.subject())
			.build();
	}

	public static Attachment toAttachment(final AttachmentEntity entity) {
		if (entity == null) {
			return null;
		}
		return Attachment.builder()
			.withName(entity.getName())
			.withMimeType(entity.getMimeType())
			.withNote(entity.getNote())
			.withExtension(entity.getExtension())
			.withFile(entity.getFile())
			.withCategory(entity.getCategory())
			.build();
	}

	public static AttachmentEntity toAttachmentEntity(final AttachmentRequest request) {
		if (request == null) {
			return null;
		}
		return AttachmentEntity.builder()
			.withMimeType(request.getMimeType())
			.withNote(request.getNote())
			.withExtension(request.getExtension())
			.withFile(request.getFile())
			.withCategory(request.getCategory())
			.withName(UUID.randomUUID().toString())
			.build();
	}

}
