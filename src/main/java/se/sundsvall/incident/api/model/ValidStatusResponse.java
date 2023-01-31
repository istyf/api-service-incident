package se.sundsvall.incident.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with", builderClassName = "Builder")
@JsonDeserialize(builder = ValidStatusResponse.Builder.class)
@AllArgsConstructor
public class ValidStatusResponse {
    private Integer STATUS_ID;
    private String status;
}
