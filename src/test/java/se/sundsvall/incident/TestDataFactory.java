package se.sundsvall.incident;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import se.sundsvall.incident.api.model.Attachment;
import se.sundsvall.incident.api.model.AttachmentRequest;
import se.sundsvall.incident.api.model.Category;
import se.sundsvall.incident.api.model.CategoryPatch;
import se.sundsvall.incident.api.model.CategoryPost;
import se.sundsvall.incident.api.model.IncidentOepResponse;
import se.sundsvall.incident.api.model.IncidentResponse;
import se.sundsvall.incident.api.model.IncidentSaveRequest;
import se.sundsvall.incident.api.model.IncidentSaveResponse;
import se.sundsvall.incident.api.model.ValidCategoryResponse;
import se.sundsvall.incident.api.model.ValidOepCategoryResponse;
import se.sundsvall.incident.integration.db.entity.AttachmentEntity;
import se.sundsvall.incident.integration.db.entity.CategoryEntity;
import se.sundsvall.incident.integration.db.entity.IncidentEntity;
import se.sundsvall.incident.integration.db.entity.enums.Status;

import generated.se.sundsvall.messaging.EmailAttachment;
import generated.se.sundsvall.messaging.EmailRequest;
import generated.se.sundsvall.messaging.EmailSender;

public final class TestDataFactory {

	public static final String INCIDENT_ID = "ef60e3e-d245-4d79-b350-fbabc022b249";
	public static final String PERSON_ID = "58f96da8-6d76-4fa6-bb92-64f71fdc6aa5";

	public static IncidentEntity createIncidentEntity() {
		return IncidentEntity.builder()
			.withIncidentId(INCIDENT_ID)
			.withExternalCaseId("12345")
			.withPersonId(PERSON_ID)
			.withStatus(Status.ARKIVERAD)
			.withPhoneNumber("0701234567")
			.withEmail("mail@mail.se")
			.withContactMethod("email")
			.withFeedback("test")
			.withStatus(Status.INSKICKAT)
			.withDescription("Ärendebeskrivning")
			.withCoordinates("62.23162,17.27403")
			.withDescription("asdas - asdasd")
			.withCategory(createCategoryEntity())
			.withAttachments(List.of(createAttachmentEntity(), createAttachmentEntity()))
			.withCreated(LocalDateTime.now())
			.withUpdated(LocalDateTime.now())
			.build();
	}

	public static IncidentResponse createIncidentResponse() {
		return IncidentResponse.builder()
			.withIncidentId(INCIDENT_ID)
			.withExternalCaseId("12345")
			.withPersonId(PERSON_ID)
			.withStatus(Status.INSKICKAT)
			.withPhoneNumber("0701234567")
			.withEmail("email@email.se")
			.withContactMethod("email")
			.withDescription("Beskrivning")
			.withCategory(createCategoryDTO())
			.withAttachments(List.of(createAttachment(), createAttachment()))
			.withCreated(LocalDateTime.now())
			.withUpdated(LocalDateTime.now())
			.build();
	}

	public static IncidentSaveResponse createIncidentSaveResponse() {
		return IncidentSaveResponse.builder()
			.withStatus(String.valueOf(Status.INSKICKAT))
			.withIncidentId(UUID.randomUUID().toString())
			.build();
	}

	public static ValidCategoryResponse createValidCategoryResponse() {
		var category = createCategoryEntity();
		return ValidCategoryResponse.builder()
			.withCategoryId(category.getCategoryId())
			.withCategory(category.getLabel())
			.build();
	}

	public static ValidOepCategoryResponse createValidOepCategoryResponse() {
		var category = createCategoryEntity();
		return ValidOepCategoryResponse.builder()
			.withKey(String.valueOf(category.getCategoryId()))
			.withValue(category.getLabel())
			.build();
	}

	public static IncidentOepResponse createIncidentOepResponse() {
		return IncidentOepResponse.builder()
			.withIncidentId(INCIDENT_ID)
			.withExternalCaseId(UUID.randomUUID().toString())
			.withStatusId(5)
			.withStatusText("status text")
			.build();
	}

	public static IncidentSaveRequest createIncidentSaveRequest() {
		return createIncidentSaveRequest(null);
	}

	public static IncidentSaveRequest createIncidentSaveRequest(final Consumer<IncidentSaveRequest> modifier) {
		final var request = IncidentSaveRequest.builder()
			.withExternalCaseId("12345")
			.withPersonId(PERSON_ID)
			.withPhoneNumber("0701234567")
			.withEmail("mail@mail.se")
			.withContactMethod("email")
			.withCategory(3)
			.withDescription("Ärendebeskrivning")
			.withMapCoordinates("62.23162,17.27403")
			.withExternalCaseId("123")
			.withAttachments(
				List.of(createAttachmentRequest()))
			.build();

		if (modifier != null) {
			modifier.accept(request);
		}

		return request;
	}

	public static AttachmentRequest createAttachmentRequest() {
		return createAttachmentRequest(null);
	}

	public static AttachmentRequest createAttachmentRequest(final Consumer<AttachmentRequest> modifier) {
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

	public static AttachmentEntity createAttachmentEntity() {
		return AttachmentEntity.builder()
			.withId(1)
			.withName("someName")
			.withMimeType("someMimeType")
			.withNote("someNote")
			.withExtension("SomeExtension")
			.withFile("someFile")
			.withCategory("someCategory")
			.build();
	}

	public static Attachment createAttachment() {
		return Attachment.builder()
			.withName("someName")
			.withMimeType("someMimeType")
			.withNote("someNote")
			.withExtension("SomeExtension")
			.withFile("someFile")
			.withCategory("someCategory")
			.build();
	}

	public static EmailRequest createMSVAEmailRequest() {
		return new EmailRequest("some@email.se", "Somesubject")
			.sender(new EmailSender("somename", "some@email.se"))
			.htmlMessage("<p>Vattenmätare " + "splitDescription[0]" + " genererat larm" + "splitDescription[1]" + " klockan " + LocalDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "</p>");
	}

	public static EmailRequest createEmailRequest() {
		return new EmailRequest("some@email.se", "Det har inkommit en felanmälan.")
			.sender(new EmailSender("somename", "some@email.se"))
			.htmlMessage("some html message")
			.attachments(List.of(new EmailAttachment("someName", "SomeContent")
				.contentType("someContentType")));
	}

	public static CategoryPost createCategoryPost() {
		return new CategoryPost("Title", "Label", "ForwardTo", "Subject");
	}

	public static CategoryPatch createCategoryPatch() {
		return new CategoryPatch("New title", null, null);
	}

	public static Category createCategoryDTO() {
		return Category.builder()
			.withForwardTo("someone@email.com")
			.withTitle("This-is-a-title")
			.withLabel("This-is-a-label")
			.build();
	}

	public static CategoryEntity createCategoryEntity() {
		return createCategoryEntity(null);
	}

	public static CategoryEntity createCategoryEntity(final Consumer<CategoryEntity> modifier) {
		var entity = CategoryEntity.builder()
			.withCategoryId(5)
			.withLabel("this is a label")
			.withTitle("this is a title")
			.withForwardTo("nowhere@nowhere.com")
			.withSubject("this is a subject")
			.build();

		if (modifier != null) {
			modifier.accept(entity);
		}
		return entity;
	}

}
