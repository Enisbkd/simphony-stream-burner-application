package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.RemiseBI;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RemiseBI entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RemiseBIRepository extends JpaRepository<RemiseBI, Integer> {}
