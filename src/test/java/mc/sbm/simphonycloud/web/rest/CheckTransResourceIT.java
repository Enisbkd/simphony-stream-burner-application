package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.CheckTransAsserts.*;
import static mc.sbm.simphonycloud.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import mc.sbm.simphonycloud.IntegrationTest;
import mc.sbm.simphonycloud.domain.CheckTrans;
import mc.sbm.simphonycloud.repository.CheckTransRepository;
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
 * Integration tests for the {@link CheckTransResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CheckTransResourceIT {

    private static final Integer DEFAULT_RVC_REF = 1;
    private static final Integer UPDATED_RVC_REF = 2;

    private static final String DEFAULT_CHECK_REF = "AAAAAAAAAA";
    private static final String UPDATED_CHECK_REF = "BBBBBBBBBB";

    private static final Integer DEFAULT_CHECK_NUMBER = 1;
    private static final Integer UPDATED_CHECK_NUMBER = 2;

    private static final String DEFAULT_CHECK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CHECK_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_CHECK_EMPLOYEE_REF = 1;
    private static final Integer UPDATED_CHECK_EMPLOYEE_REF = 2;

    private static final Integer DEFAULT_ORDER_TYPE_REF = 1;
    private static final Integer UPDATED_ORDER_TYPE_REF = 2;

    private static final Integer DEFAULT_ORDER_CHANNEL_REF = 1;
    private static final Integer UPDATED_ORDER_CHANNEL_REF = 2;

    private static final String DEFAULT_TABLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TABLE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_TABLE_GROUP_NUMBER = 1;
    private static final Integer UPDATED_TABLE_GROUP_NUMBER = 2;

    private static final Instant DEFAULT_OPEN_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OPEN_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_GUEST_COUNT = 1;
    private static final Integer UPDATED_GUEST_COUNT = 2;

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_TRAINING_CHECK = false;
    private static final Boolean UPDATED_IS_TRAINING_CHECK = true;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_PREPARATION_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_PREPARATION_STATUS = "BBBBBBBBBB";

    private static final Double DEFAULT_SUBTOTAL = 1D;
    private static final Double UPDATED_SUBTOTAL = 2D;

    private static final Double DEFAULT_SUBTOTAL_DISCOUNT_TOTAL = 1D;
    private static final Double UPDATED_SUBTOTAL_DISCOUNT_TOTAL = 2D;

    private static final Double DEFAULT_AUTO_SERVICE_CHARGE_TOTAL = 1D;
    private static final Double UPDATED_AUTO_SERVICE_CHARGE_TOTAL = 2D;

    private static final Double DEFAULT_SERVICE_CHARGE_TOTAL = 1D;
    private static final Double UPDATED_SERVICE_CHARGE_TOTAL = 2D;

    private static final Double DEFAULT_TAX_TOTAL = 1D;
    private static final Double UPDATED_TAX_TOTAL = 2D;

    private static final Double DEFAULT_PAYMENT_TOTAL = 1D;
    private static final Double UPDATED_PAYMENT_TOTAL = 2D;

    private static final Double DEFAULT_TOTAL_DUE = 1D;
    private static final Double UPDATED_TOTAL_DUE = 2D;

    private static final Integer DEFAULT_TAX_RATE_ID = 1;
    private static final Integer UPDATED_TAX_RATE_ID = 2;

    private static final String ENTITY_API_URL = "/api/check-trans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CheckTransRepository checkTransRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCheckTransMockMvc;

    private CheckTrans checkTrans;

    private CheckTrans insertedCheckTrans;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckTrans createEntity() {
        return new CheckTrans()
            .rvcRef(DEFAULT_RVC_REF)
            .checkRef(DEFAULT_CHECK_REF)
            .checkNumber(DEFAULT_CHECK_NUMBER)
            .checkName(DEFAULT_CHECK_NAME)
            .checkEmployeeRef(DEFAULT_CHECK_EMPLOYEE_REF)
            .orderTypeRef(DEFAULT_ORDER_TYPE_REF)
            .orderChannelRef(DEFAULT_ORDER_CHANNEL_REF)
            .tableName(DEFAULT_TABLE_NAME)
            .tableGroupNumber(DEFAULT_TABLE_GROUP_NUMBER)
            .openTime(DEFAULT_OPEN_TIME)
            .guestCount(DEFAULT_GUEST_COUNT)
            .language(DEFAULT_LANGUAGE)
            .isTrainingCheck(DEFAULT_IS_TRAINING_CHECK)
            .status(DEFAULT_STATUS)
            .preparationStatus(DEFAULT_PREPARATION_STATUS)
            .subtotal(DEFAULT_SUBTOTAL)
            .subtotalDiscountTotal(DEFAULT_SUBTOTAL_DISCOUNT_TOTAL)
            .autoServiceChargeTotal(DEFAULT_AUTO_SERVICE_CHARGE_TOTAL)
            .serviceChargeTotal(DEFAULT_SERVICE_CHARGE_TOTAL)
            .taxTotal(DEFAULT_TAX_TOTAL)
            .paymentTotal(DEFAULT_PAYMENT_TOTAL)
            .totalDue(DEFAULT_TOTAL_DUE)
            .taxRateId(DEFAULT_TAX_RATE_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckTrans createUpdatedEntity() {
        return new CheckTrans()
            .rvcRef(UPDATED_RVC_REF)
            .checkRef(UPDATED_CHECK_REF)
            .checkNumber(UPDATED_CHECK_NUMBER)
            .checkName(UPDATED_CHECK_NAME)
            .checkEmployeeRef(UPDATED_CHECK_EMPLOYEE_REF)
            .orderTypeRef(UPDATED_ORDER_TYPE_REF)
            .orderChannelRef(UPDATED_ORDER_CHANNEL_REF)
            .tableName(UPDATED_TABLE_NAME)
            .tableGroupNumber(UPDATED_TABLE_GROUP_NUMBER)
            .openTime(UPDATED_OPEN_TIME)
            .guestCount(UPDATED_GUEST_COUNT)
            .language(UPDATED_LANGUAGE)
            .isTrainingCheck(UPDATED_IS_TRAINING_CHECK)
            .status(UPDATED_STATUS)
            .preparationStatus(UPDATED_PREPARATION_STATUS)
            .subtotal(UPDATED_SUBTOTAL)
            .subtotalDiscountTotal(UPDATED_SUBTOTAL_DISCOUNT_TOTAL)
            .autoServiceChargeTotal(UPDATED_AUTO_SERVICE_CHARGE_TOTAL)
            .serviceChargeTotal(UPDATED_SERVICE_CHARGE_TOTAL)
            .taxTotal(UPDATED_TAX_TOTAL)
            .paymentTotal(UPDATED_PAYMENT_TOTAL)
            .totalDue(UPDATED_TOTAL_DUE)
            .taxRateId(UPDATED_TAX_RATE_ID);
    }

    @BeforeEach
    void initTest() {
        checkTrans = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedCheckTrans != null) {
            checkTransRepository.delete(insertedCheckTrans);
            insertedCheckTrans = null;
        }
    }

    @Test
    @Transactional
    void createCheckTrans() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CheckTrans
        var returnedCheckTrans = om.readValue(
            restCheckTransMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(checkTrans)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CheckTrans.class
        );

        // Validate the CheckTrans in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCheckTransUpdatableFieldsEquals(returnedCheckTrans, getPersistedCheckTrans(returnedCheckTrans));

        insertedCheckTrans = returnedCheckTrans;
    }

    @Test
    @Transactional
    void createCheckTransWithExistingId() throws Exception {
        // Create the CheckTrans with an existing ID
        checkTrans.setId(1);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckTransMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(checkTrans)))
            .andExpect(status().isBadRequest());

        // Validate the CheckTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCheckTrans() throws Exception {
        // Initialize the database
        insertedCheckTrans = checkTransRepository.saveAndFlush(checkTrans);

        // Get all the checkTransList
        restCheckTransMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkTrans.getId().intValue())))
            .andExpect(jsonPath("$.[*].rvcRef").value(hasItem(DEFAULT_RVC_REF)))
            .andExpect(jsonPath("$.[*].checkRef").value(hasItem(DEFAULT_CHECK_REF)))
            .andExpect(jsonPath("$.[*].checkNumber").value(hasItem(DEFAULT_CHECK_NUMBER)))
            .andExpect(jsonPath("$.[*].checkName").value(hasItem(DEFAULT_CHECK_NAME)))
            .andExpect(jsonPath("$.[*].checkEmployeeRef").value(hasItem(DEFAULT_CHECK_EMPLOYEE_REF)))
            .andExpect(jsonPath("$.[*].orderTypeRef").value(hasItem(DEFAULT_ORDER_TYPE_REF)))
            .andExpect(jsonPath("$.[*].orderChannelRef").value(hasItem(DEFAULT_ORDER_CHANNEL_REF)))
            .andExpect(jsonPath("$.[*].tableName").value(hasItem(DEFAULT_TABLE_NAME)))
            .andExpect(jsonPath("$.[*].tableGroupNumber").value(hasItem(DEFAULT_TABLE_GROUP_NUMBER)))
            .andExpect(jsonPath("$.[*].openTime").value(hasItem(DEFAULT_OPEN_TIME.toString())))
            .andExpect(jsonPath("$.[*].guestCount").value(hasItem(DEFAULT_GUEST_COUNT)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)))
            .andExpect(jsonPath("$.[*].isTrainingCheck").value(hasItem(DEFAULT_IS_TRAINING_CHECK)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].preparationStatus").value(hasItem(DEFAULT_PREPARATION_STATUS)))
            .andExpect(jsonPath("$.[*].subtotal").value(hasItem(DEFAULT_SUBTOTAL)))
            .andExpect(jsonPath("$.[*].subtotalDiscountTotal").value(hasItem(DEFAULT_SUBTOTAL_DISCOUNT_TOTAL)))
            .andExpect(jsonPath("$.[*].autoServiceChargeTotal").value(hasItem(DEFAULT_AUTO_SERVICE_CHARGE_TOTAL)))
            .andExpect(jsonPath("$.[*].serviceChargeTotal").value(hasItem(DEFAULT_SERVICE_CHARGE_TOTAL)))
            .andExpect(jsonPath("$.[*].taxTotal").value(hasItem(DEFAULT_TAX_TOTAL)))
            .andExpect(jsonPath("$.[*].paymentTotal").value(hasItem(DEFAULT_PAYMENT_TOTAL)))
            .andExpect(jsonPath("$.[*].totalDue").value(hasItem(DEFAULT_TOTAL_DUE)))
            .andExpect(jsonPath("$.[*].taxRateId").value(hasItem(DEFAULT_TAX_RATE_ID)));
    }

    @Test
    @Transactional
    void getCheckTrans() throws Exception {
        // Initialize the database
        insertedCheckTrans = checkTransRepository.saveAndFlush(checkTrans);

        // Get the checkTrans
        restCheckTransMockMvc
            .perform(get(ENTITY_API_URL_ID, checkTrans.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(checkTrans.getId().intValue()))
            .andExpect(jsonPath("$.rvcRef").value(DEFAULT_RVC_REF))
            .andExpect(jsonPath("$.checkRef").value(DEFAULT_CHECK_REF))
            .andExpect(jsonPath("$.checkNumber").value(DEFAULT_CHECK_NUMBER))
            .andExpect(jsonPath("$.checkName").value(DEFAULT_CHECK_NAME))
            .andExpect(jsonPath("$.checkEmployeeRef").value(DEFAULT_CHECK_EMPLOYEE_REF))
            .andExpect(jsonPath("$.orderTypeRef").value(DEFAULT_ORDER_TYPE_REF))
            .andExpect(jsonPath("$.orderChannelRef").value(DEFAULT_ORDER_CHANNEL_REF))
            .andExpect(jsonPath("$.tableName").value(DEFAULT_TABLE_NAME))
            .andExpect(jsonPath("$.tableGroupNumber").value(DEFAULT_TABLE_GROUP_NUMBER))
            .andExpect(jsonPath("$.openTime").value(DEFAULT_OPEN_TIME.toString()))
            .andExpect(jsonPath("$.guestCount").value(DEFAULT_GUEST_COUNT))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE))
            .andExpect(jsonPath("$.isTrainingCheck").value(DEFAULT_IS_TRAINING_CHECK))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.preparationStatus").value(DEFAULT_PREPARATION_STATUS))
            .andExpect(jsonPath("$.subtotal").value(DEFAULT_SUBTOTAL))
            .andExpect(jsonPath("$.subtotalDiscountTotal").value(DEFAULT_SUBTOTAL_DISCOUNT_TOTAL))
            .andExpect(jsonPath("$.autoServiceChargeTotal").value(DEFAULT_AUTO_SERVICE_CHARGE_TOTAL))
            .andExpect(jsonPath("$.serviceChargeTotal").value(DEFAULT_SERVICE_CHARGE_TOTAL))
            .andExpect(jsonPath("$.taxTotal").value(DEFAULT_TAX_TOTAL))
            .andExpect(jsonPath("$.paymentTotal").value(DEFAULT_PAYMENT_TOTAL))
            .andExpect(jsonPath("$.totalDue").value(DEFAULT_TOTAL_DUE))
            .andExpect(jsonPath("$.taxRateId").value(DEFAULT_TAX_RATE_ID));
    }

    @Test
    @Transactional
    void getNonExistingCheckTrans() throws Exception {
        // Get the checkTrans
        restCheckTransMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCheckTrans() throws Exception {
        // Initialize the database
        insertedCheckTrans = checkTransRepository.saveAndFlush(checkTrans);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the checkTrans
        CheckTrans updatedCheckTrans = checkTransRepository.findById(checkTrans.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCheckTrans are not directly saved in db
        em.detach(updatedCheckTrans);
        updatedCheckTrans
            .rvcRef(UPDATED_RVC_REF)
            .checkRef(UPDATED_CHECK_REF)
            .checkNumber(UPDATED_CHECK_NUMBER)
            .checkName(UPDATED_CHECK_NAME)
            .checkEmployeeRef(UPDATED_CHECK_EMPLOYEE_REF)
            .orderTypeRef(UPDATED_ORDER_TYPE_REF)
            .orderChannelRef(UPDATED_ORDER_CHANNEL_REF)
            .tableName(UPDATED_TABLE_NAME)
            .tableGroupNumber(UPDATED_TABLE_GROUP_NUMBER)
            .openTime(UPDATED_OPEN_TIME)
            .guestCount(UPDATED_GUEST_COUNT)
            .language(UPDATED_LANGUAGE)
            .isTrainingCheck(UPDATED_IS_TRAINING_CHECK)
            .status(UPDATED_STATUS)
            .preparationStatus(UPDATED_PREPARATION_STATUS)
            .subtotal(UPDATED_SUBTOTAL)
            .subtotalDiscountTotal(UPDATED_SUBTOTAL_DISCOUNT_TOTAL)
            .autoServiceChargeTotal(UPDATED_AUTO_SERVICE_CHARGE_TOTAL)
            .serviceChargeTotal(UPDATED_SERVICE_CHARGE_TOTAL)
            .taxTotal(UPDATED_TAX_TOTAL)
            .paymentTotal(UPDATED_PAYMENT_TOTAL)
            .totalDue(UPDATED_TOTAL_DUE)
            .taxRateId(UPDATED_TAX_RATE_ID);

        restCheckTransMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCheckTrans.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCheckTrans))
            )
            .andExpect(status().isOk());

        // Validate the CheckTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCheckTransToMatchAllProperties(updatedCheckTrans);
    }

    @Test
    @Transactional
    void putNonExistingCheckTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        checkTrans.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckTransMockMvc
            .perform(
                put(ENTITY_API_URL_ID, checkTrans.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(checkTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCheckTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        checkTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckTransMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(checkTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCheckTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        checkTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckTransMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(checkTrans)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CheckTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCheckTransWithPatch() throws Exception {
        // Initialize the database
        insertedCheckTrans = checkTransRepository.saveAndFlush(checkTrans);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the checkTrans using partial update
        CheckTrans partialUpdatedCheckTrans = new CheckTrans();
        partialUpdatedCheckTrans.setId(checkTrans.getId());

        partialUpdatedCheckTrans
            .checkRef(UPDATED_CHECK_REF)
            .checkNumber(UPDATED_CHECK_NUMBER)
            .checkName(UPDATED_CHECK_NAME)
            .tableName(UPDATED_TABLE_NAME)
            .guestCount(UPDATED_GUEST_COUNT)
            .isTrainingCheck(UPDATED_IS_TRAINING_CHECK)
            .preparationStatus(UPDATED_PREPARATION_STATUS)
            .subtotalDiscountTotal(UPDATED_SUBTOTAL_DISCOUNT_TOTAL)
            .autoServiceChargeTotal(UPDATED_AUTO_SERVICE_CHARGE_TOTAL)
            .taxTotal(UPDATED_TAX_TOTAL)
            .paymentTotal(UPDATED_PAYMENT_TOTAL);

        restCheckTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckTrans.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCheckTrans))
            )
            .andExpect(status().isOk());

        // Validate the CheckTrans in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCheckTransUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCheckTrans, checkTrans),
            getPersistedCheckTrans(checkTrans)
        );
    }

    @Test
    @Transactional
    void fullUpdateCheckTransWithPatch() throws Exception {
        // Initialize the database
        insertedCheckTrans = checkTransRepository.saveAndFlush(checkTrans);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the checkTrans using partial update
        CheckTrans partialUpdatedCheckTrans = new CheckTrans();
        partialUpdatedCheckTrans.setId(checkTrans.getId());

        partialUpdatedCheckTrans
            .rvcRef(UPDATED_RVC_REF)
            .checkRef(UPDATED_CHECK_REF)
            .checkNumber(UPDATED_CHECK_NUMBER)
            .checkName(UPDATED_CHECK_NAME)
            .checkEmployeeRef(UPDATED_CHECK_EMPLOYEE_REF)
            .orderTypeRef(UPDATED_ORDER_TYPE_REF)
            .orderChannelRef(UPDATED_ORDER_CHANNEL_REF)
            .tableName(UPDATED_TABLE_NAME)
            .tableGroupNumber(UPDATED_TABLE_GROUP_NUMBER)
            .openTime(UPDATED_OPEN_TIME)
            .guestCount(UPDATED_GUEST_COUNT)
            .language(UPDATED_LANGUAGE)
            .isTrainingCheck(UPDATED_IS_TRAINING_CHECK)
            .status(UPDATED_STATUS)
            .preparationStatus(UPDATED_PREPARATION_STATUS)
            .subtotal(UPDATED_SUBTOTAL)
            .subtotalDiscountTotal(UPDATED_SUBTOTAL_DISCOUNT_TOTAL)
            .autoServiceChargeTotal(UPDATED_AUTO_SERVICE_CHARGE_TOTAL)
            .serviceChargeTotal(UPDATED_SERVICE_CHARGE_TOTAL)
            .taxTotal(UPDATED_TAX_TOTAL)
            .paymentTotal(UPDATED_PAYMENT_TOTAL)
            .totalDue(UPDATED_TOTAL_DUE)
            .taxRateId(UPDATED_TAX_RATE_ID);

        restCheckTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckTrans.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCheckTrans))
            )
            .andExpect(status().isOk());

        // Validate the CheckTrans in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCheckTransUpdatableFieldsEquals(partialUpdatedCheckTrans, getPersistedCheckTrans(partialUpdatedCheckTrans));
    }

    @Test
    @Transactional
    void patchNonExistingCheckTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        checkTrans.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, checkTrans.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(checkTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCheckTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        checkTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(checkTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCheckTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        checkTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckTransMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(checkTrans)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CheckTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCheckTrans() throws Exception {
        // Initialize the database
        insertedCheckTrans = checkTransRepository.saveAndFlush(checkTrans);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the checkTrans
        restCheckTransMockMvc
            .perform(delete(ENTITY_API_URL_ID, checkTrans.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return checkTransRepository.count();
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

    protected CheckTrans getPersistedCheckTrans(CheckTrans checkTrans) {
        return checkTransRepository.findById(checkTrans.getId()).orElseThrow();
    }

    protected void assertPersistedCheckTransToMatchAllProperties(CheckTrans expectedCheckTrans) {
        assertCheckTransAllPropertiesEquals(expectedCheckTrans, getPersistedCheckTrans(expectedCheckTrans));
    }

    protected void assertPersistedCheckTransToMatchUpdatableProperties(CheckTrans expectedCheckTrans) {
        assertCheckTransAllUpdatablePropertiesEquals(expectedCheckTrans, getPersistedCheckTrans(expectedCheckTrans));
    }
}
