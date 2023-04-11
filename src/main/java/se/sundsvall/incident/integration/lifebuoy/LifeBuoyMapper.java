package se.sundsvall.incident.integration.lifebuoy;

import java.util.Arrays;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import se.sundsvall.incident.dto.Category;
import se.sundsvall.incident.dto.IncidentDto;
import se.sundsvall.incident.integration.lifebuoy.model.LifeBuoyRequestWrapper;
import se.sundsvall.incident.integration.lifebuoy.model.LifebuoyRequest;

@Component
@EnableConfigurationProperties(LifeBuoyProperties.class)
class LifeBuoyMapper {
    
    private final LifeBuoyProperties properties;
    
    LifeBuoyMapper(LifeBuoyProperties lifeBuoyProperties) {
        this.properties = lifeBuoyProperties;
    }
    
    LifeBuoyRequestWrapper toLifeBuoyyRequest(IncidentDto dto) throws JsonProcessingException {
        var objectmapper = new ObjectMapper();
        return LifeBuoyRequestWrapper.builder()
            .withApiKey(properties.getApikey())
            .withErrandJsonString(objectmapper.writeValueAsString(LifebuoyRequest.builder()
                .withValue(LifebuoyRequest.TypeAndValue.Type.Property, "source", dto.getIncidentId())
                .withValue(LifebuoyRequest.TypeAndValue.Type.Property, "personalnumber", "NOT MAPPED")
                .withValue(LifebuoyRequest.TypeAndValue.Type.Property, "name", "Errand Sundsvall")
                .withValue(LifebuoyRequest.TypeAndValue.Type.Property, "address", "Errand Sundsvall")
                .withValue(LifebuoyRequest.TypeAndValue.Type.Property, "phonenumber", dto.getPhoneNumber())
                .withValue(LifebuoyRequest.TypeAndValue.Type.Property, "contactmethod", dto.getContactMethod())
                .withValue(LifebuoyRequest.TypeAndValue.Type.Property, "email", dto.getEmail())
                .withValue(LifebuoyRequest.TypeAndValue.Type.Property, "category", Category.forValue(dto.getCategory()).getLabel())
                .withValue(LifebuoyRequest.TypeAndValue.Type.Property, "description", dto.getDescription())
                .withValue(LifebuoyRequest.TypeAndValue.Type.GeoProperty, "location", new LifebuoyRequest.Location(Arrays.stream(dto.getMapCoordinates().split(",")).mapToDouble(Double::parseDouble).toArray()))
                .build()))
            .build();
        
    }
}
