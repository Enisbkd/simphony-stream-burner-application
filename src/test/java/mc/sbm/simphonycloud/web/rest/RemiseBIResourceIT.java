package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.RemiseBIAsserts.*;
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
import mc.sbm.simphonycloud.domain.RemiseBI;
import mc.sbm.simphonycloud.repository.RemiseBIRepository;
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
 * Integration tests for the {@link RemiseBIResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RemiseBIResourceIT {

    private static final Integer DEFAULT_NUM = 1;
    private static final Integer UPDATED_NUM = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_POS_PERCENT = 1D;
    private static final Double UPDATED_POS_PERCENT = 2D;

    private static final Integer DEFAULT_RPT_GRP_NUM = 1;
    private static final Integer UPDATED_RPT_GRP_NUM = 2;

    private static final String DEFAULT_RPT_GRP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RPT_GRP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOC_REF = "AAAAAAAAAA";
    private static final String UPDATED_LOC_REF = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/remise-bis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RemiseBIRepository remiseBIRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRemiseBIMockMvc;

    private RemiseBI remiseBI;

    private RemiseBI insertedRemiseBI;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RemiseBI createEntity() {
        return new RemiseBI()
            .num(DEFAULT_NUM)
            .name(DEFAULT_NAME)
            .posPercent(DEFAULT_POS_PERCENT)
            .rptGrpNum(DEFAULT_RPT_GRP_NUM)
            .rptGrpName(DEFAULT_RPT_GRP_NAME)
            .locRef(DEFAULT_LOC_REF);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RemiseBI createUpdatedEntity() {
        return new RemiseBI()
            .num(UPDATED_NUM)
            .name(UPDATED_NAME)
            .posPercent(UPDATED_POS_PERCENT)
            .rptGrpNum(UPDATED_RPT_GRP_NUM)
            .rptGrpName(UPDATED_RPT_GRP_NAME)
            .locRef(UPDATED_LOC_REF);
    }

    @BeforeEach
    void initTest() {
        remiseBI = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedRemiseBI != null) {
            remiseBIRepository.delete(insertedRemiseBI);
            insertedRemiseBI = null;
        }
    }

    @Test
    @Transactional
    void createRemiseBI() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RemiseBI
        var returnedRemiseBI = om.readValue(
            restRemiseBIMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(remiseBI)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RemiseBI.class
        );

        // Validate the RemiseBI in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRemiseBIUpdatableFieldsEquals(returnedRemiseBI, getPersistedRemiseBI(returnedRemiseBI));

        insertedRemiseBI = returnedRemiseBI;
    }

    @Test
    @Transactional
    void createRemiseBIWithExistingId() throws Exception {
        // Create the RemiseBI with an existing ID
        remiseBI.setId(1);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRemiseBIMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(remiseBI)))
            .andExpect(status().isBadRequest());

        // Validate the RemiseBI in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRemiseBIS() throws Exception {
        // Initialize the database
        insertedRemiseBI = remiseBIRepository.saveAndFlush(remiseBI);

        // Get all the remiseBIList
        restRemiseBIMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(remiseBI.getId().intValue())))
            .andExpect(jsonPath("$.[*].num").value(hasItem(DEFAULT_NUM)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].posPercent").value(hasItem(DEFAULT_POS_PERCENT)))
            .andExpect(jsonPath("$.[*].rptGrpNum").value(hasItem(DEFAULT_RPT_GRP_NUM)))
            .andExpect(jsonPath("$.[*].rptGrpName").value(hasItem(DEFAULT_RPT_GRP_NAME)))
            .andExpect(jsonPath("$.[*].locRef").value(hasItem(DEFAULT_LOC_REF)));
    }

    @Test
    @Transactional
    void getRemiseBI() throws Exception {
        // Initialize the database
        insertedRemiseBI = remiseBIRepository.saveAndFlush(remiseBI);

        // Get the remiseBI
        restRemiseBIMockMvc
            .perform(get(ENTITY_API_URL_ID, remiseBI.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(remiseBI.getId().intValue()))
            .andExpect(jsonPath("$.num").value(DEFAULT_NUM))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.posPercent").value(DEFAULT_POS_PERCENT))
            .andExpect(jsonPath("$.rptGrpNum").value(DEFAULT_RPT_GRP_NUM))
            .andExpect(jsonPath("$.rptGrpName").value(DEFAULT_RPT_GRP_NAME))
            .andExpect(jsonPath("$.locRef").value(DEFAULT_LOC_REF));
    }

    @Test
    @Transactional
    void getNonExistingRemiseBI() throws Exception {
        // Get the remiseBI
        restRemiseBIMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRemiseBI() throws Exception {
        // Initialize the database
        insertedRemiseBI = remiseBIRepository.saveAndFlush(remiseBI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the remiseBI
        RemiseBI updatedRemiseBI = remiseBIRepository.findById(remiseBI.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRemiseBI are not directly saved in db
        em.detach(updatedRemiseBI);
        updatedRemiseBI
            .num(UPDATED_NUM)
            .name(UPDATED_NAME)
            .posPercent(UPDATED_POS_PERCENT)
            .rptGrpNum(UPDATED_RPT_GRP_NUM)
            .rptGrpName(UPDATED_RPT_GRP_NAME)
            .locRef(UPDATED_LOC_REF);

        restRemiseBIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRemiseBI.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRemiseBI))
            )
            .andExpect(status().isOk());

        // Validate the RemiseBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRemiseBIToMatchAllProperties(updatedRemiseBI);
    }

    @Test
    @Transactional
    void putNonExistingRemiseBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        remiseBI.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRemiseBIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, remiseBI.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(remiseBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the RemiseBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRemiseBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        remiseBI.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemiseBIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(remiseBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the RemiseBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRemiseBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        remiseBI.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemiseBIMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(remiseBI)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RemiseBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRemiseBIWithPatch() throws Exception {
        // Initialize the database
        insertedRemiseBI = remiseBIRepository.saveAndFlush(remiseBI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the remiseBI using partial update
        RemiseBI partialUpdatedRemiseBI = new RemiseBI();
        partialUpdatedRemiseBI.setId(remiseBI.getId());

        partialUpdatedRemiseBI.num(UPDATED_NUM).posPercent(UPDATED_POS_PERCENT).rptGrpName(UPDATED_RPT_GRP_NAME);

        restRemiseBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRemiseBI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRemiseBI))
            )
            .andExpect(status().isOk());

        // Validate the RemiseBI in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRemiseBIUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedRemiseBI, remiseBI), getPersistedRemiseBI(remiseBI));
    }

    @Test
    @Transactional
    void fullUpdateRemiseBIWithPatch() throws Exception {
        // Initialize the database
        insertedRemiseBI = remiseBIRepository.saveAndFlush(remiseBI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the remiseBI using partial update
        RemiseBI partialUpdatedRemiseBI = new RemiseBI();
        partialUpdatedRemiseBI.setId(remiseBI.getId());

        partialUpdatedRemiseBI
            .num(UPDATED_NUM)
            .name(UPDATED_NAME)
            .posPercent(UPDATED_POS_PERCENT)
            .rptGrpNum(UPDATED_RPT_GRP_NUM)
            .rptGrpName(UPDATED_RPT_GRP_NAME)
            .locRef(UPDATED_LOC_REF);

        restRemiseBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRemiseBI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRemiseBI))
            )
            .andExpect(status().isOk());

        // Validate the RemiseBI in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRemiseBIUpdatableFieldsEquals(partialUpdatedRemiseBI, getPersistedRemiseBI(partialUpdatedRemiseBI));
    }

    @Test
    @Transactional
    void patchNonExistingRemiseBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        remiseBI.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRemiseBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, remiseBI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(remiseBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the RemiseBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRemiseBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        remiseBI.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemiseBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(remiseBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the RemiseBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRemiseBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        remiseBI.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemiseBIMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(remiseBI)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RemiseBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRemiseBI() throws Exception {
        // Initialize the database
        insertedRemiseBI = remiseBIRepository.saveAndFlush(remiseBI);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the remiseBI
        restRemiseBIMockMvc
            .perform(delete(ENTITY_API_URL_ID, remiseBI.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return remiseBIRepository.count();
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

    protected RemiseBI getPersistedRemiseBI(RemiseBI remiseBI) {
        return remiseBIRepository.findById(remiseBI.getId()).orElseThrow();
    }

    protected void assertPersistedRemiseBIToMatchAllProperties(RemiseBI expectedRemiseBI) {
        assertRemiseBIAllPropertiesEquals(expectedRemiseBI, getPersistedRemiseBI(expectedRemiseBI));
    }

    protected void assertPersistedRemiseBIToMatchUpdatableProperties(RemiseBI expectedRemiseBI) {
        assertRemiseBIAllUpdatablePropertiesEquals(expectedRemiseBI, getPersistedRemiseBI(expectedRemiseBI));
    }
}
