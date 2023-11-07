package se.sundsvall.incident.integration.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "category", uniqueConstraints = {
	@UniqueConstraint(name = "UK_category_title", columnNames = "title"),
	@UniqueConstraint(name = "UK_category_label", columnNames = "label")})
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder(setterPrefix = "with")
@Getter
@Setter
public class CategoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private Integer categoryId;

	@Column(name = "title")
	private String title;

	@Column(name = "label")
	private String label;

	@Column(name = "forward_to")
	private String forwardTo;

	@Column(name = "subject")
	private String subject;

}
