package se.sundsvall.incident.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;
import static se.sundsvall.incident.TestDataFactory.createIncidentOepResponse;
import static se.sundsvall.incident.TestDataFactory.createIncidentResponse;
import static se.sundsvall.incident.TestDataFactory.createIncidentSaveRequest;
import static se.sundsvall.incident.TestDataFactory.createIncidentSaveResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import se.sundsvall.incident.integration.db.entity.util.Status;
import se.sundsvall.incident.service.IncidentService;

@ExtendWith(MockitoExtension.class)
class IncidentResourceTest {

	@Mock
	private IncidentService mockIncidentService;

	@InjectMocks
	private IncidentResource incidentResource;

	@Test
	void fetchAllIncidents_200_WhenFoundTest() {
		when(mockIncidentService.fetchPaginatedIncidents(any(), any())).thenReturn(List.of(createIncidentResponse()));

		var response = incidentResource.fetchAllIncidents(Optional.of(0), Optional.of(10));

		assertThat(response.getStatusCode()).isEqualTo(OK);
		assertThat(response.getBody()).hasSize(1);

		verify(mockIncidentService, times(1)).fetchPaginatedIncidents(any(), any());
	}

	@Test
	void fetchAllIncidents_200_WhenEmptyTest() {
		when(mockIncidentService.fetchPaginatedIncidents(any(), any())).thenReturn(List.of());

		var response = incidentResource.fetchAllIncidents(Optional.of(0), Optional.of(10));

		assertThat(response.getStatusCode()).isEqualTo(OK);
		assertThat(response.getBody()).isEmpty();

		verify(mockIncidentService, times(1)).fetchPaginatedIncidents(any(), any());
	}

	@Test
	void fetchIncidentById_200_Test() {
		var incidentResponse = createIncidentResponse();
		when(mockIncidentService.fetchIncidentById(any(String.class))).thenReturn(incidentResponse);

		var response = incidentResource.fetchIncidentById(anyString());

		assertThat(response.getStatusCode()).isEqualTo(OK);
		assertThat(response.getBody().getIncidentId()).isEqualTo(incidentResponse.getIncidentId());
		assertThat(response.getBody().getDescription()).isEqualTo(incidentResponse.getDescription());
	}

	@Test
	void getStatusForOeP() {
		when(mockIncidentService.fetchOepIncidentStatus(anyString())).thenReturn(createIncidentOepResponse());
		final var response = incidentResource.getStatusForOeP(anyString());
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(OK);
		assertThat(response.getBody()).isNotNull();
		verify(mockIncidentService, times(1)).fetchOepIncidentStatus(anyString());
	}

	@Test
	void createIncidentTest() {
		var request = createIncidentSaveRequest();
		when(mockIncidentService.createIncident(any())).thenReturn(createIncidentSaveResponse());

		var response = incidentResource.createIncident(request);

		assertThat(response.getStatusCode()).isEqualTo(OK);
		assertThat(response.getBody()).isNotNull();
		verify(mockIncidentService, times(1)).createIncident(any());
	}

	@Test
	void updateIncidentStatusTest() {
		var uuid = UUID.randomUUID().toString();
		incidentResource.patchStatus(uuid, 2);

		verify(mockIncidentService, times(1)).updateIncidentStatus(uuid, 2);
	}

	@Test
	void updateIncidentFeedbackTest() {
		var uuid = UUID.randomUUID().toString();
		incidentResource.patchFeedback(uuid, "Bra jobbat");

		verify(mockIncidentService, times(1)).updateIncidentFeedback(uuid, "Bra jobbat");
	}

	@Test
	void getValidStatuses() {
		var response = incidentResource.getValidIncidentStatuses();
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody()).hasSize(Status.values().length);
		response.getBody()
			.forEach(validStatusResponse -> assertThat(validStatusResponse.getStatus())
				.isEqualTo(Status.forValue(validStatusResponse.getStatusId())
					.getLabel()));
	}

}
