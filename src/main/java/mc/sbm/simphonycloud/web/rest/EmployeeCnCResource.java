package mc.sbm.simphonycloud.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.EmployeeCnC;
import mc.sbm.simphonycloud.repository.EmployeeCnCRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.EmployeeCnC}.
 */
@RestController
@RequestMapping("/api/employee-cn-cs")
@Transactional
public class EmployeeCnCResource {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeCnCResource.class);

    private static final String ENTITY_NAME = "employeeCnC";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeCnCRepository employeeCnCRepository;

    public EmployeeCnCResource(EmployeeCnCRepository employeeCnCRepository) {
        this.employeeCnCRepository = employeeCnCRepository;
    }

    /**
     * {@code POST  /employee-cn-cs} : Create a new employeeCnC.
     *
     * @param employeeCnC the employeeCnC to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeCnC, or with status {@code 400 (Bad Request)} if the employeeCnC has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EmployeeCnC> createEmployeeCnC(@RequestBody EmployeeCnC employeeCnC) throws URISyntaxException {
        LOG.debug("REST request to save EmployeeCnC : {}", employeeCnC);
        if (employeeCnC.getId() != null) {
            throw new BadRequestAlertException("A new employeeCnC cannot already have an ID", ENTITY_NAME, "idexists");
        }
        employeeCnC = employeeCnCRepository.save(employeeCnC);
        return ResponseEntity.created(new URI("/api/employee-cn-cs/" + employeeCnC.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, employeeCnC.getId().toString()))
            .body(employeeCnC);
    }

    /**
     * {@code PUT  /employee-cn-cs/:id} : Updates an existing employeeCnC.
     *
     * @param id the id of the employeeCnC to save.
     * @param employeeCnC the employeeCnC to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeCnC,
     * or with status {@code 400 (Bad Request)} if the employeeCnC is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeCnC couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeCnC> updateEmployeeCnC(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody EmployeeCnC employeeCnC
    ) throws URISyntaxException {
        LOG.debug("REST request to update EmployeeCnC : {}, {}", id, employeeCnC);
        if (employeeCnC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeCnC.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeCnCRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        employeeCnC = employeeCnCRepository.save(employeeCnC);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeCnC.getId().toString()))
            .body(employeeCnC);
    }

    /**
     * {@code PATCH  /employee-cn-cs/:id} : Partial updates given fields of an existing employeeCnC, field will ignore if it is null
     *
     * @param id the id of the employeeCnC to save.
     * @param employeeCnC the employeeCnC to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeCnC,
     * or with status {@code 400 (Bad Request)} if the employeeCnC is not valid,
     * or with status {@code 404 (Not Found)} if the employeeCnC is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeCnC couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeCnC> partialUpdateEmployeeCnC(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody EmployeeCnC employeeCnC
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update EmployeeCnC partially : {}, {}", id, employeeCnC);
        if (employeeCnC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeCnC.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeCnCRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeCnC> result = employeeCnCRepository
            .findById(employeeCnC.getId())
            .map(existingEmployeeCnC -> {
                if (employeeCnC.getObjectNum() != null) {
                    existingEmployeeCnC.setObjectNum(employeeCnC.getObjectNum());
                }
                if (employeeCnC.getFirstName() != null) {
                    existingEmployeeCnC.setFirstName(employeeCnC.getFirstName());
                }
                if (employeeCnC.getLastName() != null) {
                    existingEmployeeCnC.setLastName(employeeCnC.getLastName());
                }
                if (employeeCnC.getCheckName() != null) {
                    existingEmployeeCnC.setCheckName(employeeCnC.getCheckName());
                }
                if (employeeCnC.getEmail() != null) {
                    existingEmployeeCnC.setEmail(employeeCnC.getEmail());
                }
                if (employeeCnC.getLanguageObjNum() != null) {
                    existingEmployeeCnC.setLanguageObjNum(employeeCnC.getLanguageObjNum());
                }
                if (employeeCnC.getLangId() != null) {
                    existingEmployeeCnC.setLangId(employeeCnC.getLangId());
                }
                if (employeeCnC.getAlternateId() != null) {
                    existingEmployeeCnC.setAlternateId(employeeCnC.getAlternateId());
                }
                if (employeeCnC.getLevel() != null) {
                    existingEmployeeCnC.setLevel(employeeCnC.getLevel());
                }
                if (employeeCnC.getGroup() != null) {
                    existingEmployeeCnC.setGroup(employeeCnC.getGroup());
                }
                if (employeeCnC.getUserName() != null) {
                    existingEmployeeCnC.setUserName(employeeCnC.getUserName());
                }
                if (employeeCnC.getFirstRoleHierUnitId() != null) {
                    existingEmployeeCnC.setFirstRoleHierUnitId(employeeCnC.getFirstRoleHierUnitId());
                }
                if (employeeCnC.getFirstRoleObjNum() != null) {
                    existingEmployeeCnC.setFirstRoleObjNum(employeeCnC.getFirstRoleObjNum());
                }
                if (employeeCnC.getFirstVisibilityHierUnitId() != null) {
                    existingEmployeeCnC.setFirstVisibilityHierUnitId(employeeCnC.getFirstVisibilityHierUnitId());
                }
                if (employeeCnC.getFirstVisibilityPropagateToChildren() != null) {
                    existingEmployeeCnC.setFirstVisibilityPropagateToChildren(employeeCnC.getFirstVisibilityPropagateToChildren());
                }
                if (employeeCnC.getFirstPropertyHierUnitId() != null) {
                    existingEmployeeCnC.setFirstPropertyHierUnitId(employeeCnC.getFirstPropertyHierUnitId());
                }
                if (employeeCnC.getFirstPropertyObjNum() != null) {
                    existingEmployeeCnC.setFirstPropertyObjNum(employeeCnC.getFirstPropertyObjNum());
                }
                if (employeeCnC.getFirstPropertyEmpClassObjNum() != null) {
                    existingEmployeeCnC.setFirstPropertyEmpClassObjNum(employeeCnC.getFirstPropertyEmpClassObjNum());
                }
                if (employeeCnC.getFirstPropertyOptions() != null) {
                    existingEmployeeCnC.setFirstPropertyOptions(employeeCnC.getFirstPropertyOptions());
                }
                if (employeeCnC.getFirstOperatorRvcHierUnitId() != null) {
                    existingEmployeeCnC.setFirstOperatorRvcHierUnitId(employeeCnC.getFirstOperatorRvcHierUnitId());
                }
                if (employeeCnC.getFirstOperatorRvcObjNum() != null) {
                    existingEmployeeCnC.setFirstOperatorRvcObjNum(employeeCnC.getFirstOperatorRvcObjNum());
                }
                if (employeeCnC.getFirstOperatorOptions() != null) {
                    existingEmployeeCnC.setFirstOperatorOptions(employeeCnC.getFirstOperatorOptions());
                }
                if (employeeCnC.getFirstOperatorCashDrawerObjNum() != null) {
                    existingEmployeeCnC.setFirstOperatorCashDrawerObjNum(employeeCnC.getFirstOperatorCashDrawerObjNum());
                }
                if (employeeCnC.getFirstOperatorTmsColorObjNum() != null) {
                    existingEmployeeCnC.setFirstOperatorTmsColorObjNum(employeeCnC.getFirstOperatorTmsColorObjNum());
                }
                if (employeeCnC.getFirstOperatorServerEfficiency() != null) {
                    existingEmployeeCnC.setFirstOperatorServerEfficiency(employeeCnC.getFirstOperatorServerEfficiency());
                }
                if (employeeCnC.getPin() != null) {
                    existingEmployeeCnC.setPin(employeeCnC.getPin());
                }
                if (employeeCnC.getAccessId() != null) {
                    existingEmployeeCnC.setAccessId(employeeCnC.getAccessId());
                }

                return existingEmployeeCnC;
            })
            .map(employeeCnCRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeCnC.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-cn-cs} : get all the employeeCnCS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeCnCS in body.
     */
    @GetMapping("")
    public List<EmployeeCnC> getAllEmployeeCnCS() {
        LOG.debug("REST request to get all EmployeeCnCS");
        return employeeCnCRepository.findAll();
    }

    /**
     * {@code GET  /employee-cn-cs/:id} : get the "id" employeeCnC.
     *
     * @param id the id of the employeeCnC to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeCnC, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeCnC> getEmployeeCnC(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get EmployeeCnC : {}", id);
        Optional<EmployeeCnC> employeeCnC = employeeCnCRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(employeeCnC);
    }

    /**
     * {@code DELETE  /employee-cn-cs/:id} : delete the "id" employeeCnC.
     *
     * @param id the id of the employeeCnC to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeCnC(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete EmployeeCnC : {}", id);
        employeeCnCRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
