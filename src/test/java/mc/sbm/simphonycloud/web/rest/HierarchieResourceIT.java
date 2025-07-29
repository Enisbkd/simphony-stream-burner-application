package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.HierarchieAsserts.*;
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
import mc.sbm.simphonycloud.domain.Hierarchie;
import mc.sbm.simphonycloud.repository.HierarchieRepository;
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
 * Integration tests for the {@link HierarchieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HierarchieResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_HIER_ID = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_HIER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT_ID = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/hierarchies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HierarchieRepository hierarchieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHierarchieMockMvc;

    private Hierarchie hierarchie;

    private Hierarchie insertedHierarchie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hierarchie createEntity() {
        return new Hierarchie().nom(DEFAULT_NOM).parentHierId(DEFAULT_PARENT_HIER_ID).unitId(DEFAULT_UNIT_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hierarchie createUpdatedEntity() {
        return new Hierarchie().nom(UPDATED_NOM).parentHierId(UPDATED_PARENT_HIER_ID).unitId(UPDATED_UNIT_ID);
    }

    @BeforeEach
    void initTest() {
        hierarchie = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedHierarchie != null) {
            hierarchieRepository.delete(insertedHierarchie);
            insertedHierarchie = null;
        }
    }

    @Test
    @Transactional
    void createHierarchie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Hierarchie
        var returnedHierarchie = om.readValue(
            restHierarchieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hierarchie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Hierarchie.class
        );

        // Validate the Hierarchie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertHierarchieUpdatableFieldsEquals(returnedHierarchie, getPersistedHierarchie(returnedHierarchie));

        insertedHierarchie = returnedHierarchie;
    }

    @Test
    @Transactional
    void createHierarchieWithExistingId() throws Exception {
        // Create the Hierarchie with an existing ID
        hierarchie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHierarchieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hierarchie)))
            .andExpect(status().isBadRequest());

        // Validate the Hierarchie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        hierarchie.setNom(null);

        // Create the Hierarchie, which fails.

        restHierarchieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hierarchie)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHierarchies() throws Exception {
        // Initialize the database
        insertedHierarchie = hierarchieRepository.saveAndFlush(hierarchie);

        // Get all the hierarchieList
        restHierarchieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hierarchie.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].parentHierId").value(hasItem(DEFAULT_PARENT_HIER_ID)))
            .andExpect(jsonPath("$.[*].unitId").value(hasItem(DEFAULT_UNIT_ID)));
    }

    @Test
    @Transactional
    void getHierarchie() throws Exception {
        // Initialize the database
        insertedHierarchie = hierarchieRepository.saveAndFlush(hierarchie);

        // Get the hierarchie
        restHierarchieMockMvc
            .perform(get(ENTITY_API_URL_ID, hierarchie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hierarchie.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.parentHierId").value(DEFAULT_PARENT_HIER_ID))
            .andExpect(jsonPath("$.unitId").value(DEFAULT_UNIT_ID));
    }

    @Test
    @Transactional
    void getNonExistingHierarchie() throws Exception {
        // Get the hierarchie
        restHierarchieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHierarchie() throws Exception {
        // Initialize the database
        insertedHierarchie = hierarchieRepository.saveAndFlush(hierarchie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hierarchie
        Hierarchie updatedHierarchie = hierarchieRepository.findById(hierarchie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHierarchie are not directly saved in db
        em.detach(updatedHierarchie);
        updatedHierarchie.nom(UPDATED_NOM).parentHierId(UPDATED_PARENT_HIER_ID).unitId(UPDATED_UNIT_ID);

        restHierarchieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHierarchie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedHierarchie))
            )
            .andExpect(status().isOk());

        // Validate the Hierarchie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHierarchieToMatchAllProperties(updatedHierarchie);
    }

    @Test
    @Transactional
    void putNonExistingHierarchie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hierarchie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHierarchieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hierarchie.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hierarchie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hierarchie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHierarchie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hierarchie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHierarchieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hierarchie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hierarchie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHierarchie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hierarchie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHierarchieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hierarchie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hierarchie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHierarchieWithPatch() throws Exception {
        // Initialize the database
        insertedHierarchie = hierarchieRepository.saveAndFlush(hierarchie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hierarchie using partial update
        Hierarchie partialUpdatedHierarchie = new Hierarchie();
        partialUpdatedHierarchie.setId(hierarchie.getId());

        partialUpdatedHierarchie.unitId(UPDATED_UNIT_ID);

        restHierarchieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHierarchie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHierarchie))
            )
            .andExpect(status().isOk());

        // Validate the Hierarchie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHierarchieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedHierarchie, hierarchie),
            getPersistedHierarchie(hierarchie)
        );
    }

    @Test
    @Transactional
    void fullUpdateHierarchieWithPatch() throws Exception {
        // Initialize the database
        insertedHierarchie = hierarchieRepository.saveAndFlush(hierarchie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hierarchie using partial update
        Hierarchie partialUpdatedHierarchie = new Hierarchie();
        partialUpdatedHierarchie.setId(hierarchie.getId());

        partialUpdatedHierarchie.nom(UPDATED_NOM).parentHierId(UPDATED_PARENT_HIER_ID).unitId(UPDATED_UNIT_ID);

        restHierarchieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHierarchie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHierarchie))
            )
            .andExpect(status().isOk());

        // Validate the Hierarchie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHierarchieUpdatableFieldsEquals(partialUpdatedHierarchie, getPersistedHierarchie(partialUpdatedHierarchie));
    }

    @Test
    @Transactional
    void patchNonExistingHierarchie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hierarchie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHierarchieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hierarchie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hierarchie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hierarchie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHierarchie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hierarchie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHierarchieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hierarchie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hierarchie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHierarchie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hierarchie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHierarchieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(hierarchie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hierarchie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHierarchie() throws Exception {
        // Initialize the database
        insertedHierarchie = hierarchieRepository.saveAndFlush(hierarchie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the hierarchie
        restHierarchieMockMvc
            .perform(delete(ENTITY_API_URL_ID, hierarchie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return hierarchieRepository.count();
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

    protected Hierarchie getPersistedHierarchie(Hierarchie hierarchie) {
        return hierarchieRepository.findById(hierarchie.getId()).orElseThrow();
    }

    protected void assertPersistedHierarchieToMatchAllProperties(Hierarchie expectedHierarchie) {
        assertHierarchieAllPropertiesEquals(expectedHierarchie, getPersistedHierarchie(expectedHierarchie));
    }

    protected void assertPersistedHierarchieToMatchUpdatableProperties(Hierarchie expectedHierarchie) {
        assertHierarchieAllUpdatablePropertiesEquals(expectedHierarchie, getPersistedHierarchie(expectedHierarchie));
    }
}
