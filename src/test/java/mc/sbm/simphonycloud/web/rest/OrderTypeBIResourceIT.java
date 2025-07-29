package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.OrderTypeBIAsserts.*;
import static mc.sbm.simphonycloud.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import mc.sbm.simphonycloud.IntegrationTest;
import mc.sbm.simphonycloud.domain.OrderTypeBI;
import mc.sbm.simphonycloud.repository.OrderTypeBIRepository;
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
 * Integration tests for the {@link OrderTypeBIResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrderTypeBIResourceIT {

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

    private static final String ENTITY_API_URL = "/api/order-type-bis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OrderTypeBIRepository orderTypeBIRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderTypeBIMockMvc;

    private OrderTypeBI orderTypeBI;

    private OrderTypeBI insertedOrderTypeBI;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderTypeBI createEntity() {
        return new OrderTypeBI()
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
    public static OrderTypeBI createUpdatedEntity() {
        return new OrderTypeBI()
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
        orderTypeBI = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedOrderTypeBI != null) {
            orderTypeBIRepository.delete(insertedOrderTypeBI);
            insertedOrderTypeBI = null;
        }
    }

    @Test
    @Transactional
    void createOrderTypeBI() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the OrderTypeBI
        var returnedOrderTypeBI = om.readValue(
            restOrderTypeBIMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(orderTypeBI)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OrderTypeBI.class
        );

        // Validate the OrderTypeBI in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOrderTypeBIUpdatableFieldsEquals(returnedOrderTypeBI, getPersistedOrderTypeBI(returnedOrderTypeBI));

        insertedOrderTypeBI = returnedOrderTypeBI;
    }

    @Test
    @Transactional
    void createOrderTypeBIWithExistingId() throws Exception {
        // Create the OrderTypeBI with an existing ID
        orderTypeBI.setId(1);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderTypeBIMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(orderTypeBI)))
            .andExpect(status().isBadRequest());

        // Validate the OrderTypeBI in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLocRefIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        orderTypeBI.setLocRef(null);

        // Create the OrderTypeBI, which fails.

        restOrderTypeBIMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(orderTypeBI)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrderTypeBIS() throws Exception {
        // Initialize the database
        insertedOrderTypeBI = orderTypeBIRepository.saveAndFlush(orderTypeBI);

        // Get all the orderTypeBIList
        restOrderTypeBIMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderTypeBI.getId().intValue())))
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
    void getOrderTypeBI() throws Exception {
        // Initialize the database
        insertedOrderTypeBI = orderTypeBIRepository.saveAndFlush(orderTypeBI);

        // Get the orderTypeBI
        restOrderTypeBIMockMvc
            .perform(get(ENTITY_API_URL_ID, orderTypeBI.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderTypeBI.getId().intValue()))
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
    void getNonExistingOrderTypeBI() throws Exception {
        // Get the orderTypeBI
        restOrderTypeBIMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrderTypeBI() throws Exception {
        // Initialize the database
        insertedOrderTypeBI = orderTypeBIRepository.saveAndFlush(orderTypeBI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the orderTypeBI
        OrderTypeBI updatedOrderTypeBI = orderTypeBIRepository.findById(orderTypeBI.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOrderTypeBI are not directly saved in db
        em.detach(updatedOrderTypeBI);
        updatedOrderTypeBI
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

        restOrderTypeBIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrderTypeBI.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOrderTypeBI))
            )
            .andExpect(status().isOk());

        // Validate the OrderTypeBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOrderTypeBIToMatchAllProperties(updatedOrderTypeBI);
    }

    @Test
    @Transactional
    void putNonExistingOrderTypeBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderTypeBI.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderTypeBIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderTypeBI.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(orderTypeBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderTypeBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrderTypeBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderTypeBI.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderTypeBIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(orderTypeBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderTypeBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrderTypeBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderTypeBI.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderTypeBIMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(orderTypeBI)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderTypeBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrderTypeBIWithPatch() throws Exception {
        // Initialize the database
        insertedOrderTypeBI = orderTypeBIRepository.saveAndFlush(orderTypeBI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the orderTypeBI using partial update
        OrderTypeBI partialUpdatedOrderTypeBI = new OrderTypeBI();
        partialUpdatedOrderTypeBI.setId(orderTypeBI.getId());

        partialUpdatedOrderTypeBI
            .catGrpName1(UPDATED_CAT_GRP_NAME_1)
            .catGrpName2(UPDATED_CAT_GRP_NAME_2)
            .catGrpName4(UPDATED_CAT_GRP_NAME_4);

        restOrderTypeBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderTypeBI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrderTypeBI))
            )
            .andExpect(status().isOk());

        // Validate the OrderTypeBI in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrderTypeBIUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOrderTypeBI, orderTypeBI),
            getPersistedOrderTypeBI(orderTypeBI)
        );
    }

    @Test
    @Transactional
    void fullUpdateOrderTypeBIWithPatch() throws Exception {
        // Initialize the database
        insertedOrderTypeBI = orderTypeBIRepository.saveAndFlush(orderTypeBI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the orderTypeBI using partial update
        OrderTypeBI partialUpdatedOrderTypeBI = new OrderTypeBI();
        partialUpdatedOrderTypeBI.setId(orderTypeBI.getId());

        partialUpdatedOrderTypeBI
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

        restOrderTypeBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderTypeBI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrderTypeBI))
            )
            .andExpect(status().isOk());

        // Validate the OrderTypeBI in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrderTypeBIUpdatableFieldsEquals(partialUpdatedOrderTypeBI, getPersistedOrderTypeBI(partialUpdatedOrderTypeBI));
    }

    @Test
    @Transactional
    void patchNonExistingOrderTypeBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderTypeBI.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderTypeBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orderTypeBI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(orderTypeBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderTypeBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrderTypeBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderTypeBI.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderTypeBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(orderTypeBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderTypeBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrderTypeBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderTypeBI.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderTypeBIMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(orderTypeBI)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderTypeBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrderTypeBI() throws Exception {
        // Initialize the database
        insertedOrderTypeBI = orderTypeBIRepository.saveAndFlush(orderTypeBI);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the orderTypeBI
        restOrderTypeBIMockMvc
            .perform(delete(ENTITY_API_URL_ID, orderTypeBI.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return orderTypeBIRepository.count();
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

    protected OrderTypeBI getPersistedOrderTypeBI(OrderTypeBI orderTypeBI) {
        return orderTypeBIRepository.findById(orderTypeBI.getId()).orElseThrow();
    }

    protected void assertPersistedOrderTypeBIToMatchAllProperties(OrderTypeBI expectedOrderTypeBI) {
        assertOrderTypeBIAllPropertiesEquals(expectedOrderTypeBI, getPersistedOrderTypeBI(expectedOrderTypeBI));
    }

    protected void assertPersistedOrderTypeBIToMatchUpdatableProperties(OrderTypeBI expectedOrderTypeBI) {
        assertOrderTypeBIAllUpdatablePropertiesEquals(expectedOrderTypeBI, getPersistedOrderTypeBI(expectedOrderTypeBI));
    }
}
