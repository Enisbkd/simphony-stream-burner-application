package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.FamilyGroupAsserts.*;
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
import mc.sbm.simphonycloud.domain.FamilyGroup;
import mc.sbm.simphonycloud.repository.FamilyGroupRepository;
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
 * Integration tests for the {@link FamilyGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FamilyGroupResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_COURT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_COURT = "BBBBBBBBBB";

    private static final Integer DEFAULT_MAJOR_GROUP_REF = 1;
    private static final Integer UPDATED_MAJOR_GROUP_REF = 2;

    private static final String ENTITY_API_URL = "/api/family-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FamilyGroupRepository familyGroupRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFamilyGroupMockMvc;

    private FamilyGroup familyGroup;

    private FamilyGroup insertedFamilyGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FamilyGroup createEntity() {
        return new FamilyGroup().nom(DEFAULT_NOM).nomCourt(DEFAULT_NOM_COURT).majorGroupRef(DEFAULT_MAJOR_GROUP_REF);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FamilyGroup createUpdatedEntity() {
        return new FamilyGroup().nom(UPDATED_NOM).nomCourt(UPDATED_NOM_COURT).majorGroupRef(UPDATED_MAJOR_GROUP_REF);
    }

    @BeforeEach
    void initTest() {
        familyGroup = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedFamilyGroup != null) {
            familyGroupRepository.delete(insertedFamilyGroup);
            insertedFamilyGroup = null;
        }
    }

    @Test
    @Transactional
    void createFamilyGroup() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FamilyGroup
        var returnedFamilyGroup = om.readValue(
            restFamilyGroupMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(familyGroup)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FamilyGroup.class
        );

        // Validate the FamilyGroup in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFamilyGroupUpdatableFieldsEquals(returnedFamilyGroup, getPersistedFamilyGroup(returnedFamilyGroup));

        insertedFamilyGroup = returnedFamilyGroup;
    }

    @Test
    @Transactional
    void createFamilyGroupWithExistingId() throws Exception {
        // Create the FamilyGroup with an existing ID
        familyGroup.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFamilyGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(familyGroup)))
            .andExpect(status().isBadRequest());

        // Validate the FamilyGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFamilyGroups() throws Exception {
        // Initialize the database
        insertedFamilyGroup = familyGroupRepository.saveAndFlush(familyGroup);

        // Get all the familyGroupList
        restFamilyGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(familyGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].nomCourt").value(hasItem(DEFAULT_NOM_COURT)))
            .andExpect(jsonPath("$.[*].majorGroupRef").value(hasItem(DEFAULT_MAJOR_GROUP_REF)));
    }

    @Test
    @Transactional
    void getFamilyGroup() throws Exception {
        // Initialize the database
        insertedFamilyGroup = familyGroupRepository.saveAndFlush(familyGroup);

        // Get the familyGroup
        restFamilyGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, familyGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(familyGroup.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.nomCourt").value(DEFAULT_NOM_COURT))
            .andExpect(jsonPath("$.majorGroupRef").value(DEFAULT_MAJOR_GROUP_REF));
    }

    @Test
    @Transactional
    void getNonExistingFamilyGroup() throws Exception {
        // Get the familyGroup
        restFamilyGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFamilyGroup() throws Exception {
        // Initialize the database
        insertedFamilyGroup = familyGroupRepository.saveAndFlush(familyGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the familyGroup
        FamilyGroup updatedFamilyGroup = familyGroupRepository.findById(familyGroup.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFamilyGroup are not directly saved in db
        em.detach(updatedFamilyGroup);
        updatedFamilyGroup.nom(UPDATED_NOM).nomCourt(UPDATED_NOM_COURT).majorGroupRef(UPDATED_MAJOR_GROUP_REF);

        restFamilyGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFamilyGroup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFamilyGroup))
            )
            .andExpect(status().isOk());

        // Validate the FamilyGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFamilyGroupToMatchAllProperties(updatedFamilyGroup);
    }

    @Test
    @Transactional
    void putNonExistingFamilyGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        familyGroup.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFamilyGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, familyGroup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(familyGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the FamilyGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFamilyGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        familyGroup.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFamilyGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(familyGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the FamilyGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFamilyGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        familyGroup.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFamilyGroupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(familyGroup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FamilyGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFamilyGroupWithPatch() throws Exception {
        // Initialize the database
        insertedFamilyGroup = familyGroupRepository.saveAndFlush(familyGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the familyGroup using partial update
        FamilyGroup partialUpdatedFamilyGroup = new FamilyGroup();
        partialUpdatedFamilyGroup.setId(familyGroup.getId());

        partialUpdatedFamilyGroup.nom(UPDATED_NOM).nomCourt(UPDATED_NOM_COURT);

        restFamilyGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFamilyGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFamilyGroup))
            )
            .andExpect(status().isOk());

        // Validate the FamilyGroup in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFamilyGroupUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFamilyGroup, familyGroup),
            getPersistedFamilyGroup(familyGroup)
        );
    }

    @Test
    @Transactional
    void fullUpdateFamilyGroupWithPatch() throws Exception {
        // Initialize the database
        insertedFamilyGroup = familyGroupRepository.saveAndFlush(familyGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the familyGroup using partial update
        FamilyGroup partialUpdatedFamilyGroup = new FamilyGroup();
        partialUpdatedFamilyGroup.setId(familyGroup.getId());

        partialUpdatedFamilyGroup.nom(UPDATED_NOM).nomCourt(UPDATED_NOM_COURT).majorGroupRef(UPDATED_MAJOR_GROUP_REF);

        restFamilyGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFamilyGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFamilyGroup))
            )
            .andExpect(status().isOk());

        // Validate the FamilyGroup in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFamilyGroupUpdatableFieldsEquals(partialUpdatedFamilyGroup, getPersistedFamilyGroup(partialUpdatedFamilyGroup));
    }

    @Test
    @Transactional
    void patchNonExistingFamilyGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        familyGroup.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFamilyGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, familyGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(familyGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the FamilyGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFamilyGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        familyGroup.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFamilyGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(familyGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the FamilyGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFamilyGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        familyGroup.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFamilyGroupMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(familyGroup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FamilyGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFamilyGroup() throws Exception {
        // Initialize the database
        insertedFamilyGroup = familyGroupRepository.saveAndFlush(familyGroup);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the familyGroup
        restFamilyGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, familyGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return familyGroupRepository.count();
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

    protected FamilyGroup getPersistedFamilyGroup(FamilyGroup familyGroup) {
        return familyGroupRepository.findById(familyGroup.getId()).orElseThrow();
    }

    protected void assertPersistedFamilyGroupToMatchAllProperties(FamilyGroup expectedFamilyGroup) {
        assertFamilyGroupAllPropertiesEquals(expectedFamilyGroup, getPersistedFamilyGroup(expectedFamilyGroup));
    }

    protected void assertPersistedFamilyGroupToMatchUpdatableProperties(FamilyGroup expectedFamilyGroup) {
        assertFamilyGroupAllUpdatablePropertiesEquals(expectedFamilyGroup, getPersistedFamilyGroup(expectedFamilyGroup));
    }
}
