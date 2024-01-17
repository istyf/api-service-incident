package se.sundsvall.incident.service;
import static org.zalando.problem.Status.BAD_REQUEST;
import static org.zalando.problem.Status.NOT_FOUND;
import static se.sundsvall.incident.service.mapper.Mapper.toIncidentEntity;
import static se.sundsvall.incident.service.mapper.Mapper.toIncidentOepResponse;
import static se.sundsvall.incident.service.mapper.Mapper.toIncidentResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Problem;

import se.sundsvall.incident.api.model.IncidentOepResponse;
import se.sundsvall.incident.api.model.IncidentResponse;
import se.sundsvall.incident.api.model.IncidentSaveRequest;
import se.sundsvall.incident.api.model.IncidentSaveResponse;
import se.sundsvall.incident.integration.db.entity.IncidentEntity;
import se.sundsvall.incident.integration.db.entity.enums.Status;
import se.sundsvall.incident.integration.db.repository.CategoryRepository;
import se.sundsvall.incident.integration.db.repository.IncidentRepository;
import se.sundsvall.incident.integration.lifebuoy.LifeBuoyIntegration;
import se.sundsvall.incident.integration.messaging.MessagingIntegration;
import se.sundsvall.incident.service.mapper.Mapper;

import generated.se.sundsvall.messaging.MessageResult;
import generated.se.sundsvall.messaging.MessageStatus;

@Service
public class IncidentService {

	private static final String ENTITY_NOT_FOUND = "%s with id: %s not found";
	private static final String CATEGORY = "Category";
	private static final String INCIDENT = "Incident";

	private static final Logger log = LoggerFactory.getLogger(IncidentService.class);
	private final LifeBuoyIntegration lifeBuoyIntegration;
	private final MessagingIntegration messagingIntegration;

	private final IncidentRepository incidentRepository;
	private final CategoryRepository categoryRepository;


	public IncidentService(final LifeBuoyIntegration lifeBuoyIntegration,
		final MessagingIntegration messagingIntegration,
		final IncidentRepository incidentRepository,
		final CategoryRepository categoryRepository) {
		this.lifeBuoyIntegration = lifeBuoyIntegration;
		this.messagingIntegration = messagingIntegration;
		this.incidentRepository = incidentRepository;
		this.categoryRepository = categoryRepository;
	}

	public IncidentResponse fetchIncidentById(final String id) {
		var incident = incidentRepository.findById(id)
			.orElseThrow(() -> Problem.valueOf(NOT_FOUND, ENTITY_NOT_FOUND.formatted(INCIDENT, id)));
		return toIncidentResponse(incident);
	}

	public IncidentOepResponse fetchOepIncidentStatus(final String externalCaseId) {
		var incident = incidentRepository.findIncidentEntityByExternalCaseId(externalCaseId)
			.orElseThrow(() -> Problem.valueOf(NOT_FOUND, ENTITY_NOT_FOUND.formatted(INCIDENT, externalCaseId)));
		return toIncidentOepResponse(incident);
	}

	public List<IncidentResponse> fetchPaginatedIncidents(final Optional<Integer> pageNumber,
		final Optional<Integer> pageSize) {
		var incidents = incidentRepository.findAll(
			PageRequest.of(pageNumber.orElse(0), pageSize.orElse(100)));

		return incidents.stream()
			.map(Mapper::toIncidentResponse)
			.toList();
	}

	@Transactional
	public IncidentSaveResponse createIncident(final IncidentSaveRequest request) {
		var category = categoryRepository.findById(request.getCategory())
			.orElseThrow(() -> Problem.valueOf(BAD_REQUEST, ENTITY_NOT_FOUND.formatted(CATEGORY, request.getCategory())));
		var attachments = Optional.ofNullable(request.getAttachments())
			.map(list -> list.stream()
				.map(Mapper::toAttachmentEntity)
				.toList())
			.orElseGet(ArrayList::new);

		var incident = toIncidentEntity(request, category, attachments);

		sendNotification(incident);

		return IncidentSaveResponse.builder()
			.withIncidentId(incident.getIncidentId())
			.withStatus(String.valueOf(incident.getStatus()))
			.build();
	}

	@Transactional
	public void updateIncidentStatus(final String incidentId, final Integer statusId) {
		var incident = incidentRepository.findById(incidentId)
			.orElseThrow(() -> Problem.valueOf(NOT_FOUND, ENTITY_NOT_FOUND.formatted(INCIDENT, incidentId)));
		incident.setStatus(Status.forValue(statusId));
		incidentRepository.save(incident);
	}

	@Transactional
	public void updateIncidentFeedback(final String incidentId, final String feedback) {
		var incident = incidentRepository.findById(incidentId)
			.orElseThrow(() -> Problem.valueOf(NOT_FOUND, ENTITY_NOT_FOUND.formatted(INCIDENT, incidentId)));
		incident.setFeedback(feedback);
		incidentRepository.save(incident);
	}

	public void sendNotification(final IncidentEntity incident) {
		try {
			switch (incident.getCategory().getTitle()) {
				case "LIVBAT", "LIVBOJ" -> {
					incident.setExternalCaseId(lifeBuoyIntegration.sendLifeBuoy(incident));
					incident.setStatus(Status.INSKICKAT);
				}
				case "VATTENMATARE", "BRADD_OVERVAKNINGS_LARM" ->
					setIncidentStatus(incident, messagingIntegration.sendMSVAEmail(incident));
				default -> setIncidentStatus(incident, messagingIntegration.sendEmail(incident));

			}
		} catch (Exception e) {
			log.warn("Unable to send email for new incident with incidentId: {}, Exception was: {}", incident.getIncidentId(), e.getMessage());
			incident.setStatus(Status.ERROR);
		}
		incidentRepository.save(incident);
	}

	private void setIncidentStatus(final IncidentEntity incident, final Optional<MessageResult> messageResult) {
		messageResult.ifPresentOrElse(
			result -> {
				final var deliveryStatus = result.getDeliveries().get(0).getStatus();
				if (deliveryStatus == MessageStatus.SENT || deliveryStatus == MessageStatus.PENDING) {
					incident.setStatus(Status.INSKICKAT);
				} else {
					incident.setStatus(Status.ERROR);
				}
			},
			() -> incident.setStatus(Status.ERROR));
	}
}
