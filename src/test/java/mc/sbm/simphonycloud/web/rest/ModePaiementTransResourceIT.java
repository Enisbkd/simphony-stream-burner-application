package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.ModePaiementTransAsserts.*;
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
import mc.sbm.simphonycloud.domain.ModePaiementTrans;
import mc.sbm.simphonycloud.repository.ModePaiementTransRepository;
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
 * Integration tests for the {@link ModePaiementTransResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ModePaiementTransResourceIT {

    private static final Integer DEFAULT_TENDER_ID = 1;
    private static final Integer UPDATED_TENDER_ID = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EXTENSIONS = "AAAAAAAAAA";
    private static final String UPDATED_EXTENSIONS = "BBBBBBBBBB";

    private static final String DEFAULT_ORG_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORG_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOC_REF = "AAAAAAAAAA";
    private static final String UPDATED_LOC_REF = "BBBBBBBBBB";

    private static final Integer DEFAULT_RVC_REF = 1;
    private static final Integer UPDATED_RVC_REF = 2;

    private static final String ENTITY_API_URL = "/api/mode-paiement-trans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ModePaiementTransRepository modePaiementTransRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restModePaiementTransMockMvc;

    private ModePaiementTrans modePaiementTrans;

    private ModePaiementTrans insertedModePaiementTrans;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModePaiementTrans createEntity() {
        return new ModePaiementTrans()
            .tenderId(DEFAULT_TENDER_ID)
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .extensions(DEFAULT_EXTENSIONS)
            .orgShortName(DEFAULT_ORG_SHORT_NAME)
            .locRef(DEFAULT_LOC_REF)
            .rvcRef(DEFAULT_RVC_REF);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModePaiementTrans createUpdatedEntity() {
        return new ModePaiementTrans()
            .tenderId(UPDATED_TENDER_ID)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .extensions(UPDATED_EXTENSIONS)
            .orgShortName(UPDATED_ORG_SHORT_NAME)
            .locRef(UPDATED_LOC_REF)
            .rvcRef(UPDATED_RVC_REF);
    }

    @BeforeEach
    void initTest() {
        modePaiementTrans = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedModePaiementTrans != null) {
            modePaiementTransRepository.delete(insertedModePaiementTrans);
            insertedModePaiementTrans = null;
        }
    }

    @Test
    @Transactional
    void createModePaiementTrans() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ModePaiementTrans
        var returnedModePaiementTrans = om.readValue(
            restModePaiementTransMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(modePaiementTrans)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ModePaiementTrans.class
        );

        // Validate the ModePaiementTrans in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertModePaiementTransUpdatableFieldsEquals(returnedModePaiementTrans, getPersistedModePaiementTrans(returnedModePaiementTrans));

        insertedModePaiementTrans = returnedModePaiementTrans;
    }

    @Test
    @Transactional
    void createModePaiementTransWithExistingId() throws Exception {
        // Create the ModePaiementTrans with an existing ID
        modePaiementTrans.setId(1);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restModePaiementTransMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(modePaiementTrans)))
            .andExpect(status().isBadRequest());

        // Validate the ModePaiementTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllModePaiementTrans() throws Exception {
        // Initialize the database
        insertedModePaiementTrans = modePaiementTransRepository.saveAndFlush(modePaiementTrans);

        // Get all the modePaiementTransList
        restModePaiementTransMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modePaiementTrans.getId().intValue())))
            .andExpect(jsonPath("$.[*].tenderId").value(hasItem(DEFAULT_TENDER_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].extensions").value(hasItem(DEFAULT_EXTENSIONS)))
            .andExpect(jsonPath("$.[*].orgShortName").value(hasItem(DEFAULT_ORG_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].locRef").value(hasItem(DEFAULT_LOC_REF)))
            .andExpect(jsonPath("$.[*].rvcRef").value(hasItem(DEFAULT_RVC_REF)));
    }

    @Test
    @Transactional
    void getModePaiementTrans() throws Exception {
        // Initialize the database
        insertedModePaiementTrans = modePaiementTransRepository.saveAndFlush(modePaiementTrans);

        // Get the modePaiementTrans
        restModePaiementTransMockMvc
            .perform(get(ENTITY_API_URL_ID, modePaiementTrans.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(modePaiementTrans.getId().intValue()))
            .andExpect(jsonPath("$.tenderId").value(DEFAULT_TENDER_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.extensions").value(DEFAULT_EXTENSIONS))
            .andExpect(jsonPath("$.orgShortName").value(DEFAULT_ORG_SHORT_NAME))
            .andExpect(jsonPath("$.locRef").value(DEFAULT_LOC_REF))
            .andExpect(jsonPath("$.rvcRef").value(DEFAULT_RVC_REF));
    }

    @Test
    @Transactional
    void getNonExistingModePaiementTrans() throws Exception {
        // Get the modePaiementTrans
        restModePaiementTransMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingModePaiementTrans() throws Exception {
        // Initialize the database
        insertedModePaiementTrans = modePaiementTransRepository.saveAndFlush(modePaiementTrans);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the modePaiementTrans
        ModePaiementTrans updatedModePaiementTrans = modePaiementTransRepository.findById(modePaiementTrans.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedModePaiementTrans are not directly saved in db
        em.detach(updatedModePaiementTrans);
        updatedModePaiementTrans
            .tenderId(UPDATED_TENDER_ID)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .extensions(UPDATED_EXTENSIONS)
            .orgShortName(UPDATED_ORG_SHORT_NAME)
            .locRef(UPDATED_LOC_REF)
            .rvcRef(UPDATED_RVC_REF);

        restModePaiementTransMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedModePaiementTrans.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedModePaiementTrans))
            )
            .andExpect(status().isOk());

        // Validate the ModePaiementTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedModePaiementTransToMatchAllProperties(updatedModePaiementTrans);
    }

    @Test
    @Transactional
    void putNonExistingModePaiementTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        modePaiementTrans.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModePaiementTransMockMvc
            .perform(
                put(ENTITY_API_URL_ID, modePaiementTrans.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(modePaiementTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModePaiementTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchModePaiementTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        modePaiementTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModePaiementTransMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(modePaiementTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModePaiementTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamModePaiementTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        modePaiementTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModePaiementTransMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(modePaiementTrans)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ModePaiementTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateModePaiementTransWithPatch() throws Exception {
        // Initialize the database
        insertedModePaiementTrans = modePaiementTransRepository.saveAndFlush(modePaiementTrans);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the modePaiementTrans using partial update
        ModePaiementTrans partialUpdatedModePaiementTrans = new ModePaiementTrans();
        partialUpdatedModePaiementTrans.setId(modePaiementTrans.getId());

        restModePaiementTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModePaiementTrans.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedModePaiementTrans))
            )
            .andExpect(status().isOk());

        // Validate the ModePaiementTrans in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertModePaiementTransUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedModePaiementTrans, modePaiementTrans),
            getPersistedModePaiementTrans(modePaiementTrans)
        );
    }

    @Test
    @Transactional
    void fullUpdateModePaiementTransWithPatch() throws Exception {
        // Initialize the database
        insertedModePaiementTrans = modePaiementTransRepository.saveAndFlush(modePaiementTrans);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the modePaiementTrans using partial update
        ModePaiementTrans partialUpdatedModePaiementTrans = new ModePaiementTrans();
        partialUpdatedModePaiementTrans.setId(modePaiementTrans.getId());

        partialUpdatedModePaiementTrans
            .tenderId(UPDATED_TENDER_ID)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .extensions(UPDATED_EXTENSIONS)
            .orgShortName(UPDATED_ORG_SHORT_NAME)
            .locRef(UPDATED_LOC_REF)
            .rvcRef(UPDATED_RVC_REF);

        restModePaiementTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModePaiementTrans.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedModePaiementTrans))
            )
            .andExpect(status().isOk());

        // Validate the ModePaiementTrans in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertModePaiementTransUpdatableFieldsEquals(
            partialUpdatedModePaiementTrans,
            getPersistedModePaiementTrans(partialUpdatedModePaiementTrans)
        );
    }

    @Test
    @Transactional
    void patchNonExistingModePaiementTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        modePaiementTrans.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModePaiementTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, modePaiementTrans.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(modePaiementTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModePaiementTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchModePaiementTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        modePaiementTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModePaiementTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(modePaiementTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModePaiementTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamModePaiementTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        modePaiementTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModePaiementTransMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(modePaiementTrans)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ModePaiementTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteModePaiementTrans() throws Exception {
        // Initialize the database
        insertedModePaiementTrans = modePaiementTransRepository.saveAndFlush(modePaiementTrans);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the modePaiementTrans
        restModePaiementTransMockMvc
            .perform(delete(ENTITY_API_URL_ID, modePaiementTrans.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return modePaiementTransRepository.count();
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

    protected ModePaiementTrans getPersistedModePaiementTrans(ModePaiementTrans modePaiementTrans) {
        return modePaiementTransRepository.findById(modePaiementTrans.getId()).orElseThrow();
    }

    protected void assertPersistedModePaiementTransToMatchAllProperties(ModePaiementTrans expectedModePaiementTrans) {
        assertModePaiementTransAllPropertiesEquals(expectedModePaiementTrans, getPersistedModePaiementTrans(expectedModePaiementTrans));
    }

    protected void assertPersistedModePaiementTransToMatchUpdatableProperties(ModePaiementTrans expectedModePaiementTrans) {
        assertModePaiementTransAllUpdatablePropertiesEquals(
            expectedModePaiementTrans,
            getPersistedModePaiementTrans(expectedModePaiementTrans)
        );
    }
}
