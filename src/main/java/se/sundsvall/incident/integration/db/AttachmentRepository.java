package se.sundsvall.incident.integration.db;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import se.sundsvall.incident.integration.db.entity.AttachmentEntity;

public interface AttachmentRepository extends JpaRepository<AttachmentEntity, Integer> {

    List<AttachmentEntity> findAllByIncidentId(String incidentid);
}
