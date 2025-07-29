package mc.sbm.simphonycloud.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.CodeRaisonBI;
import mc.sbm.simphonycloud.repository.CodeRaisonBIRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.CodeRaisonBI}.
 */
@RestController
@RequestMapping("/api/code-raison-bis")
@Transactional
public class CodeRaisonBIResource {

    private static final Logger LOG = LoggerFactory.getLogger(CodeRaisonBIResource.class);

    private static final String ENTITY_NAME = "codeRaisonBI";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CodeRaisonBIRepository codeRaisonBIRepository;

    public CodeRaisonBIResource(CodeRaisonBIRepository codeRaisonBIRepository) {
        this.codeRaisonBIRepository = codeRaisonBIRepository;
    }

    /**
     * {@code POST  /code-raison-bis} : Create a new codeRaisonBI.
     *
     * @param codeRaisonBI the codeRaisonBI to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new codeRaisonBI, or with status {@code 400 (Bad Request)} if the codeRaisonBI has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CodeRaisonBI> createCodeRaisonBI(@Valid @RequestBody CodeRaisonBI codeRaisonBI) throws URISyntaxException {
        LOG.debug("REST request to save CodeRaisonBI : {}", codeRaisonBI);
        if (codeRaisonBI.getId() != null) {
            throw new BadRequestAlertException("A new codeRaisonBI cannot already have an ID", ENTITY_NAME, "idexists");
        }
        codeRaisonBI = codeRaisonBIRepository.save(codeRaisonBI);
        return ResponseEntity.created(new URI("/api/code-raison-bis/" + codeRaisonBI.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, codeRaisonBI.getId().toString()))
            .body(codeRaisonBI);
    }

    /**
     * {@code PUT  /code-raison-bis/:id} : Updates an existing codeRaisonBI.
     *
     * @param id the id of the codeRaisonBI to save.
     * @param codeRaisonBI the codeRaisonBI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated codeRaisonBI,
     * or with status {@code 400 (Bad Request)} if the codeRaisonBI is not valid,
     * or with status {@code 500 (Internal Server Error)} if the codeRaisonBI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CodeRaisonBI> updateCodeRaisonBI(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CodeRaisonBI codeRaisonBI
    ) throws URISyntaxException {
        LOG.debug("REST request to update CodeRaisonBI : {}, {}", id, codeRaisonBI);
        if (codeRaisonBI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, codeRaisonBI.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!codeRaisonBIRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        codeRaisonBI = codeRaisonBIRepository.save(codeRaisonBI);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, codeRaisonBI.getId().toString()))
            .body(codeRaisonBI);
    }

    /**
     * {@code PATCH  /code-raison-bis/:id} : Partial updates given fields of an existing codeRaisonBI, field will ignore if it is null
     *
     * @param id the id of the codeRaisonBI to save.
     * @param codeRaisonBI the codeRaisonBI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated codeRaisonBI,
     * or with status {@code 400 (Bad Request)} if the codeRaisonBI is not valid,
     * or with status {@code 404 (Not Found)} if the codeRaisonBI is not found,
     * or with status {@code 500 (Internal Server Error)} if the codeRaisonBI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CodeRaisonBI> partialUpdateCodeRaisonBI(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CodeRaisonBI codeRaisonBI
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CodeRaisonBI partially : {}, {}", id, codeRaisonBI);
        if (codeRaisonBI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, codeRaisonBI.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!codeRaisonBIRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CodeRaisonBI> result = codeRaisonBIRepository
            .findById(codeRaisonBI.getId())
            .map(existingCodeRaisonBI -> {
                if (codeRaisonBI.getNomCourt() != null) {
                    existingCodeRaisonBI.setNomCourt(codeRaisonBI.getNomCourt());
                }
                if (codeRaisonBI.getNomMstr() != null) {
                    existingCodeRaisonBI.setNomMstr(codeRaisonBI.getNomMstr());
                }
                if (codeRaisonBI.getNumMstr() != null) {
                    existingCodeRaisonBI.setNumMstr(codeRaisonBI.getNumMstr());
                }
                if (codeRaisonBI.getName() != null) {
                    existingCodeRaisonBI.setName(codeRaisonBI.getName());
                }
                if (codeRaisonBI.getEtablissementRef() != null) {
                    existingCodeRaisonBI.setEtablissementRef(codeRaisonBI.getEtablissementRef());
                }

                return existingCodeRaisonBI;
            })
            .map(codeRaisonBIRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, codeRaisonBI.getId().toString())
        );
    }

    /**
     * {@code GET  /code-raison-bis} : get all the codeRaisonBIS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of codeRaisonBIS in body.
     */
    @GetMapping("")
    public List<CodeRaisonBI> getAllCodeRaisonBIS() {
        LOG.debug("REST request to get all CodeRaisonBIS");
        return codeRaisonBIRepository.findAll();
    }

    /**
     * {@code GET  /code-raison-bis/:id} : get the "id" codeRaisonBI.
     *
     * @param id the id of the codeRaisonBI to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the codeRaisonBI, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CodeRaisonBI> getCodeRaisonBI(@PathVariable("id") Long id) {
        LOG.debug("REST request to get CodeRaisonBI : {}", id);
        Optional<CodeRaisonBI> codeRaisonBI = codeRaisonBIRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(codeRaisonBI);
    }

    /**
     * {@code DELETE  /code-raison-bis/:id} : delete the "id" codeRaisonBI.
     *
     * @param id the id of the codeRaisonBI to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCodeRaisonBI(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete CodeRaisonBI : {}", id);
        codeRaisonBIRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
