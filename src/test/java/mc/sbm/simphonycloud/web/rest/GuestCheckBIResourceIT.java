package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.GuestCheckBIAsserts.*;
import static mc.sbm.simphonycloud.web.rest.TestUtil.createUpdateProxyForBean;
import static mc.sbm.simphonycloud.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import mc.sbm.simphonycloud.IntegrationTest;
import mc.sbm.simphonycloud.domain.GuestCheckBI;
import mc.sbm.simphonycloud.repository.GuestCheckBIRepository;
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
 * Integration tests for the {@link GuestCheckBIResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GuestCheckBIResourceIT {

    private static final Integer DEFAULT_GUEST_CHECK_ID = 1;
    private static final Integer UPDATED_GUEST_CHECK_ID = 2;

    private static final Integer DEFAULT_CHK_NUM = 1;
    private static final Integer UPDATED_CHK_NUM = 2;

    private static final Instant DEFAULT_OPN_LCL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OPN_LCL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CLSD_LCL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CLSD_LCL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_CANCEL_FLAG = false;
    private static final Boolean UPDATED_CANCEL_FLAG = true;

    private static final Integer DEFAULT_GST_CNT = 1;
    private static final Integer UPDATED_GST_CNT = 2;

    private static final Integer DEFAULT_TBL_NUM = 1;
    private static final Integer UPDATED_TBL_NUM = 2;

    private static final BigDecimal DEFAULT_TAX_COLL_TTL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX_COLL_TTL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SUB_TTL = new BigDecimal(1);
    private static final BigDecimal UPDATED_SUB_TTL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CHK_TTL = new BigDecimal(1);
    private static final BigDecimal UPDATED_CHK_TTL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SVC_CHG_TTL = new BigDecimal(1);
    private static final BigDecimal UPDATED_SVC_CHG_TTL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TIP_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TIP_TOTAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DSC_TTL = new BigDecimal(1);
    private static final BigDecimal UPDATED_DSC_TTL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_ERROR_CORRECT_TTL = new BigDecimal(1);
    private static final BigDecimal UPDATED_ERROR_CORRECT_TTL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_RETURN_TTL = new BigDecimal(1);
    private static final BigDecimal UPDATED_RETURN_TTL = new BigDecimal(2);

    private static final Integer DEFAULT_XFER_TO_CHK_NUM = 1;
    private static final Integer UPDATED_XFER_TO_CHK_NUM = 2;

    private static final String DEFAULT_XFER_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_XFER_STATUS = "BBBBBBBBBB";

    private static final Integer DEFAULT_OT_NUM = 1;
    private static final Integer UPDATED_OT_NUM = 2;

    private static final String DEFAULT_LOC_REF = "AAAAAAAAAA";
    private static final String UPDATED_LOC_REF = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/guest-check-bis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GuestCheckBIRepository guestCheckBIRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGuestCheckBIMockMvc;

    private GuestCheckBI guestCheckBI;

    private GuestCheckBI insertedGuestCheckBI;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GuestCheckBI createEntity() {
        return new GuestCheckBI()
            .guestCheckId(DEFAULT_GUEST_CHECK_ID)
            .chkNum(DEFAULT_CHK_NUM)
            .opnLcl(DEFAULT_OPN_LCL)
            .clsdLcl(DEFAULT_CLSD_LCL)
            .cancelFlag(DEFAULT_CANCEL_FLAG)
            .gstCnt(DEFAULT_GST_CNT)
            .tblNum(DEFAULT_TBL_NUM)
            .taxCollTtl(DEFAULT_TAX_COLL_TTL)
            .subTtl(DEFAULT_SUB_TTL)
            .chkTtl(DEFAULT_CHK_TTL)
            .svcChgTtl(DEFAULT_SVC_CHG_TTL)
            .tipTotal(DEFAULT_TIP_TOTAL)
            .dscTtl(DEFAULT_DSC_TTL)
            .errorCorrectTtl(DEFAULT_ERROR_CORRECT_TTL)
            .returnTtl(DEFAULT_RETURN_TTL)
            .xferToChkNum(DEFAULT_XFER_TO_CHK_NUM)
            .xferStatus(DEFAULT_XFER_STATUS)
            .otNum(DEFAULT_OT_NUM)
            .locRef(DEFAULT_LOC_REF);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GuestCheckBI createUpdatedEntity() {
        return new GuestCheckBI()
            .guestCheckId(UPDATED_GUEST_CHECK_ID)
            .chkNum(UPDATED_CHK_NUM)
            .opnLcl(UPDATED_OPN_LCL)
            .clsdLcl(UPDATED_CLSD_LCL)
            .cancelFlag(UPDATED_CANCEL_FLAG)
            .gstCnt(UPDATED_GST_CNT)
            .tblNum(UPDATED_TBL_NUM)
            .taxCollTtl(UPDATED_TAX_COLL_TTL)
            .subTtl(UPDATED_SUB_TTL)
            .chkTtl(UPDATED_CHK_TTL)
            .svcChgTtl(UPDATED_SVC_CHG_TTL)
            .tipTotal(UPDATED_TIP_TOTAL)
            .dscTtl(UPDATED_DSC_TTL)
            .errorCorrectTtl(UPDATED_ERROR_CORRECT_TTL)
            .returnTtl(UPDATED_RETURN_TTL)
            .xferToChkNum(UPDATED_XFER_TO_CHK_NUM)
            .xferStatus(UPDATED_XFER_STATUS)
            .otNum(UPDATED_OT_NUM)
            .locRef(UPDATED_LOC_REF);
    }

    @BeforeEach
    void initTest() {
        guestCheckBI = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedGuestCheckBI != null) {
            guestCheckBIRepository.delete(insertedGuestCheckBI);
            insertedGuestCheckBI = null;
        }
    }

    @Test
    @Transactional
    void createGuestCheckBI() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the GuestCheckBI
        var returnedGuestCheckBI = om.readValue(
            restGuestCheckBIMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(guestCheckBI)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            GuestCheckBI.class
        );

        // Validate the GuestCheckBI in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGuestCheckBIUpdatableFieldsEquals(returnedGuestCheckBI, getPersistedGuestCheckBI(returnedGuestCheckBI));

        insertedGuestCheckBI = returnedGuestCheckBI;
    }

    @Test
    @Transactional
    void createGuestCheckBIWithExistingId() throws Exception {
        // Create the GuestCheckBI with an existing ID
        guestCheckBI.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGuestCheckBIMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(guestCheckBI)))
            .andExpect(status().isBadRequest());

        // Validate the GuestCheckBI in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGuestCheckBIS() throws Exception {
        // Initialize the database
        insertedGuestCheckBI = guestCheckBIRepository.saveAndFlush(guestCheckBI);

        // Get all the guestCheckBIList
        restGuestCheckBIMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(guestCheckBI.getId().intValue())))
            .andExpect(jsonPath("$.[*].guestCheckId").value(hasItem(DEFAULT_GUEST_CHECK_ID)))
            .andExpect(jsonPath("$.[*].chkNum").value(hasItem(DEFAULT_CHK_NUM)))
            .andExpect(jsonPath("$.[*].opnLcl").value(hasItem(DEFAULT_OPN_LCL.toString())))
            .andExpect(jsonPath("$.[*].clsdLcl").value(hasItem(DEFAULT_CLSD_LCL.toString())))
            .andExpect(jsonPath("$.[*].cancelFlag").value(hasItem(DEFAULT_CANCEL_FLAG)))
            .andExpect(jsonPath("$.[*].gstCnt").value(hasItem(DEFAULT_GST_CNT)))
            .andExpect(jsonPath("$.[*].tblNum").value(hasItem(DEFAULT_TBL_NUM)))
            .andExpect(jsonPath("$.[*].taxCollTtl").value(hasItem(sameNumber(DEFAULT_TAX_COLL_TTL))))
            .andExpect(jsonPath("$.[*].subTtl").value(hasItem(sameNumber(DEFAULT_SUB_TTL))))
            .andExpect(jsonPath("$.[*].chkTtl").value(hasItem(sameNumber(DEFAULT_CHK_TTL))))
            .andExpect(jsonPath("$.[*].svcChgTtl").value(hasItem(sameNumber(DEFAULT_SVC_CHG_TTL))))
            .andExpect(jsonPath("$.[*].tipTotal").value(hasItem(sameNumber(DEFAULT_TIP_TOTAL))))
            .andExpect(jsonPath("$.[*].dscTtl").value(hasItem(sameNumber(DEFAULT_DSC_TTL))))
            .andExpect(jsonPath("$.[*].errorCorrectTtl").value(hasItem(sameNumber(DEFAULT_ERROR_CORRECT_TTL))))
            .andExpect(jsonPath("$.[*].returnTtl").value(hasItem(sameNumber(DEFAULT_RETURN_TTL))))
            .andExpect(jsonPath("$.[*].xferToChkNum").value(hasItem(DEFAULT_XFER_TO_CHK_NUM)))
            .andExpect(jsonPath("$.[*].xferStatus").value(hasItem(DEFAULT_XFER_STATUS)))
            .andExpect(jsonPath("$.[*].otNum").value(hasItem(DEFAULT_OT_NUM)))
            .andExpect(jsonPath("$.[*].locRef").value(hasItem(DEFAULT_LOC_REF)));
    }

    @Test
    @Transactional
    void getGuestCheckBI() throws Exception {
        // Initialize the database
        insertedGuestCheckBI = guestCheckBIRepository.saveAndFlush(guestCheckBI);

        // Get the guestCheckBI
        restGuestCheckBIMockMvc
            .perform(get(ENTITY_API_URL_ID, guestCheckBI.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(guestCheckBI.getId().intValue()))
            .andExpect(jsonPath("$.guestCheckId").value(DEFAULT_GUEST_CHECK_ID))
            .andExpect(jsonPath("$.chkNum").value(DEFAULT_CHK_NUM))
            .andExpect(jsonPath("$.opnLcl").value(DEFAULT_OPN_LCL.toString()))
            .andExpect(jsonPath("$.clsdLcl").value(DEFAULT_CLSD_LCL.toString()))
            .andExpect(jsonPath("$.cancelFlag").value(DEFAULT_CANCEL_FLAG))
            .andExpect(jsonPath("$.gstCnt").value(DEFAULT_GST_CNT))
            .andExpect(jsonPath("$.tblNum").value(DEFAULT_TBL_NUM))
            .andExpect(jsonPath("$.taxCollTtl").value(sameNumber(DEFAULT_TAX_COLL_TTL)))
            .andExpect(jsonPath("$.subTtl").value(sameNumber(DEFAULT_SUB_TTL)))
            .andExpect(jsonPath("$.chkTtl").value(sameNumber(DEFAULT_CHK_TTL)))
            .andExpect(jsonPath("$.svcChgTtl").value(sameNumber(DEFAULT_SVC_CHG_TTL)))
            .andExpect(jsonPath("$.tipTotal").value(sameNumber(DEFAULT_TIP_TOTAL)))
            .andExpect(jsonPath("$.dscTtl").value(sameNumber(DEFAULT_DSC_TTL)))
            .andExpect(jsonPath("$.errorCorrectTtl").value(sameNumber(DEFAULT_ERROR_CORRECT_TTL)))
            .andExpect(jsonPath("$.returnTtl").value(sameNumber(DEFAULT_RETURN_TTL)))
            .andExpect(jsonPath("$.xferToChkNum").value(DEFAULT_XFER_TO_CHK_NUM))
            .andExpect(jsonPath("$.xferStatus").value(DEFAULT_XFER_STATUS))
            .andExpect(jsonPath("$.otNum").value(DEFAULT_OT_NUM))
            .andExpect(jsonPath("$.locRef").value(DEFAULT_LOC_REF));
    }

    @Test
    @Transactional
    void getNonExistingGuestCheckBI() throws Exception {
        // Get the guestCheckBI
        restGuestCheckBIMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGuestCheckBI() throws Exception {
        // Initialize the database
        insertedGuestCheckBI = guestCheckBIRepository.saveAndFlush(guestCheckBI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the guestCheckBI
        GuestCheckBI updatedGuestCheckBI = guestCheckBIRepository.findById(guestCheckBI.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGuestCheckBI are not directly saved in db
        em.detach(updatedGuestCheckBI);
        updatedGuestCheckBI
            .guestCheckId(UPDATED_GUEST_CHECK_ID)
            .chkNum(UPDATED_CHK_NUM)
            .opnLcl(UPDATED_OPN_LCL)
            .clsdLcl(UPDATED_CLSD_LCL)
            .cancelFlag(UPDATED_CANCEL_FLAG)
            .gstCnt(UPDATED_GST_CNT)
            .tblNum(UPDATED_TBL_NUM)
            .taxCollTtl(UPDATED_TAX_COLL_TTL)
            .subTtl(UPDATED_SUB_TTL)
            .chkTtl(UPDATED_CHK_TTL)
            .svcChgTtl(UPDATED_SVC_CHG_TTL)
            .tipTotal(UPDATED_TIP_TOTAL)
            .dscTtl(UPDATED_DSC_TTL)
            .errorCorrectTtl(UPDATED_ERROR_CORRECT_TTL)
            .returnTtl(UPDATED_RETURN_TTL)
            .xferToChkNum(UPDATED_XFER_TO_CHK_NUM)
            .xferStatus(UPDATED_XFER_STATUS)
            .otNum(UPDATED_OT_NUM)
            .locRef(UPDATED_LOC_REF);

        restGuestCheckBIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGuestCheckBI.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGuestCheckBI))
            )
            .andExpect(status().isOk());

        // Validate the GuestCheckBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGuestCheckBIToMatchAllProperties(updatedGuestCheckBI);
    }

    @Test
    @Transactional
    void putNonExistingGuestCheckBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        guestCheckBI.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGuestCheckBIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, guestCheckBI.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(guestCheckBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuestCheckBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGuestCheckBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        guestCheckBI.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestCheckBIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(guestCheckBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuestCheckBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGuestCheckBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        guestCheckBI.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestCheckBIMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(guestCheckBI)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GuestCheckBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGuestCheckBIWithPatch() throws Exception {
        // Initialize the database
        insertedGuestCheckBI = guestCheckBIRepository.saveAndFlush(guestCheckBI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the guestCheckBI using partial update
        GuestCheckBI partialUpdatedGuestCheckBI = new GuestCheckBI();
        partialUpdatedGuestCheckBI.setId(guestCheckBI.getId());

        partialUpdatedGuestCheckBI
            .guestCheckId(UPDATED_GUEST_CHECK_ID)
            .gstCnt(UPDATED_GST_CNT)
            .tblNum(UPDATED_TBL_NUM)
            .taxCollTtl(UPDATED_TAX_COLL_TTL)
            .chkTtl(UPDATED_CHK_TTL)
            .svcChgTtl(UPDATED_SVC_CHG_TTL)
            .tipTotal(UPDATED_TIP_TOTAL)
            .dscTtl(UPDATED_DSC_TTL)
            .returnTtl(UPDATED_RETURN_TTL)
            .xferToChkNum(UPDATED_XFER_TO_CHK_NUM)
            .xferStatus(UPDATED_XFER_STATUS)
            .locRef(UPDATED_LOC_REF);

        restGuestCheckBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGuestCheckBI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGuestCheckBI))
            )
            .andExpect(status().isOk());

        // Validate the GuestCheckBI in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGuestCheckBIUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGuestCheckBI, guestCheckBI),
            getPersistedGuestCheckBI(guestCheckBI)
        );
    }

    @Test
    @Transactional
    void fullUpdateGuestCheckBIWithPatch() throws Exception {
        // Initialize the database
        insertedGuestCheckBI = guestCheckBIRepository.saveAndFlush(guestCheckBI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the guestCheckBI using partial update
        GuestCheckBI partialUpdatedGuestCheckBI = new GuestCheckBI();
        partialUpdatedGuestCheckBI.setId(guestCheckBI.getId());

        partialUpdatedGuestCheckBI
            .guestCheckId(UPDATED_GUEST_CHECK_ID)
            .chkNum(UPDATED_CHK_NUM)
            .opnLcl(UPDATED_OPN_LCL)
            .clsdLcl(UPDATED_CLSD_LCL)
            .cancelFlag(UPDATED_CANCEL_FLAG)
            .gstCnt(UPDATED_GST_CNT)
            .tblNum(UPDATED_TBL_NUM)
            .taxCollTtl(UPDATED_TAX_COLL_TTL)
            .subTtl(UPDATED_SUB_TTL)
            .chkTtl(UPDATED_CHK_TTL)
            .svcChgTtl(UPDATED_SVC_CHG_TTL)
            .tipTotal(UPDATED_TIP_TOTAL)
            .dscTtl(UPDATED_DSC_TTL)
            .errorCorrectTtl(UPDATED_ERROR_CORRECT_TTL)
            .returnTtl(UPDATED_RETURN_TTL)
            .xferToChkNum(UPDATED_XFER_TO_CHK_NUM)
            .xferStatus(UPDATED_XFER_STATUS)
            .otNum(UPDATED_OT_NUM)
            .locRef(UPDATED_LOC_REF);

        restGuestCheckBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGuestCheckBI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGuestCheckBI))
            )
            .andExpect(status().isOk());

        // Validate the GuestCheckBI in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGuestCheckBIUpdatableFieldsEquals(partialUpdatedGuestCheckBI, getPersistedGuestCheckBI(partialUpdatedGuestCheckBI));
    }

    @Test
    @Transactional
    void patchNonExistingGuestCheckBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        guestCheckBI.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGuestCheckBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, guestCheckBI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(guestCheckBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuestCheckBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGuestCheckBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        guestCheckBI.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestCheckBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(guestCheckBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuestCheckBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGuestCheckBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        guestCheckBI.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestCheckBIMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(guestCheckBI)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GuestCheckBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGuestCheckBI() throws Exception {
        // Initialize the database
        insertedGuestCheckBI = guestCheckBIRepository.saveAndFlush(guestCheckBI);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the guestCheckBI
        restGuestCheckBIMockMvc
            .perform(delete(ENTITY_API_URL_ID, guestCheckBI.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return guestCheckBIRepository.count();
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

    protected GuestCheckBI getPersistedGuestCheckBI(GuestCheckBI guestCheckBI) {
        return guestCheckBIRepository.findById(guestCheckBI.getId()).orElseThrow();
    }

    protected void assertPersistedGuestCheckBIToMatchAllProperties(GuestCheckBI expectedGuestCheckBI) {
        assertGuestCheckBIAllPropertiesEquals(expectedGuestCheckBI, getPersistedGuestCheckBI(expectedGuestCheckBI));
    }

    protected void assertPersistedGuestCheckBIToMatchUpdatableProperties(GuestCheckBI expectedGuestCheckBI) {
        assertGuestCheckBIAllUpdatablePropertiesEquals(expectedGuestCheckBI, getPersistedGuestCheckBI(expectedGuestCheckBI));
    }
}
