package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.LocationCnC;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LocationCnC entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationCnCRepository extends JpaRepository<LocationCnC, Integer> {}
