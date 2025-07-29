package mc.sbm.simphonycloud.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.Hierarchie;
import mc.sbm.simphonycloud.repository.HierarchieRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.Hierarchie}.
 */
@RestController
@RequestMapping("/api/hierarchies")
@Transactional
public class HierarchieResource {

    private static final Logger LOG = LoggerFactory.getLogger(HierarchieResource.class);

    private static final String ENTITY_NAME = "hierarchie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HierarchieRepository hierarchieRepository;

    public HierarchieResource(HierarchieRepository hierarchieRepository) {
        this.hierarchieRepository = hierarchieRepository;
    }

    /**
     * {@code POST  /hierarchies} : Create a new hierarchie.
     *
     * @param hierarchie the hierarchie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hierarchie, or with status {@code 400 (Bad Request)} if the hierarchie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Hierarchie> createHierarchie(@Valid @RequestBody Hierarchie hierarchie) throws URISyntaxException {
        LOG.debug("REST request to save Hierarchie : {}", hierarchie);
        if (hierarchie.getId() != null) {
            throw new BadRequestAlertException("A new hierarchie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        hierarchie = hierarchieRepository.save(hierarchie);
        return ResponseEntity.created(new URI("/api/hierarchies/" + hierarchie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, hierarchie.getId().toString()))
            .body(hierarchie);
    }

    /**
     * {@code PUT  /hierarchies/:id} : Updates an existing hierarchie.
     *
     * @param id the id of the hierarchie to save.
     * @param hierarchie the hierarchie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hierarchie,
     * or with status {@code 400 (Bad Request)} if the hierarchie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hierarchie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Hierarchie> updateHierarchie(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Hierarchie hierarchie
    ) throws URISyntaxException {
        LOG.debug("REST request to update Hierarchie : {}, {}", id, hierarchie);
        if (hierarchie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hierarchie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hierarchieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        hierarchie = hierarchieRepository.save(hierarchie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hierarchie.getId().toString()))
            .body(hierarchie);
    }

    /**
     * {@code PATCH  /hierarchies/:id} : Partial updates given fields of an existing hierarchie, field will ignore if it is null
     *
     * @param id the id of the hierarchie to save.
     * @param hierarchie the hierarchie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hierarchie,
     * or with status {@code 400 (Bad Request)} if the hierarchie is not valid,
     * or with status {@code 404 (Not Found)} if the hierarchie is not found,
     * or with status {@code 500 (Internal Server Error)} if the hierarchie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Hierarchie> partialUpdateHierarchie(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Hierarchie hierarchie
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Hierarchie partially : {}, {}", id, hierarchie);
        if (hierarchie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hierarchie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hierarchieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Hierarchie> result = hierarchieRepository
            .findById(hierarchie.getId())
            .map(existingHierarchie -> {
                if (hierarchie.getNom() != null) {
                    existingHierarchie.setNom(hierarchie.getNom());
                }
                if (hierarchie.getParentHierId() != null) {
                    existingHierarchie.setParentHierId(hierarchie.getParentHierId());
                }
                if (hierarchie.getUnitId() != null) {
                    existingHierarchie.setUnitId(hierarchie.getUnitId());
                }

                return existingHierarchie;
            })
            .map(hierarchieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hierarchie.getId().toString())
        );
    }

    /**
     * {@code GET  /hierarchies} : get all the hierarchies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hierarchies in body.
     */
    @GetMapping("")
    public List<Hierarchie> getAllHierarchies() {
        LOG.debug("REST request to get all Hierarchies");
        return hierarchieRepository.findAll();
    }

    /**
     * {@code GET  /hierarchies/:id} : get the "id" hierarchie.
     *
     * @param id the id of the hierarchie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hierarchie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Hierarchie> getHierarchie(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Hierarchie : {}", id);
        Optional<Hierarchie> hierarchie = hierarchieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(hierarchie);
    }

    /**
     * {@code DELETE  /hierarchies/:id} : delete the "id" hierarchie.
     *
     * @param id the id of the hierarchie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHierarchie(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Hierarchie : {}", id);
        hierarchieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
