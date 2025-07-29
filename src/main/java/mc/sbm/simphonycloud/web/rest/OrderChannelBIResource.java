package mc.sbm.simphonycloud.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.OrderChannelBI;
import mc.sbm.simphonycloud.repository.OrderChannelBIRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.OrderChannelBI}.
 */
@RestController
@RequestMapping("/api/order-channel-bis")
@Transactional
public class OrderChannelBIResource {

    private static final Logger LOG = LoggerFactory.getLogger(OrderChannelBIResource.class);

    private static final String ENTITY_NAME = "orderChannelBI";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderChannelBIRepository orderChannelBIRepository;

    public OrderChannelBIResource(OrderChannelBIRepository orderChannelBIRepository) {
        this.orderChannelBIRepository = orderChannelBIRepository;
    }

    /**
     * {@code POST  /order-channel-bis} : Create a new orderChannelBI.
     *
     * @param orderChannelBI the orderChannelBI to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderChannelBI, or with status {@code 400 (Bad Request)} if the orderChannelBI has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OrderChannelBI> createOrderChannelBI(@Valid @RequestBody OrderChannelBI orderChannelBI)
        throws URISyntaxException {
        LOG.debug("REST request to save OrderChannelBI : {}", orderChannelBI);
        if (orderChannelBI.getId() != null) {
            throw new BadRequestAlertException("A new orderChannelBI cannot already have an ID", ENTITY_NAME, "idexists");
        }
        orderChannelBI = orderChannelBIRepository.save(orderChannelBI);
        return ResponseEntity.created(new URI("/api/order-channel-bis/" + orderChannelBI.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, orderChannelBI.getId().toString()))
            .body(orderChannelBI);
    }

    /**
     * {@code PUT  /order-channel-bis/:id} : Updates an existing orderChannelBI.
     *
     * @param id the id of the orderChannelBI to save.
     * @param orderChannelBI the orderChannelBI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderChannelBI,
     * or with status {@code 400 (Bad Request)} if the orderChannelBI is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderChannelBI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrderChannelBI> updateOrderChannelBI(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OrderChannelBI orderChannelBI
    ) throws URISyntaxException {
        LOG.debug("REST request to update OrderChannelBI : {}, {}", id, orderChannelBI);
        if (orderChannelBI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderChannelBI.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderChannelBIRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        orderChannelBI = orderChannelBIRepository.save(orderChannelBI);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderChannelBI.getId().toString()))
            .body(orderChannelBI);
    }

    /**
     * {@code PATCH  /order-channel-bis/:id} : Partial updates given fields of an existing orderChannelBI, field will ignore if it is null
     *
     * @param id the id of the orderChannelBI to save.
     * @param orderChannelBI the orderChannelBI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderChannelBI,
     * or with status {@code 400 (Bad Request)} if the orderChannelBI is not valid,
     * or with status {@code 404 (Not Found)} if the orderChannelBI is not found,
     * or with status {@code 500 (Internal Server Error)} if the orderChannelBI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrderChannelBI> partialUpdateOrderChannelBI(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OrderChannelBI orderChannelBI
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update OrderChannelBI partially : {}, {}", id, orderChannelBI);
        if (orderChannelBI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderChannelBI.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderChannelBIRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrderChannelBI> result = orderChannelBIRepository
            .findById(orderChannelBI.getId())
            .map(existingOrderChannelBI -> {
                if (orderChannelBI.getNum() != null) {
                    existingOrderChannelBI.setNum(orderChannelBI.getNum());
                }
                if (orderChannelBI.getLocRef() != null) {
                    existingOrderChannelBI.setLocRef(orderChannelBI.getLocRef());
                }
                if (orderChannelBI.getName() != null) {
                    existingOrderChannelBI.setName(orderChannelBI.getName());
                }
                if (orderChannelBI.getMstrNum() != null) {
                    existingOrderChannelBI.setMstrNum(orderChannelBI.getMstrNum());
                }
                if (orderChannelBI.getMstrName() != null) {
                    existingOrderChannelBI.setMstrName(orderChannelBI.getMstrName());
                }

                return existingOrderChannelBI;
            })
            .map(orderChannelBIRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderChannelBI.getId().toString())
        );
    }

    /**
     * {@code GET  /order-channel-bis} : get all the orderChannelBIS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderChannelBIS in body.
     */
    @GetMapping("")
    public List<OrderChannelBI> getAllOrderChannelBIS() {
        LOG.debug("REST request to get all OrderChannelBIS");
        return orderChannelBIRepository.findAll();
    }

    /**
     * {@code GET  /order-channel-bis/:id} : get the "id" orderChannelBI.
     *
     * @param id the id of the orderChannelBI to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderChannelBI, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderChannelBI> getOrderChannelBI(@PathVariable("id") Long id) {
        LOG.debug("REST request to get OrderChannelBI : {}", id);
        Optional<OrderChannelBI> orderChannelBI = orderChannelBIRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(orderChannelBI);
    }

    /**
     * {@code DELETE  /order-channel-bis/:id} : delete the "id" orderChannelBI.
     *
     * @param id the id of the orderChannelBI to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderChannelBI(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete OrderChannelBI : {}", id);
        orderChannelBIRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
