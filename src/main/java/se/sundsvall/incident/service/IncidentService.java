package se.sundsvall.incident.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import se.sundsvall.incident.api.model.IncidentSaveRequest;
import se.sundsvall.incident.dto.IncidentDto;
import se.sundsvall.incident.dto.Status;
import se.sundsvall.incident.integration.db.AttachmentRepository;
import se.sundsvall.incident.integration.db.IncidentRepository;
import se.sundsvall.incident.integration.db.entity.AttachmentEntity;
import se.sundsvall.incident.integration.db.entity.IncidentEntity;
import se.sundsvall.incident.integration.lifebuoy.LifeBuoyIntegration;
import se.sundsvall.incident.integration.messaging.MessagingIntegration;
import se.sundsvall.incident.service.mapper.Mapper;

@Service
public class IncidentService {

	private static final Logger log = LoggerFactory.getLogger(IncidentService.class);
	private final LifeBuoyIntegration lifeBuoyIntegration;
	private final MessagingIntegration messagingIntegration;
	private final IncidentRepository incidentRepository;
	private final AttachmentRepository attachmentRepository;
	private final Mapper mapper;

	public IncidentService(final IncidentRepository incidentRepository,
		final LifeBuoyIntegration lifeBuoyIntegration,
		final MessagingIntegration messagingIntegration,
		final Mapper mapper,
		final AttachmentRepository attachmentRepository) {
		this.incidentRepository = incidentRepository;
		this.lifeBuoyIntegration = lifeBuoyIntegration;
		this.messagingIntegration = messagingIntegration;
		this.mapper = mapper;
		this.attachmentRepository = attachmentRepository;
	}

	public Optional<IncidentDto> getOepIncidentStatus(final String externalCaseId) {
		return incidentRepository.findIncidentEntityByExternalCaseId(externalCaseId).map(mapper::toIncidentDto);
	}

	public IncidentDto sendIncident(final IncidentSaveRequest request) {
		final var entity = mapper.toEntity(request);

		final List<AttachmentEntity> attachmentList = Optional.ofNullable(request.getAttachments()).orElse(List.of())
			.stream()
			.map(e -> mapper.toEntity(e, entity.getIncidentId()))
			.toList();

		try {
			switch (entity.getCategory()) {
				case LIVBAT, LIVBOJ -> entity.setExternalCaseId(lifeBuoyIntegration.sendLifeBuoy(mapper.toIncidentDto(entity)));
				case VATTENMATARE, BRADD_OVERVAKNINGS_LARM -> messagingIntegration.sendMSVAEmail(mapper.toIncidentDto(entity));
				default -> messagingIntegration.sendEmail(mapper.toIncidentDto(entity, attachmentList));
			}
		} catch (final Exception e) {
			entity.setStatus(Status.ERROR);

			log.warn("Unable to send email for new incident with incidentId: {}, Exception was: {}", entity.getIncidentId(), e);
		}
		incidentRepository.save(entity);
		attachmentRepository.saveAll(attachmentList);

		return IncidentDto.builder()
			.withIncidentId(entity.getIncidentId())
			.withStatusText(entity.getStatus().getLabel())
			.build();
	}

	public Optional<IncidentDto> getIncident(final String incidentId) {
		return incidentRepository.findById(incidentId)
			.map((IncidentEntity incidentEntity) -> mapper.toIncidentDto(incidentEntity, attachmentRepository.findAllByIncidentId(incidentId)));
	}

	public void updateIncidentStatus(final String incidentId, final Integer statusId) {
		incidentRepository.findById(incidentId)
			.ifPresent(entity -> {
				entity.setStatus(Status.forValue(statusId));
				entity.setUpdated(LocalDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
				incidentRepository.save(entity);
			});
	}

	public List<IncidentDto> getIncidents(final int offset, final int limit) {
		return incidentRepository.findAll(PageRequest.of(offset, limit))
			.stream()
			.map((IncidentEntity incidentEntity) -> mapper.toIncidentDto(incidentEntity, attachmentRepository.findAllByIncidentId(incidentEntity.getIncidentId())))
			.toList();
	}

	public void updateIncidentFeedback(final String incidentId, final String feedback) {
		incidentRepository.findById(incidentId)
			.ifPresent(entity -> {
				entity.setFeedback(feedback);
				entity.setUpdated(LocalDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
				incidentRepository.save(entity);
			});
	}

}
