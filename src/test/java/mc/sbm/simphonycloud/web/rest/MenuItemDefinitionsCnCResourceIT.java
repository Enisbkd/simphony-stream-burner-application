package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.MenuItemDefinitionsCnCAsserts.*;
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
import mc.sbm.simphonycloud.domain.MenuItemDefinitionsCnC;
import mc.sbm.simphonycloud.repository.MenuItemDefinitionsCnCRepository;
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
 * Integration tests for the {@link MenuItemDefinitionsCnCResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MenuItemDefinitionsCnCResourceIT {

    private static final Integer DEFAULT_HIER_UNIT_ID = 1;
    private static final Integer UPDATED_HIER_UNIT_ID = 2;

    private static final Integer DEFAULT_MENU_ITEM_MASTER_OBJ_NUM = 1;
    private static final Integer UPDATED_MENU_ITEM_MASTER_OBJ_NUM = 2;

    private static final Integer DEFAULT_MENU_ITEM_MASTER_ID = 1;
    private static final Integer UPDATED_MENU_ITEM_MASTER_ID = 2;

    private static final Integer DEFAULT_MENU_ITEM_DEFINITION_ID = 1;
    private static final Integer UPDATED_MENU_ITEM_DEFINITION_ID = 2;

    private static final Integer DEFAULT_DEF_SEQUENCE_NUM = 1;
    private static final Integer UPDATED_DEF_SEQUENCE_NUM = 2;

    private static final Integer DEFAULT_MENU_ITEM_CLASS_OBJ_NUM = 1;
    private static final Integer UPDATED_MENU_ITEM_CLASS_OBJ_NUM = 2;

    private static final Integer DEFAULT_OVERRIDE_PRINT_CLASS_OBJ_NUM = 1;
    private static final Integer UPDATED_OVERRIDE_PRINT_CLASS_OBJ_NUM = 2;

    private static final String DEFAULT_MAIN_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_LEVEL = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_SUB_LEVEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Double DEFAULT_KDS_PREP_TIME = 1D;
    private static final Double UPDATED_KDS_PREP_TIME = 2D;

    private static final Integer DEFAULT_PREFIX_LEVEL_OVERRIDE = 1;
    private static final Integer UPDATED_PREFIX_LEVEL_OVERRIDE = 2;

    private static final Integer DEFAULT_GUEST_COUNT = 1;
    private static final Integer UPDATED_GUEST_COUNT = 2;

    private static final String DEFAULT_SLU_1_OBJ_NUM = "AAAAAAAAAA";
    private static final String UPDATED_SLU_1_OBJ_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_SLU_2_OBJ_NUM = "AAAAAAAAAA";
    private static final String UPDATED_SLU_2_OBJ_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_SLU_3_OBJ_NUM = "AAAAAAAAAA";
    private static final String UPDATED_SLU_3_OBJ_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_SLU_4_OBJ_NUM = "AAAAAAAAAA";
    private static final String UPDATED_SLU_4_OBJ_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_SLU_5_OBJ_NUM = "AAAAAAAAAA";
    private static final String UPDATED_SLU_5_OBJ_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_SLU_6_OBJ_NUM = "AAAAAAAAAA";
    private static final String UPDATED_SLU_6_OBJ_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_SLU_7_OBJ_NUM = "AAAAAAAAAA";
    private static final String UPDATED_SLU_7_OBJ_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_SLU_8_OBJ_NUM = "AAAAAAAAAA";
    private static final String UPDATED_SLU_8_OBJ_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/menu-item-definitions-cn-cs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MenuItemDefinitionsCnCRepository menuItemDefinitionsCnCRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMenuItemDefinitionsCnCMockMvc;

    private MenuItemDefinitionsCnC menuItemDefinitionsCnC;

    private MenuItemDefinitionsCnC insertedMenuItemDefinitionsCnC;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MenuItemDefinitionsCnC createEntity() {
        return new MenuItemDefinitionsCnC()
            .hierUnitId(DEFAULT_HIER_UNIT_ID)
            .menuItemMasterObjNum(DEFAULT_MENU_ITEM_MASTER_OBJ_NUM)
            .menuItemMasterId(DEFAULT_MENU_ITEM_MASTER_ID)
            .menuItemDefinitionId(DEFAULT_MENU_ITEM_DEFINITION_ID)
            .defSequenceNum(DEFAULT_DEF_SEQUENCE_NUM)
            .menuItemClassObjNum(DEFAULT_MENU_ITEM_CLASS_OBJ_NUM)
            .overridePrintClassObjNum(DEFAULT_OVERRIDE_PRINT_CLASS_OBJ_NUM)
            .mainLevel(DEFAULT_MAIN_LEVEL)
            .subLevel(DEFAULT_SUB_LEVEL)
            .quantity(DEFAULT_QUANTITY)
            .kdsPrepTime(DEFAULT_KDS_PREP_TIME)
            .prefixLevelOverride(DEFAULT_PREFIX_LEVEL_OVERRIDE)
            .guestCount(DEFAULT_GUEST_COUNT)
            .slu1ObjNum(DEFAULT_SLU_1_OBJ_NUM)
            .slu2ObjNum(DEFAULT_SLU_2_OBJ_NUM)
            .slu3ObjNum(DEFAULT_SLU_3_OBJ_NUM)
            .slu4ObjNum(DEFAULT_SLU_4_OBJ_NUM)
            .slu5ObjNum(DEFAULT_SLU_5_OBJ_NUM)
            .slu6ObjNum(DEFAULT_SLU_6_OBJ_NUM)
            .slu7ObjNum(DEFAULT_SLU_7_OBJ_NUM)
            .slu8ObjNum(DEFAULT_SLU_8_OBJ_NUM)
            .firstName(DEFAULT_FIRST_NAME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MenuItemDefinitionsCnC createUpdatedEntity() {
        return new MenuItemDefinitionsCnC()
            .hierUnitId(UPDATED_HIER_UNIT_ID)
            .menuItemMasterObjNum(UPDATED_MENU_ITEM_MASTER_OBJ_NUM)
            .menuItemMasterId(UPDATED_MENU_ITEM_MASTER_ID)
            .menuItemDefinitionId(UPDATED_MENU_ITEM_DEFINITION_ID)
            .defSequenceNum(UPDATED_DEF_SEQUENCE_NUM)
            .menuItemClassObjNum(UPDATED_MENU_ITEM_CLASS_OBJ_NUM)
            .overridePrintClassObjNum(UPDATED_OVERRIDE_PRINT_CLASS_OBJ_NUM)
            .mainLevel(UPDATED_MAIN_LEVEL)
            .subLevel(UPDATED_SUB_LEVEL)
            .quantity(UPDATED_QUANTITY)
            .kdsPrepTime(UPDATED_KDS_PREP_TIME)
            .prefixLevelOverride(UPDATED_PREFIX_LEVEL_OVERRIDE)
            .guestCount(UPDATED_GUEST_COUNT)
            .slu1ObjNum(UPDATED_SLU_1_OBJ_NUM)
            .slu2ObjNum(UPDATED_SLU_2_OBJ_NUM)
            .slu3ObjNum(UPDATED_SLU_3_OBJ_NUM)
            .slu4ObjNum(UPDATED_SLU_4_OBJ_NUM)
            .slu5ObjNum(UPDATED_SLU_5_OBJ_NUM)
            .slu6ObjNum(UPDATED_SLU_6_OBJ_NUM)
            .slu7ObjNum(UPDATED_SLU_7_OBJ_NUM)
            .slu8ObjNum(UPDATED_SLU_8_OBJ_NUM)
            .firstName(UPDATED_FIRST_NAME);
    }

    @BeforeEach
    void initTest() {
        menuItemDefinitionsCnC = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedMenuItemDefinitionsCnC != null) {
            menuItemDefinitionsCnCRepository.delete(insertedMenuItemDefinitionsCnC);
            insertedMenuItemDefinitionsCnC = null;
        }
    }

    @Test
    @Transactional
    void createMenuItemDefinitionsCnC() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MenuItemDefinitionsCnC
        var returnedMenuItemDefinitionsCnC = om.readValue(
            restMenuItemDefinitionsCnCMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(menuItemDefinitionsCnC)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MenuItemDefinitionsCnC.class
        );

        // Validate the MenuItemDefinitionsCnC in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMenuItemDefinitionsCnCUpdatableFieldsEquals(
            returnedMenuItemDefinitionsCnC,
            getPersistedMenuItemDefinitionsCnC(returnedMenuItemDefinitionsCnC)
        );

        insertedMenuItemDefinitionsCnC = returnedMenuItemDefinitionsCnC;
    }

    @Test
    @Transactional
    void createMenuItemDefinitionsCnCWithExistingId() throws Exception {
        // Create the MenuItemDefinitionsCnC with an existing ID
        menuItemDefinitionsCnC.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMenuItemDefinitionsCnCMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(menuItemDefinitionsCnC)))
            .andExpect(status().isBadRequest());

        // Validate the MenuItemDefinitionsCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMenuItemDefinitionsCnCS() throws Exception {
        // Initialize the database
        insertedMenuItemDefinitionsCnC = menuItemDefinitionsCnCRepository.saveAndFlush(menuItemDefinitionsCnC);

        // Get all the menuItemDefinitionsCnCList
        restMenuItemDefinitionsCnCMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(menuItemDefinitionsCnC.getId().intValue())))
            .andExpect(jsonPath("$.[*].hierUnitId").value(hasItem(DEFAULT_HIER_UNIT_ID)))
            .andExpect(jsonPath("$.[*].menuItemMasterObjNum").value(hasItem(DEFAULT_MENU_ITEM_MASTER_OBJ_NUM)))
            .andExpect(jsonPath("$.[*].menuItemMasterId").value(hasItem(DEFAULT_MENU_ITEM_MASTER_ID)))
            .andExpect(jsonPath("$.[*].menuItemDefinitionId").value(hasItem(DEFAULT_MENU_ITEM_DEFINITION_ID)))
            .andExpect(jsonPath("$.[*].defSequenceNum").value(hasItem(DEFAULT_DEF_SEQUENCE_NUM)))
            .andExpect(jsonPath("$.[*].menuItemClassObjNum").value(hasItem(DEFAULT_MENU_ITEM_CLASS_OBJ_NUM)))
            .andExpect(jsonPath("$.[*].overridePrintClassObjNum").value(hasItem(DEFAULT_OVERRIDE_PRINT_CLASS_OBJ_NUM)))
            .andExpect(jsonPath("$.[*].mainLevel").value(hasItem(DEFAULT_MAIN_LEVEL)))
            .andExpect(jsonPath("$.[*].subLevel").value(hasItem(DEFAULT_SUB_LEVEL)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].kdsPrepTime").value(hasItem(DEFAULT_KDS_PREP_TIME)))
            .andExpect(jsonPath("$.[*].prefixLevelOverride").value(hasItem(DEFAULT_PREFIX_LEVEL_OVERRIDE)))
            .andExpect(jsonPath("$.[*].guestCount").value(hasItem(DEFAULT_GUEST_COUNT)))
            .andExpect(jsonPath("$.[*].slu1ObjNum").value(hasItem(DEFAULT_SLU_1_OBJ_NUM)))
            .andExpect(jsonPath("$.[*].slu2ObjNum").value(hasItem(DEFAULT_SLU_2_OBJ_NUM)))
            .andExpect(jsonPath("$.[*].slu3ObjNum").value(hasItem(DEFAULT_SLU_3_OBJ_NUM)))
            .andExpect(jsonPath("$.[*].slu4ObjNum").value(hasItem(DEFAULT_SLU_4_OBJ_NUM)))
            .andExpect(jsonPath("$.[*].slu5ObjNum").value(hasItem(DEFAULT_SLU_5_OBJ_NUM)))
            .andExpect(jsonPath("$.[*].slu6ObjNum").value(hasItem(DEFAULT_SLU_6_OBJ_NUM)))
            .andExpect(jsonPath("$.[*].slu7ObjNum").value(hasItem(DEFAULT_SLU_7_OBJ_NUM)))
            .andExpect(jsonPath("$.[*].slu8ObjNum").value(hasItem(DEFAULT_SLU_8_OBJ_NUM)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)));
    }

    @Test
    @Transactional
    void getMenuItemDefinitionsCnC() throws Exception {
        // Initialize the database
        insertedMenuItemDefinitionsCnC = menuItemDefinitionsCnCRepository.saveAndFlush(menuItemDefinitionsCnC);

        // Get the menuItemDefinitionsCnC
        restMenuItemDefinitionsCnCMockMvc
            .perform(get(ENTITY_API_URL_ID, menuItemDefinitionsCnC.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(menuItemDefinitionsCnC.getId().intValue()))
            .andExpect(jsonPath("$.hierUnitId").value(DEFAULT_HIER_UNIT_ID))
            .andExpect(jsonPath("$.menuItemMasterObjNum").value(DEFAULT_MENU_ITEM_MASTER_OBJ_NUM))
            .andExpect(jsonPath("$.menuItemMasterId").value(DEFAULT_MENU_ITEM_MASTER_ID))
            .andExpect(jsonPath("$.menuItemDefinitionId").value(DEFAULT_MENU_ITEM_DEFINITION_ID))
            .andExpect(jsonPath("$.defSequenceNum").value(DEFAULT_DEF_SEQUENCE_NUM))
            .andExpect(jsonPath("$.menuItemClassObjNum").value(DEFAULT_MENU_ITEM_CLASS_OBJ_NUM))
            .andExpect(jsonPath("$.overridePrintClassObjNum").value(DEFAULT_OVERRIDE_PRINT_CLASS_OBJ_NUM))
            .andExpect(jsonPath("$.mainLevel").value(DEFAULT_MAIN_LEVEL))
            .andExpect(jsonPath("$.subLevel").value(DEFAULT_SUB_LEVEL))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.kdsPrepTime").value(DEFAULT_KDS_PREP_TIME))
            .andExpect(jsonPath("$.prefixLevelOverride").value(DEFAULT_PREFIX_LEVEL_OVERRIDE))
            .andExpect(jsonPath("$.guestCount").value(DEFAULT_GUEST_COUNT))
            .andExpect(jsonPath("$.slu1ObjNum").value(DEFAULT_SLU_1_OBJ_NUM))
            .andExpect(jsonPath("$.slu2ObjNum").value(DEFAULT_SLU_2_OBJ_NUM))
            .andExpect(jsonPath("$.slu3ObjNum").value(DEFAULT_SLU_3_OBJ_NUM))
            .andExpect(jsonPath("$.slu4ObjNum").value(DEFAULT_SLU_4_OBJ_NUM))
            .andExpect(jsonPath("$.slu5ObjNum").value(DEFAULT_SLU_5_OBJ_NUM))
            .andExpect(jsonPath("$.slu6ObjNum").value(DEFAULT_SLU_6_OBJ_NUM))
            .andExpect(jsonPath("$.slu7ObjNum").value(DEFAULT_SLU_7_OBJ_NUM))
            .andExpect(jsonPath("$.slu8ObjNum").value(DEFAULT_SLU_8_OBJ_NUM))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME));
    }

    @Test
    @Transactional
    void getNonExistingMenuItemDefinitionsCnC() throws Exception {
        // Get the menuItemDefinitionsCnC
        restMenuItemDefinitionsCnCMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMenuItemDefinitionsCnC() throws Exception {
        // Initialize the database
        insertedMenuItemDefinitionsCnC = menuItemDefinitionsCnCRepository.saveAndFlush(menuItemDefinitionsCnC);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the menuItemDefinitionsCnC
        MenuItemDefinitionsCnC updatedMenuItemDefinitionsCnC = menuItemDefinitionsCnCRepository
            .findById(menuItemDefinitionsCnC.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedMenuItemDefinitionsCnC are not directly saved in db
        em.detach(updatedMenuItemDefinitionsCnC);
        updatedMenuItemDefinitionsCnC
            .hierUnitId(UPDATED_HIER_UNIT_ID)
            .menuItemMasterObjNum(UPDATED_MENU_ITEM_MASTER_OBJ_NUM)
            .menuItemMasterId(UPDATED_MENU_ITEM_MASTER_ID)
            .menuItemDefinitionId(UPDATED_MENU_ITEM_DEFINITION_ID)
            .defSequenceNum(UPDATED_DEF_SEQUENCE_NUM)
            .menuItemClassObjNum(UPDATED_MENU_ITEM_CLASS_OBJ_NUM)
            .overridePrintClassObjNum(UPDATED_OVERRIDE_PRINT_CLASS_OBJ_NUM)
            .mainLevel(UPDATED_MAIN_LEVEL)
            .subLevel(UPDATED_SUB_LEVEL)
            .quantity(UPDATED_QUANTITY)
            .kdsPrepTime(UPDATED_KDS_PREP_TIME)
            .prefixLevelOverride(UPDATED_PREFIX_LEVEL_OVERRIDE)
            .guestCount(UPDATED_GUEST_COUNT)
            .slu1ObjNum(UPDATED_SLU_1_OBJ_NUM)
            .slu2ObjNum(UPDATED_SLU_2_OBJ_NUM)
            .slu3ObjNum(UPDATED_SLU_3_OBJ_NUM)
            .slu4ObjNum(UPDATED_SLU_4_OBJ_NUM)
            .slu5ObjNum(UPDATED_SLU_5_OBJ_NUM)
            .slu6ObjNum(UPDATED_SLU_6_OBJ_NUM)
            .slu7ObjNum(UPDATED_SLU_7_OBJ_NUM)
            .slu8ObjNum(UPDATED_SLU_8_OBJ_NUM)
            .firstName(UPDATED_FIRST_NAME);

        restMenuItemDefinitionsCnCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMenuItemDefinitionsCnC.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMenuItemDefinitionsCnC))
            )
            .andExpect(status().isOk());

        // Validate the MenuItemDefinitionsCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMenuItemDefinitionsCnCToMatchAllProperties(updatedMenuItemDefinitionsCnC);
    }

    @Test
    @Transactional
    void putNonExistingMenuItemDefinitionsCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        menuItemDefinitionsCnC.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMenuItemDefinitionsCnCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, menuItemDefinitionsCnC.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(menuItemDefinitionsCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the MenuItemDefinitionsCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMenuItemDefinitionsCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        menuItemDefinitionsCnC.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuItemDefinitionsCnCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(menuItemDefinitionsCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the MenuItemDefinitionsCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMenuItemDefinitionsCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        menuItemDefinitionsCnC.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuItemDefinitionsCnCMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(menuItemDefinitionsCnC)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MenuItemDefinitionsCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMenuItemDefinitionsCnCWithPatch() throws Exception {
        // Initialize the database
        insertedMenuItemDefinitionsCnC = menuItemDefinitionsCnCRepository.saveAndFlush(menuItemDefinitionsCnC);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the menuItemDefinitionsCnC using partial update
        MenuItemDefinitionsCnC partialUpdatedMenuItemDefinitionsCnC = new MenuItemDefinitionsCnC();
        partialUpdatedMenuItemDefinitionsCnC.setId(menuItemDefinitionsCnC.getId());

        partialUpdatedMenuItemDefinitionsCnC
            .menuItemMasterObjNum(UPDATED_MENU_ITEM_MASTER_OBJ_NUM)
            .menuItemDefinitionId(UPDATED_MENU_ITEM_DEFINITION_ID)
            .defSequenceNum(UPDATED_DEF_SEQUENCE_NUM)
            .menuItemClassObjNum(UPDATED_MENU_ITEM_CLASS_OBJ_NUM)
            .mainLevel(UPDATED_MAIN_LEVEL)
            .quantity(UPDATED_QUANTITY)
            .kdsPrepTime(UPDATED_KDS_PREP_TIME)
            .prefixLevelOverride(UPDATED_PREFIX_LEVEL_OVERRIDE)
            .guestCount(UPDATED_GUEST_COUNT)
            .slu5ObjNum(UPDATED_SLU_5_OBJ_NUM)
            .slu6ObjNum(UPDATED_SLU_6_OBJ_NUM)
            .slu8ObjNum(UPDATED_SLU_8_OBJ_NUM);

        restMenuItemDefinitionsCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMenuItemDefinitionsCnC.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMenuItemDefinitionsCnC))
            )
            .andExpect(status().isOk());

        // Validate the MenuItemDefinitionsCnC in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMenuItemDefinitionsCnCUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMenuItemDefinitionsCnC, menuItemDefinitionsCnC),
            getPersistedMenuItemDefinitionsCnC(menuItemDefinitionsCnC)
        );
    }

    @Test
    @Transactional
    void fullUpdateMenuItemDefinitionsCnCWithPatch() throws Exception {
        // Initialize the database
        insertedMenuItemDefinitionsCnC = menuItemDefinitionsCnCRepository.saveAndFlush(menuItemDefinitionsCnC);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the menuItemDefinitionsCnC using partial update
        MenuItemDefinitionsCnC partialUpdatedMenuItemDefinitionsCnC = new MenuItemDefinitionsCnC();
        partialUpdatedMenuItemDefinitionsCnC.setId(menuItemDefinitionsCnC.getId());

        partialUpdatedMenuItemDefinitionsCnC
            .hierUnitId(UPDATED_HIER_UNIT_ID)
            .menuItemMasterObjNum(UPDATED_MENU_ITEM_MASTER_OBJ_NUM)
            .menuItemMasterId(UPDATED_MENU_ITEM_MASTER_ID)
            .menuItemDefinitionId(UPDATED_MENU_ITEM_DEFINITION_ID)
            .defSequenceNum(UPDATED_DEF_SEQUENCE_NUM)
            .menuItemClassObjNum(UPDATED_MENU_ITEM_CLASS_OBJ_NUM)
            .overridePrintClassObjNum(UPDATED_OVERRIDE_PRINT_CLASS_OBJ_NUM)
            .mainLevel(UPDATED_MAIN_LEVEL)
            .subLevel(UPDATED_SUB_LEVEL)
            .quantity(UPDATED_QUANTITY)
            .kdsPrepTime(UPDATED_KDS_PREP_TIME)
            .prefixLevelOverride(UPDATED_PREFIX_LEVEL_OVERRIDE)
            .guestCount(UPDATED_GUEST_COUNT)
            .slu1ObjNum(UPDATED_SLU_1_OBJ_NUM)
            .slu2ObjNum(UPDATED_SLU_2_OBJ_NUM)
            .slu3ObjNum(UPDATED_SLU_3_OBJ_NUM)
            .slu4ObjNum(UPDATED_SLU_4_OBJ_NUM)
            .slu5ObjNum(UPDATED_SLU_5_OBJ_NUM)
            .slu6ObjNum(UPDATED_SLU_6_OBJ_NUM)
            .slu7ObjNum(UPDATED_SLU_7_OBJ_NUM)
            .slu8ObjNum(UPDATED_SLU_8_OBJ_NUM)
            .firstName(UPDATED_FIRST_NAME);

        restMenuItemDefinitionsCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMenuItemDefinitionsCnC.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMenuItemDefinitionsCnC))
            )
            .andExpect(status().isOk());

        // Validate the MenuItemDefinitionsCnC in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMenuItemDefinitionsCnCUpdatableFieldsEquals(
            partialUpdatedMenuItemDefinitionsCnC,
            getPersistedMenuItemDefinitionsCnC(partialUpdatedMenuItemDefinitionsCnC)
        );
    }

    @Test
    @Transactional
    void patchNonExistingMenuItemDefinitionsCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        menuItemDefinitionsCnC.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMenuItemDefinitionsCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, menuItemDefinitionsCnC.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(menuItemDefinitionsCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the MenuItemDefinitionsCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMenuItemDefinitionsCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        menuItemDefinitionsCnC.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuItemDefinitionsCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(menuItemDefinitionsCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the MenuItemDefinitionsCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMenuItemDefinitionsCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        menuItemDefinitionsCnC.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuItemDefinitionsCnCMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(menuItemDefinitionsCnC))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MenuItemDefinitionsCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMenuItemDefinitionsCnC() throws Exception {
        // Initialize the database
        insertedMenuItemDefinitionsCnC = menuItemDefinitionsCnCRepository.saveAndFlush(menuItemDefinitionsCnC);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the menuItemDefinitionsCnC
        restMenuItemDefinitionsCnCMockMvc
            .perform(delete(ENTITY_API_URL_ID, menuItemDefinitionsCnC.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return menuItemDefinitionsCnCRepository.count();
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

    protected MenuItemDefinitionsCnC getPersistedMenuItemDefinitionsCnC(MenuItemDefinitionsCnC menuItemDefinitionsCnC) {
        return menuItemDefinitionsCnCRepository.findById(menuItemDefinitionsCnC.getId()).orElseThrow();
    }

    protected void assertPersistedMenuItemDefinitionsCnCToMatchAllProperties(MenuItemDefinitionsCnC expectedMenuItemDefinitionsCnC) {
        assertMenuItemDefinitionsCnCAllPropertiesEquals(
            expectedMenuItemDefinitionsCnC,
            getPersistedMenuItemDefinitionsCnC(expectedMenuItemDefinitionsCnC)
        );
    }

    protected void assertPersistedMenuItemDefinitionsCnCToMatchUpdatableProperties(MenuItemDefinitionsCnC expectedMenuItemDefinitionsCnC) {
        assertMenuItemDefinitionsCnCAllUpdatablePropertiesEquals(
            expectedMenuItemDefinitionsCnC,
            getPersistedMenuItemDefinitionsCnC(expectedMenuItemDefinitionsCnC)
        );
    }
}
