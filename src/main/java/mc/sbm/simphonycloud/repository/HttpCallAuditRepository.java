package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.HttpCallAudit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HttpCallAudit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HttpCallAuditRepository extends JpaRepository<HttpCallAudit, Long> {}
