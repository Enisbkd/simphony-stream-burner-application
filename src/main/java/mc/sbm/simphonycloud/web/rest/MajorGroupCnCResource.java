package mc.sbm.simphonycloud.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.MajorGroupCnC;
import mc.sbm.simphonycloud.repository.MajorGroupCnCRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.MajorGroupCnC}.
 */
@RestController
@RequestMapping("/api/major-group-cn-cs")
@Transactional
public class MajorGroupCnCResource {

    private static final Logger LOG = LoggerFactory.getLogger(MajorGroupCnCResource.class);

    private static final String ENTITY_NAME = "majorGroupCnC";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MajorGroupCnCRepository majorGroupCnCRepository;

    public MajorGroupCnCResource(MajorGroupCnCRepository majorGroupCnCRepository) {
        this.majorGroupCnCRepository = majorGroupCnCRepository;
    }

    /**
     * {@code POST  /major-group-cn-cs} : Create a new majorGroupCnC.
     *
     * @param majorGroupCnC the majorGroupCnC to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new majorGroupCnC, or with status {@code 400 (Bad Request)} if the majorGroupCnC has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MajorGroupCnC> createMajorGroupCnC(@RequestBody MajorGroupCnC majorGroupCnC) throws URISyntaxException {
        LOG.debug("REST request to save MajorGroupCnC : {}", majorGroupCnC);
        if (majorGroupCnC.getId() != null) {
            throw new BadRequestAlertException("A new majorGroupCnC cannot already have an ID", ENTITY_NAME, "idexists");
        }
        majorGroupCnC = majorGroupCnCRepository.save(majorGroupCnC);
        return ResponseEntity.created(new URI("/api/major-group-cn-cs/" + majorGroupCnC.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, majorGroupCnC.getId().toString()))
            .body(majorGroupCnC);
    }

    /**
     * {@code PUT  /major-group-cn-cs/:id} : Updates an existing majorGroupCnC.
     *
     * @param id the id of the majorGroupCnC to save.
     * @param majorGroupCnC the majorGroupCnC to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated majorGroupCnC,
     * or with status {@code 400 (Bad Request)} if the majorGroupCnC is not valid,
     * or with status {@code 500 (Internal Server Error)} if the majorGroupCnC couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MajorGroupCnC> updateMajorGroupCnC(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MajorGroupCnC majorGroupCnC
    ) throws URISyntaxException {
        LOG.debug("REST request to update MajorGroupCnC : {}, {}", id, majorGroupCnC);
        if (majorGroupCnC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, majorGroupCnC.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!majorGroupCnCRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        majorGroupCnC = majorGroupCnCRepository.save(majorGroupCnC);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, majorGroupCnC.getId().toString()))
            .body(majorGroupCnC);
    }

    /**
     * {@code PATCH  /major-group-cn-cs/:id} : Partial updates given fields of an existing majorGroupCnC, field will ignore if it is null
     *
     * @param id the id of the majorGroupCnC to save.
     * @param majorGroupCnC the majorGroupCnC to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated majorGroupCnC,
     * or with status {@code 400 (Bad Request)} if the majorGroupCnC is not valid,
     * or with status {@code 404 (Not Found)} if the majorGroupCnC is not found,
     * or with status {@code 500 (Internal Server Error)} if the majorGroupCnC couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MajorGroupCnC> partialUpdateMajorGroupCnC(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MajorGroupCnC majorGroupCnC
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update MajorGroupCnC partially : {}, {}", id, majorGroupCnC);
        if (majorGroupCnC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, majorGroupCnC.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!majorGroupCnCRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MajorGroupCnC> result = majorGroupCnCRepository
            .findById(majorGroupCnC.getId())
            .map(existingMajorGroupCnC -> {
                if (majorGroupCnC.getNom() != null) {
                    existingMajorGroupCnC.setNom(majorGroupCnC.getNom());
                }
                if (majorGroupCnC.getNomCourt() != null) {
                    existingMajorGroupCnC.setNomCourt(majorGroupCnC.getNomCourt());
                }
                if (majorGroupCnC.getPointDeVenteRef() != null) {
                    existingMajorGroupCnC.setPointDeVenteRef(majorGroupCnC.getPointDeVenteRef());
                }

                return existingMajorGroupCnC;
            })
            .map(majorGroupCnCRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, majorGroupCnC.getId().toString())
        );
    }

    /**
     * {@code GET  /major-group-cn-cs} : get all the majorGroupCnCS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of majorGroupCnCS in body.
     */
    @GetMapping("")
    public List<MajorGroupCnC> getAllMajorGroupCnCS() {
        LOG.debug("REST request to get all MajorGroupCnCS");
        return majorGroupCnCRepository.findAll();
    }

    /**
     * {@code GET  /major-group-cn-cs/:id} : get the "id" majorGroupCnC.
     *
     * @param id the id of the majorGroupCnC to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the majorGroupCnC, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MajorGroupCnC> getMajorGroupCnC(@PathVariable("id") Long id) {
        LOG.debug("REST request to get MajorGroupCnC : {}", id);
        Optional<MajorGroupCnC> majorGroupCnC = majorGroupCnCRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(majorGroupCnC);
    }

    /**
     * {@code DELETE  /major-group-cn-cs/:id} : delete the "id" majorGroupCnC.
     *
     * @param id the id of the majorGroupCnC to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMajorGroupCnC(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete MajorGroupCnC : {}", id);
        majorGroupCnCRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
