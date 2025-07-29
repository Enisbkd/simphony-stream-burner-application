package mc.sbm.simphonycloud.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.TaxeClassTrans;
import mc.sbm.simphonycloud.repository.TaxeClassTransRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.TaxeClassTrans}.
 */
@RestController
@RequestMapping("/api/taxe-class-trans")
@Transactional
public class TaxeClassTransResource {

    private static final Logger LOG = LoggerFactory.getLogger(TaxeClassTransResource.class);

    private static final String ENTITY_NAME = "taxeClassTrans";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaxeClassTransRepository taxeClassTransRepository;

    public TaxeClassTransResource(TaxeClassTransRepository taxeClassTransRepository) {
        this.taxeClassTransRepository = taxeClassTransRepository;
    }

    /**
     * {@code POST  /taxe-class-trans} : Create a new taxeClassTrans.
     *
     * @param taxeClassTrans the taxeClassTrans to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taxeClassTrans, or with status {@code 400 (Bad Request)} if the taxeClassTrans has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TaxeClassTrans> createTaxeClassTrans(@RequestBody TaxeClassTrans taxeClassTrans) throws URISyntaxException {
        LOG.debug("REST request to save TaxeClassTrans : {}", taxeClassTrans);
        if (taxeClassTrans.getId() != null) {
            throw new BadRequestAlertException("A new taxeClassTrans cannot already have an ID", ENTITY_NAME, "idexists");
        }
        taxeClassTrans = taxeClassTransRepository.save(taxeClassTrans);
        return ResponseEntity.created(new URI("/api/taxe-class-trans/" + taxeClassTrans.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, taxeClassTrans.getId().toString()))
            .body(taxeClassTrans);
    }

    /**
     * {@code PUT  /taxe-class-trans/:id} : Updates an existing taxeClassTrans.
     *
     * @param id the id of the taxeClassTrans to save.
     * @param taxeClassTrans the taxeClassTrans to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxeClassTrans,
     * or with status {@code 400 (Bad Request)} if the taxeClassTrans is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taxeClassTrans couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaxeClassTrans> updateTaxeClassTrans(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody TaxeClassTrans taxeClassTrans
    ) throws URISyntaxException {
        LOG.debug("REST request to update TaxeClassTrans : {}, {}", id, taxeClassTrans);
        if (taxeClassTrans.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taxeClassTrans.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taxeClassTransRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        taxeClassTrans = taxeClassTransRepository.save(taxeClassTrans);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taxeClassTrans.getId().toString()))
            .body(taxeClassTrans);
    }

    /**
     * {@code PATCH  /taxe-class-trans/:id} : Partial updates given fields of an existing taxeClassTrans, field will ignore if it is null
     *
     * @param id the id of the taxeClassTrans to save.
     * @param taxeClassTrans the taxeClassTrans to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxeClassTrans,
     * or with status {@code 400 (Bad Request)} if the taxeClassTrans is not valid,
     * or with status {@code 404 (Not Found)} if the taxeClassTrans is not found,
     * or with status {@code 500 (Internal Server Error)} if the taxeClassTrans couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TaxeClassTrans> partialUpdateTaxeClassTrans(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody TaxeClassTrans taxeClassTrans
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update TaxeClassTrans partially : {}, {}", id, taxeClassTrans);
        if (taxeClassTrans.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taxeClassTrans.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taxeClassTransRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaxeClassTrans> result = taxeClassTransRepository
            .findById(taxeClassTrans.getId())
            .map(existingTaxeClassTrans -> {
                if (taxeClassTrans.getOrgShortName() != null) {
                    existingTaxeClassTrans.setOrgShortName(taxeClassTrans.getOrgShortName());
                }
                if (taxeClassTrans.getLocRef() != null) {
                    existingTaxeClassTrans.setLocRef(taxeClassTrans.getLocRef());
                }
                if (taxeClassTrans.getRvcRef() != null) {
                    existingTaxeClassTrans.setRvcRef(taxeClassTrans.getRvcRef());
                }
                if (taxeClassTrans.getTaxClassId() != null) {
                    existingTaxeClassTrans.setTaxClassId(taxeClassTrans.getTaxClassId());
                }
                if (taxeClassTrans.getActiveTaxRateRefs() != null) {
                    existingTaxeClassTrans.setActiveTaxRateRefs(taxeClassTrans.getActiveTaxRateRefs());
                }

                return existingTaxeClassTrans;
            })
            .map(taxeClassTransRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taxeClassTrans.getId().toString())
        );
    }

    /**
     * {@code GET  /taxe-class-trans} : get all the taxeClassTrans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taxeClassTrans in body.
     */
    @GetMapping("")
    public List<TaxeClassTrans> getAllTaxeClassTrans() {
        LOG.debug("REST request to get all TaxeClassTrans");
        return taxeClassTransRepository.findAll();
    }

    /**
     * {@code GET  /taxe-class-trans/:id} : get the "id" taxeClassTrans.
     *
     * @param id the id of the taxeClassTrans to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taxeClassTrans, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaxeClassTrans> getTaxeClassTrans(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get TaxeClassTrans : {}", id);
        Optional<TaxeClassTrans> taxeClassTrans = taxeClassTransRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(taxeClassTrans);
    }

    /**
     * {@code DELETE  /taxe-class-trans/:id} : delete the "id" taxeClassTrans.
     *
     * @param id the id of the taxeClassTrans to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaxeClassTrans(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete TaxeClassTrans : {}", id);
        taxeClassTransRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
