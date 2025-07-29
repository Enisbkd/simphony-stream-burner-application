package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.OrderTypeAsserts.*;
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
import mc.sbm.simphonycloud.domain.OrderType;
import mc.sbm.simphonycloud.repository.OrderTypeRepository;
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
 * Integration tests for the {@link OrderTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrderTypeResourceIT {

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

    private static final String DEFAULT_CAT_GRP_HIER_NAME_1 = "AAAAAAAAAA";
    private static final String UPDATED_CAT_GRP_HIER_NAME_1 = "BBBBBBBBBB";

    private static final String DEFAULT_CAT_GRP_NAME_1 = "AAAAAAAAAA";
    private static final String UPDATED_CAT_GRP_NAME_1 = "BBBBBBBBBB";

    private static final String DEFAULT_CAT_GRP_HIER_NAME_2 = "AAAAAAAAAA";
    private static final String UPDATED_CAT_GRP_HIER_NAME_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CAT_GRP_NAME_2 = "AAAAAAAAAA";
    private static final String UPDATED_CAT_GRP_NAME_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CAT_GRP_HIER_NAME_3 = "AAAAAAAAAA";
    private static final String UPDATED_CAT_GRP_HIER_NAME_3 = "BBBBBBBBBB";

    private static final String DEFAULT_CAT_GRP_NAME_3 = "AAAAAAAAAA";
    private static final String UPDATED_CAT_GRP_NAME_3 = "BBBBBBBBBB";

    private static final String DEFAULT_CAT_GRP_HIER_NAME_4 = "AAAAAAAAAA";
    private static final String UPDATED_CAT_GRP_HIER_NAME_4 = "BBBBBBBBBB";

    private static final String DEFAULT_CAT_GRP_NAME_4 = "AAAAAAAAAA";
    private static final String UPDATED_CAT_GRP_NAME_4 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/order-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OrderTypeRepository orderTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderTypeMockMvc;

    private OrderType orderType;

    private OrderType insertedOrderType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderType createEntity() {
        return new OrderType()
            .num(DEFAULT_NUM)
            .locRef(DEFAULT_LOC_REF)
            .name(DEFAULT_NAME)
            .mstrNum(DEFAULT_MSTR_NUM)
            .mstrName(DEFAULT_MSTR_NAME)
            .catGrpHierName1(DEFAULT_CAT_GRP_HIER_NAME_1)
            .catGrpName1(DEFAULT_CAT_GRP_NAME_1)
            .catGrpHierName2(DEFAULT_CAT_GRP_HIER_NAME_2)
            .catGrpName2(DEFAULT_CAT_GRP_NAME_2)
            .catGrpHierName3(DEFAULT_CAT_GRP_HIER_NAME_3)
            .catGrpName3(DEFAULT_CAT_GRP_NAME_3)
            .catGrpHierName4(DEFAULT_CAT_GRP_HIER_NAME_4)
            .catGrpName4(DEFAULT_CAT_GRP_NAME_4);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderType createUpdatedEntity() {
        return new OrderType()
            .num(UPDATED_NUM)
            .locRef(UPDATED_LOC_REF)
            .name(UPDATED_NAME)
            .mstrNum(UPDATED_MSTR_NUM)
            .mstrName(UPDATED_MSTR_NAME)
            .catGrpHierName1(UPDATED_CAT_GRP_HIER_NAME_1)
            .catGrpName1(UPDATED_CAT_GRP_NAME_1)
            .catGrpHierName2(UPDATED_CAT_GRP_HIER_NAME_2)
            .catGrpName2(UPDATED_CAT_GRP_NAME_2)
            .catGrpHierName3(UPDATED_CAT_GRP_HIER_NAME_3)
            .catGrpName3(UPDATED_CAT_GRP_NAME_3)
            .catGrpHierName4(UPDATED_CAT_GRP_HIER_NAME_4)
            .catGrpName4(UPDATED_CAT_GRP_NAME_4);
    }

    @BeforeEach
    void initTest() {
        orderType = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedOrderType != null) {
            orderTypeRepository.delete(insertedOrderType);
            insertedOrderType = null;
        }
    }

    @Test
    @Transactional
    void createOrderType() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the OrderType
        var returnedOrderType = om.readValue(
            restOrderTypeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(orderType)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OrderType.class
        );

        // Validate the OrderType in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOrderTypeUpdatableFieldsEquals(returnedOrderType, getPersistedOrderType(returnedOrderType));

        insertedOrderType = returnedOrderType;
    }

    @Test
    @Transactional
    void createOrderTypeWithExistingId() throws Exception {
        // Create the OrderType with an existing ID
        orderType.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(orderType)))
            .andExpect(status().isBadRequest());

        // Validate the OrderType in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLocRefIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        orderType.setLocRef(null);

        // Create the OrderType, which fails.

        restOrderTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(orderType)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrderTypes() throws Exception {
        // Initialize the database
        insertedOrderType = orderTypeRepository.saveAndFlush(orderType);

        // Get all the orderTypeList
        restOrderTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderType.getId().intValue())))
            .andExpect(jsonPath("$.[*].num").value(hasItem(DEFAULT_NUM)))
            .andExpect(jsonPath("$.[*].locRef").value(hasItem(DEFAULT_LOC_REF)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].mstrNum").value(hasItem(DEFAULT_MSTR_NUM)))
            .andExpect(jsonPath("$.[*].mstrName").value(hasItem(DEFAULT_MSTR_NAME)))
            .andExpect(jsonPath("$.[*].catGrpHierName1").value(hasItem(DEFAULT_CAT_GRP_HIER_NAME_1)))
            .andExpect(jsonPath("$.[*].catGrpName1").value(hasItem(DEFAULT_CAT_GRP_NAME_1)))
            .andExpect(jsonPath("$.[*].catGrpHierName2").value(hasItem(DEFAULT_CAT_GRP_HIER_NAME_2)))
            .andExpect(jsonPath("$.[*].catGrpName2").value(hasItem(DEFAULT_CAT_GRP_NAME_2)))
            .andExpect(jsonPath("$.[*].catGrpHierName3").value(hasItem(DEFAULT_CAT_GRP_HIER_NAME_3)))
            .andExpect(jsonPath("$.[*].catGrpName3").value(hasItem(DEFAULT_CAT_GRP_NAME_3)))
            .andExpect(jsonPath("$.[*].catGrpHierName4").value(hasItem(DEFAULT_CAT_GRP_HIER_NAME_4)))
            .andExpect(jsonPath("$.[*].catGrpName4").value(hasItem(DEFAULT_CAT_GRP_NAME_4)));
    }

    @Test
    @Transactional
    void getOrderType() throws Exception {
        // Initialize the database
        insertedOrderType = orderTypeRepository.saveAndFlush(orderType);

        // Get the orderType
        restOrderTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, orderType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderType.getId().intValue()))
            .andExpect(jsonPath("$.num").value(DEFAULT_NUM))
            .andExpect(jsonPath("$.locRef").value(DEFAULT_LOC_REF))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.mstrNum").value(DEFAULT_MSTR_NUM))
            .andExpect(jsonPath("$.mstrName").value(DEFAULT_MSTR_NAME))
            .andExpect(jsonPath("$.catGrpHierName1").value(DEFAULT_CAT_GRP_HIER_NAME_1))
            .andExpect(jsonPath("$.catGrpName1").value(DEFAULT_CAT_GRP_NAME_1))
            .andExpect(jsonPath("$.catGrpHierName2").value(DEFAULT_CAT_GRP_HIER_NAME_2))
            .andExpect(jsonPath("$.catGrpName2").value(DEFAULT_CAT_GRP_NAME_2))
            .andExpect(jsonPath("$.catGrpHierName3").value(DEFAULT_CAT_GRP_HIER_NAME_3))
            .andExpect(jsonPath("$.catGrpName3").value(DEFAULT_CAT_GRP_NAME_3))
            .andExpect(jsonPath("$.catGrpHierName4").value(DEFAULT_CAT_GRP_HIER_NAME_4))
            .andExpect(jsonPath("$.catGrpName4").value(DEFAULT_CAT_GRP_NAME_4));
    }

    @Test
    @Transactional
    void getNonExistingOrderType() throws Exception {
        // Get the orderType
        restOrderTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrderType() throws Exception {
        // Initialize the database
        insertedOrderType = orderTypeRepository.saveAndFlush(orderType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the orderType
        OrderType updatedOrderType = orderTypeRepository.findById(orderType.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOrderType are not directly saved in db
        em.detach(updatedOrderType);
        updatedOrderType
            .num(UPDATED_NUM)
            .locRef(UPDATED_LOC_REF)
            .name(UPDATED_NAME)
            .mstrNum(UPDATED_MSTR_NUM)
            .mstrName(UPDATED_MSTR_NAME)
            .catGrpHierName1(UPDATED_CAT_GRP_HIER_NAME_1)
            .catGrpName1(UPDATED_CAT_GRP_NAME_1)
            .catGrpHierName2(UPDATED_CAT_GRP_HIER_NAME_2)
            .catGrpName2(UPDATED_CAT_GRP_NAME_2)
            .catGrpHierName3(UPDATED_CAT_GRP_HIER_NAME_3)
            .catGrpName3(UPDATED_CAT_GRP_NAME_3)
            .catGrpHierName4(UPDATED_CAT_GRP_HIER_NAME_4)
            .catGrpName4(UPDATED_CAT_GRP_NAME_4);

        restOrderTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrderType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOrderType))
            )
            .andExpect(status().isOk());

        // Validate the OrderType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOrderTypeToMatchAllProperties(updatedOrderType);
    }

    @Test
    @Transactional
    void putNonExistingOrderType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderType.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderType.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(orderType))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrderType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(orderType))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrderType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(orderType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrderTypeWithPatch() throws Exception {
        // Initialize the database
        insertedOrderType = orderTypeRepository.saveAndFlush(orderType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the orderType using partial update
        OrderType partialUpdatedOrderType = new OrderType();
        partialUpdatedOrderType.setId(orderType.getId());

        partialUpdatedOrderType
            .num(UPDATED_NUM)
            .locRef(UPDATED_LOC_REF)
            .catGrpName1(UPDATED_CAT_GRP_NAME_1)
            .catGrpHierName3(UPDATED_CAT_GRP_HIER_NAME_3)
            .catGrpName3(UPDATED_CAT_GRP_NAME_3);

        restOrderTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrderType))
            )
            .andExpect(status().isOk());

        // Validate the OrderType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrderTypeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOrderType, orderType),
            getPersistedOrderType(orderType)
        );
    }

    @Test
    @Transactional
    void fullUpdateOrderTypeWithPatch() throws Exception {
        // Initialize the database
        insertedOrderType = orderTypeRepository.saveAndFlush(orderType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the orderType using partial update
        OrderType partialUpdatedOrderType = new OrderType();
        partialUpdatedOrderType.setId(orderType.getId());

        partialUpdatedOrderType
            .num(UPDATED_NUM)
            .locRef(UPDATED_LOC_REF)
            .name(UPDATED_NAME)
            .mstrNum(UPDATED_MSTR_NUM)
            .mstrName(UPDATED_MSTR_NAME)
            .catGrpHierName1(UPDATED_CAT_GRP_HIER_NAME_1)
            .catGrpName1(UPDATED_CAT_GRP_NAME_1)
            .catGrpHierName2(UPDATED_CAT_GRP_HIER_NAME_2)
            .catGrpName2(UPDATED_CAT_GRP_NAME_2)
            .catGrpHierName3(UPDATED_CAT_GRP_HIER_NAME_3)
            .catGrpName3(UPDATED_CAT_GRP_NAME_3)
            .catGrpHierName4(UPDATED_CAT_GRP_HIER_NAME_4)
            .catGrpName4(UPDATED_CAT_GRP_NAME_4);

        restOrderTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrderType))
            )
            .andExpect(status().isOk());

        // Validate the OrderType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrderTypeUpdatableFieldsEquals(partialUpdatedOrderType, getPersistedOrderType(partialUpdatedOrderType));
    }

    @Test
    @Transactional
    void patchNonExistingOrderType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderType.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orderType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(orderType))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrderType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(orderType))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrderType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderTypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(orderType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrderType() throws Exception {
        // Initialize the database
        insertedOrderType = orderTypeRepository.saveAndFlush(orderType);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the orderType
        restOrderTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, orderType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return orderTypeRepository.count();
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

    protected OrderType getPersistedOrderType(OrderType orderType) {
        return orderTypeRepository.findById(orderType.getId()).orElseThrow();
    }

    protected void assertPersistedOrderTypeToMatchAllProperties(OrderType expectedOrderType) {
        assertOrderTypeAllPropertiesEquals(expectedOrderType, getPersistedOrderType(expectedOrderType));
    }

    protected void assertPersistedOrderTypeToMatchUpdatableProperties(OrderType expectedOrderType) {
        assertOrderTypeAllUpdatablePropertiesEquals(expectedOrderType, getPersistedOrderType(expectedOrderType));
    }
}
