package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.MenuItemMastersCnCAsserts.*;
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
import mc.sbm.simphonycloud.domain.MenuItemMastersCnC;
import mc.sbm.simphonycloud.repository.MenuItemMastersCnCRepository;
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
 * Integration tests for the {@link MenuItemMastersCnCResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MenuItemMastersCnCResourceIT {

    private static final Integer DEFAULT_HIER_UNIT_ID = 1;
    private static final Integer UPDATED_HIER_UNIT_ID = 2;

    private static final Integer DEFAULT_MENU_ITEM_MASTER_ID = 1;
    private static final Integer UPDATED_MENU_ITEM_MASTER_ID = 2;

    private static final Integer DEFAULT_FAMILY_GROUP_OBJECT_NUM = 1;
    private static final Integer UPDATED_FAMILY_GROUP_OBJECT_NUM = 2;

    private static final Integer DEFAULT_MAJOR_GROUP_OBJECT_NUM = 1;
    private static final Integer UPDATED_MAJOR_GROUP_OBJECT_NUM = 2;

    private static final Integer DEFAULT_REPORT_GROUP_OBJECT_NUM = 1;
    private static final Integer UPDATED_REPORT_GROUP_OBJECT_NUM = 2;

    private static final String DEFAULT_EXTERNAL_REFERENCE_1 = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_REFERENCE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_EXTERNAL_REFERENCE_2 = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_REFERENCE_2 = "BBBBBBBBBB";

    private static final Integer DEFAULT_OBJECT_NUM = 1;
    private static final Integer UPDATED_OBJECT_NUM = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/menu-item-masters-cn-cs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MenuItemMastersCnCRepository menuItemMastersCnCRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMenuItemMastersCnCMockMvc;

    private MenuItemMastersCnC menuItemMastersCnC;

    private MenuItemMastersCnC insertedMenuItemMastersCnC;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MenuItemMastersCnC createEntity() {
        return new MenuItemMastersCnC()
            .hierUnitId(DEFAULT_HIER_UNIT_ID)
            .menuItemMasterId(DEFAULT_MENU_ITEM_MASTER_ID)
            .familyGroupObjectNum(DEFAULT_FAMILY_GROUP_OBJECT_NUM)
            .majorGroupObjectNum(DEFAULT_MAJOR_GROUP_OBJECT_NUM)
            .reportGroupObjectNum(DEFAULT_REPORT_GROUP_OBJECT_NUM)
            .externalReference1(DEFAULT_EXTERNAL_REFERENCE_1)
            .externalReference2(DEFAULT_EXTERNAL_REFERENCE_2)
            .objectNum(DEFAULT_OBJECT_NUM)
            .name(DEFAULT_NAME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MenuItemMastersCnC createUpdatedEntity() {
        return new MenuItemMastersCnC()
            .hierUnitId(UPDATED_HIER_UNIT_ID)
            .menuItemMasterId(UPDATED_MENU_ITEM_MASTER_ID)
            .familyGroupObjectNum(UPDATED_FAMILY_GROUP_OBJECT_NUM)
            .majorGroupObjectNum(UPDATED_MAJOR_GROUP_OBJECT_NUM)
            .reportGroupObjectNum(UPDATED_REPORT_GROUP_OBJECT_NUM)
            .externalReference1(UPDATED_EXTERNAL_REFERENCE_1)
            .externalReference2(UPDATED_EXTERNAL_REFERENCE_2)
            .objectNum(UPDATED_OBJECT_NUM)
            .name(UPDATED_NAME);
    }

    @BeforeEach
    void initTest() {
        menuItemMastersCnC = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedMenuItemMastersCnC != null) {
            menuItemMastersCnCRepository.delete(insertedMenuItemMastersCnC);
            insertedMenuItemMastersCnC = null;
        }
    }

    @Test
    @Transactional
    void createMenuItemMastersCnC() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MenuItemMastersCnC
        var returnedMenuItemMastersCnC = om.readValue(
            restMenuItemMastersCnCMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(menuItemMastersCnC)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MenuItemMastersCnC.class
        );

        // Validate the MenuItemMastersCnC in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMenuItemMastersCnCUpdatableFieldsEquals(
            returnedMenuItemMastersCnC,
            getPersistedMenuItemMastersCnC(returnedMenuItemMastersCnC)
        );

        insertedMenuItemMastersCnC = returnedMenuItemMastersCnC;
    }

    @Test
    @Transactional
    void createMenuItemMastersCnCWithExistingId() throws Exception {
        // Create the MenuItemMastersCnC with an existing ID
        menuItemMastersCnC.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMenuItemMastersCnCMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(menuItemMastersCnC)))
            .andExpect(status().isBadRequest());

        // Validate the MenuItemMastersCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMenuItemMastersCnCS() throws Exception {
        // Initialize the database
        insertedMenuItemMastersCnC = menuItemMastersCnCRepository.saveAndFlush(menuItemMastersCnC);

        // Get all the menuItemMastersCnCList
        restMenuItemMastersCnCMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(menuItemMastersCnC.getId().intValue())))
            .andExpect(jsonPath("$.[*].hierUnitId").value(hasItem(DEFAULT_HIER_UNIT_ID)))
            .andExpect(jsonPath("$.[*].menuItemMasterId").value(hasItem(DEFAULT_MENU_ITEM_MASTER_ID)))
            .andExpect(jsonPath("$.[*].familyGroupObjectNum").value(hasItem(DEFAULT_FAMILY_GROUP_OBJECT_NUM)))
            .andExpect(jsonPath("$.[*].majorGroupObjectNum").value(hasItem(DEFAULT_MAJOR_GROUP_OBJECT_NUM)))
            .andExpect(jsonPath("$.[*].reportGroupObjectNum").value(hasItem(DEFAULT_REPORT_GROUP_OBJECT_NUM)))
            .andExpect(jsonPath("$.[*].externalReference1").value(hasItem(DEFAULT_EXTERNAL_REFERENCE_1)))
            .andExpect(jsonPath("$.[*].externalReference2").value(hasItem(DEFAULT_EXTERNAL_REFERENCE_2)))
            .andExpect(jsonPath("$.[*].objectNum").value(hasItem(DEFAULT_OBJECT_NUM)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getMenuItemMastersCnC() throws Exception {
        // Initialize the database
        insertedMenuItemMastersCnC = menuItemMastersCnCRepository.saveAndFlush(menuItemMastersCnC);

        // Get the menuItemMastersCnC
        restMenuItemMastersCnCMockMvc
            .perform(get(ENTITY_API_URL_ID, menuItemMastersCnC.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(menuItemMastersCnC.getId().intValue()))
            .andExpect(jsonPath("$.hierUnitId").value(DEFAULT_HIER_UNIT_ID))
            .andExpect(jsonPath("$.menuItemMasterId").value(DEFAULT_MENU_ITEM_MASTER_ID))
            .andExpect(jsonPath("$.familyGroupObjectNum").value(DEFAULT_FAMILY_GROUP_OBJECT_NUM))
            .andExpect(jsonPath("$.majorGroupObjectNum").value(DEFAULT_MAJOR_GROUP_OBJECT_NUM))
            .andExpect(jsonPath("$.reportGroupObjectNum").value(DEFAULT_REPORT_GROUP_OBJECT_NUM))
            .andExpect(jsonPath("$.externalReference1").value(DEFAULT_EXTERNAL_REFERENCE_1))
            .andExpect(jsonPath("$.externalReference2").value(DEFAULT_EXTERNAL_REFERENCE_2))
            .andExpect(jsonPath("$.objectNum").value(DEFAULT_OBJECT_NUM))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingMenuItemMastersCnC() throws Exception {
        // Get the menuItemMastersCnC
        restMenuItemMastersCnCMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMenuItemMastersCnC() throws Exception {
        // Initialize the database
        insertedMenuItemMastersCnC = menuItemMastersCnCRepository.saveAndFlush(menuItemMastersCnC);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the menuItemMastersCnC
        MenuItemMastersCnC updatedMenuItemMastersCnC = menuItemMastersCnCRepository.findById(menuItemMastersCnC.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMenuItemMastersCnC are not directly saved in db
        em.detach(updatedMenuItemMastersCnC);
        updatedMenuItemMastersCnC
            .hierUnitId(UPDATED_HIER_UNIT_ID)
            .menuItemMasterId(UPDATED_MENU_ITEM_MASTER_ID)
            .familyGroupObjectNum(UPDATED_FAMILY_GROUP_OBJECT_NUM)
            .majorGroupObjectNum(UPDATED_MAJOR_GROUP_OBJECT_NUM)
            .reportGroupObjectNum(UPDATED_REPORT_GROUP_OBJECT_NUM)
            .externalReference1(UPDATED_EXTERNAL_REFERENCE_1)
            .externalReference2(UPDATED_EXTERNAL_REFERENCE_2)
            .objectNum(UPDATED_OBJECT_NUM)
            .name(UPDATED_NAME);

        restMenuItemMastersCnCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMenuItemMastersCnC.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMenuItemMastersCnC))
            )
            .andExpect(status().isOk());

        // Validate the MenuItemMastersCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMenuItemMastersCnCToMatchAllProperties(updatedMenuItemMastersCnC);
    }

    @Test
    @Transactional
    void putNonExistingMenuItemMastersCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        menuItemMastersCnC.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMenuItemMastersCnCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, menuItemMastersCnC.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(menuItemMastersCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the MenuItemMastersCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMenuItemMastersCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        menuItemMastersCnC.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuItemMastersCnCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(menuItemMastersCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the MenuItemMastersCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMenuItemMastersCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        menuItemMastersCnC.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuItemMastersCnCMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(menuItemMastersCnC)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MenuItemMastersCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMenuItemMastersCnCWithPatch() throws Exception {
        // Initialize the database
        insertedMenuItemMastersCnC = menuItemMastersCnCRepository.saveAndFlush(menuItemMastersCnC);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the menuItemMastersCnC using partial update
        MenuItemMastersCnC partialUpdatedMenuItemMastersCnC = new MenuItemMastersCnC();
        partialUpdatedMenuItemMastersCnC.setId(menuItemMastersCnC.getId());

        partialUpdatedMenuItemMastersCnC
            .hierUnitId(UPDATED_HIER_UNIT_ID)
            .menuItemMasterId(UPDATED_MENU_ITEM_MASTER_ID)
            .externalReference2(UPDATED_EXTERNAL_REFERENCE_2)
            .name(UPDATED_NAME);

        restMenuItemMastersCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMenuItemMastersCnC.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMenuItemMastersCnC))
            )
            .andExpect(status().isOk());

        // Validate the MenuItemMastersCnC in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMenuItemMastersCnCUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMenuItemMastersCnC, menuItemMastersCnC),
            getPersistedMenuItemMastersCnC(menuItemMastersCnC)
        );
    }

    @Test
    @Transactional
    void fullUpdateMenuItemMastersCnCWithPatch() throws Exception {
        // Initialize the database
        insertedMenuItemMastersCnC = menuItemMastersCnCRepository.saveAndFlush(menuItemMastersCnC);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the menuItemMastersCnC using partial update
        MenuItemMastersCnC partialUpdatedMenuItemMastersCnC = new MenuItemMastersCnC();
        partialUpdatedMenuItemMastersCnC.setId(menuItemMastersCnC.getId());

        partialUpdatedMenuItemMastersCnC
            .hierUnitId(UPDATED_HIER_UNIT_ID)
            .menuItemMasterId(UPDATED_MENU_ITEM_MASTER_ID)
            .familyGroupObjectNum(UPDATED_FAMILY_GROUP_OBJECT_NUM)
            .majorGroupObjectNum(UPDATED_MAJOR_GROUP_OBJECT_NUM)
            .reportGroupObjectNum(UPDATED_REPORT_GROUP_OBJECT_NUM)
            .externalReference1(UPDATED_EXTERNAL_REFERENCE_1)
            .externalReference2(UPDATED_EXTERNAL_REFERENCE_2)
            .objectNum(UPDATED_OBJECT_NUM)
            .name(UPDATED_NAME);

        restMenuItemMastersCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMenuItemMastersCnC.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMenuItemMastersCnC))
            )
            .andExpect(status().isOk());

        // Validate the MenuItemMastersCnC in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMenuItemMastersCnCUpdatableFieldsEquals(
            partialUpdatedMenuItemMastersCnC,
            getPersistedMenuItemMastersCnC(partialUpdatedMenuItemMastersCnC)
        );
    }

    @Test
    @Transactional
    void patchNonExistingMenuItemMastersCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        menuItemMastersCnC.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMenuItemMastersCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, menuItemMastersCnC.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(menuItemMastersCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the MenuItemMastersCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMenuItemMastersCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        menuItemMastersCnC.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuItemMastersCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(menuItemMastersCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the MenuItemMastersCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMenuItemMastersCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        menuItemMastersCnC.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuItemMastersCnCMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(menuItemMastersCnC)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MenuItemMastersCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMenuItemMastersCnC() throws Exception {
        // Initialize the database
        insertedMenuItemMastersCnC = menuItemMastersCnCRepository.saveAndFlush(menuItemMastersCnC);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the menuItemMastersCnC
        restMenuItemMastersCnCMockMvc
            .perform(delete(ENTITY_API_URL_ID, menuItemMastersCnC.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return menuItemMastersCnCRepository.count();
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

    protected MenuItemMastersCnC getPersistedMenuItemMastersCnC(MenuItemMastersCnC menuItemMastersCnC) {
        return menuItemMastersCnCRepository.findById(menuItemMastersCnC.getId()).orElseThrow();
    }

    protected void assertPersistedMenuItemMastersCnCToMatchAllProperties(MenuItemMastersCnC expectedMenuItemMastersCnC) {
        assertMenuItemMastersCnCAllPropertiesEquals(expectedMenuItemMastersCnC, getPersistedMenuItemMastersCnC(expectedMenuItemMastersCnC));
    }

    protected void assertPersistedMenuItemMastersCnCToMatchUpdatableProperties(MenuItemMastersCnC expectedMenuItemMastersCnC) {
        assertMenuItemMastersCnCAllUpdatablePropertiesEquals(
            expectedMenuItemMastersCnC,
            getPersistedMenuItemMastersCnC(expectedMenuItemMastersCnC)
        );
    }
}
