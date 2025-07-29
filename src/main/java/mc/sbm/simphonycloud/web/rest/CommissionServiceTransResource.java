package mc.sbm.simphonycloud.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.CommissionServiceTrans;
import mc.sbm.simphonycloud.repository.CommissionServiceTransRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.CommissionServiceTrans}.
 */
@RestController
@RequestMapping("/api/commission-service-trans")
@Transactional
public class CommissionServiceTransResource {

    private static final Logger LOG = LoggerFactory.getLogger(CommissionServiceTransResource.class);

    private static final String ENTITY_NAME = "commissionServiceTrans";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommissionServiceTransRepository commissionServiceTransRepository;

    public CommissionServiceTransResource(CommissionServiceTransRepository commissionServiceTransRepository) {
        this.commissionServiceTransRepository = commissionServiceTransRepository;
    }

    /**
     * {@code POST  /commission-service-trans} : Create a new commissionServiceTrans.
     *
     * @param commissionServiceTrans the commissionServiceTrans to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commissionServiceTrans, or with status {@code 400 (Bad Request)} if the commissionServiceTrans has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CommissionServiceTrans> createCommissionServiceTrans(@RequestBody CommissionServiceTrans commissionServiceTrans)
        throws URISyntaxException {
        LOG.debug("REST request to save CommissionServiceTrans : {}", commissionServiceTrans);
        if (commissionServiceTrans.getId() != null) {
            throw new BadRequestAlertException("A new commissionServiceTrans cannot already have an ID", ENTITY_NAME, "idexists");
        }
        commissionServiceTrans = commissionServiceTransRepository.save(commissionServiceTrans);
        return ResponseEntity.created(new URI("/api/commission-service-trans/" + commissionServiceTrans.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, commissionServiceTrans.getId().toString()))
            .body(commissionServiceTrans);
    }

    /**
     * {@code PUT  /commission-service-trans/:id} : Updates an existing commissionServiceTrans.
     *
     * @param id the id of the commissionServiceTrans to save.
     * @param commissionServiceTrans the commissionServiceTrans to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commissionServiceTrans,
     * or with status {@code 400 (Bad Request)} if the commissionServiceTrans is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commissionServiceTrans couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommissionServiceTrans> updateCommissionServiceTrans(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CommissionServiceTrans commissionServiceTrans
    ) throws URISyntaxException {
        LOG.debug("REST request to update CommissionServiceTrans : {}, {}", id, commissionServiceTrans);
        if (commissionServiceTrans.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commissionServiceTrans.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commissionServiceTransRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        commissionServiceTrans = commissionServiceTransRepository.save(commissionServiceTrans);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commissionServiceTrans.getId().toString()))
            .body(commissionServiceTrans);
    }

    /**
     * {@code PATCH  /commission-service-trans/:id} : Partial updates given fields of an existing commissionServiceTrans, field will ignore if it is null
     *
     * @param id the id of the commissionServiceTrans to save.
     * @param commissionServiceTrans the commissionServiceTrans to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commissionServiceTrans,
     * or with status {@code 400 (Bad Request)} if the commissionServiceTrans is not valid,
     * or with status {@code 404 (Not Found)} if the commissionServiceTrans is not found,
     * or with status {@code 500 (Internal Server Error)} if the commissionServiceTrans couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CommissionServiceTrans> partialUpdateCommissionServiceTrans(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CommissionServiceTrans commissionServiceTrans
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CommissionServiceTrans partially : {}, {}", id, commissionServiceTrans);
        if (commissionServiceTrans.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commissionServiceTrans.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commissionServiceTransRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CommissionServiceTrans> result = commissionServiceTransRepository
            .findById(commissionServiceTrans.getId())
            .map(existingCommissionServiceTrans -> {
                if (commissionServiceTrans.getOrgShortName() != null) {
                    existingCommissionServiceTrans.setOrgShortName(commissionServiceTrans.getOrgShortName());
                }
                if (commissionServiceTrans.getLocRef() != null) {
                    existingCommissionServiceTrans.setLocRef(commissionServiceTrans.getLocRef());
                }
                if (commissionServiceTrans.getRvcRef() != null) {
                    existingCommissionServiceTrans.setRvcRef(commissionServiceTrans.getRvcRef());
                }
                if (commissionServiceTrans.getServiceChargeId() != null) {
                    existingCommissionServiceTrans.setServiceChargeId(commissionServiceTrans.getServiceChargeId());
                }
                if (commissionServiceTrans.getName() != null) {
                    existingCommissionServiceTrans.setName(commissionServiceTrans.getName());
                }
                if (commissionServiceTrans.getType() != null) {
                    existingCommissionServiceTrans.setType(commissionServiceTrans.getType());
                }
                if (commissionServiceTrans.getValue() != null) {
                    existingCommissionServiceTrans.setValue(commissionServiceTrans.getValue());
                }

                return existingCommissionServiceTrans;
            })
            .map(commissionServiceTransRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commissionServiceTrans.getId().toString())
        );
    }

    /**
     * {@code GET  /commission-service-trans} : get all the commissionServiceTrans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commissionServiceTrans in body.
     */
    @GetMapping("")
    public List<CommissionServiceTrans> getAllCommissionServiceTrans() {
        LOG.debug("REST request to get all CommissionServiceTrans");
        return commissionServiceTransRepository.findAll();
    }

    /**
     * {@code GET  /commission-service-trans/:id} : get the "id" commissionServiceTrans.
     *
     * @param id the id of the commissionServiceTrans to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commissionServiceTrans, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommissionServiceTrans> getCommissionServiceTrans(@PathVariable("id") Long id) {
        LOG.debug("REST request to get CommissionServiceTrans : {}", id);
        Optional<CommissionServiceTrans> commissionServiceTrans = commissionServiceTransRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(commissionServiceTrans);
    }

    /**
     * {@code DELETE  /commission-service-trans/:id} : delete the "id" commissionServiceTrans.
     *
     * @param id the id of the commissionServiceTrans to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommissionServiceTrans(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete CommissionServiceTrans : {}", id);
        commissionServiceTransRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
