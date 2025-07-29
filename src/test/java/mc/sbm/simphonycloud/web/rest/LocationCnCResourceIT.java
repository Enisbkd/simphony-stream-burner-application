package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.LocationCnCAsserts.*;
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
import mc.sbm.simphonycloud.domain.LocationCnC;
import mc.sbm.simphonycloud.repository.LocationCnCRepository;
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
 * Integration tests for the {@link LocationCnCResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LocationCnCResourceIT {

    private static final Integer DEFAULT_HIER_UNIT_ID = 1;
    private static final Integer UPDATED_HIER_UNIT_ID = 2;

    private static final Integer DEFAULT_TZ_INDEX = 1;
    private static final Integer UPDATED_TZ_INDEX = 2;

    private static final String DEFAULT_TZ_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TZ_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_LOCALE_INFO_ID = 1;
    private static final Integer UPDATED_LOCALE_INFO_ID = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REPORTING_LOC_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REPORTING_LOC_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOC_REF = "AAAAAAAAAA";
    private static final String UPDATED_LOC_REF = "BBBBBBBBBB";

    private static final String DEFAULT_REPORTING_PARENT_ENTERPRISE_LEVEL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REPORTING_PARENT_ENTERPRISE_LEVEL_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_OBJECT_NUM = 1;
    private static final Integer UPDATED_OBJECT_NUM = 2;

    private static final String DEFAULT_SBM_PMS_IFC_IP = "AAAAAAAAAA";
    private static final String UPDATED_SBM_PMS_IFC_IP = "BBBBBBBBBB";

    private static final String DEFAULT_SBM_PMS_IFC_PORT = "AAAAAAAAAA";
    private static final String UPDATED_SBM_PMS_IFC_PORT = "BBBBBBBBBB";

    private static final String DEFAULT_SBM_PRIVE_ROOM_START = "AAAAAAAAAA";
    private static final String UPDATED_SBM_PRIVE_ROOM_START = "BBBBBBBBBB";

    private static final String DEFAULT_SBM_PRIVE_ROOM_END = "AAAAAAAAAA";
    private static final String UPDATED_SBM_PRIVE_ROOM_END = "BBBBBBBBBB";

    private static final String DEFAULT_SBM_PMS_SEND_ALL_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_SBM_PMS_SEND_ALL_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_SBM_PMS_SEND_FULL_DSCV = "AAAAAAAAAA";
    private static final String UPDATED_SBM_PMS_SEND_FULL_DSCV = "BBBBBBBBBB";

    private static final String DEFAULT_SBM_PMS_SEND_64_TAX = "AAAAAAAAAA";
    private static final String UPDATED_SBM_PMS_SEND_64_TAX = "BBBBBBBBBB";

    private static final String DEFAULT_SBM_CARD_PAYMENT_URL = "AAAAAAAAAA";
    private static final String UPDATED_SBM_CARD_PAYMENT_URL = "BBBBBBBBBB";

    private static final String DEFAULT_SBM_CHECK_HOTEL_DATA_URL = "AAAAAAAAAA";
    private static final String UPDATED_SBM_CHECK_HOTEL_DATA_URL = "BBBBBBBBBB";

    private static final String DEFAULT_SBM_VOUCHER_SVC_URL = "AAAAAAAAAA";
    private static final String UPDATED_SBM_VOUCHER_SVC_URL = "BBBBBBBBBB";

    private static final String DEFAULT_SBM_VOUCHER_INV_PM = "AAAAAAAAAA";
    private static final String UPDATED_SBM_VOUCHER_INV_PM = "BBBBBBBBBB";

    private static final String DEFAULT_SBM_VOUCHER_CORP_PM = "AAAAAAAAAA";
    private static final String UPDATED_SBM_VOUCHER_CORP_PM = "BBBBBBBBBB";

    private static final String DEFAULT_SBM_VOUCHER_REWARD_PM = "AAAAAAAAAA";
    private static final String UPDATED_SBM_VOUCHER_REWARD_PM = "BBBBBBBBBB";

    private static final String DEFAULT_SBM_VOUCHER_MC_PM = "AAAAAAAAAA";
    private static final String UPDATED_SBM_VOUCHER_MC_PM = "BBBBBBBBBB";

    private static final String DEFAULT_SBM_PMS_IFC_PORT_2 = "AAAAAAAAAA";
    private static final String UPDATED_SBM_PMS_IFC_PORT_2 = "BBBBBBBBBB";

    private static final String DEFAULT_SBM_PMS_IFC_PORT_3 = "AAAAAAAAAA";
    private static final String UPDATED_SBM_PMS_IFC_PORT_3 = "BBBBBBBBBB";

    private static final String DEFAULT_SBM_PMS_IFC_PORT_4 = "AAAAAAAAAA";
    private static final String UPDATED_SBM_PMS_IFC_PORT_4 = "BBBBBBBBBB";

    private static final String DEFAULT_SBM_TIMEOUT = "AAAAAAAAAA";
    private static final String UPDATED_SBM_TIMEOUT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/location-cn-cs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LocationCnCRepository locationCnCRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocationCnCMockMvc;

    private LocationCnC locationCnC;

    private LocationCnC insertedLocationCnC;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LocationCnC createEntity() {
        return new LocationCnC()
            .hierUnitId(DEFAULT_HIER_UNIT_ID)
            .tzIndex(DEFAULT_TZ_INDEX)
            .tzName(DEFAULT_TZ_NAME)
            .localeInfoId(DEFAULT_LOCALE_INFO_ID)
            .name(DEFAULT_NAME)
            .reportingLocName(DEFAULT_REPORTING_LOC_NAME)
            .locRef(DEFAULT_LOC_REF)
            .reportingParentEnterpriseLevelName(DEFAULT_REPORTING_PARENT_ENTERPRISE_LEVEL_NAME)
            .objectNum(DEFAULT_OBJECT_NUM)
            .sbmPmsIfcIp(DEFAULT_SBM_PMS_IFC_IP)
            .sbmPmsIfcPort(DEFAULT_SBM_PMS_IFC_PORT)
            .sbmPriveRoomStart(DEFAULT_SBM_PRIVE_ROOM_START)
            .sbmPriveRoomEnd(DEFAULT_SBM_PRIVE_ROOM_END)
            .sbmPmsSendAllDetails(DEFAULT_SBM_PMS_SEND_ALL_DETAILS)
            .sbmPmsSendFullDscv(DEFAULT_SBM_PMS_SEND_FULL_DSCV)
            .sbmPmsSend64Tax(DEFAULT_SBM_PMS_SEND_64_TAX)
            .sbmCardPaymentUrl(DEFAULT_SBM_CARD_PAYMENT_URL)
            .sbmCheckHotelDataUrl(DEFAULT_SBM_CHECK_HOTEL_DATA_URL)
            .sbmVoucherSvcUrl(DEFAULT_SBM_VOUCHER_SVC_URL)
            .sbmVoucherInvPm(DEFAULT_SBM_VOUCHER_INV_PM)
            .sbmVoucherCorpPm(DEFAULT_SBM_VOUCHER_CORP_PM)
            .sbmVoucherRewardPm(DEFAULT_SBM_VOUCHER_REWARD_PM)
            .sbmVoucherMcPm(DEFAULT_SBM_VOUCHER_MC_PM)
            .sbmPmsIfcPort2(DEFAULT_SBM_PMS_IFC_PORT_2)
            .sbmPmsIfcPort3(DEFAULT_SBM_PMS_IFC_PORT_3)
            .sbmPmsIfcPort4(DEFAULT_SBM_PMS_IFC_PORT_4)
            .sbmTimeout(DEFAULT_SBM_TIMEOUT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LocationCnC createUpdatedEntity() {
        return new LocationCnC()
            .hierUnitId(UPDATED_HIER_UNIT_ID)
            .tzIndex(UPDATED_TZ_INDEX)
            .tzName(UPDATED_TZ_NAME)
            .localeInfoId(UPDATED_LOCALE_INFO_ID)
            .name(UPDATED_NAME)
            .reportingLocName(UPDATED_REPORTING_LOC_NAME)
            .locRef(UPDATED_LOC_REF)
            .reportingParentEnterpriseLevelName(UPDATED_REPORTING_PARENT_ENTERPRISE_LEVEL_NAME)
            .objectNum(UPDATED_OBJECT_NUM)
            .sbmPmsIfcIp(UPDATED_SBM_PMS_IFC_IP)
            .sbmPmsIfcPort(UPDATED_SBM_PMS_IFC_PORT)
            .sbmPriveRoomStart(UPDATED_SBM_PRIVE_ROOM_START)
            .sbmPriveRoomEnd(UPDATED_SBM_PRIVE_ROOM_END)
            .sbmPmsSendAllDetails(UPDATED_SBM_PMS_SEND_ALL_DETAILS)
            .sbmPmsSendFullDscv(UPDATED_SBM_PMS_SEND_FULL_DSCV)
            .sbmPmsSend64Tax(UPDATED_SBM_PMS_SEND_64_TAX)
            .sbmCardPaymentUrl(UPDATED_SBM_CARD_PAYMENT_URL)
            .sbmCheckHotelDataUrl(UPDATED_SBM_CHECK_HOTEL_DATA_URL)
            .sbmVoucherSvcUrl(UPDATED_SBM_VOUCHER_SVC_URL)
            .sbmVoucherInvPm(UPDATED_SBM_VOUCHER_INV_PM)
            .sbmVoucherCorpPm(UPDATED_SBM_VOUCHER_CORP_PM)
            .sbmVoucherRewardPm(UPDATED_SBM_VOUCHER_REWARD_PM)
            .sbmVoucherMcPm(UPDATED_SBM_VOUCHER_MC_PM)
            .sbmPmsIfcPort2(UPDATED_SBM_PMS_IFC_PORT_2)
            .sbmPmsIfcPort3(UPDATED_SBM_PMS_IFC_PORT_3)
            .sbmPmsIfcPort4(UPDATED_SBM_PMS_IFC_PORT_4)
            .sbmTimeout(UPDATED_SBM_TIMEOUT);
    }

    @BeforeEach
    void initTest() {
        locationCnC = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedLocationCnC != null) {
            locationCnCRepository.delete(insertedLocationCnC);
            insertedLocationCnC = null;
        }
    }

    @Test
    @Transactional
    void createLocationCnC() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the LocationCnC
        var returnedLocationCnC = om.readValue(
            restLocationCnCMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationCnC)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            LocationCnC.class
        );

        // Validate the LocationCnC in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLocationCnCUpdatableFieldsEquals(returnedLocationCnC, getPersistedLocationCnC(returnedLocationCnC));

        insertedLocationCnC = returnedLocationCnC;
    }

    @Test
    @Transactional
    void createLocationCnCWithExistingId() throws Exception {
        // Create the LocationCnC with an existing ID
        locationCnC.setId(1);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationCnCMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationCnC)))
            .andExpect(status().isBadRequest());

        // Validate the LocationCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLocationCnCS() throws Exception {
        // Initialize the database
        insertedLocationCnC = locationCnCRepository.saveAndFlush(locationCnC);

        // Get all the locationCnCList
        restLocationCnCMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locationCnC.getId().intValue())))
            .andExpect(jsonPath("$.[*].hierUnitId").value(hasItem(DEFAULT_HIER_UNIT_ID)))
            .andExpect(jsonPath("$.[*].tzIndex").value(hasItem(DEFAULT_TZ_INDEX)))
            .andExpect(jsonPath("$.[*].tzName").value(hasItem(DEFAULT_TZ_NAME)))
            .andExpect(jsonPath("$.[*].localeInfoId").value(hasItem(DEFAULT_LOCALE_INFO_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].reportingLocName").value(hasItem(DEFAULT_REPORTING_LOC_NAME)))
            .andExpect(jsonPath("$.[*].locRef").value(hasItem(DEFAULT_LOC_REF)))
            .andExpect(jsonPath("$.[*].reportingParentEnterpriseLevelName").value(hasItem(DEFAULT_REPORTING_PARENT_ENTERPRISE_LEVEL_NAME)))
            .andExpect(jsonPath("$.[*].objectNum").value(hasItem(DEFAULT_OBJECT_NUM)))
            .andExpect(jsonPath("$.[*].sbmPmsIfcIp").value(hasItem(DEFAULT_SBM_PMS_IFC_IP)))
            .andExpect(jsonPath("$.[*].sbmPmsIfcPort").value(hasItem(DEFAULT_SBM_PMS_IFC_PORT)))
            .andExpect(jsonPath("$.[*].sbmPriveRoomStart").value(hasItem(DEFAULT_SBM_PRIVE_ROOM_START)))
            .andExpect(jsonPath("$.[*].sbmPriveRoomEnd").value(hasItem(DEFAULT_SBM_PRIVE_ROOM_END)))
            .andExpect(jsonPath("$.[*].sbmPmsSendAllDetails").value(hasItem(DEFAULT_SBM_PMS_SEND_ALL_DETAILS)))
            .andExpect(jsonPath("$.[*].sbmPmsSendFullDscv").value(hasItem(DEFAULT_SBM_PMS_SEND_FULL_DSCV)))
            .andExpect(jsonPath("$.[*].sbmPmsSend64Tax").value(hasItem(DEFAULT_SBM_PMS_SEND_64_TAX)))
            .andExpect(jsonPath("$.[*].sbmCardPaymentUrl").value(hasItem(DEFAULT_SBM_CARD_PAYMENT_URL)))
            .andExpect(jsonPath("$.[*].sbmCheckHotelDataUrl").value(hasItem(DEFAULT_SBM_CHECK_HOTEL_DATA_URL)))
            .andExpect(jsonPath("$.[*].sbmVoucherSvcUrl").value(hasItem(DEFAULT_SBM_VOUCHER_SVC_URL)))
            .andExpect(jsonPath("$.[*].sbmVoucherInvPm").value(hasItem(DEFAULT_SBM_VOUCHER_INV_PM)))
            .andExpect(jsonPath("$.[*].sbmVoucherCorpPm").value(hasItem(DEFAULT_SBM_VOUCHER_CORP_PM)))
            .andExpect(jsonPath("$.[*].sbmVoucherRewardPm").value(hasItem(DEFAULT_SBM_VOUCHER_REWARD_PM)))
            .andExpect(jsonPath("$.[*].sbmVoucherMcPm").value(hasItem(DEFAULT_SBM_VOUCHER_MC_PM)))
            .andExpect(jsonPath("$.[*].sbmPmsIfcPort2").value(hasItem(DEFAULT_SBM_PMS_IFC_PORT_2)))
            .andExpect(jsonPath("$.[*].sbmPmsIfcPort3").value(hasItem(DEFAULT_SBM_PMS_IFC_PORT_3)))
            .andExpect(jsonPath("$.[*].sbmPmsIfcPort4").value(hasItem(DEFAULT_SBM_PMS_IFC_PORT_4)))
            .andExpect(jsonPath("$.[*].sbmTimeout").value(hasItem(DEFAULT_SBM_TIMEOUT)));
    }

    @Test
    @Transactional
    void getLocationCnC() throws Exception {
        // Initialize the database
        insertedLocationCnC = locationCnCRepository.saveAndFlush(locationCnC);

        // Get the locationCnC
        restLocationCnCMockMvc
            .perform(get(ENTITY_API_URL_ID, locationCnC.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(locationCnC.getId().intValue()))
            .andExpect(jsonPath("$.hierUnitId").value(DEFAULT_HIER_UNIT_ID))
            .andExpect(jsonPath("$.tzIndex").value(DEFAULT_TZ_INDEX))
            .andExpect(jsonPath("$.tzName").value(DEFAULT_TZ_NAME))
            .andExpect(jsonPath("$.localeInfoId").value(DEFAULT_LOCALE_INFO_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.reportingLocName").value(DEFAULT_REPORTING_LOC_NAME))
            .andExpect(jsonPath("$.locRef").value(DEFAULT_LOC_REF))
            .andExpect(jsonPath("$.reportingParentEnterpriseLevelName").value(DEFAULT_REPORTING_PARENT_ENTERPRISE_LEVEL_NAME))
            .andExpect(jsonPath("$.objectNum").value(DEFAULT_OBJECT_NUM))
            .andExpect(jsonPath("$.sbmPmsIfcIp").value(DEFAULT_SBM_PMS_IFC_IP))
            .andExpect(jsonPath("$.sbmPmsIfcPort").value(DEFAULT_SBM_PMS_IFC_PORT))
            .andExpect(jsonPath("$.sbmPriveRoomStart").value(DEFAULT_SBM_PRIVE_ROOM_START))
            .andExpect(jsonPath("$.sbmPriveRoomEnd").value(DEFAULT_SBM_PRIVE_ROOM_END))
            .andExpect(jsonPath("$.sbmPmsSendAllDetails").value(DEFAULT_SBM_PMS_SEND_ALL_DETAILS))
            .andExpect(jsonPath("$.sbmPmsSendFullDscv").value(DEFAULT_SBM_PMS_SEND_FULL_DSCV))
            .andExpect(jsonPath("$.sbmPmsSend64Tax").value(DEFAULT_SBM_PMS_SEND_64_TAX))
            .andExpect(jsonPath("$.sbmCardPaymentUrl").value(DEFAULT_SBM_CARD_PAYMENT_URL))
            .andExpect(jsonPath("$.sbmCheckHotelDataUrl").value(DEFAULT_SBM_CHECK_HOTEL_DATA_URL))
            .andExpect(jsonPath("$.sbmVoucherSvcUrl").value(DEFAULT_SBM_VOUCHER_SVC_URL))
            .andExpect(jsonPath("$.sbmVoucherInvPm").value(DEFAULT_SBM_VOUCHER_INV_PM))
            .andExpect(jsonPath("$.sbmVoucherCorpPm").value(DEFAULT_SBM_VOUCHER_CORP_PM))
            .andExpect(jsonPath("$.sbmVoucherRewardPm").value(DEFAULT_SBM_VOUCHER_REWARD_PM))
            .andExpect(jsonPath("$.sbmVoucherMcPm").value(DEFAULT_SBM_VOUCHER_MC_PM))
            .andExpect(jsonPath("$.sbmPmsIfcPort2").value(DEFAULT_SBM_PMS_IFC_PORT_2))
            .andExpect(jsonPath("$.sbmPmsIfcPort3").value(DEFAULT_SBM_PMS_IFC_PORT_3))
            .andExpect(jsonPath("$.sbmPmsIfcPort4").value(DEFAULT_SBM_PMS_IFC_PORT_4))
            .andExpect(jsonPath("$.sbmTimeout").value(DEFAULT_SBM_TIMEOUT));
    }

    @Test
    @Transactional
    void getNonExistingLocationCnC() throws Exception {
        // Get the locationCnC
        restLocationCnCMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLocationCnC() throws Exception {
        // Initialize the database
        insertedLocationCnC = locationCnCRepository.saveAndFlush(locationCnC);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the locationCnC
        LocationCnC updatedLocationCnC = locationCnCRepository.findById(locationCnC.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLocationCnC are not directly saved in db
        em.detach(updatedLocationCnC);
        updatedLocationCnC
            .hierUnitId(UPDATED_HIER_UNIT_ID)
            .tzIndex(UPDATED_TZ_INDEX)
            .tzName(UPDATED_TZ_NAME)
            .localeInfoId(UPDATED_LOCALE_INFO_ID)
            .name(UPDATED_NAME)
            .reportingLocName(UPDATED_REPORTING_LOC_NAME)
            .locRef(UPDATED_LOC_REF)
            .reportingParentEnterpriseLevelName(UPDATED_REPORTING_PARENT_ENTERPRISE_LEVEL_NAME)
            .objectNum(UPDATED_OBJECT_NUM)
            .sbmPmsIfcIp(UPDATED_SBM_PMS_IFC_IP)
            .sbmPmsIfcPort(UPDATED_SBM_PMS_IFC_PORT)
            .sbmPriveRoomStart(UPDATED_SBM_PRIVE_ROOM_START)
            .sbmPriveRoomEnd(UPDATED_SBM_PRIVE_ROOM_END)
            .sbmPmsSendAllDetails(UPDATED_SBM_PMS_SEND_ALL_DETAILS)
            .sbmPmsSendFullDscv(UPDATED_SBM_PMS_SEND_FULL_DSCV)
            .sbmPmsSend64Tax(UPDATED_SBM_PMS_SEND_64_TAX)
            .sbmCardPaymentUrl(UPDATED_SBM_CARD_PAYMENT_URL)
            .sbmCheckHotelDataUrl(UPDATED_SBM_CHECK_HOTEL_DATA_URL)
            .sbmVoucherSvcUrl(UPDATED_SBM_VOUCHER_SVC_URL)
            .sbmVoucherInvPm(UPDATED_SBM_VOUCHER_INV_PM)
            .sbmVoucherCorpPm(UPDATED_SBM_VOUCHER_CORP_PM)
            .sbmVoucherRewardPm(UPDATED_SBM_VOUCHER_REWARD_PM)
            .sbmVoucherMcPm(UPDATED_SBM_VOUCHER_MC_PM)
            .sbmPmsIfcPort2(UPDATED_SBM_PMS_IFC_PORT_2)
            .sbmPmsIfcPort3(UPDATED_SBM_PMS_IFC_PORT_3)
            .sbmPmsIfcPort4(UPDATED_SBM_PMS_IFC_PORT_4)
            .sbmTimeout(UPDATED_SBM_TIMEOUT);

        restLocationCnCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLocationCnC.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLocationCnC))
            )
            .andExpect(status().isOk());

        // Validate the LocationCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLocationCnCToMatchAllProperties(updatedLocationCnC);
    }

    @Test
    @Transactional
    void putNonExistingLocationCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locationCnC.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationCnCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, locationCnC.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(locationCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocationCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLocationCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locationCnC.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationCnCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(locationCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocationCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLocationCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locationCnC.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationCnCMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationCnC)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LocationCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLocationCnCWithPatch() throws Exception {
        // Initialize the database
        insertedLocationCnC = locationCnCRepository.saveAndFlush(locationCnC);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the locationCnC using partial update
        LocationCnC partialUpdatedLocationCnC = new LocationCnC();
        partialUpdatedLocationCnC.setId(locationCnC.getId());

        partialUpdatedLocationCnC
            .hierUnitId(UPDATED_HIER_UNIT_ID)
            .tzName(UPDATED_TZ_NAME)
            .name(UPDATED_NAME)
            .locRef(UPDATED_LOC_REF)
            .reportingParentEnterpriseLevelName(UPDATED_REPORTING_PARENT_ENTERPRISE_LEVEL_NAME)
            .objectNum(UPDATED_OBJECT_NUM)
            .sbmPmsIfcIp(UPDATED_SBM_PMS_IFC_IP)
            .sbmPmsIfcPort(UPDATED_SBM_PMS_IFC_PORT)
            .sbmPriveRoomEnd(UPDATED_SBM_PRIVE_ROOM_END)
            .sbmPmsSendAllDetails(UPDATED_SBM_PMS_SEND_ALL_DETAILS)
            .sbmCardPaymentUrl(UPDATED_SBM_CARD_PAYMENT_URL)
            .sbmCheckHotelDataUrl(UPDATED_SBM_CHECK_HOTEL_DATA_URL)
            .sbmVoucherInvPm(UPDATED_SBM_VOUCHER_INV_PM)
            .sbmVoucherCorpPm(UPDATED_SBM_VOUCHER_CORP_PM)
            .sbmVoucherRewardPm(UPDATED_SBM_VOUCHER_REWARD_PM)
            .sbmVoucherMcPm(UPDATED_SBM_VOUCHER_MC_PM)
            .sbmPmsIfcPort2(UPDATED_SBM_PMS_IFC_PORT_2)
            .sbmPmsIfcPort3(UPDATED_SBM_PMS_IFC_PORT_3)
            .sbmPmsIfcPort4(UPDATED_SBM_PMS_IFC_PORT_4);

        restLocationCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocationCnC.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLocationCnC))
            )
            .andExpect(status().isOk());

        // Validate the LocationCnC in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocationCnCUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLocationCnC, locationCnC),
            getPersistedLocationCnC(locationCnC)
        );
    }

    @Test
    @Transactional
    void fullUpdateLocationCnCWithPatch() throws Exception {
        // Initialize the database
        insertedLocationCnC = locationCnCRepository.saveAndFlush(locationCnC);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the locationCnC using partial update
        LocationCnC partialUpdatedLocationCnC = new LocationCnC();
        partialUpdatedLocationCnC.setId(locationCnC.getId());

        partialUpdatedLocationCnC
            .hierUnitId(UPDATED_HIER_UNIT_ID)
            .tzIndex(UPDATED_TZ_INDEX)
            .tzName(UPDATED_TZ_NAME)
            .localeInfoId(UPDATED_LOCALE_INFO_ID)
            .name(UPDATED_NAME)
            .reportingLocName(UPDATED_REPORTING_LOC_NAME)
            .locRef(UPDATED_LOC_REF)
            .reportingParentEnterpriseLevelName(UPDATED_REPORTING_PARENT_ENTERPRISE_LEVEL_NAME)
            .objectNum(UPDATED_OBJECT_NUM)
            .sbmPmsIfcIp(UPDATED_SBM_PMS_IFC_IP)
            .sbmPmsIfcPort(UPDATED_SBM_PMS_IFC_PORT)
            .sbmPriveRoomStart(UPDATED_SBM_PRIVE_ROOM_START)
            .sbmPriveRoomEnd(UPDATED_SBM_PRIVE_ROOM_END)
            .sbmPmsSendAllDetails(UPDATED_SBM_PMS_SEND_ALL_DETAILS)
            .sbmPmsSendFullDscv(UPDATED_SBM_PMS_SEND_FULL_DSCV)
            .sbmPmsSend64Tax(UPDATED_SBM_PMS_SEND_64_TAX)
            .sbmCardPaymentUrl(UPDATED_SBM_CARD_PAYMENT_URL)
            .sbmCheckHotelDataUrl(UPDATED_SBM_CHECK_HOTEL_DATA_URL)
            .sbmVoucherSvcUrl(UPDATED_SBM_VOUCHER_SVC_URL)
            .sbmVoucherInvPm(UPDATED_SBM_VOUCHER_INV_PM)
            .sbmVoucherCorpPm(UPDATED_SBM_VOUCHER_CORP_PM)
            .sbmVoucherRewardPm(UPDATED_SBM_VOUCHER_REWARD_PM)
            .sbmVoucherMcPm(UPDATED_SBM_VOUCHER_MC_PM)
            .sbmPmsIfcPort2(UPDATED_SBM_PMS_IFC_PORT_2)
            .sbmPmsIfcPort3(UPDATED_SBM_PMS_IFC_PORT_3)
            .sbmPmsIfcPort4(UPDATED_SBM_PMS_IFC_PORT_4)
            .sbmTimeout(UPDATED_SBM_TIMEOUT);

        restLocationCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocationCnC.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLocationCnC))
            )
            .andExpect(status().isOk());

        // Validate the LocationCnC in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocationCnCUpdatableFieldsEquals(partialUpdatedLocationCnC, getPersistedLocationCnC(partialUpdatedLocationCnC));
    }

    @Test
    @Transactional
    void patchNonExistingLocationCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locationCnC.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, locationCnC.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(locationCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocationCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLocationCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locationCnC.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationCnCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(locationCnC))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocationCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLocationCnC() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locationCnC.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationCnCMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(locationCnC)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LocationCnC in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLocationCnC() throws Exception {
        // Initialize the database
        insertedLocationCnC = locationCnCRepository.saveAndFlush(locationCnC);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the locationCnC
        restLocationCnCMockMvc
            .perform(delete(ENTITY_API_URL_ID, locationCnC.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return locationCnCRepository.count();
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

    protected LocationCnC getPersistedLocationCnC(LocationCnC locationCnC) {
        return locationCnCRepository.findById(locationCnC.getId()).orElseThrow();
    }

    protected void assertPersistedLocationCnCToMatchAllProperties(LocationCnC expectedLocationCnC) {
        assertLocationCnCAllPropertiesEquals(expectedLocationCnC, getPersistedLocationCnC(expectedLocationCnC));
    }

    protected void assertPersistedLocationCnCToMatchUpdatableProperties(LocationCnC expectedLocationCnC) {
        assertLocationCnCAllUpdatablePropertiesEquals(expectedLocationCnC, getPersistedLocationCnC(expectedLocationCnC));
    }
}
