package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.HttpCallAuditTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HttpCallAuditTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HttpCallAudit.class);
        HttpCallAudit httpCallAudit1 = getHttpCallAuditSample1();
        HttpCallAudit httpCallAudit2 = new HttpCallAudit();
        assertThat(httpCallAudit1).isNotEqualTo(httpCallAudit2);

        httpCallAudit2.setId(httpCallAudit1.getId());
        assertThat(httpCallAudit1).isEqualTo(httpCallAudit2);

        httpCallAudit2 = getHttpCallAuditSample2();
        assertThat(httpCallAudit1).isNotEqualTo(httpCallAudit2);
    }
}
