package se.sundsvall.incident.service;


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

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class IncidentService {

    private static final Logger log = LoggerFactory.getLogger(IncidentService.class);
    private final LifeBuoyIntegration lifeBuoyIntegration;
    private final MessagingIntegration messagingIntegration;
    private final IncidentRepository incidentRepository;
    private final AttachmentRepository attachmentRepository;
    private final Mapper mapper;

    public IncidentService(IncidentRepository incidentRepository,

                           LifeBuoyIntegration lifeBuoyIntegration,
                           MessagingIntegration messagingIntegration, Mapper mapper,
                           AttachmentRepository attachmentRepository) {
        this.incidentRepository = incidentRepository;
        this.lifeBuoyIntegration = lifeBuoyIntegration;
        this.messagingIntegration = messagingIntegration;
        this.mapper = mapper;
        this.attachmentRepository = attachmentRepository;
    }

    public Optional<IncidentDto> getOepIncidentstatus(String externalCaseId) {
        return incidentRepository.findIncidentEntityByExternalCaseId(externalCaseId).map(mapper::toIncidentDto);
    }

    public IncidentDto sendIncident(IncidentSaveRequest request) {
        var entity = mapper.toEntity(request);


        List<AttachmentEntity> attachmentList = Optional.ofNullable(request.getAttachments()).orElse(List.of())
                .stream()
                .map(e -> mapper.toEntity(e, entity.getIncidentId()))
                .toList();

        try {
            switch (entity.getCategory()) {
                case LIVBAT, LIVBOJ ->
                        entity.setExternalCaseId(lifeBuoyIntegration.sendLifeBuoy(mapper.toIncidentDto(entity)));
                case VATTENMATARE -> messagingIntegration.sendMSVAEmail(mapper.toIncidentDto(entity));
                default -> messagingIntegration.sendEmail(mapper.toIncidentDto(entity, attachmentList));
            }
        } catch (Exception e) {
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

    public Optional<IncidentDto> getIncident(String incidentId) {
        return incidentRepository.findById(incidentId)
                .map((IncidentEntity incidentEntity) ->
                        mapper.toIncidentDto(incidentEntity, attachmentRepository.findAllByIncidentId(incidentId)));
    }

    public void updateIncidentStatus(String incidentId, Integer statusid) {
        incidentRepository
                .findById(incidentId).map(entity -> {
                    entity.setStatus(Status.forValue(statusid));
                    entity.setUpdated(OffsetDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                    return incidentRepository.save(entity);
                });

    }

    public List<IncidentDto> getIncidents(int offset, int limit) {
        return incidentRepository.findAll(PageRequest.of(offset, limit))
                .stream()
                .map((IncidentEntity incidentEntity) ->
                        mapper.toIncidentDto(incidentEntity, attachmentRepository.findAllByIncidentId(incidentEntity.getIncidentId())))
                .toList();
    }

    public void updateIncidentFeedback(String incidentId, String feedback) {
        incidentRepository.findById(incidentId).map(i -> {
            i.setFeedback(feedback);
            i.setUpdated(OffsetDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            return incidentRepository.save(i);
        });
    }


}
