package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.MajorGroupCnCAsserts.*;
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
import mc.sbm.simphonycloud.domain.MajorGroupCnC;
import mc.sbm.simphonycloud.repository.MajorGroupCnCRepository;
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
 * Integration tests for the {@link MajorGroupCnCResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MajorGroupCnCResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_COURT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_COURT = "BBBBBBBBBB";

    private static final Integer DEFAULT_POINT_DE_VENTE_REF = 1;
    private static final Integer UPDATED_POINT_DE_VENTE_REF = 2;

    private static final String ENTITY_API_URL = "/api/major-group-cn-cs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MajorGroupCnCRepository majorGroupCnCRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMajorGroupCnCMockMvc;

    private MajorGroupCnC majorGroupCnC;

    private MajorGroupCnC insertedMajorGroupCnC;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MajorGroupCnC createEntity() {
        return new MajorGroupCnC().nom(DEFAULT_NOM).nomCourt(DEFAULT_NOM_COURT).pointDeVenteRef(DEFAULT_POINT_DE_VENTE_REF);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MajorGroupCnC createUpdatedEntity() {
        return new MajorGroupCnC().nom(UPDATED_NOM).nomCourt(UPDATED_NOM_COURT).pointDeVenteRef(UPDATED_POINT_DE_VENTE_REF);
    }

    @BeforeEach
    void initTest() {
        majorGroupCnC = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedMajorGroupCnC != null) {
            majorGroupCnCRepository.delete(insertedMajorGroupCnC);
            insertedMajorGroupCnC = null;
        }
    }

    @Test
    @Transactional
    void createMajorGroupCnC() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MajorGroupCnC
        var returnedMajorGroupCnC = om.readValue(
            restMajorGroupCnCMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(majorGroupCnC)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MajorGroupCnC.class
        );

        // Validate the MajorGroupCnC in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMajorGroupCnCUpdatableFieldsEquals(returnedMajorGroupCnC, getPersistedMajorGroupCnC(returnedMajorGroupCnC));

        insertedMajorGroupCnC = returnedMajorGroupCnC;
    }

    @Test
    @Transactional
    void createMajorGroupCnCWithExistingId() throws Exception {
        // Create the MajorGroupCnC with an existing ID
        majorGroupCnC.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMajorGroupCnCMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(majorGroupCnC)))
            .andExpect(status().isBadRequest());

        // Validate the MajorGroupCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMajorGroupCnCS() throws Exception {
        // Initialize the database
        insertedMajorGroupCnC = majorGroupCnCRepository.saveAndFlush(majorGroupCnC);

        // Get all the majorGroupCnCList
        restMajorGroupCnCMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(majorGroupCnC.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].nomCourt").value(hasItem(DEFAULT_NOM_COURT)))
            .andExpect(jsonPath("$.[*].pointDeVenteRef").value(hasItem(DEFAULT_POINT_DE_VENTE_REF)));
    }

    @Test
    @Transactional
    void getMajorGroupCnC() throws Exception {
        // Initialize the database
        insertedMajorGroupCnC = majorGroupCnCRepository.saveAndFlush(majorGroupCnC);

        // Get the majorGroupCnC
        restMajorGroupCnCMockMvc
            .perform(get(ENTITY_API_URL_ID, majorGroupCnC.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(majorGroupCnC.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.nomCourt").value(DEFAULT_NOM_COURT))
            .andExpect(jsonPath("$.pointDeVenteRef").value(DEFAULT_POINT_DE_VENTE_REF));
    }

    @Test
    @Transactional
    void getNonExistingMajorGroupCnC() throws Exception {
        // Get the majorGroupCnC
        restMajorGroupCnCMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMajorGroupCnC() throws Exception {
        // Initialize the database
        insertedMajorGroupCnC = majorGroupCnCRepository.saveAndFlush(majorGroupCnC);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the majorGroupCnC
        MajorGroupCnC updatedMajorGroupCnC = majorGroupCnCRepository.findById(majorGroupCnC.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMajorGroupCnC are not directly saved in db
        em.detach(updatedMajorGroupCnC);
        updatedMajorGroupCnC.nom(UPDATED_NOM).nomCourt(UPDATED_NOM_COURT).pointDeVenteRef(UPDATED_POINT_DE_VENTE_REF);

        restMajorGroupCnCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMajorGroupCnC.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMajorGroupCnC))
            )
            .andExpect(status().isOk());

        // Validate the MajorGroupCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMajorGroupCnCToMatchAllProperties(updatedMajorGroupCnC);
    }

    @Test
    @Transactional
    void putNonExistingMajorGroupCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        majorGroupCnC.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMajorGroupCnCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, majorGroupCnC.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(majorGroupCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the MajorGroupCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMajorGroupCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        majorGroupCnC.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMajorGroupCnCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(majorGroupCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the MajorGroupCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMajorGroupCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        majorGroupCnC.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMajorGroupCnCMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(majorGroupCnC)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MajorGroupCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMajorGroupCnCWithPatch() throws Exception {
        // Initialize the database
        insertedMajorGroupCnC = majorGroupCnCRepository.saveAndFlush(majorGroupCnC);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the majorGroupCnC using partial update
        MajorGroupCnC partialUpdatedMajorGroupCnC = new MajorGroupCnC();
        partialUpdatedMajorGroupCnC.setId(majorGroupCnC.getId());

        partialUpdatedMajorGroupCnC.nom(UPDATED_NOM).pointDeVenteRef(UPDATED_POINT_DE_VENTE_REF);

        restMajorGroupCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMajorGroupCnC.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMajorGroupCnC))
            )
            .andExpect(status().isOk());

        // Validate the MajorGroupCnC in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMajorGroupCnCUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMajorGroupCnC, majorGroupCnC),
            getPersistedMajorGroupCnC(majorGroupCnC)
        );
    }

    @Test
    @Transactional
    void fullUpdateMajorGroupCnCWithPatch() throws Exception {
        // Initialize the database
        insertedMajorGroupCnC = majorGroupCnCRepository.saveAndFlush(majorGroupCnC);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the majorGroupCnC using partial update
        MajorGroupCnC partialUpdatedMajorGroupCnC = new MajorGroupCnC();
        partialUpdatedMajorGroupCnC.setId(majorGroupCnC.getId());

        partialUpdatedMajorGroupCnC.nom(UPDATED_NOM).nomCourt(UPDATED_NOM_COURT).pointDeVenteRef(UPDATED_POINT_DE_VENTE_REF);

        restMajorGroupCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMajorGroupCnC.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMajorGroupCnC))
            )
            .andExpect(status().isOk());

        // Validate the MajorGroupCnC in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMajorGroupCnCUpdatableFieldsEquals(partialUpdatedMajorGroupCnC, getPersistedMajorGroupCnC(partialUpdatedMajorGroupCnC));
    }

    @Test
    @Transactional
    void patchNonExistingMajorGroupCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        majorGroupCnC.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMajorGroupCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, majorGroupCnC.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(majorGroupCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the MajorGroupCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMajorGroupCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        majorGroupCnC.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMajorGroupCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(majorGroupCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the MajorGroupCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMajorGroupCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        majorGroupCnC.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMajorGroupCnCMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(majorGroupCnC)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MajorGroupCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMajorGroupCnC() throws Exception {
        // Initialize the database
        insertedMajorGroupCnC = majorGroupCnCRepository.saveAndFlush(majorGroupCnC);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the majorGroupCnC
        restMajorGroupCnCMockMvc
            .perform(delete(ENTITY_API_URL_ID, majorGroupCnC.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return majorGroupCnCRepository.count();
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

    protected MajorGroupCnC getPersistedMajorGroupCnC(MajorGroupCnC majorGroupCnC) {
        return majorGroupCnCRepository.findById(majorGroupCnC.getId()).orElseThrow();
    }

    protected void assertPersistedMajorGroupCnCToMatchAllProperties(MajorGroupCnC expectedMajorGroupCnC) {
        assertMajorGroupCnCAllPropertiesEquals(expectedMajorGroupCnC, getPersistedMajorGroupCnC(expectedMajorGroupCnC));
    }

    protected void assertPersistedMajorGroupCnCToMatchUpdatableProperties(MajorGroupCnC expectedMajorGroupCnC) {
        assertMajorGroupCnCAllUpdatablePropertiesEquals(expectedMajorGroupCnC, getPersistedMajorGroupCnC(expectedMajorGroupCnC));
    }
}
