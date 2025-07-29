package mc.sbm.simphonycloud.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.ElementMenu;
import mc.sbm.simphonycloud.repository.ElementMenuRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.ElementMenu}.
 */
@RestController
@RequestMapping("/api/element-menus")
@Transactional
public class ElementMenuResource {

    private static final Logger LOG = LoggerFactory.getLogger(ElementMenuResource.class);

    private static final String ENTITY_NAME = "elementMenu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ElementMenuRepository elementMenuRepository;

    public ElementMenuResource(ElementMenuRepository elementMenuRepository) {
        this.elementMenuRepository = elementMenuRepository;
    }

    /**
     * {@code POST  /element-menus} : Create a new elementMenu.
     *
     * @param elementMenu the elementMenu to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new elementMenu, or with status {@code 400 (Bad Request)} if the elementMenu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ElementMenu> createElementMenu(@Valid @RequestBody ElementMenu elementMenu) throws URISyntaxException {
        LOG.debug("REST request to save ElementMenu : {}", elementMenu);
        if (elementMenu.getId() != null) {
            throw new BadRequestAlertException("A new elementMenu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        elementMenu = elementMenuRepository.save(elementMenu);
        return ResponseEntity.created(new URI("/api/element-menus/" + elementMenu.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, elementMenu.getId().toString()))
            .body(elementMenu);
    }

    /**
     * {@code PUT  /element-menus/:id} : Updates an existing elementMenu.
     *
     * @param id the id of the elementMenu to save.
     * @param elementMenu the elementMenu to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated elementMenu,
     * or with status {@code 400 (Bad Request)} if the elementMenu is not valid,
     * or with status {@code 500 (Internal Server Error)} if the elementMenu couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ElementMenu> updateElementMenu(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ElementMenu elementMenu
    ) throws URISyntaxException {
        LOG.debug("REST request to update ElementMenu : {}, {}", id, elementMenu);
        if (elementMenu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, elementMenu.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!elementMenuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        elementMenu = elementMenuRepository.save(elementMenu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, elementMenu.getId().toString()))
            .body(elementMenu);
    }

    /**
     * {@code PATCH  /element-menus/:id} : Partial updates given fields of an existing elementMenu, field will ignore if it is null
     *
     * @param id the id of the elementMenu to save.
     * @param elementMenu the elementMenu to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated elementMenu,
     * or with status {@code 400 (Bad Request)} if the elementMenu is not valid,
     * or with status {@code 404 (Not Found)} if the elementMenu is not found,
     * or with status {@code 500 (Internal Server Error)} if the elementMenu couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ElementMenu> partialUpdateElementMenu(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ElementMenu elementMenu
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ElementMenu partially : {}, {}", id, elementMenu);
        if (elementMenu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, elementMenu.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!elementMenuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ElementMenu> result = elementMenuRepository
            .findById(elementMenu.getId())
            .map(existingElementMenu -> {
                if (elementMenu.getMasterId() != null) {
                    existingElementMenu.setMasterId(elementMenu.getMasterId());
                }
                if (elementMenu.getNom() != null) {
                    existingElementMenu.setNom(elementMenu.getNom());
                }
                if (elementMenu.getNomCourt() != null) {
                    existingElementMenu.setNomCourt(elementMenu.getNomCourt());
                }
                if (elementMenu.getFamilyGroupRef() != null) {
                    existingElementMenu.setFamilyGroupRef(elementMenu.getFamilyGroupRef());
                }
                if (elementMenu.getPrix() != null) {
                    existingElementMenu.setPrix(elementMenu.getPrix());
                }
                if (elementMenu.getMenuRef() != null) {
                    existingElementMenu.setMenuRef(elementMenu.getMenuRef());
                }

                return existingElementMenu;
            })
            .map(elementMenuRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, elementMenu.getId().toString())
        );
    }

    /**
     * {@code GET  /element-menus} : get all the elementMenus.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of elementMenus in body.
     */
    @GetMapping("")
    public List<ElementMenu> getAllElementMenus() {
        LOG.debug("REST request to get all ElementMenus");
        return elementMenuRepository.findAll();
    }

    /**
     * {@code GET  /element-menus/:id} : get the "id" elementMenu.
     *
     * @param id the id of the elementMenu to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the elementMenu, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ElementMenu> getElementMenu(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ElementMenu : {}", id);
        Optional<ElementMenu> elementMenu = elementMenuRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(elementMenu);
    }

    /**
     * {@code DELETE  /element-menus/:id} : delete the "id" elementMenu.
     *
     * @param id the id of the elementMenu to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteElementMenu(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ElementMenu : {}", id);
        elementMenuRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
