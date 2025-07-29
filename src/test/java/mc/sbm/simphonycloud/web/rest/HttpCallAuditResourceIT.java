package mc.sbm.simphonycloud.web.rest;

import static mc.sbm.simphonycloud.domain.HttpCallAuditAsserts.*;
import static mc.sbm.simphonycloud.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import mc.sbm.simphonycloud.IntegrationTest;
import mc.sbm.simphonycloud.domain.HttpCallAudit;
import mc.sbm.simphonycloud.repository.HttpCallAuditRepository;
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
 * Integration tests for the {@link HttpCallAuditResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HttpCallAuditResourceIT {

    private static final String DEFAULT_CORRELATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_CORRELATION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_METHOD = "BBBBBBBBBB";

    private static final String DEFAULT_BASE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_BASE_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_ENDPOINT = "AAAAAAAAAA";
    private static final String UPDATED_ENDPOINT = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_URL = "AAAAAAAAAA";
    private static final String UPDATED_FULL_URL = "BBBBBBBBBB";

    private static final String DEFAULT_PATH_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_PATH_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_QUERY_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_QUERY_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_REQUEST_HEADERS = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_HEADERS = "BBBBBBBBBB";

    private static final String DEFAULT_REQUEST_BODY = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_BODY = "BBBBBBBBBB";

    private static final Integer DEFAULT_RESPONSE_STATUS_CODE = 1;
    private static final Integer UPDATED_RESPONSE_STATUS_CODE = 2;

    private static final String DEFAULT_RESPONSE_STATUS_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE_STATUS_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSE_HEADERS = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE_HEADERS = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSE_BODY = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE_BODY = "BBBBBBBBBB";

    private static final Instant DEFAULT_TIMESTAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIMESTAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_DURATION_MS = 1L;
    private static final Long UPDATED_DURATION_MS = 2L;

    private static final String DEFAULT_ERROR_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_ERROR_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_ERROR_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ERROR_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ENVIRONMENT = "AAAAAAAAAA";
    private static final String UPDATED_ENVIRONMENT = "BBBBBBBBBB";

    private static final String DEFAULT_USER_AGENT = "AAAAAAAAAA";
    private static final String UPDATED_USER_AGENT = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_IP = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_IP = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SUCCESS = false;
    private static final Boolean UPDATED_SUCCESS = true;

    private static final Integer DEFAULT_RETRY_COUNT = 1;
    private static final Integer UPDATED_RETRY_COUNT = 2;

    private static final String DEFAULT_KAFKA_TOPIC = "AAAAAAAAAA";
    private static final String UPDATED_KAFKA_TOPIC = "BBBBBBBBBB";

    private static final String DEFAULT_SESSION_ID = "AAAAAAAAAA";
    private static final String UPDATED_SESSION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/http-call-audits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HttpCallAuditRepository httpCallAuditRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHttpCallAuditMockMvc;

    private HttpCallAudit httpCallAudit;

    private HttpCallAudit insertedHttpCallAudit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HttpCallAudit createEntity() {
        return new HttpCallAudit()
            .correlationId(DEFAULT_CORRELATION_ID)
            .method(DEFAULT_METHOD)
            .basePath(DEFAULT_BASE_PATH)
            .endpoint(DEFAULT_ENDPOINT)
            .fullUrl(DEFAULT_FULL_URL)
            .pathParams(DEFAULT_PATH_PARAMS)
            .queryParams(DEFAULT_QUERY_PARAMS)
            .requestHeaders(DEFAULT_REQUEST_HEADERS)
            .requestBody(DEFAULT_REQUEST_BODY)
            .responseStatusCode(DEFAULT_RESPONSE_STATUS_CODE)
            .responseStatusText(DEFAULT_RESPONSE_STATUS_TEXT)
            .responseHeaders(DEFAULT_RESPONSE_HEADERS)
            .responseBody(DEFAULT_RESPONSE_BODY)
            .timestamp(DEFAULT_TIMESTAMP)
            .durationMs(DEFAULT_DURATION_MS)
            .errorMessage(DEFAULT_ERROR_MESSAGE)
            .errorType(DEFAULT_ERROR_TYPE)
            .serviceName(DEFAULT_SERVICE_NAME)
            .environment(DEFAULT_ENVIRONMENT)
            .userAgent(DEFAULT_USER_AGENT)
            .clientIp(DEFAULT_CLIENT_IP)
            .success(DEFAULT_SUCCESS)
            .retryCount(DEFAULT_RETRY_COUNT)
            .kafkaTopic(DEFAULT_KAFKA_TOPIC)
            .sessionId(DEFAULT_SESSION_ID)
            .userId(DEFAULT_USER_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HttpCallAudit createUpdatedEntity() {
        return new HttpCallAudit()
            .correlationId(UPDATED_CORRELATION_ID)
            .method(UPDATED_METHOD)
            .basePath(UPDATED_BASE_PATH)
            .endpoint(UPDATED_ENDPOINT)
            .fullUrl(UPDATED_FULL_URL)
            .pathParams(UPDATED_PATH_PARAMS)
            .queryParams(UPDATED_QUERY_PARAMS)
            .requestHeaders(UPDATED_REQUEST_HEADERS)
            .requestBody(UPDATED_REQUEST_BODY)
            .responseStatusCode(UPDATED_RESPONSE_STATUS_CODE)
            .responseStatusText(UPDATED_RESPONSE_STATUS_TEXT)
            .responseHeaders(UPDATED_RESPONSE_HEADERS)
            .responseBody(UPDATED_RESPONSE_BODY)
            .timestamp(UPDATED_TIMESTAMP)
            .durationMs(UPDATED_DURATION_MS)
            .errorMessage(UPDATED_ERROR_MESSAGE)
            .errorType(UPDATED_ERROR_TYPE)
            .serviceName(UPDATED_SERVICE_NAME)
            .environment(UPDATED_ENVIRONMENT)
            .userAgent(UPDATED_USER_AGENT)
            .clientIp(UPDATED_CLIENT_IP)
            .success(UPDATED_SUCCESS)
            .retryCount(UPDATED_RETRY_COUNT)
            .kafkaTopic(UPDATED_KAFKA_TOPIC)
            .sessionId(UPDATED_SESSION_ID)
            .userId(UPDATED_USER_ID);
    }

    @BeforeEach
    void initTest() {
        httpCallAudit = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedHttpCallAudit != null) {
            httpCallAuditRepository.delete(insertedHttpCallAudit);
            insertedHttpCallAudit = null;
        }
    }

    @Test
    @Transactional
    void createHttpCallAudit() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the HttpCallAudit
        var returnedHttpCallAudit = om.readValue(
            restHttpCallAuditMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(httpCallAudit)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            HttpCallAudit.class
        );

        // Validate the HttpCallAudit in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertHttpCallAuditUpdatableFieldsEquals(returnedHttpCallAudit, getPersistedHttpCallAudit(returnedHttpCallAudit));

        insertedHttpCallAudit = returnedHttpCallAudit;
    }

    @Test
    @Transactional
    void createHttpCallAuditWithExistingId() throws Exception {
        // Create the HttpCallAudit with an existing ID
        httpCallAudit.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHttpCallAuditMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(httpCallAudit)))
            .andExpect(status().isBadRequest());

        // Validate the HttpCallAudit in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCorrelationIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        httpCallAudit.setCorrelationId(null);

        // Create the HttpCallAudit, which fails.

        restHttpCallAuditMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(httpCallAudit)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMethodIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        httpCallAudit.setMethod(null);

        // Create the HttpCallAudit, which fails.

        restHttpCallAuditMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(httpCallAudit)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTimestampIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        httpCallAudit.setTimestamp(null);

        // Create the HttpCallAudit, which fails.

        restHttpCallAuditMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(httpCallAudit)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkServiceNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        httpCallAudit.setServiceName(null);

        // Create the HttpCallAudit, which fails.

        restHttpCallAuditMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(httpCallAudit)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSuccessIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        httpCallAudit.setSuccess(null);

        // Create the HttpCallAudit, which fails.

        restHttpCallAuditMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(httpCallAudit)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRetryCountIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        httpCallAudit.setRetryCount(null);

        // Create the HttpCallAudit, which fails.

        restHttpCallAuditMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(httpCallAudit)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHttpCallAudits() throws Exception {
        // Initialize the database
        insertedHttpCallAudit = httpCallAuditRepository.saveAndFlush(httpCallAudit);

        // Get all the httpCallAuditList
        restHttpCallAuditMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(httpCallAudit.getId().intValue())))
            .andExpect(jsonPath("$.[*].correlationId").value(hasItem(DEFAULT_CORRELATION_ID)))
            .andExpect(jsonPath("$.[*].method").value(hasItem(DEFAULT_METHOD)))
            .andExpect(jsonPath("$.[*].basePath").value(hasItem(DEFAULT_BASE_PATH)))
            .andExpect(jsonPath("$.[*].endpoint").value(hasItem(DEFAULT_ENDPOINT)))
            .andExpect(jsonPath("$.[*].fullUrl").value(hasItem(DEFAULT_FULL_URL)))
            .andExpect(jsonPath("$.[*].pathParams").value(hasItem(DEFAULT_PATH_PARAMS)))
            .andExpect(jsonPath("$.[*].queryParams").value(hasItem(DEFAULT_QUERY_PARAMS)))
            .andExpect(jsonPath("$.[*].requestHeaders").value(hasItem(DEFAULT_REQUEST_HEADERS)))
            .andExpect(jsonPath("$.[*].requestBody").value(hasItem(DEFAULT_REQUEST_BODY)))
            .andExpect(jsonPath("$.[*].responseStatusCode").value(hasItem(DEFAULT_RESPONSE_STATUS_CODE)))
            .andExpect(jsonPath("$.[*].responseStatusText").value(hasItem(DEFAULT_RESPONSE_STATUS_TEXT)))
            .andExpect(jsonPath("$.[*].responseHeaders").value(hasItem(DEFAULT_RESPONSE_HEADERS)))
            .andExpect(jsonPath("$.[*].responseBody").value(hasItem(DEFAULT_RESPONSE_BODY)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].durationMs").value(hasItem(DEFAULT_DURATION_MS.intValue())))
            .andExpect(jsonPath("$.[*].errorMessage").value(hasItem(DEFAULT_ERROR_MESSAGE)))
            .andExpect(jsonPath("$.[*].errorType").value(hasItem(DEFAULT_ERROR_TYPE)))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME)))
            .andExpect(jsonPath("$.[*].environment").value(hasItem(DEFAULT_ENVIRONMENT)))
            .andExpect(jsonPath("$.[*].userAgent").value(hasItem(DEFAULT_USER_AGENT)))
            .andExpect(jsonPath("$.[*].clientIp").value(hasItem(DEFAULT_CLIENT_IP)))
            .andExpect(jsonPath("$.[*].success").value(hasItem(DEFAULT_SUCCESS)))
            .andExpect(jsonPath("$.[*].retryCount").value(hasItem(DEFAULT_RETRY_COUNT)))
            .andExpect(jsonPath("$.[*].kafkaTopic").value(hasItem(DEFAULT_KAFKA_TOPIC)))
            .andExpect(jsonPath("$.[*].sessionId").value(hasItem(DEFAULT_SESSION_ID)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)));
    }

    @Test
    @Transactional
    void getHttpCallAudit() throws Exception {
        // Initialize the database
        insertedHttpCallAudit = httpCallAuditRepository.saveAndFlush(httpCallAudit);

        // Get the httpCallAudit
        restHttpCallAuditMockMvc
            .perform(get(ENTITY_API_URL_ID, httpCallAudit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(httpCallAudit.getId().intValue()))
            .andExpect(jsonPath("$.correlationId").value(DEFAULT_CORRELATION_ID))
            .andExpect(jsonPath("$.method").value(DEFAULT_METHOD))
            .andExpect(jsonPath("$.basePath").value(DEFAULT_BASE_PATH))
            .andExpect(jsonPath("$.endpoint").value(DEFAULT_ENDPOINT))
            .andExpect(jsonPath("$.fullUrl").value(DEFAULT_FULL_URL))
            .andExpect(jsonPath("$.pathParams").value(DEFAULT_PATH_PARAMS))
            .andExpect(jsonPath("$.queryParams").value(DEFAULT_QUERY_PARAMS))
            .andExpect(jsonPath("$.requestHeaders").value(DEFAULT_REQUEST_HEADERS))
            .andExpect(jsonPath("$.requestBody").value(DEFAULT_REQUEST_BODY))
            .andExpect(jsonPath("$.responseStatusCode").value(DEFAULT_RESPONSE_STATUS_CODE))
            .andExpect(jsonPath("$.responseStatusText").value(DEFAULT_RESPONSE_STATUS_TEXT))
            .andExpect(jsonPath("$.responseHeaders").value(DEFAULT_RESPONSE_HEADERS))
            .andExpect(jsonPath("$.responseBody").value(DEFAULT_RESPONSE_BODY))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP.toString()))
            .andExpect(jsonPath("$.durationMs").value(DEFAULT_DURATION_MS.intValue()))
            .andExpect(jsonPath("$.errorMessage").value(DEFAULT_ERROR_MESSAGE))
            .andExpect(jsonPath("$.errorType").value(DEFAULT_ERROR_TYPE))
            .andExpect(jsonPath("$.serviceName").value(DEFAULT_SERVICE_NAME))
            .andExpect(jsonPath("$.environment").value(DEFAULT_ENVIRONMENT))
            .andExpect(jsonPath("$.userAgent").value(DEFAULT_USER_AGENT))
            .andExpect(jsonPath("$.clientIp").value(DEFAULT_CLIENT_IP))
            .andExpect(jsonPath("$.success").value(DEFAULT_SUCCESS))
            .andExpect(jsonPath("$.retryCount").value(DEFAULT_RETRY_COUNT))
            .andExpect(jsonPath("$.kafkaTopic").value(DEFAULT_KAFKA_TOPIC))
            .andExpect(jsonPath("$.sessionId").value(DEFAULT_SESSION_ID))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID));
    }

    @Test
    @Transactional
    void getNonExistingHttpCallAudit() throws Exception {
        // Get the httpCallAudit
        restHttpCallAuditMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHttpCallAudit() throws Exception {
        // Initialize the database
        insertedHttpCallAudit = httpCallAuditRepository.saveAndFlush(httpCallAudit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the httpCallAudit
        HttpCallAudit updatedHttpCallAudit = httpCallAuditRepository.findById(httpCallAudit.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHttpCallAudit are not directly saved in db
        em.detach(updatedHttpCallAudit);
        updatedHttpCallAudit
            .correlationId(UPDATED_CORRELATION_ID)
            .method(UPDATED_METHOD)
            .basePath(UPDATED_BASE_PATH)
            .endpoint(UPDATED_ENDPOINT)
            .fullUrl(UPDATED_FULL_URL)
            .pathParams(UPDATED_PATH_PARAMS)
            .queryParams(UPDATED_QUERY_PARAMS)
            .requestHeaders(UPDATED_REQUEST_HEADERS)
            .requestBody(UPDATED_REQUEST_BODY)
            .responseStatusCode(UPDATED_RESPONSE_STATUS_CODE)
            .responseStatusText(UPDATED_RESPONSE_STATUS_TEXT)
            .responseHeaders(UPDATED_RESPONSE_HEADERS)
            .responseBody(UPDATED_RESPONSE_BODY)
            .timestamp(UPDATED_TIMESTAMP)
            .durationMs(UPDATED_DURATION_MS)
            .errorMessage(UPDATED_ERROR_MESSAGE)
            .errorType(UPDATED_ERROR_TYPE)
            .serviceName(UPDATED_SERVICE_NAME)
            .environment(UPDATED_ENVIRONMENT)
            .userAgent(UPDATED_USER_AGENT)
            .clientIp(UPDATED_CLIENT_IP)
            .success(UPDATED_SUCCESS)
            .retryCount(UPDATED_RETRY_COUNT)
            .kafkaTopic(UPDATED_KAFKA_TOPIC)
            .sessionId(UPDATED_SESSION_ID)
            .userId(UPDATED_USER_ID);

        restHttpCallAuditMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHttpCallAudit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedHttpCallAudit))
            )
            .andExpect(status().isOk());

        // Validate the HttpCallAudit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHttpCallAuditToMatchAllProperties(updatedHttpCallAudit);
    }

    @Test
    @Transactional
    void putNonExistingHttpCallAudit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        httpCallAudit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHttpCallAuditMockMvc
            .perform(
                put(ENTITY_API_URL_ID, httpCallAudit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(httpCallAudit))
            )
            .andExpect(status().isBadRequest());

        // Validate the HttpCallAudit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHttpCallAudit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        httpCallAudit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHttpCallAuditMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(httpCallAudit))
            )
            .andExpect(status().isBadRequest());

        // Validate the HttpCallAudit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHttpCallAudit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        httpCallAudit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHttpCallAuditMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(httpCallAudit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HttpCallAudit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHttpCallAuditWithPatch() throws Exception {
        // Initialize the database
        insertedHttpCallAudit = httpCallAuditRepository.saveAndFlush(httpCallAudit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the httpCallAudit using partial update
        HttpCallAudit partialUpdatedHttpCallAudit = new HttpCallAudit();
        partialUpdatedHttpCallAudit.setId(httpCallAudit.getId());

        partialUpdatedHttpCallAudit
            .basePath(UPDATED_BASE_PATH)
            .endpoint(UPDATED_ENDPOINT)
            .fullUrl(UPDATED_FULL_URL)
            .requestBody(UPDATED_REQUEST_BODY)
            .responseStatusCode(UPDATED_RESPONSE_STATUS_CODE)
            .responseBody(UPDATED_RESPONSE_BODY)
            .timestamp(UPDATED_TIMESTAMP)
            .errorType(UPDATED_ERROR_TYPE)
            .serviceName(UPDATED_SERVICE_NAME)
            .environment(UPDATED_ENVIRONMENT)
            .clientIp(UPDATED_CLIENT_IP)
            .success(UPDATED_SUCCESS)
            .retryCount(UPDATED_RETRY_COUNT);

        restHttpCallAuditMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHttpCallAudit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHttpCallAudit))
            )
            .andExpect(status().isOk());

        // Validate the HttpCallAudit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHttpCallAuditUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedHttpCallAudit, httpCallAudit),
            getPersistedHttpCallAudit(httpCallAudit)
        );
    }

    @Test
    @Transactional
    void fullUpdateHttpCallAuditWithPatch() throws Exception {
        // Initialize the database
        insertedHttpCallAudit = httpCallAuditRepository.saveAndFlush(httpCallAudit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the httpCallAudit using partial update
        HttpCallAudit partialUpdatedHttpCallAudit = new HttpCallAudit();
        partialUpdatedHttpCallAudit.setId(httpCallAudit.getId());

        partialUpdatedHttpCallAudit
            .correlationId(UPDATED_CORRELATION_ID)
            .method(UPDATED_METHOD)
            .basePath(UPDATED_BASE_PATH)
            .endpoint(UPDATED_ENDPOINT)
            .fullUrl(UPDATED_FULL_URL)
            .pathParams(UPDATED_PATH_PARAMS)
            .queryParams(UPDATED_QUERY_PARAMS)
            .requestHeaders(UPDATED_REQUEST_HEADERS)
            .requestBody(UPDATED_REQUEST_BODY)
            .responseStatusCode(UPDATED_RESPONSE_STATUS_CODE)
            .responseStatusText(UPDATED_RESPONSE_STATUS_TEXT)
            .responseHeaders(UPDATED_RESPONSE_HEADERS)
            .responseBody(UPDATED_RESPONSE_BODY)
            .timestamp(UPDATED_TIMESTAMP)
            .durationMs(UPDATED_DURATION_MS)
            .errorMessage(UPDATED_ERROR_MESSAGE)
            .errorType(UPDATED_ERROR_TYPE)
            .serviceName(UPDATED_SERVICE_NAME)
            .environment(UPDATED_ENVIRONMENT)
            .userAgent(UPDATED_USER_AGENT)
            .clientIp(UPDATED_CLIENT_IP)
            .success(UPDATED_SUCCESS)
            .retryCount(UPDATED_RETRY_COUNT)
            .kafkaTopic(UPDATED_KAFKA_TOPIC)
            .sessionId(UPDATED_SESSION_ID)
            .userId(UPDATED_USER_ID);

        restHttpCallAuditMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHttpCallAudit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHttpCallAudit))
            )
            .andExpect(status().isOk());

        // Validate the HttpCallAudit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHttpCallAuditUpdatableFieldsEquals(partialUpdatedHttpCallAudit, getPersistedHttpCallAudit(partialUpdatedHttpCallAudit));
    }

    @Test
    @Transactional
    void patchNonExistingHttpCallAudit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        httpCallAudit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHttpCallAuditMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, httpCallAudit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(httpCallAudit))
            )
            .andExpect(status().isBadRequest());

        // Validate the HttpCallAudit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHttpCallAudit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        httpCallAudit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHttpCallAuditMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(httpCallAudit))
            )
            .andExpect(status().isBadRequest());

        // Validate the HttpCallAudit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHttpCallAudit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        httpCallAudit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHttpCallAuditMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(httpCallAudit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HttpCallAudit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHttpCallAudit() throws Exception {
        // Initialize the database
        insertedHttpCallAudit = httpCallAuditRepository.saveAndFlush(httpCallAudit);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the httpCallAudit
        restHttpCallAuditMockMvc
            .perform(delete(ENTITY_API_URL_ID, httpCallAudit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return httpCallAuditRepository.count();
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

    protected HttpCallAudit getPersistedHttpCallAudit(HttpCallAudit httpCallAudit) {
        return httpCallAuditRepository.findById(httpCallAudit.getId()).orElseThrow();
    }

    protected void assertPersistedHttpCallAuditToMatchAllProperties(HttpCallAudit expectedHttpCallAudit) {
        assertHttpCallAuditAllPropertiesEquals(expectedHttpCallAudit, getPersistedHttpCallAudit(expectedHttpCallAudit));
    }

    protected void assertPersistedHttpCallAuditToMatchUpdatableProperties(HttpCallAudit expectedHttpCallAudit) {
        assertHttpCallAuditAllUpdatablePropertiesEquals(expectedHttpCallAudit, getPersistedHttpCallAudit(expectedHttpCallAudit));
    }
}
