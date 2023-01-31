package se.sundsvall.incident.integration.lifebuoy;

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

    public String sendLifeBuoy(IncidentDto dto) {
        return client.sendLifebuoy(mapper.toLifeBuoyyRequest(dto)).getBody();

    }
}
