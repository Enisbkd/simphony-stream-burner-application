package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.Hierarchie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Hierarchie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HierarchieRepository extends JpaRepository<Hierarchie, Long> {}
