package se.sundsvall.incident.integration.db.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import se.sundsvall.incident.dto.Category;
import se.sundsvall.incident.dto.Status;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "incident")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder(setterPrefix = "with")
@Getter
@Setter
public class IncidentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "incident_id")
	private String incidentId;

	@Column(name = "external_case_id")
	private String externalCaseId;

	@Column(name = "person_id")
	private String personId;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "email")
	private String email;

	@Column(name = "contact_method")
	private String contactMethod;

	@Column(name = "description")
	private String description;

	@Column(name = "coordinates")
	private String coordinates;

	@Column(name = "feedback")
	private String feedback;

	@Enumerated(EnumType.STRING)
	@Column(name = "category")
	private Category category;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;

	@Column(name = "updated")
	private LocalDateTime updated;

	@Column(name = "created")
	private LocalDateTime created;

	@PrePersist
	void prePersist() {
		var now = LocalDateTime.now();
		this.updated = now;
		this.created = now;
	}

	@PreUpdate
	void preUpdate() {
		this.updated = LocalDateTime.now();
	}

}
