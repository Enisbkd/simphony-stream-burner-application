package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.OrganizationLocationAsserts.*;
import static mc.sbm.simphonycloud.web.rest.TestUtil.createUpdateProxyForBean;
import static mc.sbm.simphonycloud.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import mc.sbm.simphonycloud.IntegrationTest;
import mc.sbm.simphonycloud.domain.OrganizationLocation;
import mc.sbm.simphonycloud.repository.OrganizationLocationRepository;
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
 * Integration tests for the {@link OrganizationLocationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrganizationLocationResourceIT {

    private static final String DEFAULT_ORG_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORG_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOC_REF = "AAAAAAAAAA";
    private static final String UPDATED_LOC_REF = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGES = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGES = "BBBBBBBBBB";

    private static final String DEFAULT_TIMEZONE_IANA_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TIMEZONE_IANA_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TIMEZONE_WINDOWS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TIMEZONE_WINDOWS_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_TIMEZONE_TZ_INDEX = 1;
    private static final Integer UPDATED_TIMEZONE_TZ_INDEX = 2;

    private static final String DEFAULT_ADDRESS_LINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LINE_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_FLOOR = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_FLOOR = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LOCALITY = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LOCALITY = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_REGION = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_NOTES = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_GEO_LATITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_GEO_LATITUDE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_GEO_LONGITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_GEO_LONGITUDE = new BigDecimal(2);

    private static final String DEFAULT_POS_PLATFORM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_POS_PLATFORM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_POS_PLATFORM_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_POS_PLATFORM_VERSION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/organization-locations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OrganizationLocationRepository organizationLocationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganizationLocationMockMvc;

    private OrganizationLocation organizationLocation;

    private OrganizationLocation insertedOrganizationLocation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationLocation createEntity() {
        return new OrganizationLocation()
            .orgShortName(DEFAULT_ORG_SHORT_NAME)
            .locRef(DEFAULT_LOC_REF)
            .name(DEFAULT_NAME)
            .currency(DEFAULT_CURRENCY)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .languages(DEFAULT_LANGUAGES)
            .timezoneIanaName(DEFAULT_TIMEZONE_IANA_NAME)
            .timezoneWindowsName(DEFAULT_TIMEZONE_WINDOWS_NAME)
            .timezoneTzIndex(DEFAULT_TIMEZONE_TZ_INDEX)
            .addressLine1(DEFAULT_ADDRESS_LINE_1)
            .addressLine2(DEFAULT_ADDRESS_LINE_2)
            .addressFloor(DEFAULT_ADDRESS_FLOOR)
            .addressLocality(DEFAULT_ADDRESS_LOCALITY)
            .addressRegion(DEFAULT_ADDRESS_REGION)
            .addressPostalCode(DEFAULT_ADDRESS_POSTAL_CODE)
            .addressCountry(DEFAULT_ADDRESS_COUNTRY)
            .addressNotes(DEFAULT_ADDRESS_NOTES)
            .geoLatitude(DEFAULT_GEO_LATITUDE)
            .geoLongitude(DEFAULT_GEO_LONGITUDE)
            .posPlatformName(DEFAULT_POS_PLATFORM_NAME)
            .posPlatformVersion(DEFAULT_POS_PLATFORM_VERSION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationLocation createUpdatedEntity() {
        return new OrganizationLocation()
            .orgShortName(UPDATED_ORG_SHORT_NAME)
            .locRef(UPDATED_LOC_REF)
            .name(UPDATED_NAME)
            .currency(UPDATED_CURRENCY)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .languages(UPDATED_LANGUAGES)
            .timezoneIanaName(UPDATED_TIMEZONE_IANA_NAME)
            .timezoneWindowsName(UPDATED_TIMEZONE_WINDOWS_NAME)
            .timezoneTzIndex(UPDATED_TIMEZONE_TZ_INDEX)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .addressFloor(UPDATED_ADDRESS_FLOOR)
            .addressLocality(UPDATED_ADDRESS_LOCALITY)
            .addressRegion(UPDATED_ADDRESS_REGION)
            .addressPostalCode(UPDATED_ADDRESS_POSTAL_CODE)
            .addressCountry(UPDATED_ADDRESS_COUNTRY)
            .addressNotes(UPDATED_ADDRESS_NOTES)
            .geoLatitude(UPDATED_GEO_LATITUDE)
            .geoLongitude(UPDATED_GEO_LONGITUDE)
            .posPlatformName(UPDATED_POS_PLATFORM_NAME)
            .posPlatformVersion(UPDATED_POS_PLATFORM_VERSION);
    }

    @BeforeEach
    void initTest() {
        organizationLocation = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedOrganizationLocation != null) {
            organizationLocationRepository.delete(insertedOrganizationLocation);
            insertedOrganizationLocation = null;
        }
    }

    @Test
    @Transactional
    void createOrganizationLocation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the OrganizationLocation
        var returnedOrganizationLocation = om.readValue(
            restOrganizationLocationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(organizationLocation)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OrganizationLocation.class
        );

        // Validate the OrganizationLocation in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOrganizationLocationUpdatableFieldsEquals(
            returnedOrganizationLocation,
            getPersistedOrganizationLocation(returnedOrganizationLocation)
        );

        insertedOrganizationLocation = returnedOrganizationLocation;
    }

    @Test
    @Transactional
    void createOrganizationLocationWithExistingId() throws Exception {
        // Create the OrganizationLocation with an existing ID
        organizationLocation.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(organizationLocation)))
            .andExpect(status().isBadRequest());

        // Validate the OrganizationLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrganizationLocations() throws Exception {
        // Initialize the database
        insertedOrganizationLocation = organizationLocationRepository.saveAndFlush(organizationLocation);

        // Get all the organizationLocationList
        restOrganizationLocationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organizationLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].orgShortName").value(hasItem(DEFAULT_ORG_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].locRef").value(hasItem(DEFAULT_LOC_REF)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].languages").value(hasItem(DEFAULT_LANGUAGES)))
            .andExpect(jsonPath("$.[*].timezoneIanaName").value(hasItem(DEFAULT_TIMEZONE_IANA_NAME)))
            .andExpect(jsonPath("$.[*].timezoneWindowsName").value(hasItem(DEFAULT_TIMEZONE_WINDOWS_NAME)))
            .andExpect(jsonPath("$.[*].timezoneTzIndex").value(hasItem(DEFAULT_TIMEZONE_TZ_INDEX)))
            .andExpect(jsonPath("$.[*].addressLine1").value(hasItem(DEFAULT_ADDRESS_LINE_1)))
            .andExpect(jsonPath("$.[*].addressLine2").value(hasItem(DEFAULT_ADDRESS_LINE_2)))
            .andExpect(jsonPath("$.[*].addressFloor").value(hasItem(DEFAULT_ADDRESS_FLOOR)))
            .andExpect(jsonPath("$.[*].addressLocality").value(hasItem(DEFAULT_ADDRESS_LOCALITY)))
            .andExpect(jsonPath("$.[*].addressRegion").value(hasItem(DEFAULT_ADDRESS_REGION)))
            .andExpect(jsonPath("$.[*].addressPostalCode").value(hasItem(DEFAULT_ADDRESS_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].addressCountry").value(hasItem(DEFAULT_ADDRESS_COUNTRY)))
            .andExpect(jsonPath("$.[*].addressNotes").value(hasItem(DEFAULT_ADDRESS_NOTES)))
            .andExpect(jsonPath("$.[*].geoLatitude").value(hasItem(sameNumber(DEFAULT_GEO_LATITUDE))))
            .andExpect(jsonPath("$.[*].geoLongitude").value(hasItem(sameNumber(DEFAULT_GEO_LONGITUDE))))
            .andExpect(jsonPath("$.[*].posPlatformName").value(hasItem(DEFAULT_POS_PLATFORM_NAME)))
            .andExpect(jsonPath("$.[*].posPlatformVersion").value(hasItem(DEFAULT_POS_PLATFORM_VERSION)));
    }

    @Test
    @Transactional
    void getOrganizationLocation() throws Exception {
        // Initialize the database
        insertedOrganizationLocation = organizationLocationRepository.saveAndFlush(organizationLocation);

        // Get the organizationLocation
        restOrganizationLocationMockMvc
            .perform(get(ENTITY_API_URL_ID, organizationLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organizationLocation.getId().intValue()))
            .andExpect(jsonPath("$.orgShortName").value(DEFAULT_ORG_SHORT_NAME))
            .andExpect(jsonPath("$.locRef").value(DEFAULT_LOC_REF))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.languages").value(DEFAULT_LANGUAGES))
            .andExpect(jsonPath("$.timezoneIanaName").value(DEFAULT_TIMEZONE_IANA_NAME))
            .andExpect(jsonPath("$.timezoneWindowsName").value(DEFAULT_TIMEZONE_WINDOWS_NAME))
            .andExpect(jsonPath("$.timezoneTzIndex").value(DEFAULT_TIMEZONE_TZ_INDEX))
            .andExpect(jsonPath("$.addressLine1").value(DEFAULT_ADDRESS_LINE_1))
            .andExpect(jsonPath("$.addressLine2").value(DEFAULT_ADDRESS_LINE_2))
            .andExpect(jsonPath("$.addressFloor").value(DEFAULT_ADDRESS_FLOOR))
            .andExpect(jsonPath("$.addressLocality").value(DEFAULT_ADDRESS_LOCALITY))
            .andExpect(jsonPath("$.addressRegion").value(DEFAULT_ADDRESS_REGION))
            .andExpect(jsonPath("$.addressPostalCode").value(DEFAULT_ADDRESS_POSTAL_CODE))
            .andExpect(jsonPath("$.addressCountry").value(DEFAULT_ADDRESS_COUNTRY))
            .andExpect(jsonPath("$.addressNotes").value(DEFAULT_ADDRESS_NOTES))
            .andExpect(jsonPath("$.geoLatitude").value(sameNumber(DEFAULT_GEO_LATITUDE)))
            .andExpect(jsonPath("$.geoLongitude").value(sameNumber(DEFAULT_GEO_LONGITUDE)))
            .andExpect(jsonPath("$.posPlatformName").value(DEFAULT_POS_PLATFORM_NAME))
            .andExpect(jsonPath("$.posPlatformVersion").value(DEFAULT_POS_PLATFORM_VERSION));
    }

    @Test
    @Transactional
    void getNonExistingOrganizationLocation() throws Exception {
        // Get the organizationLocation
        restOrganizationLocationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrganizationLocation() throws Exception {
        // Initialize the database
        insertedOrganizationLocation = organizationLocationRepository.saveAndFlush(organizationLocation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organizationLocation
        OrganizationLocation updatedOrganizationLocation = organizationLocationRepository
            .findById(organizationLocation.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedOrganizationLocation are not directly saved in db
        em.detach(updatedOrganizationLocation);
        updatedOrganizationLocation
            .orgShortName(UPDATED_ORG_SHORT_NAME)
            .locRef(UPDATED_LOC_REF)
            .name(UPDATED_NAME)
            .currency(UPDATED_CURRENCY)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .languages(UPDATED_LANGUAGES)
            .timezoneIanaName(UPDATED_TIMEZONE_IANA_NAME)
            .timezoneWindowsName(UPDATED_TIMEZONE_WINDOWS_NAME)
            .timezoneTzIndex(UPDATED_TIMEZONE_TZ_INDEX)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .addressFloor(UPDATED_ADDRESS_FLOOR)
            .addressLocality(UPDATED_ADDRESS_LOCALITY)
            .addressRegion(UPDATED_ADDRESS_REGION)
            .addressPostalCode(UPDATED_ADDRESS_POSTAL_CODE)
            .addressCountry(UPDATED_ADDRESS_COUNTRY)
            .addressNotes(UPDATED_ADDRESS_NOTES)
            .geoLatitude(UPDATED_GEO_LATITUDE)
            .geoLongitude(UPDATED_GEO_LONGITUDE)
            .posPlatformName(UPDATED_POS_PLATFORM_NAME)
            .posPlatformVersion(UPDATED_POS_PLATFORM_VERSION);

        restOrganizationLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrganizationLocation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOrganizationLocation))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOrganizationLocationToMatchAllProperties(updatedOrganizationLocation);
    }

    @Test
    @Transactional
    void putNonExistingOrganizationLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationLocation.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizationLocation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationLocation))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrganizationLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationLocation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationLocation))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrganizationLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationLocation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationLocationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(organizationLocation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrganizationLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrganizationLocationWithPatch() throws Exception {
        // Initialize the database
        insertedOrganizationLocation = organizationLocationRepository.saveAndFlush(organizationLocation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organizationLocation using partial update
        OrganizationLocation partialUpdatedOrganizationLocation = new OrganizationLocation();
        partialUpdatedOrganizationLocation.setId(organizationLocation.getId());

        partialUpdatedOrganizationLocation
            .locRef(UPDATED_LOC_REF)
            .name(UPDATED_NAME)
            .languages(UPDATED_LANGUAGES)
            .timezoneTzIndex(UPDATED_TIMEZONE_TZ_INDEX)
            .addressLocality(UPDATED_ADDRESS_LOCALITY)
            .addressNotes(UPDATED_ADDRESS_NOTES);

        restOrganizationLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganizationLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrganizationLocation))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationLocation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrganizationLocationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOrganizationLocation, organizationLocation),
            getPersistedOrganizationLocation(organizationLocation)
        );
    }

    @Test
    @Transactional
    void fullUpdateOrganizationLocationWithPatch() throws Exception {
        // Initialize the database
        insertedOrganizationLocation = organizationLocationRepository.saveAndFlush(organizationLocation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organizationLocation using partial update
        OrganizationLocation partialUpdatedOrganizationLocation = new OrganizationLocation();
        partialUpdatedOrganizationLocation.setId(organizationLocation.getId());

        partialUpdatedOrganizationLocation
            .orgShortName(UPDATED_ORG_SHORT_NAME)
            .locRef(UPDATED_LOC_REF)
            .name(UPDATED_NAME)
            .currency(UPDATED_CURRENCY)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .languages(UPDATED_LANGUAGES)
            .timezoneIanaName(UPDATED_TIMEZONE_IANA_NAME)
            .timezoneWindowsName(UPDATED_TIMEZONE_WINDOWS_NAME)
            .timezoneTzIndex(UPDATED_TIMEZONE_TZ_INDEX)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .addressFloor(UPDATED_ADDRESS_FLOOR)
            .addressLocality(UPDATED_ADDRESS_LOCALITY)
            .addressRegion(UPDATED_ADDRESS_REGION)
            .addressPostalCode(UPDATED_ADDRESS_POSTAL_CODE)
            .addressCountry(UPDATED_ADDRESS_COUNTRY)
            .addressNotes(UPDATED_ADDRESS_NOTES)
            .geoLatitude(UPDATED_GEO_LATITUDE)
            .geoLongitude(UPDATED_GEO_LONGITUDE)
            .posPlatformName(UPDATED_POS_PLATFORM_NAME)
            .posPlatformVersion(UPDATED_POS_PLATFORM_VERSION);

        restOrganizationLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganizationLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrganizationLocation))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationLocation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrganizationLocationUpdatableFieldsEquals(
            partialUpdatedOrganizationLocation,
            getPersistedOrganizationLocation(partialUpdatedOrganizationLocation)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOrganizationLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationLocation.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, organizationLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(organizationLocation))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrganizationLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationLocation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(organizationLocation))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrganizationLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationLocation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationLocationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(organizationLocation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrganizationLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrganizationLocation() throws Exception {
        // Initialize the database
        insertedOrganizationLocation = organizationLocationRepository.saveAndFlush(organizationLocation);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the organizationLocation
        restOrganizationLocationMockMvc
            .perform(delete(ENTITY_API_URL_ID, organizationLocation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return organizationLocationRepository.count();
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

    protected OrganizationLocation getPersistedOrganizationLocation(OrganizationLocation organizationLocation) {
        return organizationLocationRepository.findById(organizationLocation.getId()).orElseThrow();
    }

    protected void assertPersistedOrganizationLocationToMatchAllProperties(OrganizationLocation expectedOrganizationLocation) {
        assertOrganizationLocationAllPropertiesEquals(
            expectedOrganizationLocation,
            getPersistedOrganizationLocation(expectedOrganizationLocation)
        );
    }

    protected void assertPersistedOrganizationLocationToMatchUpdatableProperties(OrganizationLocation expectedOrganizationLocation) {
        assertOrganizationLocationAllUpdatablePropertiesEquals(
            expectedOrganizationLocation,
            getPersistedOrganizationLocation(expectedOrganizationLocation)
        );
    }
}
