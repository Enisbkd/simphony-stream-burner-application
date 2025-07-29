package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.CommissionServiceBI;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CommissionServiceBI entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommissionServiceBIRepository extends JpaRepository<CommissionServiceBI, Integer> {}
