package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.GuestCheckBI;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GuestCheckBI entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GuestCheckBIRepository extends JpaRepository<GuestCheckBI, Long> {}
