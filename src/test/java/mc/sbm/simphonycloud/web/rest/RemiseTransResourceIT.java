package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.RemiseTransAsserts.*;
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
import mc.sbm.simphonycloud.domain.RemiseTrans;
import mc.sbm.simphonycloud.repository.RemiseTransRepository;
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
 * Integration tests for the {@link RemiseTransResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RemiseTransResourceIT {

    private static final String DEFAULT_ORG_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORG_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOC_REF = "AAAAAAAAAA";
    private static final String UPDATED_LOC_REF = "BBBBBBBBBB";

    private static final Integer DEFAULT_RVC_REF = 1;
    private static final Integer UPDATED_RVC_REF = 2;

    private static final Integer DEFAULT_DISCOUNT_ID = 1;
    private static final Integer UPDATED_DISCOUNT_ID = 2;

    private static final String DEFAULT_FR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ENG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENG_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISCOUNT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DISCOUNT_TYPE = "BBBBBBBBBB";

    private static final Double DEFAULT_DISCOUNT_VALUE = 1D;
    private static final Double UPDATED_DISCOUNT_VALUE = 2D;

    private static final String ENTITY_API_URL = "/api/remise-trans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RemiseTransRepository remiseTransRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRemiseTransMockMvc;

    private RemiseTrans remiseTrans;

    private RemiseTrans insertedRemiseTrans;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RemiseTrans createEntity() {
        return new RemiseTrans()
            .orgShortName(DEFAULT_ORG_SHORT_NAME)
            .locRef(DEFAULT_LOC_REF)
            .rvcRef(DEFAULT_RVC_REF)
            .discountId(DEFAULT_DISCOUNT_ID)
            .frName(DEFAULT_FR_NAME)
            .engName(DEFAULT_ENG_NAME)
            .discountType(DEFAULT_DISCOUNT_TYPE)
            .discountValue(DEFAULT_DISCOUNT_VALUE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RemiseTrans createUpdatedEntity() {
        return new RemiseTrans()
            .orgShortName(UPDATED_ORG_SHORT_NAME)
            .locRef(UPDATED_LOC_REF)
            .rvcRef(UPDATED_RVC_REF)
            .discountId(UPDATED_DISCOUNT_ID)
            .frName(UPDATED_FR_NAME)
            .engName(UPDATED_ENG_NAME)
            .discountType(UPDATED_DISCOUNT_TYPE)
            .discountValue(UPDATED_DISCOUNT_VALUE);
    }

    @BeforeEach
    void initTest() {
        remiseTrans = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedRemiseTrans != null) {
            remiseTransRepository.delete(insertedRemiseTrans);
            insertedRemiseTrans = null;
        }
    }

    @Test
    @Transactional
    void createRemiseTrans() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RemiseTrans
        var returnedRemiseTrans = om.readValue(
            restRemiseTransMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(remiseTrans)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RemiseTrans.class
        );

        // Validate the RemiseTrans in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRemiseTransUpdatableFieldsEquals(returnedRemiseTrans, getPersistedRemiseTrans(returnedRemiseTrans));

        insertedRemiseTrans = returnedRemiseTrans;
    }

    @Test
    @Transactional
    void createRemiseTransWithExistingId() throws Exception {
        // Create the RemiseTrans with an existing ID
        remiseTrans.setId(1);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRemiseTransMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(remiseTrans)))
            .andExpect(status().isBadRequest());

        // Validate the RemiseTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRemiseTrans() throws Exception {
        // Initialize the database
        insertedRemiseTrans = remiseTransRepository.saveAndFlush(remiseTrans);

        // Get all the remiseTransList
        restRemiseTransMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(remiseTrans.getId().intValue())))
            .andExpect(jsonPath("$.[*].orgShortName").value(hasItem(DEFAULT_ORG_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].locRef").value(hasItem(DEFAULT_LOC_REF)))
            .andExpect(jsonPath("$.[*].rvcRef").value(hasItem(DEFAULT_RVC_REF)))
            .andExpect(jsonPath("$.[*].discountId").value(hasItem(DEFAULT_DISCOUNT_ID)))
            .andExpect(jsonPath("$.[*].frName").value(hasItem(DEFAULT_FR_NAME)))
            .andExpect(jsonPath("$.[*].engName").value(hasItem(DEFAULT_ENG_NAME)))
            .andExpect(jsonPath("$.[*].discountType").value(hasItem(DEFAULT_DISCOUNT_TYPE)))
            .andExpect(jsonPath("$.[*].discountValue").value(hasItem(DEFAULT_DISCOUNT_VALUE)));
    }

    @Test
    @Transactional
    void getRemiseTrans() throws Exception {
        // Initialize the database
        insertedRemiseTrans = remiseTransRepository.saveAndFlush(remiseTrans);

        // Get the remiseTrans
        restRemiseTransMockMvc
            .perform(get(ENTITY_API_URL_ID, remiseTrans.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(remiseTrans.getId().intValue()))
            .andExpect(jsonPath("$.orgShortName").value(DEFAULT_ORG_SHORT_NAME))
            .andExpect(jsonPath("$.locRef").value(DEFAULT_LOC_REF))
            .andExpect(jsonPath("$.rvcRef").value(DEFAULT_RVC_REF))
            .andExpect(jsonPath("$.discountId").value(DEFAULT_DISCOUNT_ID))
            .andExpect(jsonPath("$.frName").value(DEFAULT_FR_NAME))
            .andExpect(jsonPath("$.engName").value(DEFAULT_ENG_NAME))
            .andExpect(jsonPath("$.discountType").value(DEFAULT_DISCOUNT_TYPE))
            .andExpect(jsonPath("$.discountValue").value(DEFAULT_DISCOUNT_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingRemiseTrans() throws Exception {
        // Get the remiseTrans
        restRemiseTransMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRemiseTrans() throws Exception {
        // Initialize the database
        insertedRemiseTrans = remiseTransRepository.saveAndFlush(remiseTrans);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the remiseTrans
        RemiseTrans updatedRemiseTrans = remiseTransRepository.findById(remiseTrans.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRemiseTrans are not directly saved in db
        em.detach(updatedRemiseTrans);
        updatedRemiseTrans
            .orgShortName(UPDATED_ORG_SHORT_NAME)
            .locRef(UPDATED_LOC_REF)
            .rvcRef(UPDATED_RVC_REF)
            .discountId(UPDATED_DISCOUNT_ID)
            .frName(UPDATED_FR_NAME)
            .engName(UPDATED_ENG_NAME)
            .discountType(UPDATED_DISCOUNT_TYPE)
            .discountValue(UPDATED_DISCOUNT_VALUE);

        restRemiseTransMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRemiseTrans.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRemiseTrans))
            )
            .andExpect(status().isOk());

        // Validate the RemiseTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRemiseTransToMatchAllProperties(updatedRemiseTrans);
    }

    @Test
    @Transactional
    void putNonExistingRemiseTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        remiseTrans.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRemiseTransMockMvc
            .perform(
                put(ENTITY_API_URL_ID, remiseTrans.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(remiseTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the RemiseTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRemiseTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        remiseTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemiseTransMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(remiseTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the RemiseTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRemiseTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        remiseTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemiseTransMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(remiseTrans)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RemiseTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRemiseTransWithPatch() throws Exception {
        // Initialize the database
        insertedRemiseTrans = remiseTransRepository.saveAndFlush(remiseTrans);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the remiseTrans using partial update
        RemiseTrans partialUpdatedRemiseTrans = new RemiseTrans();
        partialUpdatedRemiseTrans.setId(remiseTrans.getId());

        partialUpdatedRemiseTrans.rvcRef(UPDATED_RVC_REF).engName(UPDATED_ENG_NAME);

        restRemiseTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRemiseTrans.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRemiseTrans))
            )
            .andExpect(status().isOk());

        // Validate the RemiseTrans in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRemiseTransUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRemiseTrans, remiseTrans),
            getPersistedRemiseTrans(remiseTrans)
        );
    }

    @Test
    @Transactional
    void fullUpdateRemiseTransWithPatch() throws Exception {
        // Initialize the database
        insertedRemiseTrans = remiseTransRepository.saveAndFlush(remiseTrans);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the remiseTrans using partial update
        RemiseTrans partialUpdatedRemiseTrans = new RemiseTrans();
        partialUpdatedRemiseTrans.setId(remiseTrans.getId());

        partialUpdatedRemiseTrans
            .orgShortName(UPDATED_ORG_SHORT_NAME)
            .locRef(UPDATED_LOC_REF)
            .rvcRef(UPDATED_RVC_REF)
            .discountId(UPDATED_DISCOUNT_ID)
            .frName(UPDATED_FR_NAME)
            .engName(UPDATED_ENG_NAME)
            .discountType(UPDATED_DISCOUNT_TYPE)
            .discountValue(UPDATED_DISCOUNT_VALUE);

        restRemiseTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRemiseTrans.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRemiseTrans))
            )
            .andExpect(status().isOk());

        // Validate the RemiseTrans in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRemiseTransUpdatableFieldsEquals(partialUpdatedRemiseTrans, getPersistedRemiseTrans(partialUpdatedRemiseTrans));
    }

    @Test
    @Transactional
    void patchNonExistingRemiseTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        remiseTrans.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRemiseTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, remiseTrans.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(remiseTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the RemiseTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRemiseTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        remiseTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemiseTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(remiseTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the RemiseTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRemiseTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        remiseTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemiseTransMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(remiseTrans)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RemiseTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRemiseTrans() throws Exception {
        // Initialize the database
        insertedRemiseTrans = remiseTransRepository.saveAndFlush(remiseTrans);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the remiseTrans
        restRemiseTransMockMvc
            .perform(delete(ENTITY_API_URL_ID, remiseTrans.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return remiseTransRepository.count();
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

    protected RemiseTrans getPersistedRemiseTrans(RemiseTrans remiseTrans) {
        return remiseTransRepository.findById(remiseTrans.getId()).orElseThrow();
    }

    protected void assertPersistedRemiseTransToMatchAllProperties(RemiseTrans expectedRemiseTrans) {
        assertRemiseTransAllPropertiesEquals(expectedRemiseTrans, getPersistedRemiseTrans(expectedRemiseTrans));
    }

    protected void assertPersistedRemiseTransToMatchUpdatableProperties(RemiseTrans expectedRemiseTrans) {
        assertRemiseTransAllUpdatablePropertiesEquals(expectedRemiseTrans, getPersistedRemiseTrans(expectedRemiseTrans));
    }
}
