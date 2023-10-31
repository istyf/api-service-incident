package se.sundsvall.incident.service;

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

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
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

@ExtendWith(MockitoExtension.class)
class IncidentServiceTest {

	@Mock
	private LifeBuoyIntegration lifeBuoyIntegration;

	@Mock
	private MessagingIntegration messagingIntegration;

	@Mock
	private IncidentRepository incidentRepository;

	@Mock
	private AttachmentRepository attachmentRepository;

	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private Mapper mapper;

	@InjectMocks
	private IncidentService incidentService;

	@Test
	void getOepIncidentstatus() {
		final var incidentEntity = buildIncidentEntity(Category.VATTENMATARE);
		when(incidentRepository.findIncidentEntityByExternalCaseId(anyString())).thenReturn(Optional.of(incidentEntity));

		final var response = incidentService.getOepIncidentStatus("123").orElse(null);

		assertThat(response).isNotNull();
		assertThat(response.getIncidentId()).isEqualTo(incidentEntity.getIncidentId());

		verify(incidentRepository, times(1)).findIncidentEntityByExternalCaseId(anyString());
	}

	@Test
	void sendIncident() throws JsonProcessingException {
		final var incidentSaveRequest = buildIncidentSaveRequest(Category.LIVBOJ);
		final var incidentEntity = buildIncidentEntity(Category.LIVBOJ);
		when(incidentRepository.save(any())).thenReturn(incidentEntity);
		when(lifeBuoyIntegration.sendLifeBuoy(any(IncidentDto.class))).thenReturn("SP_20220826_30694");

		final var response = incidentService.sendIncident(incidentSaveRequest);
		assertThat(response).isNotNull();
		assertThat(response.getStatusText()).isEqualTo(Status.INSKICKAT.getLabel());
		verify(incidentRepository, times(1)).save(any());
		verify(lifeBuoyIntegration, times(1)).sendLifeBuoy(any(IncidentDto.class));

	}

	@Test
	void sendMSVIncident() {
		final var incidentSaveRequest = buildIncidentSaveRequest(Category.VATTENMATARE);
		final var incidentEntity = buildIncidentEntity(Category.VATTENMATARE);
		when(incidentRepository.save(any())).thenReturn(incidentEntity);

		doNothing().when(messagingIntegration).sendMSVAEmail(any(IncidentDto.class));

		final var response = incidentService.sendIncident(incidentSaveRequest);
		assertThat(response).isNotNull();
		assertThat(response.getStatusText()).isEqualTo(Status.INSKICKAT.getLabel());
		verify(incidentRepository, times(1)).save(any());
		verify(messagingIntegration, times(1)).sendMSVAEmail(any(IncidentDto.class));
	}

	@Test
	void sendIncidentAndExceptionThrown() {
		final var incidentSaveRequest = buildIncidentSaveRequest(Category.KLOTTER);
		final var incidentEntity = buildIncidentEntity(Category.KLOTTER);
		when(incidentRepository.save(any())).thenReturn(incidentEntity);
		doThrow(new NullPointerException()).when(messagingIntegration).sendEmail(any(IncidentDto.class));

		final var response = incidentService.sendIncident(incidentSaveRequest);
		assertThat(response).isNotNull();
		assertThat(response.getStatusText()).isEqualTo(Status.ERROR.getLabel());
		verify(incidentRepository, times(1)).save(any());
		verify(messagingIntegration, times(1)).sendEmail(any(IncidentDto.class));

	}

	@Test
	void getIncident() {
		final var incidentEntity = buildIncidentEntity(Category.VAG_GATA);
		when(incidentRepository.findById(anyString())).thenReturn(Optional.of(incidentEntity));
		when(attachmentRepository.findAllByIncidentId(anyString())).thenReturn(buildAttachmentEntityList(2));

		final var response = incidentService.getIncident("123").orElse(null);

		assertThat(response).isNotNull();
		assertThat(response.getAttachments()).isNotNull();
		assertThat(response.getAttachments()).hasSize(2);

		verify(incidentRepository, times(1)).findById(anyString());
		verify(attachmentRepository, times(1)).findAllByIncidentId(anyString());

	}

	@Test
	void updateIncidentStatus() {
		final var incidentEntity = buildIncidentEntity(Category.BELYSNING);
		when(incidentRepository.findById(anyString())).thenReturn(Optional.of(incidentEntity));

		incidentService.updateIncidentStatus(INCIDENTID, 2);
		verify(incidentRepository, times(1)).findById(any());
		verify(incidentRepository, times(1)).save(any());
	}

	@Test
	void getIncidents() {
		final var list = buildListIncidentEntities();
		final Page<IncidentEntity> pagey = new PageImpl<>(list);

		when(incidentRepository.findAll(any(PageRequest.class))).thenReturn(pagey);

		final var response = incidentService.getIncidents(1, 4);

		assertThat(response)
			.isNotNull()
			.hasSize(4);
	}

	@Test
	void updateIncidentFeedback() {
		final var incidentEntity = buildIncidentEntity(Category.FELPARKERAD_BIL);
		when(incidentRepository.findById(anyString())).thenReturn(Optional.of(incidentEntity));

		incidentService.updateIncidentFeedback(INCIDENTID, "someFeedback");
		verify(incidentRepository, times(1)).findById(any());
		verify(incidentRepository, times(1)).save(any());
	}

	@Test
	void sendDiwiseIncident() {
		final var request = IncidentSaveRequest.builder()
			.withPersonId("diwise")
			.withContactMethod("email")
			.withCategory(17)
			.withDescription("XXX - Temporärt Fel Läckage")
			.withMapCoordinates("62.388178,17.315090")
			.build();

		final var incidentEntity = IncidentEntity.builder()
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

		final var response = incidentService.sendIncident(request);

		assertThat(response).isNotNull();
		assertThat(response.getStatusText()).isEqualTo(Status.INSKICKAT.getLabel());
		verify(incidentRepository, times(1)).save(any());

	}
}
