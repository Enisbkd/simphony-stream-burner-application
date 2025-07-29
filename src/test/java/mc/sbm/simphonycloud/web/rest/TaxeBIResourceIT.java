package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.TaxeBIAsserts.*;
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
import mc.sbm.simphonycloud.domain.TaxeBI;
import mc.sbm.simphonycloud.repository.TaxeBIRepository;
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
 * Integration tests for the {@link TaxeBIResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaxeBIResourceIT {

    private static final String DEFAULT_LOC_REF = "AAAAAAAAAA";
    private static final String UPDATED_LOC_REF = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUM = 1;
    private static final Integer UPDATED_NUM = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final String ENTITY_API_URL = "/api/taxe-bis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaxeBIRepository taxeBIRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaxeBIMockMvc;

    private TaxeBI taxeBI;

    private TaxeBI insertedTaxeBI;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxeBI createEntity() {
        return new TaxeBI().locRef(DEFAULT_LOC_REF).num(DEFAULT_NUM).name(DEFAULT_NAME).type(DEFAULT_TYPE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxeBI createUpdatedEntity() {
        return new TaxeBI().locRef(UPDATED_LOC_REF).num(UPDATED_NUM).name(UPDATED_NAME).type(UPDATED_TYPE);
    }

    @BeforeEach
    void initTest() {
        taxeBI = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedTaxeBI != null) {
            taxeBIRepository.delete(insertedTaxeBI);
            insertedTaxeBI = null;
        }
    }

    @Test
    @Transactional
    void createTaxeBI() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TaxeBI
        var returnedTaxeBI = om.readValue(
            restTaxeBIMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taxeBI)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TaxeBI.class
        );

        // Validate the TaxeBI in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTaxeBIUpdatableFieldsEquals(returnedTaxeBI, getPersistedTaxeBI(returnedTaxeBI));

        insertedTaxeBI = returnedTaxeBI;
    }

    @Test
    @Transactional
    void createTaxeBIWithExistingId() throws Exception {
        // Create the TaxeBI with an existing ID
        taxeBI.setId(1);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaxeBIMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taxeBI)))
            .andExpect(status().isBadRequest());

        // Validate the TaxeBI in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTaxeBIS() throws Exception {
        // Initialize the database
        insertedTaxeBI = taxeBIRepository.saveAndFlush(taxeBI);

        // Get all the taxeBIList
        restTaxeBIMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxeBI.getId().intValue())))
            .andExpect(jsonPath("$.[*].locRef").value(hasItem(DEFAULT_LOC_REF)))
            .andExpect(jsonPath("$.[*].num").value(hasItem(DEFAULT_NUM)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getTaxeBI() throws Exception {
        // Initialize the database
        insertedTaxeBI = taxeBIRepository.saveAndFlush(taxeBI);

        // Get the taxeBI
        restTaxeBIMockMvc
            .perform(get(ENTITY_API_URL_ID, taxeBI.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taxeBI.getId().intValue()))
            .andExpect(jsonPath("$.locRef").value(DEFAULT_LOC_REF))
            .andExpect(jsonPath("$.num").value(DEFAULT_NUM))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingTaxeBI() throws Exception {
        // Get the taxeBI
        restTaxeBIMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTaxeBI() throws Exception {
        // Initialize the database
        insertedTaxeBI = taxeBIRepository.saveAndFlush(taxeBI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the taxeBI
        TaxeBI updatedTaxeBI = taxeBIRepository.findById(taxeBI.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTaxeBI are not directly saved in db
        em.detach(updatedTaxeBI);
        updatedTaxeBI.locRef(UPDATED_LOC_REF).num(UPDATED_NUM).name(UPDATED_NAME).type(UPDATED_TYPE);

        restTaxeBIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTaxeBI.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTaxeBI))
            )
            .andExpect(status().isOk());

        // Validate the TaxeBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTaxeBIToMatchAllProperties(updatedTaxeBI);
    }

    @Test
    @Transactional
    void putNonExistingTaxeBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxeBI.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxeBIMockMvc
            .perform(put(ENTITY_API_URL_ID, taxeBI.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taxeBI)))
            .andExpect(status().isBadRequest());

        // Validate the TaxeBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaxeBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxeBI.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxeBIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(taxeBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxeBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaxeBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxeBI.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxeBIMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taxeBI)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaxeBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaxeBIWithPatch() throws Exception {
        // Initialize the database
        insertedTaxeBI = taxeBIRepository.saveAndFlush(taxeBI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the taxeBI using partial update
        TaxeBI partialUpdatedTaxeBI = new TaxeBI();
        partialUpdatedTaxeBI.setId(taxeBI.getId());

        partialUpdatedTaxeBI.locRef(UPDATED_LOC_REF).num(UPDATED_NUM);

        restTaxeBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaxeBI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTaxeBI))
            )
            .andExpect(status().isOk());

        // Validate the TaxeBI in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTaxeBIUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTaxeBI, taxeBI), getPersistedTaxeBI(taxeBI));
    }

    @Test
    @Transactional
    void fullUpdateTaxeBIWithPatch() throws Exception {
        // Initialize the database
        insertedTaxeBI = taxeBIRepository.saveAndFlush(taxeBI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the taxeBI using partial update
        TaxeBI partialUpdatedTaxeBI = new TaxeBI();
        partialUpdatedTaxeBI.setId(taxeBI.getId());

        partialUpdatedTaxeBI.locRef(UPDATED_LOC_REF).num(UPDATED_NUM).name(UPDATED_NAME).type(UPDATED_TYPE);

        restTaxeBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaxeBI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTaxeBI))
            )
            .andExpect(status().isOk());

        // Validate the TaxeBI in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTaxeBIUpdatableFieldsEquals(partialUpdatedTaxeBI, getPersistedTaxeBI(partialUpdatedTaxeBI));
    }

    @Test
    @Transactional
    void patchNonExistingTaxeBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxeBI.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxeBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taxeBI.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(taxeBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxeBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaxeBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxeBI.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxeBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(taxeBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxeBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaxeBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxeBI.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxeBIMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(taxeBI)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaxeBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaxeBI() throws Exception {
        // Initialize the database
        insertedTaxeBI = taxeBIRepository.saveAndFlush(taxeBI);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the taxeBI
        restTaxeBIMockMvc
            .perform(delete(ENTITY_API_URL_ID, taxeBI.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return taxeBIRepository.count();
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

    protected TaxeBI getPersistedTaxeBI(TaxeBI taxeBI) {
        return taxeBIRepository.findById(taxeBI.getId()).orElseThrow();
    }

    protected void assertPersistedTaxeBIToMatchAllProperties(TaxeBI expectedTaxeBI) {
        assertTaxeBIAllPropertiesEquals(expectedTaxeBI, getPersistedTaxeBI(expectedTaxeBI));
    }

    protected void assertPersistedTaxeBIToMatchUpdatableProperties(TaxeBI expectedTaxeBI) {
        assertTaxeBIAllUpdatablePropertiesEquals(expectedTaxeBI, getPersistedTaxeBI(expectedTaxeBI));
    }
}
