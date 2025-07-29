package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.PointDeVenteTrans;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PointDeVenteTrans entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PointDeVenteTransRepository extends JpaRepository<PointDeVenteTrans, Long> {}
