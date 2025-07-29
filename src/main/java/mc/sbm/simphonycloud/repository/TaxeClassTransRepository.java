package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.TaxeClassTrans;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TaxeClassTrans entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxeClassTransRepository extends JpaRepository<TaxeClassTrans, Integer> {}
