package se.sundsvall.incident.integration.lifebuoy.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(setterPrefix = "with")
public class LifeBuoyRequestWrapper {
    String apiKey;
    String errandJsonString;
}
