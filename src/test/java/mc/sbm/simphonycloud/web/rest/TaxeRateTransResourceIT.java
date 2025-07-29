package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.TaxeRateTransAsserts.*;
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
import mc.sbm.simphonycloud.domain.TaxeRateTrans;
import mc.sbm.simphonycloud.repository.TaxeRateTransRepository;
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
 * Integration tests for the {@link TaxeRateTransResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaxeRateTransResourceIT {

    private static final String DEFAULT_ORG_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORG_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOC_REF = "AAAAAAAAAA";
    private static final String UPDATED_LOC_REF = "BBBBBBBBBB";

    private static final Integer DEFAULT_RVC_REF = 1;
    private static final Integer UPDATED_RVC_REF = 2;

    private static final Integer DEFAULT_TAX_RATE_ID = 1;
    private static final Integer UPDATED_TAX_RATE_ID = 2;

    private static final Double DEFAULT_PERCENTAGE = 1D;
    private static final Double UPDATED_PERCENTAGE = 2D;

    private static final String DEFAULT_TAX_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TAX_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_FR = "AAAAAAAAAA";
    private static final String UPDATED_NAME_FR = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_NAME_EN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/taxe-rate-trans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaxeRateTransRepository taxeRateTransRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaxeRateTransMockMvc;

    private TaxeRateTrans taxeRateTrans;

    private TaxeRateTrans insertedTaxeRateTrans;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxeRateTrans createEntity() {
        return new TaxeRateTrans()
            .orgShortName(DEFAULT_ORG_SHORT_NAME)
            .locRef(DEFAULT_LOC_REF)
            .rvcRef(DEFAULT_RVC_REF)
            .taxRateId(DEFAULT_TAX_RATE_ID)
            .percentage(DEFAULT_PERCENTAGE)
            .taxType(DEFAULT_TAX_TYPE)
            .nameFR(DEFAULT_NAME_FR)
            .nameEN(DEFAULT_NAME_EN);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxeRateTrans createUpdatedEntity() {
        return new TaxeRateTrans()
            .orgShortName(UPDATED_ORG_SHORT_NAME)
            .locRef(UPDATED_LOC_REF)
            .rvcRef(UPDATED_RVC_REF)
            .taxRateId(UPDATED_TAX_RATE_ID)
            .percentage(UPDATED_PERCENTAGE)
            .taxType(UPDATED_TAX_TYPE)
            .nameFR(UPDATED_NAME_FR)
            .nameEN(UPDATED_NAME_EN);
    }

    @BeforeEach
    void initTest() {
        taxeRateTrans = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedTaxeRateTrans != null) {
            taxeRateTransRepository.delete(insertedTaxeRateTrans);
            insertedTaxeRateTrans = null;
        }
    }

    @Test
    @Transactional
    void createTaxeRateTrans() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TaxeRateTrans
        var returnedTaxeRateTrans = om.readValue(
            restTaxeRateTransMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taxeRateTrans)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TaxeRateTrans.class
        );

        // Validate the TaxeRateTrans in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTaxeRateTransUpdatableFieldsEquals(returnedTaxeRateTrans, getPersistedTaxeRateTrans(returnedTaxeRateTrans));

        insertedTaxeRateTrans = returnedTaxeRateTrans;
    }

    @Test
    @Transactional
    void createTaxeRateTransWithExistingId() throws Exception {
        // Create the TaxeRateTrans with an existing ID
        taxeRateTrans.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaxeRateTransMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taxeRateTrans)))
            .andExpect(status().isBadRequest());

        // Validate the TaxeRateTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTaxeRateTrans() throws Exception {
        // Initialize the database
        insertedTaxeRateTrans = taxeRateTransRepository.saveAndFlush(taxeRateTrans);

        // Get all the taxeRateTransList
        restTaxeRateTransMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxeRateTrans.getId().intValue())))
            .andExpect(jsonPath("$.[*].orgShortName").value(hasItem(DEFAULT_ORG_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].locRef").value(hasItem(DEFAULT_LOC_REF)))
            .andExpect(jsonPath("$.[*].rvcRef").value(hasItem(DEFAULT_RVC_REF)))
            .andExpect(jsonPath("$.[*].taxRateId").value(hasItem(DEFAULT_TAX_RATE_ID)))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE)))
            .andExpect(jsonPath("$.[*].taxType").value(hasItem(DEFAULT_TAX_TYPE)))
            .andExpect(jsonPath("$.[*].nameFR").value(hasItem(DEFAULT_NAME_FR)))
            .andExpect(jsonPath("$.[*].nameEN").value(hasItem(DEFAULT_NAME_EN)));
    }

    @Test
    @Transactional
    void getTaxeRateTrans() throws Exception {
        // Initialize the database
        insertedTaxeRateTrans = taxeRateTransRepository.saveAndFlush(taxeRateTrans);

        // Get the taxeRateTrans
        restTaxeRateTransMockMvc
            .perform(get(ENTITY_API_URL_ID, taxeRateTrans.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taxeRateTrans.getId().intValue()))
            .andExpect(jsonPath("$.orgShortName").value(DEFAULT_ORG_SHORT_NAME))
            .andExpect(jsonPath("$.locRef").value(DEFAULT_LOC_REF))
            .andExpect(jsonPath("$.rvcRef").value(DEFAULT_RVC_REF))
            .andExpect(jsonPath("$.taxRateId").value(DEFAULT_TAX_RATE_ID))
            .andExpect(jsonPath("$.percentage").value(DEFAULT_PERCENTAGE))
            .andExpect(jsonPath("$.taxType").value(DEFAULT_TAX_TYPE))
            .andExpect(jsonPath("$.nameFR").value(DEFAULT_NAME_FR))
            .andExpect(jsonPath("$.nameEN").value(DEFAULT_NAME_EN));
    }

    @Test
    @Transactional
    void getNonExistingTaxeRateTrans() throws Exception {
        // Get the taxeRateTrans
        restTaxeRateTransMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTaxeRateTrans() throws Exception {
        // Initialize the database
        insertedTaxeRateTrans = taxeRateTransRepository.saveAndFlush(taxeRateTrans);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the taxeRateTrans
        TaxeRateTrans updatedTaxeRateTrans = taxeRateTransRepository.findById(taxeRateTrans.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTaxeRateTrans are not directly saved in db
        em.detach(updatedTaxeRateTrans);
        updatedTaxeRateTrans
            .orgShortName(UPDATED_ORG_SHORT_NAME)
            .locRef(UPDATED_LOC_REF)
            .rvcRef(UPDATED_RVC_REF)
            .taxRateId(UPDATED_TAX_RATE_ID)
            .percentage(UPDATED_PERCENTAGE)
            .taxType(UPDATED_TAX_TYPE)
            .nameFR(UPDATED_NAME_FR)
            .nameEN(UPDATED_NAME_EN);

        restTaxeRateTransMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTaxeRateTrans.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTaxeRateTrans))
            )
            .andExpect(status().isOk());

        // Validate the TaxeRateTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTaxeRateTransToMatchAllProperties(updatedTaxeRateTrans);
    }

    @Test
    @Transactional
    void putNonExistingTaxeRateTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxeRateTrans.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxeRateTransMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taxeRateTrans.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(taxeRateTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxeRateTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaxeRateTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxeRateTrans.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxeRateTransMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(taxeRateTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxeRateTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaxeRateTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxeRateTrans.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxeRateTransMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taxeRateTrans)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaxeRateTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaxeRateTransWithPatch() throws Exception {
        // Initialize the database
        insertedTaxeRateTrans = taxeRateTransRepository.saveAndFlush(taxeRateTrans);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the taxeRateTrans using partial update
        TaxeRateTrans partialUpdatedTaxeRateTrans = new TaxeRateTrans();
        partialUpdatedTaxeRateTrans.setId(taxeRateTrans.getId());

        partialUpdatedTaxeRateTrans
            .orgShortName(UPDATED_ORG_SHORT_NAME)
            .locRef(UPDATED_LOC_REF)
            .percentage(UPDATED_PERCENTAGE)
            .nameFR(UPDATED_NAME_FR)
            .nameEN(UPDATED_NAME_EN);

        restTaxeRateTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaxeRateTrans.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTaxeRateTrans))
            )
            .andExpect(status().isOk());

        // Validate the TaxeRateTrans in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTaxeRateTransUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTaxeRateTrans, taxeRateTrans),
            getPersistedTaxeRateTrans(taxeRateTrans)
        );
    }

    @Test
    @Transactional
    void fullUpdateTaxeRateTransWithPatch() throws Exception {
        // Initialize the database
        insertedTaxeRateTrans = taxeRateTransRepository.saveAndFlush(taxeRateTrans);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the taxeRateTrans using partial update
        TaxeRateTrans partialUpdatedTaxeRateTrans = new TaxeRateTrans();
        partialUpdatedTaxeRateTrans.setId(taxeRateTrans.getId());

        partialUpdatedTaxeRateTrans
            .orgShortName(UPDATED_ORG_SHORT_NAME)
            .locRef(UPDATED_LOC_REF)
            .rvcRef(UPDATED_RVC_REF)
            .taxRateId(UPDATED_TAX_RATE_ID)
            .percentage(UPDATED_PERCENTAGE)
            .taxType(UPDATED_TAX_TYPE)
            .nameFR(UPDATED_NAME_FR)
            .nameEN(UPDATED_NAME_EN);

        restTaxeRateTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaxeRateTrans.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTaxeRateTrans))
            )
            .andExpect(status().isOk());

        // Validate the TaxeRateTrans in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTaxeRateTransUpdatableFieldsEquals(partialUpdatedTaxeRateTrans, getPersistedTaxeRateTrans(partialUpdatedTaxeRateTrans));
    }

    @Test
    @Transactional
    void patchNonExistingTaxeRateTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxeRateTrans.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxeRateTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taxeRateTrans.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(taxeRateTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxeRateTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaxeRateTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxeRateTrans.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxeRateTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(taxeRateTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxeRateTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaxeRateTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxeRateTrans.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxeRateTransMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(taxeRateTrans)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaxeRateTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaxeRateTrans() throws Exception {
        // Initialize the database
        insertedTaxeRateTrans = taxeRateTransRepository.saveAndFlush(taxeRateTrans);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the taxeRateTrans
        restTaxeRateTransMockMvc
            .perform(delete(ENTITY_API_URL_ID, taxeRateTrans.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return taxeRateTransRepository.count();
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

    protected TaxeRateTrans getPersistedTaxeRateTrans(TaxeRateTrans taxeRateTrans) {
        return taxeRateTransRepository.findById(taxeRateTrans.getId()).orElseThrow();
    }

    protected void assertPersistedTaxeRateTransToMatchAllProperties(TaxeRateTrans expectedTaxeRateTrans) {
        assertTaxeRateTransAllPropertiesEquals(expectedTaxeRateTrans, getPersistedTaxeRateTrans(expectedTaxeRateTrans));
    }

    protected void assertPersistedTaxeRateTransToMatchUpdatableProperties(TaxeRateTrans expectedTaxeRateTrans) {
        assertTaxeRateTransAllUpdatablePropertiesEquals(expectedTaxeRateTrans, getPersistedTaxeRateTrans(expectedTaxeRateTrans));
    }
}
