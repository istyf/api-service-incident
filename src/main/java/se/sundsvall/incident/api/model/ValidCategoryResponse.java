package se.sundsvall.incident.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with", builderClassName = "Builder")
@JsonDeserialize(builder = ValidCategoryResponse.Builder.class)
@AllArgsConstructor
public class ValidCategoryResponse {
    private String category;
    private Integer CATEGORY_ID;
}
