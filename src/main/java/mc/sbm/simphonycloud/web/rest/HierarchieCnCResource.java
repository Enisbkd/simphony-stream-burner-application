package mc.sbm.simphonycloud.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.HierarchieCnC;
import mc.sbm.simphonycloud.repository.HierarchieCnCRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.HierarchieCnC}.
 */
@RestController
@RequestMapping("/api/hierarchie-cn-cs")
@Transactional
public class HierarchieCnCResource {

    private static final Logger LOG = LoggerFactory.getLogger(HierarchieCnCResource.class);

    private static final String ENTITY_NAME = "hierarchieCnC";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HierarchieCnCRepository hierarchieCnCRepository;

    public HierarchieCnCResource(HierarchieCnCRepository hierarchieCnCRepository) {
        this.hierarchieCnCRepository = hierarchieCnCRepository;
    }

    /**
     * {@code POST  /hierarchie-cn-cs} : Create a new hierarchieCnC.
     *
     * @param hierarchieCnC the hierarchieCnC to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hierarchieCnC, or with status {@code 400 (Bad Request)} if the hierarchieCnC has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<HierarchieCnC> createHierarchieCnC(@Valid @RequestBody HierarchieCnC hierarchieCnC) throws URISyntaxException {
        LOG.debug("REST request to save HierarchieCnC : {}", hierarchieCnC);
        if (hierarchieCnC.getId() != null) {
            throw new BadRequestAlertException("A new hierarchieCnC cannot already have an ID", ENTITY_NAME, "idexists");
        }
        hierarchieCnC = hierarchieCnCRepository.save(hierarchieCnC);
        return ResponseEntity.created(new URI("/api/hierarchie-cn-cs/" + hierarchieCnC.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, hierarchieCnC.getId().toString()))
            .body(hierarchieCnC);
    }

    /**
     * {@code PUT  /hierarchie-cn-cs/:id} : Updates an existing hierarchieCnC.
     *
     * @param id the id of the hierarchieCnC to save.
     * @param hierarchieCnC the hierarchieCnC to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hierarchieCnC,
     * or with status {@code 400 (Bad Request)} if the hierarchieCnC is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hierarchieCnC couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HierarchieCnC> updateHierarchieCnC(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HierarchieCnC hierarchieCnC
    ) throws URISyntaxException {
        LOG.debug("REST request to update HierarchieCnC : {}, {}", id, hierarchieCnC);
        if (hierarchieCnC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hierarchieCnC.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hierarchieCnCRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        hierarchieCnC = hierarchieCnCRepository.save(hierarchieCnC);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hierarchieCnC.getId().toString()))
            .body(hierarchieCnC);
    }

    /**
     * {@code PATCH  /hierarchie-cn-cs/:id} : Partial updates given fields of an existing hierarchieCnC, field will ignore if it is null
     *
     * @param id the id of the hierarchieCnC to save.
     * @param hierarchieCnC the hierarchieCnC to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hierarchieCnC,
     * or with status {@code 400 (Bad Request)} if the hierarchieCnC is not valid,
     * or with status {@code 404 (Not Found)} if the hierarchieCnC is not found,
     * or with status {@code 500 (Internal Server Error)} if the hierarchieCnC couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HierarchieCnC> partialUpdateHierarchieCnC(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HierarchieCnC hierarchieCnC
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update HierarchieCnC partially : {}, {}", id, hierarchieCnC);
        if (hierarchieCnC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hierarchieCnC.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hierarchieCnCRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HierarchieCnC> result = hierarchieCnCRepository
            .findById(hierarchieCnC.getId())
            .map(existingHierarchieCnC -> {
                if (hierarchieCnC.getNom() != null) {
                    existingHierarchieCnC.setNom(hierarchieCnC.getNom());
                }
                if (hierarchieCnC.getParentHierId() != null) {
                    existingHierarchieCnC.setParentHierId(hierarchieCnC.getParentHierId());
                }
                if (hierarchieCnC.getUnitId() != null) {
                    existingHierarchieCnC.setUnitId(hierarchieCnC.getUnitId());
                }

                return existingHierarchieCnC;
            })
            .map(hierarchieCnCRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hierarchieCnC.getId().toString())
        );
    }

    /**
     * {@code GET  /hierarchie-cn-cs} : get all the hierarchieCnCS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hierarchieCnCS in body.
     */
    @GetMapping("")
    public List<HierarchieCnC> getAllHierarchieCnCS() {
        LOG.debug("REST request to get all HierarchieCnCS");
        return hierarchieCnCRepository.findAll();
    }

    /**
     * {@code GET  /hierarchie-cn-cs/:id} : get the "id" hierarchieCnC.
     *
     * @param id the id of the hierarchieCnC to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hierarchieCnC, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HierarchieCnC> getHierarchieCnC(@PathVariable("id") Long id) {
        LOG.debug("REST request to get HierarchieCnC : {}", id);
        Optional<HierarchieCnC> hierarchieCnC = hierarchieCnCRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(hierarchieCnC);
    }

    /**
     * {@code DELETE  /hierarchie-cn-cs/:id} : delete the "id" hierarchieCnC.
     *
     * @param id the id of the hierarchieCnC to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHierarchieCnC(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete HierarchieCnC : {}", id);
        hierarchieCnCRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
