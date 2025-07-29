package mc.sbm.simphonycloud.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.FamilyGroupCnC;
import mc.sbm.simphonycloud.repository.FamilyGroupCnCRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.FamilyGroupCnC}.
 */
@RestController
@RequestMapping("/api/family-group-cn-cs")
@Transactional
public class FamilyGroupCnCResource {

    private static final Logger LOG = LoggerFactory.getLogger(FamilyGroupCnCResource.class);

    private static final String ENTITY_NAME = "familyGroupCnC";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FamilyGroupCnCRepository familyGroupCnCRepository;

    public FamilyGroupCnCResource(FamilyGroupCnCRepository familyGroupCnCRepository) {
        this.familyGroupCnCRepository = familyGroupCnCRepository;
    }

    /**
     * {@code POST  /family-group-cn-cs} : Create a new familyGroupCnC.
     *
     * @param familyGroupCnC the familyGroupCnC to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new familyGroupCnC, or with status {@code 400 (Bad Request)} if the familyGroupCnC has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FamilyGroupCnC> createFamilyGroupCnC(@RequestBody FamilyGroupCnC familyGroupCnC) throws URISyntaxException {
        LOG.debug("REST request to save FamilyGroupCnC : {}", familyGroupCnC);
        if (familyGroupCnC.getId() != null) {
            throw new BadRequestAlertException("A new familyGroupCnC cannot already have an ID", ENTITY_NAME, "idexists");
        }
        familyGroupCnC = familyGroupCnCRepository.save(familyGroupCnC);
        return ResponseEntity.created(new URI("/api/family-group-cn-cs/" + familyGroupCnC.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, familyGroupCnC.getId().toString()))
            .body(familyGroupCnC);
    }

    /**
     * {@code PUT  /family-group-cn-cs/:id} : Updates an existing familyGroupCnC.
     *
     * @param id the id of the familyGroupCnC to save.
     * @param familyGroupCnC the familyGroupCnC to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated familyGroupCnC,
     * or with status {@code 400 (Bad Request)} if the familyGroupCnC is not valid,
     * or with status {@code 500 (Internal Server Error)} if the familyGroupCnC couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FamilyGroupCnC> updateFamilyGroupCnC(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody FamilyGroupCnC familyGroupCnC
    ) throws URISyntaxException {
        LOG.debug("REST request to update FamilyGroupCnC : {}, {}", id, familyGroupCnC);
        if (familyGroupCnC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, familyGroupCnC.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!familyGroupCnCRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        familyGroupCnC = familyGroupCnCRepository.save(familyGroupCnC);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, familyGroupCnC.getId().toString()))
            .body(familyGroupCnC);
    }

    /**
     * {@code PATCH  /family-group-cn-cs/:id} : Partial updates given fields of an existing familyGroupCnC, field will ignore if it is null
     *
     * @param id the id of the familyGroupCnC to save.
     * @param familyGroupCnC the familyGroupCnC to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated familyGroupCnC,
     * or with status {@code 400 (Bad Request)} if the familyGroupCnC is not valid,
     * or with status {@code 404 (Not Found)} if the familyGroupCnC is not found,
     * or with status {@code 500 (Internal Server Error)} if the familyGroupCnC couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FamilyGroupCnC> partialUpdateFamilyGroupCnC(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody FamilyGroupCnC familyGroupCnC
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FamilyGroupCnC partially : {}, {}", id, familyGroupCnC);
        if (familyGroupCnC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, familyGroupCnC.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!familyGroupCnCRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FamilyGroupCnC> result = familyGroupCnCRepository
            .findById(familyGroupCnC.getId())
            .map(existingFamilyGroupCnC -> {
                if (familyGroupCnC.getNom() != null) {
                    existingFamilyGroupCnC.setNom(familyGroupCnC.getNom());
                }
                if (familyGroupCnC.getNomCourt() != null) {
                    existingFamilyGroupCnC.setNomCourt(familyGroupCnC.getNomCourt());
                }
                if (familyGroupCnC.getMajorGroupRef() != null) {
                    existingFamilyGroupCnC.setMajorGroupRef(familyGroupCnC.getMajorGroupRef());
                }

                return existingFamilyGroupCnC;
            })
            .map(familyGroupCnCRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, familyGroupCnC.getId().toString())
        );
    }

    /**
     * {@code GET  /family-group-cn-cs} : get all the familyGroupCnCS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of familyGroupCnCS in body.
     */
    @GetMapping("")
    public List<FamilyGroupCnC> getAllFamilyGroupCnCS() {
        LOG.debug("REST request to get all FamilyGroupCnCS");
        return familyGroupCnCRepository.findAll();
    }

    /**
     * {@code GET  /family-group-cn-cs/:id} : get the "id" familyGroupCnC.
     *
     * @param id the id of the familyGroupCnC to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the familyGroupCnC, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FamilyGroupCnC> getFamilyGroupCnC(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get FamilyGroupCnC : {}", id);
        Optional<FamilyGroupCnC> familyGroupCnC = familyGroupCnCRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(familyGroupCnC);
    }

    /**
     * {@code DELETE  /family-group-cn-cs/:id} : delete the "id" familyGroupCnC.
     *
     * @param id the id of the familyGroupCnC to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFamilyGroupCnC(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete FamilyGroupCnC : {}", id);
        familyGroupCnCRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
