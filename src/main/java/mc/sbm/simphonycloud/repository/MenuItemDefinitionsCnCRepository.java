package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.MenuItemDefinitionsCnC;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MenuItemDefinitionsCnC entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MenuItemDefinitionsCnCRepository extends JpaRepository<MenuItemDefinitionsCnC, Long> {}
