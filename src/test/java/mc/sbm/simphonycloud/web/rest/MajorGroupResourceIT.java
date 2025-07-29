package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.MajorGroupAsserts.*;
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
import mc.sbm.simphonycloud.domain.MajorGroup;
import mc.sbm.simphonycloud.repository.MajorGroupRepository;
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
 * Integration tests for the {@link MajorGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MajorGroupResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_COURT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_COURT = "BBBBBBBBBB";

    private static final Integer DEFAULT_POINT_DE_VENTE_REF = 1;
    private static final Integer UPDATED_POINT_DE_VENTE_REF = 2;

    private static final String ENTITY_API_URL = "/api/major-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MajorGroupRepository majorGroupRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMajorGroupMockMvc;

    private MajorGroup majorGroup;

    private MajorGroup insertedMajorGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MajorGroup createEntity() {
        return new MajorGroup().nom(DEFAULT_NOM).nomCourt(DEFAULT_NOM_COURT).pointDeVenteRef(DEFAULT_POINT_DE_VENTE_REF);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MajorGroup createUpdatedEntity() {
        return new MajorGroup().nom(UPDATED_NOM).nomCourt(UPDATED_NOM_COURT).pointDeVenteRef(UPDATED_POINT_DE_VENTE_REF);
    }

    @BeforeEach
    void initTest() {
        majorGroup = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedMajorGroup != null) {
            majorGroupRepository.delete(insertedMajorGroup);
            insertedMajorGroup = null;
        }
    }

    @Test
    @Transactional
    void createMajorGroup() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MajorGroup
        var returnedMajorGroup = om.readValue(
            restMajorGroupMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(majorGroup)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MajorGroup.class
        );

        // Validate the MajorGroup in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMajorGroupUpdatableFieldsEquals(returnedMajorGroup, getPersistedMajorGroup(returnedMajorGroup));

        insertedMajorGroup = returnedMajorGroup;
    }

    @Test
    @Transactional
    void createMajorGroupWithExistingId() throws Exception {
        // Create the MajorGroup with an existing ID
        majorGroup.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMajorGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(majorGroup)))
            .andExpect(status().isBadRequest());

        // Validate the MajorGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMajorGroups() throws Exception {
        // Initialize the database
        insertedMajorGroup = majorGroupRepository.saveAndFlush(majorGroup);

        // Get all the majorGroupList
        restMajorGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(majorGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].nomCourt").value(hasItem(DEFAULT_NOM_COURT)))
            .andExpect(jsonPath("$.[*].pointDeVenteRef").value(hasItem(DEFAULT_POINT_DE_VENTE_REF)));
    }

    @Test
    @Transactional
    void getMajorGroup() throws Exception {
        // Initialize the database
        insertedMajorGroup = majorGroupRepository.saveAndFlush(majorGroup);

        // Get the majorGroup
        restMajorGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, majorGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(majorGroup.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.nomCourt").value(DEFAULT_NOM_COURT))
            .andExpect(jsonPath("$.pointDeVenteRef").value(DEFAULT_POINT_DE_VENTE_REF));
    }

    @Test
    @Transactional
    void getNonExistingMajorGroup() throws Exception {
        // Get the majorGroup
        restMajorGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMajorGroup() throws Exception {
        // Initialize the database
        insertedMajorGroup = majorGroupRepository.saveAndFlush(majorGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the majorGroup
        MajorGroup updatedMajorGroup = majorGroupRepository.findById(majorGroup.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMajorGroup are not directly saved in db
        em.detach(updatedMajorGroup);
        updatedMajorGroup.nom(UPDATED_NOM).nomCourt(UPDATED_NOM_COURT).pointDeVenteRef(UPDATED_POINT_DE_VENTE_REF);

        restMajorGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMajorGroup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMajorGroup))
            )
            .andExpect(status().isOk());

        // Validate the MajorGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMajorGroupToMatchAllProperties(updatedMajorGroup);
    }

    @Test
    @Transactional
    void putNonExistingMajorGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        majorGroup.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMajorGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, majorGroup.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(majorGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the MajorGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMajorGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        majorGroup.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMajorGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(majorGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the MajorGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMajorGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        majorGroup.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMajorGroupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(majorGroup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MajorGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMajorGroupWithPatch() throws Exception {
        // Initialize the database
        insertedMajorGroup = majorGroupRepository.saveAndFlush(majorGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the majorGroup using partial update
        MajorGroup partialUpdatedMajorGroup = new MajorGroup();
        partialUpdatedMajorGroup.setId(majorGroup.getId());

        partialUpdatedMajorGroup.nom(UPDATED_NOM).nomCourt(UPDATED_NOM_COURT);

        restMajorGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMajorGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMajorGroup))
            )
            .andExpect(status().isOk());

        // Validate the MajorGroup in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMajorGroupUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMajorGroup, majorGroup),
            getPersistedMajorGroup(majorGroup)
        );
    }

    @Test
    @Transactional
    void fullUpdateMajorGroupWithPatch() throws Exception {
        // Initialize the database
        insertedMajorGroup = majorGroupRepository.saveAndFlush(majorGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the majorGroup using partial update
        MajorGroup partialUpdatedMajorGroup = new MajorGroup();
        partialUpdatedMajorGroup.setId(majorGroup.getId());

        partialUpdatedMajorGroup.nom(UPDATED_NOM).nomCourt(UPDATED_NOM_COURT).pointDeVenteRef(UPDATED_POINT_DE_VENTE_REF);

        restMajorGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMajorGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMajorGroup))
            )
            .andExpect(status().isOk());

        // Validate the MajorGroup in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMajorGroupUpdatableFieldsEquals(partialUpdatedMajorGroup, getPersistedMajorGroup(partialUpdatedMajorGroup));
    }

    @Test
    @Transactional
    void patchNonExistingMajorGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        majorGroup.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMajorGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, majorGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(majorGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the MajorGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMajorGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        majorGroup.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMajorGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(majorGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the MajorGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMajorGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        majorGroup.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMajorGroupMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(majorGroup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MajorGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMajorGroup() throws Exception {
        // Initialize the database
        insertedMajorGroup = majorGroupRepository.saveAndFlush(majorGroup);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the majorGroup
        restMajorGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, majorGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return majorGroupRepository.count();
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

    protected MajorGroup getPersistedMajorGroup(MajorGroup majorGroup) {
        return majorGroupRepository.findById(majorGroup.getId()).orElseThrow();
    }

    protected void assertPersistedMajorGroupToMatchAllProperties(MajorGroup expectedMajorGroup) {
        assertMajorGroupAllPropertiesEquals(expectedMajorGroup, getPersistedMajorGroup(expectedMajorGroup));
    }

    protected void assertPersistedMajorGroupToMatchUpdatableProperties(MajorGroup expectedMajorGroup) {
        assertMajorGroupAllUpdatablePropertiesEquals(expectedMajorGroup, getPersistedMajorGroup(expectedMajorGroup));
    }
}
