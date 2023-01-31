package se.sundsvall.incident.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with", builderClassName = "Builder")
@JsonDeserialize(builder = ValidOepCategoryReponse.Builder.class)
@AllArgsConstructor
public class ValidOepCategoryReponse {
    private String key;
    private String value;
}
