package mc.sbm.simphonycloud.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.OrderType;
import mc.sbm.simphonycloud.repository.OrderTypeRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.OrderType}.
 */
@RestController
@RequestMapping("/api/order-types")
@Transactional
public class OrderTypeResource {

    private static final Logger LOG = LoggerFactory.getLogger(OrderTypeResource.class);

    private static final String ENTITY_NAME = "orderType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderTypeRepository orderTypeRepository;

    public OrderTypeResource(OrderTypeRepository orderTypeRepository) {
        this.orderTypeRepository = orderTypeRepository;
    }

    /**
     * {@code POST  /order-types} : Create a new orderType.
     *
     * @param orderType the orderType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderType, or with status {@code 400 (Bad Request)} if the orderType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OrderType> createOrderType(@Valid @RequestBody OrderType orderType) throws URISyntaxException {
        LOG.debug("REST request to save OrderType : {}", orderType);
        if (orderType.getId() != null) {
            throw new BadRequestAlertException("A new orderType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        orderType = orderTypeRepository.save(orderType);
        return ResponseEntity.created(new URI("/api/order-types/" + orderType.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, orderType.getId().toString()))
            .body(orderType);
    }

    /**
     * {@code PUT  /order-types/:id} : Updates an existing orderType.
     *
     * @param id the id of the orderType to save.
     * @param orderType the orderType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderType,
     * or with status {@code 400 (Bad Request)} if the orderType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrderType> updateOrderType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OrderType orderType
    ) throws URISyntaxException {
        LOG.debug("REST request to update OrderType : {}, {}", id, orderType);
        if (orderType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        orderType = orderTypeRepository.save(orderType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderType.getId().toString()))
            .body(orderType);
    }

    /**
     * {@code PATCH  /order-types/:id} : Partial updates given fields of an existing orderType, field will ignore if it is null
     *
     * @param id the id of the orderType to save.
     * @param orderType the orderType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderType,
     * or with status {@code 400 (Bad Request)} if the orderType is not valid,
     * or with status {@code 404 (Not Found)} if the orderType is not found,
     * or with status {@code 500 (Internal Server Error)} if the orderType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrderType> partialUpdateOrderType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OrderType orderType
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update OrderType partially : {}, {}", id, orderType);
        if (orderType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrderType> result = orderTypeRepository
            .findById(orderType.getId())
            .map(existingOrderType -> {
                if (orderType.getNum() != null) {
                    existingOrderType.setNum(orderType.getNum());
                }
                if (orderType.getLocRef() != null) {
                    existingOrderType.setLocRef(orderType.getLocRef());
                }
                if (orderType.getName() != null) {
                    existingOrderType.setName(orderType.getName());
                }
                if (orderType.getMstrNum() != null) {
                    existingOrderType.setMstrNum(orderType.getMstrNum());
                }
                if (orderType.getMstrName() != null) {
                    existingOrderType.setMstrName(orderType.getMstrName());
                }
                if (orderType.getCatGrpHierName1() != null) {
                    existingOrderType.setCatGrpHierName1(orderType.getCatGrpHierName1());
                }
                if (orderType.getCatGrpName1() != null) {
                    existingOrderType.setCatGrpName1(orderType.getCatGrpName1());
                }
                if (orderType.getCatGrpHierName2() != null) {
                    existingOrderType.setCatGrpHierName2(orderType.getCatGrpHierName2());
                }
                if (orderType.getCatGrpName2() != null) {
                    existingOrderType.setCatGrpName2(orderType.getCatGrpName2());
                }
                if (orderType.getCatGrpHierName3() != null) {
                    existingOrderType.setCatGrpHierName3(orderType.getCatGrpHierName3());
                }
                if (orderType.getCatGrpName3() != null) {
                    existingOrderType.setCatGrpName3(orderType.getCatGrpName3());
                }
                if (orderType.getCatGrpHierName4() != null) {
                    existingOrderType.setCatGrpHierName4(orderType.getCatGrpHierName4());
                }
                if (orderType.getCatGrpName4() != null) {
                    existingOrderType.setCatGrpName4(orderType.getCatGrpName4());
                }

                return existingOrderType;
            })
            .map(orderTypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderType.getId().toString())
        );
    }

    /**
     * {@code GET  /order-types} : get all the orderTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderTypes in body.
     */
    @GetMapping("")
    public List<OrderType> getAllOrderTypes() {
        LOG.debug("REST request to get all OrderTypes");
        return orderTypeRepository.findAll();
    }

    /**
     * {@code GET  /order-types/:id} : get the "id" orderType.
     *
     * @param id the id of the orderType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderType> getOrderType(@PathVariable("id") Long id) {
        LOG.debug("REST request to get OrderType : {}", id);
        Optional<OrderType> orderType = orderTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(orderType);
    }

    /**
     * {@code DELETE  /order-types/:id} : delete the "id" orderType.
     *
     * @param id the id of the orderType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderType(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete OrderType : {}", id);
        orderTypeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
