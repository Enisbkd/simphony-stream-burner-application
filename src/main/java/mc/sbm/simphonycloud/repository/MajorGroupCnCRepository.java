package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.MajorGroupCnC;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MajorGroupCnC entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MajorGroupCnCRepository extends JpaRepository<MajorGroupCnC, Long> {}
