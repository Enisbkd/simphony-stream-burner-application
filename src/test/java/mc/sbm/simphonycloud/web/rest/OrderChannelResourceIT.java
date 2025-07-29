package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.OrderChannelAsserts.*;
import static mc.sbm.simphonycloud.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import mc.sbm.simphonycloud.IntegrationTest;
import mc.sbm.simphonycloud.domain.OrderChannel;
import mc.sbm.simphonycloud.repository.OrderChannelRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OrderChannelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrderChannelResourceIT {

    private static final Integer DEFAULT_NUM = 1;
    private static final Integer UPDATED_NUM = 2;

    private static final String DEFAULT_LOC_REF = "AAAAAAAAAA";
    private static final String UPDATED_LOC_REF = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_MSTR_NUM = 1;
    private static final Integer UPDATED_MSTR_NUM = 2;

    private static final String DEFAULT_MSTR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MSTR_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/order-channels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OrderChannelRepository orderChannelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderChannelMockMvc;

    private OrderChannel orderChannel;

    private OrderChannel insertedOrderChannel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderChannel createEntity() {
        return new OrderChannel()
            .num(DEFAULT_NUM)
            .locRef(DEFAULT_LOC_REF)
            .name(DEFAULT_NAME)
            .mstrNum(DEFAULT_MSTR_NUM)
            .mstrName(DEFAULT_MSTR_NAME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderChannel createUpdatedEntity() {
        return new OrderChannel()
            .num(UPDATED_NUM)
            .locRef(UPDATED_LOC_REF)
            .name(UPDATED_NAME)
            .mstrNum(UPDATED_MSTR_NUM)
            .mstrName(UPDATED_MSTR_NAME);
    }

    @BeforeEach
    void initTest() {
        orderChannel = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedOrderChannel != null) {
            orderChannelRepository.delete(insertedOrderChannel);
            insertedOrderChannel = null;
        }
    }

    @Test
    @Transactional
    void createOrderChannel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the OrderChannel
        var returnedOrderChannel = om.readValue(
            restOrderChannelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(orderChannel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OrderChannel.class
        );

        // Validate the OrderChannel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOrderChannelUpdatableFieldsEquals(returnedOrderChannel, getPersistedOrderChannel(returnedOrderChannel));

        insertedOrderChannel = returnedOrderChannel;
    }

    @Test
    @Transactional
    void createOrderChannelWithExistingId() throws Exception {
        // Create the OrderChannel with an existing ID
        orderChannel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderChannelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(orderChannel)))
            .andExpect(status().isBadRequest());

        // Validate the OrderChannel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLocRefIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        orderChannel.setLocRef(null);

        // Create the OrderChannel, which fails.

        restOrderChannelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(orderChannel)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrderChannels() throws Exception {
        // Initialize the database
        insertedOrderChannel = orderChannelRepository.saveAndFlush(orderChannel);

        // Get all the orderChannelList
        restOrderChannelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderChannel.getId().intValue())))
            .andExpect(jsonPath("$.[*].num").value(hasItem(DEFAULT_NUM)))
            .andExpect(jsonPath("$.[*].locRef").value(hasItem(DEFAULT_LOC_REF)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].mstrNum").value(hasItem(DEFAULT_MSTR_NUM)))
            .andExpect(jsonPath("$.[*].mstrName").value(hasItem(DEFAULT_MSTR_NAME)));
    }

    @Test
    @Transactional
    void getOrderChannel() throws Exception {
        // Initialize the database
        insertedOrderChannel = orderChannelRepository.saveAndFlush(orderChannel);

        // Get the orderChannel
        restOrderChannelMockMvc
            .perform(get(ENTITY_API_URL_ID, orderChannel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderChannel.getId().intValue()))
            .andExpect(jsonPath("$.num").value(DEFAULT_NUM))
            .andExpect(jsonPath("$.locRef").value(DEFAULT_LOC_REF))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.mstrNum").value(DEFAULT_MSTR_NUM))
            .andExpect(jsonPath("$.mstrName").value(DEFAULT_MSTR_NAME));
    }

    @Test
    @Transactional
    void getNonExistingOrderChannel() throws Exception {
        // Get the orderChannel
        restOrderChannelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrderChannel() throws Exception {
        // Initialize the database
        insertedOrderChannel = orderChannelRepository.saveAndFlush(orderChannel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the orderChannel
        OrderChannel updatedOrderChannel = orderChannelRepository.findById(orderChannel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOrderChannel are not directly saved in db
        em.detach(updatedOrderChannel);
        updatedOrderChannel
            .num(UPDATED_NUM)
            .locRef(UPDATED_LOC_REF)
            .name(UPDATED_NAME)
            .mstrNum(UPDATED_MSTR_NUM)
            .mstrName(UPDATED_MSTR_NAME);

        restOrderChannelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrderChannel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOrderChannel))
            )
            .andExpect(status().isOk());

        // Validate the OrderChannel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOrderChannelToMatchAllProperties(updatedOrderChannel);
    }

    @Test
    @Transactional
    void putNonExistingOrderChannel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderChannel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderChannelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderChannel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(orderChannel))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderChannel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrderChannel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderChannel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderChannelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(orderChannel))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderChannel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrderChannel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderChannel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderChannelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(orderChannel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderChannel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrderChannelWithPatch() throws Exception {
        // Initialize the database
        insertedOrderChannel = orderChannelRepository.saveAndFlush(orderChannel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the orderChannel using partial update
        OrderChannel partialUpdatedOrderChannel = new OrderChannel();
        partialUpdatedOrderChannel.setId(orderChannel.getId());

        partialUpdatedOrderChannel.num(UPDATED_NUM).locRef(UPDATED_LOC_REF).mstrNum(UPDATED_MSTR_NUM);

        restOrderChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderChannel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrderChannel))
            )
            .andExpect(status().isOk());

        // Validate the OrderChannel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrderChannelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOrderChannel, orderChannel),
            getPersistedOrderChannel(orderChannel)
        );
    }

    @Test
    @Transactional
    void fullUpdateOrderChannelWithPatch() throws Exception {
        // Initialize the database
        insertedOrderChannel = orderChannelRepository.saveAndFlush(orderChannel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the orderChannel using partial update
        OrderChannel partialUpdatedOrderChannel = new OrderChannel();
        partialUpdatedOrderChannel.setId(orderChannel.getId());

        partialUpdatedOrderChannel
            .num(UPDATED_NUM)
            .locRef(UPDATED_LOC_REF)
            .name(UPDATED_NAME)
            .mstrNum(UPDATED_MSTR_NUM)
            .mstrName(UPDATED_MSTR_NAME);

        restOrderChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderChannel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrderChannel))
            )
            .andExpect(status().isOk());

        // Validate the OrderChannel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrderChannelUpdatableFieldsEquals(partialUpdatedOrderChannel, getPersistedOrderChannel(partialUpdatedOrderChannel));
    }

    @Test
    @Transactional
    void patchNonExistingOrderChannel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderChannel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orderChannel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(orderChannel))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderChannel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrderChannel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderChannel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(orderChannel))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderChannel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrderChannel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderChannel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderChannelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(orderChannel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderChannel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrderChannel() throws Exception {
        // Initialize the database
        insertedOrderChannel = orderChannelRepository.saveAndFlush(orderChannel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the orderChannel
        restOrderChannelMockMvc
            .perform(delete(ENTITY_API_URL_ID, orderChannel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return orderChannelRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected OrderChannel getPersistedOrderChannel(OrderChannel orderChannel) {
        return orderChannelRepository.findById(orderChannel.getId()).orElseThrow();
    }

    protected void assertPersistedOrderChannelToMatchAllProperties(OrderChannel expectedOrderChannel) {
        assertOrderChannelAllPropertiesEquals(expectedOrderChannel, getPersistedOrderChannel(expectedOrderChannel));
    }

    protected void assertPersistedOrderChannelToMatchUpdatableProperties(OrderChannel expectedOrderChannel) {
        assertOrderChannelAllUpdatablePropertiesEquals(expectedOrderChannel, getPersistedOrderChannel(expectedOrderChannel));
    }
}
