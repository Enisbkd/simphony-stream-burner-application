package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.PointDeVenteCnC;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PointDeVenteCnC entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PointDeVenteCnCRepository extends JpaRepository<PointDeVenteCnC, Long> {}
