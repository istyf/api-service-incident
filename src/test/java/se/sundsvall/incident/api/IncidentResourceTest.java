package se.sundsvall.incident.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.sundsvall.incident.TestDataFactory.INCIDENTID;
import static se.sundsvall.incident.TestDataFactory.buildIncidentDto;
import static se.sundsvall.incident.TestDataFactory.buildIncidentSaveRequest;
import static se.sundsvall.incident.TestDataFactory.buildListOfIncidentDto;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import se.sundsvall.incident.dto.Category;
import se.sundsvall.incident.dto.IncidentDto;
import se.sundsvall.incident.service.IncidentService;

@ExtendWith(MockitoExtension.class)
class IncidentResourceTest {

	@Mock
	private IncidentService mockIncidentService;

	@InjectMocks
	private IncidentResource incidentResource;

	@Test
	void getStatusForOeP() {

		final var incidentDto = buildIncidentDto();
		when(mockIncidentService.getOepIncidentStatus(anyString())).thenReturn(Optional.of(incidentDto));
		final var response = incidentResource.getStatusForOeP(INCIDENTID);
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getIncidentId()).isEqualTo(incidentDto.getIncidentId());
		assertThat(response.getBody().getStatusText()).isEqualTo(incidentDto.getStatusText());
		assertThat(response.getBody().getStatusId()).isEqualTo(incidentDto.getStatusId());
		assertThat(response.getBody().getExternalCaseId()).isEqualTo(incidentDto.getExternalCaseId());

		verify(mockIncidentService, times(1)).getOepIncidentStatus(anyString());
	}

	@Test
	void sendIncident() {
		final var incidentDto = buildIncidentDto();
		when(mockIncidentService.sendIncident(any())).thenReturn(incidentDto);

		final var request = buildIncidentSaveRequest(Category.LIVBOJ);

		final var response = incidentResource.sendIncident(request);
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getIncidentId()).isEqualTo(INCIDENTID);
		assertThat(response.getBody().getStatus()).isEqualTo("OK");

		verify(mockIncidentService, times(1)).sendIncident(any());

	}

	@Test
	void getIncident() {
		final var incidentDto = buildIncidentDto();
		when(mockIncidentService.getIncident(anyString())).thenReturn(Optional.of(incidentDto));

		final var response = incidentResource.getIncident(INCIDENTID);
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody()).usingRecursiveComparison()
			.ignoringFields("status")
			.isEqualTo(incidentDto);
		assertThat(response.getBody().getStatus()).isEqualTo(incidentDto.getStatusText());

		verify(mockIncidentService, times(1)).getIncident(anyString());

	}

	@Test
	void getAllIncidents() {
		final List<IncidentDto> incidentDtos = buildListOfIncidentDto();

		when(mockIncidentService.getIncidents(anyInt(), anyInt()))
			.thenReturn(incidentDtos);
		final var response = incidentResource.getAllIncidents(0, 2);
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody()).hasSize(2);
		assertThat(response.getBody().get(0).getIncidentId()).isEqualTo(incidentDtos.get(0).getIncidentId());
		assertThat(response.getBody().get(0).getExternalCaseId()).isEqualTo(incidentDtos.get(0).getExternalCaseId());
		assertThat(response.getBody().get(0).getStatus()).isEqualTo(incidentDtos.get(0).getStatusText());

		assertThat(response.getBody().get(1).getIncidentId()).isEqualTo(incidentDtos.get(1).getIncidentId());
		assertThat(response.getBody().get(1).getExternalCaseId()).isEqualTo(incidentDtos.get(1).getExternalCaseId());
		assertThat(response.getBody().get(1).getStatus()).isEqualTo(incidentDtos.get(1).getStatusText());

		verify(mockIncidentService, times(1)).getIncidents(anyInt(), anyInt());

	}

	@Test
	void updateIncidentStatus() {
		doNothing().when(mockIncidentService).updateIncidentStatus(anyString(), anyInt());
		final var response = incidentResource.updateIncidentStatus(INCIDENTID, 3);
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		verify(mockIncidentService, times(1)).updateIncidentStatus(anyString(), anyInt());

	}

	@Test
	void setIncidentFeedback() {
		doNothing().when(mockIncidentService).updateIncidentFeedback(anyString(), anyString());
		final var response = incidentResource.setIncidentFeedback(INCIDENTID, "test");
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		verify(mockIncidentService, times(1)).updateIncidentFeedback(anyString(), anyString());

	}
}
