package se.sundsvall.incident.integration.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Attachments")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder(setterPrefix = "with")
@Getter
public class AttachmentEntity {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;

	@Column(name = "IncidentId")
	private String incidentId;

	@Column(name = "category")
	private String category;

	@Column(name = "extension")
	private String extension;

	@Column(name = "mimetype")
	private String mimeType;

	@Column(name = "note")
	private String note;

	@Column(name = "file")
	private String file;

	@Column(name = "name")
	private String name;

	@Column(name = "Created")
	private String created;
}
