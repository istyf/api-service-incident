package se.sundsvall.incident.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import se.sundsvall.incident.api.model.AttachmentResponse;
import se.sundsvall.incident.api.model.IncidentListResponse;
import se.sundsvall.incident.api.model.IncidentOepResponse;
import se.sundsvall.incident.api.model.IncidentResponse;
import se.sundsvall.incident.api.model.IncidentSaveRequest;
import se.sundsvall.incident.api.model.IncidentSaveResponse;
import se.sundsvall.incident.service.IncidentService;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Incident resources")
@RestController
@RequestMapping("/api")
public class IncidentResource {

    private final IncidentService incidentService;

    IncidentResource(IncidentService incidentService) {
        this.incidentService = incidentService;
    }


    @Operation(summary = "Get status for a OeP incident")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful Operation",
                    content = @Content(schema = @Schema(implementation = IncidentOepResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = Problem.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = @Content(schema = @Schema(implementation = Problem.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = Problem.class))
            )
    })
    @GetMapping("/internal/oep/status/{externalCaseId}")
    ResponseEntity<IncidentOepResponse> getStatusForOeP(@PathVariable(name = "externalCaseId") String externalCaseId) {
        return
                incidentService.getOepIncidentstatus(externalCaseId)
                        .map(oepIncidentDto -> IncidentOepResponse.builder()
                                .withIncidentId(oepIncidentDto.getIncidentId())
                                .withStatusId(oepIncidentDto.getStatusId())
                                .withStatusText(oepIncidentDto.getStatusText())
                                .withExternalCaseId(oepIncidentDto.getExternalCaseId())
                                .build())
                        .map(ResponseEntity::ok)
                        .orElseThrow(() -> Problem.valueOf(Status.NOT_FOUND));
    }

    @Operation(summary = "Send a single Incident")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful Operation",
                    content = @Content(schema = @Schema(implementation = IncidentSaveResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = Problem.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = Problem.class))
            )
    })
    @PostMapping("/sendincident")
    ResponseEntity<IncidentSaveResponse> sendIncident(@Valid @RequestBody IncidentSaveRequest incidentSaveRequest) {
        var incident = incidentService.sendIncident(incidentSaveRequest);
        return ResponseEntity.ok(IncidentSaveResponse.builder()
                .withIncidentId(incident.getIncidentId())
                .withStatus("OK")
                .build());
    }

    @Operation(summary = "Get a single incident")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful Operation",
                    content = @Content(schema = @Schema(implementation = IncidentResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = Problem.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = @Content(schema = @Schema(implementation = Problem.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = Problem.class))
            )
    })
    @GetMapping("/getincident/{id}")
    ResponseEntity<IncidentResponse> getIncident(@PathVariable(name = "id") String incidentId) {
        return incidentService.getIncident(incidentId)
                .map(incidentDto -> IncidentResponse.builder()
                        .withIncidentId(incidentId)
                        .withExternalCaseId(incidentDto.getExternalCaseId())
                        .withPersonId(incidentDto.getPersonId())
                        .withCreated(incidentDto.getCreated())
                        .withUpdated(incidentDto.getUpdated())
                        .withPhoneNumber(incidentDto.getPhoneNumber())
                        .withEmail(incidentDto.getEmail())
                        .withContactMethod(incidentDto.getContactMethod())
                        .withCategory(incidentDto.getCategory())
                        .withDescription(incidentDto.getDescription())
                        .withStatus(incidentDto.getStatusText())
                        .withAttachments(incidentDto.getAttachments().stream()
                                .map(attachmentDto -> AttachmentResponse.builder()
                                        .withName(attachmentDto.getName())
                                        .withMimeType(attachmentDto.getMimeType())
                                        .withNote(attachmentDto.getNote())
                                        .withExtension(attachmentDto.getExtension())
                                        .withFile(attachmentDto.getFile())
                                        .withCategory(attachmentDto.getCategory())
                                        .withIncidentId(incidentId).build())
                                .toList())
                        .build())
                .map(ResponseEntity::ok)
                .orElseThrow(() -> Problem.valueOf(Status.NOT_FOUND));

    }

    @Operation(summary = "Get list of incidents")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful Operation",
                    content = @Content(schema = @Schema(implementation = IncidentListResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = Problem.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = Problem.class))
            )
    })
    @GetMapping("/listincidents")
    ResponseEntity<List<IncidentListResponse>> getAllIncidents(@RequestParam(required = false) int offset, @RequestParam(required = false) int limit) {
        return ResponseEntity.ok(incidentService.getIncidents(offset, limit).stream()
                .map(dto -> IncidentListResponse.builder()
                        .withIncidentId(dto.getIncidentId())
                        .withExternalCaseId(dto.getExternalCaseId())
                        .withStatus(dto.getStatusText())
                        .build())
                .toList());
    }

    @Operation(summary = "Update the status for a specific incident")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful Operation",
                    content = @Content(schema = @Schema())
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = Problem.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = Problem.class))
            )
    })
    @PatchMapping("/statusupdate")
    ResponseEntity<Void> updateIncidentStatus(@RequestParam(name = "id") String incidentId, @RequestParam(name = "status") Integer statusid) {
        incidentService.updateIncidentStatus(incidentId, statusid);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Set type of errand")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful Operation",
                    content = @Content(schema = @Schema())
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = Problem.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = Problem.class))
            )
    })
    @GetMapping("/setincidentfeedback")
    ResponseEntity<Void> setIncidentFeedback(@RequestParam(name = "errandid") String incidentId, @RequestParam(name = "feedback") String feedback) {
        incidentService.updateIncidentFeedback(incidentId, feedback);
        return ResponseEntity.ok().build();
    }
}


