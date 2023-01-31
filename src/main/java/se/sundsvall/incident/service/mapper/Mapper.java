package se.sundsvall.incident.service.mapper;

import org.springframework.stereotype.Component;
import se.sundsvall.incident.api.model.AttachmentRequest;
import se.sundsvall.incident.api.model.IncidentSaveRequest;
import se.sundsvall.incident.dto.AttachmentDto;
import se.sundsvall.incident.dto.Category;
import se.sundsvall.incident.dto.IncidentDto;
import se.sundsvall.incident.dto.Status;
import se.sundsvall.incident.integration.db.entity.AttachmentEntity;
import se.sundsvall.incident.integration.db.entity.IncidentEntity;

import java.util.List;
import java.util.UUID;

@Component
public class Mapper {

    public IncidentEntity toEntity(IncidentSaveRequest request) {
        var uuid = UUID.randomUUID().toString();
        return IncidentEntity.builder()
                .withIncidentId(uuid)
                .withExternalCaseId(request.getExternalCaseId())
                .withPersonID(request.getPersonId())
                .withPhoneNumber(request.getPhoneNumber())
                .withEmail(request.getEmail())
                .withContactMethod(request.getContactMethod())
                .withCategory(Category.forValue(request.getCategory()))
                .withDescription(request.getDescription())
                .withMapCoordinates(request.getMapCoordinates())
                .withStatus(Status.INSKICKAT)
                .build();
    }

    public AttachmentEntity toEntity(AttachmentRequest request, String incidentId) {
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

    public IncidentDto toIncidentDto(IncidentEntity incidentEntity) {
        return IncidentDto.builder()
                .withIncidentId(incidentEntity.getIncidentId())
                .withExternalCaseId(incidentEntity.getExternalCaseId())
                .withPersonId(incidentEntity.getPersonID())
                .withCreated(incidentEntity.getCreated())
                .withUpdated(incidentEntity.getUpdated())
                .withPhoneNumber(incidentEntity.getPhoneNumber())
                .withEmail(incidentEntity.getEmail())
                .withContactMethod(incidentEntity.getContactMethod())
                .withCategory(incidentEntity.getCategory().getValue())
                .withDescription(incidentEntity.getDescription())
                .withMapCoordinates(incidentEntity.getMapCoordinates())
                .withFeedback(incidentEntity.getFeedback())
                .withStatusText(incidentEntity.getStatus().getLabel())
                .withStatusId(incidentEntity.getStatus().getValue())
                .build();
    }

    public IncidentDto toIncidentDto(IncidentEntity incidentEntity, List<AttachmentEntity> attachmentList) {
        return IncidentDto.builder()
                .withIncidentId(incidentEntity.getIncidentId())
                .withExternalCaseId(incidentEntity.getExternalCaseId())
                .withPersonId(incidentEntity.getPersonID())
                .withCreated(incidentEntity.getCreated())
                .withUpdated(incidentEntity.getUpdated())
                .withPhoneNumber(incidentEntity.getPhoneNumber())
                .withEmail(incidentEntity.getEmail())
                .withContactMethod(incidentEntity.getContactMethod())
                .withCategory(incidentEntity.getCategory().getValue())
                .withDescription(incidentEntity.getDescription())
                .withMapCoordinates(incidentEntity.getMapCoordinates())
                .withFeedback(incidentEntity.getFeedback())
                .withAttachments(attachmentList.stream()
                        .map(this::toAttachmentDto)
                        .toList())
                .build();
    }

    public AttachmentDto toAttachmentDto(AttachmentEntity attachmentEntity) {
        return AttachmentDto.builder()
                .withName(attachmentEntity.getName())
                .withMimeType(attachmentEntity.getMimeType())
                .withNote(attachmentEntity.getNote())
                .withExtension(attachmentEntity.getExtension())
                .withFile(attachmentEntity.getFile())
                .withCategory(attachmentEntity.getCategory())
                .withIncidentId(attachmentEntity.getIncidentId())
                .build();
    }
}
