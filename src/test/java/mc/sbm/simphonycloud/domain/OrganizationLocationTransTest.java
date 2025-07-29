package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.OrganizationLocationTransTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrganizationLocationTransTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationLocationTrans.class);
        OrganizationLocationTrans organizationLocationTrans1 = getOrganizationLocationTransSample1();
        OrganizationLocationTrans organizationLocationTrans2 = new OrganizationLocationTrans();
        assertThat(organizationLocationTrans1).isNotEqualTo(organizationLocationTrans2);

        organizationLocationTrans2.setId(organizationLocationTrans1.getId());
        assertThat(organizationLocationTrans1).isEqualTo(organizationLocationTrans2);

        organizationLocationTrans2 = getOrganizationLocationTransSample2();
        assertThat(organizationLocationTrans1).isNotEqualTo(organizationLocationTrans2);
    }
}
