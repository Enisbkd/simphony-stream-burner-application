package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.TaxeRateTrans;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TaxeRateTrans entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxeRateTransRepository extends JpaRepository<TaxeRateTrans, Integer> {}
