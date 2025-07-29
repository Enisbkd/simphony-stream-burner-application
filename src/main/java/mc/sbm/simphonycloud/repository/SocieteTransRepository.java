package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.SocieteTrans;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SocieteTrans entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocieteTransRepository extends JpaRepository<SocieteTrans, Integer> {}
