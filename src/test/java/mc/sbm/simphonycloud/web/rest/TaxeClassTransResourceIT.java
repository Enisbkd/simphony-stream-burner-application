package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.TaxeClassTransAsserts.*;
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
import mc.sbm.simphonycloud.domain.TaxeClassTrans;
import mc.sbm.simphonycloud.repository.TaxeClassTransRepository;
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
 * Integration tests for the {@link TaxeClassTransResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaxeClassTransResourceIT {

    private static final String DEFAULT_ORG_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORG_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOC_REF = "AAAAAAAAAA";
    private static final String UPDATED_LOC_REF = "BBBBBBBBBB";

    private static final Integer DEFAULT_RVC_REF = 1;
    private static final Integer UPDATED_RVC_REF = 2;

    private static final Integer DEFAULT_TAX_CLASS_ID = 1;
    private static final Integer UPDATED_TAX_CLASS_ID = 2;

    private static final String DEFAULT_ACTIVE_TAX_RATE_REFS = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVE_TAX_RATE_REFS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/taxe-class-trans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaxeClassTransRepository taxeClassTransRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaxeClassTransMockMvc;

    private TaxeClassTrans taxeClassTrans;

    private TaxeClassTrans insertedTaxeClassTrans;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxeClassTrans createEntity() {
        return new TaxeClassTrans()
            .orgShortName(DEFAULT_ORG_SHORT_NAME)
            .locRef(DEFAULT_LOC_REF)
            .rvcRef(DEFAULT_RVC_REF)
            .taxClassId(DEFAULT_TAX_CLASS_ID)
            .activeTaxRateRefs(DEFAULT_ACTIVE_TAX_RATE_REFS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxeClassTrans createUpdatedEntity() {
        return new TaxeClassTrans()
            .orgShortName(UPDATED_ORG_SHORT_NAME)
            .locRef(UPDATED_LOC_REF)
            .rvcRef(UPDATED_RVC_REF)
            .taxClassId(UPDATED_TAX_CLASS_ID)
            .activeTaxRateRefs(UPDATED_ACTIVE_TAX_RATE_REFS);
    }

    @BeforeEach
    void initTest() {
        taxeClassTrans = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedTaxeClassTrans != null) {
            taxeClassTransRepository.delete(insertedTaxeClassTrans);
            insertedTaxeClassTrans = null;
        }
    }

    @Test
    @Transactional
    void createTaxeClassTrans() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TaxeClassTrans
        var returnedTaxeClassTrans = om.readValue(
            restTaxeClassTransMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taxeClassTrans)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TaxeClassTrans.class
        );

        // Validate the TaxeClassTrans in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTaxeClassTransUpdatableFieldsEquals(returnedTaxeClassTrans, getPersistedTaxeClassTrans(returnedTaxeClassTrans));

        insertedTaxeClassTrans = returnedTaxeClassTrans;
    }

    @Test
    @Transactional
    void createTaxeClassTransWithExistingId() throws Exception {
        // Create the TaxeClassTrans with an existing ID
        taxeClassTrans.setId(1);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaxeClassTransMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taxeClassTrans)))
            .andExpect(status().isBadRequest());

        // Validate the TaxeClassTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTaxeClassTrans() throws Exception {
        // Initialize the database
        insertedTaxeClassTrans = taxeClassTransRepository.saveAndFlush(taxeClassTrans);

        // Get all the taxeClassTransList
        restTaxeClassTransMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxeClassTrans.getId().intValue())))
            .andExpect(jsonPath("$.[*].orgShortName").value(hasItem(DEFAULT_ORG_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].locRef").value(hasItem(DEFAULT_LOC_REF)))
            .andExpect(jsonPath("$.[*].rvcRef").value(hasItem(DEFAULT_RVC_REF)))
            .andExpect(jsonPath("$.[*].taxClassId").value(hasItem(DEFAULT_TAX_CLASS_ID)))
            .andExpect(jsonPath("$.[*].activeTaxRateRefs").value(hasItem(DEFAULT_ACTIVE_TAX_RATE_REFS)));
    }

    @Test
    @Transactional
    void getTaxeClassTrans() throws Exception {
        // Initialize the database
        insertedTaxeClassTrans = taxeClassTransRepository.saveAndFlush(taxeClassTrans);

        // Get the taxeClassTrans
        restTaxeClassTransMockMvc
            .perform(get(ENTITY_API_URL_ID, taxeClassTrans.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taxeClassTrans.getId().intValue()))
            .andExpect(jsonPath("$.orgShortName").value(DEFAULT_ORG_SHORT_NAME))
            .andExpect(jsonPath("$.locRef").value(DEFAULT_LOC_REF))
            .andExpect(jsonPath("$.rvcRef").value(DEFAULT_RVC_REF))
            .andExpect(jsonPath("$.taxClassId").value(DEFAULT_TAX_CLASS_ID))
            .andExpect(jsonPath("$.activeTaxRateRefs").value(DEFAULT_ACTIVE_TAX_RATE_REFS));
    }

    @Test
    @Transactional
    void getNonExistingTaxeClassTrans() throws Exception {
        // Get the taxeClassTrans
        restTaxeClassTransMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTaxeClassTrans() throws Exception {
        // Initialize the database
        insertedTaxeClassTrans = taxeClassTransRepository.saveAndFlush(taxeClassTrans);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the taxeClassTrans
        TaxeClassTrans updatedTaxeClassTrans = taxeClassTransRepository.findById(taxeClassTrans.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTaxeClassTrans are not directly saved in db
        em.detach(updatedTaxeClassTrans);
        updatedTaxeClassTrans
            .orgShortName(UPDATED_ORG_SHORT_NAME)
            .locRef(UPDATED_LOC_REF)
            .rvcRef(UPDATED_RVC_REF)
            .taxClassId(UPDATED_TAX_CLASS_ID)
            .activeTaxRateRefs(UPDATED_ACTIVE_TAX_RATE_REFS);

        restTaxeClassTransMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTaxeClassTrans.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTaxeClassTrans))
            )
            .andExpect(status().isOk());

        // Validate the TaxeClassTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTaxeClassTransToMatchAllProperties(updatedTaxeClassTrans);
    }

    @Test
    @Transactional
    void putNonExistingTaxeClassTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxeClassTrans.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxeClassTransMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taxeClassTrans.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(taxeClassTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxeClassTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaxeClassTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxeClassTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxeClassTransMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(taxeClassTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxeClassTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaxeClassTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxeClassTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxeClassTransMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taxeClassTrans)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaxeClassTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaxeClassTransWithPatch() throws Exception {
        // Initialize the database
        insertedTaxeClassTrans = taxeClassTransRepository.saveAndFlush(taxeClassTrans);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the taxeClassTrans using partial update
        TaxeClassTrans partialUpdatedTaxeClassTrans = new TaxeClassTrans();
        partialUpdatedTaxeClassTrans.setId(taxeClassTrans.getId());

        partialUpdatedTaxeClassTrans.orgShortName(UPDATED_ORG_SHORT_NAME).locRef(UPDATED_LOC_REF).taxClassId(UPDATED_TAX_CLASS_ID);

        restTaxeClassTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaxeClassTrans.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTaxeClassTrans))
            )
            .andExpect(status().isOk());

        // Validate the TaxeClassTrans in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTaxeClassTransUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTaxeClassTrans, taxeClassTrans),
            getPersistedTaxeClassTrans(taxeClassTrans)
        );
    }

    @Test
    @Transactional
    void fullUpdateTaxeClassTransWithPatch() throws Exception {
        // Initialize the database
        insertedTaxeClassTrans = taxeClassTransRepository.saveAndFlush(taxeClassTrans);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the taxeClassTrans using partial update
        TaxeClassTrans partialUpdatedTaxeClassTrans = new TaxeClassTrans();
        partialUpdatedTaxeClassTrans.setId(taxeClassTrans.getId());

        partialUpdatedTaxeClassTrans
            .orgShortName(UPDATED_ORG_SHORT_NAME)
            .locRef(UPDATED_LOC_REF)
            .rvcRef(UPDATED_RVC_REF)
            .taxClassId(UPDATED_TAX_CLASS_ID)
            .activeTaxRateRefs(UPDATED_ACTIVE_TAX_RATE_REFS);

        restTaxeClassTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaxeClassTrans.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTaxeClassTrans))
            )
            .andExpect(status().isOk());

        // Validate the TaxeClassTrans in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTaxeClassTransUpdatableFieldsEquals(partialUpdatedTaxeClassTrans, getPersistedTaxeClassTrans(partialUpdatedTaxeClassTrans));
    }

    @Test
    @Transactional
    void patchNonExistingTaxeClassTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxeClassTrans.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxeClassTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taxeClassTrans.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(taxeClassTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxeClassTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaxeClassTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxeClassTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxeClassTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(taxeClassTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxeClassTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaxeClassTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxeClassTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxeClassTransMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(taxeClassTrans)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaxeClassTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaxeClassTrans() throws Exception {
        // Initialize the database
        insertedTaxeClassTrans = taxeClassTransRepository.saveAndFlush(taxeClassTrans);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the taxeClassTrans
        restTaxeClassTransMockMvc
            .perform(delete(ENTITY_API_URL_ID, taxeClassTrans.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return taxeClassTransRepository.count();
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

    protected TaxeClassTrans getPersistedTaxeClassTrans(TaxeClassTrans taxeClassTrans) {
        return taxeClassTransRepository.findById(taxeClassTrans.getId()).orElseThrow();
    }

    protected void assertPersistedTaxeClassTransToMatchAllProperties(TaxeClassTrans expectedTaxeClassTrans) {
        assertTaxeClassTransAllPropertiesEquals(expectedTaxeClassTrans, getPersistedTaxeClassTrans(expectedTaxeClassTrans));
    }

    protected void assertPersistedTaxeClassTransToMatchUpdatableProperties(TaxeClassTrans expectedTaxeClassTrans) {
        assertTaxeClassTransAllUpdatablePropertiesEquals(expectedTaxeClassTrans, getPersistedTaxeClassTrans(expectedTaxeClassTrans));
    }
}
