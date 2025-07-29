package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.BarCodeTrans;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BarCodeTrans entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BarCodeTransRepository extends JpaRepository<BarCodeTrans, Long> {}
