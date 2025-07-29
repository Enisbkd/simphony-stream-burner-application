package mc.sbm.simphonycloud.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.ModePaiementTrans;
import mc.sbm.simphonycloud.repository.ModePaiementTransRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.ModePaiementTrans}.
 */
@RestController
@RequestMapping("/api/mode-paiement-trans")
@Transactional
public class ModePaiementTransResource {

    private static final Logger LOG = LoggerFactory.getLogger(ModePaiementTransResource.class);

    private static final String ENTITY_NAME = "modePaiementTrans";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModePaiementTransRepository modePaiementTransRepository;

    public ModePaiementTransResource(ModePaiementTransRepository modePaiementTransRepository) {
        this.modePaiementTransRepository = modePaiementTransRepository;
    }

    /**
     * {@code POST  /mode-paiement-trans} : Create a new modePaiementTrans.
     *
     * @param modePaiementTrans the modePaiementTrans to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modePaiementTrans, or with status {@code 400 (Bad Request)} if the modePaiementTrans has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ModePaiementTrans> createModePaiementTrans(@RequestBody ModePaiementTrans modePaiementTrans)
        throws URISyntaxException {
        LOG.debug("REST request to save ModePaiementTrans : {}", modePaiementTrans);
        if (modePaiementTrans.getId() != null) {
            throw new BadRequestAlertException("A new modePaiementTrans cannot already have an ID", ENTITY_NAME, "idexists");
        }
        modePaiementTrans = modePaiementTransRepository.save(modePaiementTrans);
        return ResponseEntity.created(new URI("/api/mode-paiement-trans/" + modePaiementTrans.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, modePaiementTrans.getId().toString()))
            .body(modePaiementTrans);
    }

    /**
     * {@code PUT  /mode-paiement-trans/:id} : Updates an existing modePaiementTrans.
     *
     * @param id the id of the modePaiementTrans to save.
     * @param modePaiementTrans the modePaiementTrans to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modePaiementTrans,
     * or with status {@code 400 (Bad Request)} if the modePaiementTrans is not valid,
     * or with status {@code 500 (Internal Server Error)} if the modePaiementTrans couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ModePaiementTrans> updateModePaiementTrans(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody ModePaiementTrans modePaiementTrans
    ) throws URISyntaxException {
        LOG.debug("REST request to update ModePaiementTrans : {}, {}", id, modePaiementTrans);
        if (modePaiementTrans.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modePaiementTrans.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!modePaiementTransRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        modePaiementTrans = modePaiementTransRepository.save(modePaiementTrans);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modePaiementTrans.getId().toString()))
            .body(modePaiementTrans);
    }

    /**
     * {@code PATCH  /mode-paiement-trans/:id} : Partial updates given fields of an existing modePaiementTrans, field will ignore if it is null
     *
     * @param id the id of the modePaiementTrans to save.
     * @param modePaiementTrans the modePaiementTrans to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modePaiementTrans,
     * or with status {@code 400 (Bad Request)} if the modePaiementTrans is not valid,
     * or with status {@code 404 (Not Found)} if the modePaiementTrans is not found,
     * or with status {@code 500 (Internal Server Error)} if the modePaiementTrans couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ModePaiementTrans> partialUpdateModePaiementTrans(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody ModePaiementTrans modePaiementTrans
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ModePaiementTrans partially : {}, {}", id, modePaiementTrans);
        if (modePaiementTrans.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modePaiementTrans.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!modePaiementTransRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ModePaiementTrans> result = modePaiementTransRepository
            .findById(modePaiementTrans.getId())
            .map(existingModePaiementTrans -> {
                if (modePaiementTrans.getTenderId() != null) {
                    existingModePaiementTrans.setTenderId(modePaiementTrans.getTenderId());
                }
                if (modePaiementTrans.getName() != null) {
                    existingModePaiementTrans.setName(modePaiementTrans.getName());
                }
                if (modePaiementTrans.getType() != null) {
                    existingModePaiementTrans.setType(modePaiementTrans.getType());
                }
                if (modePaiementTrans.getExtensions() != null) {
                    existingModePaiementTrans.setExtensions(modePaiementTrans.getExtensions());
                }
                if (modePaiementTrans.getOrgShortName() != null) {
                    existingModePaiementTrans.setOrgShortName(modePaiementTrans.getOrgShortName());
                }
                if (modePaiementTrans.getLocRef() != null) {
                    existingModePaiementTrans.setLocRef(modePaiementTrans.getLocRef());
                }
                if (modePaiementTrans.getRvcRef() != null) {
                    existingModePaiementTrans.setRvcRef(modePaiementTrans.getRvcRef());
                }

                return existingModePaiementTrans;
            })
            .map(modePaiementTransRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modePaiementTrans.getId().toString())
        );
    }

    /**
     * {@code GET  /mode-paiement-trans} : get all the modePaiementTrans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modePaiementTrans in body.
     */
    @GetMapping("")
    public List<ModePaiementTrans> getAllModePaiementTrans() {
        LOG.debug("REST request to get all ModePaiementTrans");
        return modePaiementTransRepository.findAll();
    }

    /**
     * {@code GET  /mode-paiement-trans/:id} : get the "id" modePaiementTrans.
     *
     * @param id the id of the modePaiementTrans to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modePaiementTrans, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ModePaiementTrans> getModePaiementTrans(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get ModePaiementTrans : {}", id);
        Optional<ModePaiementTrans> modePaiementTrans = modePaiementTransRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(modePaiementTrans);
    }

    /**
     * {@code DELETE  /mode-paiement-trans/:id} : delete the "id" modePaiementTrans.
     *
     * @param id the id of the modePaiementTrans to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModePaiementTrans(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete ModePaiementTrans : {}", id);
        modePaiementTransRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
