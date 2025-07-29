package mc.sbm.simphonycloud.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.GuestCheckBI;
import mc.sbm.simphonycloud.repository.GuestCheckBIRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.GuestCheckBI}.
 */
@RestController
@RequestMapping("/api/guest-check-bis")
@Transactional
public class GuestCheckBIResource {

    private static final Logger LOG = LoggerFactory.getLogger(GuestCheckBIResource.class);

    private static final String ENTITY_NAME = "guestCheckBI";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GuestCheckBIRepository guestCheckBIRepository;

    public GuestCheckBIResource(GuestCheckBIRepository guestCheckBIRepository) {
        this.guestCheckBIRepository = guestCheckBIRepository;
    }

    /**
     * {@code POST  /guest-check-bis} : Create a new guestCheckBI.
     *
     * @param guestCheckBI the guestCheckBI to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new guestCheckBI, or with status {@code 400 (Bad Request)} if the guestCheckBI has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<GuestCheckBI> createGuestCheckBI(@RequestBody GuestCheckBI guestCheckBI) throws URISyntaxException {
        LOG.debug("REST request to save GuestCheckBI : {}", guestCheckBI);
        if (guestCheckBI.getId() != null) {
            throw new BadRequestAlertException("A new guestCheckBI cannot already have an ID", ENTITY_NAME, "idexists");
        }
        guestCheckBI = guestCheckBIRepository.save(guestCheckBI);
        return ResponseEntity.created(new URI("/api/guest-check-bis/" + guestCheckBI.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, guestCheckBI.getId().toString()))
            .body(guestCheckBI);
    }

    /**
     * {@code PUT  /guest-check-bis/:id} : Updates an existing guestCheckBI.
     *
     * @param id the id of the guestCheckBI to save.
     * @param guestCheckBI the guestCheckBI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated guestCheckBI,
     * or with status {@code 400 (Bad Request)} if the guestCheckBI is not valid,
     * or with status {@code 500 (Internal Server Error)} if the guestCheckBI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<GuestCheckBI> updateGuestCheckBI(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GuestCheckBI guestCheckBI
    ) throws URISyntaxException {
        LOG.debug("REST request to update GuestCheckBI : {}, {}", id, guestCheckBI);
        if (guestCheckBI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, guestCheckBI.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!guestCheckBIRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        guestCheckBI = guestCheckBIRepository.save(guestCheckBI);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, guestCheckBI.getId().toString()))
            .body(guestCheckBI);
    }

    /**
     * {@code PATCH  /guest-check-bis/:id} : Partial updates given fields of an existing guestCheckBI, field will ignore if it is null
     *
     * @param id the id of the guestCheckBI to save.
     * @param guestCheckBI the guestCheckBI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated guestCheckBI,
     * or with status {@code 400 (Bad Request)} if the guestCheckBI is not valid,
     * or with status {@code 404 (Not Found)} if the guestCheckBI is not found,
     * or with status {@code 500 (Internal Server Error)} if the guestCheckBI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GuestCheckBI> partialUpdateGuestCheckBI(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GuestCheckBI guestCheckBI
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update GuestCheckBI partially : {}, {}", id, guestCheckBI);
        if (guestCheckBI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, guestCheckBI.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!guestCheckBIRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GuestCheckBI> result = guestCheckBIRepository
            .findById(guestCheckBI.getId())
            .map(existingGuestCheckBI -> {
                if (guestCheckBI.getGuestCheckId() != null) {
                    existingGuestCheckBI.setGuestCheckId(guestCheckBI.getGuestCheckId());
                }
                if (guestCheckBI.getChkNum() != null) {
                    existingGuestCheckBI.setChkNum(guestCheckBI.getChkNum());
                }
                if (guestCheckBI.getOpnLcl() != null) {
                    existingGuestCheckBI.setOpnLcl(guestCheckBI.getOpnLcl());
                }
                if (guestCheckBI.getClsdLcl() != null) {
                    existingGuestCheckBI.setClsdLcl(guestCheckBI.getClsdLcl());
                }
                if (guestCheckBI.getCancelFlag() != null) {
                    existingGuestCheckBI.setCancelFlag(guestCheckBI.getCancelFlag());
                }
                if (guestCheckBI.getGstCnt() != null) {
                    existingGuestCheckBI.setGstCnt(guestCheckBI.getGstCnt());
                }
                if (guestCheckBI.getTblNum() != null) {
                    existingGuestCheckBI.setTblNum(guestCheckBI.getTblNum());
                }
                if (guestCheckBI.getTaxCollTtl() != null) {
                    existingGuestCheckBI.setTaxCollTtl(guestCheckBI.getTaxCollTtl());
                }
                if (guestCheckBI.getSubTtl() != null) {
                    existingGuestCheckBI.setSubTtl(guestCheckBI.getSubTtl());
                }
                if (guestCheckBI.getChkTtl() != null) {
                    existingGuestCheckBI.setChkTtl(guestCheckBI.getChkTtl());
                }
                if (guestCheckBI.getSvcChgTtl() != null) {
                    existingGuestCheckBI.setSvcChgTtl(guestCheckBI.getSvcChgTtl());
                }
                if (guestCheckBI.getTipTotal() != null) {
                    existingGuestCheckBI.setTipTotal(guestCheckBI.getTipTotal());
                }
                if (guestCheckBI.getDscTtl() != null) {
                    existingGuestCheckBI.setDscTtl(guestCheckBI.getDscTtl());
                }
                if (guestCheckBI.getErrorCorrectTtl() != null) {
                    existingGuestCheckBI.setErrorCorrectTtl(guestCheckBI.getErrorCorrectTtl());
                }
                if (guestCheckBI.getReturnTtl() != null) {
                    existingGuestCheckBI.setReturnTtl(guestCheckBI.getReturnTtl());
                }
                if (guestCheckBI.getXferToChkNum() != null) {
                    existingGuestCheckBI.setXferToChkNum(guestCheckBI.getXferToChkNum());
                }
                if (guestCheckBI.getXferStatus() != null) {
                    existingGuestCheckBI.setXferStatus(guestCheckBI.getXferStatus());
                }
                if (guestCheckBI.getOtNum() != null) {
                    existingGuestCheckBI.setOtNum(guestCheckBI.getOtNum());
                }
                if (guestCheckBI.getLocRef() != null) {
                    existingGuestCheckBI.setLocRef(guestCheckBI.getLocRef());
                }

                return existingGuestCheckBI;
            })
            .map(guestCheckBIRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, guestCheckBI.getId().toString())
        );
    }

    /**
     * {@code GET  /guest-check-bis} : get all the guestCheckBIS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of guestCheckBIS in body.
     */
    @GetMapping("")
    public List<GuestCheckBI> getAllGuestCheckBIS() {
        LOG.debug("REST request to get all GuestCheckBIS");
        return guestCheckBIRepository.findAll();
    }

    /**
     * {@code GET  /guest-check-bis/:id} : get the "id" guestCheckBI.
     *
     * @param id the id of the guestCheckBI to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the guestCheckBI, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GuestCheckBI> getGuestCheckBI(@PathVariable("id") Long id) {
        LOG.debug("REST request to get GuestCheckBI : {}", id);
        Optional<GuestCheckBI> guestCheckBI = guestCheckBIRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(guestCheckBI);
    }

    /**
     * {@code DELETE  /guest-check-bis/:id} : delete the "id" guestCheckBI.
     *
     * @param id the id of the guestCheckBI to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuestCheckBI(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete GuestCheckBI : {}", id);
        guestCheckBIRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
