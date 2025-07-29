package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A HttpCallAudit.
 */
@Entity
@Table(name = "http_call_audit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HttpCallAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "correlation_id", nullable = false)
    private String correlationId;

    @NotNull
    @Column(name = "method", nullable = false)
    private String method;

    @Column(name = "base_path")
    private String basePath;

    @Column(name = "endpoint")
    private String endpoint;

    @Column(name = "full_url")
    private String fullUrl;

    @Column(name = "path_params")
    private String pathParams;

    @Column(name = "query_params")
    private String queryParams;

    @Column(name = "request_headers")
    private String requestHeaders;

    @Column(name = "request_body")
    private String requestBody;

    @Column(name = "response_status_code")
    private Integer responseStatusCode;

    @Column(name = "response_status_text")
    private String responseStatusText;

    @Column(name = "response_headers")
    private String responseHeaders;

    @Column(name = "response_body")
    private String responseBody;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    @Column(name = "duration_ms")
    private Long durationMs;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "error_type")
    private String errorType;

    @NotNull
    @Column(name = "service_name", nullable = false)
    private String serviceName;

    @Column(name = "environment")
    private String environment;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "client_ip")
    private String clientIp;

    @NotNull
    @Column(name = "success", nullable = false)
    private Boolean success;

    @NotNull
    @Column(name = "retry_count", nullable = false)
    private Integer retryCount;

    @Column(name = "kafka_topic")
    private String kafkaTopic;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "user_id")
    private String userId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public HttpCallAudit id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCorrelationId() {
        return this.correlationId;
    }

    public HttpCallAudit correlationId(String correlationId) {
        this.setCorrelationId(correlationId);
        return this;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getMethod() {
        return this.method;
    }

    public HttpCallAudit method(String method) {
        this.setMethod(method);
        return this;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getBasePath() {
        return this.basePath;
    }

    public HttpCallAudit basePath(String basePath) {
        this.setBasePath(basePath);
        return this;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getEndpoint() {
        return this.endpoint;
    }

    public HttpCallAudit endpoint(String endpoint) {
        this.setEndpoint(endpoint);
        return this;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getFullUrl() {
        return this.fullUrl;
    }

    public HttpCallAudit fullUrl(String fullUrl) {
        this.setFullUrl(fullUrl);
        return this;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getPathParams() {
        return this.pathParams;
    }

    public HttpCallAudit pathParams(String pathParams) {
        this.setPathParams(pathParams);
        return this;
    }

    public void setPathParams(String pathParams) {
        this.pathParams = pathParams;
    }

    public String getQueryParams() {
        return this.queryParams;
    }

    public HttpCallAudit queryParams(String queryParams) {
        this.setQueryParams(queryParams);
        return this;
    }

    public void setQueryParams(String queryParams) {
        this.queryParams = queryParams;
    }

    public String getRequestHeaders() {
        return this.requestHeaders;
    }

    public HttpCallAudit requestHeaders(String requestHeaders) {
        this.setRequestHeaders(requestHeaders);
        return this;
    }

    public void setRequestHeaders(String requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public String getRequestBody() {
        return this.requestBody;
    }

    public HttpCallAudit requestBody(String requestBody) {
        this.setRequestBody(requestBody);
        return this;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public Integer getResponseStatusCode() {
        return this.responseStatusCode;
    }

    public HttpCallAudit responseStatusCode(Integer responseStatusCode) {
        this.setResponseStatusCode(responseStatusCode);
        return this;
    }

    public void setResponseStatusCode(Integer responseStatusCode) {
        this.responseStatusCode = responseStatusCode;
    }

    public String getResponseStatusText() {
        return this.responseStatusText;
    }

    public HttpCallAudit responseStatusText(String responseStatusText) {
        this.setResponseStatusText(responseStatusText);
        return this;
    }

    public void setResponseStatusText(String responseStatusText) {
        this.responseStatusText = responseStatusText;
    }

    public String getResponseHeaders() {
        return this.responseHeaders;
    }

    public HttpCallAudit responseHeaders(String responseHeaders) {
        this.setResponseHeaders(responseHeaders);
        return this;
    }

    public void setResponseHeaders(String responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public String getResponseBody() {
        return this.responseBody;
    }

    public HttpCallAudit responseBody(String responseBody) {
        this.setResponseBody(responseBody);
        return this;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public HttpCallAudit timestamp(Instant timestamp) {
        this.setTimestamp(timestamp);
        return this;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Long getDurationMs() {
        return this.durationMs;
    }

    public HttpCallAudit durationMs(Long durationMs) {
        this.setDurationMs(durationMs);
        return this;
    }

    public void setDurationMs(Long durationMs) {
        this.durationMs = durationMs;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public HttpCallAudit errorMessage(String errorMessage) {
        this.setErrorMessage(errorMessage);
        return this;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorType() {
        return this.errorType;
    }

    public HttpCallAudit errorType(String errorType) {
        this.setErrorType(errorType);
        return this;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public HttpCallAudit serviceName(String serviceName) {
        this.setServiceName(serviceName);
        return this;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getEnvironment() {
        return this.environment;
    }

    public HttpCallAudit environment(String environment) {
        this.setEnvironment(environment);
        return this;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public HttpCallAudit userAgent(String userAgent) {
        this.setUserAgent(userAgent);
        return this;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getClientIp() {
        return this.clientIp;
    }

    public HttpCallAudit clientIp(String clientIp) {
        this.setClientIp(clientIp);
        return this;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public HttpCallAudit success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getRetryCount() {
        return this.retryCount;
    }

    public HttpCallAudit retryCount(Integer retryCount) {
        this.setRetryCount(retryCount);
        return this;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public String getKafkaTopic() {
        return this.kafkaTopic;
    }

    public HttpCallAudit kafkaTopic(String kafkaTopic) {
        this.setKafkaTopic(kafkaTopic);
        return this;
    }

    public void setKafkaTopic(String kafkaTopic) {
        this.kafkaTopic = kafkaTopic;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public HttpCallAudit sessionId(String sessionId) {
        this.setSessionId(sessionId);
        return this;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return this.userId;
    }

    public HttpCallAudit userId(String userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HttpCallAudit)) {
            return false;
        }
        return getId() != null && getId().equals(((HttpCallAudit) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HttpCallAudit{" +
            "id=" + getId() +
            ", correlationId='" + getCorrelationId() + "'" +
            ", method='" + getMethod() + "'" +
            ", basePath='" + getBasePath() + "'" +
            ", endpoint='" + getEndpoint() + "'" +
            ", fullUrl='" + getFullUrl() + "'" +
            ", pathParams='" + getPathParams() + "'" +
            ", queryParams='" + getQueryParams() + "'" +
            ", requestHeaders='" + getRequestHeaders() + "'" +
            ", requestBody='" + getRequestBody() + "'" +
            ", responseStatusCode=" + getResponseStatusCode() +
            ", responseStatusText='" + getResponseStatusText() + "'" +
            ", responseHeaders='" + getResponseHeaders() + "'" +
            ", responseBody='" + getResponseBody() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", durationMs=" + getDurationMs() +
            ", errorMessage='" + getErrorMessage() + "'" +
            ", errorType='" + getErrorType() + "'" +
            ", serviceName='" + getServiceName() + "'" +
            ", environment='" + getEnvironment() + "'" +
            ", userAgent='" + getUserAgent() + "'" +
            ", clientIp='" + getClientIp() + "'" +
            ", success='" + getSuccess() + "'" +
            ", retryCount=" + getRetryCount() +
            ", kafkaTopic='" + getKafkaTopic() + "'" +
            ", sessionId='" + getSessionId() + "'" +
            ", userId='" + getUserId() + "'" +
            "}";
    }
}
