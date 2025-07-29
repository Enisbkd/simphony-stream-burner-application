package mc.sbm.simphonycloud.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.RemiseTrans;
import mc.sbm.simphonycloud.repository.RemiseTransRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.RemiseTrans}.
 */
@RestController
@RequestMapping("/api/remise-trans")
@Transactional
public class RemiseTransResource {

    private static final Logger LOG = LoggerFactory.getLogger(RemiseTransResource.class);

    private static final String ENTITY_NAME = "remiseTrans";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RemiseTransRepository remiseTransRepository;

    public RemiseTransResource(RemiseTransRepository remiseTransRepository) {
        this.remiseTransRepository = remiseTransRepository;
    }

    /**
     * {@code POST  /remise-trans} : Create a new remiseTrans.
     *
     * @param remiseTrans the remiseTrans to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new remiseTrans, or with status {@code 400 (Bad Request)} if the remiseTrans has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RemiseTrans> createRemiseTrans(@RequestBody RemiseTrans remiseTrans) throws URISyntaxException {
        LOG.debug("REST request to save RemiseTrans : {}", remiseTrans);
        if (remiseTrans.getId() != null) {
            throw new BadRequestAlertException("A new remiseTrans cannot already have an ID", ENTITY_NAME, "idexists");
        }
        remiseTrans = remiseTransRepository.save(remiseTrans);
        return ResponseEntity.created(new URI("/api/remise-trans/" + remiseTrans.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, remiseTrans.getId().toString()))
            .body(remiseTrans);
    }

    /**
     * {@code PUT  /remise-trans/:id} : Updates an existing remiseTrans.
     *
     * @param id the id of the remiseTrans to save.
     * @param remiseTrans the remiseTrans to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated remiseTrans,
     * or with status {@code 400 (Bad Request)} if the remiseTrans is not valid,
     * or with status {@code 500 (Internal Server Error)} if the remiseTrans couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RemiseTrans> updateRemiseTrans(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RemiseTrans remiseTrans
    ) throws URISyntaxException {
        LOG.debug("REST request to update RemiseTrans : {}, {}", id, remiseTrans);
        if (remiseTrans.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, remiseTrans.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!remiseTransRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        remiseTrans = remiseTransRepository.save(remiseTrans);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, remiseTrans.getId().toString()))
            .body(remiseTrans);
    }

    /**
     * {@code PATCH  /remise-trans/:id} : Partial updates given fields of an existing remiseTrans, field will ignore if it is null
     *
     * @param id the id of the remiseTrans to save.
     * @param remiseTrans the remiseTrans to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated remiseTrans,
     * or with status {@code 400 (Bad Request)} if the remiseTrans is not valid,
     * or with status {@code 404 (Not Found)} if the remiseTrans is not found,
     * or with status {@code 500 (Internal Server Error)} if the remiseTrans couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RemiseTrans> partialUpdateRemiseTrans(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RemiseTrans remiseTrans
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update RemiseTrans partially : {}, {}", id, remiseTrans);
        if (remiseTrans.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, remiseTrans.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!remiseTransRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RemiseTrans> result = remiseTransRepository
            .findById(remiseTrans.getId())
            .map(existingRemiseTrans -> {
                if (remiseTrans.getOrgShortName() != null) {
                    existingRemiseTrans.setOrgShortName(remiseTrans.getOrgShortName());
                }
                if (remiseTrans.getLocRef() != null) {
                    existingRemiseTrans.setLocRef(remiseTrans.getLocRef());
                }
                if (remiseTrans.getRvcRef() != null) {
                    existingRemiseTrans.setRvcRef(remiseTrans.getRvcRef());
                }
                if (remiseTrans.getDiscountId() != null) {
                    existingRemiseTrans.setDiscountId(remiseTrans.getDiscountId());
                }
                if (remiseTrans.getFrName() != null) {
                    existingRemiseTrans.setFrName(remiseTrans.getFrName());
                }
                if (remiseTrans.getEngName() != null) {
                    existingRemiseTrans.setEngName(remiseTrans.getEngName());
                }
                if (remiseTrans.getDiscountType() != null) {
                    existingRemiseTrans.setDiscountType(remiseTrans.getDiscountType());
                }
                if (remiseTrans.getDiscountValue() != null) {
                    existingRemiseTrans.setDiscountValue(remiseTrans.getDiscountValue());
                }

                return existingRemiseTrans;
            })
            .map(remiseTransRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, remiseTrans.getId().toString())
        );
    }

    /**
     * {@code GET  /remise-trans} : get all the remiseTrans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of remiseTrans in body.
     */
    @GetMapping("")
    public List<RemiseTrans> getAllRemiseTrans() {
        LOG.debug("REST request to get all RemiseTrans");
        return remiseTransRepository.findAll();
    }

    /**
     * {@code GET  /remise-trans/:id} : get the "id" remiseTrans.
     *
     * @param id the id of the remiseTrans to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the remiseTrans, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RemiseTrans> getRemiseTrans(@PathVariable("id") Long id) {
        LOG.debug("REST request to get RemiseTrans : {}", id);
        Optional<RemiseTrans> remiseTrans = remiseTransRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(remiseTrans);
    }

    /**
     * {@code DELETE  /remise-trans/:id} : delete the "id" remiseTrans.
     *
     * @param id the id of the remiseTrans to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRemiseTrans(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete RemiseTrans : {}", id);
        remiseTransRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
