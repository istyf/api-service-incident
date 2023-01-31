package se.sundsvall.incident.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import se.sundsvall.incident.api.model.IncidentSaveRequest;
import se.sundsvall.incident.dto.Category;
import se.sundsvall.incident.dto.IncidentDto;
import se.sundsvall.incident.dto.Status;
import se.sundsvall.incident.integration.db.AttachmentRepository;
import se.sundsvall.incident.integration.db.IncidentRepository;
import se.sundsvall.incident.integration.db.entity.IncidentEntity;
import se.sundsvall.incident.integration.lifebuoy.LifeBuoyIntegration;
import se.sundsvall.incident.integration.messaging.MessagingIntegration;
import se.sundsvall.incident.service.mapper.Mapper;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.sundsvall.incident.TestDataFactory.INCIDENTID;
import static se.sundsvall.incident.TestDataFactory.buildAttachmentEntityList;
import static se.sundsvall.incident.TestDataFactory.buildIncidentEntity;
import static se.sundsvall.incident.TestDataFactory.buildIncidentSaveRequest;
import static se.sundsvall.incident.TestDataFactory.buildListIncidentEntities;

@ExtendWith(MockitoExtension.class)
class IncidentServiceTest {

    @Mock
    LifeBuoyIntegration lifeBuoyIntegration;
    @Mock
    MessagingIntegration messagingIntegration;
    @Mock
    IncidentRepository incidentRepository;
    @Mock
    AttachmentRepository attachmentRepository;

    IncidentService incidentService;
    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private Mapper mockMapper;

    @BeforeEach
    void setUp() {
        incidentService = new IncidentService(incidentRepository, lifeBuoyIntegration, messagingIntegration, mockMapper, attachmentRepository);
    }

    @Test
    void getOepIncidentstatus() {
        var incidentEntity = buildIncidentEntity(Category.VATTENMATARE);
        when(incidentRepository.findIncidentEntityByExternalCaseId(anyString())).thenReturn(Optional.of(incidentEntity));

        var response = incidentService.getOepIncidentstatus("123").orElse(null);

        assertThat(response).isNotNull();
        assertThat(response.getIncidentId()).isEqualTo(incidentEntity.getIncidentId());

        verify(incidentRepository, times(1)).findIncidentEntityByExternalCaseId(anyString());

    }

    @Test
    void sendIncident() {
        var incidentSaveRequest = buildIncidentSaveRequest(Category.LIVBOJ);
        var incidentEntity = buildIncidentEntity(Category.LIVBOJ);
        when(incidentRepository.save(any())).thenReturn(incidentEntity);
        when(lifeBuoyIntegration.sendLifeBuoy(any(IncidentDto.class))).thenReturn("SP_20220826_30694");

        var response = incidentService.sendIncident(incidentSaveRequest);
        assertThat(response).isNotNull();
        assertThat(response.getStatusText()).isEqualTo(Status.INSKICKAT.getLabel());
        verify(incidentRepository, times(1)).save(any());
        verify(lifeBuoyIntegration, times(1)).sendLifeBuoy(any(IncidentDto.class));


    }

    @Test
    void sendMSVIncident() {
        var incidentSaveRequest = buildIncidentSaveRequest(Category.VATTENMATARE);
        var incidentEntity = buildIncidentEntity(Category.VATTENMATARE);
        when(incidentRepository.save(any())).thenReturn(incidentEntity);

        doNothing().when(messagingIntegration).sendMSVAEmail(any(IncidentDto.class));

        var response = incidentService.sendIncident(incidentSaveRequest);
        assertThat(response).isNotNull();
        assertThat(response.getStatusText()).isEqualTo(Status.INSKICKAT.getLabel());
        verify(incidentRepository, times(1)).save(any());
        verify(messagingIntegration, times(1)).sendMSVAEmail(any(IncidentDto.class));
    }

    @Test
    void sendIncidentAndExceptionThrown() {
        var incidentSaveRequest = buildIncidentSaveRequest(Category.KLOTTER);
        var incidentEntity = buildIncidentEntity(Category.KLOTTER);
        when(incidentRepository.save(any())).thenReturn(incidentEntity);
        doThrow(new NullPointerException()).when(messagingIntegration).sendEmail(any(IncidentDto.class));

        var response = incidentService.sendIncident(incidentSaveRequest);
        assertThat(response).isNotNull();
        assertThat(response.getStatusText()).isEqualTo(Status.ERROR.getLabel());
        verify(incidentRepository, times(1)).save(any());
        verify(messagingIntegration, times(1)).sendEmail(any(IncidentDto.class));

    }

    @Test
    void getIncident() {
        var incidentEntity = buildIncidentEntity(Category.VAG_GATA);
        when(incidentRepository.findById(anyString())).thenReturn(Optional.of(incidentEntity));
        when(attachmentRepository.findAllByIncidentId(anyString())).thenReturn(buildAttachmentEntityList(2));

        var response = incidentService.getIncident("123").orElse(null);

        assertThat(response).isNotNull();
        assertThat(response.getAttachments()).isNotNull();
        assertThat(response.getAttachments().size()).isEqualTo(2);

        verify(incidentRepository, times(1)).findById(anyString());
        verify(attachmentRepository, times(1)).findAllByIncidentId(anyString());

    }

    @Test
    void updateIncidentStatus() {
        var incidentEntity = buildIncidentEntity(Category.BELYSNING);
        when(incidentRepository.findById(anyString())).thenReturn(Optional.of(incidentEntity));

        incidentService.updateIncidentStatus(INCIDENTID, 2);
        verify(incidentRepository, times(1)).findById(any());
        verify(incidentRepository, times(1)).save(any());


    }

    @Test
    void getIncidents() {
        var list = buildListIncidentEntities();
        Page<IncidentEntity> pagey = new PageImpl<>(list);

        when(incidentRepository.findAll(any(PageRequest.class))).thenReturn(pagey);

        var response = incidentService.getIncidents(1, 4);

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(4);
    }

    @Test
    void updateIncidentFeedback() {
        var incidentEntity = buildIncidentEntity(Category.FELPARKERAD_BIL);
        when(incidentRepository.findById(anyString())).thenReturn(Optional.of(incidentEntity));

        incidentService.updateIncidentFeedback(INCIDENTID, "someFeedback");
        verify(incidentRepository, times(1)).findById(any());
        verify(incidentRepository, times(1)).save(any());
    }

    @Test
    void sendDiwiseIncident() {
        var request = IncidentSaveRequest.builder()
                .withPersonId("diwise")
                .withContactMethod("email")
                .withCategory(17)
                .withDescription("XXX - Temporärt Fel Läckage")
                .withMapCoordinates("62.388178,17.315090")
                .build();

        var incidentEntity = IncidentEntity.builder()
                .withIncidentId(INCIDENTID)
                .withExternalCaseId("12345")
                .withPersonID("diwise")
                .withCreated("2021-06-17T23:04:11.000Z")
                .withUpdated("2022-02-13T09:13:45.000Z")
                .withCategory(Category.VATTENMATARE)
                .withDescription("XXX - Temporärt Fel Läckage")
                .withMapCoordinates("62.388178,17.315090")
                .withStatus(Status.INSKICKAT)
                .build();


        when(incidentRepository.save(any())).thenReturn(incidentEntity);

        var response = incidentService.sendIncident(request);

        assertThat(response).isNotNull();
        assertThat(response.getStatusText()).isEqualTo(Status.INSKICKAT.getLabel());
        verify(incidentRepository, times(1)).save(any());

    }
}