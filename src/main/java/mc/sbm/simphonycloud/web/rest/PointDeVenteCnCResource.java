package mc.sbm.simphonycloud.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.PointDeVenteCnC;
import mc.sbm.simphonycloud.repository.PointDeVenteCnCRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.PointDeVenteCnC}.
 */
@RestController
@RequestMapping("/api/point-de-vente-cn-cs")
@Transactional
public class PointDeVenteCnCResource {

    private static final Logger LOG = LoggerFactory.getLogger(PointDeVenteCnCResource.class);

    private static final String ENTITY_NAME = "pointDeVenteCnC";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PointDeVenteCnCRepository pointDeVenteCnCRepository;

    public PointDeVenteCnCResource(PointDeVenteCnCRepository pointDeVenteCnCRepository) {
        this.pointDeVenteCnCRepository = pointDeVenteCnCRepository;
    }

    /**
     * {@code POST  /point-de-vente-cn-cs} : Create a new pointDeVenteCnC.
     *
     * @param pointDeVenteCnC the pointDeVenteCnC to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pointDeVenteCnC, or with status {@code 400 (Bad Request)} if the pointDeVenteCnC has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PointDeVenteCnC> createPointDeVenteCnC(@RequestBody PointDeVenteCnC pointDeVenteCnC) throws URISyntaxException {
        LOG.debug("REST request to save PointDeVenteCnC : {}", pointDeVenteCnC);
        if (pointDeVenteCnC.getId() != null) {
            throw new BadRequestAlertException("A new pointDeVenteCnC cannot already have an ID", ENTITY_NAME, "idexists");
        }
        pointDeVenteCnC = pointDeVenteCnCRepository.save(pointDeVenteCnC);
        return ResponseEntity.created(new URI("/api/point-de-vente-cn-cs/" + pointDeVenteCnC.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, pointDeVenteCnC.getId().toString()))
            .body(pointDeVenteCnC);
    }

    /**
     * {@code PUT  /point-de-vente-cn-cs/:id} : Updates an existing pointDeVenteCnC.
     *
     * @param id the id of the pointDeVenteCnC to save.
     * @param pointDeVenteCnC the pointDeVenteCnC to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pointDeVenteCnC,
     * or with status {@code 400 (Bad Request)} if the pointDeVenteCnC is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pointDeVenteCnC couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PointDeVenteCnC> updatePointDeVenteCnC(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody PointDeVenteCnC pointDeVenteCnC
    ) throws URISyntaxException {
        LOG.debug("REST request to update PointDeVenteCnC : {}, {}", id, pointDeVenteCnC);
        if (pointDeVenteCnC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pointDeVenteCnC.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pointDeVenteCnCRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        pointDeVenteCnC = pointDeVenteCnCRepository.save(pointDeVenteCnC);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pointDeVenteCnC.getId().toString()))
            .body(pointDeVenteCnC);
    }

    /**
     * {@code PATCH  /point-de-vente-cn-cs/:id} : Partial updates given fields of an existing pointDeVenteCnC, field will ignore if it is null
     *
     * @param id the id of the pointDeVenteCnC to save.
     * @param pointDeVenteCnC the pointDeVenteCnC to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pointDeVenteCnC,
     * or with status {@code 400 (Bad Request)} if the pointDeVenteCnC is not valid,
     * or with status {@code 404 (Not Found)} if the pointDeVenteCnC is not found,
     * or with status {@code 500 (Internal Server Error)} if the pointDeVenteCnC couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PointDeVenteCnC> partialUpdatePointDeVenteCnC(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody PointDeVenteCnC pointDeVenteCnC
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PointDeVenteCnC partially : {}, {}", id, pointDeVenteCnC);
        if (pointDeVenteCnC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pointDeVenteCnC.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pointDeVenteCnCRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PointDeVenteCnC> result = pointDeVenteCnCRepository
            .findById(pointDeVenteCnC.getId())
            .map(existingPointDeVenteCnC -> {
                if (pointDeVenteCnC.getLocHierUnitId() != null) {
                    existingPointDeVenteCnC.setLocHierUnitId(pointDeVenteCnC.getLocHierUnitId());
                }
                if (pointDeVenteCnC.getLocObjNum() != null) {
                    existingPointDeVenteCnC.setLocObjNum(pointDeVenteCnC.getLocObjNum());
                }
                if (pointDeVenteCnC.getRvcId() != null) {
                    existingPointDeVenteCnC.setRvcId(pointDeVenteCnC.getRvcId());
                }
                if (pointDeVenteCnC.getKdsControllerId() != null) {
                    existingPointDeVenteCnC.setKdsControllerId(pointDeVenteCnC.getKdsControllerId());
                }
                if (pointDeVenteCnC.getHierUnitId() != null) {
                    existingPointDeVenteCnC.setHierUnitId(pointDeVenteCnC.getHierUnitId());
                }
                if (pointDeVenteCnC.getObjectNum() != null) {
                    existingPointDeVenteCnC.setObjectNum(pointDeVenteCnC.getObjectNum());
                }
                if (pointDeVenteCnC.getName() != null) {
                    existingPointDeVenteCnC.setName(pointDeVenteCnC.getName());
                }
                if (pointDeVenteCnC.getDataExtensions() != null) {
                    existingPointDeVenteCnC.setDataExtensions(pointDeVenteCnC.getDataExtensions());
                }

                return existingPointDeVenteCnC;
            })
            .map(pointDeVenteCnCRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pointDeVenteCnC.getId().toString())
        );
    }

    /**
     * {@code GET  /point-de-vente-cn-cs} : get all the pointDeVenteCnCS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pointDeVenteCnCS in body.
     */
    @GetMapping("")
    public List<PointDeVenteCnC> getAllPointDeVenteCnCS() {
        LOG.debug("REST request to get all PointDeVenteCnCS");
        return pointDeVenteCnCRepository.findAll();
    }

    /**
     * {@code GET  /point-de-vente-cn-cs/:id} : get the "id" pointDeVenteCnC.
     *
     * @param id the id of the pointDeVenteCnC to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pointDeVenteCnC, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PointDeVenteCnC> getPointDeVenteCnC(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get PointDeVenteCnC : {}", id);
        Optional<PointDeVenteCnC> pointDeVenteCnC = pointDeVenteCnCRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pointDeVenteCnC);
    }

    /**
     * {@code DELETE  /point-de-vente-cn-cs/:id} : delete the "id" pointDeVenteCnC.
     *
     * @param id the id of the pointDeVenteCnC to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePointDeVenteCnC(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete PointDeVenteCnC : {}", id);
        pointDeVenteCnCRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
