package mc.sbm.simphonycloud.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.OrderChannel;
import mc.sbm.simphonycloud.repository.OrderChannelRepository;
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
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.OrderChannel}.
 */
@RestController
@RequestMapping("/api/order-channels")
@Transactional
public class OrderChannelResource {

    private static final Logger LOG = LoggerFactory.getLogger(OrderChannelResource.class);

    private static final String ENTITY_NAME = "orderChannel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderChannelRepository orderChannelRepository;

    public OrderChannelResource(OrderChannelRepository orderChannelRepository) {
        this.orderChannelRepository = orderChannelRepository;
    }

    /**
     * {@code POST  /order-channels} : Create a new orderChannel.
     *
     * @param orderChannel the orderChannel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderChannel, or with status {@code 400 (Bad Request)} if the orderChannel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OrderChannel> createOrderChannel(@Valid @RequestBody OrderChannel orderChannel) throws URISyntaxException {
        LOG.debug("REST request to save OrderChannel : {}", orderChannel);
        if (orderChannel.getId() != null) {
            throw new BadRequestAlertException("A new orderChannel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        orderChannel = orderChannelRepository.save(orderChannel);
        return ResponseEntity.created(new URI("/api/order-channels/" + orderChannel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, orderChannel.getId().toString()))
            .body(orderChannel);
    }

    /**
     * {@code PUT  /order-channels/:id} : Updates an existing orderChannel.
     *
     * @param id the id of the orderChannel to save.
     * @param orderChannel the orderChannel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderChannel,
     * or with status {@code 400 (Bad Request)} if the orderChannel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderChannel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrderChannel> updateOrderChannel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OrderChannel orderChannel
    ) throws URISyntaxException {
        LOG.debug("REST request to update OrderChannel : {}, {}", id, orderChannel);
        if (orderChannel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderChannel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderChannelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        orderChannel = orderChannelRepository.save(orderChannel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderChannel.getId().toString()))
            .body(orderChannel);
    }

    /**
     * {@code PATCH  /order-channels/:id} : Partial updates given fields of an existing orderChannel, field will ignore if it is null
     *
     * @param id the id of the orderChannel to save.
     * @param orderChannel the orderChannel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderChannel,
     * or with status {@code 400 (Bad Request)} if the orderChannel is not valid,
     * or with status {@code 404 (Not Found)} if the orderChannel is not found,
     * or with status {@code 500 (Internal Server Error)} if the orderChannel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrderChannel> partialUpdateOrderChannel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OrderChannel orderChannel
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update OrderChannel partially : {}, {}", id, orderChannel);
        if (orderChannel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderChannel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderChannelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrderChannel> result = orderChannelRepository
            .findById(orderChannel.getId())
            .map(existingOrderChannel -> {
                if (orderChannel.getNum() != null) {
                    existingOrderChannel.setNum(orderChannel.getNum());
                }
                if (orderChannel.getLocRef() != null) {
                    existingOrderChannel.setLocRef(orderChannel.getLocRef());
                }
                if (orderChannel.getName() != null) {
                    existingOrderChannel.setName(orderChannel.getName());
                }
                if (orderChannel.getMstrNum() != null) {
                    existingOrderChannel.setMstrNum(orderChannel.getMstrNum());
                }
                if (orderChannel.getMstrName() != null) {
                    existingOrderChannel.setMstrName(orderChannel.getMstrName());
                }

                return existingOrderChannel;
            })
            .map(orderChannelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderChannel.getId().toString())
        );
    }

    /**
     * {@code GET  /order-channels} : get all the orderChannels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderChannels in body.
     */
    @GetMapping("")
    public List<OrderChannel> getAllOrderChannels() {
        LOG.debug("REST request to get all OrderChannels");
        return orderChannelRepository.findAll();
    }

    /**
     * {@code GET  /order-channels/:id} : get the "id" orderChannel.
     *
     * @param id the id of the orderChannel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderChannel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderChannel> getOrderChannel(@PathVariable("id") Long id) {
        LOG.debug("REST request to get OrderChannel : {}", id);
        Optional<OrderChannel> orderChannel = orderChannelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(orderChannel);
    }

    /**
     * {@code DELETE  /order-channels/:id} : delete the "id" orderChannel.
     *
     * @param id the id of the orderChannel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderChannel(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete OrderChannel : {}", id);
        orderChannelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
