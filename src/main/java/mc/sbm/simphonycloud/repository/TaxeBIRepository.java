package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.TaxeBI;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TaxeBI entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxeBIRepository extends JpaRepository<TaxeBI, Long> {}
