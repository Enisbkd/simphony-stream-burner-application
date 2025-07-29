package mc.sbm.simphonycloud.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.PartieDeJournee;
import mc.sbm.simphonycloud.repository.PartieDeJourneeRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.PartieDeJournee}.
 */
@RestController
@RequestMapping("/api/partie-de-journees")
@Transactional
public class PartieDeJourneeResource {

    private static final Logger LOG = LoggerFactory.getLogger(PartieDeJourneeResource.class);

    private static final String ENTITY_NAME = "partieDeJournee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartieDeJourneeRepository partieDeJourneeRepository;

    public PartieDeJourneeResource(PartieDeJourneeRepository partieDeJourneeRepository) {
        this.partieDeJourneeRepository = partieDeJourneeRepository;
    }

    /**
     * {@code POST  /partie-de-journees} : Create a new partieDeJournee.
     *
     * @param partieDeJournee the partieDeJournee to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partieDeJournee, or with status {@code 400 (Bad Request)} if the partieDeJournee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PartieDeJournee> createPartieDeJournee(@Valid @RequestBody PartieDeJournee partieDeJournee)
        throws URISyntaxException {
        LOG.debug("REST request to save PartieDeJournee : {}", partieDeJournee);
        if (partieDeJournee.getId() != null) {
            throw new BadRequestAlertException("A new partieDeJournee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        partieDeJournee = partieDeJourneeRepository.save(partieDeJournee);
        return ResponseEntity.created(new URI("/api/partie-de-journees/" + partieDeJournee.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, partieDeJournee.getId().toString()))
            .body(partieDeJournee);
    }

    /**
     * {@code PUT  /partie-de-journees/:id} : Updates an existing partieDeJournee.
     *
     * @param id the id of the partieDeJournee to save.
     * @param partieDeJournee the partieDeJournee to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partieDeJournee,
     * or with status {@code 400 (Bad Request)} if the partieDeJournee is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partieDeJournee couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PartieDeJournee> updatePartieDeJournee(
        @PathVariable(value = "id", required = false) final Integer id,
        @Valid @RequestBody PartieDeJournee partieDeJournee
    ) throws URISyntaxException {
        LOG.debug("REST request to update PartieDeJournee : {}, {}", id, partieDeJournee);
        if (partieDeJournee.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partieDeJournee.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partieDeJourneeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        partieDeJournee = partieDeJourneeRepository.save(partieDeJournee);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partieDeJournee.getId().toString()))
            .body(partieDeJournee);
    }

    /**
     * {@code PATCH  /partie-de-journees/:id} : Partial updates given fields of an existing partieDeJournee, field will ignore if it is null
     *
     * @param id the id of the partieDeJournee to save.
     * @param partieDeJournee the partieDeJournee to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partieDeJournee,
     * or with status {@code 400 (Bad Request)} if the partieDeJournee is not valid,
     * or with status {@code 404 (Not Found)} if the partieDeJournee is not found,
     * or with status {@code 500 (Internal Server Error)} if the partieDeJournee couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PartieDeJournee> partialUpdatePartieDeJournee(
        @PathVariable(value = "id", required = false) final Integer id,
        @NotNull @RequestBody PartieDeJournee partieDeJournee
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PartieDeJournee partially : {}, {}", id, partieDeJournee);
        if (partieDeJournee.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partieDeJournee.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partieDeJourneeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PartieDeJournee> result = partieDeJourneeRepository
            .findById(partieDeJournee.getId())
            .map(existingPartieDeJournee -> {
                if (partieDeJournee.getTimeRangeStart() != null) {
                    existingPartieDeJournee.setTimeRangeStart(partieDeJournee.getTimeRangeStart());
                }
                if (partieDeJournee.getTimeRangeEnd() != null) {
                    existingPartieDeJournee.setTimeRangeEnd(partieDeJournee.getTimeRangeEnd());
                }
                if (partieDeJournee.getNom() != null) {
                    existingPartieDeJournee.setNom(partieDeJournee.getNom());
                }

                return existingPartieDeJournee;
            })
            .map(partieDeJourneeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partieDeJournee.getId().toString())
        );
    }

    /**
     * {@code GET  /partie-de-journees} : get all the partieDeJournees.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partieDeJournees in body.
     */
    @GetMapping("")
    public List<PartieDeJournee> getAllPartieDeJournees() {
        LOG.debug("REST request to get all PartieDeJournees");
        return partieDeJourneeRepository.findAll();
    }

    /**
     * {@code GET  /partie-de-journees/:id} : get the "id" partieDeJournee.
     *
     * @param id the id of the partieDeJournee to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partieDeJournee, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PartieDeJournee> getPartieDeJournee(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get PartieDeJournee : {}", id);
        Optional<PartieDeJournee> partieDeJournee = partieDeJourneeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partieDeJournee);
    }

    /**
     * {@code DELETE  /partie-de-journees/:id} : delete the "id" partieDeJournee.
     *
     * @param id the id of the partieDeJournee to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartieDeJournee(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete PartieDeJournee : {}", id);
        partieDeJourneeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
