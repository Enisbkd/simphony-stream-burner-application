package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.CommissionServiceBIAsserts.*;
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
import mc.sbm.simphonycloud.domain.CommissionServiceBI;
import mc.sbm.simphonycloud.repository.CommissionServiceBIRepository;
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
 * Integration tests for the {@link CommissionServiceBIResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CommissionServiceBIResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_COURT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_COURT = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_VALUE = "BBBBBBBBBB";

    private static final Float DEFAULT_VALUE = 1F;
    private static final Float UPDATED_VALUE = 2F;

    private static final String DEFAULT_ETABLISSEMENT_REF = "AAAAAAAAAA";
    private static final String UPDATED_ETABLISSEMENT_REF = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/commission-service-bis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CommissionServiceBIRepository commissionServiceBIRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommissionServiceBIMockMvc;

    private CommissionServiceBI commissionServiceBI;

    private CommissionServiceBI insertedCommissionServiceBI;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommissionServiceBI createEntity() {
        return new CommissionServiceBI()
            .nom(DEFAULT_NOM)
            .nomCourt(DEFAULT_NOM_COURT)
            .typeValue(DEFAULT_TYPE_VALUE)
            .value(DEFAULT_VALUE)
            .etablissementRef(DEFAULT_ETABLISSEMENT_REF);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommissionServiceBI createUpdatedEntity() {
        return new CommissionServiceBI()
            .nom(UPDATED_NOM)
            .nomCourt(UPDATED_NOM_COURT)
            .typeValue(UPDATED_TYPE_VALUE)
            .value(UPDATED_VALUE)
            .etablissementRef(UPDATED_ETABLISSEMENT_REF);
    }

    @BeforeEach
    void initTest() {
        commissionServiceBI = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedCommissionServiceBI != null) {
            commissionServiceBIRepository.delete(insertedCommissionServiceBI);
            insertedCommissionServiceBI = null;
        }
    }

    @Test
    @Transactional
    void createCommissionServiceBI() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CommissionServiceBI
        var returnedCommissionServiceBI = om.readValue(
            restCommissionServiceBIMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(commissionServiceBI)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CommissionServiceBI.class
        );

        // Validate the CommissionServiceBI in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCommissionServiceBIUpdatableFieldsEquals(
            returnedCommissionServiceBI,
            getPersistedCommissionServiceBI(returnedCommissionServiceBI)
        );

        insertedCommissionServiceBI = returnedCommissionServiceBI;
    }

    @Test
    @Transactional
    void createCommissionServiceBIWithExistingId() throws Exception {
        // Create the CommissionServiceBI with an existing ID
        commissionServiceBI.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommissionServiceBIMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(commissionServiceBI)))
            .andExpect(status().isBadRequest());

        // Validate the CommissionServiceBI in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEtablissementRefIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        commissionServiceBI.setEtablissementRef(null);

        // Create the CommissionServiceBI, which fails.

        restCommissionServiceBIMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(commissionServiceBI)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCommissionServiceBIS() throws Exception {
        // Initialize the database
        insertedCommissionServiceBI = commissionServiceBIRepository.saveAndFlush(commissionServiceBI);

        // Get all the commissionServiceBIList
        restCommissionServiceBIMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commissionServiceBI.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].nomCourt").value(hasItem(DEFAULT_NOM_COURT)))
            .andExpect(jsonPath("$.[*].typeValue").value(hasItem(DEFAULT_TYPE_VALUE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].etablissementRef").value(hasItem(DEFAULT_ETABLISSEMENT_REF)));
    }

    @Test
    @Transactional
    void getCommissionServiceBI() throws Exception {
        // Initialize the database
        insertedCommissionServiceBI = commissionServiceBIRepository.saveAndFlush(commissionServiceBI);

        // Get the commissionServiceBI
        restCommissionServiceBIMockMvc
            .perform(get(ENTITY_API_URL_ID, commissionServiceBI.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commissionServiceBI.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.nomCourt").value(DEFAULT_NOM_COURT))
            .andExpect(jsonPath("$.typeValue").value(DEFAULT_TYPE_VALUE))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.etablissementRef").value(DEFAULT_ETABLISSEMENT_REF));
    }

    @Test
    @Transactional
    void getNonExistingCommissionServiceBI() throws Exception {
        // Get the commissionServiceBI
        restCommissionServiceBIMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCommissionServiceBI() throws Exception {
        // Initialize the database
        insertedCommissionServiceBI = commissionServiceBIRepository.saveAndFlush(commissionServiceBI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the commissionServiceBI
        CommissionServiceBI updatedCommissionServiceBI = commissionServiceBIRepository.findById(commissionServiceBI.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCommissionServiceBI are not directly saved in db
        em.detach(updatedCommissionServiceBI);
        updatedCommissionServiceBI
            .nom(UPDATED_NOM)
            .nomCourt(UPDATED_NOM_COURT)
            .typeValue(UPDATED_TYPE_VALUE)
            .value(UPDATED_VALUE)
            .etablissementRef(UPDATED_ETABLISSEMENT_REF);

        restCommissionServiceBIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCommissionServiceBI.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCommissionServiceBI))
            )
            .andExpect(status().isOk());

        // Validate the CommissionServiceBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCommissionServiceBIToMatchAllProperties(updatedCommissionServiceBI);
    }

    @Test
    @Transactional
    void putNonExistingCommissionServiceBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commissionServiceBI.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommissionServiceBIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commissionServiceBI.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(commissionServiceBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommissionServiceBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCommissionServiceBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commissionServiceBI.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommissionServiceBIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(commissionServiceBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommissionServiceBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCommissionServiceBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commissionServiceBI.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommissionServiceBIMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(commissionServiceBI)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CommissionServiceBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCommissionServiceBIWithPatch() throws Exception {
        // Initialize the database
        insertedCommissionServiceBI = commissionServiceBIRepository.saveAndFlush(commissionServiceBI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the commissionServiceBI using partial update
        CommissionServiceBI partialUpdatedCommissionServiceBI = new CommissionServiceBI();
        partialUpdatedCommissionServiceBI.setId(commissionServiceBI.getId());

        partialUpdatedCommissionServiceBI.value(UPDATED_VALUE);

        restCommissionServiceBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommissionServiceBI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCommissionServiceBI))
            )
            .andExpect(status().isOk());

        // Validate the CommissionServiceBI in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCommissionServiceBIUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCommissionServiceBI, commissionServiceBI),
            getPersistedCommissionServiceBI(commissionServiceBI)
        );
    }

    @Test
    @Transactional
    void fullUpdateCommissionServiceBIWithPatch() throws Exception {
        // Initialize the database
        insertedCommissionServiceBI = commissionServiceBIRepository.saveAndFlush(commissionServiceBI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the commissionServiceBI using partial update
        CommissionServiceBI partialUpdatedCommissionServiceBI = new CommissionServiceBI();
        partialUpdatedCommissionServiceBI.setId(commissionServiceBI.getId());

        partialUpdatedCommissionServiceBI
            .nom(UPDATED_NOM)
            .nomCourt(UPDATED_NOM_COURT)
            .typeValue(UPDATED_TYPE_VALUE)
            .value(UPDATED_VALUE)
            .etablissementRef(UPDATED_ETABLISSEMENT_REF);

        restCommissionServiceBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommissionServiceBI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCommissionServiceBI))
            )
            .andExpect(status().isOk());

        // Validate the CommissionServiceBI in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCommissionServiceBIUpdatableFieldsEquals(
            partialUpdatedCommissionServiceBI,
            getPersistedCommissionServiceBI(partialUpdatedCommissionServiceBI)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCommissionServiceBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commissionServiceBI.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommissionServiceBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, commissionServiceBI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(commissionServiceBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommissionServiceBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCommissionServiceBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commissionServiceBI.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommissionServiceBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(commissionServiceBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommissionServiceBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCommissionServiceBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commissionServiceBI.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommissionServiceBIMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(commissionServiceBI)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CommissionServiceBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCommissionServiceBI() throws Exception {
        // Initialize the database
        insertedCommissionServiceBI = commissionServiceBIRepository.saveAndFlush(commissionServiceBI);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the commissionServiceBI
        restCommissionServiceBIMockMvc
            .perform(delete(ENTITY_API_URL_ID, commissionServiceBI.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return commissionServiceBIRepository.count();
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

    protected CommissionServiceBI getPersistedCommissionServiceBI(CommissionServiceBI commissionServiceBI) {
        return commissionServiceBIRepository.findById(commissionServiceBI.getId()).orElseThrow();
    }

    protected void assertPersistedCommissionServiceBIToMatchAllProperties(CommissionServiceBI expectedCommissionServiceBI) {
        assertCommissionServiceBIAllPropertiesEquals(
            expectedCommissionServiceBI,
            getPersistedCommissionServiceBI(expectedCommissionServiceBI)
        );
    }

    protected void assertPersistedCommissionServiceBIToMatchUpdatableProperties(CommissionServiceBI expectedCommissionServiceBI) {
        assertCommissionServiceBIAllUpdatablePropertiesEquals(
            expectedCommissionServiceBI,
            getPersistedCommissionServiceBI(expectedCommissionServiceBI)
        );
    }
}
