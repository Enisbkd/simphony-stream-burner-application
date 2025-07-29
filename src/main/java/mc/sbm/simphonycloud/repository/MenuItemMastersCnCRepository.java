package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.MenuItemMastersCnC;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MenuItemMastersCnC entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MenuItemMastersCnCRepository extends JpaRepository<MenuItemMastersCnC, Integer> {}
