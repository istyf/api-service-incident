package se.sundsvall.incident;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import generated.se.sundsvall.messaging.EmailAttachment;
import generated.se.sundsvall.messaging.EmailRequest;
import generated.se.sundsvall.messaging.EmailSender;
import se.sundsvall.incident.api.model.AttachmentRequest;
import se.sundsvall.incident.api.model.IncidentSaveRequest;
import se.sundsvall.incident.dto.AttachmentDto;
import se.sundsvall.incident.dto.Category;
import se.sundsvall.incident.dto.IncidentDto;
import se.sundsvall.incident.dto.Status;
import se.sundsvall.incident.integration.db.entity.AttachmentEntity;
import se.sundsvall.incident.integration.db.entity.IncidentEntity;

public final class TestDataFactory {
	public static final String INCIDENTID = "ef60e3e-d245-4d79-b350-fbabc022b249";
	public static final String PERSONID = "58f96da8-6d76-4fa6-bb92-64f71fdc6aa5";

	public static IncidentDto buildIncidentDto() {
		return IncidentDto.builder()
			.withIncidentId(INCIDENTID)
			.withExternalCaseId("12345")
			.withPersonId(PERSONID)
			.withCreated("2021-06-17T23:04:11.000Z")
			.withUpdated("2022-02-13T09:13:45.000Z")
			.withPhoneNumber("0701234567")
			.withEmail("mail@mail.se")
			.withContactMethod("email")
			.withCategory(1)
			.withFeedback("test")
			.withDescription("Ärendebeskrivning")
			.withMapCoordinates("62.23162,17.27403")
			.withDescription("asdas - asdasd")
			.withStatusText(Status.INSKICKAT.getLabel())
			.withStatusId(Status.INSKICKAT.getValue())
			.withAttachments(buildAttachmentDtoList(2))
			.build();
	}

	public static IncidentSaveRequest buildIncidentSaveRequest(Category category) {
		return buildIncidentSaveRequest(category, null);
	}

	public static IncidentSaveRequest buildIncidentSaveRequest(Category category, Consumer<IncidentSaveRequest> modifier) {
		final var request = IncidentSaveRequest.builder()
			.withExternalCaseId("12345")
			.withPersonId(PERSONID)
			.withPhoneNumber("0701234567")
			.withEmail("mail@mail.se")
			.withContactMethod("email")
			.withCategory(category.getValue())
			.withDescription("Ärendebeskrivning")
			.withMapCoordinates("62.23162,17.27403")
			.withExternalCaseId("123")
			.withAttachments(
				List.of(buildAttachmentRequest()))
			.build();

		if (modifier != null) {
			modifier.accept(request);
		}

		return request;
	}

	public static AttachmentRequest buildAttachmentRequest() {
		return buildAttachmentRequest(null);
	}

	public static AttachmentRequest buildAttachmentRequest(Consumer<AttachmentRequest> modifier) {
		final var request = AttachmentRequest.builder()
			.withMimeType("text/plain")
			.withNote("Some note")
			.withExtension(".txt")
			.withFile("Zmlsc29tIGJhc2U2NA==")
			.withCategory("ADRESS")
			.build();

		if (modifier != null) {
			modifier.accept(request);
		}
		return request;
	}

	public static List<IncidentDto> buildListOfIncidentDto() {
		return List.of(buildIncidentDto(), IncidentDto
			.builder()
			.withStatusText(Status.KLART.getLabel())
			.withExternalCaseId("123")
			.withIncidentId(INCIDENTID)
			.build());
	}

	public static IncidentEntity buildIncidentEntity(Category category) {
		return IncidentEntity.builder()
			.withIncidentId(INCIDENTID)
			.withExternalCaseId("12345")
			.withPersonID(PERSONID)
			.withCreated("2021-06-17T23:04:11.000Z")
			.withUpdated("2022-02-13T09:13:45.000Z")
			.withPhoneNumber("0701234567")
			.withEmail("mail@mail.se")
			.withContactMethod("email")
			.withCategory(category)
			.withFeedback("test")
			.withDescription("Ärendebeskrivning")
			.withMapCoordinates("62.23162,17.27403")
			.withDescription("asdas - asdasd")
			.withStatus(Status.ARKIVERAD)
			.build();

	}

	public static List<IncidentEntity> buildListIncidentEntities() {
		return List.of(buildIncidentEntity(Category.BELYSNING), buildIncidentEntity(Category.BELYSNING), buildIncidentEntity(Category.BRO_TUNNEL_KONSTRUKTION), buildIncidentEntity(Category.FELPARKERAD_BIL));
	}

	public static AttachmentEntity buildAttachmentEntity() {
		return AttachmentEntity.builder()
			.withId(1)
			.withName("someName")
			.withMimeType("someMimeType")
			.withNote("someNote")
			.withExtension("SomeExtension")
			.withFile("someFile")
			.withCategory("someCategory")
			.withIncidentId(INCIDENTID)
			.build();
	}

	public static List<AttachmentEntity> buildAttachmentEntityList(Integer count) {
		final List<AttachmentEntity> attachmentEntities = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			attachmentEntities.add(buildAttachmentEntity());
		}
		return attachmentEntities;
	}

	public static AttachmentDto buildAttachmentDto() {
		return AttachmentDto.builder()
			.withName("someName")
			.withMimeType("someMimeType")
			.withNote("someNote")
			.withExtension("SomeExtension")
			.withFile("someFile")
			.withCategory("someCategory")
			.withIncidentId(INCIDENTID)
			.build();
	}

	public static List<AttachmentDto> buildAttachmentDtoList(Integer count) {
		final List<AttachmentDto> attachmentEntities = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			attachmentEntities.add(buildAttachmentDto());
		}
		return attachmentEntities;
	}

	public static EmailRequest buildMSVAEmailRequest() {
		return new EmailRequest("some@email.se", "Somesubject")
			.sender(new EmailSender("somename", "some@email.se"))
			.htmlMessage("<p>Vattenmätare " + "splitDescription[0]" + " genererat larm" + "splitDescription[1]" + " klockan " + LocalDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "</p>");
	}

	public static EmailRequest buildEmailRequest() {
		return new EmailRequest("some@email.se", "Det har inkommit en felanmälan.")
			.sender(new EmailSender("somename", "some@email.se"))
			.htmlMessage("some html message")
			.attachments(List.of(new EmailAttachment("someName", "SomeContent")
				.contentType("someContentType")));
	}
}
