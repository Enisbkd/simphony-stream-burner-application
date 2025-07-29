package mc.sbm.simphonycloud.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.RemiseBI;
import mc.sbm.simphonycloud.repository.RemiseBIRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.RemiseBI}.
 */
@RestController
@RequestMapping("/api/remise-bis")
@Transactional
public class RemiseBIResource {

    private static final Logger LOG = LoggerFactory.getLogger(RemiseBIResource.class);

    private static final String ENTITY_NAME = "remiseBI";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RemiseBIRepository remiseBIRepository;

    public RemiseBIResource(RemiseBIRepository remiseBIRepository) {
        this.remiseBIRepository = remiseBIRepository;
    }

    /**
     * {@code POST  /remise-bis} : Create a new remiseBI.
     *
     * @param remiseBI the remiseBI to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new remiseBI, or with status {@code 400 (Bad Request)} if the remiseBI has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RemiseBI> createRemiseBI(@RequestBody RemiseBI remiseBI) throws URISyntaxException {
        LOG.debug("REST request to save RemiseBI : {}", remiseBI);
        if (remiseBI.getId() != null) {
            throw new BadRequestAlertException("A new remiseBI cannot already have an ID", ENTITY_NAME, "idexists");
        }
        remiseBI = remiseBIRepository.save(remiseBI);
        return ResponseEntity.created(new URI("/api/remise-bis/" + remiseBI.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, remiseBI.getId().toString()))
            .body(remiseBI);
    }

    /**
     * {@code PUT  /remise-bis/:id} : Updates an existing remiseBI.
     *
     * @param id the id of the remiseBI to save.
     * @param remiseBI the remiseBI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated remiseBI,
     * or with status {@code 400 (Bad Request)} if the remiseBI is not valid,
     * or with status {@code 500 (Internal Server Error)} if the remiseBI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RemiseBI> updateRemiseBI(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody RemiseBI remiseBI
    ) throws URISyntaxException {
        LOG.debug("REST request to update RemiseBI : {}, {}", id, remiseBI);
        if (remiseBI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, remiseBI.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!remiseBIRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        remiseBI = remiseBIRepository.save(remiseBI);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, remiseBI.getId().toString()))
            .body(remiseBI);
    }

    /**
     * {@code PATCH  /remise-bis/:id} : Partial updates given fields of an existing remiseBI, field will ignore if it is null
     *
     * @param id the id of the remiseBI to save.
     * @param remiseBI the remiseBI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated remiseBI,
     * or with status {@code 400 (Bad Request)} if the remiseBI is not valid,
     * or with status {@code 404 (Not Found)} if the remiseBI is not found,
     * or with status {@code 500 (Internal Server Error)} if the remiseBI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RemiseBI> partialUpdateRemiseBI(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody RemiseBI remiseBI
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update RemiseBI partially : {}, {}", id, remiseBI);
        if (remiseBI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, remiseBI.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!remiseBIRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RemiseBI> result = remiseBIRepository
            .findById(remiseBI.getId())
            .map(existingRemiseBI -> {
                if (remiseBI.getNum() != null) {
                    existingRemiseBI.setNum(remiseBI.getNum());
                }
                if (remiseBI.getName() != null) {
                    existingRemiseBI.setName(remiseBI.getName());
                }
                if (remiseBI.getPosPercent() != null) {
                    existingRemiseBI.setPosPercent(remiseBI.getPosPercent());
                }
                if (remiseBI.getRptGrpNum() != null) {
                    existingRemiseBI.setRptGrpNum(remiseBI.getRptGrpNum());
                }
                if (remiseBI.getRptGrpName() != null) {
                    existingRemiseBI.setRptGrpName(remiseBI.getRptGrpName());
                }
                if (remiseBI.getLocRef() != null) {
                    existingRemiseBI.setLocRef(remiseBI.getLocRef());
                }

                return existingRemiseBI;
            })
            .map(remiseBIRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, remiseBI.getId().toString())
        );
    }

    /**
     * {@code GET  /remise-bis} : get all the remiseBIS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of remiseBIS in body.
     */
    @GetMapping("")
    public List<RemiseBI> getAllRemiseBIS() {
        LOG.debug("REST request to get all RemiseBIS");
        return remiseBIRepository.findAll();
    }

    /**
     * {@code GET  /remise-bis/:id} : get the "id" remiseBI.
     *
     * @param id the id of the remiseBI to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the remiseBI, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RemiseBI> getRemiseBI(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get RemiseBI : {}", id);
        Optional<RemiseBI> remiseBI = remiseBIRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(remiseBI);
    }

    /**
     * {@code DELETE  /remise-bis/:id} : delete the "id" remiseBI.
     *
     * @param id the id of the remiseBI to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRemiseBI(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete RemiseBI : {}", id);
        remiseBIRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
