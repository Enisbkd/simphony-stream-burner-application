package mc.sbm.simphonycloud.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.ModePaiementBI;
import mc.sbm.simphonycloud.repository.ModePaiementBIRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.ModePaiementBI}.
 */
@RestController
@RequestMapping("/api/mode-paiement-bis")
@Transactional
public class ModePaiementBIResource {

    private static final Logger LOG = LoggerFactory.getLogger(ModePaiementBIResource.class);

    private static final String ENTITY_NAME = "modePaiementBI";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModePaiementBIRepository modePaiementBIRepository;

    public ModePaiementBIResource(ModePaiementBIRepository modePaiementBIRepository) {
        this.modePaiementBIRepository = modePaiementBIRepository;
    }

    /**
     * {@code POST  /mode-paiement-bis} : Create a new modePaiementBI.
     *
     * @param modePaiementBI the modePaiementBI to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modePaiementBI, or with status {@code 400 (Bad Request)} if the modePaiementBI has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ModePaiementBI> createModePaiementBI(@RequestBody ModePaiementBI modePaiementBI) throws URISyntaxException {
        LOG.debug("REST request to save ModePaiementBI : {}", modePaiementBI);
        if (modePaiementBI.getId() != null) {
            throw new BadRequestAlertException("A new modePaiementBI cannot already have an ID", ENTITY_NAME, "idexists");
        }
        modePaiementBI = modePaiementBIRepository.save(modePaiementBI);
        return ResponseEntity.created(new URI("/api/mode-paiement-bis/" + modePaiementBI.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, modePaiementBI.getId().toString()))
            .body(modePaiementBI);
    }

    /**
     * {@code PUT  /mode-paiement-bis/:id} : Updates an existing modePaiementBI.
     *
     * @param id the id of the modePaiementBI to save.
     * @param modePaiementBI the modePaiementBI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modePaiementBI,
     * or with status {@code 400 (Bad Request)} if the modePaiementBI is not valid,
     * or with status {@code 500 (Internal Server Error)} if the modePaiementBI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ModePaiementBI> updateModePaiementBI(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody ModePaiementBI modePaiementBI
    ) throws URISyntaxException {
        LOG.debug("REST request to update ModePaiementBI : {}, {}", id, modePaiementBI);
        if (modePaiementBI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modePaiementBI.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!modePaiementBIRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        modePaiementBI = modePaiementBIRepository.save(modePaiementBI);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modePaiementBI.getId().toString()))
            .body(modePaiementBI);
    }

    /**
     * {@code PATCH  /mode-paiement-bis/:id} : Partial updates given fields of an existing modePaiementBI, field will ignore if it is null
     *
     * @param id the id of the modePaiementBI to save.
     * @param modePaiementBI the modePaiementBI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modePaiementBI,
     * or with status {@code 400 (Bad Request)} if the modePaiementBI is not valid,
     * or with status {@code 404 (Not Found)} if the modePaiementBI is not found,
     * or with status {@code 500 (Internal Server Error)} if the modePaiementBI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ModePaiementBI> partialUpdateModePaiementBI(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody ModePaiementBI modePaiementBI
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ModePaiementBI partially : {}, {}", id, modePaiementBI);
        if (modePaiementBI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modePaiementBI.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!modePaiementBIRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ModePaiementBI> result = modePaiementBIRepository
            .findById(modePaiementBI.getId())
            .map(existingModePaiementBI -> {
                if (modePaiementBI.getLocRef() != null) {
                    existingModePaiementBI.setLocRef(modePaiementBI.getLocRef());
                }
                if (modePaiementBI.getNum() != null) {
                    existingModePaiementBI.setNum(modePaiementBI.getNum());
                }
                if (modePaiementBI.getName() != null) {
                    existingModePaiementBI.setName(modePaiementBI.getName());
                }
                if (modePaiementBI.getType() != null) {
                    existingModePaiementBI.setType(modePaiementBI.getType());
                }

                return existingModePaiementBI;
            })
            .map(modePaiementBIRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modePaiementBI.getId().toString())
        );
    }

    /**
     * {@code GET  /mode-paiement-bis} : get all the modePaiementBIS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modePaiementBIS in body.
     */
    @GetMapping("")
    public List<ModePaiementBI> getAllModePaiementBIS() {
        LOG.debug("REST request to get all ModePaiementBIS");
        return modePaiementBIRepository.findAll();
    }

    /**
     * {@code GET  /mode-paiement-bis/:id} : get the "id" modePaiementBI.
     *
     * @param id the id of the modePaiementBI to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modePaiementBI, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ModePaiementBI> getModePaiementBI(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get ModePaiementBI : {}", id);
        Optional<ModePaiementBI> modePaiementBI = modePaiementBIRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(modePaiementBI);
    }

    /**
     * {@code DELETE  /mode-paiement-bis/:id} : delete the "id" modePaiementBI.
     *
     * @param id the id of the modePaiementBI to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModePaiementBI(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete ModePaiementBI : {}", id);
        modePaiementBIRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
