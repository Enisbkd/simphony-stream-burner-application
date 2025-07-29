package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.CodeRaisonBIAsserts.*;
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
import mc.sbm.simphonycloud.domain.CodeRaisonBI;
import mc.sbm.simphonycloud.repository.CodeRaisonBIRepository;
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
 * Integration tests for the {@link CodeRaisonBIResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CodeRaisonBIResourceIT {

    private static final Integer DEFAULT_NOM_COURT = 1;
    private static final Integer UPDATED_NOM_COURT = 2;

    private static final String DEFAULT_NOM_MSTR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_MSTR = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUM_MSTR = 1;
    private static final Integer UPDATED_NUM_MSTR = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ETABLISSEMENT_REF = "AAAAAAAAAA";
    private static final String UPDATED_ETABLISSEMENT_REF = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/code-raison-bis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CodeRaisonBIRepository codeRaisonBIRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCodeRaisonBIMockMvc;

    private CodeRaisonBI codeRaisonBI;

    private CodeRaisonBI insertedCodeRaisonBI;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CodeRaisonBI createEntity() {
        return new CodeRaisonBI()
            .nomCourt(DEFAULT_NOM_COURT)
            .nomMstr(DEFAULT_NOM_MSTR)
            .numMstr(DEFAULT_NUM_MSTR)
            .name(DEFAULT_NAME)
            .etablissementRef(DEFAULT_ETABLISSEMENT_REF);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CodeRaisonBI createUpdatedEntity() {
        return new CodeRaisonBI()
            .nomCourt(UPDATED_NOM_COURT)
            .nomMstr(UPDATED_NOM_MSTR)
            .numMstr(UPDATED_NUM_MSTR)
            .name(UPDATED_NAME)
            .etablissementRef(UPDATED_ETABLISSEMENT_REF);
    }

    @BeforeEach
    void initTest() {
        codeRaisonBI = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedCodeRaisonBI != null) {
            codeRaisonBIRepository.delete(insertedCodeRaisonBI);
            insertedCodeRaisonBI = null;
        }
    }

    @Test
    @Transactional
    void createCodeRaisonBI() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CodeRaisonBI
        var returnedCodeRaisonBI = om.readValue(
            restCodeRaisonBIMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(codeRaisonBI)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CodeRaisonBI.class
        );

        // Validate the CodeRaisonBI in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCodeRaisonBIUpdatableFieldsEquals(returnedCodeRaisonBI, getPersistedCodeRaisonBI(returnedCodeRaisonBI));

        insertedCodeRaisonBI = returnedCodeRaisonBI;
    }

    @Test
    @Transactional
    void createCodeRaisonBIWithExistingId() throws Exception {
        // Create the CodeRaisonBI with an existing ID
        codeRaisonBI.setId(1);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCodeRaisonBIMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(codeRaisonBI)))
            .andExpect(status().isBadRequest());

        // Validate the CodeRaisonBI in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomCourtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        codeRaisonBI.setNomCourt(null);

        // Create the CodeRaisonBI, which fails.

        restCodeRaisonBIMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(codeRaisonBI)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEtablissementRefIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        codeRaisonBI.setEtablissementRef(null);

        // Create the CodeRaisonBI, which fails.

        restCodeRaisonBIMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(codeRaisonBI)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCodeRaisonBIS() throws Exception {
        // Initialize the database
        insertedCodeRaisonBI = codeRaisonBIRepository.saveAndFlush(codeRaisonBI);

        // Get all the codeRaisonBIList
        restCodeRaisonBIMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(codeRaisonBI.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomCourt").value(hasItem(DEFAULT_NOM_COURT)))
            .andExpect(jsonPath("$.[*].nomMstr").value(hasItem(DEFAULT_NOM_MSTR)))
            .andExpect(jsonPath("$.[*].numMstr").value(hasItem(DEFAULT_NUM_MSTR)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].etablissementRef").value(hasItem(DEFAULT_ETABLISSEMENT_REF)));
    }

    @Test
    @Transactional
    void getCodeRaisonBI() throws Exception {
        // Initialize the database
        insertedCodeRaisonBI = codeRaisonBIRepository.saveAndFlush(codeRaisonBI);

        // Get the codeRaisonBI
        restCodeRaisonBIMockMvc
            .perform(get(ENTITY_API_URL_ID, codeRaisonBI.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(codeRaisonBI.getId().intValue()))
            .andExpect(jsonPath("$.nomCourt").value(DEFAULT_NOM_COURT))
            .andExpect(jsonPath("$.nomMstr").value(DEFAULT_NOM_MSTR))
            .andExpect(jsonPath("$.numMstr").value(DEFAULT_NUM_MSTR))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.etablissementRef").value(DEFAULT_ETABLISSEMENT_REF));
    }

    @Test
    @Transactional
    void getNonExistingCodeRaisonBI() throws Exception {
        // Get the codeRaisonBI
        restCodeRaisonBIMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCodeRaisonBI() throws Exception {
        // Initialize the database
        insertedCodeRaisonBI = codeRaisonBIRepository.saveAndFlush(codeRaisonBI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the codeRaisonBI
        CodeRaisonBI updatedCodeRaisonBI = codeRaisonBIRepository.findById(codeRaisonBI.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCodeRaisonBI are not directly saved in db
        em.detach(updatedCodeRaisonBI);
        updatedCodeRaisonBI
            .nomCourt(UPDATED_NOM_COURT)
            .nomMstr(UPDATED_NOM_MSTR)
            .numMstr(UPDATED_NUM_MSTR)
            .name(UPDATED_NAME)
            .etablissementRef(UPDATED_ETABLISSEMENT_REF);

        restCodeRaisonBIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCodeRaisonBI.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCodeRaisonBI))
            )
            .andExpect(status().isOk());

        // Validate the CodeRaisonBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCodeRaisonBIToMatchAllProperties(updatedCodeRaisonBI);
    }

    @Test
    @Transactional
    void putNonExistingCodeRaisonBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        codeRaisonBI.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCodeRaisonBIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, codeRaisonBI.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(codeRaisonBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the CodeRaisonBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCodeRaisonBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        codeRaisonBI.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodeRaisonBIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(codeRaisonBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the CodeRaisonBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCodeRaisonBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        codeRaisonBI.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodeRaisonBIMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(codeRaisonBI)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CodeRaisonBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCodeRaisonBIWithPatch() throws Exception {
        // Initialize the database
        insertedCodeRaisonBI = codeRaisonBIRepository.saveAndFlush(codeRaisonBI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the codeRaisonBI using partial update
        CodeRaisonBI partialUpdatedCodeRaisonBI = new CodeRaisonBI();
        partialUpdatedCodeRaisonBI.setId(codeRaisonBI.getId());

        partialUpdatedCodeRaisonBI.numMstr(UPDATED_NUM_MSTR).etablissementRef(UPDATED_ETABLISSEMENT_REF);

        restCodeRaisonBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCodeRaisonBI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCodeRaisonBI))
            )
            .andExpect(status().isOk());

        // Validate the CodeRaisonBI in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCodeRaisonBIUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCodeRaisonBI, codeRaisonBI),
            getPersistedCodeRaisonBI(codeRaisonBI)
        );
    }

    @Test
    @Transactional
    void fullUpdateCodeRaisonBIWithPatch() throws Exception {
        // Initialize the database
        insertedCodeRaisonBI = codeRaisonBIRepository.saveAndFlush(codeRaisonBI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the codeRaisonBI using partial update
        CodeRaisonBI partialUpdatedCodeRaisonBI = new CodeRaisonBI();
        partialUpdatedCodeRaisonBI.setId(codeRaisonBI.getId());

        partialUpdatedCodeRaisonBI
            .nomCourt(UPDATED_NOM_COURT)
            .nomMstr(UPDATED_NOM_MSTR)
            .numMstr(UPDATED_NUM_MSTR)
            .name(UPDATED_NAME)
            .etablissementRef(UPDATED_ETABLISSEMENT_REF);

        restCodeRaisonBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCodeRaisonBI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCodeRaisonBI))
            )
            .andExpect(status().isOk());

        // Validate the CodeRaisonBI in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCodeRaisonBIUpdatableFieldsEquals(partialUpdatedCodeRaisonBI, getPersistedCodeRaisonBI(partialUpdatedCodeRaisonBI));
    }

    @Test
    @Transactional
    void patchNonExistingCodeRaisonBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        codeRaisonBI.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCodeRaisonBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, codeRaisonBI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(codeRaisonBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the CodeRaisonBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCodeRaisonBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        codeRaisonBI.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodeRaisonBIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(codeRaisonBI))
            )
            .andExpect(status().isBadRequest());

        // Validate the CodeRaisonBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCodeRaisonBI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        codeRaisonBI.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodeRaisonBIMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(codeRaisonBI)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CodeRaisonBI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCodeRaisonBI() throws Exception {
        // Initialize the database
        insertedCodeRaisonBI = codeRaisonBIRepository.saveAndFlush(codeRaisonBI);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the codeRaisonBI
        restCodeRaisonBIMockMvc
            .perform(delete(ENTITY_API_URL_ID, codeRaisonBI.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return codeRaisonBIRepository.count();
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

    protected CodeRaisonBI getPersistedCodeRaisonBI(CodeRaisonBI codeRaisonBI) {
        return codeRaisonBIRepository.findById(codeRaisonBI.getId()).orElseThrow();
    }

    protected void assertPersistedCodeRaisonBIToMatchAllProperties(CodeRaisonBI expectedCodeRaisonBI) {
        assertCodeRaisonBIAllPropertiesEquals(expectedCodeRaisonBI, getPersistedCodeRaisonBI(expectedCodeRaisonBI));
    }

    protected void assertPersistedCodeRaisonBIToMatchUpdatableProperties(CodeRaisonBI expectedCodeRaisonBI) {
        assertCodeRaisonBIAllUpdatablePropertiesEquals(expectedCodeRaisonBI, getPersistedCodeRaisonBI(expectedCodeRaisonBI));
    }
}
