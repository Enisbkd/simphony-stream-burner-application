package mc.sbm.simphonycloud.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.OrganizationLocationTrans;
import mc.sbm.simphonycloud.repository.OrganizationLocationTransRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.OrganizationLocationTrans}.
 */
@RestController
@RequestMapping("/api/organization-location-trans")
@Transactional
public class OrganizationLocationTransResource {

    private static final Logger LOG = LoggerFactory.getLogger(OrganizationLocationTransResource.class);

    private static final String ENTITY_NAME = "organizationLocationTrans";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganizationLocationTransRepository organizationLocationTransRepository;

    public OrganizationLocationTransResource(OrganizationLocationTransRepository organizationLocationTransRepository) {
        this.organizationLocationTransRepository = organizationLocationTransRepository;
    }

    /**
     * {@code POST  /organization-location-trans} : Create a new organizationLocationTrans.
     *
     * @param organizationLocationTrans the organizationLocationTrans to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organizationLocationTrans, or with status {@code 400 (Bad Request)} if the organizationLocationTrans has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OrganizationLocationTrans> createOrganizationLocationTrans(
        @RequestBody OrganizationLocationTrans organizationLocationTrans
    ) throws URISyntaxException {
        LOG.debug("REST request to save OrganizationLocationTrans : {}", organizationLocationTrans);
        if (organizationLocationTrans.getId() != null) {
            throw new BadRequestAlertException("A new organizationLocationTrans cannot already have an ID", ENTITY_NAME, "idexists");
        }
        organizationLocationTrans = organizationLocationTransRepository.save(organizationLocationTrans);
        return ResponseEntity.created(new URI("/api/organization-location-trans/" + organizationLocationTrans.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, organizationLocationTrans.getId().toString()))
            .body(organizationLocationTrans);
    }

    /**
     * {@code PUT  /organization-location-trans/:id} : Updates an existing organizationLocationTrans.
     *
     * @param id the id of the organizationLocationTrans to save.
     * @param organizationLocationTrans the organizationLocationTrans to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationLocationTrans,
     * or with status {@code 400 (Bad Request)} if the organizationLocationTrans is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organizationLocationTrans couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrganizationLocationTrans> updateOrganizationLocationTrans(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody OrganizationLocationTrans organizationLocationTrans
    ) throws URISyntaxException {
        LOG.debug("REST request to update OrganizationLocationTrans : {}, {}", id, organizationLocationTrans);
        if (organizationLocationTrans.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organizationLocationTrans.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organizationLocationTransRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        organizationLocationTrans = organizationLocationTransRepository.save(organizationLocationTrans);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organizationLocationTrans.getId().toString()))
            .body(organizationLocationTrans);
    }

    /**
     * {@code PATCH  /organization-location-trans/:id} : Partial updates given fields of an existing organizationLocationTrans, field will ignore if it is null
     *
     * @param id the id of the organizationLocationTrans to save.
     * @param organizationLocationTrans the organizationLocationTrans to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationLocationTrans,
     * or with status {@code 400 (Bad Request)} if the organizationLocationTrans is not valid,
     * or with status {@code 404 (Not Found)} if the organizationLocationTrans is not found,
     * or with status {@code 500 (Internal Server Error)} if the organizationLocationTrans couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrganizationLocationTrans> partialUpdateOrganizationLocationTrans(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody OrganizationLocationTrans organizationLocationTrans
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update OrganizationLocationTrans partially : {}, {}", id, organizationLocationTrans);
        if (organizationLocationTrans.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organizationLocationTrans.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organizationLocationTransRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrganizationLocationTrans> result = organizationLocationTransRepository
            .findById(organizationLocationTrans.getId())
            .map(existingOrganizationLocationTrans -> {
                if (organizationLocationTrans.getOrgShortName() != null) {
                    existingOrganizationLocationTrans.setOrgShortName(organizationLocationTrans.getOrgShortName());
                }
                if (organizationLocationTrans.getLocRef() != null) {
                    existingOrganizationLocationTrans.setLocRef(organizationLocationTrans.getLocRef());
                }
                if (organizationLocationTrans.getName() != null) {
                    existingOrganizationLocationTrans.setName(organizationLocationTrans.getName());
                }
                if (organizationLocationTrans.getCurrency() != null) {
                    existingOrganizationLocationTrans.setCurrency(organizationLocationTrans.getCurrency());
                }
                if (organizationLocationTrans.getPhoneNumber() != null) {
                    existingOrganizationLocationTrans.setPhoneNumber(organizationLocationTrans.getPhoneNumber());
                }
                if (organizationLocationTrans.getLanguages() != null) {
                    existingOrganizationLocationTrans.setLanguages(organizationLocationTrans.getLanguages());
                }
                if (organizationLocationTrans.getTimezoneIanaName() != null) {
                    existingOrganizationLocationTrans.setTimezoneIanaName(organizationLocationTrans.getTimezoneIanaName());
                }
                if (organizationLocationTrans.getTimezoneWindowsName() != null) {
                    existingOrganizationLocationTrans.setTimezoneWindowsName(organizationLocationTrans.getTimezoneWindowsName());
                }
                if (organizationLocationTrans.getTimezoneTzIndex() != null) {
                    existingOrganizationLocationTrans.setTimezoneTzIndex(organizationLocationTrans.getTimezoneTzIndex());
                }
                if (organizationLocationTrans.getAddressLine1() != null) {
                    existingOrganizationLocationTrans.setAddressLine1(organizationLocationTrans.getAddressLine1());
                }
                if (organizationLocationTrans.getAddressLine2() != null) {
                    existingOrganizationLocationTrans.setAddressLine2(organizationLocationTrans.getAddressLine2());
                }
                if (organizationLocationTrans.getAddressFloor() != null) {
                    existingOrganizationLocationTrans.setAddressFloor(organizationLocationTrans.getAddressFloor());
                }
                if (organizationLocationTrans.getAddressLocality() != null) {
                    existingOrganizationLocationTrans.setAddressLocality(organizationLocationTrans.getAddressLocality());
                }
                if (organizationLocationTrans.getAddressRegion() != null) {
                    existingOrganizationLocationTrans.setAddressRegion(organizationLocationTrans.getAddressRegion());
                }
                if (organizationLocationTrans.getAddressPostalCode() != null) {
                    existingOrganizationLocationTrans.setAddressPostalCode(organizationLocationTrans.getAddressPostalCode());
                }
                if (organizationLocationTrans.getAddressCountry() != null) {
                    existingOrganizationLocationTrans.setAddressCountry(organizationLocationTrans.getAddressCountry());
                }
                if (organizationLocationTrans.getAddressNotes() != null) {
                    existingOrganizationLocationTrans.setAddressNotes(organizationLocationTrans.getAddressNotes());
                }
                if (organizationLocationTrans.getGeoLatitude() != null) {
                    existingOrganizationLocationTrans.setGeoLatitude(organizationLocationTrans.getGeoLatitude());
                }
                if (organizationLocationTrans.getGeoLongitude() != null) {
                    existingOrganizationLocationTrans.setGeoLongitude(organizationLocationTrans.getGeoLongitude());
                }
                if (organizationLocationTrans.getPosPlatformName() != null) {
                    existingOrganizationLocationTrans.setPosPlatformName(organizationLocationTrans.getPosPlatformName());
                }
                if (organizationLocationTrans.getPosPlatformVersion() != null) {
                    existingOrganizationLocationTrans.setPosPlatformVersion(organizationLocationTrans.getPosPlatformVersion());
                }

                return existingOrganizationLocationTrans;
            })
            .map(organizationLocationTransRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organizationLocationTrans.getId().toString())
        );
    }

    /**
     * {@code GET  /organization-location-trans} : get all the organizationLocationTrans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organizationLocationTrans in body.
     */
    @GetMapping("")
    public List<OrganizationLocationTrans> getAllOrganizationLocationTrans() {
        LOG.debug("REST request to get all OrganizationLocationTrans");
        return organizationLocationTransRepository.findAll();
    }

    /**
     * {@code GET  /organization-location-trans/:id} : get the "id" organizationLocationTrans.
     *
     * @param id the id of the organizationLocationTrans to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organizationLocationTrans, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrganizationLocationTrans> getOrganizationLocationTrans(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get OrganizationLocationTrans : {}", id);
        Optional<OrganizationLocationTrans> organizationLocationTrans = organizationLocationTransRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(organizationLocationTrans);
    }

    /**
     * {@code DELETE  /organization-location-trans/:id} : delete the "id" organizationLocationTrans.
     *
     * @param id the id of the organizationLocationTrans to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizationLocationTrans(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete OrganizationLocationTrans : {}", id);
        organizationLocationTransRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
