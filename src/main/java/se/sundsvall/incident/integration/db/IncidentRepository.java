package se.sundsvall.incident.integration.db;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import se.sundsvall.incident.integration.db.entity.IncidentEntity;

public interface IncidentRepository extends JpaRepository<IncidentEntity, String> {

    Optional<IncidentEntity> findIncidentEntityByExternalCaseId(String externalCaseid);

}
