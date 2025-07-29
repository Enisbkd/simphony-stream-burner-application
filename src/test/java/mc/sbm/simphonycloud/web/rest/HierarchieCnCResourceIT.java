package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.HierarchieCnCAsserts.*;
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
import mc.sbm.simphonycloud.domain.HierarchieCnC;
import mc.sbm.simphonycloud.repository.HierarchieCnCRepository;
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
 * Integration tests for the {@link HierarchieCnCResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HierarchieCnCResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_HIER_ID = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_HIER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT_ID = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/hierarchie-cn-cs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HierarchieCnCRepository hierarchieCnCRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHierarchieCnCMockMvc;

    private HierarchieCnC hierarchieCnC;

    private HierarchieCnC insertedHierarchieCnC;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HierarchieCnC createEntity() {
        return new HierarchieCnC().nom(DEFAULT_NOM).parentHierId(DEFAULT_PARENT_HIER_ID).unitId(DEFAULT_UNIT_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HierarchieCnC createUpdatedEntity() {
        return new HierarchieCnC().nom(UPDATED_NOM).parentHierId(UPDATED_PARENT_HIER_ID).unitId(UPDATED_UNIT_ID);
    }

    @BeforeEach
    void initTest() {
        hierarchieCnC = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedHierarchieCnC != null) {
            hierarchieCnCRepository.delete(insertedHierarchieCnC);
            insertedHierarchieCnC = null;
        }
    }

    @Test
    @Transactional
    void createHierarchieCnC() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the HierarchieCnC
        var returnedHierarchieCnC = om.readValue(
            restHierarchieCnCMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hierarchieCnC)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            HierarchieCnC.class
        );

        // Validate the HierarchieCnC in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertHierarchieCnCUpdatableFieldsEquals(returnedHierarchieCnC, getPersistedHierarchieCnC(returnedHierarchieCnC));

        insertedHierarchieCnC = returnedHierarchieCnC;
    }

    @Test
    @Transactional
    void createHierarchieCnCWithExistingId() throws Exception {
        // Create the HierarchieCnC with an existing ID
        hierarchieCnC.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHierarchieCnCMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hierarchieCnC)))
            .andExpect(status().isBadRequest());

        // Validate the HierarchieCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        hierarchieCnC.setNom(null);

        // Create the HierarchieCnC, which fails.

        restHierarchieCnCMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hierarchieCnC)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHierarchieCnCS() throws Exception {
        // Initialize the database
        insertedHierarchieCnC = hierarchieCnCRepository.saveAndFlush(hierarchieCnC);

        // Get all the hierarchieCnCList
        restHierarchieCnCMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hierarchieCnC.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].parentHierId").value(hasItem(DEFAULT_PARENT_HIER_ID)))
            .andExpect(jsonPath("$.[*].unitId").value(hasItem(DEFAULT_UNIT_ID)));
    }

    @Test
    @Transactional
    void getHierarchieCnC() throws Exception {
        // Initialize the database
        insertedHierarchieCnC = hierarchieCnCRepository.saveAndFlush(hierarchieCnC);

        // Get the hierarchieCnC
        restHierarchieCnCMockMvc
            .perform(get(ENTITY_API_URL_ID, hierarchieCnC.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hierarchieCnC.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.parentHierId").value(DEFAULT_PARENT_HIER_ID))
            .andExpect(jsonPath("$.unitId").value(DEFAULT_UNIT_ID));
    }

    @Test
    @Transactional
    void getNonExistingHierarchieCnC() throws Exception {
        // Get the hierarchieCnC
        restHierarchieCnCMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHierarchieCnC() throws Exception {
        // Initialize the database
        insertedHierarchieCnC = hierarchieCnCRepository.saveAndFlush(hierarchieCnC);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hierarchieCnC
        HierarchieCnC updatedHierarchieCnC = hierarchieCnCRepository.findById(hierarchieCnC.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHierarchieCnC are not directly saved in db
        em.detach(updatedHierarchieCnC);
        updatedHierarchieCnC.nom(UPDATED_NOM).parentHierId(UPDATED_PARENT_HIER_ID).unitId(UPDATED_UNIT_ID);

        restHierarchieCnCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHierarchieCnC.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedHierarchieCnC))
            )
            .andExpect(status().isOk());

        // Validate the HierarchieCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHierarchieCnCToMatchAllProperties(updatedHierarchieCnC);
    }

    @Test
    @Transactional
    void putNonExistingHierarchieCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hierarchieCnC.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHierarchieCnCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hierarchieCnC.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hierarchieCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the HierarchieCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHierarchieCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hierarchieCnC.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHierarchieCnCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hierarchieCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the HierarchieCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHierarchieCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hierarchieCnC.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHierarchieCnCMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hierarchieCnC)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HierarchieCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHierarchieCnCWithPatch() throws Exception {
        // Initialize the database
        insertedHierarchieCnC = hierarchieCnCRepository.saveAndFlush(hierarchieCnC);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hierarchieCnC using partial update
        HierarchieCnC partialUpdatedHierarchieCnC = new HierarchieCnC();
        partialUpdatedHierarchieCnC.setId(hierarchieCnC.getId());

        partialUpdatedHierarchieCnC.nom(UPDATED_NOM).parentHierId(UPDATED_PARENT_HIER_ID);

        restHierarchieCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHierarchieCnC.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHierarchieCnC))
            )
            .andExpect(status().isOk());

        // Validate the HierarchieCnC in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHierarchieCnCUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedHierarchieCnC, hierarchieCnC),
            getPersistedHierarchieCnC(hierarchieCnC)
        );
    }

    @Test
    @Transactional
    void fullUpdateHierarchieCnCWithPatch() throws Exception {
        // Initialize the database
        insertedHierarchieCnC = hierarchieCnCRepository.saveAndFlush(hierarchieCnC);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hierarchieCnC using partial update
        HierarchieCnC partialUpdatedHierarchieCnC = new HierarchieCnC();
        partialUpdatedHierarchieCnC.setId(hierarchieCnC.getId());

        partialUpdatedHierarchieCnC.nom(UPDATED_NOM).parentHierId(UPDATED_PARENT_HIER_ID).unitId(UPDATED_UNIT_ID);

        restHierarchieCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHierarchieCnC.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHierarchieCnC))
            )
            .andExpect(status().isOk());

        // Validate the HierarchieCnC in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHierarchieCnCUpdatableFieldsEquals(partialUpdatedHierarchieCnC, getPersistedHierarchieCnC(partialUpdatedHierarchieCnC));
    }

    @Test
    @Transactional
    void patchNonExistingHierarchieCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hierarchieCnC.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHierarchieCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hierarchieCnC.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hierarchieCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the HierarchieCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHierarchieCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hierarchieCnC.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHierarchieCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hierarchieCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the HierarchieCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHierarchieCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hierarchieCnC.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHierarchieCnCMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(hierarchieCnC)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HierarchieCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHierarchieCnC() throws Exception {
        // Initialize the database
        insertedHierarchieCnC = hierarchieCnCRepository.saveAndFlush(hierarchieCnC);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the hierarchieCnC
        restHierarchieCnCMockMvc
            .perform(delete(ENTITY_API_URL_ID, hierarchieCnC.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return hierarchieCnCRepository.count();
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

    protected HierarchieCnC getPersistedHierarchieCnC(HierarchieCnC hierarchieCnC) {
        return hierarchieCnCRepository.findById(hierarchieCnC.getId()).orElseThrow();
    }

    protected void assertPersistedHierarchieCnCToMatchAllProperties(HierarchieCnC expectedHierarchieCnC) {
        assertHierarchieCnCAllPropertiesEquals(expectedHierarchieCnC, getPersistedHierarchieCnC(expectedHierarchieCnC));
    }

    protected void assertPersistedHierarchieCnCToMatchUpdatableProperties(HierarchieCnC expectedHierarchieCnC) {
        assertHierarchieCnCAllUpdatablePropertiesEquals(expectedHierarchieCnC, getPersistedHierarchieCnC(expectedHierarchieCnC));
    }
}
