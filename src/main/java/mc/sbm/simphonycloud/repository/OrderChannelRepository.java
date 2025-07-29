package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.OrderChannel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrderChannel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderChannelRepository extends JpaRepository<OrderChannel, Long> {}
