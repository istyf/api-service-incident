package se.sundsvall.incident.integration.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import se.sundsvall.incident.integration.db.entity.AttachmentEntity;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@CircuitBreaker(name = "attachmentRepository")
public interface AttachmentRepository extends JpaRepository<AttachmentEntity, Integer> {

	List<AttachmentEntity> findAllByIncidentId(final String incidentId);
}
