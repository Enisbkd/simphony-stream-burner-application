package mc.sbm.simphonycloud.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.OrganizationLocation;
import mc.sbm.simphonycloud.repository.OrganizationLocationRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.OrganizationLocation}.
 */
@RestController
@RequestMapping("/api/organization-locations")
@Transactional
public class OrganizationLocationResource {

    private static final Logger LOG = LoggerFactory.getLogger(OrganizationLocationResource.class);

    private static final String ENTITY_NAME = "organizationLocation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganizationLocationRepository organizationLocationRepository;

    public OrganizationLocationResource(OrganizationLocationRepository organizationLocationRepository) {
        this.organizationLocationRepository = organizationLocationRepository;
    }

    /**
     * {@code POST  /organization-locations} : Create a new organizationLocation.
     *
     * @param organizationLocation the organizationLocation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organizationLocation, or with status {@code 400 (Bad Request)} if the organizationLocation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OrganizationLocation> createOrganizationLocation(@RequestBody OrganizationLocation organizationLocation)
        throws URISyntaxException {
        LOG.debug("REST request to save OrganizationLocation : {}", organizationLocation);
        if (organizationLocation.getId() != null) {
            throw new BadRequestAlertException("A new organizationLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        organizationLocation = organizationLocationRepository.save(organizationLocation);
        return ResponseEntity.created(new URI("/api/organization-locations/" + organizationLocation.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, organizationLocation.getId().toString()))
            .body(organizationLocation);
    }

    /**
     * {@code PUT  /organization-locations/:id} : Updates an existing organizationLocation.
     *
     * @param id the id of the organizationLocation to save.
     * @param organizationLocation the organizationLocation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationLocation,
     * or with status {@code 400 (Bad Request)} if the organizationLocation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organizationLocation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrganizationLocation> updateOrganizationLocation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrganizationLocation organizationLocation
    ) throws URISyntaxException {
        LOG.debug("REST request to update OrganizationLocation : {}, {}", id, organizationLocation);
        if (organizationLocation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organizationLocation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organizationLocationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        organizationLocation = organizationLocationRepository.save(organizationLocation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organizationLocation.getId().toString()))
            .body(organizationLocation);
    }

    /**
     * {@code PATCH  /organization-locations/:id} : Partial updates given fields of an existing organizationLocation, field will ignore if it is null
     *
     * @param id the id of the organizationLocation to save.
     * @param organizationLocation the organizationLocation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationLocation,
     * or with status {@code 400 (Bad Request)} if the organizationLocation is not valid,
     * or with status {@code 404 (Not Found)} if the organizationLocation is not found,
     * or with status {@code 500 (Internal Server Error)} if the organizationLocation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrganizationLocation> partialUpdateOrganizationLocation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrganizationLocation organizationLocation
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update OrganizationLocation partially : {}, {}", id, organizationLocation);
        if (organizationLocation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organizationLocation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organizationLocationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrganizationLocation> result = organizationLocationRepository
            .findById(organizationLocation.getId())
            .map(existingOrganizationLocation -> {
                if (organizationLocation.getOrgShortName() != null) {
                    existingOrganizationLocation.setOrgShortName(organizationLocation.getOrgShortName());
                }
                if (organizationLocation.getLocRef() != null) {
                    existingOrganizationLocation.setLocRef(organizationLocation.getLocRef());
                }
                if (organizationLocation.getName() != null) {
                    existingOrganizationLocation.setName(organizationLocation.getName());
                }
                if (organizationLocation.getCurrency() != null) {
                    existingOrganizationLocation.setCurrency(organizationLocation.getCurrency());
                }
                if (organizationLocation.getPhoneNumber() != null) {
                    existingOrganizationLocation.setPhoneNumber(organizationLocation.getPhoneNumber());
                }
                if (organizationLocation.getLanguages() != null) {
                    existingOrganizationLocation.setLanguages(organizationLocation.getLanguages());
                }
                if (organizationLocation.getTimezoneIanaName() != null) {
                    existingOrganizationLocation.setTimezoneIanaName(organizationLocation.getTimezoneIanaName());
                }
                if (organizationLocation.getTimezoneWindowsName() != null) {
                    existingOrganizationLocation.setTimezoneWindowsName(organizationLocation.getTimezoneWindowsName());
                }
                if (organizationLocation.getTimezoneTzIndex() != null) {
                    existingOrganizationLocation.setTimezoneTzIndex(organizationLocation.getTimezoneTzIndex());
                }
                if (organizationLocation.getAddressLine1() != null) {
                    existingOrganizationLocation.setAddressLine1(organizationLocation.getAddressLine1());
                }
                if (organizationLocation.getAddressLine2() != null) {
                    existingOrganizationLocation.setAddressLine2(organizationLocation.getAddressLine2());
                }
                if (organizationLocation.getAddressFloor() != null) {
                    existingOrganizationLocation.setAddressFloor(organizationLocation.getAddressFloor());
                }
                if (organizationLocation.getAddressLocality() != null) {
                    existingOrganizationLocation.setAddressLocality(organizationLocation.getAddressLocality());
                }
                if (organizationLocation.getAddressRegion() != null) {
                    existingOrganizationLocation.setAddressRegion(organizationLocation.getAddressRegion());
                }
                if (organizationLocation.getAddressPostalCode() != null) {
                    existingOrganizationLocation.setAddressPostalCode(organizationLocation.getAddressPostalCode());
                }
                if (organizationLocation.getAddressCountry() != null) {
                    existingOrganizationLocation.setAddressCountry(organizationLocation.getAddressCountry());
                }
                if (organizationLocation.getAddressNotes() != null) {
                    existingOrganizationLocation.setAddressNotes(organizationLocation.getAddressNotes());
                }
                if (organizationLocation.getGeoLatitude() != null) {
                    existingOrganizationLocation.setGeoLatitude(organizationLocation.getGeoLatitude());
                }
                if (organizationLocation.getGeoLongitude() != null) {
                    existingOrganizationLocation.setGeoLongitude(organizationLocation.getGeoLongitude());
                }
                if (organizationLocation.getPosPlatformName() != null) {
                    existingOrganizationLocation.setPosPlatformName(organizationLocation.getPosPlatformName());
                }
                if (organizationLocation.getPosPlatformVersion() != null) {
                    existingOrganizationLocation.setPosPlatformVersion(organizationLocation.getPosPlatformVersion());
                }

                return existingOrganizationLocation;
            })
            .map(organizationLocationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organizationLocation.getId().toString())
        );
    }

    /**
     * {@code GET  /organization-locations} : get all the organizationLocations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organizationLocations in body.
     */
    @GetMapping("")
    public List<OrganizationLocation> getAllOrganizationLocations() {
        LOG.debug("REST request to get all OrganizationLocations");
        return organizationLocationRepository.findAll();
    }

    /**
     * {@code GET  /organization-locations/:id} : get the "id" organizationLocation.
     *
     * @param id the id of the organizationLocation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organizationLocation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrganizationLocation> getOrganizationLocation(@PathVariable("id") Long id) {
        LOG.debug("REST request to get OrganizationLocation : {}", id);
        Optional<OrganizationLocation> organizationLocation = organizationLocationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(organizationLocation);
    }

    /**
     * {@code DELETE  /organization-locations/:id} : delete the "id" organizationLocation.
     *
     * @param id the id of the organizationLocation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizationLocation(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete OrganizationLocation : {}", id);
        organizationLocationRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
