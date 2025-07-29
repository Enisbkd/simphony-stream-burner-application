package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.ModePaiementTrans;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ModePaiementTrans entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModePaiementTransRepository extends JpaRepository<ModePaiementTrans, Integer> {}
