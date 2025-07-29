package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.CheckTrans;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CheckTrans entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckTransRepository extends JpaRepository<CheckTrans, Integer> {}
