package mc.sbm.simphonycloud.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.Societe;
import mc.sbm.simphonycloud.repository.SocieteRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.Societe}.
 */
@RestController
@RequestMapping("/api/societes")
@Transactional
public class SocieteResource {

    private static final Logger LOG = LoggerFactory.getLogger(SocieteResource.class);

    private static final String ENTITY_NAME = "societe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SocieteRepository societeRepository;

    public SocieteResource(SocieteRepository societeRepository) {
        this.societeRepository = societeRepository;
    }

    /**
     * {@code POST  /societes} : Create a new societe.
     *
     * @param societe the societe to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new societe, or with status {@code 400 (Bad Request)} if the societe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Societe> createSociete(@RequestBody Societe societe) throws URISyntaxException {
        LOG.debug("REST request to save Societe : {}", societe);
        if (societe.getId() != null) {
            throw new BadRequestAlertException("A new societe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        societe = societeRepository.save(societe);
        return ResponseEntity.created(new URI("/api/societes/" + societe.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, societe.getId().toString()))
            .body(societe);
    }

    /**
     * {@code PUT  /societes/:id} : Updates an existing societe.
     *
     * @param id the id of the societe to save.
     * @param societe the societe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societe,
     * or with status {@code 400 (Bad Request)} if the societe is not valid,
     * or with status {@code 500 (Internal Server Error)} if the societe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Societe> updateSociete(@PathVariable(value = "id", required = false) final Long id, @RequestBody Societe societe)
        throws URISyntaxException {
        LOG.debug("REST request to update Societe : {}, {}", id, societe);
        if (societe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, societe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!societeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        societe = societeRepository.save(societe);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, societe.getId().toString()))
            .body(societe);
    }

    /**
     * {@code PATCH  /societes/:id} : Partial updates given fields of an existing societe, field will ignore if it is null
     *
     * @param id the id of the societe to save.
     * @param societe the societe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societe,
     * or with status {@code 400 (Bad Request)} if the societe is not valid,
     * or with status {@code 404 (Not Found)} if the societe is not found,
     * or with status {@code 500 (Internal Server Error)} if the societe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Societe> partialUpdateSociete(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Societe societe
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Societe partially : {}, {}", id, societe);
        if (societe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, societe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!societeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Societe> result = societeRepository
            .findById(societe.getId())
            .map(existingSociete -> {
                if (societe.getNom() != null) {
                    existingSociete.setNom(societe.getNom());
                }
                if (societe.getNomCourt() != null) {
                    existingSociete.setNomCourt(societe.getNomCourt());
                }

                return existingSociete;
            })
            .map(societeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, societe.getId().toString())
        );
    }

    /**
     * {@code GET  /societes} : get all the societes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of societes in body.
     */
    @GetMapping("")
    public List<Societe> getAllSocietes() {
        LOG.debug("REST request to get all Societes");
        return societeRepository.findAll();
    }

    /**
     * {@code GET  /societes/:id} : get the "id" societe.
     *
     * @param id the id of the societe to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the societe, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Societe> getSociete(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Societe : {}", id);
        Optional<Societe> societe = societeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(societe);
    }

    /**
     * {@code DELETE  /societes/:id} : delete the "id" societe.
     *
     * @param id the id of the societe to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSociete(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Societe : {}", id);
        societeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
