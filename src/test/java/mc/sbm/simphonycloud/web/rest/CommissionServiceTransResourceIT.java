package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.CommissionServiceTransAsserts.*;
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
import mc.sbm.simphonycloud.domain.CommissionServiceTrans;
import mc.sbm.simphonycloud.repository.CommissionServiceTransRepository;
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
 * Integration tests for the {@link CommissionServiceTransResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CommissionServiceTransResourceIT {

    private static final String DEFAULT_ORG_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORG_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOC_REF = "AAAAAAAAAA";
    private static final String UPDATED_LOC_REF = "BBBBBBBBBB";

    private static final Integer DEFAULT_RVC_REF = 1;
    private static final Integer UPDATED_RVC_REF = 2;

    private static final Integer DEFAULT_SERVICE_CHARGE_ID = 1;
    private static final Integer UPDATED_SERVICE_CHARGE_ID = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Double DEFAULT_VALUE = 1D;
    private static final Double UPDATED_VALUE = 2D;

    private static final String ENTITY_API_URL = "/api/commission-service-trans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CommissionServiceTransRepository commissionServiceTransRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommissionServiceTransMockMvc;

    private CommissionServiceTrans commissionServiceTrans;

    private CommissionServiceTrans insertedCommissionServiceTrans;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommissionServiceTrans createEntity() {
        return new CommissionServiceTrans()
            .orgShortName(DEFAULT_ORG_SHORT_NAME)
            .locRef(DEFAULT_LOC_REF)
            .rvcRef(DEFAULT_RVC_REF)
            .serviceChargeId(DEFAULT_SERVICE_CHARGE_ID)
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .value(DEFAULT_VALUE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommissionServiceTrans createUpdatedEntity() {
        return new CommissionServiceTrans()
            .orgShortName(UPDATED_ORG_SHORT_NAME)
            .locRef(UPDATED_LOC_REF)
            .rvcRef(UPDATED_RVC_REF)
            .serviceChargeId(UPDATED_SERVICE_CHARGE_ID)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE);
    }

    @BeforeEach
    void initTest() {
        commissionServiceTrans = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedCommissionServiceTrans != null) {
            commissionServiceTransRepository.delete(insertedCommissionServiceTrans);
            insertedCommissionServiceTrans = null;
        }
    }

    @Test
    @Transactional
    void createCommissionServiceTrans() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CommissionServiceTrans
        var returnedCommissionServiceTrans = om.readValue(
            restCommissionServiceTransMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(commissionServiceTrans)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CommissionServiceTrans.class
        );

        // Validate the CommissionServiceTrans in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCommissionServiceTransUpdatableFieldsEquals(
            returnedCommissionServiceTrans,
            getPersistedCommissionServiceTrans(returnedCommissionServiceTrans)
        );

        insertedCommissionServiceTrans = returnedCommissionServiceTrans;
    }

    @Test
    @Transactional
    void createCommissionServiceTransWithExistingId() throws Exception {
        // Create the CommissionServiceTrans with an existing ID
        commissionServiceTrans.setId(1);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommissionServiceTransMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(commissionServiceTrans)))
            .andExpect(status().isBadRequest());

        // Validate the CommissionServiceTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCommissionServiceTrans() throws Exception {
        // Initialize the database
        insertedCommissionServiceTrans = commissionServiceTransRepository.saveAndFlush(commissionServiceTrans);

        // Get all the commissionServiceTransList
        restCommissionServiceTransMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commissionServiceTrans.getId().intValue())))
            .andExpect(jsonPath("$.[*].orgShortName").value(hasItem(DEFAULT_ORG_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].locRef").value(hasItem(DEFAULT_LOC_REF)))
            .andExpect(jsonPath("$.[*].rvcRef").value(hasItem(DEFAULT_RVC_REF)))
            .andExpect(jsonPath("$.[*].serviceChargeId").value(hasItem(DEFAULT_SERVICE_CHARGE_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    void getCommissionServiceTrans() throws Exception {
        // Initialize the database
        insertedCommissionServiceTrans = commissionServiceTransRepository.saveAndFlush(commissionServiceTrans);

        // Get the commissionServiceTrans
        restCommissionServiceTransMockMvc
            .perform(get(ENTITY_API_URL_ID, commissionServiceTrans.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commissionServiceTrans.getId().intValue()))
            .andExpect(jsonPath("$.orgShortName").value(DEFAULT_ORG_SHORT_NAME))
            .andExpect(jsonPath("$.locRef").value(DEFAULT_LOC_REF))
            .andExpect(jsonPath("$.rvcRef").value(DEFAULT_RVC_REF))
            .andExpect(jsonPath("$.serviceChargeId").value(DEFAULT_SERVICE_CHARGE_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingCommissionServiceTrans() throws Exception {
        // Get the commissionServiceTrans
        restCommissionServiceTransMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCommissionServiceTrans() throws Exception {
        // Initialize the database
        insertedCommissionServiceTrans = commissionServiceTransRepository.saveAndFlush(commissionServiceTrans);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the commissionServiceTrans
        CommissionServiceTrans updatedCommissionServiceTrans = commissionServiceTransRepository
            .findById(commissionServiceTrans.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedCommissionServiceTrans are not directly saved in db
        em.detach(updatedCommissionServiceTrans);
        updatedCommissionServiceTrans
            .orgShortName(UPDATED_ORG_SHORT_NAME)
            .locRef(UPDATED_LOC_REF)
            .rvcRef(UPDATED_RVC_REF)
            .serviceChargeId(UPDATED_SERVICE_CHARGE_ID)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE);

        restCommissionServiceTransMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCommissionServiceTrans.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCommissionServiceTrans))
            )
            .andExpect(status().isOk());

        // Validate the CommissionServiceTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCommissionServiceTransToMatchAllProperties(updatedCommissionServiceTrans);
    }

    @Test
    @Transactional
    void putNonExistingCommissionServiceTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commissionServiceTrans.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommissionServiceTransMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commissionServiceTrans.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(commissionServiceTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommissionServiceTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCommissionServiceTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commissionServiceTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommissionServiceTransMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(commissionServiceTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommissionServiceTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCommissionServiceTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commissionServiceTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommissionServiceTransMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(commissionServiceTrans)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CommissionServiceTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCommissionServiceTransWithPatch() throws Exception {
        // Initialize the database
        insertedCommissionServiceTrans = commissionServiceTransRepository.saveAndFlush(commissionServiceTrans);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the commissionServiceTrans using partial update
        CommissionServiceTrans partialUpdatedCommissionServiceTrans = new CommissionServiceTrans();
        partialUpdatedCommissionServiceTrans.setId(commissionServiceTrans.getId());

        partialUpdatedCommissionServiceTrans
            .rvcRef(UPDATED_RVC_REF)
            .serviceChargeId(UPDATED_SERVICE_CHARGE_ID)
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE);

        restCommissionServiceTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommissionServiceTrans.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCommissionServiceTrans))
            )
            .andExpect(status().isOk());

        // Validate the CommissionServiceTrans in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCommissionServiceTransUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCommissionServiceTrans, commissionServiceTrans),
            getPersistedCommissionServiceTrans(commissionServiceTrans)
        );
    }

    @Test
    @Transactional
    void fullUpdateCommissionServiceTransWithPatch() throws Exception {
        // Initialize the database
        insertedCommissionServiceTrans = commissionServiceTransRepository.saveAndFlush(commissionServiceTrans);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the commissionServiceTrans using partial update
        CommissionServiceTrans partialUpdatedCommissionServiceTrans = new CommissionServiceTrans();
        partialUpdatedCommissionServiceTrans.setId(commissionServiceTrans.getId());

        partialUpdatedCommissionServiceTrans
            .orgShortName(UPDATED_ORG_SHORT_NAME)
            .locRef(UPDATED_LOC_REF)
            .rvcRef(UPDATED_RVC_REF)
            .serviceChargeId(UPDATED_SERVICE_CHARGE_ID)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE);

        restCommissionServiceTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommissionServiceTrans.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCommissionServiceTrans))
            )
            .andExpect(status().isOk());

        // Validate the CommissionServiceTrans in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCommissionServiceTransUpdatableFieldsEquals(
            partialUpdatedCommissionServiceTrans,
            getPersistedCommissionServiceTrans(partialUpdatedCommissionServiceTrans)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCommissionServiceTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commissionServiceTrans.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommissionServiceTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, commissionServiceTrans.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(commissionServiceTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommissionServiceTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCommissionServiceTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commissionServiceTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommissionServiceTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(commissionServiceTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommissionServiceTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCommissionServiceTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commissionServiceTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommissionServiceTransMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(commissionServiceTrans))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CommissionServiceTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCommissionServiceTrans() throws Exception {
        // Initialize the database
        insertedCommissionServiceTrans = commissionServiceTransRepository.saveAndFlush(commissionServiceTrans);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the commissionServiceTrans
        restCommissionServiceTransMockMvc
            .perform(delete(ENTITY_API_URL_ID, commissionServiceTrans.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return commissionServiceTransRepository.count();
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

    protected CommissionServiceTrans getPersistedCommissionServiceTrans(CommissionServiceTrans commissionServiceTrans) {
        return commissionServiceTransRepository.findById(commissionServiceTrans.getId()).orElseThrow();
    }

    protected void assertPersistedCommissionServiceTransToMatchAllProperties(CommissionServiceTrans expectedCommissionServiceTrans) {
        assertCommissionServiceTransAllPropertiesEquals(
            expectedCommissionServiceTrans,
            getPersistedCommissionServiceTrans(expectedCommissionServiceTrans)
        );
    }

    protected void assertPersistedCommissionServiceTransToMatchUpdatableProperties(CommissionServiceTrans expectedCommissionServiceTrans) {
        assertCommissionServiceTransAllUpdatablePropertiesEquals(
            expectedCommissionServiceTrans,
            getPersistedCommissionServiceTrans(expectedCommissionServiceTrans)
        );
    }
}
