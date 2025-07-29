package mc.sbm.simphonycloud.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.CodeRaison;
import mc.sbm.simphonycloud.repository.CodeRaisonRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.CodeRaison}.
 */
@RestController
@RequestMapping("/api/code-raisons")
@Transactional
public class CodeRaisonResource {

    private static final Logger LOG = LoggerFactory.getLogger(CodeRaisonResource.class);

    private static final String ENTITY_NAME = "codeRaison";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CodeRaisonRepository codeRaisonRepository;

    public CodeRaisonResource(CodeRaisonRepository codeRaisonRepository) {
        this.codeRaisonRepository = codeRaisonRepository;
    }

    /**
     * {@code POST  /code-raisons} : Create a new codeRaison.
     *
     * @param codeRaison the codeRaison to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new codeRaison, or with status {@code 400 (Bad Request)} if the codeRaison has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CodeRaison> createCodeRaison(@Valid @RequestBody CodeRaison codeRaison) throws URISyntaxException {
        LOG.debug("REST request to save CodeRaison : {}", codeRaison);
        if (codeRaison.getId() != null) {
            throw new BadRequestAlertException("A new codeRaison cannot already have an ID", ENTITY_NAME, "idexists");
        }
        codeRaison = codeRaisonRepository.save(codeRaison);
        return ResponseEntity.created(new URI("/api/code-raisons/" + codeRaison.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, codeRaison.getId().toString()))
            .body(codeRaison);
    }

    /**
     * {@code PUT  /code-raisons/:id} : Updates an existing codeRaison.
     *
     * @param id the id of the codeRaison to save.
     * @param codeRaison the codeRaison to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated codeRaison,
     * or with status {@code 400 (Bad Request)} if the codeRaison is not valid,
     * or with status {@code 500 (Internal Server Error)} if the codeRaison couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CodeRaison> updateCodeRaison(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CodeRaison codeRaison
    ) throws URISyntaxException {
        LOG.debug("REST request to update CodeRaison : {}, {}", id, codeRaison);
        if (codeRaison.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, codeRaison.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!codeRaisonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        codeRaison = codeRaisonRepository.save(codeRaison);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, codeRaison.getId().toString()))
            .body(codeRaison);
    }

    /**
     * {@code PATCH  /code-raisons/:id} : Partial updates given fields of an existing codeRaison, field will ignore if it is null
     *
     * @param id the id of the codeRaison to save.
     * @param codeRaison the codeRaison to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated codeRaison,
     * or with status {@code 400 (Bad Request)} if the codeRaison is not valid,
     * or with status {@code 404 (Not Found)} if the codeRaison is not found,
     * or with status {@code 500 (Internal Server Error)} if the codeRaison couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CodeRaison> partialUpdateCodeRaison(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CodeRaison codeRaison
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CodeRaison partially : {}, {}", id, codeRaison);
        if (codeRaison.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, codeRaison.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!codeRaisonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CodeRaison> result = codeRaisonRepository
            .findById(codeRaison.getId())
            .map(existingCodeRaison -> {
                if (codeRaison.getNomCourt() != null) {
                    existingCodeRaison.setNomCourt(codeRaison.getNomCourt());
                }
                if (codeRaison.getNomMstr() != null) {
                    existingCodeRaison.setNomMstr(codeRaison.getNomMstr());
                }
                if (codeRaison.getNumMstr() != null) {
                    existingCodeRaison.setNumMstr(codeRaison.getNumMstr());
                }
                if (codeRaison.getName() != null) {
                    existingCodeRaison.setName(codeRaison.getName());
                }
                if (codeRaison.getEtablissementRef() != null) {
                    existingCodeRaison.setEtablissementRef(codeRaison.getEtablissementRef());
                }

                return existingCodeRaison;
            })
            .map(codeRaisonRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, codeRaison.getId().toString())
        );
    }

    /**
     * {@code GET  /code-raisons} : get all the codeRaisons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of codeRaisons in body.
     */
    @GetMapping("")
    public List<CodeRaison> getAllCodeRaisons() {
        LOG.debug("REST request to get all CodeRaisons");
        return codeRaisonRepository.findAll();
    }

    /**
     * {@code GET  /code-raisons/:id} : get the "id" codeRaison.
     *
     * @param id the id of the codeRaison to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the codeRaison, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CodeRaison> getCodeRaison(@PathVariable("id") Long id) {
        LOG.debug("REST request to get CodeRaison : {}", id);
        Optional<CodeRaison> codeRaison = codeRaisonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(codeRaison);
    }

    /**
     * {@code DELETE  /code-raisons/:id} : delete the "id" codeRaison.
     *
     * @param id the id of the codeRaison to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCodeRaison(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete CodeRaison : {}", id);
        codeRaisonRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
