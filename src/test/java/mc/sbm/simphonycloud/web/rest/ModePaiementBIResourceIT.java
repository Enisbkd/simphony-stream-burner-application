package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.ModePaiementBIAsserts.*;
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
import mc.sbm.simphonycloud.domain.ModePaiementBI;
import mc.sbm.simphonycloud.repository.ModePaiementBIRepository;
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
 * Integration tests for the {@link ModePaiementBIResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ModePaiementBIResourceIT {

    private static final String DEFAULT_LOC_REF = "AAAAAAAAAA";
    private static final String UPDATED_LOC_REF = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUM = 1;
    private static final Integer UPDATED_NUM = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final String ENTITY_API_URL = "/api/mode-paiement-bis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ModePaiementBIRepository modePaiementBIRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restModePaiementBIMockMvc;

    private ModePaiementBI modePaiementBI;

    private ModePaiementBI insertedModePaiementBI;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModePaiementBI createEntity() {
        return new ModePaiementBI().locRef(DEFAULT_LOC_REF).num(DEFAULT_NUM).name(DEFAULT_NAME).type(DEFAULT_TYPE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModePaiementBI createUpdatedEntity() {
        return new ModePaiementBI().locRef(UPDATED_LOC_REF).num(UPDATED_NUM).name(UPDATED_NAME).type(UPDATED_TYPE);
    }

    @BeforeEach
    void initTest() {
        modePaiementBI = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedModePaiementBI != null) {
            modePaiementBIRepository.delete(insertedModePaiementBI);
            insertedModePaiementBI = null;
        }
    }

    @Test
    @Transactional
    void createModePaiementBI() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ModePaiementBI
        var returnedModePaiementBI = om.readValue(
            restModePaiementBIMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(modePaiementBI)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ModePaiementBI.class
        );

        // Validate the ModePaiementBI in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertModePaiementBIUpdatableFieldsEquals(returnedModePaiementBI, getPersistedModePaiementBI(returnedModePaiementBI));

        insertedModePaiementBI = returnedModePaiementBI;
    }

    @Test
    @Transactional
    void createModePaiementBIWithExistingId() throws Exception {
        // Create the ModePaiementBI with an existing ID
        modePaiementBI.setId(1);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restModePaiementBIMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(modePaiementBI)))
            .andExpect(status().isBadRequest());

        // Validate the ModePaiementBI in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllModePaiementBIS() throws Exception {
        // Initialize the database
        insertedModePaiementBI = modePaiementBIRepository.saveAndFlush(modePaiementBI);

        // Get all the modePaiementBIList
        restModePaiementBIMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modePaiementBI.getId().intValue())))
            .andExpect(jsonPath("$.[*].locRef").value(hasItem(DEFAULT_LOC_REF)))
            .andExpect(jsonPath("$.[*].num").value(hasItem(DEFAULT_NUM)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getModePaiementBI() throws Exception {
        // Initialize the database
        insertedModePaiementBI = modePaiementBIRepository.saveAndFlush(modePaiementBI);

        // Get the modePaiementBI
        restModePaiementBIMockMvc
            .perform(get(ENTITY_API_URL_ID, modePaiementBI.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(modePaiementBI.getId().intValue()))
            .andExpect(jsonPath("$.locRef").value(DEFAULT_LOC_REF))
            .andExpect(jsonPath("$.num").value(DEFAULT_NUM))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingModePaiementBI() throws Exception {
        // Get the modePaiementBI
        restModePaiementBIMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingModePaiementBI() throws Exception {
        // Initialize the database
        insertedModePaiementBI = modePaiementBIRepository.saveAndFlush(modePaiementBI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the modePaiementBI
        ModePaiementBI updatedModePaiementBI = modePaiementBIRepository.findById(modePaiementBI.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedModePaiementBI are not directly saved in db
        em.detach(updatedModePaiementBI);
        updatedModePaiementBI.locRef(UPDATED_LOC_REF).num(UPDATED_NUM).name(UPDATED_NAME).type(UPDATED_TYPE);

        restModePaiementBIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedModePaiementBI.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedModePaiementBI))
            )
            .andExpect(status().isOk());

        // Validate the ModePaiementBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedModePaiementBIToMatchAllProperties(updatedModePaiementBI);
    }

    @Test
    @Transactional
    void putNonExistingModePaiementBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        modePaiementBI.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModePaiementBIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, modePaiementBI.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(modePaiementBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModePaiementBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchModePaiementBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        modePaiementBI.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModePaiementBIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(modePaiementBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModePaiementBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamModePaiementBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        modePaiementBI.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModePaiementBIMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(modePaiementBI)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ModePaiementBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateModePaiementBIWithPatch() throws Exception {
        // Initialize the database
        insertedModePaiementBI = modePaiementBIRepository.saveAndFlush(modePaiementBI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the modePaiementBI using partial update
        ModePaiementBI partialUpdatedModePaiementBI = new ModePaiementBI();
        partialUpdatedModePaiementBI.setId(modePaiementBI.getId());

        partialUpdatedModePaiementBI.locRef(UPDATED_LOC_REF).num(UPDATED_NUM).name(UPDATED_NAME).type(UPDATED_TYPE);

        restModePaiementBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModePaiementBI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedModePaiementBI))
            )
            .andExpect(status().isOk());

        // Validate the ModePaiementBI in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertModePaiementBIUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedModePaiementBI, modePaiementBI),
            getPersistedModePaiementBI(modePaiementBI)
        );
    }

    @Test
    @Transactional
    void fullUpdateModePaiementBIWithPatch() throws Exception {
        // Initialize the database
        insertedModePaiementBI = modePaiementBIRepository.saveAndFlush(modePaiementBI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the modePaiementBI using partial update
        ModePaiementBI partialUpdatedModePaiementBI = new ModePaiementBI();
        partialUpdatedModePaiementBI.setId(modePaiementBI.getId());

        partialUpdatedModePaiementBI.locRef(UPDATED_LOC_REF).num(UPDATED_NUM).name(UPDATED_NAME).type(UPDATED_TYPE);

        restModePaiementBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModePaiementBI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedModePaiementBI))
            )
            .andExpect(status().isOk());

        // Validate the ModePaiementBI in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertModePaiementBIUpdatableFieldsEquals(partialUpdatedModePaiementBI, getPersistedModePaiementBI(partialUpdatedModePaiementBI));
    }

    @Test
    @Transactional
    void patchNonExistingModePaiementBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        modePaiementBI.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModePaiementBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, modePaiementBI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(modePaiementBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModePaiementBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchModePaiementBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        modePaiementBI.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModePaiementBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(modePaiementBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModePaiementBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamModePaiementBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        modePaiementBI.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModePaiementBIMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(modePaiementBI)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ModePaiementBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteModePaiementBI() throws Exception {
        // Initialize the database
        insertedModePaiementBI = modePaiementBIRepository.saveAndFlush(modePaiementBI);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the modePaiementBI
        restModePaiementBIMockMvc
            .perform(delete(ENTITY_API_URL_ID, modePaiementBI.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return modePaiementBIRepository.count();
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

    protected ModePaiementBI getPersistedModePaiementBI(ModePaiementBI modePaiementBI) {
        return modePaiementBIRepository.findById(modePaiementBI.getId()).orElseThrow();
    }

    protected void assertPersistedModePaiementBIToMatchAllProperties(ModePaiementBI expectedModePaiementBI) {
        assertModePaiementBIAllPropertiesEquals(expectedModePaiementBI, getPersistedModePaiementBI(expectedModePaiementBI));
    }

    protected void assertPersistedModePaiementBIToMatchUpdatableProperties(ModePaiementBI expectedModePaiementBI) {
        assertModePaiementBIAllUpdatablePropertiesEquals(expectedModePaiementBI, getPersistedModePaiementBI(expectedModePaiementBI));
    }
}
