package mc.sbm.simphonycloud.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.OrderTypeBI;
import mc.sbm.simphonycloud.repository.OrderTypeBIRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.OrderTypeBI}.
 */
@RestController
@RequestMapping("/api/order-type-bis")
@Transactional
public class OrderTypeBIResource {

    private static final Logger LOG = LoggerFactory.getLogger(OrderTypeBIResource.class);

    private static final String ENTITY_NAME = "orderTypeBI";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderTypeBIRepository orderTypeBIRepository;

    public OrderTypeBIResource(OrderTypeBIRepository orderTypeBIRepository) {
        this.orderTypeBIRepository = orderTypeBIRepository;
    }

    /**
     * {@code POST  /order-type-bis} : Create a new orderTypeBI.
     *
     * @param orderTypeBI the orderTypeBI to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderTypeBI, or with status {@code 400 (Bad Request)} if the orderTypeBI has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OrderTypeBI> createOrderTypeBI(@Valid @RequestBody OrderTypeBI orderTypeBI) throws URISyntaxException {
        LOG.debug("REST request to save OrderTypeBI : {}", orderTypeBI);
        if (orderTypeBI.getId() != null) {
            throw new BadRequestAlertException("A new orderTypeBI cannot already have an ID", ENTITY_NAME, "idexists");
        }
        orderTypeBI = orderTypeBIRepository.save(orderTypeBI);
        return ResponseEntity.created(new URI("/api/order-type-bis/" + orderTypeBI.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, orderTypeBI.getId().toString()))
            .body(orderTypeBI);
    }

    /**
     * {@code PUT  /order-type-bis/:id} : Updates an existing orderTypeBI.
     *
     * @param id the id of the orderTypeBI to save.
     * @param orderTypeBI the orderTypeBI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderTypeBI,
     * or with status {@code 400 (Bad Request)} if the orderTypeBI is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderTypeBI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrderTypeBI> updateOrderTypeBI(
        @PathVariable(value = "id", required = false) final Integer id,
        @Valid @RequestBody OrderTypeBI orderTypeBI
    ) throws URISyntaxException {
        LOG.debug("REST request to update OrderTypeBI : {}, {}", id, orderTypeBI);
        if (orderTypeBI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderTypeBI.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderTypeBIRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        orderTypeBI = orderTypeBIRepository.save(orderTypeBI);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderTypeBI.getId().toString()))
            .body(orderTypeBI);
    }

    /**
     * {@code PATCH  /order-type-bis/:id} : Partial updates given fields of an existing orderTypeBI, field will ignore if it is null
     *
     * @param id the id of the orderTypeBI to save.
     * @param orderTypeBI the orderTypeBI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderTypeBI,
     * or with status {@code 400 (Bad Request)} if the orderTypeBI is not valid,
     * or with status {@code 404 (Not Found)} if the orderTypeBI is not found,
     * or with status {@code 500 (Internal Server Error)} if the orderTypeBI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrderTypeBI> partialUpdateOrderTypeBI(
        @PathVariable(value = "id", required = false) final Integer id,
        @NotNull @RequestBody OrderTypeBI orderTypeBI
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update OrderTypeBI partially : {}, {}", id, orderTypeBI);
        if (orderTypeBI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderTypeBI.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderTypeBIRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrderTypeBI> result = orderTypeBIRepository
            .findById(orderTypeBI.getId())
            .map(existingOrderTypeBI -> {
                if (orderTypeBI.getNum() != null) {
                    existingOrderTypeBI.setNum(orderTypeBI.getNum());
                }
                if (orderTypeBI.getLocRef() != null) {
                    existingOrderTypeBI.setLocRef(orderTypeBI.getLocRef());
                }
                if (orderTypeBI.getName() != null) {
                    existingOrderTypeBI.setName(orderTypeBI.getName());
                }
                if (orderTypeBI.getMstrNum() != null) {
                    existingOrderTypeBI.setMstrNum(orderTypeBI.getMstrNum());
                }
                if (orderTypeBI.getMstrName() != null) {
                    existingOrderTypeBI.setMstrName(orderTypeBI.getMstrName());
                }
                if (orderTypeBI.getCatGrpHierName1() != null) {
                    existingOrderTypeBI.setCatGrpHierName1(orderTypeBI.getCatGrpHierName1());
                }
                if (orderTypeBI.getCatGrpName1() != null) {
                    existingOrderTypeBI.setCatGrpName1(orderTypeBI.getCatGrpName1());
                }
                if (orderTypeBI.getCatGrpHierName2() != null) {
                    existingOrderTypeBI.setCatGrpHierName2(orderTypeBI.getCatGrpHierName2());
                }
                if (orderTypeBI.getCatGrpName2() != null) {
                    existingOrderTypeBI.setCatGrpName2(orderTypeBI.getCatGrpName2());
                }
                if (orderTypeBI.getCatGrpHierName3() != null) {
                    existingOrderTypeBI.setCatGrpHierName3(orderTypeBI.getCatGrpHierName3());
                }
                if (orderTypeBI.getCatGrpName3() != null) {
                    existingOrderTypeBI.setCatGrpName3(orderTypeBI.getCatGrpName3());
                }
                if (orderTypeBI.getCatGrpHierName4() != null) {
                    existingOrderTypeBI.setCatGrpHierName4(orderTypeBI.getCatGrpHierName4());
                }
                if (orderTypeBI.getCatGrpName4() != null) {
                    existingOrderTypeBI.setCatGrpName4(orderTypeBI.getCatGrpName4());
                }

                return existingOrderTypeBI;
            })
            .map(orderTypeBIRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderTypeBI.getId().toString())
        );
    }

    /**
     * {@code GET  /order-type-bis} : get all the orderTypeBIS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderTypeBIS in body.
     */
    @GetMapping("")
    public List<OrderTypeBI> getAllOrderTypeBIS() {
        LOG.debug("REST request to get all OrderTypeBIS");
        return orderTypeBIRepository.findAll();
    }

    /**
     * {@code GET  /order-type-bis/:id} : get the "id" orderTypeBI.
     *
     * @param id the id of the orderTypeBI to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderTypeBI, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderTypeBI> getOrderTypeBI(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get OrderTypeBI : {}", id);
        Optional<OrderTypeBI> orderTypeBI = orderTypeBIRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(orderTypeBI);
    }

    /**
     * {@code DELETE  /order-type-bis/:id} : delete the "id" orderTypeBI.
     *
     * @param id the id of the orderTypeBI to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderTypeBI(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete OrderTypeBI : {}", id);
        orderTypeBIRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
