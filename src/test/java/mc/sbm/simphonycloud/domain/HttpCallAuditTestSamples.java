package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class HttpCallAuditTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static HttpCallAudit getHttpCallAuditSample1() {
        return new HttpCallAudit()
            .id(1)
            .correlationId("correlationId1")
            .method("method1")
            .basePath("basePath1")
            .endpoint("endpoint1")
            .fullUrl("fullUrl1")
            .pathParams("pathParams1")
            .queryParams("queryParams1")
            .requestHeaders("requestHeaders1")
            .requestBody("requestBody1")
            .responseStatusCode(1)
            .responseStatusText("responseStatusText1")
            .responseHeaders("responseHeaders1")
            .responseBody("responseBody1")
            .durationMs(1L)
            .errorMessage("errorMessage1")
            .errorType("errorType1")
            .serviceName("serviceName1")
            .environment("environment1")
            .userAgent("userAgent1")
            .clientIp("clientIp1")
            .retryCount(1)
            .kafkaTopic("kafkaTopic1")
            .sessionId("sessionId1")
            .userId("userId1");
    }

    public static HttpCallAudit getHttpCallAuditSample2() {
        return new HttpCallAudit()
            .id(2)
            .correlationId("correlationId2")
            .method("method2")
            .basePath("basePath2")
            .endpoint("endpoint2")
            .fullUrl("fullUrl2")
            .pathParams("pathParams2")
            .queryParams("queryParams2")
            .requestHeaders("requestHeaders2")
            .requestBody("requestBody2")
            .responseStatusCode(2)
            .responseStatusText("responseStatusText2")
            .responseHeaders("responseHeaders2")
            .responseBody("responseBody2")
            .durationMs(2L)
            .errorMessage("errorMessage2")
            .errorType("errorType2")
            .serviceName("serviceName2")
            .environment("environment2")
            .userAgent("userAgent2")
            .clientIp("clientIp2")
            .retryCount(2)
            .kafkaTopic("kafkaTopic2")
            .sessionId("sessionId2")
            .userId("userId2");
    }

    public static HttpCallAudit getHttpCallAuditRandomSampleGenerator() {
        return new HttpCallAudit()
            .id(intCount.incrementAndGet())
            .correlationId(UUID.randomUUID().toString())
            .method(UUID.randomUUID().toString())
            .basePath(UUID.randomUUID().toString())
            .endpoint(UUID.randomUUID().toString())
            .fullUrl(UUID.randomUUID().toString())
            .pathParams(UUID.randomUUID().toString())
            .queryParams(UUID.randomUUID().toString())
            .requestHeaders(UUID.randomUUID().toString())
            .requestBody(UUID.randomUUID().toString())
            .responseStatusCode(intCount.incrementAndGet())
            .responseStatusText(UUID.randomUUID().toString())
            .responseHeaders(UUID.randomUUID().toString())
            .responseBody(UUID.randomUUID().toString())
            .durationMs(longCount.incrementAndGet())
            .errorMessage(UUID.randomUUID().toString())
            .errorType(UUID.randomUUID().toString())
            .serviceName(UUID.randomUUID().toString())
            .environment(UUID.randomUUID().toString())
            .userAgent(UUID.randomUUID().toString())
            .clientIp(UUID.randomUUID().toString())
            .retryCount(intCount.incrementAndGet())
            .kafkaTopic(UUID.randomUUID().toString())
            .sessionId(UUID.randomUUID().toString())
            .userId(UUID.randomUUID().toString());
    }
}
