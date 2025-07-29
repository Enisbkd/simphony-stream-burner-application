package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.OrderChannelBI;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrderChannelBI entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderChannelBIRepository extends JpaRepository<OrderChannelBI, Long> {}
