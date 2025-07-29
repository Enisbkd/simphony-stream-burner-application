package mc.sbm.simphonycloud.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.TaxeBI;
import mc.sbm.simphonycloud.repository.TaxeBIRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.TaxeBI}.
 */
@RestController
@RequestMapping("/api/taxe-bis")
@Transactional
public class TaxeBIResource {

    private static final Logger LOG = LoggerFactory.getLogger(TaxeBIResource.class);

    private static final String ENTITY_NAME = "taxeBI";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaxeBIRepository taxeBIRepository;

    public TaxeBIResource(TaxeBIRepository taxeBIRepository) {
        this.taxeBIRepository = taxeBIRepository;
    }

    /**
     * {@code POST  /taxe-bis} : Create a new taxeBI.
     *
     * @param taxeBI the taxeBI to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taxeBI, or with status {@code 400 (Bad Request)} if the taxeBI has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TaxeBI> createTaxeBI(@RequestBody TaxeBI taxeBI) throws URISyntaxException {
        LOG.debug("REST request to save TaxeBI : {}", taxeBI);
        if (taxeBI.getId() != null) {
            throw new BadRequestAlertException("A new taxeBI cannot already have an ID", ENTITY_NAME, "idexists");
        }
        taxeBI = taxeBIRepository.save(taxeBI);
        return ResponseEntity.created(new URI("/api/taxe-bis/" + taxeBI.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, taxeBI.getId().toString()))
            .body(taxeBI);
    }

    /**
     * {@code PUT  /taxe-bis/:id} : Updates an existing taxeBI.
     *
     * @param id the id of the taxeBI to save.
     * @param taxeBI the taxeBI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxeBI,
     * or with status {@code 400 (Bad Request)} if the taxeBI is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taxeBI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaxeBI> updateTaxeBI(@PathVariable(value = "id", required = false) final Integer id, @RequestBody TaxeBI taxeBI)
        throws URISyntaxException {
        LOG.debug("REST request to update TaxeBI : {}, {}", id, taxeBI);
        if (taxeBI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taxeBI.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taxeBIRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        taxeBI = taxeBIRepository.save(taxeBI);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taxeBI.getId().toString()))
            .body(taxeBI);
    }

    /**
     * {@code PATCH  /taxe-bis/:id} : Partial updates given fields of an existing taxeBI, field will ignore if it is null
     *
     * @param id the id of the taxeBI to save.
     * @param taxeBI the taxeBI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxeBI,
     * or with status {@code 400 (Bad Request)} if the taxeBI is not valid,
     * or with status {@code 404 (Not Found)} if the taxeBI is not found,
     * or with status {@code 500 (Internal Server Error)} if the taxeBI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TaxeBI> partialUpdateTaxeBI(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody TaxeBI taxeBI
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update TaxeBI partially : {}, {}", id, taxeBI);
        if (taxeBI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taxeBI.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taxeBIRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaxeBI> result = taxeBIRepository
            .findById(taxeBI.getId())
            .map(existingTaxeBI -> {
                if (taxeBI.getLocRef() != null) {
                    existingTaxeBI.setLocRef(taxeBI.getLocRef());
                }
                if (taxeBI.getNum() != null) {
                    existingTaxeBI.setNum(taxeBI.getNum());
                }
                if (taxeBI.getName() != null) {
                    existingTaxeBI.setName(taxeBI.getName());
                }
                if (taxeBI.getType() != null) {
                    existingTaxeBI.setType(taxeBI.getType());
                }

                return existingTaxeBI;
            })
            .map(taxeBIRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taxeBI.getId().toString())
        );
    }

    /**
     * {@code GET  /taxe-bis} : get all the taxeBIS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taxeBIS in body.
     */
    @GetMapping("")
    public List<TaxeBI> getAllTaxeBIS() {
        LOG.debug("REST request to get all TaxeBIS");
        return taxeBIRepository.findAll();
    }

    /**
     * {@code GET  /taxe-bis/:id} : get the "id" taxeBI.
     *
     * @param id the id of the taxeBI to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taxeBI, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaxeBI> getTaxeBI(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get TaxeBI : {}", id);
        Optional<TaxeBI> taxeBI = taxeBIRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(taxeBI);
    }

    /**
     * {@code DELETE  /taxe-bis/:id} : delete the "id" taxeBI.
     *
     * @param id the id of the taxeBI to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaxeBI(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete TaxeBI : {}", id);
        taxeBIRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
