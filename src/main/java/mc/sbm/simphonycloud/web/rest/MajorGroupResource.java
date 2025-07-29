package mc.sbm.simphonycloud.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.MajorGroup;
import mc.sbm.simphonycloud.repository.MajorGroupRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.MajorGroup}.
 */
@RestController
@RequestMapping("/api/major-groups")
@Transactional
public class MajorGroupResource {

    private static final Logger LOG = LoggerFactory.getLogger(MajorGroupResource.class);

    private static final String ENTITY_NAME = "majorGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MajorGroupRepository majorGroupRepository;

    public MajorGroupResource(MajorGroupRepository majorGroupRepository) {
        this.majorGroupRepository = majorGroupRepository;
    }

    /**
     * {@code POST  /major-groups} : Create a new majorGroup.
     *
     * @param majorGroup the majorGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new majorGroup, or with status {@code 400 (Bad Request)} if the majorGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MajorGroup> createMajorGroup(@RequestBody MajorGroup majorGroup) throws URISyntaxException {
        LOG.debug("REST request to save MajorGroup : {}", majorGroup);
        if (majorGroup.getId() != null) {
            throw new BadRequestAlertException("A new majorGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        majorGroup = majorGroupRepository.save(majorGroup);
        return ResponseEntity.created(new URI("/api/major-groups/" + majorGroup.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, majorGroup.getId().toString()))
            .body(majorGroup);
    }

    /**
     * {@code PUT  /major-groups/:id} : Updates an existing majorGroup.
     *
     * @param id the id of the majorGroup to save.
     * @param majorGroup the majorGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated majorGroup,
     * or with status {@code 400 (Bad Request)} if the majorGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the majorGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MajorGroup> updateMajorGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MajorGroup majorGroup
    ) throws URISyntaxException {
        LOG.debug("REST request to update MajorGroup : {}, {}", id, majorGroup);
        if (majorGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, majorGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!majorGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        majorGroup = majorGroupRepository.save(majorGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, majorGroup.getId().toString()))
            .body(majorGroup);
    }

    /**
     * {@code PATCH  /major-groups/:id} : Partial updates given fields of an existing majorGroup, field will ignore if it is null
     *
     * @param id the id of the majorGroup to save.
     * @param majorGroup the majorGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated majorGroup,
     * or with status {@code 400 (Bad Request)} if the majorGroup is not valid,
     * or with status {@code 404 (Not Found)} if the majorGroup is not found,
     * or with status {@code 500 (Internal Server Error)} if the majorGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MajorGroup> partialUpdateMajorGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MajorGroup majorGroup
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update MajorGroup partially : {}, {}", id, majorGroup);
        if (majorGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, majorGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!majorGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MajorGroup> result = majorGroupRepository
            .findById(majorGroup.getId())
            .map(existingMajorGroup -> {
                if (majorGroup.getNom() != null) {
                    existingMajorGroup.setNom(majorGroup.getNom());
                }
                if (majorGroup.getNomCourt() != null) {
                    existingMajorGroup.setNomCourt(majorGroup.getNomCourt());
                }
                if (majorGroup.getPointDeVenteRef() != null) {
                    existingMajorGroup.setPointDeVenteRef(majorGroup.getPointDeVenteRef());
                }

                return existingMajorGroup;
            })
            .map(majorGroupRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, majorGroup.getId().toString())
        );
    }

    /**
     * {@code GET  /major-groups} : get all the majorGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of majorGroups in body.
     */
    @GetMapping("")
    public List<MajorGroup> getAllMajorGroups() {
        LOG.debug("REST request to get all MajorGroups");
        return majorGroupRepository.findAll();
    }

    /**
     * {@code GET  /major-groups/:id} : get the "id" majorGroup.
     *
     * @param id the id of the majorGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the majorGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MajorGroup> getMajorGroup(@PathVariable("id") Long id) {
        LOG.debug("REST request to get MajorGroup : {}", id);
        Optional<MajorGroup> majorGroup = majorGroupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(majorGroup);
    }

    /**
     * {@code DELETE  /major-groups/:id} : delete the "id" majorGroup.
     *
     * @param id the id of the majorGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMajorGroup(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete MajorGroup : {}", id);
        majorGroupRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
