package se.sundsvall.incident.integration.lifebuoy;

import static se.sundsvall.incident.integration.lifebuoy.LifeBuoyIntegration.INTEGRATION_NAME;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = INTEGRATION_NAME,
        url = "${integration.lifebuoy.url}",
        configuration = LifeBuoyClientConfiguration.class
)
interface LifeBuoyClient {

    @PostMapping(consumes = "application/x-www-form-urlencoded")
    ResponseEntity<String> sendLifebuoy(@RequestBody String request);
}