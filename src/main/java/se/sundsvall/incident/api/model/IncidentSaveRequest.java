package se.sundsvall.incident.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import se.sundsvall.dept44.common.validators.annotation.ValidMobileNumber;
import se.sundsvall.incident.api.model.validation.ValidCoords;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@SuperBuilder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class IncidentSaveRequest {
    @Schema(description = "A uuid string representing a person", example = "58f96da8-6d76-4fa6-bb92-64f71fdc6aa5", required = true)
    private String personId;
    @Schema(description = "Mobile number. Should start with 07x", example = "0701234578")
    @ValidMobileNumber(nullable = true)
    private String phoneNumber;
    @Email
    @Schema(description = "Reporters e-mail address", example = "someemail@somehost.se")
    private String email;
    @Schema(description = "The way the reporter want to be contacted", example = "email")
    private String contactMethod;
    @NotNull
    @Schema(description = "The category id for the incident ", example = "15", required = true)
    private Integer category;
    @NotBlank
    @Schema(description = "Description of the incident", example = "A description", required = true)
    private String description;
    @ValidCoords
    @Schema(description = "The map coordinates for the incident", example = "62.23162,17.27403")
    private String mapCoordinates;
    @Schema(description = "The external case id for this incident", example = "1234")
    private String externalCaseId;
    @Schema(description = "Attachments")
    private List<AttachmentRequest> attachments;
}
