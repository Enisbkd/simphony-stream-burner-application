package mc.sbm.simphonycloud.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.MenuItemPricesCnC;
import mc.sbm.simphonycloud.repository.MenuItemPricesCnCRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.MenuItemPricesCnC}.
 */
@RestController
@RequestMapping("/api/menu-item-prices-cn-cs")
@Transactional
public class MenuItemPricesCnCResource {

    private static final Logger LOG = LoggerFactory.getLogger(MenuItemPricesCnCResource.class);

    private static final String ENTITY_NAME = "menuItemPricesCnC";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MenuItemPricesCnCRepository menuItemPricesCnCRepository;

    public MenuItemPricesCnCResource(MenuItemPricesCnCRepository menuItemPricesCnCRepository) {
        this.menuItemPricesCnCRepository = menuItemPricesCnCRepository;
    }

    /**
     * {@code POST  /menu-item-prices-cn-cs} : Create a new menuItemPricesCnC.
     *
     * @param menuItemPricesCnC the menuItemPricesCnC to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new menuItemPricesCnC, or with status {@code 400 (Bad Request)} if the menuItemPricesCnC has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MenuItemPricesCnC> createMenuItemPricesCnC(@RequestBody MenuItemPricesCnC menuItemPricesCnC)
        throws URISyntaxException {
        LOG.debug("REST request to save MenuItemPricesCnC : {}", menuItemPricesCnC);
        if (menuItemPricesCnC.getId() != null) {
            throw new BadRequestAlertException("A new menuItemPricesCnC cannot already have an ID", ENTITY_NAME, "idexists");
        }
        menuItemPricesCnC = menuItemPricesCnCRepository.save(menuItemPricesCnC);
        return ResponseEntity.created(new URI("/api/menu-item-prices-cn-cs/" + menuItemPricesCnC.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, menuItemPricesCnC.getId().toString()))
            .body(menuItemPricesCnC);
    }

    /**
     * {@code PUT  /menu-item-prices-cn-cs/:id} : Updates an existing menuItemPricesCnC.
     *
     * @param id the id of the menuItemPricesCnC to save.
     * @param menuItemPricesCnC the menuItemPricesCnC to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated menuItemPricesCnC,
     * or with status {@code 400 (Bad Request)} if the menuItemPricesCnC is not valid,
     * or with status {@code 500 (Internal Server Error)} if the menuItemPricesCnC couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MenuItemPricesCnC> updateMenuItemPricesCnC(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody MenuItemPricesCnC menuItemPricesCnC
    ) throws URISyntaxException {
        LOG.debug("REST request to update MenuItemPricesCnC : {}, {}", id, menuItemPricesCnC);
        if (menuItemPricesCnC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, menuItemPricesCnC.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!menuItemPricesCnCRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        menuItemPricesCnC = menuItemPricesCnCRepository.save(menuItemPricesCnC);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, menuItemPricesCnC.getId().toString()))
            .body(menuItemPricesCnC);
    }

    /**
     * {@code PATCH  /menu-item-prices-cn-cs/:id} : Partial updates given fields of an existing menuItemPricesCnC, field will ignore if it is null
     *
     * @param id the id of the menuItemPricesCnC to save.
     * @param menuItemPricesCnC the menuItemPricesCnC to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated menuItemPricesCnC,
     * or with status {@code 400 (Bad Request)} if the menuItemPricesCnC is not valid,
     * or with status {@code 404 (Not Found)} if the menuItemPricesCnC is not found,
     * or with status {@code 500 (Internal Server Error)} if the menuItemPricesCnC couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MenuItemPricesCnC> partialUpdateMenuItemPricesCnC(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody MenuItemPricesCnC menuItemPricesCnC
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update MenuItemPricesCnC partially : {}, {}", id, menuItemPricesCnC);
        if (menuItemPricesCnC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, menuItemPricesCnC.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!menuItemPricesCnCRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MenuItemPricesCnC> result = menuItemPricesCnCRepository
            .findById(menuItemPricesCnC.getId())
            .map(existingMenuItemPricesCnC -> {
                if (menuItemPricesCnC.getHierUnitId() != null) {
                    existingMenuItemPricesCnC.setHierUnitId(menuItemPricesCnC.getHierUnitId());
                }
                if (menuItemPricesCnC.getMenuItemPriceId() != null) {
                    existingMenuItemPricesCnC.setMenuItemPriceId(menuItemPricesCnC.getMenuItemPriceId());
                }
                if (menuItemPricesCnC.getMenuItemMasterId() != null) {
                    existingMenuItemPricesCnC.setMenuItemMasterId(menuItemPricesCnC.getMenuItemMasterId());
                }
                if (menuItemPricesCnC.getMenuItemMasterObjNum() != null) {
                    existingMenuItemPricesCnC.setMenuItemMasterObjNum(menuItemPricesCnC.getMenuItemMasterObjNum());
                }
                if (menuItemPricesCnC.getMenuItemDefinitionId() != null) {
                    existingMenuItemPricesCnC.setMenuItemDefinitionId(menuItemPricesCnC.getMenuItemDefinitionId());
                }
                if (menuItemPricesCnC.getDefSequenceNum() != null) {
                    existingMenuItemPricesCnC.setDefSequenceNum(menuItemPricesCnC.getDefSequenceNum());
                }
                if (menuItemPricesCnC.getExternalReference1() != null) {
                    existingMenuItemPricesCnC.setExternalReference1(menuItemPricesCnC.getExternalReference1());
                }
                if (menuItemPricesCnC.getExternalReference2() != null) {
                    existingMenuItemPricesCnC.setExternalReference2(menuItemPricesCnC.getExternalReference2());
                }
                if (menuItemPricesCnC.getPriceSequenceNum() != null) {
                    existingMenuItemPricesCnC.setPriceSequenceNum(menuItemPricesCnC.getPriceSequenceNum());
                }
                if (menuItemPricesCnC.getActiveOnMenuLevel() != null) {
                    existingMenuItemPricesCnC.setActiveOnMenuLevel(menuItemPricesCnC.getActiveOnMenuLevel());
                }
                if (menuItemPricesCnC.getEffectivityGroupObjNum() != null) {
                    existingMenuItemPricesCnC.setEffectivityGroupObjNum(menuItemPricesCnC.getEffectivityGroupObjNum());
                }
                if (menuItemPricesCnC.getPrepCost() != null) {
                    existingMenuItemPricesCnC.setPrepCost(menuItemPricesCnC.getPrepCost());
                }
                if (menuItemPricesCnC.getPrice() != null) {
                    existingMenuItemPricesCnC.setPrice(menuItemPricesCnC.getPrice());
                }
                if (menuItemPricesCnC.getOptions() != null) {
                    existingMenuItemPricesCnC.setOptions(menuItemPricesCnC.getOptions());
                }

                return existingMenuItemPricesCnC;
            })
            .map(menuItemPricesCnCRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, menuItemPricesCnC.getId().toString())
        );
    }

    /**
     * {@code GET  /menu-item-prices-cn-cs} : get all the menuItemPricesCnCS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of menuItemPricesCnCS in body.
     */
    @GetMapping("")
    public List<MenuItemPricesCnC> getAllMenuItemPricesCnCS() {
        LOG.debug("REST request to get all MenuItemPricesCnCS");
        return menuItemPricesCnCRepository.findAll();
    }

    /**
     * {@code GET  /menu-item-prices-cn-cs/:id} : get the "id" menuItemPricesCnC.
     *
     * @param id the id of the menuItemPricesCnC to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the menuItemPricesCnC, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MenuItemPricesCnC> getMenuItemPricesCnC(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get MenuItemPricesCnC : {}", id);
        Optional<MenuItemPricesCnC> menuItemPricesCnC = menuItemPricesCnCRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(menuItemPricesCnC);
    }

    /**
     * {@code DELETE  /menu-item-prices-cn-cs/:id} : delete the "id" menuItemPricesCnC.
     *
     * @param id the id of the menuItemPricesCnC to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItemPricesCnC(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete MenuItemPricesCnC : {}", id);
        menuItemPricesCnCRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
