package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.MenuItemPricesCnCAsserts.*;
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
import mc.sbm.simphonycloud.domain.MenuItemPricesCnC;
import mc.sbm.simphonycloud.repository.MenuItemPricesCnCRepository;
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
 * Integration tests for the {@link MenuItemPricesCnCResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MenuItemPricesCnCResourceIT {

    private static final Long DEFAULT_HIER_UNIT_ID = 1L;
    private static final Long UPDATED_HIER_UNIT_ID = 2L;

    private static final Long DEFAULT_MENU_ITEM_PRICE_ID = 1L;
    private static final Long UPDATED_MENU_ITEM_PRICE_ID = 2L;

    private static final Long DEFAULT_MENU_ITEM_MASTER_ID = 1L;
    private static final Long UPDATED_MENU_ITEM_MASTER_ID = 2L;

    private static final Long DEFAULT_MENU_ITEM_MASTER_OBJ_NUM = 1L;
    private static final Long UPDATED_MENU_ITEM_MASTER_OBJ_NUM = 2L;

    private static final Long DEFAULT_MENU_ITEM_DEFINITION_ID = 1L;
    private static final Long UPDATED_MENU_ITEM_DEFINITION_ID = 2L;

    private static final Integer DEFAULT_DEF_SEQUENCE_NUM = 1;
    private static final Integer UPDATED_DEF_SEQUENCE_NUM = 2;

    private static final String DEFAULT_EXTERNAL_REFERENCE_1 = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_REFERENCE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_EXTERNAL_REFERENCE_2 = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_REFERENCE_2 = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRICE_SEQUENCE_NUM = 1;
    private static final Integer UPDATED_PRICE_SEQUENCE_NUM = 2;

    private static final Integer DEFAULT_ACTIVE_ON_MENU_LEVEL = 1;
    private static final Integer UPDATED_ACTIVE_ON_MENU_LEVEL = 2;

    private static final String DEFAULT_EFFECTIVITY_GROUP_OBJ_NUM = "AAAAAAAAAA";
    private static final String UPDATED_EFFECTIVITY_GROUP_OBJ_NUM = "BBBBBBBBBB";

    private static final Double DEFAULT_PREP_COST = 1D;
    private static final Double UPDATED_PREP_COST = 2D;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final String DEFAULT_OPTIONS = "AAAAAAAAAA";
    private static final String UPDATED_OPTIONS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/menu-item-prices-cn-cs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MenuItemPricesCnCRepository menuItemPricesCnCRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMenuItemPricesCnCMockMvc;

    private MenuItemPricesCnC menuItemPricesCnC;

    private MenuItemPricesCnC insertedMenuItemPricesCnC;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MenuItemPricesCnC createEntity() {
        return new MenuItemPricesCnC()
            .hierUnitId(DEFAULT_HIER_UNIT_ID)
            .menuItemPriceId(DEFAULT_MENU_ITEM_PRICE_ID)
            .menuItemMasterId(DEFAULT_MENU_ITEM_MASTER_ID)
            .menuItemMasterObjNum(DEFAULT_MENU_ITEM_MASTER_OBJ_NUM)
            .menuItemDefinitionId(DEFAULT_MENU_ITEM_DEFINITION_ID)
            .defSequenceNum(DEFAULT_DEF_SEQUENCE_NUM)
            .externalReference1(DEFAULT_EXTERNAL_REFERENCE_1)
            .externalReference2(DEFAULT_EXTERNAL_REFERENCE_2)
            .priceSequenceNum(DEFAULT_PRICE_SEQUENCE_NUM)
            .activeOnMenuLevel(DEFAULT_ACTIVE_ON_MENU_LEVEL)
            .effectivityGroupObjNum(DEFAULT_EFFECTIVITY_GROUP_OBJ_NUM)
            .prepCost(DEFAULT_PREP_COST)
            .price(DEFAULT_PRICE)
            .options(DEFAULT_OPTIONS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MenuItemPricesCnC createUpdatedEntity() {
        return new MenuItemPricesCnC()
            .hierUnitId(UPDATED_HIER_UNIT_ID)
            .menuItemPriceId(UPDATED_MENU_ITEM_PRICE_ID)
            .menuItemMasterId(UPDATED_MENU_ITEM_MASTER_ID)
            .menuItemMasterObjNum(UPDATED_MENU_ITEM_MASTER_OBJ_NUM)
            .menuItemDefinitionId(UPDATED_MENU_ITEM_DEFINITION_ID)
            .defSequenceNum(UPDATED_DEF_SEQUENCE_NUM)
            .externalReference1(UPDATED_EXTERNAL_REFERENCE_1)
            .externalReference2(UPDATED_EXTERNAL_REFERENCE_2)
            .priceSequenceNum(UPDATED_PRICE_SEQUENCE_NUM)
            .activeOnMenuLevel(UPDATED_ACTIVE_ON_MENU_LEVEL)
            .effectivityGroupObjNum(UPDATED_EFFECTIVITY_GROUP_OBJ_NUM)
            .prepCost(UPDATED_PREP_COST)
            .price(UPDATED_PRICE)
            .options(UPDATED_OPTIONS);
    }

    @BeforeEach
    void initTest() {
        menuItemPricesCnC = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedMenuItemPricesCnC != null) {
            menuItemPricesCnCRepository.delete(insertedMenuItemPricesCnC);
            insertedMenuItemPricesCnC = null;
        }
    }

    @Test
    @Transactional
    void createMenuItemPricesCnC() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MenuItemPricesCnC
        var returnedMenuItemPricesCnC = om.readValue(
            restMenuItemPricesCnCMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(menuItemPricesCnC)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MenuItemPricesCnC.class
        );

        // Validate the MenuItemPricesCnC in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMenuItemPricesCnCUpdatableFieldsEquals(returnedMenuItemPricesCnC, getPersistedMenuItemPricesCnC(returnedMenuItemPricesCnC));

        insertedMenuItemPricesCnC = returnedMenuItemPricesCnC;
    }

    @Test
    @Transactional
    void createMenuItemPricesCnCWithExistingId() throws Exception {
        // Create the MenuItemPricesCnC with an existing ID
        menuItemPricesCnC.setId(1);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMenuItemPricesCnCMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(menuItemPricesCnC)))
            .andExpect(status().isBadRequest());

        // Validate the MenuItemPricesCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMenuItemPricesCnCS() throws Exception {
        // Initialize the database
        insertedMenuItemPricesCnC = menuItemPricesCnCRepository.saveAndFlush(menuItemPricesCnC);

        // Get all the menuItemPricesCnCList
        restMenuItemPricesCnCMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(menuItemPricesCnC.getId().intValue())))
            .andExpect(jsonPath("$.[*].hierUnitId").value(hasItem(DEFAULT_HIER_UNIT_ID.intValue())))
            .andExpect(jsonPath("$.[*].menuItemPriceId").value(hasItem(DEFAULT_MENU_ITEM_PRICE_ID.intValue())))
            .andExpect(jsonPath("$.[*].menuItemMasterId").value(hasItem(DEFAULT_MENU_ITEM_MASTER_ID.intValue())))
            .andExpect(jsonPath("$.[*].menuItemMasterObjNum").value(hasItem(DEFAULT_MENU_ITEM_MASTER_OBJ_NUM.intValue())))
            .andExpect(jsonPath("$.[*].menuItemDefinitionId").value(hasItem(DEFAULT_MENU_ITEM_DEFINITION_ID.intValue())))
            .andExpect(jsonPath("$.[*].defSequenceNum").value(hasItem(DEFAULT_DEF_SEQUENCE_NUM)))
            .andExpect(jsonPath("$.[*].externalReference1").value(hasItem(DEFAULT_EXTERNAL_REFERENCE_1)))
            .andExpect(jsonPath("$.[*].externalReference2").value(hasItem(DEFAULT_EXTERNAL_REFERENCE_2)))
            .andExpect(jsonPath("$.[*].priceSequenceNum").value(hasItem(DEFAULT_PRICE_SEQUENCE_NUM)))
            .andExpect(jsonPath("$.[*].activeOnMenuLevel").value(hasItem(DEFAULT_ACTIVE_ON_MENU_LEVEL)))
            .andExpect(jsonPath("$.[*].effectivityGroupObjNum").value(hasItem(DEFAULT_EFFECTIVITY_GROUP_OBJ_NUM)))
            .andExpect(jsonPath("$.[*].prepCost").value(hasItem(DEFAULT_PREP_COST)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].options").value(hasItem(DEFAULT_OPTIONS)));
    }

    @Test
    @Transactional
    void getMenuItemPricesCnC() throws Exception {
        // Initialize the database
        insertedMenuItemPricesCnC = menuItemPricesCnCRepository.saveAndFlush(menuItemPricesCnC);

        // Get the menuItemPricesCnC
        restMenuItemPricesCnCMockMvc
            .perform(get(ENTITY_API_URL_ID, menuItemPricesCnC.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(menuItemPricesCnC.getId().intValue()))
            .andExpect(jsonPath("$.hierUnitId").value(DEFAULT_HIER_UNIT_ID.intValue()))
            .andExpect(jsonPath("$.menuItemPriceId").value(DEFAULT_MENU_ITEM_PRICE_ID.intValue()))
            .andExpect(jsonPath("$.menuItemMasterId").value(DEFAULT_MENU_ITEM_MASTER_ID.intValue()))
            .andExpect(jsonPath("$.menuItemMasterObjNum").value(DEFAULT_MENU_ITEM_MASTER_OBJ_NUM.intValue()))
            .andExpect(jsonPath("$.menuItemDefinitionId").value(DEFAULT_MENU_ITEM_DEFINITION_ID.intValue()))
            .andExpect(jsonPath("$.defSequenceNum").value(DEFAULT_DEF_SEQUENCE_NUM))
            .andExpect(jsonPath("$.externalReference1").value(DEFAULT_EXTERNAL_REFERENCE_1))
            .andExpect(jsonPath("$.externalReference2").value(DEFAULT_EXTERNAL_REFERENCE_2))
            .andExpect(jsonPath("$.priceSequenceNum").value(DEFAULT_PRICE_SEQUENCE_NUM))
            .andExpect(jsonPath("$.activeOnMenuLevel").value(DEFAULT_ACTIVE_ON_MENU_LEVEL))
            .andExpect(jsonPath("$.effectivityGroupObjNum").value(DEFAULT_EFFECTIVITY_GROUP_OBJ_NUM))
            .andExpect(jsonPath("$.prepCost").value(DEFAULT_PREP_COST))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.options").value(DEFAULT_OPTIONS));
    }

    @Test
    @Transactional
    void getNonExistingMenuItemPricesCnC() throws Exception {
        // Get the menuItemPricesCnC
        restMenuItemPricesCnCMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMenuItemPricesCnC() throws Exception {
        // Initialize the database
        insertedMenuItemPricesCnC = menuItemPricesCnCRepository.saveAndFlush(menuItemPricesCnC);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the menuItemPricesCnC
        MenuItemPricesCnC updatedMenuItemPricesCnC = menuItemPricesCnCRepository.findById(menuItemPricesCnC.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMenuItemPricesCnC are not directly saved in db
        em.detach(updatedMenuItemPricesCnC);
        updatedMenuItemPricesCnC
            .hierUnitId(UPDATED_HIER_UNIT_ID)
            .menuItemPriceId(UPDATED_MENU_ITEM_PRICE_ID)
            .menuItemMasterId(UPDATED_MENU_ITEM_MASTER_ID)
            .menuItemMasterObjNum(UPDATED_MENU_ITEM_MASTER_OBJ_NUM)
            .menuItemDefinitionId(UPDATED_MENU_ITEM_DEFINITION_ID)
            .defSequenceNum(UPDATED_DEF_SEQUENCE_NUM)
            .externalReference1(UPDATED_EXTERNAL_REFERENCE_1)
            .externalReference2(UPDATED_EXTERNAL_REFERENCE_2)
            .priceSequenceNum(UPDATED_PRICE_SEQUENCE_NUM)
            .activeOnMenuLevel(UPDATED_ACTIVE_ON_MENU_LEVEL)
            .effectivityGroupObjNum(UPDATED_EFFECTIVITY_GROUP_OBJ_NUM)
            .prepCost(UPDATED_PREP_COST)
            .price(UPDATED_PRICE)
            .options(UPDATED_OPTIONS);

        restMenuItemPricesCnCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMenuItemPricesCnC.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMenuItemPricesCnC))
            )
            .andExpect(status().isOk());

        // Validate the MenuItemPricesCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMenuItemPricesCnCToMatchAllProperties(updatedMenuItemPricesCnC);
    }

    @Test
    @Transactional
    void putNonExistingMenuItemPricesCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        menuItemPricesCnC.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMenuItemPricesCnCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, menuItemPricesCnC.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(menuItemPricesCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the MenuItemPricesCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMenuItemPricesCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        menuItemPricesCnC.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuItemPricesCnCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(menuItemPricesCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the MenuItemPricesCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMenuItemPricesCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        menuItemPricesCnC.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuItemPricesCnCMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(menuItemPricesCnC)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MenuItemPricesCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMenuItemPricesCnCWithPatch() throws Exception {
        // Initialize the database
        insertedMenuItemPricesCnC = menuItemPricesCnCRepository.saveAndFlush(menuItemPricesCnC);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the menuItemPricesCnC using partial update
        MenuItemPricesCnC partialUpdatedMenuItemPricesCnC = new MenuItemPricesCnC();
        partialUpdatedMenuItemPricesCnC.setId(menuItemPricesCnC.getId());

        partialUpdatedMenuItemPricesCnC
            .hierUnitId(UPDATED_HIER_UNIT_ID)
            .menuItemPriceId(UPDATED_MENU_ITEM_PRICE_ID)
            .menuItemMasterId(UPDATED_MENU_ITEM_MASTER_ID)
            .menuItemMasterObjNum(UPDATED_MENU_ITEM_MASTER_OBJ_NUM)
            .menuItemDefinitionId(UPDATED_MENU_ITEM_DEFINITION_ID)
            .defSequenceNum(UPDATED_DEF_SEQUENCE_NUM)
            .externalReference2(UPDATED_EXTERNAL_REFERENCE_2)
            .activeOnMenuLevel(UPDATED_ACTIVE_ON_MENU_LEVEL)
            .effectivityGroupObjNum(UPDATED_EFFECTIVITY_GROUP_OBJ_NUM)
            .prepCost(UPDATED_PREP_COST)
            .price(UPDATED_PRICE)
            .options(UPDATED_OPTIONS);

        restMenuItemPricesCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMenuItemPricesCnC.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMenuItemPricesCnC))
            )
            .andExpect(status().isOk());

        // Validate the MenuItemPricesCnC in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMenuItemPricesCnCUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMenuItemPricesCnC, menuItemPricesCnC),
            getPersistedMenuItemPricesCnC(menuItemPricesCnC)
        );
    }

    @Test
    @Transactional
    void fullUpdateMenuItemPricesCnCWithPatch() throws Exception {
        // Initialize the database
        insertedMenuItemPricesCnC = menuItemPricesCnCRepository.saveAndFlush(menuItemPricesCnC);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the menuItemPricesCnC using partial update
        MenuItemPricesCnC partialUpdatedMenuItemPricesCnC = new MenuItemPricesCnC();
        partialUpdatedMenuItemPricesCnC.setId(menuItemPricesCnC.getId());

        partialUpdatedMenuItemPricesCnC
            .hierUnitId(UPDATED_HIER_UNIT_ID)
            .menuItemPriceId(UPDATED_MENU_ITEM_PRICE_ID)
            .menuItemMasterId(UPDATED_MENU_ITEM_MASTER_ID)
            .menuItemMasterObjNum(UPDATED_MENU_ITEM_MASTER_OBJ_NUM)
            .menuItemDefinitionId(UPDATED_MENU_ITEM_DEFINITION_ID)
            .defSequenceNum(UPDATED_DEF_SEQUENCE_NUM)
            .externalReference1(UPDATED_EXTERNAL_REFERENCE_1)
            .externalReference2(UPDATED_EXTERNAL_REFERENCE_2)
            .priceSequenceNum(UPDATED_PRICE_SEQUENCE_NUM)
            .activeOnMenuLevel(UPDATED_ACTIVE_ON_MENU_LEVEL)
            .effectivityGroupObjNum(UPDATED_EFFECTIVITY_GROUP_OBJ_NUM)
            .prepCost(UPDATED_PREP_COST)
            .price(UPDATED_PRICE)
            .options(UPDATED_OPTIONS);

        restMenuItemPricesCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMenuItemPricesCnC.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMenuItemPricesCnC))
            )
            .andExpect(status().isOk());

        // Validate the MenuItemPricesCnC in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMenuItemPricesCnCUpdatableFieldsEquals(
            partialUpdatedMenuItemPricesCnC,
            getPersistedMenuItemPricesCnC(partialUpdatedMenuItemPricesCnC)
        );
    }

    @Test
    @Transactional
    void patchNonExistingMenuItemPricesCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        menuItemPricesCnC.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMenuItemPricesCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, menuItemPricesCnC.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(menuItemPricesCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the MenuItemPricesCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMenuItemPricesCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        menuItemPricesCnC.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuItemPricesCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(menuItemPricesCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the MenuItemPricesCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMenuItemPricesCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        menuItemPricesCnC.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuItemPricesCnCMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(menuItemPricesCnC)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MenuItemPricesCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMenuItemPricesCnC() throws Exception {
        // Initialize the database
        insertedMenuItemPricesCnC = menuItemPricesCnCRepository.saveAndFlush(menuItemPricesCnC);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the menuItemPricesCnC
        restMenuItemPricesCnCMockMvc
            .perform(delete(ENTITY_API_URL_ID, menuItemPricesCnC.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return menuItemPricesCnCRepository.count();
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

    protected MenuItemPricesCnC getPersistedMenuItemPricesCnC(MenuItemPricesCnC menuItemPricesCnC) {
        return menuItemPricesCnCRepository.findById(menuItemPricesCnC.getId()).orElseThrow();
    }

    protected void assertPersistedMenuItemPricesCnCToMatchAllProperties(MenuItemPricesCnC expectedMenuItemPricesCnC) {
        assertMenuItemPricesCnCAllPropertiesEquals(expectedMenuItemPricesCnC, getPersistedMenuItemPricesCnC(expectedMenuItemPricesCnC));
    }

    protected void assertPersistedMenuItemPricesCnCToMatchUpdatableProperties(MenuItemPricesCnC expectedMenuItemPricesCnC) {
        assertMenuItemPricesCnCAllUpdatablePropertiesEquals(
            expectedMenuItemPricesCnC,
            getPersistedMenuItemPricesCnC(expectedMenuItemPricesCnC)
        );
    }
}
