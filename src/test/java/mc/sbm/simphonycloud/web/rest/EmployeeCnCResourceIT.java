package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.EmployeeCnCAsserts.*;
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
import mc.sbm.simphonycloud.domain.EmployeeCnC;
import mc.sbm.simphonycloud.repository.EmployeeCnCRepository;
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
 * Integration tests for the {@link EmployeeCnCResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeCnCResourceIT {

    private static final Integer DEFAULT_OBJECT_NUM = 1;
    private static final Integer UPDATED_OBJECT_NUM = 2;

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CHECK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CHECK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Integer DEFAULT_LANGUAGE_OBJ_NUM = 1;
    private static final Integer UPDATED_LANGUAGE_OBJ_NUM = 2;

    private static final Integer DEFAULT_LANG_ID = 1;
    private static final Integer UPDATED_LANG_ID = 2;

    private static final Integer DEFAULT_ALTERNATE_ID = 1;
    private static final Integer UPDATED_ALTERNATE_ID = 2;

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    private static final Integer DEFAULT_GROUP = 1;
    private static final Integer UPDATED_GROUP = 2;

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_FIRST_ROLE_HIER_UNIT_ID = 1;
    private static final Integer UPDATED_FIRST_ROLE_HIER_UNIT_ID = 2;

    private static final Integer DEFAULT_FIRST_ROLE_OBJ_NUM = 1;
    private static final Integer UPDATED_FIRST_ROLE_OBJ_NUM = 2;

    private static final Integer DEFAULT_FIRST_VISIBILITY_HIER_UNIT_ID = 1;
    private static final Integer UPDATED_FIRST_VISIBILITY_HIER_UNIT_ID = 2;

    private static final Boolean DEFAULT_FIRST_VISIBILITY_PROPAGATE_TO_CHILDREN = false;
    private static final Boolean UPDATED_FIRST_VISIBILITY_PROPAGATE_TO_CHILDREN = true;

    private static final Integer DEFAULT_FIRST_PROPERTY_HIER_UNIT_ID = 1;
    private static final Integer UPDATED_FIRST_PROPERTY_HIER_UNIT_ID = 2;

    private static final Integer DEFAULT_FIRST_PROPERTY_OBJ_NUM = 1;
    private static final Integer UPDATED_FIRST_PROPERTY_OBJ_NUM = 2;

    private static final Integer DEFAULT_FIRST_PROPERTY_EMP_CLASS_OBJ_NUM = 1;
    private static final Integer UPDATED_FIRST_PROPERTY_EMP_CLASS_OBJ_NUM = 2;

    private static final String DEFAULT_FIRST_PROPERTY_OPTIONS = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_PROPERTY_OPTIONS = "BBBBBBBBBB";

    private static final Integer DEFAULT_FIRST_OPERATOR_RVC_HIER_UNIT_ID = 1;
    private static final Integer UPDATED_FIRST_OPERATOR_RVC_HIER_UNIT_ID = 2;

    private static final Integer DEFAULT_FIRST_OPERATOR_RVC_OBJ_NUM = 1;
    private static final Integer UPDATED_FIRST_OPERATOR_RVC_OBJ_NUM = 2;

    private static final String DEFAULT_FIRST_OPERATOR_OPTIONS = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_OPERATOR_OPTIONS = "BBBBBBBBBB";

    private static final Integer DEFAULT_FIRST_OPERATOR_CASH_DRAWER_OBJ_NUM = 1;
    private static final Integer UPDATED_FIRST_OPERATOR_CASH_DRAWER_OBJ_NUM = 2;

    private static final Integer DEFAULT_FIRST_OPERATOR_TMS_COLOR_OBJ_NUM = 1;
    private static final Integer UPDATED_FIRST_OPERATOR_TMS_COLOR_OBJ_NUM = 2;

    private static final Integer DEFAULT_FIRST_OPERATOR_SERVER_EFFICIENCY = 1;
    private static final Integer UPDATED_FIRST_OPERATOR_SERVER_EFFICIENCY = 2;

    private static final String DEFAULT_PIN = "AAAAAAAAAA";
    private static final String UPDATED_PIN = "BBBBBBBBBB";

    private static final Integer DEFAULT_ACCESS_ID = 1;
    private static final Integer UPDATED_ACCESS_ID = 2;

    private static final String ENTITY_API_URL = "/api/employee-cn-cs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EmployeeCnCRepository employeeCnCRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeCnCMockMvc;

    private EmployeeCnC employeeCnC;

    private EmployeeCnC insertedEmployeeCnC;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeCnC createEntity() {
        return new EmployeeCnC()
            .objectNum(DEFAULT_OBJECT_NUM)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .checkName(DEFAULT_CHECK_NAME)
            .email(DEFAULT_EMAIL)
            .languageObjNum(DEFAULT_LANGUAGE_OBJ_NUM)
            .langId(DEFAULT_LANG_ID)
            .alternateId(DEFAULT_ALTERNATE_ID)
            .level(DEFAULT_LEVEL)
            .group(DEFAULT_GROUP)
            .userName(DEFAULT_USER_NAME)
            .firstRoleHierUnitId(DEFAULT_FIRST_ROLE_HIER_UNIT_ID)
            .firstRoleObjNum(DEFAULT_FIRST_ROLE_OBJ_NUM)
            .firstVisibilityHierUnitId(DEFAULT_FIRST_VISIBILITY_HIER_UNIT_ID)
            .firstVisibilityPropagateToChildren(DEFAULT_FIRST_VISIBILITY_PROPAGATE_TO_CHILDREN)
            .firstPropertyHierUnitId(DEFAULT_FIRST_PROPERTY_HIER_UNIT_ID)
            .firstPropertyObjNum(DEFAULT_FIRST_PROPERTY_OBJ_NUM)
            .firstPropertyEmpClassObjNum(DEFAULT_FIRST_PROPERTY_EMP_CLASS_OBJ_NUM)
            .firstPropertyOptions(DEFAULT_FIRST_PROPERTY_OPTIONS)
            .firstOperatorRvcHierUnitId(DEFAULT_FIRST_OPERATOR_RVC_HIER_UNIT_ID)
            .firstOperatorRvcObjNum(DEFAULT_FIRST_OPERATOR_RVC_OBJ_NUM)
            .firstOperatorOptions(DEFAULT_FIRST_OPERATOR_OPTIONS)
            .firstOperatorCashDrawerObjNum(DEFAULT_FIRST_OPERATOR_CASH_DRAWER_OBJ_NUM)
            .firstOperatorTmsColorObjNum(DEFAULT_FIRST_OPERATOR_TMS_COLOR_OBJ_NUM)
            .firstOperatorServerEfficiency(DEFAULT_FIRST_OPERATOR_SERVER_EFFICIENCY)
            .pin(DEFAULT_PIN)
            .accessId(DEFAULT_ACCESS_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeCnC createUpdatedEntity() {
        return new EmployeeCnC()
            .objectNum(UPDATED_OBJECT_NUM)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .checkName(UPDATED_CHECK_NAME)
            .email(UPDATED_EMAIL)
            .languageObjNum(UPDATED_LANGUAGE_OBJ_NUM)
            .langId(UPDATED_LANG_ID)
            .alternateId(UPDATED_ALTERNATE_ID)
            .level(UPDATED_LEVEL)
            .group(UPDATED_GROUP)
            .userName(UPDATED_USER_NAME)
            .firstRoleHierUnitId(UPDATED_FIRST_ROLE_HIER_UNIT_ID)
            .firstRoleObjNum(UPDATED_FIRST_ROLE_OBJ_NUM)
            .firstVisibilityHierUnitId(UPDATED_FIRST_VISIBILITY_HIER_UNIT_ID)
            .firstVisibilityPropagateToChildren(UPDATED_FIRST_VISIBILITY_PROPAGATE_TO_CHILDREN)
            .firstPropertyHierUnitId(UPDATED_FIRST_PROPERTY_HIER_UNIT_ID)
            .firstPropertyObjNum(UPDATED_FIRST_PROPERTY_OBJ_NUM)
            .firstPropertyEmpClassObjNum(UPDATED_FIRST_PROPERTY_EMP_CLASS_OBJ_NUM)
            .firstPropertyOptions(UPDATED_FIRST_PROPERTY_OPTIONS)
            .firstOperatorRvcHierUnitId(UPDATED_FIRST_OPERATOR_RVC_HIER_UNIT_ID)
            .firstOperatorRvcObjNum(UPDATED_FIRST_OPERATOR_RVC_OBJ_NUM)
            .firstOperatorOptions(UPDATED_FIRST_OPERATOR_OPTIONS)
            .firstOperatorCashDrawerObjNum(UPDATED_FIRST_OPERATOR_CASH_DRAWER_OBJ_NUM)
            .firstOperatorTmsColorObjNum(UPDATED_FIRST_OPERATOR_TMS_COLOR_OBJ_NUM)
            .firstOperatorServerEfficiency(UPDATED_FIRST_OPERATOR_SERVER_EFFICIENCY)
            .pin(UPDATED_PIN)
            .accessId(UPDATED_ACCESS_ID);
    }

    @BeforeEach
    void initTest() {
        employeeCnC = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedEmployeeCnC != null) {
            employeeCnCRepository.delete(insertedEmployeeCnC);
            insertedEmployeeCnC = null;
        }
    }

    @Test
    @Transactional
    void createEmployeeCnC() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EmployeeCnC
        var returnedEmployeeCnC = om.readValue(
            restEmployeeCnCMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(employeeCnC)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EmployeeCnC.class
        );

        // Validate the EmployeeCnC in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEmployeeCnCUpdatableFieldsEquals(returnedEmployeeCnC, getPersistedEmployeeCnC(returnedEmployeeCnC));

        insertedEmployeeCnC = returnedEmployeeCnC;
    }

    @Test
    @Transactional
    void createEmployeeCnCWithExistingId() throws Exception {
        // Create the EmployeeCnC with an existing ID
        employeeCnC.setId(1);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeCnCMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(employeeCnC)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmployeeCnCS() throws Exception {
        // Initialize the database
        insertedEmployeeCnC = employeeCnCRepository.saveAndFlush(employeeCnC);

        // Get all the employeeCnCList
        restEmployeeCnCMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeCnC.getId().intValue())))
            .andExpect(jsonPath("$.[*].objectNum").value(hasItem(DEFAULT_OBJECT_NUM)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].checkName").value(hasItem(DEFAULT_CHECK_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].languageObjNum").value(hasItem(DEFAULT_LANGUAGE_OBJ_NUM)))
            .andExpect(jsonPath("$.[*].langId").value(hasItem(DEFAULT_LANG_ID)))
            .andExpect(jsonPath("$.[*].alternateId").value(hasItem(DEFAULT_ALTERNATE_ID)))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].group").value(hasItem(DEFAULT_GROUP)))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].firstRoleHierUnitId").value(hasItem(DEFAULT_FIRST_ROLE_HIER_UNIT_ID)))
            .andExpect(jsonPath("$.[*].firstRoleObjNum").value(hasItem(DEFAULT_FIRST_ROLE_OBJ_NUM)))
            .andExpect(jsonPath("$.[*].firstVisibilityHierUnitId").value(hasItem(DEFAULT_FIRST_VISIBILITY_HIER_UNIT_ID)))
            .andExpect(jsonPath("$.[*].firstVisibilityPropagateToChildren").value(hasItem(DEFAULT_FIRST_VISIBILITY_PROPAGATE_TO_CHILDREN)))
            .andExpect(jsonPath("$.[*].firstPropertyHierUnitId").value(hasItem(DEFAULT_FIRST_PROPERTY_HIER_UNIT_ID)))
            .andExpect(jsonPath("$.[*].firstPropertyObjNum").value(hasItem(DEFAULT_FIRST_PROPERTY_OBJ_NUM)))
            .andExpect(jsonPath("$.[*].firstPropertyEmpClassObjNum").value(hasItem(DEFAULT_FIRST_PROPERTY_EMP_CLASS_OBJ_NUM)))
            .andExpect(jsonPath("$.[*].firstPropertyOptions").value(hasItem(DEFAULT_FIRST_PROPERTY_OPTIONS)))
            .andExpect(jsonPath("$.[*].firstOperatorRvcHierUnitId").value(hasItem(DEFAULT_FIRST_OPERATOR_RVC_HIER_UNIT_ID)))
            .andExpect(jsonPath("$.[*].firstOperatorRvcObjNum").value(hasItem(DEFAULT_FIRST_OPERATOR_RVC_OBJ_NUM)))
            .andExpect(jsonPath("$.[*].firstOperatorOptions").value(hasItem(DEFAULT_FIRST_OPERATOR_OPTIONS)))
            .andExpect(jsonPath("$.[*].firstOperatorCashDrawerObjNum").value(hasItem(DEFAULT_FIRST_OPERATOR_CASH_DRAWER_OBJ_NUM)))
            .andExpect(jsonPath("$.[*].firstOperatorTmsColorObjNum").value(hasItem(DEFAULT_FIRST_OPERATOR_TMS_COLOR_OBJ_NUM)))
            .andExpect(jsonPath("$.[*].firstOperatorServerEfficiency").value(hasItem(DEFAULT_FIRST_OPERATOR_SERVER_EFFICIENCY)))
            .andExpect(jsonPath("$.[*].pin").value(hasItem(DEFAULT_PIN)))
            .andExpect(jsonPath("$.[*].accessId").value(hasItem(DEFAULT_ACCESS_ID)));
    }

    @Test
    @Transactional
    void getEmployeeCnC() throws Exception {
        // Initialize the database
        insertedEmployeeCnC = employeeCnCRepository.saveAndFlush(employeeCnC);

        // Get the employeeCnC
        restEmployeeCnCMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeCnC.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeCnC.getId().intValue()))
            .andExpect(jsonPath("$.objectNum").value(DEFAULT_OBJECT_NUM))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.checkName").value(DEFAULT_CHECK_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.languageObjNum").value(DEFAULT_LANGUAGE_OBJ_NUM))
            .andExpect(jsonPath("$.langId").value(DEFAULT_LANG_ID))
            .andExpect(jsonPath("$.alternateId").value(DEFAULT_ALTERNATE_ID))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.group").value(DEFAULT_GROUP))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.firstRoleHierUnitId").value(DEFAULT_FIRST_ROLE_HIER_UNIT_ID))
            .andExpect(jsonPath("$.firstRoleObjNum").value(DEFAULT_FIRST_ROLE_OBJ_NUM))
            .andExpect(jsonPath("$.firstVisibilityHierUnitId").value(DEFAULT_FIRST_VISIBILITY_HIER_UNIT_ID))
            .andExpect(jsonPath("$.firstVisibilityPropagateToChildren").value(DEFAULT_FIRST_VISIBILITY_PROPAGATE_TO_CHILDREN))
            .andExpect(jsonPath("$.firstPropertyHierUnitId").value(DEFAULT_FIRST_PROPERTY_HIER_UNIT_ID))
            .andExpect(jsonPath("$.firstPropertyObjNum").value(DEFAULT_FIRST_PROPERTY_OBJ_NUM))
            .andExpect(jsonPath("$.firstPropertyEmpClassObjNum").value(DEFAULT_FIRST_PROPERTY_EMP_CLASS_OBJ_NUM))
            .andExpect(jsonPath("$.firstPropertyOptions").value(DEFAULT_FIRST_PROPERTY_OPTIONS))
            .andExpect(jsonPath("$.firstOperatorRvcHierUnitId").value(DEFAULT_FIRST_OPERATOR_RVC_HIER_UNIT_ID))
            .andExpect(jsonPath("$.firstOperatorRvcObjNum").value(DEFAULT_FIRST_OPERATOR_RVC_OBJ_NUM))
            .andExpect(jsonPath("$.firstOperatorOptions").value(DEFAULT_FIRST_OPERATOR_OPTIONS))
            .andExpect(jsonPath("$.firstOperatorCashDrawerObjNum").value(DEFAULT_FIRST_OPERATOR_CASH_DRAWER_OBJ_NUM))
            .andExpect(jsonPath("$.firstOperatorTmsColorObjNum").value(DEFAULT_FIRST_OPERATOR_TMS_COLOR_OBJ_NUM))
            .andExpect(jsonPath("$.firstOperatorServerEfficiency").value(DEFAULT_FIRST_OPERATOR_SERVER_EFFICIENCY))
            .andExpect(jsonPath("$.pin").value(DEFAULT_PIN))
            .andExpect(jsonPath("$.accessId").value(DEFAULT_ACCESS_ID));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeCnC() throws Exception {
        // Get the employeeCnC
        restEmployeeCnCMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployeeCnC() throws Exception {
        // Initialize the database
        insertedEmployeeCnC = employeeCnCRepository.saveAndFlush(employeeCnC);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the employeeCnC
        EmployeeCnC updatedEmployeeCnC = employeeCnCRepository.findById(employeeCnC.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEmployeeCnC are not directly saved in db
        em.detach(updatedEmployeeCnC);
        updatedEmployeeCnC
            .objectNum(UPDATED_OBJECT_NUM)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .checkName(UPDATED_CHECK_NAME)
            .email(UPDATED_EMAIL)
            .languageObjNum(UPDATED_LANGUAGE_OBJ_NUM)
            .langId(UPDATED_LANG_ID)
            .alternateId(UPDATED_ALTERNATE_ID)
            .level(UPDATED_LEVEL)
            .group(UPDATED_GROUP)
            .userName(UPDATED_USER_NAME)
            .firstRoleHierUnitId(UPDATED_FIRST_ROLE_HIER_UNIT_ID)
            .firstRoleObjNum(UPDATED_FIRST_ROLE_OBJ_NUM)
            .firstVisibilityHierUnitId(UPDATED_FIRST_VISIBILITY_HIER_UNIT_ID)
            .firstVisibilityPropagateToChildren(UPDATED_FIRST_VISIBILITY_PROPAGATE_TO_CHILDREN)
            .firstPropertyHierUnitId(UPDATED_FIRST_PROPERTY_HIER_UNIT_ID)
            .firstPropertyObjNum(UPDATED_FIRST_PROPERTY_OBJ_NUM)
            .firstPropertyEmpClassObjNum(UPDATED_FIRST_PROPERTY_EMP_CLASS_OBJ_NUM)
            .firstPropertyOptions(UPDATED_FIRST_PROPERTY_OPTIONS)
            .firstOperatorRvcHierUnitId(UPDATED_FIRST_OPERATOR_RVC_HIER_UNIT_ID)
            .firstOperatorRvcObjNum(UPDATED_FIRST_OPERATOR_RVC_OBJ_NUM)
            .firstOperatorOptions(UPDATED_FIRST_OPERATOR_OPTIONS)
            .firstOperatorCashDrawerObjNum(UPDATED_FIRST_OPERATOR_CASH_DRAWER_OBJ_NUM)
            .firstOperatorTmsColorObjNum(UPDATED_FIRST_OPERATOR_TMS_COLOR_OBJ_NUM)
            .firstOperatorServerEfficiency(UPDATED_FIRST_OPERATOR_SERVER_EFFICIENCY)
            .pin(UPDATED_PIN)
            .accessId(UPDATED_ACCESS_ID);

        restEmployeeCnCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployeeCnC.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEmployeeCnC))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEmployeeCnCToMatchAllProperties(updatedEmployeeCnC);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        employeeCnC.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeCnCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeCnC.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(employeeCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        employeeCnC.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeCnCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(employeeCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        employeeCnC.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeCnCMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(employeeCnC)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeCnCWithPatch() throws Exception {
        // Initialize the database
        insertedEmployeeCnC = employeeCnCRepository.saveAndFlush(employeeCnC);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the employeeCnC using partial update
        EmployeeCnC partialUpdatedEmployeeCnC = new EmployeeCnC();
        partialUpdatedEmployeeCnC.setId(employeeCnC.getId());

        partialUpdatedEmployeeCnC
            .email(UPDATED_EMAIL)
            .alternateId(UPDATED_ALTERNATE_ID)
            .level(UPDATED_LEVEL)
            .firstRoleObjNum(UPDATED_FIRST_ROLE_OBJ_NUM)
            .firstVisibilityPropagateToChildren(UPDATED_FIRST_VISIBILITY_PROPAGATE_TO_CHILDREN)
            .firstOperatorRvcHierUnitId(UPDATED_FIRST_OPERATOR_RVC_HIER_UNIT_ID)
            .firstOperatorServerEfficiency(UPDATED_FIRST_OPERATOR_SERVER_EFFICIENCY)
            .accessId(UPDATED_ACCESS_ID);

        restEmployeeCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeCnC.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmployeeCnC))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeCnC in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmployeeCnCUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEmployeeCnC, employeeCnC),
            getPersistedEmployeeCnC(employeeCnC)
        );
    }

    @Test
    @Transactional
    void fullUpdateEmployeeCnCWithPatch() throws Exception {
        // Initialize the database
        insertedEmployeeCnC = employeeCnCRepository.saveAndFlush(employeeCnC);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the employeeCnC using partial update
        EmployeeCnC partialUpdatedEmployeeCnC = new EmployeeCnC();
        partialUpdatedEmployeeCnC.setId(employeeCnC.getId());

        partialUpdatedEmployeeCnC
            .objectNum(UPDATED_OBJECT_NUM)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .checkName(UPDATED_CHECK_NAME)
            .email(UPDATED_EMAIL)
            .languageObjNum(UPDATED_LANGUAGE_OBJ_NUM)
            .langId(UPDATED_LANG_ID)
            .alternateId(UPDATED_ALTERNATE_ID)
            .level(UPDATED_LEVEL)
            .group(UPDATED_GROUP)
            .userName(UPDATED_USER_NAME)
            .firstRoleHierUnitId(UPDATED_FIRST_ROLE_HIER_UNIT_ID)
            .firstRoleObjNum(UPDATED_FIRST_ROLE_OBJ_NUM)
            .firstVisibilityHierUnitId(UPDATED_FIRST_VISIBILITY_HIER_UNIT_ID)
            .firstVisibilityPropagateToChildren(UPDATED_FIRST_VISIBILITY_PROPAGATE_TO_CHILDREN)
            .firstPropertyHierUnitId(UPDATED_FIRST_PROPERTY_HIER_UNIT_ID)
            .firstPropertyObjNum(UPDATED_FIRST_PROPERTY_OBJ_NUM)
            .firstPropertyEmpClassObjNum(UPDATED_FIRST_PROPERTY_EMP_CLASS_OBJ_NUM)
            .firstPropertyOptions(UPDATED_FIRST_PROPERTY_OPTIONS)
            .firstOperatorRvcHierUnitId(UPDATED_FIRST_OPERATOR_RVC_HIER_UNIT_ID)
            .firstOperatorRvcObjNum(UPDATED_FIRST_OPERATOR_RVC_OBJ_NUM)
            .firstOperatorOptions(UPDATED_FIRST_OPERATOR_OPTIONS)
            .firstOperatorCashDrawerObjNum(UPDATED_FIRST_OPERATOR_CASH_DRAWER_OBJ_NUM)
            .firstOperatorTmsColorObjNum(UPDATED_FIRST_OPERATOR_TMS_COLOR_OBJ_NUM)
            .firstOperatorServerEfficiency(UPDATED_FIRST_OPERATOR_SERVER_EFFICIENCY)
            .pin(UPDATED_PIN)
            .accessId(UPDATED_ACCESS_ID);

        restEmployeeCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeCnC.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmployeeCnC))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeCnC in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmployeeCnCUpdatableFieldsEquals(partialUpdatedEmployeeCnC, getPersistedEmployeeCnC(partialUpdatedEmployeeCnC));
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        employeeCnC.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeCnC.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(employeeCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        employeeCnC.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(employeeCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        employeeCnC.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeCnCMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(employeeCnC)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeCnC() throws Exception {
        // Initialize the database
        insertedEmployeeCnC = employeeCnCRepository.saveAndFlush(employeeCnC);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the employeeCnC
        restEmployeeCnCMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeCnC.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return employeeCnCRepository.count();
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

    protected EmployeeCnC getPersistedEmployeeCnC(EmployeeCnC employeeCnC) {
        return employeeCnCRepository.findById(employeeCnC.getId()).orElseThrow();
    }

    protected void assertPersistedEmployeeCnCToMatchAllProperties(EmployeeCnC expectedEmployeeCnC) {
        assertEmployeeCnCAllPropertiesEquals(expectedEmployeeCnC, getPersistedEmployeeCnC(expectedEmployeeCnC));
    }

    protected void assertPersistedEmployeeCnCToMatchUpdatableProperties(EmployeeCnC expectedEmployeeCnC) {
        assertEmployeeCnCAllUpdatablePropertiesEquals(expectedEmployeeCnC, getPersistedEmployeeCnC(expectedEmployeeCnC));
    }
}
