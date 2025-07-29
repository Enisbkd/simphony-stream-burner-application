package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.LocationAsserts.*;
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
import mc.sbm.simphonycloud.domain.Location;
import mc.sbm.simphonycloud.repository.LocationRepository;
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
 * Integration tests for the {@link LocationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LocationResourceIT {

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

    private static final String ENTITY_API_URL = "/api/locations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocationMockMvc;

    private Location location;

    private Location insertedLocation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Location createEntity() {
        return new Location()
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
    public static Location createUpdatedEntity() {
        return new Location()
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
        location = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedLocation != null) {
            locationRepository.delete(insertedLocation);
            insertedLocation = null;
        }
    }

    @Test
    @Transactional
    void createLocation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Location
        var returnedLocation = om.readValue(
            restLocationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(location)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Location.class
        );

        // Validate the Location in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLocationUpdatableFieldsEquals(returnedLocation, getPersistedLocation(returnedLocation));

        insertedLocation = returnedLocation;
    }

    @Test
    @Transactional
    void createLocationWithExistingId() throws Exception {
        // Create the Location with an existing ID
        location.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(location)))
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLocations() throws Exception {
        // Initialize the database
        insertedLocation = locationRepository.saveAndFlush(location);

        // Get all the locationList
        restLocationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(location.getId().intValue())))
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
    void getLocation() throws Exception {
        // Initialize the database
        insertedLocation = locationRepository.saveAndFlush(location);

        // Get the location
        restLocationMockMvc
            .perform(get(ENTITY_API_URL_ID, location.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(location.getId().intValue()))
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
    void getNonExistingLocation() throws Exception {
        // Get the location
        restLocationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLocation() throws Exception {
        // Initialize the database
        insertedLocation = locationRepository.saveAndFlush(location);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the location
        Location updatedLocation = locationRepository.findById(location.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLocation are not directly saved in db
        em.detach(updatedLocation);
        updatedLocation
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

        restLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLocation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLocation))
            )
            .andExpect(status().isOk());

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLocationToMatchAllProperties(updatedLocation);
    }

    @Test
    @Transactional
    void putNonExistingLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, location.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(location))
            )
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(location))
            )
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(location)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLocationWithPatch() throws Exception {
        // Initialize the database
        insertedLocation = locationRepository.saveAndFlush(location);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the location using partial update
        Location partialUpdatedLocation = new Location();
        partialUpdatedLocation.setId(location.getId());

        partialUpdatedLocation
            .hierUnitId(UPDATED_HIER_UNIT_ID)
            .tzIndex(UPDATED_TZ_INDEX)
            .localeInfoId(UPDATED_LOCALE_INFO_ID)
            .name(UPDATED_NAME)
            .reportingLocName(UPDATED_REPORTING_LOC_NAME)
            .reportingParentEnterpriseLevelName(UPDATED_REPORTING_PARENT_ENTERPRISE_LEVEL_NAME)
            .sbmPmsIfcIp(UPDATED_SBM_PMS_IFC_IP)
            .sbmPmsIfcPort(UPDATED_SBM_PMS_IFC_PORT)
            .sbmPriveRoomEnd(UPDATED_SBM_PRIVE_ROOM_END)
            .sbmPmsSendFullDscv(UPDATED_SBM_PMS_SEND_FULL_DSCV)
            .sbmPmsSend64Tax(UPDATED_SBM_PMS_SEND_64_TAX)
            .sbmCardPaymentUrl(UPDATED_SBM_CARD_PAYMENT_URL)
            .sbmCheckHotelDataUrl(UPDATED_SBM_CHECK_HOTEL_DATA_URL)
            .sbmVoucherInvPm(UPDATED_SBM_VOUCHER_INV_PM)
            .sbmVoucherRewardPm(UPDATED_SBM_VOUCHER_REWARD_PM)
            .sbmPmsIfcPort4(UPDATED_SBM_PMS_IFC_PORT_4);

        restLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLocation))
            )
            .andExpect(status().isOk());

        // Validate the Location in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocationUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedLocation, location), getPersistedLocation(location));
    }

    @Test
    @Transactional
    void fullUpdateLocationWithPatch() throws Exception {
        // Initialize the database
        insertedLocation = locationRepository.saveAndFlush(location);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the location using partial update
        Location partialUpdatedLocation = new Location();
        partialUpdatedLocation.setId(location.getId());

        partialUpdatedLocation
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

        restLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLocation))
            )
            .andExpect(status().isOk());

        // Validate the Location in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocationUpdatableFieldsEquals(partialUpdatedLocation, getPersistedLocation(partialUpdatedLocation));
    }

    @Test
    @Transactional
    void patchNonExistingLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, location.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(location))
            )
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(location))
            )
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(location)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLocation() throws Exception {
        // Initialize the database
        insertedLocation = locationRepository.saveAndFlush(location);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the location
        restLocationMockMvc
            .perform(delete(ENTITY_API_URL_ID, location.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return locationRepository.count();
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

    protected Location getPersistedLocation(Location location) {
        return locationRepository.findById(location.getId()).orElseThrow();
    }

    protected void assertPersistedLocationToMatchAllProperties(Location expectedLocation) {
        assertLocationAllPropertiesEquals(expectedLocation, getPersistedLocation(expectedLocation));
    }

    protected void assertPersistedLocationToMatchUpdatableProperties(Location expectedLocation) {
        assertLocationAllUpdatablePropertiesEquals(expectedLocation, getPersistedLocation(expectedLocation));
    }
}
