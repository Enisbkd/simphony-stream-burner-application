package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.DetailLineBI;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DetailLineBI entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetailLineBIRepository extends JpaRepository<DetailLineBI, Long> {}
