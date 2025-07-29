package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.RemiseTrans;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RemiseTrans entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RemiseTransRepository extends JpaRepository<RemiseTrans, Long> {}
