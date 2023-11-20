package se.sundsvall.incident.integration.lifebuoy;

import java.util.Arrays;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import se.sundsvall.incident.integration.db.entity.IncidentEntity;
import se.sundsvall.incident.integration.lifebuoy.configuration.LifeBuoyProperties;
import se.sundsvall.incident.integration.lifebuoy.model.LifeBuoyRequestWrapper;
import se.sundsvall.incident.integration.lifebuoy.model.LifebuoyRequest;

@Component
@EnableConfigurationProperties(LifeBuoyProperties.class)
class LifeBuoyMapper {

	private final ObjectMapper objectMapper;
	private final LifeBuoyProperties properties;

	LifeBuoyMapper(ObjectMapper objectMapper, LifeBuoyProperties lifeBuoyProperties) {
		this.objectMapper = objectMapper;
		this.properties = lifeBuoyProperties;
	}

	LifeBuoyRequestWrapper toLifeBuoyRequest(IncidentEntity incident) throws JsonProcessingException {
		return LifeBuoyRequestWrapper.builder()
			.withApiKey(properties.apiKey())
			.withErrandJsonString(objectMapper.writeValueAsString(LifebuoyRequest.builder()
				.withValue(LifebuoyRequest.TypeAndValue.Type.Property, "source", incident.getIncidentId())
				.withValue(LifebuoyRequest.TypeAndValue.Type.Property, "personalnumber", "NOT MAPPED")
				.withValue(LifebuoyRequest.TypeAndValue.Type.Property, "name", "Errand Sundsvall")
				.withValue(LifebuoyRequest.TypeAndValue.Type.Property, "address", "Errand Sundsvall")
				.withValue(LifebuoyRequest.TypeAndValue.Type.Property, "phonenumber", incident.getPhoneNumber())
				.withValue(LifebuoyRequest.TypeAndValue.Type.Property, "contactmethod", incident.getContactMethod())
				.withValue(LifebuoyRequest.TypeAndValue.Type.Property, "email", incident.getEmail())
				.withValue(LifebuoyRequest.TypeAndValue.Type.Property, "category", incident.getCategory().getCategoryId())
				.withValue(LifebuoyRequest.TypeAndValue.Type.Property, "description", incident.getDescription())
				.withValue(LifebuoyRequest.TypeAndValue.Type.GeoProperty, "location", new LifebuoyRequest.Location(Arrays.stream(incident.getCoordinates().split(",")).mapToDouble(Double::parseDouble).toArray()))
				.build()))
			.build();

	}
}
