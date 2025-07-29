package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.FamilyGroupCnCAsserts.*;
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
import mc.sbm.simphonycloud.domain.FamilyGroupCnC;
import mc.sbm.simphonycloud.repository.FamilyGroupCnCRepository;
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
 * Integration tests for the {@link FamilyGroupCnCResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FamilyGroupCnCResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_COURT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_COURT = "BBBBBBBBBB";

    private static final Integer DEFAULT_MAJOR_GROUP_REF = 1;
    private static final Integer UPDATED_MAJOR_GROUP_REF = 2;

    private static final String ENTITY_API_URL = "/api/family-group-cn-cs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FamilyGroupCnCRepository familyGroupCnCRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFamilyGroupCnCMockMvc;

    private FamilyGroupCnC familyGroupCnC;

    private FamilyGroupCnC insertedFamilyGroupCnC;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FamilyGroupCnC createEntity() {
        return new FamilyGroupCnC().nom(DEFAULT_NOM).nomCourt(DEFAULT_NOM_COURT).majorGroupRef(DEFAULT_MAJOR_GROUP_REF);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FamilyGroupCnC createUpdatedEntity() {
        return new FamilyGroupCnC().nom(UPDATED_NOM).nomCourt(UPDATED_NOM_COURT).majorGroupRef(UPDATED_MAJOR_GROUP_REF);
    }

    @BeforeEach
    void initTest() {
        familyGroupCnC = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedFamilyGroupCnC != null) {
            familyGroupCnCRepository.delete(insertedFamilyGroupCnC);
            insertedFamilyGroupCnC = null;
        }
    }

    @Test
    @Transactional
    void createFamilyGroupCnC() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FamilyGroupCnC
        var returnedFamilyGroupCnC = om.readValue(
            restFamilyGroupCnCMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(familyGroupCnC)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FamilyGroupCnC.class
        );

        // Validate the FamilyGroupCnC in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFamilyGroupCnCUpdatableFieldsEquals(returnedFamilyGroupCnC, getPersistedFamilyGroupCnC(returnedFamilyGroupCnC));

        insertedFamilyGroupCnC = returnedFamilyGroupCnC;
    }

    @Test
    @Transactional
    void createFamilyGroupCnCWithExistingId() throws Exception {
        // Create the FamilyGroupCnC with an existing ID
        familyGroupCnC.setId(1);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFamilyGroupCnCMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(familyGroupCnC)))
            .andExpect(status().isBadRequest());

        // Validate the FamilyGroupCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFamilyGroupCnCS() throws Exception {
        // Initialize the database
        insertedFamilyGroupCnC = familyGroupCnCRepository.saveAndFlush(familyGroupCnC);

        // Get all the familyGroupCnCList
        restFamilyGroupCnCMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(familyGroupCnC.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].nomCourt").value(hasItem(DEFAULT_NOM_COURT)))
            .andExpect(jsonPath("$.[*].majorGroupRef").value(hasItem(DEFAULT_MAJOR_GROUP_REF)));
    }

    @Test
    @Transactional
    void getFamilyGroupCnC() throws Exception {
        // Initialize the database
        insertedFamilyGroupCnC = familyGroupCnCRepository.saveAndFlush(familyGroupCnC);

        // Get the familyGroupCnC
        restFamilyGroupCnCMockMvc
            .perform(get(ENTITY_API_URL_ID, familyGroupCnC.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(familyGroupCnC.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.nomCourt").value(DEFAULT_NOM_COURT))
            .andExpect(jsonPath("$.majorGroupRef").value(DEFAULT_MAJOR_GROUP_REF));
    }

    @Test
    @Transactional
    void getNonExistingFamilyGroupCnC() throws Exception {
        // Get the familyGroupCnC
        restFamilyGroupCnCMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFamilyGroupCnC() throws Exception {
        // Initialize the database
        insertedFamilyGroupCnC = familyGroupCnCRepository.saveAndFlush(familyGroupCnC);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the familyGroupCnC
        FamilyGroupCnC updatedFamilyGroupCnC = familyGroupCnCRepository.findById(familyGroupCnC.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFamilyGroupCnC are not directly saved in db
        em.detach(updatedFamilyGroupCnC);
        updatedFamilyGroupCnC.nom(UPDATED_NOM).nomCourt(UPDATED_NOM_COURT).majorGroupRef(UPDATED_MAJOR_GROUP_REF);

        restFamilyGroupCnCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFamilyGroupCnC.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFamilyGroupCnC))
            )
            .andExpect(status().isOk());

        // Validate the FamilyGroupCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFamilyGroupCnCToMatchAllProperties(updatedFamilyGroupCnC);
    }

    @Test
    @Transactional
    void putNonExistingFamilyGroupCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        familyGroupCnC.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFamilyGroupCnCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, familyGroupCnC.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(familyGroupCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the FamilyGroupCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFamilyGroupCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        familyGroupCnC.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFamilyGroupCnCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(familyGroupCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the FamilyGroupCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFamilyGroupCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        familyGroupCnC.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFamilyGroupCnCMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(familyGroupCnC)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FamilyGroupCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFamilyGroupCnCWithPatch() throws Exception {
        // Initialize the database
        insertedFamilyGroupCnC = familyGroupCnCRepository.saveAndFlush(familyGroupCnC);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the familyGroupCnC using partial update
        FamilyGroupCnC partialUpdatedFamilyGroupCnC = new FamilyGroupCnC();
        partialUpdatedFamilyGroupCnC.setId(familyGroupCnC.getId());

        partialUpdatedFamilyGroupCnC.nom(UPDATED_NOM).nomCourt(UPDATED_NOM_COURT);

        restFamilyGroupCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFamilyGroupCnC.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFamilyGroupCnC))
            )
            .andExpect(status().isOk());

        // Validate the FamilyGroupCnC in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFamilyGroupCnCUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFamilyGroupCnC, familyGroupCnC),
            getPersistedFamilyGroupCnC(familyGroupCnC)
        );
    }

    @Test
    @Transactional
    void fullUpdateFamilyGroupCnCWithPatch() throws Exception {
        // Initialize the database
        insertedFamilyGroupCnC = familyGroupCnCRepository.saveAndFlush(familyGroupCnC);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the familyGroupCnC using partial update
        FamilyGroupCnC partialUpdatedFamilyGroupCnC = new FamilyGroupCnC();
        partialUpdatedFamilyGroupCnC.setId(familyGroupCnC.getId());

        partialUpdatedFamilyGroupCnC.nom(UPDATED_NOM).nomCourt(UPDATED_NOM_COURT).majorGroupRef(UPDATED_MAJOR_GROUP_REF);

        restFamilyGroupCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFamilyGroupCnC.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFamilyGroupCnC))
            )
            .andExpect(status().isOk());

        // Validate the FamilyGroupCnC in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFamilyGroupCnCUpdatableFieldsEquals(partialUpdatedFamilyGroupCnC, getPersistedFamilyGroupCnC(partialUpdatedFamilyGroupCnC));
    }

    @Test
    @Transactional
    void patchNonExistingFamilyGroupCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        familyGroupCnC.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFamilyGroupCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, familyGroupCnC.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(familyGroupCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the FamilyGroupCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFamilyGroupCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        familyGroupCnC.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFamilyGroupCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(familyGroupCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the FamilyGroupCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFamilyGroupCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        familyGroupCnC.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFamilyGroupCnCMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(familyGroupCnC)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FamilyGroupCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFamilyGroupCnC() throws Exception {
        // Initialize the database
        insertedFamilyGroupCnC = familyGroupCnCRepository.saveAndFlush(familyGroupCnC);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the familyGroupCnC
        restFamilyGroupCnCMockMvc
            .perform(delete(ENTITY_API_URL_ID, familyGroupCnC.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return familyGroupCnCRepository.count();
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

    protected FamilyGroupCnC getPersistedFamilyGroupCnC(FamilyGroupCnC familyGroupCnC) {
        return familyGroupCnCRepository.findById(familyGroupCnC.getId()).orElseThrow();
    }

    protected void assertPersistedFamilyGroupCnCToMatchAllProperties(FamilyGroupCnC expectedFamilyGroupCnC) {
        assertFamilyGroupCnCAllPropertiesEquals(expectedFamilyGroupCnC, getPersistedFamilyGroupCnC(expectedFamilyGroupCnC));
    }

    protected void assertPersistedFamilyGroupCnCToMatchUpdatableProperties(FamilyGroupCnC expectedFamilyGroupCnC) {
        assertFamilyGroupCnCAllUpdatablePropertiesEquals(expectedFamilyGroupCnC, getPersistedFamilyGroupCnC(expectedFamilyGroupCnC));
    }
}
