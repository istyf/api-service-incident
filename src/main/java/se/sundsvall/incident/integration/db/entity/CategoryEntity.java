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
import lombok.Setter;

@Entity
@Table(name = "categories")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder(setterPrefix = "with")
@Getter
@Setter
public class CategoryEntity {

	@Id
	@GeneratedValue
	@Column(name = "category_id")
	private Integer categoryId;

	@Column(name = "title", unique = true)
	private String title;

	@Column(name = "label")
	private String label;

	@Column(name = "forward_to")
	private String forwardTo;

}
