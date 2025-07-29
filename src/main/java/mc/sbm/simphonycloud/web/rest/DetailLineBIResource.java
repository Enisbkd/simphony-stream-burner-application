package mc.sbm.simphonycloud.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.DetailLineBI;
import mc.sbm.simphonycloud.repository.DetailLineBIRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.DetailLineBI}.
 */
@RestController
@RequestMapping("/api/detail-line-bis")
@Transactional
public class DetailLineBIResource {

    private static final Logger LOG = LoggerFactory.getLogger(DetailLineBIResource.class);

    private static final String ENTITY_NAME = "detailLineBI";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetailLineBIRepository detailLineBIRepository;

    public DetailLineBIResource(DetailLineBIRepository detailLineBIRepository) {
        this.detailLineBIRepository = detailLineBIRepository;
    }

    /**
     * {@code POST  /detail-line-bis} : Create a new detailLineBI.
     *
     * @param detailLineBI the detailLineBI to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detailLineBI, or with status {@code 400 (Bad Request)} if the detailLineBI has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DetailLineBI> createDetailLineBI(@RequestBody DetailLineBI detailLineBI) throws URISyntaxException {
        LOG.debug("REST request to save DetailLineBI : {}", detailLineBI);
        if (detailLineBI.getId() != null) {
            throw new BadRequestAlertException("A new detailLineBI cannot already have an ID", ENTITY_NAME, "idexists");
        }
        detailLineBI = detailLineBIRepository.save(detailLineBI);
        return ResponseEntity.created(new URI("/api/detail-line-bis/" + detailLineBI.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, detailLineBI.getId().toString()))
            .body(detailLineBI);
    }

    /**
     * {@code PUT  /detail-line-bis/:id} : Updates an existing detailLineBI.
     *
     * @param id the id of the detailLineBI to save.
     * @param detailLineBI the detailLineBI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailLineBI,
     * or with status {@code 400 (Bad Request)} if the detailLineBI is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detailLineBI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DetailLineBI> updateDetailLineBI(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DetailLineBI detailLineBI
    ) throws URISyntaxException {
        LOG.debug("REST request to update DetailLineBI : {}, {}", id, detailLineBI);
        if (detailLineBI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detailLineBI.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detailLineBIRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        detailLineBI = detailLineBIRepository.save(detailLineBI);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detailLineBI.getId().toString()))
            .body(detailLineBI);
    }

    /**
     * {@code PATCH  /detail-line-bis/:id} : Partial updates given fields of an existing detailLineBI, field will ignore if it is null
     *
     * @param id the id of the detailLineBI to save.
     * @param detailLineBI the detailLineBI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailLineBI,
     * or with status {@code 400 (Bad Request)} if the detailLineBI is not valid,
     * or with status {@code 404 (Not Found)} if the detailLineBI is not found,
     * or with status {@code 500 (Internal Server Error)} if the detailLineBI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DetailLineBI> partialUpdateDetailLineBI(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DetailLineBI detailLineBI
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update DetailLineBI partially : {}, {}", id, detailLineBI);
        if (detailLineBI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detailLineBI.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detailLineBIRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DetailLineBI> result = detailLineBIRepository
            .findById(detailLineBI.getId())
            .map(existingDetailLineBI -> {
                if (detailLineBI.getGuestCheckLineItemId() != null) {
                    existingDetailLineBI.setGuestCheckLineItemId(detailLineBI.getGuestCheckLineItemId());
                }
                if (detailLineBI.getDetailUTC() != null) {
                    existingDetailLineBI.setDetailUTC(detailLineBI.getDetailUTC());
                }
                if (detailLineBI.getDetailLcl() != null) {
                    existingDetailLineBI.setDetailLcl(detailLineBI.getDetailLcl());
                }
                if (detailLineBI.getSeatNum() != null) {
                    existingDetailLineBI.setSeatNum(detailLineBI.getSeatNum());
                }
                if (detailLineBI.getPrcLvl() != null) {
                    existingDetailLineBI.setPrcLvl(detailLineBI.getPrcLvl());
                }
                if (detailLineBI.getDspTtl() != null) {
                    existingDetailLineBI.setDspTtl(detailLineBI.getDspTtl());
                }
                if (detailLineBI.getDspQty() != null) {
                    existingDetailLineBI.setDspQty(detailLineBI.getDspQty());
                }
                if (detailLineBI.getErrCorFlag() != null) {
                    existingDetailLineBI.setErrCorFlag(detailLineBI.getErrCorFlag());
                }
                if (detailLineBI.getVdFlag() != null) {
                    existingDetailLineBI.setVdFlag(detailLineBI.getVdFlag());
                }
                if (detailLineBI.getReturnFlag() != null) {
                    existingDetailLineBI.setReturnFlag(detailLineBI.getReturnFlag());
                }
                if (detailLineBI.getDoNotShowFlag() != null) {
                    existingDetailLineBI.setDoNotShowFlag(detailLineBI.getDoNotShowFlag());
                }
                if (detailLineBI.getAggTtl() != null) {
                    existingDetailLineBI.setAggTtl(detailLineBI.getAggTtl());
                }
                if (detailLineBI.getRsnCodeNum() != null) {
                    existingDetailLineBI.setRsnCodeNum(detailLineBI.getRsnCodeNum());
                }
                if (detailLineBI.getRefInfo1() != null) {
                    existingDetailLineBI.setRefInfo1(detailLineBI.getRefInfo1());
                }
                if (detailLineBI.getRefInfo2() != null) {
                    existingDetailLineBI.setRefInfo2(detailLineBI.getRefInfo2());
                }
                if (detailLineBI.getSvcRndNum() != null) {
                    existingDetailLineBI.setSvcRndNum(detailLineBI.getSvcRndNum());
                }
                if (detailLineBI.getParDtlId() != null) {
                    existingDetailLineBI.setParDtlId(detailLineBI.getParDtlId());
                }
                if (detailLineBI.getChkEmpId() != null) {
                    existingDetailLineBI.setChkEmpId(detailLineBI.getChkEmpId());
                }
                if (detailLineBI.getTransEmpNum() != null) {
                    existingDetailLineBI.setTransEmpNum(detailLineBI.getTransEmpNum());
                }
                if (detailLineBI.getMgrEmpNum() != null) {
                    existingDetailLineBI.setMgrEmpNum(detailLineBI.getMgrEmpNum());
                }
                if (detailLineBI.getMealEmpNum() != null) {
                    existingDetailLineBI.setMealEmpNum(detailLineBI.getMealEmpNum());
                }
                if (detailLineBI.getDscNum() != null) {
                    existingDetailLineBI.setDscNum(detailLineBI.getDscNum());
                }
                if (detailLineBI.getDscMiNum() != null) {
                    existingDetailLineBI.setDscMiNum(detailLineBI.getDscMiNum());
                }
                if (detailLineBI.getSvcChgNum() != null) {
                    existingDetailLineBI.setSvcChgNum(detailLineBI.getSvcChgNum());
                }
                if (detailLineBI.getTmedNum() != null) {
                    existingDetailLineBI.setTmedNum(detailLineBI.getTmedNum());
                }
                if (detailLineBI.getMiNum() != null) {
                    existingDetailLineBI.setMiNum(detailLineBI.getMiNum());
                }

                return existingDetailLineBI;
            })
            .map(detailLineBIRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detailLineBI.getId().toString())
        );
    }

    /**
     * {@code GET  /detail-line-bis} : get all the detailLineBIS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detailLineBIS in body.
     */
    @GetMapping("")
    public List<DetailLineBI> getAllDetailLineBIS() {
        LOG.debug("REST request to get all DetailLineBIS");
        return detailLineBIRepository.findAll();
    }

    /**
     * {@code GET  /detail-line-bis/:id} : get the "id" detailLineBI.
     *
     * @param id the id of the detailLineBI to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detailLineBI, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DetailLineBI> getDetailLineBI(@PathVariable("id") Long id) {
        LOG.debug("REST request to get DetailLineBI : {}", id);
        Optional<DetailLineBI> detailLineBI = detailLineBIRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(detailLineBI);
    }

    /**
     * {@code DELETE  /detail-line-bis/:id} : delete the "id" detailLineBI.
     *
     * @param id the id of the detailLineBI to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetailLineBI(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete DetailLineBI : {}", id);
        detailLineBIRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
