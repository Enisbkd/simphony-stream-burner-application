package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.OrderChannelBIAsserts.*;
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
import mc.sbm.simphonycloud.domain.OrderChannelBI;
import mc.sbm.simphonycloud.repository.OrderChannelBIRepository;
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
 * Integration tests for the {@link OrderChannelBIResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrderChannelBIResourceIT {

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

    private static final String ENTITY_API_URL = "/api/order-channel-bis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OrderChannelBIRepository orderChannelBIRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderChannelBIMockMvc;

    private OrderChannelBI orderChannelBI;

    private OrderChannelBI insertedOrderChannelBI;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderChannelBI createEntity() {
        return new OrderChannelBI()
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
    public static OrderChannelBI createUpdatedEntity() {
        return new OrderChannelBI()
            .num(UPDATED_NUM)
            .locRef(UPDATED_LOC_REF)
            .name(UPDATED_NAME)
            .mstrNum(UPDATED_MSTR_NUM)
            .mstrName(UPDATED_MSTR_NAME);
    }

    @BeforeEach
    void initTest() {
        orderChannelBI = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedOrderChannelBI != null) {
            orderChannelBIRepository.delete(insertedOrderChannelBI);
            insertedOrderChannelBI = null;
        }
    }

    @Test
    @Transactional
    void createOrderChannelBI() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the OrderChannelBI
        var returnedOrderChannelBI = om.readValue(
            restOrderChannelBIMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(orderChannelBI)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OrderChannelBI.class
        );

        // Validate the OrderChannelBI in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOrderChannelBIUpdatableFieldsEquals(returnedOrderChannelBI, getPersistedOrderChannelBI(returnedOrderChannelBI));

        insertedOrderChannelBI = returnedOrderChannelBI;
    }

    @Test
    @Transactional
    void createOrderChannelBIWithExistingId() throws Exception {
        // Create the OrderChannelBI with an existing ID
        orderChannelBI.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderChannelBIMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(orderChannelBI)))
            .andExpect(status().isBadRequest());

        // Validate the OrderChannelBI in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLocRefIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        orderChannelBI.setLocRef(null);

        // Create the OrderChannelBI, which fails.

        restOrderChannelBIMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(orderChannelBI)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrderChannelBIS() throws Exception {
        // Initialize the database
        insertedOrderChannelBI = orderChannelBIRepository.saveAndFlush(orderChannelBI);

        // Get all the orderChannelBIList
        restOrderChannelBIMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderChannelBI.getId().intValue())))
            .andExpect(jsonPath("$.[*].num").value(hasItem(DEFAULT_NUM)))
            .andExpect(jsonPath("$.[*].locRef").value(hasItem(DEFAULT_LOC_REF)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].mstrNum").value(hasItem(DEFAULT_MSTR_NUM)))
            .andExpect(jsonPath("$.[*].mstrName").value(hasItem(DEFAULT_MSTR_NAME)));
    }

    @Test
    @Transactional
    void getOrderChannelBI() throws Exception {
        // Initialize the database
        insertedOrderChannelBI = orderChannelBIRepository.saveAndFlush(orderChannelBI);

        // Get the orderChannelBI
        restOrderChannelBIMockMvc
            .perform(get(ENTITY_API_URL_ID, orderChannelBI.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderChannelBI.getId().intValue()))
            .andExpect(jsonPath("$.num").value(DEFAULT_NUM))
            .andExpect(jsonPath("$.locRef").value(DEFAULT_LOC_REF))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.mstrNum").value(DEFAULT_MSTR_NUM))
            .andExpect(jsonPath("$.mstrName").value(DEFAULT_MSTR_NAME));
    }

    @Test
    @Transactional
    void getNonExistingOrderChannelBI() throws Exception {
        // Get the orderChannelBI
        restOrderChannelBIMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrderChannelBI() throws Exception {
        // Initialize the database
        insertedOrderChannelBI = orderChannelBIRepository.saveAndFlush(orderChannelBI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the orderChannelBI
        OrderChannelBI updatedOrderChannelBI = orderChannelBIRepository.findById(orderChannelBI.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOrderChannelBI are not directly saved in db
        em.detach(updatedOrderChannelBI);
        updatedOrderChannelBI
            .num(UPDATED_NUM)
            .locRef(UPDATED_LOC_REF)
            .name(UPDATED_NAME)
            .mstrNum(UPDATED_MSTR_NUM)
            .mstrName(UPDATED_MSTR_NAME);

        restOrderChannelBIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrderChannelBI.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOrderChannelBI))
            )
            .andExpect(status().isOk());

        // Validate the OrderChannelBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOrderChannelBIToMatchAllProperties(updatedOrderChannelBI);
    }

    @Test
    @Transactional
    void putNonExistingOrderChannelBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderChannelBI.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderChannelBIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderChannelBI.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(orderChannelBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderChannelBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrderChannelBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderChannelBI.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderChannelBIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(orderChannelBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderChannelBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrderChannelBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderChannelBI.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderChannelBIMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(orderChannelBI)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderChannelBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrderChannelBIWithPatch() throws Exception {
        // Initialize the database
        insertedOrderChannelBI = orderChannelBIRepository.saveAndFlush(orderChannelBI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the orderChannelBI using partial update
        OrderChannelBI partialUpdatedOrderChannelBI = new OrderChannelBI();
        partialUpdatedOrderChannelBI.setId(orderChannelBI.getId());

        partialUpdatedOrderChannelBI.locRef(UPDATED_LOC_REF).name(UPDATED_NAME);

        restOrderChannelBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderChannelBI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrderChannelBI))
            )
            .andExpect(status().isOk());

        // Validate the OrderChannelBI in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrderChannelBIUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOrderChannelBI, orderChannelBI),
            getPersistedOrderChannelBI(orderChannelBI)
        );
    }

    @Test
    @Transactional
    void fullUpdateOrderChannelBIWithPatch() throws Exception {
        // Initialize the database
        insertedOrderChannelBI = orderChannelBIRepository.saveAndFlush(orderChannelBI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the orderChannelBI using partial update
        OrderChannelBI partialUpdatedOrderChannelBI = new OrderChannelBI();
        partialUpdatedOrderChannelBI.setId(orderChannelBI.getId());

        partialUpdatedOrderChannelBI
            .num(UPDATED_NUM)
            .locRef(UPDATED_LOC_REF)
            .name(UPDATED_NAME)
            .mstrNum(UPDATED_MSTR_NUM)
            .mstrName(UPDATED_MSTR_NAME);

        restOrderChannelBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderChannelBI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrderChannelBI))
            )
            .andExpect(status().isOk());

        // Validate the OrderChannelBI in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrderChannelBIUpdatableFieldsEquals(partialUpdatedOrderChannelBI, getPersistedOrderChannelBI(partialUpdatedOrderChannelBI));
    }

    @Test
    @Transactional
    void patchNonExistingOrderChannelBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderChannelBI.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderChannelBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orderChannelBI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(orderChannelBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderChannelBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrderChannelBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderChannelBI.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderChannelBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(orderChannelBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderChannelBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrderChannelBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderChannelBI.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderChannelBIMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(orderChannelBI)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderChannelBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrderChannelBI() throws Exception {
        // Initialize the database
        insertedOrderChannelBI = orderChannelBIRepository.saveAndFlush(orderChannelBI);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the orderChannelBI
        restOrderChannelBIMockMvc
            .perform(delete(ENTITY_API_URL_ID, orderChannelBI.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return orderChannelBIRepository.count();
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

    protected OrderChannelBI getPersistedOrderChannelBI(OrderChannelBI orderChannelBI) {
        return orderChannelBIRepository.findById(orderChannelBI.getId()).orElseThrow();
    }

    protected void assertPersistedOrderChannelBIToMatchAllProperties(OrderChannelBI expectedOrderChannelBI) {
        assertOrderChannelBIAllPropertiesEquals(expectedOrderChannelBI, getPersistedOrderChannelBI(expectedOrderChannelBI));
    }

    protected void assertPersistedOrderChannelBIToMatchUpdatableProperties(OrderChannelBI expectedOrderChannelBI) {
        assertOrderChannelBIAllUpdatablePropertiesEquals(expectedOrderChannelBI, getPersistedOrderChannelBI(expectedOrderChannelBI));
    }
}
