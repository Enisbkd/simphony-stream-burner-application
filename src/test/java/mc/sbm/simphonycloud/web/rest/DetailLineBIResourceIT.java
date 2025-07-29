package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.DetailLineBIAsserts.*;
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
import mc.sbm.simphonycloud.domain.DetailLineBI;
import mc.sbm.simphonycloud.repository.DetailLineBIRepository;
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
 * Integration tests for the {@link DetailLineBIResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DetailLineBIResourceIT {

    private static final Long DEFAULT_GUEST_CHECK_LINE_ITEM_ID = 1L;
    private static final Long UPDATED_GUEST_CHECK_LINE_ITEM_ID = 2L;

    private static final Instant DEFAULT_DETAIL_UTC = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DETAIL_UTC = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DETAIL_LCL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DETAIL_LCL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_SEAT_NUM = 1;
    private static final Integer UPDATED_SEAT_NUM = 2;

    private static final Integer DEFAULT_PRC_LVL = 1;
    private static final Integer UPDATED_PRC_LVL = 2;

    private static final BigDecimal DEFAULT_DSP_TTL = new BigDecimal(1);
    private static final BigDecimal UPDATED_DSP_TTL = new BigDecimal(2);

    private static final Integer DEFAULT_DSP_QTY = 1;
    private static final Integer UPDATED_DSP_QTY = 2;

    private static final Boolean DEFAULT_ERR_COR_FLAG = false;
    private static final Boolean UPDATED_ERR_COR_FLAG = true;

    private static final Boolean DEFAULT_VD_FLAG = false;
    private static final Boolean UPDATED_VD_FLAG = true;

    private static final Boolean DEFAULT_RETURN_FLAG = false;
    private static final Boolean UPDATED_RETURN_FLAG = true;

    private static final Boolean DEFAULT_DO_NOT_SHOW_FLAG = false;
    private static final Boolean UPDATED_DO_NOT_SHOW_FLAG = true;

    private static final BigDecimal DEFAULT_AGG_TTL = new BigDecimal(1);
    private static final BigDecimal UPDATED_AGG_TTL = new BigDecimal(2);

    private static final Integer DEFAULT_RSN_CODE_NUM = 1;
    private static final Integer UPDATED_RSN_CODE_NUM = 2;

    private static final String DEFAULT_REF_INFO_1 = "AAAAAAAAAA";
    private static final String UPDATED_REF_INFO_1 = "BBBBBBBBBB";

    private static final String DEFAULT_REF_INFO_2 = "AAAAAAAAAA";
    private static final String UPDATED_REF_INFO_2 = "BBBBBBBBBB";

    private static final Integer DEFAULT_SVC_RND_NUM = 1;
    private static final Integer UPDATED_SVC_RND_NUM = 2;

    private static final Integer DEFAULT_PAR_DTL_ID = 1;
    private static final Integer UPDATED_PAR_DTL_ID = 2;

    private static final Integer DEFAULT_CHK_EMP_ID = 1;
    private static final Integer UPDATED_CHK_EMP_ID = 2;

    private static final Integer DEFAULT_TRANS_EMP_NUM = 1;
    private static final Integer UPDATED_TRANS_EMP_NUM = 2;

    private static final Integer DEFAULT_MGR_EMP_NUM = 1;
    private static final Integer UPDATED_MGR_EMP_NUM = 2;

    private static final Integer DEFAULT_MEAL_EMP_NUM = 1;
    private static final Integer UPDATED_MEAL_EMP_NUM = 2;

    private static final Integer DEFAULT_DSC_NUM = 1;
    private static final Integer UPDATED_DSC_NUM = 2;

    private static final Integer DEFAULT_DSC_MI_NUM = 1;
    private static final Integer UPDATED_DSC_MI_NUM = 2;

    private static final Integer DEFAULT_SVC_CHG_NUM = 1;
    private static final Integer UPDATED_SVC_CHG_NUM = 2;

    private static final Integer DEFAULT_TMED_NUM = 1;
    private static final Integer UPDATED_TMED_NUM = 2;

    private static final Integer DEFAULT_MI_NUM = 1;
    private static final Integer UPDATED_MI_NUM = 2;

    private static final String ENTITY_API_URL = "/api/detail-line-bis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DetailLineBIRepository detailLineBIRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDetailLineBIMockMvc;

    private DetailLineBI detailLineBI;

    private DetailLineBI insertedDetailLineBI;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailLineBI createEntity() {
        return new DetailLineBI()
            .guestCheckLineItemId(DEFAULT_GUEST_CHECK_LINE_ITEM_ID)
            .detailUTC(DEFAULT_DETAIL_UTC)
            .detailLcl(DEFAULT_DETAIL_LCL)
            .seatNum(DEFAULT_SEAT_NUM)
            .prcLvl(DEFAULT_PRC_LVL)
            .dspTtl(DEFAULT_DSP_TTL)
            .dspQty(DEFAULT_DSP_QTY)
            .errCorFlag(DEFAULT_ERR_COR_FLAG)
            .vdFlag(DEFAULT_VD_FLAG)
            .returnFlag(DEFAULT_RETURN_FLAG)
            .doNotShowFlag(DEFAULT_DO_NOT_SHOW_FLAG)
            .aggTtl(DEFAULT_AGG_TTL)
            .rsnCodeNum(DEFAULT_RSN_CODE_NUM)
            .refInfo1(DEFAULT_REF_INFO_1)
            .refInfo2(DEFAULT_REF_INFO_2)
            .svcRndNum(DEFAULT_SVC_RND_NUM)
            .parDtlId(DEFAULT_PAR_DTL_ID)
            .chkEmpId(DEFAULT_CHK_EMP_ID)
            .transEmpNum(DEFAULT_TRANS_EMP_NUM)
            .mgrEmpNum(DEFAULT_MGR_EMP_NUM)
            .mealEmpNum(DEFAULT_MEAL_EMP_NUM)
            .dscNum(DEFAULT_DSC_NUM)
            .dscMiNum(DEFAULT_DSC_MI_NUM)
            .svcChgNum(DEFAULT_SVC_CHG_NUM)
            .tmedNum(DEFAULT_TMED_NUM)
            .miNum(DEFAULT_MI_NUM);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailLineBI createUpdatedEntity() {
        return new DetailLineBI()
            .guestCheckLineItemId(UPDATED_GUEST_CHECK_LINE_ITEM_ID)
            .detailUTC(UPDATED_DETAIL_UTC)
            .detailLcl(UPDATED_DETAIL_LCL)
            .seatNum(UPDATED_SEAT_NUM)
            .prcLvl(UPDATED_PRC_LVL)
            .dspTtl(UPDATED_DSP_TTL)
            .dspQty(UPDATED_DSP_QTY)
            .errCorFlag(UPDATED_ERR_COR_FLAG)
            .vdFlag(UPDATED_VD_FLAG)
            .returnFlag(UPDATED_RETURN_FLAG)
            .doNotShowFlag(UPDATED_DO_NOT_SHOW_FLAG)
            .aggTtl(UPDATED_AGG_TTL)
            .rsnCodeNum(UPDATED_RSN_CODE_NUM)
            .refInfo1(UPDATED_REF_INFO_1)
            .refInfo2(UPDATED_REF_INFO_2)
            .svcRndNum(UPDATED_SVC_RND_NUM)
            .parDtlId(UPDATED_PAR_DTL_ID)
            .chkEmpId(UPDATED_CHK_EMP_ID)
            .transEmpNum(UPDATED_TRANS_EMP_NUM)
            .mgrEmpNum(UPDATED_MGR_EMP_NUM)
            .mealEmpNum(UPDATED_MEAL_EMP_NUM)
            .dscNum(UPDATED_DSC_NUM)
            .dscMiNum(UPDATED_DSC_MI_NUM)
            .svcChgNum(UPDATED_SVC_CHG_NUM)
            .tmedNum(UPDATED_TMED_NUM)
            .miNum(UPDATED_MI_NUM);
    }

    @BeforeEach
    void initTest() {
        detailLineBI = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedDetailLineBI != null) {
            detailLineBIRepository.delete(insertedDetailLineBI);
            insertedDetailLineBI = null;
        }
    }

    @Test
    @Transactional
    void createDetailLineBI() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DetailLineBI
        var returnedDetailLineBI = om.readValue(
            restDetailLineBIMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(detailLineBI)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DetailLineBI.class
        );

        // Validate the DetailLineBI in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDetailLineBIUpdatableFieldsEquals(returnedDetailLineBI, getPersistedDetailLineBI(returnedDetailLineBI));

        insertedDetailLineBI = returnedDetailLineBI;
    }

    @Test
    @Transactional
    void createDetailLineBIWithExistingId() throws Exception {
        // Create the DetailLineBI with an existing ID
        detailLineBI.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetailLineBIMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(detailLineBI)))
            .andExpect(status().isBadRequest());

        // Validate the DetailLineBI in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDetailLineBIS() throws Exception {
        // Initialize the database
        insertedDetailLineBI = detailLineBIRepository.saveAndFlush(detailLineBI);

        // Get all the detailLineBIList
        restDetailLineBIMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detailLineBI.getId().intValue())))
            .andExpect(jsonPath("$.[*].guestCheckLineItemId").value(hasItem(DEFAULT_GUEST_CHECK_LINE_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].detailUTC").value(hasItem(DEFAULT_DETAIL_UTC.toString())))
            .andExpect(jsonPath("$.[*].detailLcl").value(hasItem(DEFAULT_DETAIL_LCL.toString())))
            .andExpect(jsonPath("$.[*].seatNum").value(hasItem(DEFAULT_SEAT_NUM)))
            .andExpect(jsonPath("$.[*].prcLvl").value(hasItem(DEFAULT_PRC_LVL)))
            .andExpect(jsonPath("$.[*].dspTtl").value(hasItem(sameNumber(DEFAULT_DSP_TTL))))
            .andExpect(jsonPath("$.[*].dspQty").value(hasItem(DEFAULT_DSP_QTY)))
            .andExpect(jsonPath("$.[*].errCorFlag").value(hasItem(DEFAULT_ERR_COR_FLAG)))
            .andExpect(jsonPath("$.[*].vdFlag").value(hasItem(DEFAULT_VD_FLAG)))
            .andExpect(jsonPath("$.[*].returnFlag").value(hasItem(DEFAULT_RETURN_FLAG)))
            .andExpect(jsonPath("$.[*].doNotShowFlag").value(hasItem(DEFAULT_DO_NOT_SHOW_FLAG)))
            .andExpect(jsonPath("$.[*].aggTtl").value(hasItem(sameNumber(DEFAULT_AGG_TTL))))
            .andExpect(jsonPath("$.[*].rsnCodeNum").value(hasItem(DEFAULT_RSN_CODE_NUM)))
            .andExpect(jsonPath("$.[*].refInfo1").value(hasItem(DEFAULT_REF_INFO_1)))
            .andExpect(jsonPath("$.[*].refInfo2").value(hasItem(DEFAULT_REF_INFO_2)))
            .andExpect(jsonPath("$.[*].svcRndNum").value(hasItem(DEFAULT_SVC_RND_NUM)))
            .andExpect(jsonPath("$.[*].parDtlId").value(hasItem(DEFAULT_PAR_DTL_ID)))
            .andExpect(jsonPath("$.[*].chkEmpId").value(hasItem(DEFAULT_CHK_EMP_ID)))
            .andExpect(jsonPath("$.[*].transEmpNum").value(hasItem(DEFAULT_TRANS_EMP_NUM)))
            .andExpect(jsonPath("$.[*].mgrEmpNum").value(hasItem(DEFAULT_MGR_EMP_NUM)))
            .andExpect(jsonPath("$.[*].mealEmpNum").value(hasItem(DEFAULT_MEAL_EMP_NUM)))
            .andExpect(jsonPath("$.[*].dscNum").value(hasItem(DEFAULT_DSC_NUM)))
            .andExpect(jsonPath("$.[*].dscMiNum").value(hasItem(DEFAULT_DSC_MI_NUM)))
            .andExpect(jsonPath("$.[*].svcChgNum").value(hasItem(DEFAULT_SVC_CHG_NUM)))
            .andExpect(jsonPath("$.[*].tmedNum").value(hasItem(DEFAULT_TMED_NUM)))
            .andExpect(jsonPath("$.[*].miNum").value(hasItem(DEFAULT_MI_NUM)));
    }

    @Test
    @Transactional
    void getDetailLineBI() throws Exception {
        // Initialize the database
        insertedDetailLineBI = detailLineBIRepository.saveAndFlush(detailLineBI);

        // Get the detailLineBI
        restDetailLineBIMockMvc
            .perform(get(ENTITY_API_URL_ID, detailLineBI.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(detailLineBI.getId().intValue()))
            .andExpect(jsonPath("$.guestCheckLineItemId").value(DEFAULT_GUEST_CHECK_LINE_ITEM_ID.intValue()))
            .andExpect(jsonPath("$.detailUTC").value(DEFAULT_DETAIL_UTC.toString()))
            .andExpect(jsonPath("$.detailLcl").value(DEFAULT_DETAIL_LCL.toString()))
            .andExpect(jsonPath("$.seatNum").value(DEFAULT_SEAT_NUM))
            .andExpect(jsonPath("$.prcLvl").value(DEFAULT_PRC_LVL))
            .andExpect(jsonPath("$.dspTtl").value(sameNumber(DEFAULT_DSP_TTL)))
            .andExpect(jsonPath("$.dspQty").value(DEFAULT_DSP_QTY))
            .andExpect(jsonPath("$.errCorFlag").value(DEFAULT_ERR_COR_FLAG))
            .andExpect(jsonPath("$.vdFlag").value(DEFAULT_VD_FLAG))
            .andExpect(jsonPath("$.returnFlag").value(DEFAULT_RETURN_FLAG))
            .andExpect(jsonPath("$.doNotShowFlag").value(DEFAULT_DO_NOT_SHOW_FLAG))
            .andExpect(jsonPath("$.aggTtl").value(sameNumber(DEFAULT_AGG_TTL)))
            .andExpect(jsonPath("$.rsnCodeNum").value(DEFAULT_RSN_CODE_NUM))
            .andExpect(jsonPath("$.refInfo1").value(DEFAULT_REF_INFO_1))
            .andExpect(jsonPath("$.refInfo2").value(DEFAULT_REF_INFO_2))
            .andExpect(jsonPath("$.svcRndNum").value(DEFAULT_SVC_RND_NUM))
            .andExpect(jsonPath("$.parDtlId").value(DEFAULT_PAR_DTL_ID))
            .andExpect(jsonPath("$.chkEmpId").value(DEFAULT_CHK_EMP_ID))
            .andExpect(jsonPath("$.transEmpNum").value(DEFAULT_TRANS_EMP_NUM))
            .andExpect(jsonPath("$.mgrEmpNum").value(DEFAULT_MGR_EMP_NUM))
            .andExpect(jsonPath("$.mealEmpNum").value(DEFAULT_MEAL_EMP_NUM))
            .andExpect(jsonPath("$.dscNum").value(DEFAULT_DSC_NUM))
            .andExpect(jsonPath("$.dscMiNum").value(DEFAULT_DSC_MI_NUM))
            .andExpect(jsonPath("$.svcChgNum").value(DEFAULT_SVC_CHG_NUM))
            .andExpect(jsonPath("$.tmedNum").value(DEFAULT_TMED_NUM))
            .andExpect(jsonPath("$.miNum").value(DEFAULT_MI_NUM));
    }

    @Test
    @Transactional
    void getNonExistingDetailLineBI() throws Exception {
        // Get the detailLineBI
        restDetailLineBIMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDetailLineBI() throws Exception {
        // Initialize the database
        insertedDetailLineBI = detailLineBIRepository.saveAndFlush(detailLineBI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the detailLineBI
        DetailLineBI updatedDetailLineBI = detailLineBIRepository.findById(detailLineBI.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDetailLineBI are not directly saved in db
        em.detach(updatedDetailLineBI);
        updatedDetailLineBI
            .guestCheckLineItemId(UPDATED_GUEST_CHECK_LINE_ITEM_ID)
            .detailUTC(UPDATED_DETAIL_UTC)
            .detailLcl(UPDATED_DETAIL_LCL)
            .seatNum(UPDATED_SEAT_NUM)
            .prcLvl(UPDATED_PRC_LVL)
            .dspTtl(UPDATED_DSP_TTL)
            .dspQty(UPDATED_DSP_QTY)
            .errCorFlag(UPDATED_ERR_COR_FLAG)
            .vdFlag(UPDATED_VD_FLAG)
            .returnFlag(UPDATED_RETURN_FLAG)
            .doNotShowFlag(UPDATED_DO_NOT_SHOW_FLAG)
            .aggTtl(UPDATED_AGG_TTL)
            .rsnCodeNum(UPDATED_RSN_CODE_NUM)
            .refInfo1(UPDATED_REF_INFO_1)
            .refInfo2(UPDATED_REF_INFO_2)
            .svcRndNum(UPDATED_SVC_RND_NUM)
            .parDtlId(UPDATED_PAR_DTL_ID)
            .chkEmpId(UPDATED_CHK_EMP_ID)
            .transEmpNum(UPDATED_TRANS_EMP_NUM)
            .mgrEmpNum(UPDATED_MGR_EMP_NUM)
            .mealEmpNum(UPDATED_MEAL_EMP_NUM)
            .dscNum(UPDATED_DSC_NUM)
            .dscMiNum(UPDATED_DSC_MI_NUM)
            .svcChgNum(UPDATED_SVC_CHG_NUM)
            .tmedNum(UPDATED_TMED_NUM)
            .miNum(UPDATED_MI_NUM);

        restDetailLineBIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDetailLineBI.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDetailLineBI))
            )
            .andExpect(status().isOk());

        // Validate the DetailLineBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDetailLineBIToMatchAllProperties(updatedDetailLineBI);
    }

    @Test
    @Transactional
    void putNonExistingDetailLineBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        detailLineBI.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetailLineBIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, detailLineBI.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(detailLineBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailLineBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDetailLineBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        detailLineBI.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailLineBIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(detailLineBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailLineBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDetailLineBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        detailLineBI.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailLineBIMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(detailLineBI)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetailLineBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDetailLineBIWithPatch() throws Exception {
        // Initialize the database
        insertedDetailLineBI = detailLineBIRepository.saveAndFlush(detailLineBI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the detailLineBI using partial update
        DetailLineBI partialUpdatedDetailLineBI = new DetailLineBI();
        partialUpdatedDetailLineBI.setId(detailLineBI.getId());

        partialUpdatedDetailLineBI
            .guestCheckLineItemId(UPDATED_GUEST_CHECK_LINE_ITEM_ID)
            .detailLcl(UPDATED_DETAIL_LCL)
            .prcLvl(UPDATED_PRC_LVL)
            .dspTtl(UPDATED_DSP_TTL)
            .vdFlag(UPDATED_VD_FLAG)
            .doNotShowFlag(UPDATED_DO_NOT_SHOW_FLAG)
            .rsnCodeNum(UPDATED_RSN_CODE_NUM)
            .refInfo1(UPDATED_REF_INFO_1)
            .parDtlId(UPDATED_PAR_DTL_ID)
            .chkEmpId(UPDATED_CHK_EMP_ID)
            .mealEmpNum(UPDATED_MEAL_EMP_NUM)
            .miNum(UPDATED_MI_NUM);

        restDetailLineBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetailLineBI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDetailLineBI))
            )
            .andExpect(status().isOk());

        // Validate the DetailLineBI in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDetailLineBIUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDetailLineBI, detailLineBI),
            getPersistedDetailLineBI(detailLineBI)
        );
    }

    @Test
    @Transactional
    void fullUpdateDetailLineBIWithPatch() throws Exception {
        // Initialize the database
        insertedDetailLineBI = detailLineBIRepository.saveAndFlush(detailLineBI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the detailLineBI using partial update
        DetailLineBI partialUpdatedDetailLineBI = new DetailLineBI();
        partialUpdatedDetailLineBI.setId(detailLineBI.getId());

        partialUpdatedDetailLineBI
            .guestCheckLineItemId(UPDATED_GUEST_CHECK_LINE_ITEM_ID)
            .detailUTC(UPDATED_DETAIL_UTC)
            .detailLcl(UPDATED_DETAIL_LCL)
            .seatNum(UPDATED_SEAT_NUM)
            .prcLvl(UPDATED_PRC_LVL)
            .dspTtl(UPDATED_DSP_TTL)
            .dspQty(UPDATED_DSP_QTY)
            .errCorFlag(UPDATED_ERR_COR_FLAG)
            .vdFlag(UPDATED_VD_FLAG)
            .returnFlag(UPDATED_RETURN_FLAG)
            .doNotShowFlag(UPDATED_DO_NOT_SHOW_FLAG)
            .aggTtl(UPDATED_AGG_TTL)
            .rsnCodeNum(UPDATED_RSN_CODE_NUM)
            .refInfo1(UPDATED_REF_INFO_1)
            .refInfo2(UPDATED_REF_INFO_2)
            .svcRndNum(UPDATED_SVC_RND_NUM)
            .parDtlId(UPDATED_PAR_DTL_ID)
            .chkEmpId(UPDATED_CHK_EMP_ID)
            .transEmpNum(UPDATED_TRANS_EMP_NUM)
            .mgrEmpNum(UPDATED_MGR_EMP_NUM)
            .mealEmpNum(UPDATED_MEAL_EMP_NUM)
            .dscNum(UPDATED_DSC_NUM)
            .dscMiNum(UPDATED_DSC_MI_NUM)
            .svcChgNum(UPDATED_SVC_CHG_NUM)
            .tmedNum(UPDATED_TMED_NUM)
            .miNum(UPDATED_MI_NUM);

        restDetailLineBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetailLineBI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDetailLineBI))
            )
            .andExpect(status().isOk());

        // Validate the DetailLineBI in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDetailLineBIUpdatableFieldsEquals(partialUpdatedDetailLineBI, getPersistedDetailLineBI(partialUpdatedDetailLineBI));
    }

    @Test
    @Transactional
    void patchNonExistingDetailLineBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        detailLineBI.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetailLineBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, detailLineBI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(detailLineBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailLineBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDetailLineBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        detailLineBI.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailLineBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(detailLineBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailLineBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDetailLineBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        detailLineBI.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailLineBIMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(detailLineBI)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetailLineBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDetailLineBI() throws Exception {
        // Initialize the database
        insertedDetailLineBI = detailLineBIRepository.saveAndFlush(detailLineBI);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the detailLineBI
        restDetailLineBIMockMvc
            .perform(delete(ENTITY_API_URL_ID, detailLineBI.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return detailLineBIRepository.count();
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

    protected DetailLineBI getPersistedDetailLineBI(DetailLineBI detailLineBI) {
        return detailLineBIRepository.findById(detailLineBI.getId()).orElseThrow();
    }

    protected void assertPersistedDetailLineBIToMatchAllProperties(DetailLineBI expectedDetailLineBI) {
        assertDetailLineBIAllPropertiesEquals(expectedDetailLineBI, getPersistedDetailLineBI(expectedDetailLineBI));
    }

    protected void assertPersistedDetailLineBIToMatchUpdatableProperties(DetailLineBI expectedDetailLineBI) {
        assertDetailLineBIAllUpdatablePropertiesEquals(expectedDetailLineBI, getPersistedDetailLineBI(expectedDetailLineBI));
    }
}
