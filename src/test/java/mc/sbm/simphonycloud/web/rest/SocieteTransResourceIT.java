package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.SocieteTransAsserts.*;
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
import mc.sbm.simphonycloud.domain.SocieteTrans;
import mc.sbm.simphonycloud.repository.SocieteTransRepository;
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
 * Integration tests for the {@link SocieteTransResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SocieteTransResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_COURT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_COURT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/societe-trans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SocieteTransRepository societeTransRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSocieteTransMockMvc;

    private SocieteTrans societeTrans;

    private SocieteTrans insertedSocieteTrans;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocieteTrans createEntity() {
        return new SocieteTrans().nom(DEFAULT_NOM).nomCourt(DEFAULT_NOM_COURT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocieteTrans createUpdatedEntity() {
        return new SocieteTrans().nom(UPDATED_NOM).nomCourt(UPDATED_NOM_COURT);
    }

    @BeforeEach
    void initTest() {
        societeTrans = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedSocieteTrans != null) {
            societeTransRepository.delete(insertedSocieteTrans);
            insertedSocieteTrans = null;
        }
    }

    @Test
    @Transactional
    void createSocieteTrans() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SocieteTrans
        var returnedSocieteTrans = om.readValue(
            restSocieteTransMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(societeTrans)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SocieteTrans.class
        );

        // Validate the SocieteTrans in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSocieteTransUpdatableFieldsEquals(returnedSocieteTrans, getPersistedSocieteTrans(returnedSocieteTrans));

        insertedSocieteTrans = returnedSocieteTrans;
    }

    @Test
    @Transactional
    void createSocieteTransWithExistingId() throws Exception {
        // Create the SocieteTrans with an existing ID
        societeTrans.setId(1);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocieteTransMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(societeTrans)))
            .andExpect(status().isBadRequest());

        // Validate the SocieteTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSocieteTrans() throws Exception {
        // Initialize the database
        insertedSocieteTrans = societeTransRepository.saveAndFlush(societeTrans);

        // Get all the societeTransList
        restSocieteTransMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(societeTrans.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].nomCourt").value(hasItem(DEFAULT_NOM_COURT)));
    }

    @Test
    @Transactional
    void getSocieteTrans() throws Exception {
        // Initialize the database
        insertedSocieteTrans = societeTransRepository.saveAndFlush(societeTrans);

        // Get the societeTrans
        restSocieteTransMockMvc
            .perform(get(ENTITY_API_URL_ID, societeTrans.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(societeTrans.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.nomCourt").value(DEFAULT_NOM_COURT));
    }

    @Test
    @Transactional
    void getNonExistingSocieteTrans() throws Exception {
        // Get the societeTrans
        restSocieteTransMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSocieteTrans() throws Exception {
        // Initialize the database
        insertedSocieteTrans = societeTransRepository.saveAndFlush(societeTrans);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the societeTrans
        SocieteTrans updatedSocieteTrans = societeTransRepository.findById(societeTrans.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSocieteTrans are not directly saved in db
        em.detach(updatedSocieteTrans);
        updatedSocieteTrans.nom(UPDATED_NOM).nomCourt(UPDATED_NOM_COURT);

        restSocieteTransMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSocieteTrans.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSocieteTrans))
            )
            .andExpect(status().isOk());

        // Validate the SocieteTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSocieteTransToMatchAllProperties(updatedSocieteTrans);
    }

    @Test
    @Transactional
    void putNonExistingSocieteTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        societeTrans.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocieteTransMockMvc
            .perform(
                put(ENTITY_API_URL_ID, societeTrans.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(societeTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocieteTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSocieteTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        societeTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocieteTransMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(societeTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocieteTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSocieteTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        societeTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocieteTransMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(societeTrans)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SocieteTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSocieteTransWithPatch() throws Exception {
        // Initialize the database
        insertedSocieteTrans = societeTransRepository.saveAndFlush(societeTrans);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the societeTrans using partial update
        SocieteTrans partialUpdatedSocieteTrans = new SocieteTrans();
        partialUpdatedSocieteTrans.setId(societeTrans.getId());

        partialUpdatedSocieteTrans.nom(UPDATED_NOM);

        restSocieteTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocieteTrans.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSocieteTrans))
            )
            .andExpect(status().isOk());

        // Validate the SocieteTrans in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSocieteTransUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSocieteTrans, societeTrans),
            getPersistedSocieteTrans(societeTrans)
        );
    }

    @Test
    @Transactional
    void fullUpdateSocieteTransWithPatch() throws Exception {
        // Initialize the database
        insertedSocieteTrans = societeTransRepository.saveAndFlush(societeTrans);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the societeTrans using partial update
        SocieteTrans partialUpdatedSocieteTrans = new SocieteTrans();
        partialUpdatedSocieteTrans.setId(societeTrans.getId());

        partialUpdatedSocieteTrans.nom(UPDATED_NOM).nomCourt(UPDATED_NOM_COURT);

        restSocieteTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocieteTrans.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSocieteTrans))
            )
            .andExpect(status().isOk());

        // Validate the SocieteTrans in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSocieteTransUpdatableFieldsEquals(partialUpdatedSocieteTrans, getPersistedSocieteTrans(partialUpdatedSocieteTrans));
    }

    @Test
    @Transactional
    void patchNonExistingSocieteTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        societeTrans.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocieteTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, societeTrans.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(societeTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocieteTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSocieteTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        societeTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocieteTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(societeTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocieteTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSocieteTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        societeTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocieteTransMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(societeTrans)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SocieteTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSocieteTrans() throws Exception {
        // Initialize the database
        insertedSocieteTrans = societeTransRepository.saveAndFlush(societeTrans);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the societeTrans
        restSocieteTransMockMvc
            .perform(delete(ENTITY_API_URL_ID, societeTrans.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return societeTransRepository.count();
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

    protected SocieteTrans getPersistedSocieteTrans(SocieteTrans societeTrans) {
        return societeTransRepository.findById(societeTrans.getId()).orElseThrow();
    }

    protected void assertPersistedSocieteTransToMatchAllProperties(SocieteTrans expectedSocieteTrans) {
        assertSocieteTransAllPropertiesEquals(expectedSocieteTrans, getPersistedSocieteTrans(expectedSocieteTrans));
    }

    protected void assertPersistedSocieteTransToMatchUpdatableProperties(SocieteTrans expectedSocieteTrans) {
        assertSocieteTransAllUpdatablePropertiesEquals(expectedSocieteTrans, getPersistedSocieteTrans(expectedSocieteTrans));
    }
}
