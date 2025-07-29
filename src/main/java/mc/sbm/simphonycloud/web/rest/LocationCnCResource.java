package mc.sbm.simphonycloud.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.LocationCnC;
import mc.sbm.simphonycloud.repository.LocationCnCRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.LocationCnC}.
 */
@RestController
@RequestMapping("/api/location-cn-cs")
@Transactional
public class LocationCnCResource {

    private static final Logger LOG = LoggerFactory.getLogger(LocationCnCResource.class);

    private static final String ENTITY_NAME = "locationCnC";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocationCnCRepository locationCnCRepository;

    public LocationCnCResource(LocationCnCRepository locationCnCRepository) {
        this.locationCnCRepository = locationCnCRepository;
    }

    /**
     * {@code POST  /location-cn-cs} : Create a new locationCnC.
     *
     * @param locationCnC the locationCnC to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locationCnC, or with status {@code 400 (Bad Request)} if the locationCnC has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<LocationCnC> createLocationCnC(@RequestBody LocationCnC locationCnC) throws URISyntaxException {
        LOG.debug("REST request to save LocationCnC : {}", locationCnC);
        if (locationCnC.getId() != null) {
            throw new BadRequestAlertException("A new locationCnC cannot already have an ID", ENTITY_NAME, "idexists");
        }
        locationCnC = locationCnCRepository.save(locationCnC);
        return ResponseEntity.created(new URI("/api/location-cn-cs/" + locationCnC.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, locationCnC.getId().toString()))
            .body(locationCnC);
    }

    /**
     * {@code PUT  /location-cn-cs/:id} : Updates an existing locationCnC.
     *
     * @param id the id of the locationCnC to save.
     * @param locationCnC the locationCnC to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locationCnC,
     * or with status {@code 400 (Bad Request)} if the locationCnC is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locationCnC couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LocationCnC> updateLocationCnC(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody LocationCnC locationCnC
    ) throws URISyntaxException {
        LOG.debug("REST request to update LocationCnC : {}, {}", id, locationCnC);
        if (locationCnC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locationCnC.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locationCnCRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        locationCnC = locationCnCRepository.save(locationCnC);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, locationCnC.getId().toString()))
            .body(locationCnC);
    }

    /**
     * {@code PATCH  /location-cn-cs/:id} : Partial updates given fields of an existing locationCnC, field will ignore if it is null
     *
     * @param id the id of the locationCnC to save.
     * @param locationCnC the locationCnC to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locationCnC,
     * or with status {@code 400 (Bad Request)} if the locationCnC is not valid,
     * or with status {@code 404 (Not Found)} if the locationCnC is not found,
     * or with status {@code 500 (Internal Server Error)} if the locationCnC couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LocationCnC> partialUpdateLocationCnC(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody LocationCnC locationCnC
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update LocationCnC partially : {}, {}", id, locationCnC);
        if (locationCnC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locationCnC.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locationCnCRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LocationCnC> result = locationCnCRepository
            .findById(locationCnC.getId())
            .map(existingLocationCnC -> {
                if (locationCnC.getHierUnitId() != null) {
                    existingLocationCnC.setHierUnitId(locationCnC.getHierUnitId());
                }
                if (locationCnC.getTzIndex() != null) {
                    existingLocationCnC.setTzIndex(locationCnC.getTzIndex());
                }
                if (locationCnC.getTzName() != null) {
                    existingLocationCnC.setTzName(locationCnC.getTzName());
                }
                if (locationCnC.getLocaleInfoId() != null) {
                    existingLocationCnC.setLocaleInfoId(locationCnC.getLocaleInfoId());
                }
                if (locationCnC.getName() != null) {
                    existingLocationCnC.setName(locationCnC.getName());
                }
                if (locationCnC.getReportingLocName() != null) {
                    existingLocationCnC.setReportingLocName(locationCnC.getReportingLocName());
                }
                if (locationCnC.getLocRef() != null) {
                    existingLocationCnC.setLocRef(locationCnC.getLocRef());
                }
                if (locationCnC.getReportingParentEnterpriseLevelName() != null) {
                    existingLocationCnC.setReportingParentEnterpriseLevelName(locationCnC.getReportingParentEnterpriseLevelName());
                }
                if (locationCnC.getObjectNum() != null) {
                    existingLocationCnC.setObjectNum(locationCnC.getObjectNum());
                }
                if (locationCnC.getSbmPmsIfcIp() != null) {
                    existingLocationCnC.setSbmPmsIfcIp(locationCnC.getSbmPmsIfcIp());
                }
                if (locationCnC.getSbmPmsIfcPort() != null) {
                    existingLocationCnC.setSbmPmsIfcPort(locationCnC.getSbmPmsIfcPort());
                }
                if (locationCnC.getSbmPriveRoomStart() != null) {
                    existingLocationCnC.setSbmPriveRoomStart(locationCnC.getSbmPriveRoomStart());
                }
                if (locationCnC.getSbmPriveRoomEnd() != null) {
                    existingLocationCnC.setSbmPriveRoomEnd(locationCnC.getSbmPriveRoomEnd());
                }
                if (locationCnC.getSbmPmsSendAllDetails() != null) {
                    existingLocationCnC.setSbmPmsSendAllDetails(locationCnC.getSbmPmsSendAllDetails());
                }
                if (locationCnC.getSbmPmsSendFullDscv() != null) {
                    existingLocationCnC.setSbmPmsSendFullDscv(locationCnC.getSbmPmsSendFullDscv());
                }
                if (locationCnC.getSbmPmsSend64Tax() != null) {
                    existingLocationCnC.setSbmPmsSend64Tax(locationCnC.getSbmPmsSend64Tax());
                }
                if (locationCnC.getSbmCardPaymentUrl() != null) {
                    existingLocationCnC.setSbmCardPaymentUrl(locationCnC.getSbmCardPaymentUrl());
                }
                if (locationCnC.getSbmCheckHotelDataUrl() != null) {
                    existingLocationCnC.setSbmCheckHotelDataUrl(locationCnC.getSbmCheckHotelDataUrl());
                }
                if (locationCnC.getSbmVoucherSvcUrl() != null) {
                    existingLocationCnC.setSbmVoucherSvcUrl(locationCnC.getSbmVoucherSvcUrl());
                }
                if (locationCnC.getSbmVoucherInvPm() != null) {
                    existingLocationCnC.setSbmVoucherInvPm(locationCnC.getSbmVoucherInvPm());
                }
                if (locationCnC.getSbmVoucherCorpPm() != null) {
                    existingLocationCnC.setSbmVoucherCorpPm(locationCnC.getSbmVoucherCorpPm());
                }
                if (locationCnC.getSbmVoucherRewardPm() != null) {
                    existingLocationCnC.setSbmVoucherRewardPm(locationCnC.getSbmVoucherRewardPm());
                }
                if (locationCnC.getSbmVoucherMcPm() != null) {
                    existingLocationCnC.setSbmVoucherMcPm(locationCnC.getSbmVoucherMcPm());
                }
                if (locationCnC.getSbmPmsIfcPort2() != null) {
                    existingLocationCnC.setSbmPmsIfcPort2(locationCnC.getSbmPmsIfcPort2());
                }
                if (locationCnC.getSbmPmsIfcPort3() != null) {
                    existingLocationCnC.setSbmPmsIfcPort3(locationCnC.getSbmPmsIfcPort3());
                }
                if (locationCnC.getSbmPmsIfcPort4() != null) {
                    existingLocationCnC.setSbmPmsIfcPort4(locationCnC.getSbmPmsIfcPort4());
                }
                if (locationCnC.getSbmTimeout() != null) {
                    existingLocationCnC.setSbmTimeout(locationCnC.getSbmTimeout());
                }

                return existingLocationCnC;
            })
            .map(locationCnCRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, locationCnC.getId().toString())
        );
    }

    /**
     * {@code GET  /location-cn-cs} : get all the locationCnCS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locationCnCS in body.
     */
    @GetMapping("")
    public List<LocationCnC> getAllLocationCnCS() {
        LOG.debug("REST request to get all LocationCnCS");
        return locationCnCRepository.findAll();
    }

    /**
     * {@code GET  /location-cn-cs/:id} : get the "id" locationCnC.
     *
     * @param id the id of the locationCnC to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locationCnC, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LocationCnC> getLocationCnC(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get LocationCnC : {}", id);
        Optional<LocationCnC> locationCnC = locationCnCRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(locationCnC);
    }

    /**
     * {@code DELETE  /location-cn-cs/:id} : delete the "id" locationCnC.
     *
     * @param id the id of the locationCnC to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocationCnC(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete LocationCnC : {}", id);
        locationCnCRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
