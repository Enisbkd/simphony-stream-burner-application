package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.MenuItemPricesCnC;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MenuItemPricesCnC entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MenuItemPricesCnCRepository extends JpaRepository<MenuItemPricesCnC, Integer> {}
