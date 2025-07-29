package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.OrganizationLocationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrganizationLocationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationLocation.class);
        OrganizationLocation organizationLocation1 = getOrganizationLocationSample1();
        OrganizationLocation organizationLocation2 = new OrganizationLocation();
        assertThat(organizationLocation1).isNotEqualTo(organizationLocation2);

        organizationLocation2.setId(organizationLocation1.getId());
        assertThat(organizationLocation1).isEqualTo(organizationLocation2);

        organizationLocation2 = getOrganizationLocationSample2();
        assertThat(organizationLocation1).isNotEqualTo(organizationLocation2);
    }
}
