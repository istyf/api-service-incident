package se.sundsvall.incident.integration.db;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se.sundsvall.incident.integration.db.entity.IncidentEntity;

@Repository
public interface IncidentRepository extends JpaRepository<IncidentEntity, String> {

	Optional<IncidentEntity> findIncidentEntityByExternalCaseId(final String externalCaseId);

}
