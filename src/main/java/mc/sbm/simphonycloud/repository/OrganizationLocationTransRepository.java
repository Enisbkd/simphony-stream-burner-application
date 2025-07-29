package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.OrganizationLocationTrans;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrganizationLocationTrans entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizationLocationTransRepository extends JpaRepository<OrganizationLocationTrans, Long> {}
