package mc.sbm.simphonycloud.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.MenuItemMastersCnC;
import mc.sbm.simphonycloud.repository.MenuItemMastersCnCRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.MenuItemMastersCnC}.
 */
@RestController
@RequestMapping("/api/menu-item-masters-cn-cs")
@Transactional
public class MenuItemMastersCnCResource {

    private static final Logger LOG = LoggerFactory.getLogger(MenuItemMastersCnCResource.class);

    private static final String ENTITY_NAME = "menuItemMastersCnC";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MenuItemMastersCnCRepository menuItemMastersCnCRepository;

    public MenuItemMastersCnCResource(MenuItemMastersCnCRepository menuItemMastersCnCRepository) {
        this.menuItemMastersCnCRepository = menuItemMastersCnCRepository;
    }

    /**
     * {@code POST  /menu-item-masters-cn-cs} : Create a new menuItemMastersCnC.
     *
     * @param menuItemMastersCnC the menuItemMastersCnC to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new menuItemMastersCnC, or with status {@code 400 (Bad Request)} if the menuItemMastersCnC has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MenuItemMastersCnC> createMenuItemMastersCnC(@RequestBody MenuItemMastersCnC menuItemMastersCnC)
        throws URISyntaxException {
        LOG.debug("REST request to save MenuItemMastersCnC : {}", menuItemMastersCnC);
        if (menuItemMastersCnC.getId() != null) {
            throw new BadRequestAlertException("A new menuItemMastersCnC cannot already have an ID", ENTITY_NAME, "idexists");
        }
        menuItemMastersCnC = menuItemMastersCnCRepository.save(menuItemMastersCnC);
        return ResponseEntity.created(new URI("/api/menu-item-masters-cn-cs/" + menuItemMastersCnC.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, menuItemMastersCnC.getId().toString()))
            .body(menuItemMastersCnC);
    }

    /**
     * {@code PUT  /menu-item-masters-cn-cs/:id} : Updates an existing menuItemMastersCnC.
     *
     * @param id the id of the menuItemMastersCnC to save.
     * @param menuItemMastersCnC the menuItemMastersCnC to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated menuItemMastersCnC,
     * or with status {@code 400 (Bad Request)} if the menuItemMastersCnC is not valid,
     * or with status {@code 500 (Internal Server Error)} if the menuItemMastersCnC couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MenuItemMastersCnC> updateMenuItemMastersCnC(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MenuItemMastersCnC menuItemMastersCnC
    ) throws URISyntaxException {
        LOG.debug("REST request to update MenuItemMastersCnC : {}, {}", id, menuItemMastersCnC);
        if (menuItemMastersCnC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, menuItemMastersCnC.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!menuItemMastersCnCRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        menuItemMastersCnC = menuItemMastersCnCRepository.save(menuItemMastersCnC);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, menuItemMastersCnC.getId().toString()))
            .body(menuItemMastersCnC);
    }

    /**
     * {@code PATCH  /menu-item-masters-cn-cs/:id} : Partial updates given fields of an existing menuItemMastersCnC, field will ignore if it is null
     *
     * @param id the id of the menuItemMastersCnC to save.
     * @param menuItemMastersCnC the menuItemMastersCnC to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated menuItemMastersCnC,
     * or with status {@code 400 (Bad Request)} if the menuItemMastersCnC is not valid,
     * or with status {@code 404 (Not Found)} if the menuItemMastersCnC is not found,
     * or with status {@code 500 (Internal Server Error)} if the menuItemMastersCnC couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MenuItemMastersCnC> partialUpdateMenuItemMastersCnC(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MenuItemMastersCnC menuItemMastersCnC
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update MenuItemMastersCnC partially : {}, {}", id, menuItemMastersCnC);
        if (menuItemMastersCnC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, menuItemMastersCnC.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!menuItemMastersCnCRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MenuItemMastersCnC> result = menuItemMastersCnCRepository
            .findById(menuItemMastersCnC.getId())
            .map(existingMenuItemMastersCnC -> {
                if (menuItemMastersCnC.getHierUnitId() != null) {
                    existingMenuItemMastersCnC.setHierUnitId(menuItemMastersCnC.getHierUnitId());
                }
                if (menuItemMastersCnC.getMenuItemMasterId() != null) {
                    existingMenuItemMastersCnC.setMenuItemMasterId(menuItemMastersCnC.getMenuItemMasterId());
                }
                if (menuItemMastersCnC.getFamilyGroupObjectNum() != null) {
                    existingMenuItemMastersCnC.setFamilyGroupObjectNum(menuItemMastersCnC.getFamilyGroupObjectNum());
                }
                if (menuItemMastersCnC.getMajorGroupObjectNum() != null) {
                    existingMenuItemMastersCnC.setMajorGroupObjectNum(menuItemMastersCnC.getMajorGroupObjectNum());
                }
                if (menuItemMastersCnC.getReportGroupObjectNum() != null) {
                    existingMenuItemMastersCnC.setReportGroupObjectNum(menuItemMastersCnC.getReportGroupObjectNum());
                }
                if (menuItemMastersCnC.getExternalReference1() != null) {
                    existingMenuItemMastersCnC.setExternalReference1(menuItemMastersCnC.getExternalReference1());
                }
                if (menuItemMastersCnC.getExternalReference2() != null) {
                    existingMenuItemMastersCnC.setExternalReference2(menuItemMastersCnC.getExternalReference2());
                }
                if (menuItemMastersCnC.getObjectNum() != null) {
                    existingMenuItemMastersCnC.setObjectNum(menuItemMastersCnC.getObjectNum());
                }
                if (menuItemMastersCnC.getName() != null) {
                    existingMenuItemMastersCnC.setName(menuItemMastersCnC.getName());
                }

                return existingMenuItemMastersCnC;
            })
            .map(menuItemMastersCnCRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, menuItemMastersCnC.getId().toString())
        );
    }

    /**
     * {@code GET  /menu-item-masters-cn-cs} : get all the menuItemMastersCnCS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of menuItemMastersCnCS in body.
     */
    @GetMapping("")
    public List<MenuItemMastersCnC> getAllMenuItemMastersCnCS() {
        LOG.debug("REST request to get all MenuItemMastersCnCS");
        return menuItemMastersCnCRepository.findAll();
    }

    /**
     * {@code GET  /menu-item-masters-cn-cs/:id} : get the "id" menuItemMastersCnC.
     *
     * @param id the id of the menuItemMastersCnC to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the menuItemMastersCnC, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MenuItemMastersCnC> getMenuItemMastersCnC(@PathVariable("id") Long id) {
        LOG.debug("REST request to get MenuItemMastersCnC : {}", id);
        Optional<MenuItemMastersCnC> menuItemMastersCnC = menuItemMastersCnCRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(menuItemMastersCnC);
    }

    /**
     * {@code DELETE  /menu-item-masters-cn-cs/:id} : delete the "id" menuItemMastersCnC.
     *
     * @param id the id of the menuItemMastersCnC to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItemMastersCnC(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete MenuItemMastersCnC : {}", id);
        menuItemMastersCnCRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
