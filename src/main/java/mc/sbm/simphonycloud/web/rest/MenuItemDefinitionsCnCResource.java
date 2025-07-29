package mc.sbm.simphonycloud.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.MenuItemDefinitionsCnC;
import mc.sbm.simphonycloud.repository.MenuItemDefinitionsCnCRepository;
import mc.sbm.simphonycloud.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.MenuItemDefinitionsCnC}.
 */
@RestController
@RequestMapping("/api/menu-item-definitions-cn-cs")
@Transactional
public class MenuItemDefinitionsCnCResource {

    private static final Logger LOG = LoggerFactory.getLogger(MenuItemDefinitionsCnCResource.class);

    private static final String ENTITY_NAME = "menuItemDefinitionsCnC";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MenuItemDefinitionsCnCRepository menuItemDefinitionsCnCRepository;

    public MenuItemDefinitionsCnCResource(MenuItemDefinitionsCnCRepository menuItemDefinitionsCnCRepository) {
        this.menuItemDefinitionsCnCRepository = menuItemDefinitionsCnCRepository;
    }

    /**
     * {@code POST  /menu-item-definitions-cn-cs} : Create a new menuItemDefinitionsCnC.
     *
     * @param menuItemDefinitionsCnC the menuItemDefinitionsCnC to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new menuItemDefinitionsCnC, or with status {@code 400 (Bad Request)} if the menuItemDefinitionsCnC has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MenuItemDefinitionsCnC> createMenuItemDefinitionsCnC(@RequestBody MenuItemDefinitionsCnC menuItemDefinitionsCnC)
        throws URISyntaxException {
        LOG.debug("REST request to save MenuItemDefinitionsCnC : {}", menuItemDefinitionsCnC);
        if (menuItemDefinitionsCnC.getId() != null) {
            throw new BadRequestAlertException("A new menuItemDefinitionsCnC cannot already have an ID", ENTITY_NAME, "idexists");
        }
        menuItemDefinitionsCnC = menuItemDefinitionsCnCRepository.save(menuItemDefinitionsCnC);
        return ResponseEntity.created(new URI("/api/menu-item-definitions-cn-cs/" + menuItemDefinitionsCnC.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, menuItemDefinitionsCnC.getId().toString()))
            .body(menuItemDefinitionsCnC);
    }

    /**
     * {@code PUT  /menu-item-definitions-cn-cs/:id} : Updates an existing menuItemDefinitionsCnC.
     *
     * @param id the id of the menuItemDefinitionsCnC to save.
     * @param menuItemDefinitionsCnC the menuItemDefinitionsCnC to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated menuItemDefinitionsCnC,
     * or with status {@code 400 (Bad Request)} if the menuItemDefinitionsCnC is not valid,
     * or with status {@code 500 (Internal Server Error)} if the menuItemDefinitionsCnC couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MenuItemDefinitionsCnC> updateMenuItemDefinitionsCnC(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody MenuItemDefinitionsCnC menuItemDefinitionsCnC
    ) throws URISyntaxException {
        LOG.debug("REST request to update MenuItemDefinitionsCnC : {}, {}", id, menuItemDefinitionsCnC);
        if (menuItemDefinitionsCnC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, menuItemDefinitionsCnC.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!menuItemDefinitionsCnCRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        menuItemDefinitionsCnC = menuItemDefinitionsCnCRepository.save(menuItemDefinitionsCnC);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, menuItemDefinitionsCnC.getId().toString()))
            .body(menuItemDefinitionsCnC);
    }

    /**
     * {@code PATCH  /menu-item-definitions-cn-cs/:id} : Partial updates given fields of an existing menuItemDefinitionsCnC, field will ignore if it is null
     *
     * @param id the id of the menuItemDefinitionsCnC to save.
     * @param menuItemDefinitionsCnC the menuItemDefinitionsCnC to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated menuItemDefinitionsCnC,
     * or with status {@code 400 (Bad Request)} if the menuItemDefinitionsCnC is not valid,
     * or with status {@code 404 (Not Found)} if the menuItemDefinitionsCnC is not found,
     * or with status {@code 500 (Internal Server Error)} if the menuItemDefinitionsCnC couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MenuItemDefinitionsCnC> partialUpdateMenuItemDefinitionsCnC(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody MenuItemDefinitionsCnC menuItemDefinitionsCnC
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update MenuItemDefinitionsCnC partially : {}, {}", id, menuItemDefinitionsCnC);
        if (menuItemDefinitionsCnC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, menuItemDefinitionsCnC.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!menuItemDefinitionsCnCRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MenuItemDefinitionsCnC> result = menuItemDefinitionsCnCRepository
            .findById(menuItemDefinitionsCnC.getId())
            .map(existingMenuItemDefinitionsCnC -> {
                if (menuItemDefinitionsCnC.getHierUnitId() != null) {
                    existingMenuItemDefinitionsCnC.setHierUnitId(menuItemDefinitionsCnC.getHierUnitId());
                }
                if (menuItemDefinitionsCnC.getMenuItemMasterObjNum() != null) {
                    existingMenuItemDefinitionsCnC.setMenuItemMasterObjNum(menuItemDefinitionsCnC.getMenuItemMasterObjNum());
                }
                if (menuItemDefinitionsCnC.getMenuItemMasterId() != null) {
                    existingMenuItemDefinitionsCnC.setMenuItemMasterId(menuItemDefinitionsCnC.getMenuItemMasterId());
                }
                if (menuItemDefinitionsCnC.getMenuItemDefinitionId() != null) {
                    existingMenuItemDefinitionsCnC.setMenuItemDefinitionId(menuItemDefinitionsCnC.getMenuItemDefinitionId());
                }
                if (menuItemDefinitionsCnC.getDefSequenceNum() != null) {
                    existingMenuItemDefinitionsCnC.setDefSequenceNum(menuItemDefinitionsCnC.getDefSequenceNum());
                }
                if (menuItemDefinitionsCnC.getMenuItemClassObjNum() != null) {
                    existingMenuItemDefinitionsCnC.setMenuItemClassObjNum(menuItemDefinitionsCnC.getMenuItemClassObjNum());
                }
                if (menuItemDefinitionsCnC.getOverridePrintClassObjNum() != null) {
                    existingMenuItemDefinitionsCnC.setOverridePrintClassObjNum(menuItemDefinitionsCnC.getOverridePrintClassObjNum());
                }
                if (menuItemDefinitionsCnC.getMainLevel() != null) {
                    existingMenuItemDefinitionsCnC.setMainLevel(menuItemDefinitionsCnC.getMainLevel());
                }
                if (menuItemDefinitionsCnC.getSubLevel() != null) {
                    existingMenuItemDefinitionsCnC.setSubLevel(menuItemDefinitionsCnC.getSubLevel());
                }
                if (menuItemDefinitionsCnC.getQuantity() != null) {
                    existingMenuItemDefinitionsCnC.setQuantity(menuItemDefinitionsCnC.getQuantity());
                }
                if (menuItemDefinitionsCnC.getKdsPrepTime() != null) {
                    existingMenuItemDefinitionsCnC.setKdsPrepTime(menuItemDefinitionsCnC.getKdsPrepTime());
                }
                if (menuItemDefinitionsCnC.getPrefixLevelOverride() != null) {
                    existingMenuItemDefinitionsCnC.setPrefixLevelOverride(menuItemDefinitionsCnC.getPrefixLevelOverride());
                }
                if (menuItemDefinitionsCnC.getGuestCount() != null) {
                    existingMenuItemDefinitionsCnC.setGuestCount(menuItemDefinitionsCnC.getGuestCount());
                }
                if (menuItemDefinitionsCnC.getSlu1ObjNum() != null) {
                    existingMenuItemDefinitionsCnC.setSlu1ObjNum(menuItemDefinitionsCnC.getSlu1ObjNum());
                }
                if (menuItemDefinitionsCnC.getSlu2ObjNum() != null) {
                    existingMenuItemDefinitionsCnC.setSlu2ObjNum(menuItemDefinitionsCnC.getSlu2ObjNum());
                }
                if (menuItemDefinitionsCnC.getSlu3ObjNum() != null) {
                    existingMenuItemDefinitionsCnC.setSlu3ObjNum(menuItemDefinitionsCnC.getSlu3ObjNum());
                }
                if (menuItemDefinitionsCnC.getSlu4ObjNum() != null) {
                    existingMenuItemDefinitionsCnC.setSlu4ObjNum(menuItemDefinitionsCnC.getSlu4ObjNum());
                }
                if (menuItemDefinitionsCnC.getSlu5ObjNum() != null) {
                    existingMenuItemDefinitionsCnC.setSlu5ObjNum(menuItemDefinitionsCnC.getSlu5ObjNum());
                }
                if (menuItemDefinitionsCnC.getSlu6ObjNum() != null) {
                    existingMenuItemDefinitionsCnC.setSlu6ObjNum(menuItemDefinitionsCnC.getSlu6ObjNum());
                }
                if (menuItemDefinitionsCnC.getSlu7ObjNum() != null) {
                    existingMenuItemDefinitionsCnC.setSlu7ObjNum(menuItemDefinitionsCnC.getSlu7ObjNum());
                }
                if (menuItemDefinitionsCnC.getSlu8ObjNum() != null) {
                    existingMenuItemDefinitionsCnC.setSlu8ObjNum(menuItemDefinitionsCnC.getSlu8ObjNum());
                }
                if (menuItemDefinitionsCnC.getFirstName() != null) {
                    existingMenuItemDefinitionsCnC.setFirstName(menuItemDefinitionsCnC.getFirstName());
                }

                return existingMenuItemDefinitionsCnC;
            })
            .map(menuItemDefinitionsCnCRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, menuItemDefinitionsCnC.getId().toString())
        );
    }

    /**
     * {@code GET  /menu-item-definitions-cn-cs} : get all the menuItemDefinitionsCnCS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of menuItemDefinitionsCnCS in body.
     */
    @GetMapping("")
    public List<MenuItemDefinitionsCnC> getAllMenuItemDefinitionsCnCS() {
        LOG.debug("REST request to get all MenuItemDefinitionsCnCS");
        return menuItemDefinitionsCnCRepository.findAll();
    }

    /**
     * {@code GET  /menu-item-definitions-cn-cs/:id} : get the "id" menuItemDefinitionsCnC.
     *
     * @param id the id of the menuItemDefinitionsCnC to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the menuItemDefinitionsCnC, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MenuItemDefinitionsCnC> getMenuItemDefinitionsCnC(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get MenuItemDefinitionsCnC : {}", id);
        Optional<MenuItemDefinitionsCnC> menuItemDefinitionsCnC = menuItemDefinitionsCnCRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(menuItemDefinitionsCnC);
    }

    /**
     * {@code DELETE  /menu-item-definitions-cn-cs/:id} : delete the "id" menuItemDefinitionsCnC.
     *
     * @param id the id of the menuItemDefinitionsCnC to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItemDefinitionsCnC(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete MenuItemDefinitionsCnC : {}", id);
        menuItemDefinitionsCnCRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
