package se.sundsvall.incident.integration.lifebuoy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import se.sundsvall.incident.dto.IncidentDto;

@Component
public class LifeBuoyIntegration {

    private final LifeBuoyClient client;
    private final LifeBuoyMapper mapper;
    static final String INTEGRATION_NAME = "LifeBuoyClient";


    public LifeBuoyIntegration(LifeBuoyClient client, LifeBuoyMapper mapper) {
        this.client = client;
        this.mapper = mapper;
    }

    public String sendLifeBuoy(IncidentDto dto) throws JsonProcessingException {
        var objectmapper = new ObjectMapper();
            return client.sendLifebuoy(objectmapper.writeValueAsString(mapper.toLifeBuoyyRequest(dto))).getBody();
    }
}
