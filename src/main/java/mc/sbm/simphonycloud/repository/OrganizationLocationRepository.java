package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.OrganizationLocation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrganizationLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizationLocationRepository extends JpaRepository<OrganizationLocation, Long> {}
