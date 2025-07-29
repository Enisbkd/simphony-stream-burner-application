package mc.sbm.simphonycloud.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.CheckTrans;
import mc.sbm.simphonycloud.repository.CheckTransRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.CheckTrans}.
 */
@RestController
@RequestMapping("/api/check-trans")
@Transactional
public class CheckTransResource {

    private static final Logger LOG = LoggerFactory.getLogger(CheckTransResource.class);

    private static final String ENTITY_NAME = "checkTrans";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CheckTransRepository checkTransRepository;

    public CheckTransResource(CheckTransRepository checkTransRepository) {
        this.checkTransRepository = checkTransRepository;
    }

    /**
     * {@code POST  /check-trans} : Create a new checkTrans.
     *
     * @param checkTrans the checkTrans to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new checkTrans, or with status {@code 400 (Bad Request)} if the checkTrans has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CheckTrans> createCheckTrans(@RequestBody CheckTrans checkTrans) throws URISyntaxException {
        LOG.debug("REST request to save CheckTrans : {}", checkTrans);
        if (checkTrans.getId() != null) {
            throw new BadRequestAlertException("A new checkTrans cannot already have an ID", ENTITY_NAME, "idexists");
        }
        checkTrans = checkTransRepository.save(checkTrans);
        return ResponseEntity.created(new URI("/api/check-trans/" + checkTrans.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, checkTrans.getId().toString()))
            .body(checkTrans);
    }

    /**
     * {@code PUT  /check-trans/:id} : Updates an existing checkTrans.
     *
     * @param id the id of the checkTrans to save.
     * @param checkTrans the checkTrans to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkTrans,
     * or with status {@code 400 (Bad Request)} if the checkTrans is not valid,
     * or with status {@code 500 (Internal Server Error)} if the checkTrans couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CheckTrans> updateCheckTrans(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody CheckTrans checkTrans
    ) throws URISyntaxException {
        LOG.debug("REST request to update CheckTrans : {}, {}", id, checkTrans);
        if (checkTrans.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, checkTrans.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkTransRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        checkTrans = checkTransRepository.save(checkTrans);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, checkTrans.getId().toString()))
            .body(checkTrans);
    }

    /**
     * {@code PATCH  /check-trans/:id} : Partial updates given fields of an existing checkTrans, field will ignore if it is null
     *
     * @param id the id of the checkTrans to save.
     * @param checkTrans the checkTrans to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkTrans,
     * or with status {@code 400 (Bad Request)} if the checkTrans is not valid,
     * or with status {@code 404 (Not Found)} if the checkTrans is not found,
     * or with status {@code 500 (Internal Server Error)} if the checkTrans couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CheckTrans> partialUpdateCheckTrans(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody CheckTrans checkTrans
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CheckTrans partially : {}, {}", id, checkTrans);
        if (checkTrans.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, checkTrans.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkTransRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CheckTrans> result = checkTransRepository
            .findById(checkTrans.getId())
            .map(existingCheckTrans -> {
                if (checkTrans.getRvcRef() != null) {
                    existingCheckTrans.setRvcRef(checkTrans.getRvcRef());
                }
                if (checkTrans.getCheckRef() != null) {
                    existingCheckTrans.setCheckRef(checkTrans.getCheckRef());
                }
                if (checkTrans.getCheckNumber() != null) {
                    existingCheckTrans.setCheckNumber(checkTrans.getCheckNumber());
                }
                if (checkTrans.getCheckName() != null) {
                    existingCheckTrans.setCheckName(checkTrans.getCheckName());
                }
                if (checkTrans.getCheckEmployeeRef() != null) {
                    existingCheckTrans.setCheckEmployeeRef(checkTrans.getCheckEmployeeRef());
                }
                if (checkTrans.getOrderTypeRef() != null) {
                    existingCheckTrans.setOrderTypeRef(checkTrans.getOrderTypeRef());
                }
                if (checkTrans.getOrderChannelRef() != null) {
                    existingCheckTrans.setOrderChannelRef(checkTrans.getOrderChannelRef());
                }
                if (checkTrans.getTableName() != null) {
                    existingCheckTrans.setTableName(checkTrans.getTableName());
                }
                if (checkTrans.getTableGroupNumber() != null) {
                    existingCheckTrans.setTableGroupNumber(checkTrans.getTableGroupNumber());
                }
                if (checkTrans.getOpenTime() != null) {
                    existingCheckTrans.setOpenTime(checkTrans.getOpenTime());
                }
                if (checkTrans.getGuestCount() != null) {
                    existingCheckTrans.setGuestCount(checkTrans.getGuestCount());
                }
                if (checkTrans.getLanguage() != null) {
                    existingCheckTrans.setLanguage(checkTrans.getLanguage());
                }
                if (checkTrans.getIsTrainingCheck() != null) {
                    existingCheckTrans.setIsTrainingCheck(checkTrans.getIsTrainingCheck());
                }
                if (checkTrans.getStatus() != null) {
                    existingCheckTrans.setStatus(checkTrans.getStatus());
                }
                if (checkTrans.getPreparationStatus() != null) {
                    existingCheckTrans.setPreparationStatus(checkTrans.getPreparationStatus());
                }
                if (checkTrans.getSubtotal() != null) {
                    existingCheckTrans.setSubtotal(checkTrans.getSubtotal());
                }
                if (checkTrans.getSubtotalDiscountTotal() != null) {
                    existingCheckTrans.setSubtotalDiscountTotal(checkTrans.getSubtotalDiscountTotal());
                }
                if (checkTrans.getAutoServiceChargeTotal() != null) {
                    existingCheckTrans.setAutoServiceChargeTotal(checkTrans.getAutoServiceChargeTotal());
                }
                if (checkTrans.getServiceChargeTotal() != null) {
                    existingCheckTrans.setServiceChargeTotal(checkTrans.getServiceChargeTotal());
                }
                if (checkTrans.getTaxTotal() != null) {
                    existingCheckTrans.setTaxTotal(checkTrans.getTaxTotal());
                }
                if (checkTrans.getPaymentTotal() != null) {
                    existingCheckTrans.setPaymentTotal(checkTrans.getPaymentTotal());
                }
                if (checkTrans.getTotalDue() != null) {
                    existingCheckTrans.setTotalDue(checkTrans.getTotalDue());
                }
                if (checkTrans.getTaxRateId() != null) {
                    existingCheckTrans.setTaxRateId(checkTrans.getTaxRateId());
                }

                return existingCheckTrans;
            })
            .map(checkTransRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, checkTrans.getId().toString())
        );
    }

    /**
     * {@code GET  /check-trans} : get all the checkTrans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkTrans in body.
     */
    @GetMapping("")
    public List<CheckTrans> getAllCheckTrans() {
        LOG.debug("REST request to get all CheckTrans");
        return checkTransRepository.findAll();
    }

    /**
     * {@code GET  /check-trans/:id} : get the "id" checkTrans.
     *
     * @param id the id of the checkTrans to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the checkTrans, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CheckTrans> getCheckTrans(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get CheckTrans : {}", id);
        Optional<CheckTrans> checkTrans = checkTransRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(checkTrans);
    }

    /**
     * {@code DELETE  /check-trans/:id} : delete the "id" checkTrans.
     *
     * @param id the id of the checkTrans to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCheckTrans(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete CheckTrans : {}", id);
        checkTransRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
