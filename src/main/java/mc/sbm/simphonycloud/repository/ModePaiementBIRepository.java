package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.ModePaiementBI;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ModePaiementBI entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModePaiementBIRepository extends JpaRepository<ModePaiementBI, Long> {}
