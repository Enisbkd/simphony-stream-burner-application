package mc.sbm.simphonycloud.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.TaxeRateTrans;
import mc.sbm.simphonycloud.repository.TaxeRateTransRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.TaxeRateTrans}.
 */
@RestController
@RequestMapping("/api/taxe-rate-trans")
@Transactional
public class TaxeRateTransResource {

    private static final Logger LOG = LoggerFactory.getLogger(TaxeRateTransResource.class);

    private static final String ENTITY_NAME = "taxeRateTrans";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaxeRateTransRepository taxeRateTransRepository;

    public TaxeRateTransResource(TaxeRateTransRepository taxeRateTransRepository) {
        this.taxeRateTransRepository = taxeRateTransRepository;
    }

    /**
     * {@code POST  /taxe-rate-trans} : Create a new taxeRateTrans.
     *
     * @param taxeRateTrans the taxeRateTrans to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taxeRateTrans, or with status {@code 400 (Bad Request)} if the taxeRateTrans has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TaxeRateTrans> createTaxeRateTrans(@RequestBody TaxeRateTrans taxeRateTrans) throws URISyntaxException {
        LOG.debug("REST request to save TaxeRateTrans : {}", taxeRateTrans);
        if (taxeRateTrans.getId() != null) {
            throw new BadRequestAlertException("A new taxeRateTrans cannot already have an ID", ENTITY_NAME, "idexists");
        }
        taxeRateTrans = taxeRateTransRepository.save(taxeRateTrans);
        return ResponseEntity.created(new URI("/api/taxe-rate-trans/" + taxeRateTrans.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, taxeRateTrans.getId().toString()))
            .body(taxeRateTrans);
    }

    /**
     * {@code PUT  /taxe-rate-trans/:id} : Updates an existing taxeRateTrans.
     *
     * @param id the id of the taxeRateTrans to save.
     * @param taxeRateTrans the taxeRateTrans to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxeRateTrans,
     * or with status {@code 400 (Bad Request)} if the taxeRateTrans is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taxeRateTrans couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaxeRateTrans> updateTaxeRateTrans(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaxeRateTrans taxeRateTrans
    ) throws URISyntaxException {
        LOG.debug("REST request to update TaxeRateTrans : {}, {}", id, taxeRateTrans);
        if (taxeRateTrans.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taxeRateTrans.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taxeRateTransRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        taxeRateTrans = taxeRateTransRepository.save(taxeRateTrans);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taxeRateTrans.getId().toString()))
            .body(taxeRateTrans);
    }

    /**
     * {@code PATCH  /taxe-rate-trans/:id} : Partial updates given fields of an existing taxeRateTrans, field will ignore if it is null
     *
     * @param id the id of the taxeRateTrans to save.
     * @param taxeRateTrans the taxeRateTrans to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxeRateTrans,
     * or with status {@code 400 (Bad Request)} if the taxeRateTrans is not valid,
     * or with status {@code 404 (Not Found)} if the taxeRateTrans is not found,
     * or with status {@code 500 (Internal Server Error)} if the taxeRateTrans couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TaxeRateTrans> partialUpdateTaxeRateTrans(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaxeRateTrans taxeRateTrans
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update TaxeRateTrans partially : {}, {}", id, taxeRateTrans);
        if (taxeRateTrans.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taxeRateTrans.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taxeRateTransRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaxeRateTrans> result = taxeRateTransRepository
            .findById(taxeRateTrans.getId())
            .map(existingTaxeRateTrans -> {
                if (taxeRateTrans.getOrgShortName() != null) {
                    existingTaxeRateTrans.setOrgShortName(taxeRateTrans.getOrgShortName());
                }
                if (taxeRateTrans.getLocRef() != null) {
                    existingTaxeRateTrans.setLocRef(taxeRateTrans.getLocRef());
                }
                if (taxeRateTrans.getRvcRef() != null) {
                    existingTaxeRateTrans.setRvcRef(taxeRateTrans.getRvcRef());
                }
                if (taxeRateTrans.getTaxRateId() != null) {
                    existingTaxeRateTrans.setTaxRateId(taxeRateTrans.getTaxRateId());
                }
                if (taxeRateTrans.getPercentage() != null) {
                    existingTaxeRateTrans.setPercentage(taxeRateTrans.getPercentage());
                }
                if (taxeRateTrans.getTaxType() != null) {
                    existingTaxeRateTrans.setTaxType(taxeRateTrans.getTaxType());
                }
                if (taxeRateTrans.getNameFR() != null) {
                    existingTaxeRateTrans.setNameFR(taxeRateTrans.getNameFR());
                }
                if (taxeRateTrans.getNameEN() != null) {
                    existingTaxeRateTrans.setNameEN(taxeRateTrans.getNameEN());
                }

                return existingTaxeRateTrans;
            })
            .map(taxeRateTransRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taxeRateTrans.getId().toString())
        );
    }

    /**
     * {@code GET  /taxe-rate-trans} : get all the taxeRateTrans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taxeRateTrans in body.
     */
    @GetMapping("")
    public List<TaxeRateTrans> getAllTaxeRateTrans() {
        LOG.debug("REST request to get all TaxeRateTrans");
        return taxeRateTransRepository.findAll();
    }

    /**
     * {@code GET  /taxe-rate-trans/:id} : get the "id" taxeRateTrans.
     *
     * @param id the id of the taxeRateTrans to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taxeRateTrans, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaxeRateTrans> getTaxeRateTrans(@PathVariable("id") Long id) {
        LOG.debug("REST request to get TaxeRateTrans : {}", id);
        Optional<TaxeRateTrans> taxeRateTrans = taxeRateTransRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(taxeRateTrans);
    }

    /**
     * {@code DELETE  /taxe-rate-trans/:id} : delete the "id" taxeRateTrans.
     *
     * @param id the id of the taxeRateTrans to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaxeRateTrans(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete TaxeRateTrans : {}", id);
        taxeRateTransRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
