package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.CommissionServiceTrans;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CommissionServiceTrans entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommissionServiceTransRepository extends JpaRepository<CommissionServiceTrans, Long> {}
