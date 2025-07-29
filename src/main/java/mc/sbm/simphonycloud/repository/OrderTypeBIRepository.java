package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.OrderTypeBI;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrderTypeBI entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderTypeBIRepository extends JpaRepository<OrderTypeBI, Integer> {}
