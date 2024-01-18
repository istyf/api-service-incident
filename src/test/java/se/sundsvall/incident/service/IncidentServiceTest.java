package se.sundsvall.incident.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.zalando.problem.Status.BAD_REQUEST;
import static se.sundsvall.incident.TestDataFactory.createCategoryEntity;
import static se.sundsvall.incident.TestDataFactory.createIncidentEntity;
import static se.sundsvall.incident.TestDataFactory.createIncidentSaveRequest;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.zalando.problem.Problem;

import se.sundsvall.incident.api.model.IncidentOepResponse;
import se.sundsvall.incident.api.model.IncidentResponse;
import se.sundsvall.incident.api.model.IncidentSaveResponse;
import se.sundsvall.incident.integration.db.entity.IncidentEntity;
import se.sundsvall.incident.integration.db.entity.enums.Status;
import se.sundsvall.incident.integration.db.repository.CategoryRepository;
import se.sundsvall.incident.integration.db.repository.IncidentRepository;
import se.sundsvall.incident.integration.lifebuoy.LifeBuoyIntegration;
import se.sundsvall.incident.integration.messaging.MessagingIntegration;

import generated.se.sundsvall.messaging.MessageResult;

@ExtendWith(MockitoExtension.class)
class IncidentServiceTest {

	@Mock
	private LifeBuoyIntegration mockLifeBuoyIntegration;

	@Mock
	private MessagingIntegration mockMessagingIntegration;

	@Mock
	private CategoryRepository mockCategoryRepository;

	@Mock
	private IncidentRepository mockIncidentRepository;

	@InjectMocks
	private IncidentService incidentService;

	@Test
	void fetchIncidentByIdTest() {
		when(mockIncidentRepository.findById(anyString())).thenReturn(Optional.ofNullable(createIncidentEntity()));

		var result = incidentService.fetchIncidentById(anyString());

		assertThat(result).isNotNull().isInstanceOf(IncidentResponse.class);
		verify(mockIncidentRepository).findById(anyString());
		verifyNoMoreInteractions(mockIncidentRepository);
	}

	@Test
	void fetchIncidentById_NotFoundTest() {
		when(mockIncidentRepository.findById(anyString())).thenReturn(Optional.empty());

		assertThatThrownBy(() -> incidentService.fetchIncidentById(anyString()))
			.hasMessageContaining("Not Found: Incident with id:  not found")
			.isInstanceOf(Problem.class);

		verify(mockIncidentRepository).findById(anyString());
	}

	@Test
	void fetchOepIncidentStatusTest() {
		when(mockIncidentRepository.findIncidentEntityByExternalCaseId(anyString()))
			.thenReturn(Optional.ofNullable(createIncidentEntity()));

		var result = incidentService.fetchOepIncidentStatus(anyString());

		assertThat(result).isNotNull().isInstanceOf(IncidentOepResponse.class);
		verify(mockIncidentRepository).findIncidentEntityByExternalCaseId(any());
		verifyNoMoreInteractions(mockIncidentRepository);
	}

	@Test
	void fetchOepIncidentStatus_NotFoundTest() {
		when(mockIncidentRepository.findIncidentEntityByExternalCaseId(anyString())).thenReturn(Optional.empty());

		assertThatThrownBy(() -> incidentService.fetchOepIncidentStatus(anyString()))
			.hasMessageContaining("Not Found: Incident with id:  not found")
			.isInstanceOf(Problem.class);

		verify(mockIncidentRepository).findIncidentEntityByExternalCaseId(anyString());
	}

	@Test
	void fetchPaginatedIncidentsTest() {
		var incidents = List.of(createIncidentEntity(), createIncidentEntity());
		Page<IncidentEntity> page = new PageImpl<>(incidents);
		when(mockIncidentRepository.findAll(PageRequest.of(1, 2))).thenReturn(page);

		var result = incidentService.fetchPaginatedIncidents(Optional.of(1), Optional.of(2));

		assertThat(result).hasSize(2);
		verify(mockIncidentRepository).findAll(any(PageRequest.class));
		verifyNoMoreInteractions(mockIncidentRepository);
	}

	@Test
	void createIncidentTest() {
		var request = createIncidentSaveRequest();
		when(mockCategoryRepository.findById(any())).thenReturn(Optional.ofNullable(createCategoryEntity()));

		var result = incidentService.createIncident(request);

		assertThat(result).isNotNull().isInstanceOf(IncidentSaveResponse.class);
		verify(mockCategoryRepository).findById(any());
		verify(mockIncidentRepository).save(any());
		verifyNoMoreInteractions(mockCategoryRepository);
	}

	@Test
	void createIncidentNotFoundTest() {
		var request = createIncidentSaveRequest();
		when(mockCategoryRepository.findById(anyInt())).thenReturn(Optional.empty());

		assertThatThrownBy(() -> incidentService.createIncident(request))
			.isInstanceOf(Problem.class)
			.hasMessageContaining("Bad Request: Category with id: ")
			.extracting("status").isEqualTo(BAD_REQUEST);

		verify(mockCategoryRepository).findById(request.getCategory());
		verify(mockIncidentRepository, never()).save(any());
	}

	@Test
	void updateIncidentStatusTest() {
		var entity = createIncidentEntity();
		when(mockIncidentRepository.findById(any())).thenReturn(Optional.ofNullable(entity));

		incidentService.updateIncidentStatus(entity.getIncidentId(), 7);

		assertThat(entity.getStatus()).isEqualTo(Status.ARKIVERAD);
		verify(mockIncidentRepository).findById(entity.getIncidentId());
		verify(mockIncidentRepository).save(entity);
		verifyNoMoreInteractions(mockIncidentRepository);
	}

	@Test
	void updateIncidentStatusNotFoundTest() {
		var entity = createIncidentEntity();
		when(mockIncidentRepository.findById(any())).thenReturn(Optional.empty());

		assertThatThrownBy(() -> incidentService.updateIncidentStatus(entity.getIncidentId(), anyInt()))
			.isInstanceOf(Problem.class)
			.hasMessageContaining("Not Found: Incident with id: ");

		assertThat(entity.getStatus()).isEqualTo(Status.INSKICKAT);
		verify(mockIncidentRepository).findById(entity.getIncidentId());
		verify(mockIncidentRepository, never()).save(entity);
	}

	@Test
	void updateIncidentFeedbackTest() {
		var entity = createIncidentEntity();
		when(mockIncidentRepository.findById(any())).thenReturn(Optional.ofNullable(entity));

		incidentService.updateIncidentFeedback(entity.getIncidentId(), "Feedback!!");

		assertThat(entity.getFeedback()).isEqualTo("Feedback!!");
		verify(mockIncidentRepository).findById(entity.getIncidentId());
		verify(mockIncidentRepository).save(entity);
		verifyNoMoreInteractions(mockIncidentRepository);
	}

	@Test
	void updateIncidentFeedbackNotFoundTest() {
		var entity = createIncidentEntity();
		when(mockIncidentRepository.findById(any())).thenReturn(Optional.empty());

		assertThatThrownBy(() -> incidentService.updateIncidentFeedback(entity.getIncidentId(), anyString()))
			.isInstanceOf(Problem.class)
			.hasMessageContaining("Not Found: Incident with id: ");

		verify(mockIncidentRepository).findById(entity.getIncidentId());
		verify(mockIncidentRepository, never()).save(entity);
	}

	@ParameterizedTest
	@ValueSource(strings = {"LIVBAT", "LIVBOJ"})
	void sendNotification_Lifebuoy_Test() throws JsonProcessingException {
		when(mockLifeBuoyIntegration.sendLifeBuoy(any())).thenReturn("nothing");
		var entity = createIncidentEntity();
		var category = createCategoryEntity();
		category.setTitle("LIVBAT");
		entity.setCategory(category);

		incidentService.sendNotification(entity);

		verify(mockLifeBuoyIntegration).sendLifeBuoy(any());
	}

	@ParameterizedTest
	@ValueSource(strings = {"VATTENMATARE", "BRADD_OVERVAKNINGS_LARM"})
	void sendNotificationTest(final String categoryTitle) {
		when(mockMessagingIntegration.sendMSVAEmail(any())).thenReturn(Optional.of(new MessageResult()));
		var entity = createIncidentEntity();
		var category = createCategoryEntity();
		category.setTitle(categoryTitle);
		entity.setCategory(category);

		incidentService.sendNotification(entity);

		verify(mockMessagingIntegration).sendMSVAEmail(entity);
	}

	@Test
	void sendNotification_WhenThrows_Test() throws JsonProcessingException {
		var entity = createIncidentEntity();
		var category = createCategoryEntity();
		category.setTitle("LIVBOJ");
		entity.setCategory(category);
		doThrow(new RuntimeException()).when(mockLifeBuoyIntegration).sendLifeBuoy(any());

		incidentService.sendNotification(entity);

		assertThat(entity.getStatus()).isEqualTo(Status.ERROR);
		verify(mockLifeBuoyIntegration).sendLifeBuoy(any());
	}

}
