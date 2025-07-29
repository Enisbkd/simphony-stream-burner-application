package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.FamilyGroupCnC;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FamilyGroupCnC entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FamilyGroupCnCRepository extends JpaRepository<FamilyGroupCnC, Long> {}
