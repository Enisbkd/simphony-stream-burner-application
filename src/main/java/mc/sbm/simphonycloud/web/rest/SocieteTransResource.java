package mc.sbm.simphonycloud.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.SocieteTrans;
import mc.sbm.simphonycloud.repository.SocieteTransRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.SocieteTrans}.
 */
@RestController
@RequestMapping("/api/societe-trans")
@Transactional
public class SocieteTransResource {

    private static final Logger LOG = LoggerFactory.getLogger(SocieteTransResource.class);

    private static final String ENTITY_NAME = "societeTrans";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SocieteTransRepository societeTransRepository;

    public SocieteTransResource(SocieteTransRepository societeTransRepository) {
        this.societeTransRepository = societeTransRepository;
    }

    /**
     * {@code POST  /societe-trans} : Create a new societeTrans.
     *
     * @param societeTrans the societeTrans to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new societeTrans, or with status {@code 400 (Bad Request)} if the societeTrans has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SocieteTrans> createSocieteTrans(@RequestBody SocieteTrans societeTrans) throws URISyntaxException {
        LOG.debug("REST request to save SocieteTrans : {}", societeTrans);
        if (societeTrans.getId() != null) {
            throw new BadRequestAlertException("A new societeTrans cannot already have an ID", ENTITY_NAME, "idexists");
        }
        societeTrans = societeTransRepository.save(societeTrans);
        return ResponseEntity.created(new URI("/api/societe-trans/" + societeTrans.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, societeTrans.getId().toString()))
            .body(societeTrans);
    }

    /**
     * {@code PUT  /societe-trans/:id} : Updates an existing societeTrans.
     *
     * @param id the id of the societeTrans to save.
     * @param societeTrans the societeTrans to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societeTrans,
     * or with status {@code 400 (Bad Request)} if the societeTrans is not valid,
     * or with status {@code 500 (Internal Server Error)} if the societeTrans couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SocieteTrans> updateSocieteTrans(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SocieteTrans societeTrans
    ) throws URISyntaxException {
        LOG.debug("REST request to update SocieteTrans : {}, {}", id, societeTrans);
        if (societeTrans.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, societeTrans.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!societeTransRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        societeTrans = societeTransRepository.save(societeTrans);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, societeTrans.getId().toString()))
            .body(societeTrans);
    }

    /**
     * {@code PATCH  /societe-trans/:id} : Partial updates given fields of an existing societeTrans, field will ignore if it is null
     *
     * @param id the id of the societeTrans to save.
     * @param societeTrans the societeTrans to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societeTrans,
     * or with status {@code 400 (Bad Request)} if the societeTrans is not valid,
     * or with status {@code 404 (Not Found)} if the societeTrans is not found,
     * or with status {@code 500 (Internal Server Error)} if the societeTrans couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SocieteTrans> partialUpdateSocieteTrans(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SocieteTrans societeTrans
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SocieteTrans partially : {}, {}", id, societeTrans);
        if (societeTrans.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, societeTrans.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!societeTransRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SocieteTrans> result = societeTransRepository
            .findById(societeTrans.getId())
            .map(existingSocieteTrans -> {
                if (societeTrans.getNom() != null) {
                    existingSocieteTrans.setNom(societeTrans.getNom());
                }
                if (societeTrans.getNomCourt() != null) {
                    existingSocieteTrans.setNomCourt(societeTrans.getNomCourt());
                }

                return existingSocieteTrans;
            })
            .map(societeTransRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, societeTrans.getId().toString())
        );
    }

    /**
     * {@code GET  /societe-trans} : get all the societeTrans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of societeTrans in body.
     */
    @GetMapping("")
    public List<SocieteTrans> getAllSocieteTrans() {
        LOG.debug("REST request to get all SocieteTrans");
        return societeTransRepository.findAll();
    }

    /**
     * {@code GET  /societe-trans/:id} : get the "id" societeTrans.
     *
     * @param id the id of the societeTrans to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the societeTrans, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SocieteTrans> getSocieteTrans(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SocieteTrans : {}", id);
        Optional<SocieteTrans> societeTrans = societeTransRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(societeTrans);
    }

    /**
     * {@code DELETE  /societe-trans/:id} : delete the "id" societeTrans.
     *
     * @param id the id of the societeTrans to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSocieteTrans(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SocieteTrans : {}", id);
        societeTransRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
