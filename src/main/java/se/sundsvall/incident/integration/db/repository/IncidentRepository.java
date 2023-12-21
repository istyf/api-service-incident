package se.sundsvall.incident.integration.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import se.sundsvall.incident.integration.db.entity.IncidentEntity;

@CircuitBreaker(name = "incidentRepository")
public interface IncidentRepository extends JpaRepository<IncidentEntity, String> {

	Optional<IncidentEntity> findIncidentEntityByExternalCaseId(final String externalCaseId);
}
