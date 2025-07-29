package mc.sbm.simphonycloud.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.BarCodeTrans;
import mc.sbm.simphonycloud.repository.BarCodeTransRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.BarCodeTrans}.
 */
@RestController
@RequestMapping("/api/bar-code-trans")
@Transactional
public class BarCodeTransResource {

    private static final Logger LOG = LoggerFactory.getLogger(BarCodeTransResource.class);

    private static final String ENTITY_NAME = "barCodeTrans";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BarCodeTransRepository barCodeTransRepository;

    public BarCodeTransResource(BarCodeTransRepository barCodeTransRepository) {
        this.barCodeTransRepository = barCodeTransRepository;
    }

    /**
     * {@code POST  /bar-code-trans} : Create a new barCodeTrans.
     *
     * @param barCodeTrans the barCodeTrans to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new barCodeTrans, or with status {@code 400 (Bad Request)} if the barCodeTrans has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BarCodeTrans> createBarCodeTrans(@RequestBody BarCodeTrans barCodeTrans) throws URISyntaxException {
        LOG.debug("REST request to save BarCodeTrans : {}", barCodeTrans);
        if (barCodeTrans.getId() != null) {
            throw new BadRequestAlertException("A new barCodeTrans cannot already have an ID", ENTITY_NAME, "idexists");
        }
        barCodeTrans = barCodeTransRepository.save(barCodeTrans);
        return ResponseEntity.created(new URI("/api/bar-code-trans/" + barCodeTrans.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, barCodeTrans.getId().toString()))
            .body(barCodeTrans);
    }

    /**
     * {@code PUT  /bar-code-trans/:id} : Updates an existing barCodeTrans.
     *
     * @param id the id of the barCodeTrans to save.
     * @param barCodeTrans the barCodeTrans to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated barCodeTrans,
     * or with status {@code 400 (Bad Request)} if the barCodeTrans is not valid,
     * or with status {@code 500 (Internal Server Error)} if the barCodeTrans couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BarCodeTrans> updateBarCodeTrans(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody BarCodeTrans barCodeTrans
    ) throws URISyntaxException {
        LOG.debug("REST request to update BarCodeTrans : {}, {}", id, barCodeTrans);
        if (barCodeTrans.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, barCodeTrans.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!barCodeTransRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        barCodeTrans = barCodeTransRepository.save(barCodeTrans);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, barCodeTrans.getId().toString()))
            .body(barCodeTrans);
    }

    /**
     * {@code PATCH  /bar-code-trans/:id} : Partial updates given fields of an existing barCodeTrans, field will ignore if it is null
     *
     * @param id the id of the barCodeTrans to save.
     * @param barCodeTrans the barCodeTrans to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated barCodeTrans,
     * or with status {@code 400 (Bad Request)} if the barCodeTrans is not valid,
     * or with status {@code 404 (Not Found)} if the barCodeTrans is not found,
     * or with status {@code 500 (Internal Server Error)} if the barCodeTrans couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BarCodeTrans> partialUpdateBarCodeTrans(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody BarCodeTrans barCodeTrans
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update BarCodeTrans partially : {}, {}", id, barCodeTrans);
        if (barCodeTrans.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, barCodeTrans.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!barCodeTransRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BarCodeTrans> result = barCodeTransRepository
            .findById(barCodeTrans.getId())
            .map(existingBarCodeTrans -> {
                if (barCodeTrans.getLocRef() != null) {
                    existingBarCodeTrans.setLocRef(barCodeTrans.getLocRef());
                }
                if (barCodeTrans.getRvcRef() != null) {
                    existingBarCodeTrans.setRvcRef(barCodeTrans.getRvcRef());
                }
                if (barCodeTrans.getBarcodeId() != null) {
                    existingBarCodeTrans.setBarcodeId(barCodeTrans.getBarcodeId());
                }
                if (barCodeTrans.getBarcode() != null) {
                    existingBarCodeTrans.setBarcode(barCodeTrans.getBarcode());
                }
                if (barCodeTrans.getMenuItemId() != null) {
                    existingBarCodeTrans.setMenuItemId(barCodeTrans.getMenuItemId());
                }
                if (barCodeTrans.getDefenitionSequence() != null) {
                    existingBarCodeTrans.setDefenitionSequence(barCodeTrans.getDefenitionSequence());
                }
                if (barCodeTrans.getPrice() != null) {
                    existingBarCodeTrans.setPrice(barCodeTrans.getPrice());
                }
                if (barCodeTrans.getPriceSequence() != null) {
                    existingBarCodeTrans.setPriceSequence(barCodeTrans.getPriceSequence());
                }
                if (barCodeTrans.getPreparationCost() != null) {
                    existingBarCodeTrans.setPreparationCost(barCodeTrans.getPreparationCost());
                }

                return existingBarCodeTrans;
            })
            .map(barCodeTransRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, barCodeTrans.getId().toString())
        );
    }

    /**
     * {@code GET  /bar-code-trans} : get all the barCodeTrans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of barCodeTrans in body.
     */
    @GetMapping("")
    public List<BarCodeTrans> getAllBarCodeTrans() {
        LOG.debug("REST request to get all BarCodeTrans");
        return barCodeTransRepository.findAll();
    }

    /**
     * {@code GET  /bar-code-trans/:id} : get the "id" barCodeTrans.
     *
     * @param id the id of the barCodeTrans to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the barCodeTrans, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BarCodeTrans> getBarCodeTrans(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get BarCodeTrans : {}", id);
        Optional<BarCodeTrans> barCodeTrans = barCodeTransRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(barCodeTrans);
    }

    /**
     * {@code DELETE  /bar-code-trans/:id} : delete the "id" barCodeTrans.
     *
     * @param id the id of the barCodeTrans to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBarCodeTrans(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete BarCodeTrans : {}", id);
        barCodeTransRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
