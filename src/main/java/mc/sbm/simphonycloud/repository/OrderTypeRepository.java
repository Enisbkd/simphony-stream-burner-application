package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.OrderType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrderType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderTypeRepository extends JpaRepository<OrderType, Long> {}
