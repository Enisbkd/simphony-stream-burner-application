package mc.sbm.simphonycloud.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.PointDeVenteTrans;
import mc.sbm.simphonycloud.repository.PointDeVenteTransRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.PointDeVenteTrans}.
 */
@RestController
@RequestMapping("/api/point-de-vente-trans")
@Transactional
public class PointDeVenteTransResource {

    private static final Logger LOG = LoggerFactory.getLogger(PointDeVenteTransResource.class);

    private static final String ENTITY_NAME = "pointDeVenteTrans";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PointDeVenteTransRepository pointDeVenteTransRepository;

    public PointDeVenteTransResource(PointDeVenteTransRepository pointDeVenteTransRepository) {
        this.pointDeVenteTransRepository = pointDeVenteTransRepository;
    }

    /**
     * {@code POST  /point-de-vente-trans} : Create a new pointDeVenteTrans.
     *
     * @param pointDeVenteTrans the pointDeVenteTrans to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pointDeVenteTrans, or with status {@code 400 (Bad Request)} if the pointDeVenteTrans has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PointDeVenteTrans> createPointDeVenteTrans(@RequestBody PointDeVenteTrans pointDeVenteTrans)
        throws URISyntaxException {
        LOG.debug("REST request to save PointDeVenteTrans : {}", pointDeVenteTrans);
        if (pointDeVenteTrans.getId() != null) {
            throw new BadRequestAlertException("A new pointDeVenteTrans cannot already have an ID", ENTITY_NAME, "idexists");
        }
        pointDeVenteTrans = pointDeVenteTransRepository.save(pointDeVenteTrans);
        return ResponseEntity.created(new URI("/api/point-de-vente-trans/" + pointDeVenteTrans.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, pointDeVenteTrans.getId().toString()))
            .body(pointDeVenteTrans);
    }

    /**
     * {@code PUT  /point-de-vente-trans/:id} : Updates an existing pointDeVenteTrans.
     *
     * @param id the id of the pointDeVenteTrans to save.
     * @param pointDeVenteTrans the pointDeVenteTrans to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pointDeVenteTrans,
     * or with status {@code 400 (Bad Request)} if the pointDeVenteTrans is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pointDeVenteTrans couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PointDeVenteTrans> updatePointDeVenteTrans(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody PointDeVenteTrans pointDeVenteTrans
    ) throws URISyntaxException {
        LOG.debug("REST request to update PointDeVenteTrans : {}, {}", id, pointDeVenteTrans);
        if (pointDeVenteTrans.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pointDeVenteTrans.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pointDeVenteTransRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        pointDeVenteTrans = pointDeVenteTransRepository.save(pointDeVenteTrans);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pointDeVenteTrans.getId().toString()))
            .body(pointDeVenteTrans);
    }

    /**
     * {@code PATCH  /point-de-vente-trans/:id} : Partial updates given fields of an existing pointDeVenteTrans, field will ignore if it is null
     *
     * @param id the id of the pointDeVenteTrans to save.
     * @param pointDeVenteTrans the pointDeVenteTrans to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pointDeVenteTrans,
     * or with status {@code 400 (Bad Request)} if the pointDeVenteTrans is not valid,
     * or with status {@code 404 (Not Found)} if the pointDeVenteTrans is not found,
     * or with status {@code 500 (Internal Server Error)} if the pointDeVenteTrans couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PointDeVenteTrans> partialUpdatePointDeVenteTrans(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody PointDeVenteTrans pointDeVenteTrans
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PointDeVenteTrans partially : {}, {}", id, pointDeVenteTrans);
        if (pointDeVenteTrans.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pointDeVenteTrans.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pointDeVenteTransRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PointDeVenteTrans> result = pointDeVenteTransRepository
            .findById(pointDeVenteTrans.getId())
            .map(existingPointDeVenteTrans -> {
                if (pointDeVenteTrans.getRvcRef() != null) {
                    existingPointDeVenteTrans.setRvcRef(pointDeVenteTrans.getRvcRef());
                }
                if (pointDeVenteTrans.getName() != null) {
                    existingPointDeVenteTrans.setName(pointDeVenteTrans.getName());
                }
                if (pointDeVenteTrans.getLocRef() != null) {
                    existingPointDeVenteTrans.setLocRef(pointDeVenteTrans.getLocRef());
                }
                if (pointDeVenteTrans.getOrgShortName() != null) {
                    existingPointDeVenteTrans.setOrgShortName(pointDeVenteTrans.getOrgShortName());
                }
                if (pointDeVenteTrans.getAddress() != null) {
                    existingPointDeVenteTrans.setAddress(pointDeVenteTrans.getAddress());
                }

                return existingPointDeVenteTrans;
            })
            .map(pointDeVenteTransRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pointDeVenteTrans.getId().toString())
        );
    }

    /**
     * {@code GET  /point-de-vente-trans} : get all the pointDeVenteTrans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pointDeVenteTrans in body.
     */
    @GetMapping("")
    public List<PointDeVenteTrans> getAllPointDeVenteTrans() {
        LOG.debug("REST request to get all PointDeVenteTrans");
        return pointDeVenteTransRepository.findAll();
    }

    /**
     * {@code GET  /point-de-vente-trans/:id} : get the "id" pointDeVenteTrans.
     *
     * @param id the id of the pointDeVenteTrans to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pointDeVenteTrans, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PointDeVenteTrans> getPointDeVenteTrans(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get PointDeVenteTrans : {}", id);
        Optional<PointDeVenteTrans> pointDeVenteTrans = pointDeVenteTransRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pointDeVenteTrans);
    }

    /**
     * {@code DELETE  /point-de-vente-trans/:id} : delete the "id" pointDeVenteTrans.
     *
     * @param id the id of the pointDeVenteTrans to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePointDeVenteTrans(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete PointDeVenteTrans : {}", id);
        pointDeVenteTransRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
