package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.EmployeeCnC;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeCnC entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeCnCRepository extends JpaRepository<EmployeeCnC, Integer> {}
