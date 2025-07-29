package mc.sbm.simphonycloud.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mc.sbm.simphonycloud.domain.HttpCallAudit;
import mc.sbm.simphonycloud.repository.HttpCallAuditRepository;
import mc.sbm.simphonycloud.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link mc.sbm.simphonycloud.domain.HttpCallAudit}.
 */
@RestController
@RequestMapping("/api/http-call-audits")
@Transactional
public class HttpCallAuditResource {

    private static final Logger LOG = LoggerFactory.getLogger(HttpCallAuditResource.class);

    private static final String ENTITY_NAME = "httpCallAudit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HttpCallAuditRepository httpCallAuditRepository;

    public HttpCallAuditResource(HttpCallAuditRepository httpCallAuditRepository) {
        this.httpCallAuditRepository = httpCallAuditRepository;
    }

    /**
     * {@code POST  /http-call-audits} : Create a new httpCallAudit.
     *
     * @param httpCallAudit the httpCallAudit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new httpCallAudit, or with status {@code 400 (Bad Request)} if the httpCallAudit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<HttpCallAudit> createHttpCallAudit(@Valid @RequestBody HttpCallAudit httpCallAudit) throws URISyntaxException {
        LOG.debug("REST request to save HttpCallAudit : {}", httpCallAudit);
        if (httpCallAudit.getId() != null) {
            throw new BadRequestAlertException("A new httpCallAudit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        httpCallAudit = httpCallAuditRepository.save(httpCallAudit);
        return ResponseEntity.created(new URI("/api/http-call-audits/" + httpCallAudit.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, httpCallAudit.getId().toString()))
            .body(httpCallAudit);
    }

    /**
     * {@code PUT  /http-call-audits/:id} : Updates an existing httpCallAudit.
     *
     * @param id the id of the httpCallAudit to save.
     * @param httpCallAudit the httpCallAudit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated httpCallAudit,
     * or with status {@code 400 (Bad Request)} if the httpCallAudit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the httpCallAudit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HttpCallAudit> updateHttpCallAudit(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HttpCallAudit httpCallAudit
    ) throws URISyntaxException {
        LOG.debug("REST request to update HttpCallAudit : {}, {}", id, httpCallAudit);
        if (httpCallAudit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, httpCallAudit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!httpCallAuditRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        httpCallAudit = httpCallAuditRepository.save(httpCallAudit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, httpCallAudit.getId().toString()))
            .body(httpCallAudit);
    }

    /**
     * {@code PATCH  /http-call-audits/:id} : Partial updates given fields of an existing httpCallAudit, field will ignore if it is null
     *
     * @param id the id of the httpCallAudit to save.
     * @param httpCallAudit the httpCallAudit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated httpCallAudit,
     * or with status {@code 400 (Bad Request)} if the httpCallAudit is not valid,
     * or with status {@code 404 (Not Found)} if the httpCallAudit is not found,
     * or with status {@code 500 (Internal Server Error)} if the httpCallAudit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HttpCallAudit> partialUpdateHttpCallAudit(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HttpCallAudit httpCallAudit
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update HttpCallAudit partially : {}, {}", id, httpCallAudit);
        if (httpCallAudit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, httpCallAudit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!httpCallAuditRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HttpCallAudit> result = httpCallAuditRepository
            .findById(httpCallAudit.getId())
            .map(existingHttpCallAudit -> {
                if (httpCallAudit.getCorrelationId() != null) {
                    existingHttpCallAudit.setCorrelationId(httpCallAudit.getCorrelationId());
                }
                if (httpCallAudit.getMethod() != null) {
                    existingHttpCallAudit.setMethod(httpCallAudit.getMethod());
                }
                if (httpCallAudit.getBasePath() != null) {
                    existingHttpCallAudit.setBasePath(httpCallAudit.getBasePath());
                }
                if (httpCallAudit.getEndpoint() != null) {
                    existingHttpCallAudit.setEndpoint(httpCallAudit.getEndpoint());
                }
                if (httpCallAudit.getFullUrl() != null) {
                    existingHttpCallAudit.setFullUrl(httpCallAudit.getFullUrl());
                }
                if (httpCallAudit.getPathParams() != null) {
                    existingHttpCallAudit.setPathParams(httpCallAudit.getPathParams());
                }
                if (httpCallAudit.getQueryParams() != null) {
                    existingHttpCallAudit.setQueryParams(httpCallAudit.getQueryParams());
                }
                if (httpCallAudit.getRequestHeaders() != null) {
                    existingHttpCallAudit.setRequestHeaders(httpCallAudit.getRequestHeaders());
                }
                if (httpCallAudit.getRequestBody() != null) {
                    existingHttpCallAudit.setRequestBody(httpCallAudit.getRequestBody());
                }
                if (httpCallAudit.getResponseStatusCode() != null) {
                    existingHttpCallAudit.setResponseStatusCode(httpCallAudit.getResponseStatusCode());
                }
                if (httpCallAudit.getResponseStatusText() != null) {
                    existingHttpCallAudit.setResponseStatusText(httpCallAudit.getResponseStatusText());
                }
                if (httpCallAudit.getResponseHeaders() != null) {
                    existingHttpCallAudit.setResponseHeaders(httpCallAudit.getResponseHeaders());
                }
                if (httpCallAudit.getResponseBody() != null) {
                    existingHttpCallAudit.setResponseBody(httpCallAudit.getResponseBody());
                }
                if (httpCallAudit.getTimestamp() != null) {
                    existingHttpCallAudit.setTimestamp(httpCallAudit.getTimestamp());
                }
                if (httpCallAudit.getDurationMs() != null) {
                    existingHttpCallAudit.setDurationMs(httpCallAudit.getDurationMs());
                }
                if (httpCallAudit.getErrorMessage() != null) {
                    existingHttpCallAudit.setErrorMessage(httpCallAudit.getErrorMessage());
                }
                if (httpCallAudit.getErrorType() != null) {
                    existingHttpCallAudit.setErrorType(httpCallAudit.getErrorType());
                }
                if (httpCallAudit.getServiceName() != null) {
                    existingHttpCallAudit.setServiceName(httpCallAudit.getServiceName());
                }
                if (httpCallAudit.getEnvironment() != null) {
                    existingHttpCallAudit.setEnvironment(httpCallAudit.getEnvironment());
                }
                if (httpCallAudit.getUserAgent() != null) {
                    existingHttpCallAudit.setUserAgent(httpCallAudit.getUserAgent());
                }
                if (httpCallAudit.getClientIp() != null) {
                    existingHttpCallAudit.setClientIp(httpCallAudit.getClientIp());
                }
                if (httpCallAudit.getSuccess() != null) {
                    existingHttpCallAudit.setSuccess(httpCallAudit.getSuccess());
                }
                if (httpCallAudit.getRetryCount() != null) {
                    existingHttpCallAudit.setRetryCount(httpCallAudit.getRetryCount());
                }
                if (httpCallAudit.getKafkaTopic() != null) {
                    existingHttpCallAudit.setKafkaTopic(httpCallAudit.getKafkaTopic());
                }
                if (httpCallAudit.getSessionId() != null) {
                    existingHttpCallAudit.setSessionId(httpCallAudit.getSessionId());
                }
                if (httpCallAudit.getUserId() != null) {
                    existingHttpCallAudit.setUserId(httpCallAudit.getUserId());
                }

                return existingHttpCallAudit;
            })
            .map(httpCallAuditRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, httpCallAudit.getId().toString())
        );
    }

    /**
     * {@code GET  /http-call-audits} : get all the httpCallAudits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of httpCallAudits in body.
     */
    @GetMapping("")
    public List<HttpCallAudit> getAllHttpCallAudits() {
        LOG.debug("REST request to get all HttpCallAudits");
        return httpCallAuditRepository.findAll();
    }

    /**
     * {@code GET  /http-call-audits/:id} : get the "id" httpCallAudit.
     *
     * @param id the id of the httpCallAudit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the httpCallAudit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HttpCallAudit> getHttpCallAudit(@PathVariable("id") Long id) {
        LOG.debug("REST request to get HttpCallAudit : {}", id);
        Optional<HttpCallAudit> httpCallAudit = httpCallAuditRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(httpCallAudit);
    }

    /**
     * {@code DELETE  /http-call-audits/:id} : delete the "id" httpCallAudit.
     *
     * @param id the id of the httpCallAudit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHttpCallAudit(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete HttpCallAudit : {}", id);
        httpCallAuditRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
