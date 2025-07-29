package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.HierarchieCnC;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HierarchieCnC entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HierarchieCnCRepository extends JpaRepository<HierarchieCnC, Integer> {}
