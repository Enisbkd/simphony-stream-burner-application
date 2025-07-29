package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.PointDeVenteCnCAsserts.*;
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
import mc.sbm.simphonycloud.domain.PointDeVenteCnC;
import mc.sbm.simphonycloud.repository.PointDeVenteCnCRepository;
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
 * Integration tests for the {@link PointDeVenteCnCResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PointDeVenteCnCResourceIT {

    private static final Integer DEFAULT_LOC_HIER_UNIT_ID = 1;
    private static final Integer UPDATED_LOC_HIER_UNIT_ID = 2;

    private static final Integer DEFAULT_LOC_OBJ_NUM = 1;
    private static final Integer UPDATED_LOC_OBJ_NUM = 2;

    private static final Integer DEFAULT_RVC_ID = 1;
    private static final Integer UPDATED_RVC_ID = 2;

    private static final Integer DEFAULT_KDS_CONTROLLER_ID = 1;
    private static final Integer UPDATED_KDS_CONTROLLER_ID = 2;

    private static final Integer DEFAULT_HIER_UNIT_ID = 1;
    private static final Integer UPDATED_HIER_UNIT_ID = 2;

    private static final Integer DEFAULT_OBJECT_NUM = 1;
    private static final Integer UPDATED_OBJECT_NUM = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_EXTENSIONS = "AAAAAAAAAA";
    private static final String UPDATED_DATA_EXTENSIONS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/point-de-vente-cn-cs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PointDeVenteCnCRepository pointDeVenteCnCRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPointDeVenteCnCMockMvc;

    private PointDeVenteCnC pointDeVenteCnC;

    private PointDeVenteCnC insertedPointDeVenteCnC;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointDeVenteCnC createEntity() {
        return new PointDeVenteCnC()
            .locHierUnitId(DEFAULT_LOC_HIER_UNIT_ID)
            .locObjNum(DEFAULT_LOC_OBJ_NUM)
            .rvcId(DEFAULT_RVC_ID)
            .kdsControllerId(DEFAULT_KDS_CONTROLLER_ID)
            .hierUnitId(DEFAULT_HIER_UNIT_ID)
            .objectNum(DEFAULT_OBJECT_NUM)
            .name(DEFAULT_NAME)
            .dataExtensions(DEFAULT_DATA_EXTENSIONS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointDeVenteCnC createUpdatedEntity() {
        return new PointDeVenteCnC()
            .locHierUnitId(UPDATED_LOC_HIER_UNIT_ID)
            .locObjNum(UPDATED_LOC_OBJ_NUM)
            .rvcId(UPDATED_RVC_ID)
            .kdsControllerId(UPDATED_KDS_CONTROLLER_ID)
            .hierUnitId(UPDATED_HIER_UNIT_ID)
            .objectNum(UPDATED_OBJECT_NUM)
            .name(UPDATED_NAME)
            .dataExtensions(UPDATED_DATA_EXTENSIONS);
    }

    @BeforeEach
    void initTest() {
        pointDeVenteCnC = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPointDeVenteCnC != null) {
            pointDeVenteCnCRepository.delete(insertedPointDeVenteCnC);
            insertedPointDeVenteCnC = null;
        }
    }

    @Test
    @Transactional
    void createPointDeVenteCnC() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PointDeVenteCnC
        var returnedPointDeVenteCnC = om.readValue(
            restPointDeVenteCnCMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pointDeVenteCnC)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PointDeVenteCnC.class
        );

        // Validate the PointDeVenteCnC in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPointDeVenteCnCUpdatableFieldsEquals(returnedPointDeVenteCnC, getPersistedPointDeVenteCnC(returnedPointDeVenteCnC));

        insertedPointDeVenteCnC = returnedPointDeVenteCnC;
    }

    @Test
    @Transactional
    void createPointDeVenteCnCWithExistingId() throws Exception {
        // Create the PointDeVenteCnC with an existing ID
        pointDeVenteCnC.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPointDeVenteCnCMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pointDeVenteCnC)))
            .andExpect(status().isBadRequest());

        // Validate the PointDeVenteCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPointDeVenteCnCS() throws Exception {
        // Initialize the database
        insertedPointDeVenteCnC = pointDeVenteCnCRepository.saveAndFlush(pointDeVenteCnC);

        // Get all the pointDeVenteCnCList
        restPointDeVenteCnCMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pointDeVenteCnC.getId().intValue())))
            .andExpect(jsonPath("$.[*].locHierUnitId").value(hasItem(DEFAULT_LOC_HIER_UNIT_ID)))
            .andExpect(jsonPath("$.[*].locObjNum").value(hasItem(DEFAULT_LOC_OBJ_NUM)))
            .andExpect(jsonPath("$.[*].rvcId").value(hasItem(DEFAULT_RVC_ID)))
            .andExpect(jsonPath("$.[*].kdsControllerId").value(hasItem(DEFAULT_KDS_CONTROLLER_ID)))
            .andExpect(jsonPath("$.[*].hierUnitId").value(hasItem(DEFAULT_HIER_UNIT_ID)))
            .andExpect(jsonPath("$.[*].objectNum").value(hasItem(DEFAULT_OBJECT_NUM)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].dataExtensions").value(hasItem(DEFAULT_DATA_EXTENSIONS)));
    }

    @Test
    @Transactional
    void getPointDeVenteCnC() throws Exception {
        // Initialize the database
        insertedPointDeVenteCnC = pointDeVenteCnCRepository.saveAndFlush(pointDeVenteCnC);

        // Get the pointDeVenteCnC
        restPointDeVenteCnCMockMvc
            .perform(get(ENTITY_API_URL_ID, pointDeVenteCnC.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pointDeVenteCnC.getId().intValue()))
            .andExpect(jsonPath("$.locHierUnitId").value(DEFAULT_LOC_HIER_UNIT_ID))
            .andExpect(jsonPath("$.locObjNum").value(DEFAULT_LOC_OBJ_NUM))
            .andExpect(jsonPath("$.rvcId").value(DEFAULT_RVC_ID))
            .andExpect(jsonPath("$.kdsControllerId").value(DEFAULT_KDS_CONTROLLER_ID))
            .andExpect(jsonPath("$.hierUnitId").value(DEFAULT_HIER_UNIT_ID))
            .andExpect(jsonPath("$.objectNum").value(DEFAULT_OBJECT_NUM))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.dataExtensions").value(DEFAULT_DATA_EXTENSIONS));
    }

    @Test
    @Transactional
    void getNonExistingPointDeVenteCnC() throws Exception {
        // Get the pointDeVenteCnC
        restPointDeVenteCnCMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPointDeVenteCnC() throws Exception {
        // Initialize the database
        insertedPointDeVenteCnC = pointDeVenteCnCRepository.saveAndFlush(pointDeVenteCnC);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pointDeVenteCnC
        PointDeVenteCnC updatedPointDeVenteCnC = pointDeVenteCnCRepository.findById(pointDeVenteCnC.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPointDeVenteCnC are not directly saved in db
        em.detach(updatedPointDeVenteCnC);
        updatedPointDeVenteCnC
            .locHierUnitId(UPDATED_LOC_HIER_UNIT_ID)
            .locObjNum(UPDATED_LOC_OBJ_NUM)
            .rvcId(UPDATED_RVC_ID)
            .kdsControllerId(UPDATED_KDS_CONTROLLER_ID)
            .hierUnitId(UPDATED_HIER_UNIT_ID)
            .objectNum(UPDATED_OBJECT_NUM)
            .name(UPDATED_NAME)
            .dataExtensions(UPDATED_DATA_EXTENSIONS);

        restPointDeVenteCnCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPointDeVenteCnC.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPointDeVenteCnC))
            )
            .andExpect(status().isOk());

        // Validate the PointDeVenteCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPointDeVenteCnCToMatchAllProperties(updatedPointDeVenteCnC);
    }

    @Test
    @Transactional
    void putNonExistingPointDeVenteCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pointDeVenteCnC.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointDeVenteCnCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pointDeVenteCnC.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pointDeVenteCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointDeVenteCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPointDeVenteCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pointDeVenteCnC.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointDeVenteCnCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pointDeVenteCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointDeVenteCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPointDeVenteCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pointDeVenteCnC.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointDeVenteCnCMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pointDeVenteCnC)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PointDeVenteCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePointDeVenteCnCWithPatch() throws Exception {
        // Initialize the database
        insertedPointDeVenteCnC = pointDeVenteCnCRepository.saveAndFlush(pointDeVenteCnC);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pointDeVenteCnC using partial update
        PointDeVenteCnC partialUpdatedPointDeVenteCnC = new PointDeVenteCnC();
        partialUpdatedPointDeVenteCnC.setId(pointDeVenteCnC.getId());

        partialUpdatedPointDeVenteCnC
            .locHierUnitId(UPDATED_LOC_HIER_UNIT_ID)
            .kdsControllerId(UPDATED_KDS_CONTROLLER_ID)
            .name(UPDATED_NAME)
            .dataExtensions(UPDATED_DATA_EXTENSIONS);

        restPointDeVenteCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPointDeVenteCnC.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPointDeVenteCnC))
            )
            .andExpect(status().isOk());

        // Validate the PointDeVenteCnC in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPointDeVenteCnCUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPointDeVenteCnC, pointDeVenteCnC),
            getPersistedPointDeVenteCnC(pointDeVenteCnC)
        );
    }

    @Test
    @Transactional
    void fullUpdatePointDeVenteCnCWithPatch() throws Exception {
        // Initialize the database
        insertedPointDeVenteCnC = pointDeVenteCnCRepository.saveAndFlush(pointDeVenteCnC);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pointDeVenteCnC using partial update
        PointDeVenteCnC partialUpdatedPointDeVenteCnC = new PointDeVenteCnC();
        partialUpdatedPointDeVenteCnC.setId(pointDeVenteCnC.getId());

        partialUpdatedPointDeVenteCnC
            .locHierUnitId(UPDATED_LOC_HIER_UNIT_ID)
            .locObjNum(UPDATED_LOC_OBJ_NUM)
            .rvcId(UPDATED_RVC_ID)
            .kdsControllerId(UPDATED_KDS_CONTROLLER_ID)
            .hierUnitId(UPDATED_HIER_UNIT_ID)
            .objectNum(UPDATED_OBJECT_NUM)
            .name(UPDATED_NAME)
            .dataExtensions(UPDATED_DATA_EXTENSIONS);

        restPointDeVenteCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPointDeVenteCnC.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPointDeVenteCnC))
            )
            .andExpect(status().isOk());

        // Validate the PointDeVenteCnC in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPointDeVenteCnCUpdatableFieldsEquals(
            partialUpdatedPointDeVenteCnC,
            getPersistedPointDeVenteCnC(partialUpdatedPointDeVenteCnC)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPointDeVenteCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pointDeVenteCnC.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointDeVenteCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pointDeVenteCnC.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pointDeVenteCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointDeVenteCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPointDeVenteCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pointDeVenteCnC.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointDeVenteCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pointDeVenteCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointDeVenteCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPointDeVenteCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pointDeVenteCnC.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointDeVenteCnCMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pointDeVenteCnC)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PointDeVenteCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePointDeVenteCnC() throws Exception {
        // Initialize the database
        insertedPointDeVenteCnC = pointDeVenteCnCRepository.saveAndFlush(pointDeVenteCnC);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the pointDeVenteCnC
        restPointDeVenteCnCMockMvc
            .perform(delete(ENTITY_API_URL_ID, pointDeVenteCnC.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return pointDeVenteCnCRepository.count();
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

    protected PointDeVenteCnC getPersistedPointDeVenteCnC(PointDeVenteCnC pointDeVenteCnC) {
        return pointDeVenteCnCRepository.findById(pointDeVenteCnC.getId()).orElseThrow();
    }

    protected void assertPersistedPointDeVenteCnCToMatchAllProperties(PointDeVenteCnC expectedPointDeVenteCnC) {
        assertPointDeVenteCnCAllPropertiesEquals(expectedPointDeVenteCnC, getPersistedPointDeVenteCnC(expectedPointDeVenteCnC));
    }

    protected void assertPersistedPointDeVenteCnCToMatchUpdatableProperties(PointDeVenteCnC expectedPointDeVenteCnC) {
        assertPointDeVenteCnCAllUpdatablePropertiesEquals(expectedPointDeVenteCnC, getPersistedPointDeVenteCnC(expectedPointDeVenteCnC));
    }
}
