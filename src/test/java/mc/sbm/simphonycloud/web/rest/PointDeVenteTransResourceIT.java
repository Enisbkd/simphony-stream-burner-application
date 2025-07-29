package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.PointDeVenteTransAsserts.*;
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
import mc.sbm.simphonycloud.domain.PointDeVenteTrans;
import mc.sbm.simphonycloud.repository.PointDeVenteTransRepository;
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
 * Integration tests for the {@link PointDeVenteTransResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PointDeVenteTransResourceIT {

    private static final Integer DEFAULT_RVC_REF = 1;
    private static final Integer UPDATED_RVC_REF = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOC_REF = "AAAAAAAAAA";
    private static final String UPDATED_LOC_REF = "BBBBBBBBBB";

    private static final String DEFAULT_ORG_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORG_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/point-de-vente-trans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PointDeVenteTransRepository pointDeVenteTransRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPointDeVenteTransMockMvc;

    private PointDeVenteTrans pointDeVenteTrans;

    private PointDeVenteTrans insertedPointDeVenteTrans;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointDeVenteTrans createEntity() {
        return new PointDeVenteTrans()
            .rvcRef(DEFAULT_RVC_REF)
            .name(DEFAULT_NAME)
            .locRef(DEFAULT_LOC_REF)
            .orgShortName(DEFAULT_ORG_SHORT_NAME)
            .address(DEFAULT_ADDRESS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointDeVenteTrans createUpdatedEntity() {
        return new PointDeVenteTrans()
            .rvcRef(UPDATED_RVC_REF)
            .name(UPDATED_NAME)
            .locRef(UPDATED_LOC_REF)
            .orgShortName(UPDATED_ORG_SHORT_NAME)
            .address(UPDATED_ADDRESS);
    }

    @BeforeEach
    void initTest() {
        pointDeVenteTrans = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPointDeVenteTrans != null) {
            pointDeVenteTransRepository.delete(insertedPointDeVenteTrans);
            insertedPointDeVenteTrans = null;
        }
    }

    @Test
    @Transactional
    void createPointDeVenteTrans() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PointDeVenteTrans
        var returnedPointDeVenteTrans = om.readValue(
            restPointDeVenteTransMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pointDeVenteTrans)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PointDeVenteTrans.class
        );

        // Validate the PointDeVenteTrans in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPointDeVenteTransUpdatableFieldsEquals(returnedPointDeVenteTrans, getPersistedPointDeVenteTrans(returnedPointDeVenteTrans));

        insertedPointDeVenteTrans = returnedPointDeVenteTrans;
    }

    @Test
    @Transactional
    void createPointDeVenteTransWithExistingId() throws Exception {
        // Create the PointDeVenteTrans with an existing ID
        pointDeVenteTrans.setId(1);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPointDeVenteTransMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pointDeVenteTrans)))
            .andExpect(status().isBadRequest());

        // Validate the PointDeVenteTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPointDeVenteTrans() throws Exception {
        // Initialize the database
        insertedPointDeVenteTrans = pointDeVenteTransRepository.saveAndFlush(pointDeVenteTrans);

        // Get all the pointDeVenteTransList
        restPointDeVenteTransMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pointDeVenteTrans.getId().intValue())))
            .andExpect(jsonPath("$.[*].rvcRef").value(hasItem(DEFAULT_RVC_REF)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].locRef").value(hasItem(DEFAULT_LOC_REF)))
            .andExpect(jsonPath("$.[*].orgShortName").value(hasItem(DEFAULT_ORG_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }

    @Test
    @Transactional
    void getPointDeVenteTrans() throws Exception {
        // Initialize the database
        insertedPointDeVenteTrans = pointDeVenteTransRepository.saveAndFlush(pointDeVenteTrans);

        // Get the pointDeVenteTrans
        restPointDeVenteTransMockMvc
            .perform(get(ENTITY_API_URL_ID, pointDeVenteTrans.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pointDeVenteTrans.getId().intValue()))
            .andExpect(jsonPath("$.rvcRef").value(DEFAULT_RVC_REF))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.locRef").value(DEFAULT_LOC_REF))
            .andExpect(jsonPath("$.orgShortName").value(DEFAULT_ORG_SHORT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }

    @Test
    @Transactional
    void getNonExistingPointDeVenteTrans() throws Exception {
        // Get the pointDeVenteTrans
        restPointDeVenteTransMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPointDeVenteTrans() throws Exception {
        // Initialize the database
        insertedPointDeVenteTrans = pointDeVenteTransRepository.saveAndFlush(pointDeVenteTrans);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pointDeVenteTrans
        PointDeVenteTrans updatedPointDeVenteTrans = pointDeVenteTransRepository.findById(pointDeVenteTrans.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPointDeVenteTrans are not directly saved in db
        em.detach(updatedPointDeVenteTrans);
        updatedPointDeVenteTrans
            .rvcRef(UPDATED_RVC_REF)
            .name(UPDATED_NAME)
            .locRef(UPDATED_LOC_REF)
            .orgShortName(UPDATED_ORG_SHORT_NAME)
            .address(UPDATED_ADDRESS);

        restPointDeVenteTransMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPointDeVenteTrans.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPointDeVenteTrans))
            )
            .andExpect(status().isOk());

        // Validate the PointDeVenteTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPointDeVenteTransToMatchAllProperties(updatedPointDeVenteTrans);
    }

    @Test
    @Transactional
    void putNonExistingPointDeVenteTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pointDeVenteTrans.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointDeVenteTransMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pointDeVenteTrans.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pointDeVenteTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointDeVenteTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPointDeVenteTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pointDeVenteTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointDeVenteTransMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pointDeVenteTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointDeVenteTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPointDeVenteTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pointDeVenteTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointDeVenteTransMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pointDeVenteTrans)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PointDeVenteTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePointDeVenteTransWithPatch() throws Exception {
        // Initialize the database
        insertedPointDeVenteTrans = pointDeVenteTransRepository.saveAndFlush(pointDeVenteTrans);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pointDeVenteTrans using partial update
        PointDeVenteTrans partialUpdatedPointDeVenteTrans = new PointDeVenteTrans();
        partialUpdatedPointDeVenteTrans.setId(pointDeVenteTrans.getId());

        partialUpdatedPointDeVenteTrans.rvcRef(UPDATED_RVC_REF).address(UPDATED_ADDRESS);

        restPointDeVenteTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPointDeVenteTrans.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPointDeVenteTrans))
            )
            .andExpect(status().isOk());

        // Validate the PointDeVenteTrans in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPointDeVenteTransUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPointDeVenteTrans, pointDeVenteTrans),
            getPersistedPointDeVenteTrans(pointDeVenteTrans)
        );
    }

    @Test
    @Transactional
    void fullUpdatePointDeVenteTransWithPatch() throws Exception {
        // Initialize the database
        insertedPointDeVenteTrans = pointDeVenteTransRepository.saveAndFlush(pointDeVenteTrans);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pointDeVenteTrans using partial update
        PointDeVenteTrans partialUpdatedPointDeVenteTrans = new PointDeVenteTrans();
        partialUpdatedPointDeVenteTrans.setId(pointDeVenteTrans.getId());

        partialUpdatedPointDeVenteTrans
            .rvcRef(UPDATED_RVC_REF)
            .name(UPDATED_NAME)
            .locRef(UPDATED_LOC_REF)
            .orgShortName(UPDATED_ORG_SHORT_NAME)
            .address(UPDATED_ADDRESS);

        restPointDeVenteTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPointDeVenteTrans.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPointDeVenteTrans))
            )
            .andExpect(status().isOk());

        // Validate the PointDeVenteTrans in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPointDeVenteTransUpdatableFieldsEquals(
            partialUpdatedPointDeVenteTrans,
            getPersistedPointDeVenteTrans(partialUpdatedPointDeVenteTrans)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPointDeVenteTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pointDeVenteTrans.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointDeVenteTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pointDeVenteTrans.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pointDeVenteTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointDeVenteTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPointDeVenteTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pointDeVenteTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointDeVenteTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pointDeVenteTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointDeVenteTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPointDeVenteTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pointDeVenteTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointDeVenteTransMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pointDeVenteTrans)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PointDeVenteTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePointDeVenteTrans() throws Exception {
        // Initialize the database
        insertedPointDeVenteTrans = pointDeVenteTransRepository.saveAndFlush(pointDeVenteTrans);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the pointDeVenteTrans
        restPointDeVenteTransMockMvc
            .perform(delete(ENTITY_API_URL_ID, pointDeVenteTrans.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return pointDeVenteTransRepository.count();
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

    protected PointDeVenteTrans getPersistedPointDeVenteTrans(PointDeVenteTrans pointDeVenteTrans) {
        return pointDeVenteTransRepository.findById(pointDeVenteTrans.getId()).orElseThrow();
    }

    protected void assertPersistedPointDeVenteTransToMatchAllProperties(PointDeVenteTrans expectedPointDeVenteTrans) {
        assertPointDeVenteTransAllPropertiesEquals(expectedPointDeVenteTrans, getPersistedPointDeVenteTrans(expectedPointDeVenteTrans));
    }

    protected void assertPersistedPointDeVenteTransToMatchUpdatableProperties(PointDeVenteTrans expectedPointDeVenteTrans) {
        assertPointDeVenteTransAllUpdatablePropertiesEquals(
            expectedPointDeVenteTrans,
            getPersistedPointDeVenteTrans(expectedPointDeVenteTrans)
        );
    }
}
