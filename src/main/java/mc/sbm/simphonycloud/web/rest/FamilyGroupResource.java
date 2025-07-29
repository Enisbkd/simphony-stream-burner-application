package mc.sbm.simphonycloud.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.FamilyGroup;
import mc.sbm.simphonycloud.repository.FamilyGroupRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.FamilyGroup}.
 */
@RestController
@RequestMapping("/api/family-groups")
@Transactional
public class FamilyGroupResource {

    private static final Logger LOG = LoggerFactory.getLogger(FamilyGroupResource.class);

    private static final String ENTITY_NAME = "familyGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FamilyGroupRepository familyGroupRepository;

    public FamilyGroupResource(FamilyGroupRepository familyGroupRepository) {
        this.familyGroupRepository = familyGroupRepository;
    }

    /**
     * {@code POST  /family-groups} : Create a new familyGroup.
     *
     * @param familyGroup the familyGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new familyGroup, or with status {@code 400 (Bad Request)} if the familyGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FamilyGroup> createFamilyGroup(@RequestBody FamilyGroup familyGroup) throws URISyntaxException {
        LOG.debug("REST request to save FamilyGroup : {}", familyGroup);
        if (familyGroup.getId() != null) {
            throw new BadRequestAlertException("A new familyGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        familyGroup = familyGroupRepository.save(familyGroup);
        return ResponseEntity.created(new URI("/api/family-groups/" + familyGroup.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, familyGroup.getId().toString()))
            .body(familyGroup);
    }

    /**
     * {@code PUT  /family-groups/:id} : Updates an existing familyGroup.
     *
     * @param id the id of the familyGroup to save.
     * @param familyGroup the familyGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated familyGroup,
     * or with status {@code 400 (Bad Request)} if the familyGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the familyGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FamilyGroup> updateFamilyGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FamilyGroup familyGroup
    ) throws URISyntaxException {
        LOG.debug("REST request to update FamilyGroup : {}, {}", id, familyGroup);
        if (familyGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, familyGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!familyGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        familyGroup = familyGroupRepository.save(familyGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, familyGroup.getId().toString()))
            .body(familyGroup);
    }

    /**
     * {@code PATCH  /family-groups/:id} : Partial updates given fields of an existing familyGroup, field will ignore if it is null
     *
     * @param id the id of the familyGroup to save.
     * @param familyGroup the familyGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated familyGroup,
     * or with status {@code 400 (Bad Request)} if the familyGroup is not valid,
     * or with status {@code 404 (Not Found)} if the familyGroup is not found,
     * or with status {@code 500 (Internal Server Error)} if the familyGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FamilyGroup> partialUpdateFamilyGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FamilyGroup familyGroup
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FamilyGroup partially : {}, {}", id, familyGroup);
        if (familyGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, familyGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!familyGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FamilyGroup> result = familyGroupRepository
            .findById(familyGroup.getId())
            .map(existingFamilyGroup -> {
                if (familyGroup.getNom() != null) {
                    existingFamilyGroup.setNom(familyGroup.getNom());
                }
                if (familyGroup.getNomCourt() != null) {
                    existingFamilyGroup.setNomCourt(familyGroup.getNomCourt());
                }
                if (familyGroup.getMajorGroupRef() != null) {
                    existingFamilyGroup.setMajorGroupRef(familyGroup.getMajorGroupRef());
                }

                return existingFamilyGroup;
            })
            .map(familyGroupRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, familyGroup.getId().toString())
        );
    }

    /**
     * {@code GET  /family-groups} : get all the familyGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of familyGroups in body.
     */
    @GetMapping("")
    public List<FamilyGroup> getAllFamilyGroups() {
        LOG.debug("REST request to get all FamilyGroups");
        return familyGroupRepository.findAll();
    }

    /**
     * {@code GET  /family-groups/:id} : get the "id" familyGroup.
     *
     * @param id the id of the familyGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the familyGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FamilyGroup> getFamilyGroup(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FamilyGroup : {}", id);
        Optional<FamilyGroup> familyGroup = familyGroupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(familyGroup);
    }

    /**
     * {@code DELETE  /family-groups/:id} : delete the "id" familyGroup.
     *
     * @param id the id of the familyGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFamilyGroup(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete FamilyGroup : {}", id);
        familyGroupRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
