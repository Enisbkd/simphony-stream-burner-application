package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.CodeRaisonBI;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CodeRaisonBI entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CodeRaisonBIRepository extends JpaRepository<CodeRaisonBI, Long> {}
