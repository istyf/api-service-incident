package se.sundsvall.incident.integration.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se.sundsvall.incident.integration.db.entity.AttachmentEntity;

@Repository
public interface AttachmentRepository extends JpaRepository<AttachmentEntity, Integer> {

	List<AttachmentEntity> findAllByIncidentId(final String incidentId);
}
