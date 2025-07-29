package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.BarCodeTransAsserts.*;
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
import mc.sbm.simphonycloud.domain.BarCodeTrans;
import mc.sbm.simphonycloud.repository.BarCodeTransRepository;
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
 * Integration tests for the {@link BarCodeTransResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BarCodeTransResourceIT {

    private static final String DEFAULT_LOC_REF = "AAAAAAAAAA";
    private static final String UPDATED_LOC_REF = "BBBBBBBBBB";

    private static final Integer DEFAULT_RVC_REF = 1;
    private static final Integer UPDATED_RVC_REF = 2;

    private static final Integer DEFAULT_BARCODE_ID = 1;
    private static final Integer UPDATED_BARCODE_ID = 2;

    private static final String DEFAULT_BARCODE = "AAAAAAAAAA";
    private static final String UPDATED_BARCODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_MENU_ITEM_ID = 1;
    private static final Integer UPDATED_MENU_ITEM_ID = 2;

    private static final Integer DEFAULT_DEFENITION_SEQUENCE = 1;
    private static final Integer UPDATED_DEFENITION_SEQUENCE = 2;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final Integer DEFAULT_PRICE_SEQUENCE = 1;
    private static final Integer UPDATED_PRICE_SEQUENCE = 2;

    private static final Double DEFAULT_PREPARATION_COST = 1D;
    private static final Double UPDATED_PREPARATION_COST = 2D;

    private static final String ENTITY_API_URL = "/api/bar-code-trans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BarCodeTransRepository barCodeTransRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBarCodeTransMockMvc;

    private BarCodeTrans barCodeTrans;

    private BarCodeTrans insertedBarCodeTrans;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BarCodeTrans createEntity() {
        return new BarCodeTrans()
            .locRef(DEFAULT_LOC_REF)
            .rvcRef(DEFAULT_RVC_REF)
            .barcodeId(DEFAULT_BARCODE_ID)
            .barcode(DEFAULT_BARCODE)
            .menuItemId(DEFAULT_MENU_ITEM_ID)
            .defenitionSequence(DEFAULT_DEFENITION_SEQUENCE)
            .price(DEFAULT_PRICE)
            .priceSequence(DEFAULT_PRICE_SEQUENCE)
            .preparationCost(DEFAULT_PREPARATION_COST);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BarCodeTrans createUpdatedEntity() {
        return new BarCodeTrans()
            .locRef(UPDATED_LOC_REF)
            .rvcRef(UPDATED_RVC_REF)
            .barcodeId(UPDATED_BARCODE_ID)
            .barcode(UPDATED_BARCODE)
            .menuItemId(UPDATED_MENU_ITEM_ID)
            .defenitionSequence(UPDATED_DEFENITION_SEQUENCE)
            .price(UPDATED_PRICE)
            .priceSequence(UPDATED_PRICE_SEQUENCE)
            .preparationCost(UPDATED_PREPARATION_COST);
    }

    @BeforeEach
    void initTest() {
        barCodeTrans = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedBarCodeTrans != null) {
            barCodeTransRepository.delete(insertedBarCodeTrans);
            insertedBarCodeTrans = null;
        }
    }

    @Test
    @Transactional
    void createBarCodeTrans() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the BarCodeTrans
        var returnedBarCodeTrans = om.readValue(
            restBarCodeTransMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(barCodeTrans)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BarCodeTrans.class
        );

        // Validate the BarCodeTrans in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBarCodeTransUpdatableFieldsEquals(returnedBarCodeTrans, getPersistedBarCodeTrans(returnedBarCodeTrans));

        insertedBarCodeTrans = returnedBarCodeTrans;
    }

    @Test
    @Transactional
    void createBarCodeTransWithExistingId() throws Exception {
        // Create the BarCodeTrans with an existing ID
        barCodeTrans.setId(1);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBarCodeTransMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(barCodeTrans)))
            .andExpect(status().isBadRequest());

        // Validate the BarCodeTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBarCodeTrans() throws Exception {
        // Initialize the database
        insertedBarCodeTrans = barCodeTransRepository.saveAndFlush(barCodeTrans);

        // Get all the barCodeTransList
        restBarCodeTransMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(barCodeTrans.getId().intValue())))
            .andExpect(jsonPath("$.[*].locRef").value(hasItem(DEFAULT_LOC_REF)))
            .andExpect(jsonPath("$.[*].rvcRef").value(hasItem(DEFAULT_RVC_REF)))
            .andExpect(jsonPath("$.[*].barcodeId").value(hasItem(DEFAULT_BARCODE_ID)))
            .andExpect(jsonPath("$.[*].barcode").value(hasItem(DEFAULT_BARCODE)))
            .andExpect(jsonPath("$.[*].menuItemId").value(hasItem(DEFAULT_MENU_ITEM_ID)))
            .andExpect(jsonPath("$.[*].defenitionSequence").value(hasItem(DEFAULT_DEFENITION_SEQUENCE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].priceSequence").value(hasItem(DEFAULT_PRICE_SEQUENCE)))
            .andExpect(jsonPath("$.[*].preparationCost").value(hasItem(DEFAULT_PREPARATION_COST)));
    }

    @Test
    @Transactional
    void getBarCodeTrans() throws Exception {
        // Initialize the database
        insertedBarCodeTrans = barCodeTransRepository.saveAndFlush(barCodeTrans);

        // Get the barCodeTrans
        restBarCodeTransMockMvc
            .perform(get(ENTITY_API_URL_ID, barCodeTrans.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(barCodeTrans.getId().intValue()))
            .andExpect(jsonPath("$.locRef").value(DEFAULT_LOC_REF))
            .andExpect(jsonPath("$.rvcRef").value(DEFAULT_RVC_REF))
            .andExpect(jsonPath("$.barcodeId").value(DEFAULT_BARCODE_ID))
            .andExpect(jsonPath("$.barcode").value(DEFAULT_BARCODE))
            .andExpect(jsonPath("$.menuItemId").value(DEFAULT_MENU_ITEM_ID))
            .andExpect(jsonPath("$.defenitionSequence").value(DEFAULT_DEFENITION_SEQUENCE))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.priceSequence").value(DEFAULT_PRICE_SEQUENCE))
            .andExpect(jsonPath("$.preparationCost").value(DEFAULT_PREPARATION_COST));
    }

    @Test
    @Transactional
    void getNonExistingBarCodeTrans() throws Exception {
        // Get the barCodeTrans
        restBarCodeTransMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBarCodeTrans() throws Exception {
        // Initialize the database
        insertedBarCodeTrans = barCodeTransRepository.saveAndFlush(barCodeTrans);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the barCodeTrans
        BarCodeTrans updatedBarCodeTrans = barCodeTransRepository.findById(barCodeTrans.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBarCodeTrans are not directly saved in db
        em.detach(updatedBarCodeTrans);
        updatedBarCodeTrans
            .locRef(UPDATED_LOC_REF)
            .rvcRef(UPDATED_RVC_REF)
            .barcodeId(UPDATED_BARCODE_ID)
            .barcode(UPDATED_BARCODE)
            .menuItemId(UPDATED_MENU_ITEM_ID)
            .defenitionSequence(UPDATED_DEFENITION_SEQUENCE)
            .price(UPDATED_PRICE)
            .priceSequence(UPDATED_PRICE_SEQUENCE)
            .preparationCost(UPDATED_PREPARATION_COST);

        restBarCodeTransMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBarCodeTrans.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBarCodeTrans))
            )
            .andExpect(status().isOk());

        // Validate the BarCodeTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBarCodeTransToMatchAllProperties(updatedBarCodeTrans);
    }

    @Test
    @Transactional
    void putNonExistingBarCodeTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        barCodeTrans.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBarCodeTransMockMvc
            .perform(
                put(ENTITY_API_URL_ID, barCodeTrans.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(barCodeTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the BarCodeTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBarCodeTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        barCodeTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBarCodeTransMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(barCodeTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the BarCodeTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBarCodeTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        barCodeTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBarCodeTransMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(barCodeTrans)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BarCodeTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBarCodeTransWithPatch() throws Exception {
        // Initialize the database
        insertedBarCodeTrans = barCodeTransRepository.saveAndFlush(barCodeTrans);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the barCodeTrans using partial update
        BarCodeTrans partialUpdatedBarCodeTrans = new BarCodeTrans();
        partialUpdatedBarCodeTrans.setId(barCodeTrans.getId());

        partialUpdatedBarCodeTrans
            .barcodeId(UPDATED_BARCODE_ID)
            .barcode(UPDATED_BARCODE)
            .defenitionSequence(UPDATED_DEFENITION_SEQUENCE)
            .preparationCost(UPDATED_PREPARATION_COST);

        restBarCodeTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBarCodeTrans.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBarCodeTrans))
            )
            .andExpect(status().isOk());

        // Validate the BarCodeTrans in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBarCodeTransUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBarCodeTrans, barCodeTrans),
            getPersistedBarCodeTrans(barCodeTrans)
        );
    }

    @Test
    @Transactional
    void fullUpdateBarCodeTransWithPatch() throws Exception {
        // Initialize the database
        insertedBarCodeTrans = barCodeTransRepository.saveAndFlush(barCodeTrans);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the barCodeTrans using partial update
        BarCodeTrans partialUpdatedBarCodeTrans = new BarCodeTrans();
        partialUpdatedBarCodeTrans.setId(barCodeTrans.getId());

        partialUpdatedBarCodeTrans
            .locRef(UPDATED_LOC_REF)
            .rvcRef(UPDATED_RVC_REF)
            .barcodeId(UPDATED_BARCODE_ID)
            .barcode(UPDATED_BARCODE)
            .menuItemId(UPDATED_MENU_ITEM_ID)
            .defenitionSequence(UPDATED_DEFENITION_SEQUENCE)
            .price(UPDATED_PRICE)
            .priceSequence(UPDATED_PRICE_SEQUENCE)
            .preparationCost(UPDATED_PREPARATION_COST);

        restBarCodeTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBarCodeTrans.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBarCodeTrans))
            )
            .andExpect(status().isOk());

        // Validate the BarCodeTrans in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBarCodeTransUpdatableFieldsEquals(partialUpdatedBarCodeTrans, getPersistedBarCodeTrans(partialUpdatedBarCodeTrans));
    }

    @Test
    @Transactional
    void patchNonExistingBarCodeTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        barCodeTrans.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBarCodeTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, barCodeTrans.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(barCodeTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the BarCodeTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBarCodeTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        barCodeTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBarCodeTransMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(barCodeTrans))
            )
            .andExpect(status().isBadRequest());

        // Validate the BarCodeTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBarCodeTrans() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        barCodeTrans.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBarCodeTransMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(barCodeTrans)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BarCodeTrans in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBarCodeTrans() throws Exception {
        // Initialize the database
        insertedBarCodeTrans = barCodeTransRepository.saveAndFlush(barCodeTrans);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the barCodeTrans
        restBarCodeTransMockMvc
            .perform(delete(ENTITY_API_URL_ID, barCodeTrans.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return barCodeTransRepository.count();
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

    protected BarCodeTrans getPersistedBarCodeTrans(BarCodeTrans barCodeTrans) {
        return barCodeTransRepository.findById(barCodeTrans.getId()).orElseThrow();
    }

    protected void assertPersistedBarCodeTransToMatchAllProperties(BarCodeTrans expectedBarCodeTrans) {
        assertBarCodeTransAllPropertiesEquals(expectedBarCodeTrans, getPersistedBarCodeTrans(expectedBarCodeTrans));
    }

    protected void assertPersistedBarCodeTransToMatchUpdatableProperties(BarCodeTrans expectedBarCodeTrans) {
        assertBarCodeTransAllUpdatablePropertiesEquals(expectedBarCodeTrans, getPersistedBarCodeTrans(expectedBarCodeTrans));
    }
}
