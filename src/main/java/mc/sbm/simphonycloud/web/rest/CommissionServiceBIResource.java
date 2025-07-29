package mc.sbm.simphonycloud.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.CommissionServiceBI;
import mc.sbm.simphonycloud.repository.CommissionServiceBIRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.CommissionServiceBI}.
 */
@RestController
@RequestMapping("/api/commission-service-bis")
@Transactional
public class CommissionServiceBIResource {

    private static final Logger LOG = LoggerFactory.getLogger(CommissionServiceBIResource.class);

    private static final String ENTITY_NAME = "commissionServiceBI";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommissionServiceBIRepository commissionServiceBIRepository;

    public CommissionServiceBIResource(CommissionServiceBIRepository commissionServiceBIRepository) {
        this.commissionServiceBIRepository = commissionServiceBIRepository;
    }

    /**
     * {@code POST  /commission-service-bis} : Create a new commissionServiceBI.
     *
     * @param commissionServiceBI the commissionServiceBI to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commissionServiceBI, or with status {@code 400 (Bad Request)} if the commissionServiceBI has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CommissionServiceBI> createCommissionServiceBI(@Valid @RequestBody CommissionServiceBI commissionServiceBI)
        throws URISyntaxException {
        LOG.debug("REST request to save CommissionServiceBI : {}", commissionServiceBI);
        if (commissionServiceBI.getId() != null) {
            throw new BadRequestAlertException("A new commissionServiceBI cannot already have an ID", ENTITY_NAME, "idexists");
        }
        commissionServiceBI = commissionServiceBIRepository.save(commissionServiceBI);
        return ResponseEntity.created(new URI("/api/commission-service-bis/" + commissionServiceBI.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, commissionServiceBI.getId().toString()))
            .body(commissionServiceBI);
    }

    /**
     * {@code PUT  /commission-service-bis/:id} : Updates an existing commissionServiceBI.
     *
     * @param id the id of the commissionServiceBI to save.
     * @param commissionServiceBI the commissionServiceBI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commissionServiceBI,
     * or with status {@code 400 (Bad Request)} if the commissionServiceBI is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commissionServiceBI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommissionServiceBI> updateCommissionServiceBI(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CommissionServiceBI commissionServiceBI
    ) throws URISyntaxException {
        LOG.debug("REST request to update CommissionServiceBI : {}, {}", id, commissionServiceBI);
        if (commissionServiceBI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commissionServiceBI.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commissionServiceBIRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        commissionServiceBI = commissionServiceBIRepository.save(commissionServiceBI);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commissionServiceBI.getId().toString()))
            .body(commissionServiceBI);
    }

    /**
     * {@code PATCH  /commission-service-bis/:id} : Partial updates given fields of an existing commissionServiceBI, field will ignore if it is null
     *
     * @param id the id of the commissionServiceBI to save.
     * @param commissionServiceBI the commissionServiceBI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commissionServiceBI,
     * or with status {@code 400 (Bad Request)} if the commissionServiceBI is not valid,
     * or with status {@code 404 (Not Found)} if the commissionServiceBI is not found,
     * or with status {@code 500 (Internal Server Error)} if the commissionServiceBI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CommissionServiceBI> partialUpdateCommissionServiceBI(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CommissionServiceBI commissionServiceBI
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CommissionServiceBI partially : {}, {}", id, commissionServiceBI);
        if (commissionServiceBI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commissionServiceBI.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commissionServiceBIRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CommissionServiceBI> result = commissionServiceBIRepository
            .findById(commissionServiceBI.getId())
            .map(existingCommissionServiceBI -> {
                if (commissionServiceBI.getNom() != null) {
                    existingCommissionServiceBI.setNom(commissionServiceBI.getNom());
                }
                if (commissionServiceBI.getNomCourt() != null) {
                    existingCommissionServiceBI.setNomCourt(commissionServiceBI.getNomCourt());
                }
                if (commissionServiceBI.getTypeValue() != null) {
                    existingCommissionServiceBI.setTypeValue(commissionServiceBI.getTypeValue());
                }
                if (commissionServiceBI.getValue() != null) {
                    existingCommissionServiceBI.setValue(commissionServiceBI.getValue());
                }
                if (commissionServiceBI.getEtablissementRef() != null) {
                    existingCommissionServiceBI.setEtablissementRef(commissionServiceBI.getEtablissementRef());
                }

                return existingCommissionServiceBI;
            })
            .map(commissionServiceBIRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commissionServiceBI.getId().toString())
        );
    }

    /**
     * {@code GET  /commission-service-bis} : get all the commissionServiceBIS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commissionServiceBIS in body.
     */
    @GetMapping("")
    public List<CommissionServiceBI> getAllCommissionServiceBIS() {
        LOG.debug("REST request to get all CommissionServiceBIS");
        return commissionServiceBIRepository.findAll();
    }

    /**
     * {@code GET  /commission-service-bis/:id} : get the "id" commissionServiceBI.
     *
     * @param id the id of the commissionServiceBI to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commissionServiceBI, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommissionServiceBI> getCommissionServiceBI(@PathVariable("id") Long id) {
        LOG.debug("REST request to get CommissionServiceBI : {}", id);
        Optional<CommissionServiceBI> commissionServiceBI = commissionServiceBIRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(commissionServiceBI);
    }

    /**
     * {@code DELETE  /commission-service-bis/:id} : delete the "id" commissionServiceBI.
     *
     * @param id the id of the commissionServiceBI to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommissionServiceBI(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete CommissionServiceBI : {}", id);
        commissionServiceBIRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
